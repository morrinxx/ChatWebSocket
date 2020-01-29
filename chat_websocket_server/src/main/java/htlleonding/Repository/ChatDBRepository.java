package htlleonding.Repository;

import Model.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@ApplicationScoped

public class ChatDBRepository {
    @Inject
    EntityManager em;

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Transactional
    public CompletionStage<Void> createUser(){
        System.out.println("Grüße im DBService");
        Message m = new Message();
        m.setMsg("hallaParaller");
        System.out.println(m.getMsg());
        return CompletableFuture.supplyAsync(() ->{
            System.out.println("in dem kack");

            doShit(m);
            return null;
        }, executor);
    }

    @Transactional
    public void doShit(Message m){
        System.out.println("in shit");
        em.persist(m);
    }
}
