package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vizzyy.domain.MotionRepository;
import vizzyy.service.LoggingService;
import vizzyy.service.S3ResourceService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(value = "/video")
public class VideoController {

    @Autowired
    LoggingService loggingService;

    @Autowired
    MotionRepository motionRepository;

    @Value("${rest.stream.limit}")
    String streamLengthMinutes;

    @Autowired
    RestTemplate sslRestTemplate;

    private static final String cameras = (String) S3ResourceService.loadFileFromS3("vizzyy-credentials", "cam.url").toArray()[0];

    @RequestMapping("/door")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_ADMIN')")
    public void door(HttpServletResponse response) {
        loggingService.addEntry("Calling /video/door...");

        sslRestTemplate.execute(
                URI.create(cameras + "/video"),
                HttpMethod.GET,
                null,
                responseExtractor -> {
                    response.setContentType("multipart/x-mixed-replace; boundary=BoundaryString");
                    copyLarge(responseExtractor.getBody(), response.getOutputStream());
                    loggingService.addEntry("Closing output stream");
//                    responseExtractor.close();
                    return null;
                }
        );
        loggingService.addEntry("Call to stream /video/door has ended.");
    }

    @RequestMapping("/recordings")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    public byte[] recordings(@RequestParam int spot) {
        loggingService.addEntry(String.format("Calling /video/recordings?spot=%s...", spot));
        return motionRepository.findImageAtPlace(spot).getImage();
    }

    // Custom implementation of IOUtils.copy(stream)
    // Allows us to close stream so it is not endlessly copying in>out
    public void copyLarge(InputStream input, OutputStream output)
            throws IOException {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = now.plus(Long.parseLong(streamLengthMinutes), ChronoUnit.MINUTES);
        loggingService.addEntry("now: "+now+", limit: "+limit);
        byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = input.read(buffer))) {
            now = LocalDateTime.now();
            if(now.isAfter(limit)) {
                loggingService.addEntry("Stream limit reached.");
                break;
            }
            output.write(buffer, 0, n);
        }
        output.close();
        loggingService.addEntry("Call to copyLarge resolved.");
    }

}
