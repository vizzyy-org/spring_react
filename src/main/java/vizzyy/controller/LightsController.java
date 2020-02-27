package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vizzyy.service.LoggingService;

@RestController
@RequestMapping(value = "/lights")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_POWER', 'ROLE_ADMIN')")
public class LightsController {

    @Autowired
    LoggingService loggingService;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/one")
    public String lightOne(@RequestParam Boolean state) {
        loggingService.addEntry("Calling /lights/one?state="+state);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9001/light1?status="+state, String.class);
        return res;
    }

    @RequestMapping(value = "/two")
    public String lightTwo(@RequestParam Boolean state) {
        loggingService.addEntry("Calling /lights/two?state="+state);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9001/light2?status="+state, String.class);
        return res;
    }

    @RequestMapping(value = "/three")
    public String lightThree(@RequestParam Boolean state) {
        loggingService.addEntry("Calling /lights/three?state="+state);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9007/light1?status="+state, String.class);
        return res;
    }
}
