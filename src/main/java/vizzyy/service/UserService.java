package vizzyy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vizzyy.domain.User;
import vizzyy.domain.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    LoggingService loggingService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<GrantedAuthority> getRole(String role){
        String authority;
        switch (role) {
            case "owner":
                authority = "ROLE_OWNER";
                break;
            case "admin":
                authority = "ROLE_ADMIN";
                break;
            case "power":
                authority = "ROLE_POWER";
                break;
            default:
                authority = "ROLE_BASE";
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User createUser(String CN, String role, String username, String clearTextPassword){
        int size = getUsers().size() + 1;
        User newUser = new User((long) size, CN, role, true, username, bCryptPasswordEncoder.encode(clearTextPassword));
        return userRepository.save(newUser);
    }

    public void deleteUser(String CN){
        User user = getUser(CN);
        if(user != null && !user.getRole().equals("owner"))
            userRepository.delete(user);
        else
            throw new RuntimeException("Could not delete applicant.");
    }

    public User getUser(String CN){
        List<User> results = userRepository.findByCommonName(CN);
        return results.size() > 0 ? results.get(0) : null;
    }

    public User getUserByUsername(String username){
        List<User> results = userRepository.findByUsername(username);
        return results.size() > 0 ? results.get(0) : null;
    }

    public void removeRole(String role){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities =
                auth.getAuthorities().stream()
                        .filter(r -> !role.equals(r.getAuthority()))
                        .collect(Collectors.toList());

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public void expireUserSessions(String username) {
        loggingService.addEntry("attempting to expire sessions... ");
        for (Object principal : sessionRegistry.getAllPrincipals()) {

            if (principal instanceof org.springframework.security.core.userdetails.User) {
                UserDetails userDetails = (UserDetails) principal;
                loggingService.addEntry("expireUserSessions currently inspecting: "+ userDetails.getUsername());
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        loggingService.addEntry("expiring "+username+": "+information.getPrincipal()+" - "+ information.getLastRequest());
                        information.expireNow();
                    }
                }
            } else {
                loggingService.addEntry("not User???");
            }
        }
    }

}
