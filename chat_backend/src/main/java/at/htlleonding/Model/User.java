package at.htlleonding.Model;

import javax.persistence.*;

@Entity
@Table(name = "ChatUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="chatDBSeq")
    private int id;

    private String username;

    public User(String username) {
        this.username = username;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
