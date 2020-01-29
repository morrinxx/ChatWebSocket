package at.htlleonding.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chatSeq")

    private String type;
    private String username;
    private String password;
    private String msg;
    private String group;

    public Message(String type, String username, String password, String msg, String group) {
        this.type = type;
        this.username = username;
        this.password = password;
        this.msg = msg;
        this.group = group;
    }

    public Message() {
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

    public String getGroup() {
        return group;
    }

    public void setGroups(String group) {
        this.group = group;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
