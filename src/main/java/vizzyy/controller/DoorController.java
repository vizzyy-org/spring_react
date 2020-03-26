package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vizzyy.service.DoorService;
import vizzyy.service.LoggingService;
import vizzyy.service.S3ResourceService;

@RestController
@RequestMapping(value = "/door")
@PreAuthorize("hasAnyAuthority('ROLE_POWER', 'ROLE_ADMIN')")
public class DoorController {

    @Autowired
    DoorService doorService;

    @RequestMapping(value = "/open")
    public String open() {
        return doorService.openDoor();
    }

    @RequestMapping(value = "/close")
    public String close() {
        return doorService.closeDoor();
    }

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    public void setState(@RequestParam boolean state){
        doorService.setDoorOpen(state);
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public boolean getState(){
        return doorService.isDoorOpen();
    }

}
