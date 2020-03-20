package vizzyy.controller.responses;

public class RoleResponse {

    private String role;
    private String username;

    public RoleResponse(){
        this.role = "Generic_Role";
        this.username = "Generic_Username";
    }

    public RoleResponse(String role, String username) {
        this.role = role;
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
