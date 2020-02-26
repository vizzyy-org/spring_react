package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.service.AuthenticationService;
import vizzyy.service.LoggingService;

import java.security.Principal;

@RestController
public class HomeController {

    @Autowired
    LoggingService loggingService;

    @RequestMapping(value = "/home")
    public void home(){
        loggingService.addEntry("Calling /home");
    }

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
