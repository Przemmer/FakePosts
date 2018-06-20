package pl.pjsiwinski.fakeposts.model;

public class FpComment {

    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public FpComment(String postIdString, String idString, String name, String email, String body) {
        this.postId = Integer.parseInt(postIdString);
        this.id = Integer.parseInt(idString);
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
