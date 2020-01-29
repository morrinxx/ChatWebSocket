package htlleonding.Repository;

import Model.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Observable;

@ApplicationScoped
@Transactional
public class DbReaderObserverThread extends Observable implements Runnable {

    @Inject
    EntityManager em;
    public DbReaderObserverThread(EntityManager em){
        this.em = em;
    }
    public DbReaderObserverThread(){

    }

    @Override
    @Transactional
    public void run() {
        try {
            System.out.println("DbReaderObserverThread ready to rumble");
            System.out.println("With em: " + em);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Message> cquery = cb.createQuery(Message.class);
            Root<Message> root = cquery.from(Message.class);
            cquery.select(root);
            TypedQuery<Message> query = em.createQuery(cquery);
            List<Message> messageList = query.getResultList();
            setChanged();
            notifyObservers(messageList);
        }
        catch (Exception e){
            System.out.println(e);
        }


    }

}
