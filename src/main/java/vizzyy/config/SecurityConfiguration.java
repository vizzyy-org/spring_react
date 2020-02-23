package vizzyy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("enableAuth")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("#{'${server.users.admin}'.split(',')}")
    private List<String> adminUsers;

    @Value("#{'${server.users.power}'.split(',')}")
    private List<String> powerUsers;

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
            System.out.println(username);
            if (adminUsers.contains(username)) { // If username exists in DB
                return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
            } else if (powerUsers.contains(username)) { // If username exists in DB
                return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_POWER"));
            } else {
                return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
            }
        };
    }

    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}