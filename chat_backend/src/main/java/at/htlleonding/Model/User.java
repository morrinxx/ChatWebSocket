package at.htlleonding.Model;

public class User {


    private String username;
    private String password;
    private Group group;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsernameAndPassword(){
        return this.username + "_" + this.password;
    }

    public String getUsername() {
        return username;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
