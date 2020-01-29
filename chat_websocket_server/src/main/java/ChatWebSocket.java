import Model.Group;
import Model.Message;
import Model.User;
import com.google.gson.Gson;
import htlleonding.Repository.ChatDBRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;



@ServerEndpoint("/chat/{username}")
@ApplicationScoped
public class ChatWebSocket
{
    private boolean initialized = false;

    List<User> users = new LinkedList<>();

    List<Group> groups = new LinkedList<>();

    List<String> messages = new LinkedList<>();

    @Inject
    ChatDBRepository dbRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        if(!initialized)Init();
        if(IsKnown(username)) {
            System.out.println("known");
            User u = GetByUsername(username);
            u.setSession(session);
            System.out.println(u.getUsername() + " is now online");
            SendMessageHistory(u);
        }
        else{     //If user is unknown -> connection will be refused
            System.out.println("Connection to " + username + " refused");
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        System.out.println("onclose called");
        User removeUser = GetByUsername(username);
        removeUser.setSession(null);
        System.out.println(removeUser.getUsername() + " is now offline");
        SendMessageHistory(removeUser);
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        System.out.println(username + " had an error: " + throwable.getMessage());
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        System.out.println(message);
        Gson g = new Gson();
        Message m = g.fromJson(message, Message.class);
        dbRepository.addMessage(m);
        messages.add(message);
        broadcast( message, GetGroupFromString(m.getGroup()));
    }

    private Group GetGroupFromString(String stringGroup) {
        for(Group g : groups){
            if(g.getName().equals(stringGroup))return g;
        }
        System.out.println("Gruppe nicht gefunden!");
        return null;
    }

    private void broadcast(String message, Group g) {
        for (User u : g.getUsers()) {
            if (u.getSession() != null) {
                u.getSession().getAsyncRemote().sendObject(message, sendResult -> {
                    if (sendResult.getException() != null) {
                        System.out.println("Unable to send message: " + sendResult.getException());
                    }
                });
            }
        }
    }
/*
    private void initialBroadcast(User u){
        List<Message> messages = dbRepository.getMessages(u.getGroups());

        for(Message m : messages){
            System.out.println("halla");
            u.getSession().getAsyncRemote().sendObject(m, sendResult -> {
                if (sendResult.getException() != null) {
                    System.out.println("Unable to send message history from " + u.getUsername() + " with result:  " + sendResult.getException());
                }
            });
        }
    }*/

    private void SendMessageHistory(User u){
        //dbRepository.getMessageHistory(u.getGroups());

        System.out.println(messages.size() + " messages");
        if(u.getSession() == null) System.out.println("session is null");
        for(String m : messages){
            Gson gson = new Gson();
            Message message = gson.fromJson(m, Message.class);
            for(Group g : u.getGroups()){
                if(g.getName().equals(message.getGroup())){
                    u.getSession().getAsyncRemote().sendObject(m, sendResult -> {
                        if(sendResult.getException() != null){
                            System.out.println("Error on MessageHistory");
                        }
                    });
                }
            }
        }
    }

    private boolean IsKnown(String username){
        for(User u : users){
            if(u.getUsernameAndPassword().equals(username))return true;
        }
        return false;
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

        u1.addGroup(g1);
        u1.addGroup(g2);
        u2.addGroup(g1);
        u2.addGroup(g2);
        u3.addGroup(g2);
        u4.addGroup(g2);
        u5.addGroup(g3);
        u6.addGroup(g3);
        u7.addGroup(g4);
        u8.addGroup(g4);
        g1.addUser(u1);
        g1.addUser(u2);
        g2.addUser(u1);
        g2.addUser(u2);
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
