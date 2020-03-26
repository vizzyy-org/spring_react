package vizzyy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LightsService {

    @Autowired
    LoggingService loggingService;

    @Autowired
    RestTemplate restTemplate;

    private static String ddns = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/ddns.url").toArray()[0];

    public String toggleLight(int lightNumber, boolean state) {
        String url = "";
        switch (lightNumber){
            case 1:
                url = ":9001/light1?status=";
                break;
            case 2:
                url = ":9001/light2?status=";
                break;
            case 3:
                url = ":9007/light1?status=";
                break;
        }

        loggingService.addEntry(String.format("Light %d toggled %b by %s", lightNumber, state, AuthenticationService.getUserName()));

        return restTemplate.getForObject(ddns + url + state, String.class);

    }
}
