package vizzyy.domain;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name="user_id")
    private Long id;

    @Column(name="common_name")
    private String commonName;

    private String role;

    private boolean enabled;

    protected User() {}

    public User(Long user_id, String common_name, String role, Boolean enabled) {
        this.id = user_id;
        this.commonName = common_name;
        this.role = role;
        this.enabled = enabled;
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
}
