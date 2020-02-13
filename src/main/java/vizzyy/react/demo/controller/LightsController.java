package vizzyy.react.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/lights")
public class LightsController {

    Logger log = LoggerFactory.getLogger(LightsController.class);

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/one")
    public String lightOne(@RequestParam Boolean state) {
        log.info("Calling /lights/one?state="+state);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9001/light1?status="+state, String.class);
        return res;
    }

    @RequestMapping(value = "/two")
    public String lightTwo(@RequestParam Boolean state) {
        log.info("Calling /lights/two?state="+state);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9001/light2?status="+state, String.class);
        return res;
    }

    @RequestMapping(value = "/three")
    public String lightThree(@RequestParam Boolean state) {
        log.info("Calling /lights/three?state="+state);
        String res = restTemplate.getForObject("https://vizzyy.ddns.net:9007/light1?status="+state, String.class);
        return res;
    }
}
