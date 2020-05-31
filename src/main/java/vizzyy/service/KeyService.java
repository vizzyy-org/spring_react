package vizzyy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vizzyy.domain.UserRepository;

@Service
public class KeyService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoggingService loggingService;

    @Value("${rest.ca.endpoint}")
    String caEndpoint;

    public Object generateUser(String CN, String password){
        loggingService.addEntry(String.format("generateUser %s, by %s", CN, AuthenticationService.getUserName()));
        return restTemplate.getForObject(caEndpoint+"/createUser?cn="+CN+"&pw="+password, String.class);
    }

    public Object revokeUserCertificate(String CN){
        loggingService.addEntry(String.format("revokeUserCertificate %s, by %s", CN, AuthenticationService.getUserName()));
        return restTemplate.getForObject(caEndpoint+"/revokeUser?cn="+CN, String.class);
    }

    public Object checkRevoked(String CN){
        loggingService.addEntry(String.format("checkRevoked %s, by %s", CN, AuthenticationService.getUserName()));
        return restTemplate.getForObject(caEndpoint+"/checkRevoked?cn="+CN, String.class);
    }

}
