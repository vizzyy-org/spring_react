package vizzyy.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vizzyy.service.LoggingService;
import vizzyy.service.S3ResourceService;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping(value = "/video")
@PreAuthorize("hasAnyAuthority('ROLE_POWER', 'ROLE_ADMIN')")
public class VideoController {

    @Autowired
    LoggingService loggingService;

    private static String voxAuth = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/vox.password").toArray()[0];
    private static String oculusAuth = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/oculus.password").toArray()[0];
    private static String cameras = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/cam.url").toArray()[0];

    @RequestMapping("/oculus")
    public void oculus(HttpServletResponse response) {
        loggingService.addEntry("Calling /video/oculus...");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.execute(
                URI.create(cameras + ":9003"),
                HttpMethod.GET,
                clientHttpRequest -> {
                    clientHttpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION, "Basic "+oculusAuth);
                },
                responseExtractor -> {
                    response.setContentType("multipart/x-mixed-replace; boundary=BoundaryString");
                    IOUtils.copy(responseExtractor.getBody(), response.getOutputStream());
                    return null;
                }
        );
    }

    @RequestMapping("/door")
    public void door(HttpServletResponse response) {
        loggingService.addEntry("Calling /video/door...");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.execute(
                URI.create(cameras + ":9002"),
                HttpMethod.GET,
                clientHttpRequest -> {
                    clientHttpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION, "Basic "+voxAuth);
                },
                responseExtractor -> {
                    response.setContentType("multipart/x-mixed-replace; boundary=BoundaryString");
                    IOUtils.copy(responseExtractor.getBody(), response.getOutputStream());
                    return null;
                }
        );
    }

}
