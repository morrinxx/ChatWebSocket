package htlleonding.Repository;

import Model.Group;
import Model.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@ApplicationScoped
@Transactional
public class ChatDBRepository {
    @Inject
    EntityManager em;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private List<Message> messageList = new LinkedList<>();



    public List<Message> getMessageHistory(List<Group> groups) {
        System.out.println("GetMessageHistory called");
        String[] stringsGroup = new String[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            stringsGroup[i] = groups.get(i).getName();
        }
        System.out.println("Getting Messages Async");

        return performGetMessages(stringsGroup);
    }

    public CompletionStage<Void> addMessage(Message m) {
        return CompletableFuture.supplyAsync(() ->{
            System.out.println("Adding message async");
            performInsert(m);
            return null;
        }, executor);
    }

    private void performInsert(Message m){
        em.persist(m);
    }

    private List<Message> performGetMessages(String[] stringsGroup){
        System.out.println("in performGetMessges");
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Message.class));
        messageList = em.createQuery(cq).getResultList();
        for(Message m : messageList ){
            System.out.println(m.getMsg());
        }
        System.out.println("HALAHLAHLAHLAH");
        return messageList;
    }
}
