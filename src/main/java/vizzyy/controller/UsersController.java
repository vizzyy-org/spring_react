package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.config.SecurityConfiguration;
import vizzyy.domain.User;
import vizzyy.service.KeyService;
import vizzyy.service.LoggingService;
import vizzyy.service.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    KeyService keyService;

    @Autowired
    LoggingService loggingService;

    @RequestMapping(value= "/list")
    public List<User> users(){
        loggingService.addEntry("Calling /users/list");
        return userService.getUsers();
    }

    @RequestMapping(value = "/generate")
    public void generate(@RequestParam String CN, @RequestParam String role, @RequestParam String pw) throws IOException, InterruptedException {
        loggingService.addEntry(String.format("Calling /users/generate?CN=%s&role=%s&pw=%s", CN, role, pw));
        keyService.generatePair(CN);
        keyService.createSigningRequest(CN);
        keyService.signWithCA(CN);
        keyService.combine(CN, pw);
        keyService.export(CN);
        User newUser = userService.createUser(CN, role);
        loggingService.addEntry(String.format("Successfully created user: %s", newUser.toString()));
    }

    @RequestMapping(value = "/delete")
    public void delete(@RequestParam String CN) {
        loggingService.addEntry(String.format("Calling /users/remove?CN=%s", CN));
        userService.deleteUser(CN);
        userService.expireUserSessions(CN);
        loggingService.addEntry(String.format("Successfully delete user: %s", CN));
    }
}
