package htlleonding.Repository;

import Model.Group;
import Model.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@ApplicationScoped
public class ChatDBRepository {
    @Inject
    EntityManager em;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private List<Message> messageList = new LinkedList<>();
/*
    @Transactional
    public List<Message> getMessages(List<Group> groups){
        String[] stringsGroup = new String[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            stringsGroup[i] = groups.get(i).getName();
        }
        CompletableFuture.supplyAsync(() ->{
            System.out.println("Getting Messages Async");

            messageList = performGetMessages(stringsGroup);
            return null;
        }, executor);
        return messageList;
    }*/

    @Transactional
    public CompletionStage<Void> addMessage(Message m) {
        return CompletableFuture.supplyAsync(() ->{
            System.out.println("Adding Message Async");

            performeInsert(m);
            return null;
        }, executor);
    }
    /*
    @Transactional
    private List<Message> performGetMessages(String[] stringsGroup){
        TypedQuery<Message> query = em.createNamedQuery(Message.GET_MESSAGES, Message.class);
        query.setParameter("groups", stringsGroup);
        List<Message> result = query.getResultList();
        for(Message m : result ){
            System.out.println(m.getMsg());
        }
        return result;
    }
*/
    @Transactional
    private void performeInsert(Message m){
        em.merge(m);
    }
}
