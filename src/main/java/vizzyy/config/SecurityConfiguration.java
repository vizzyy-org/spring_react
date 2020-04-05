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
import vizzyy.service.UserService;

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

    @Autowired
    UserService userService;

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .x509().subjectPrincipalRegex("CN=(.*?)(?:,|$)").userDetailsService(userDetailsService())
                .and()
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            List<vizzyy.domain.User> localUser = userRepository.findByCommonName(username);

            if(localUser.size() > 0) {
                loggingService.addEntry("User details: " + localUser.toString());
                return new User(username, "", userService.getRole(localUser.get(0).getRole()));
            } else {
                return null;
            }
        };
    }

}