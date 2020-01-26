package at.htlleonding.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

import at.htlleonding.Model.Group;
import at.htlleonding.Model.Message;
import at.htlleonding.Model.User;
import com.google.gson.Gson;

@ServerEndpoint("/chat/{username}")
@ApplicationScoped
public class ChatSocket {
    private boolean initialized = false;

    List<User> users = new LinkedList<>();

    List<Group> groups = new LinkedList<>();


    //ChatDBRepository dbRepository = new ChatDBRepository();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        if(!initialized)Init();
        if(IsKnown(username)) {
            User u = GetByUsername(username);
            u.setSession(session);
            System.out.println("known user detected");
            broadcast("User " + u.getUsername() + " joined", u.getGroup());
        }
        else{     //If user is unknown -> connection will be refused
            try {   //Todo not working
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        User removeUser = GetByUsername(username);
        removeUser.setSession(null);
        if(IsKnown(username))broadcast("User " + removeUser.getUsername() + " left", removeUser.getGroup());
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        User removeUser = GetByUsername(username);
        removeUser.setSession(null);
        broadcast("User " + username + " left on error: " + throwable, removeUser.getGroup());
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        System.out.println(message);
        Gson g = new Gson();
        Message m = g.fromJson(message, Message.class);
        System.out.println(m.getMsg());
        broadcast( GetByUsername(username).getUsername() + ": " + message, GetByUsername(username).getGroup());
    }

    private void broadcast(String message, Group g) {
        for(User u : g.getUsers()){
            if(u.getSession() != null){
                u.getSession().getAsyncRemote().sendObject(message, sendResult -> {
                    if (sendResult.getException() != null) {
                        System.out.println("Unable to send message: " + sendResult.getException());
                    }
                });
            }
        }
    }

    private boolean IsKnown(String username){
        AtomicBoolean returnBool = new AtomicBoolean(false);
        users.forEach(u ->{
            if(u.getUsernameAndPassword().equals(username)){
                returnBool.set(true);
            }
        });
        return returnBool.get();
    }
    private User GetByUsername(String username){
        for(User u : users){
            if(u.getUsernameAndPassword().equals(username))return u;
        }
        System.out.println("Error when searching for Username");
        return null;
    }

    private void Init() {
        System.out.println("initializing ...");
        User u1 = new User("a", "a");
        User u2 = new User("b", "b");
        User u3 = new User("c", "c");
        User u4 = new User("d", "d");
        User u5 = new User("e", "e");
        User u6 = new User("f", "f");
        User u7 = new User("g", "g");
        User u8 = new User("h", "h");

        Group g1 = new Group("ab");
        Group g2 = new Group("cd");
        Group g3 = new Group("ef");
        Group g4 = new Group("gh");

        u1.setGroup(g1);
        u2.setGroup(g1);
        u3.setGroup(g2);
        u4.setGroup(g2);
        u5.setGroup(g3);
        u6.setGroup(g3);
        u7.setGroup(g4);
        u8.setGroup(g4);
        g1.addUser(u1);
        g1.addUser(u2);
        g2.addUser(u3);
        g2.addUser(u4);
        g3.addUser(u5);
        g3.addUser(u6);
        g4.addUser(u7);
        g4.addUser(u8);
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
        users.add(u6);
        users.add(u7);
        users.add(u8);
        groups.add(g1);
        groups.add(g2);
        groups.add(g3);
        groups.add(g4);
        initialized = true;
        System.out.println(users.size() + " users added and " + groups.size() + " groups");
    }
}