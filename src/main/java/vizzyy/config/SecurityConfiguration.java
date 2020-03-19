package vizzyy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import vizzyy.domain.UserRepository;
import vizzyy.service.LoggingService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("enableAuth")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoggingService loggingService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .x509()
                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                .userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            List<vizzyy.domain.User> localUser = userRepository.findByCommonName(username);

            if(localUser.size() > 0) {
                System.out.println("User details: " + localUser.toString());
                return new User(username, "", getRole(localUser.get(0).getRole()));
            } else {
//                System.out.println("User not in DB: "+ username);
//                return new User(username, "", getRole("default"));
                return null;
            }
        };
    }

    private List<GrantedAuthority> getRole(String role){
        String authority;
        switch (role) {
            case "owner":
                authority = "ROLE_ADMIN";
                break;
            case "admin":
            case "power":
                authority = "ROLE_POWER";
                break;
            default:
                authority = "ROLE_BASE";
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }



}