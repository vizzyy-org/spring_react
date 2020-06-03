package vizzyy.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name="user_id")
    private Long id;

    @Column(name="common_name")
    private String commonName;

    @Column(name="username")
    private String username;

    @Column(name="pass")
    private String password;

    private String role;

    private boolean enabled;

    protected User() {}

    public User(Long user_id, String common_name, String role, Boolean enabled, String username, String password) {
        this.id = user_id;
        this.commonName = common_name;
        this.role = role;
        this.enabled = enabled;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, commonName='%s', role='%s', enabled='%b']",
                id, commonName, role, enabled);
    }

    public String getCommonName() {
        return commonName;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
