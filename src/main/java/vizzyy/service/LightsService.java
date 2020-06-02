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

    @Autowired
    S3ResourceService s3ResourceService;

    private final String ddns = (String) s3ResourceService.loadFileFromS3(s3ResourceService.getCredentialsBucket(), "ddns.url").toArray()[0];

    public String toggleLight(int lightNumber, Object state) {
        String url = "";
        switch (lightNumber){
            case 1:
                url = "/lights/light1?status=";
                break;
            case 2:
                url = "/lights/light2?status=";
                break;
            case 3:
                url = "/inside/arrange/";
                break;
            case 4:
                url = "/outside/arrange/";
                break;
        }

        loggingService.addEntry(String.format("Light %d toggled %s by %s", lightNumber, state, AuthenticationService.getUserName()));

        return restTemplate.getForObject(ddns + url + state, String.class);

    }
}
