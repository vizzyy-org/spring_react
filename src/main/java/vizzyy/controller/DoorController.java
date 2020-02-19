package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vizzyy.service.LoggingService;

@RestController
@RequestMapping(value = "/door")
public class DoorController {

    @Autowired
    LoggingService loggingService;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/open")
    public String open() {
        String entry = "User BLEEP BLOOP opened door at BLAH BLAH BLAH time.";
        loggingService.addEntry("Calling /door/open?entry="+entry);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9000/open?entry="+entry, String.class);
        return res;
    }

    @RequestMapping(value = "/close")
    public String close() {
        String entry = "User BLEEP BLOOP closed door at BLAH BLAH BLAH time.";
        loggingService.addEntry("Calling /door/close?entry="+entry);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9000/close?entry="+entry, String.class);
        return res;
    }

}