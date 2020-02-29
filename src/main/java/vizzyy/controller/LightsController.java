package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vizzyy.service.LoggingService;
import vizzyy.service.S3ResourceService;

@RestController
@RequestMapping(value = "/lights")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_POWER', 'ROLE_ADMIN')")
public class LightsController {

    @Autowired
    LoggingService loggingService;

    @Autowired
    RestTemplate restTemplate;

    private static String ddns = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/ddns.url").toArray()[0];

    @RequestMapping(value = "/one")
    public String lightOne(@RequestParam Boolean state) {
        loggingService.addEntry("Calling /lights/one?state="+state);
        String res = restTemplate.getForObject(ddns + ":9001/light1?status="+state, String.class);
        return res;
    }

    @RequestMapping(value = "/two")
    public String lightTwo(@RequestParam Boolean state) {
        loggingService.addEntry("Calling /lights/two?state="+state);
        String res = restTemplate.getForObject(ddns + ":9001/light2?status="+state, String.class);
        return res;
    }

    @RequestMapping(value = "/three")
    public String lightThree(@RequestParam Boolean state) {
        loggingService.addEntry("Calling /lights/three?state="+state);
        String res = restTemplate.getForObject(ddns + ":9007/light1?status="+state, String.class);
        return res;
    }
}
