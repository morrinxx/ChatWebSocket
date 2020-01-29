package Model;

import javax.persistence.Entity;
import javax.websocket.Session;
import java.util.LinkedList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private List<Group> groups = new LinkedList<>();
    private Session session;

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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    public void addGroup(Group g){
        this.groups.add(g);
    }
}

