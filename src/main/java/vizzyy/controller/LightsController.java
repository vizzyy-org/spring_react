package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.service.LightsService;

@RestController
@RequestMapping(value = "/lights")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_POWER', 'ROLE_ADMIN', 'ROLE_OWNER')")
public class LightsController {

    @Autowired
    LightsService lightsService;

    @RequestMapping(value = "/one")
    public String lightOne(@RequestParam Boolean state) {
        return lightsService.toggleLight(1, state);
    }

    @RequestMapping(value = "/two")
    public String lightTwo(@RequestParam Boolean state) {
        return lightsService.toggleLight(2, state);
    }

    @RequestMapping(value = "/inside")
    public String lightInside(@RequestParam String state) {
        return lightsService.toggleLight(3, state);
    }

    @RequestMapping(value = "/outside")
    public String lightOutside(@RequestParam String state) {
        return lightsService.toggleLight(4, state);
    }
}
