package at.gv.brz.wad2023.sample1_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Service
public class UserService {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    protected EntityManager getEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }

    public void insertUser(User user) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public Optional<User> findUserById(Long userId) {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }
}
