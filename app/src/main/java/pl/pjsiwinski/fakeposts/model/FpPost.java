package pl.pjsiwinski.fakeposts.model;

public class FpPost {

    private int userId;
    private int id;
    private String title;
    private String body;

    public FpPost(String userIdString, String StringId, String title, String body) {
        this.userId = Integer.parseInt(userIdString);
        this.id = Integer.parseInt(StringId);
        this.title = title;
        this.body = body;
    }

    public FpPost() {
        this.userId = 0;
        this.id = 0;
        this.title = "";
        this.body = "";
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setUserId(String userIdString) {
        this.userId = Integer.parseInt(userIdString);
    }

    public void setId(String StringId) {
        this.id = Integer.parseInt(StringId);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
