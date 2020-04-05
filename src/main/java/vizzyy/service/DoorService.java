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
        String entry = String.format("Door opened by: %s", AuthenticationService.getUserName());
        return restTemplate.getForObject(ddns + ":9000/open?entry="+entry, String.class);
    }

    public String closeDoor(){
        setDoorOpen(false);
        String entry = String.format("Door closed by: %s", AuthenticationService.getUserName());
        return restTemplate.getForObject(ddns + ":9000/close?entry="+entry, String.class);
    }

    public boolean isDoorOpen() {
        String remoteValue = restTemplate.getForObject(ddns + ":9000/status", String.class);
        loggingService.addEntry("Remote value of door: "+remoteValue);
        String localValue = isDoorOpen ? "Opened" : "Closed";
        loggingService.addEntry("Get door state - door is: " + localValue);
        return remoteValue != null && remoteValue.equals("Opened");
    }

    public void setDoorOpen(boolean doorOpen) {
        isDoorOpen = doorOpen;
    }
}
