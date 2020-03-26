package vizzyy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DoorService {

    @Autowired
    LoggingService loggingService;

    @Autowired
    RestTemplate restTemplate;

    private boolean isDoorOpen = false;

    private static String ddns = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/ddns.url").toArray()[0];

    public String openDoor(){
        setDoorOpen(true);
        return restTemplate.getForObject(ddns + ":9000/open?entry=test", String.class);
    }

    public String closeDoor(){
        setDoorOpen(false);
        return restTemplate.getForObject(ddns + ":9000/close?entry=test", String.class);
    }

    public boolean isDoorOpen() {
        loggingService.addEntry("Get door state - door is opened: "+isDoorOpen);
        return isDoorOpen;
    }

    public void setDoorOpen(boolean doorOpen) {
        loggingService.addEntry("Set door state - door is opened: "+ doorOpen);
        isDoorOpen = doorOpen;
    }
}
