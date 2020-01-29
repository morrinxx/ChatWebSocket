import Model.Group;
import Model.Message;
import Model.User;
import com.google.gson.Gson;
import htlleonding.Repository.ChatDBRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Inject
    ChatDBRepository dbRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        if(!initialized)Init();
        if(IsKnown(username)) {
            User u = GetByUsername(username);
            u.setSession(session);
            System.out.println("----- WS: " + u.getUsername() + " is now online\n");
            SendMessageHistory(u);
        }
        else{     //If user is unknown -> connection will be refused
            System.out.println("----- WS: Connection to " + username + " refused\n");
            try {
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
        System.out.println("----- WS: " + removeUser.getUsername() + " is now offline\n");
        SendMessageHistory(removeUser);
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        System.out.println("----- WS: " + username + " had an error: " + throwable.getMessage() + "\n");
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        Gson g = new Gson();
        Message m = g.fromJson(message, Message.class);
        System.out.println("----- WS: Got message: " + m.getMsg() + "\n");
        dbRepository.addMessage(m);
        broadcast( message, GetGroupFromString(m.getGroup()));
    }

    private Group GetGroupFromString(String stringGroup) {
        for(Group g : groups){
            if(g.getName().equals(stringGroup))return g;
        }
        System.out.println("----- WS: Group \"" + stringGroup + "\" not found\n");
        return null;
    }

    private void broadcast(String message, Group g) {
        for (User u : g.getUsers()) {
            if (u.getSession() != null) {
                u.getSession().getAsyncRemote().sendObject(message, sendResult -> {
                    if (sendResult.getException() != null) {
                        System.out.println("----- WS: Unable to send message: " + sendResult.getException() + "\n");
                    }
                });
            }
        }
    }

    private CompletableFuture SendMessageHistory(User u){
        return CompletableFuture.supplyAsync(() ->{
            List<Message> dbMessages = dbRepository.getMessageHistory(u.getGroups());
            if(u.getSession() == null) System.out.println("----- WS: Session is null\n");
            for(Message message : dbMessages){
                Gson gson = new Gson();
                String m = gson.toJson(message);
                u.getSession().getAsyncRemote().sendObject(m, sendResult -> {
                    if(sendResult.getException() != null){
                        System.out.println("----- WS: Error in Async SendMessageHistory\n");
                    }
                });
            }
            return null;
        }, executor);
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
        System.out.println("----- WS: Error when searching for User\n");
        return null;
    }



    private void Init() {
        System.out.println("initializing ..."
);
        User u1 = new User("Anton", "a");
        User u2 = new User("Franz", "f");
        User u3 = new User("Peter", "p");
        User u4 = new User("Hans", "h");


        Group g1 = new Group("Family");
        Group g2 = new Group("School");
        Group g3 = new Group("Friends");

        u1.addGroup(g1);
        u1.addGroup(g2);
        u1.addGroup(g3);
        u2.addGroup(g1);
        u2.addGroup(g2);
        u2.addGroup(g3);
        u3.addGroup(g1);
        u3.addGroup(g2);
        u3.addGroup(g3);
        u4.addGroup(g1);
        u4.addGroup(g2);
        u4.addGroup(g3);
        g1.addUser(u1);
        g1.addUser(u2);
        g1.addUser(u3);
        g1.addUser(u4);
        g2.addUser(u1);
        g2.addUser(u2);
        g2.addUser(u3);
        g2.addUser(u4);
        g3.addUser(u1);
        g3.addUser(u2);
        g3.addUser(u3);
        g3.addUser(u4);

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        groups.add(g1);
        groups.add(g2);
        groups.add(g3);
        initialized = true;
        System.out.println(users.size() + " users added and " + groups.size() + " groups\n");
    }
}
