package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.domain.User;
import vizzyy.domain.UserRepository;
import vizzyy.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class UsersController {

    @Autowired
    UserService userService;

    @RequestMapping(value= "list")
    public List<User> users(){
        return userService.getUsers();
    }
}
