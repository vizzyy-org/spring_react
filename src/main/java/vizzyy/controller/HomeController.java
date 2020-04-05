package vizzyy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.service.AuthenticationService;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_POWER', 'ROLE_ADMIN', 'ROLE_OWNER')")
public class HomeController {

    @RequestMapping(value = "/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return "greeting";
    }

    @RequestMapping(value = "/user")
    public String user(Model model) {
        model.addAttribute("username", AuthenticationService.getUserName());
        return AuthenticationService.getUserName();
    }

}
