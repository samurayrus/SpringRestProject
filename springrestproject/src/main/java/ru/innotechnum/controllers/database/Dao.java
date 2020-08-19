package ru.innotechnum.controllers.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class Dao {
    @PersistenceContext
    private EntityManager entityManager;
    //entityManager.persist(country);
    //entityManager.find(Country.class, id);
    //entityManager.remove(country);

    public String createUser(User user) {
        entityManager.persist(user);
        return "OK";
    }

    public User findUser(int id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    public String addRaiting(int id) {
        Publication publication = entityManager.find(Publication.class, id);
        publication.setRaiting(publication.getRaiting()+1);
        entityManager.merge(publication);
        return "+1";
    }

    public String editUser(User user, int id) {
        User orig = findUser(id);
        orig.setNickName(user.getNickName());
        orig.setAboutMe(user.getAboutMe());
        entityManager.merge(orig);
        return "ok";
    }

    public String editPublication(int id, String name, String about) {
        Publication orig = entityManager.find(Publication.class, id);
        orig.setName(name);
        orig.setText(about);
        entityManager.merge(orig);
        return "ok";
    }

    public String getUserRaiting(int id) {
        //return entityManager.createQuery("from Country c order by c.id desc", Country.class).getResultList();
        //return entityManager.createQuery("from publication_table p order by p.author_id=3", Publication.class).getResultList();
        var publ = entityManager.createQuery("Select SUM(raiting) from Publication where author_id=" + id).getResultList().get(0);
        return publ.toString();
    }

    public String createPublication(Publication publication) {
        publication.setAuthorName(entityManager.find(User.class, publication.getAuthorId()).getNickName());
        entityManager.persist(publication);
        return publication.getAuthorName();
    }


}
