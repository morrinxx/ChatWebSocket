package at.htlleonding.Repository;

import at.htlleonding.Model.Message;
import at.htlleonding.Model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped

public class DBService {

    //EntityManagerFactory ef = Persistence.createEntityManagerFactory("ChatPU");
//    EntityManager em = ef.createEntityManager();
    @Inject
    EntityManager em;

    @Transactional
    public void createUser(){
        System.out.println("Grüße im DBService");
        Message m = new Message();
        m.setMsg("hallaParaller");
        System.out.println(em);
        em.persist(m);
    }
}
