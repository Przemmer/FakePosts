package pl.pjsiwinski.fakeposts.model;

public class FpUser {

    private int id;
    private String name;
    private String username;
    private String email;

    public FpUser() {
        this.id = 0;
        this.name = "";
        this.username = "";
        this.email = "";
    }

    public int getId() {
        return id;
    }

    public void setId(String stringId) {
        this.id = Integer.parseInt(stringId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
