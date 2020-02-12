package vizzyy.react.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/lights")
public class LightsController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/one")
    public String getProductList() {
        System.out.println("received call!");
        String greetings = restTemplate.getForObject("https://vizzyy.ddns.net:9001/light1?status=false", String.class);
        return greetings;
    }
}
