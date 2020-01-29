package htlleonding.Repository;

import Model.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped

public class ChatDBRepository {
    @Inject
    EntityManager em;

    @Transactional
    public void createUser(){
        System.out.println("Grüße im DBService");
        Message m =new Message();
        m.setMsg("hallaParaller");
        System.out.println(em);
        em.persist(m);
    }
}
