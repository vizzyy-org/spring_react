package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vizzyy.service.LoggingService;
import vizzyy.service.S3ResourceService;

@RestController
@RequestMapping(value = "/door")
@PreAuthorize("hasAnyAuthority('ROLE_POWER', 'ROLE_ADMIN')")
public class DoorController {

    @Autowired
    LoggingService loggingService;

    @Autowired
    RestTemplate restTemplate;

    private static String ddns = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/ddns.url").toArray()[0];

    @RequestMapping(value = "/open")
    public String open() {
        String entry = "User BLEEP BLOOP opened door at BLAH BLAH BLAH time.";
        loggingService.addEntry("Calling /door/open?entry="+entry);
        String res = restTemplate.getForObject(ddns + ":9000/open?entry="+entry, String.class);
        return res;
    }

    @RequestMapping(value = "/close")
    public String close() {
        String entry = "User BLEEP BLOOP closed door at BLAH BLAH BLAH time.";
        loggingService.addEntry("Calling /door/close?entry="+entry);
        String res = restTemplate.getForObject(ddns + ":9000/close?entry="+entry, String.class);
        return res;
    }

}
