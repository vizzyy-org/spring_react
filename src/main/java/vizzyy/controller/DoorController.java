package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vizzyy.service.LoggingService;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_POWER', 'ROLE_ADMIN')")
public class DoorController {

    @Autowired
    LoggingService loggingService;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/door")
    public void door(){
        loggingService.addEntry("Calling /door");
    }

    @RequestMapping(value = "/door/open")
    public String open() {
        String entry = "User BLEEP BLOOP opened door at BLAH BLAH BLAH time.";
        loggingService.addEntry("Calling /door/open?entry="+entry);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9000/open?entry="+entry, String.class);
        return res;
    }

    @RequestMapping(value = "/door/close")
    public String close() {
        String entry = "User BLEEP BLOOP closed door at BLAH BLAH BLAH time.";
        loggingService.addEntry("Calling /door/close?entry="+entry);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9000/close?entry="+entry, String.class);
        return res;
    }

}
