package vizzyy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name="user_id")
    private Long id;
    @Column(name="common_name")
    private String commonName;
    private String role;

    protected User() {}

    public User(Long user_id, String common_name, String role) {
        this.id = user_id;
        this.commonName = common_name;
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, commonName='%s', role='%s']",
                id, commonName, role);
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
}
