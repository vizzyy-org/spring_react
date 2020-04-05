package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.controller.responses.DoorResponse;
import vizzyy.service.DoorService;

@RestController
@RequestMapping(value = "/door")
@PreAuthorize("hasAnyAuthority('ROLE_POWER', 'ROLE_ADMIN', 'ROLE_OWNER')")
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

    @RequestMapping(value = "/setstate", method = RequestMethod.GET)
    public void setState(@RequestParam boolean state){
        doorService.setDoorOpen(state);
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public DoorResponse getState(){
        return new DoorResponse(doorService.isDoorOpen());
    }

}
