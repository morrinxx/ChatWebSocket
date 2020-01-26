package at.htlleonding.Model;

public class Message {
    private String type;
    private String username;
    private String password;
    private String msg;

    public Message(String type, String username, String password, String msg) {
        this.type = type;
        this.username = username;
        this.password = password;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
