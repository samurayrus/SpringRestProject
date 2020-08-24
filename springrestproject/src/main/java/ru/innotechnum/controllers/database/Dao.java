package ru.innotechnum.controllers.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Deprecated
@Repository
@Transactional
public class Dao {
    @PersistenceContext
    private EntityManager entityManager;

    public String createUser(User user) {
        entityManager.persist(user);
        return "OK";
    }

    public User getUser(int id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    public String createPublication(Publication publication) {
        User user = entityManager.find(User.class, publication.getAuthorId());
        publication.setUser(user);
        publication.setAuthorName(user.getNickName());
        entityManager.persist(publication);
        return publication.getAuthorName();
    }

    public Publication getPublication(int id) {
        Publication publication = entityManager.find(Publication.class, id);
        return publication;
    }

    public List<Publication> getAllPublicationNew() {
        return entityManager.createQuery("from Publication p order by p.id desc", Publication.class).getResultList();
    }

    public List<Publication> getAllPublicationOld() {
        return entityManager.createQuery("from Publication p order by p.id asc", Publication.class).getResultList();
    }

    public List<Publication> getAllPublicationRaiting() {
        return entityManager.createQuery("from Publication p order by p.raiting asc", Publication.class).getResultList();
    }

    public List<Publication> getAllPublicationComments() {
        List<Publication> list = entityManager.createQuery("from Publication", Publication.class).getResultList();
        list.stream().sorted(Comparator.comparing(x -> x.getCommentList().size()));
        return list;
    }

    public String createComment(Comment comm) {
        comm.setComment(entityManager.find(Comment.class, comm.getParentId()));
        comm.setPublication(entityManager.find(Publication.class, comm.getPublicationId()));
        comm.setUser(getUser(comm.getAuthorId()));
        comm.setAuthName(comm.getUser().getNickName());
        entityManager.persist(comm);
        return getUser(comm.getAuthorId()).toString();
    }

    public String deleteComment(int id) {
        entityManager.remove(entityManager.find(Comment.class, id));
        return "entityManager.find(Comment.class, id).toString()";
    }

    public String addRaiting(int id) {
        Publication publication = entityManager.find(Publication.class, id);
        publication.setRaiting(publication.getRaiting() + 1);
        entityManager.merge(publication);
        return "+1";
    }

    public String editUser(User user, int id) {
        User orig = getUser(id);
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


}
