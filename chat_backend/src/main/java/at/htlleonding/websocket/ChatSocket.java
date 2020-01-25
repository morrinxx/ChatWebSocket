package at.htlleonding.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

import at.htlleonding.Model.User;

@ServerEndpoint("/chat/{username}")
@ApplicationScoped
public class ChatSocket {

    Map<User, Session> sessions = new ConcurrentHashMap<>();
    List<User> users = new ArrayList<User>(){
        {
            add(new User("t", "t"));
            add(new User("Franz-Peter", "f"));
            add(new User("a", "a"));
        }
    };

    //ChatDBRepository dbRepository = new ChatDBRepository();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        if(IsKnown(username)) {
            String[] split = username.split("_");
            User newUser = new User(split[0], split[1]);
            System.out.println("known user detected");
            sessions.put(newUser, session);
            broadcast("User " + newUser.getUsername() + " joined");
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
        sessions.remove(removeUser);
        if(IsKnown(username))broadcast("User " + removeUser.getUsername() + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        User removeUser = GetByUsername(username);
        sessions.remove(removeUser);
        broadcast("User " + username + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        broadcast(">> " + username + ": " + message);
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }

    private boolean IsKnown(String username){
        AtomicBoolean returnBool = new AtomicBoolean(false);
        users.forEach(u ->{
            if(u.getUsernameAndPassword().equals(username)){
                returnBool.set(true);
            }
        });
        System.out.println(returnBool.get());
        return returnBool.get();
    }
    private User GetByUsername(String username){
        for(User u : users){
            if(u.getUsernameAndPassword().equals(username))return u;
        }
        System.out.println("Error when searching for Username");
        return null;
    }

}