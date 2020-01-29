package Model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

public class Group {

    private String name;
    private List<User> users = new LinkedList<>();


    public Group(String name) {
        this.name = name;
    }

    public Group(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User u){
        this.users.add(u);
    }
    public void removeUser(User u){
        this.users.remove(u);
    }
    
}
