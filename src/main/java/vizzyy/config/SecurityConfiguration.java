package vizzyy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vizzyy.domain.UserRepository;
import vizzyy.service.KeyService;
import vizzyy.service.LoggingService;
import vizzyy.service.UserService;

import java.security.SecureRandom;

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
    KeyService keyService;

    @Autowired
    SessionRegistry sessionRegistry;

    @Value("${password.hashing.salt}")
    int saltFactor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout().permitAll().logoutSuccessUrl("/login")
                .and()
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImp();
    }

}