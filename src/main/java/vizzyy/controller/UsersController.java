package vizzyy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class UsersController {
}
