package at.htlleonding.repository;

import at.htlleonding.Model.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.TransactionScoped;

@ApplicationScoped
public class ChatDBRepository {
    @Inject
    EntityManager em;

    @TransactionScoped
    public void CreateUser(String username){
        System.out.println("bla");
        User user = new User(username);
        try {
            em.persist(user);
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}
