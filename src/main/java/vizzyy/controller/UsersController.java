package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.domain.User;
import vizzyy.service.KeyService;
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

    @RequestMapping(value= "list")
    public List<User> users(){
        return userService.getUsers();
    }

    @RequestMapping(value = "generate")
    public void generate(@RequestParam String CN, @RequestParam String role, @RequestParam String pw) throws IOException, InterruptedException {
        keyService.generatePair(CN);
        keyService.createSigningRequest(CN);
        keyService.signWithCA(CN);
        keyService.combine(CN, pw);
        keyService.export(CN);
        userService.createUser(CN, role);
    }
}
