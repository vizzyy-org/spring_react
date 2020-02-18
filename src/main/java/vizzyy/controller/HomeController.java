package vizzyy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return "greeting";
    }

    @RequestMapping(value = "/user")
    public String user(Model model, Principal principal) {
        UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        model.addAttribute("username", currentUser.getUsername());
        return currentUser.getUsername();
    }


}
