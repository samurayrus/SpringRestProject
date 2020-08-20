package ru.innotechnum.controllers.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class Dao {
    @PersistenceContext
    private EntityManager entityManager;

    public String createUser(User user) {
        entityManager.persist(user);
        return "OK";
    }

    public String createComment(Comment user) {
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
        var publ = entityManager.createQuery("Select SUM(raiting) from Publication where author_id=" + id).getResultList().get(0);
        return publ.toString();
    }

    public String createPublication(Publication publication) {
        publication.setAuthorName(entityManager.find(User.class, publication.getAuthorId()).getNickName());
        entityManager.persist(publication);
        return publication.getAuthorName();
    }

    public List<Publication> getAllPublication() {
        return entityManager.createQuery("from Publication p order by p.id desc", Publication.class).getResultList();
    }

}
