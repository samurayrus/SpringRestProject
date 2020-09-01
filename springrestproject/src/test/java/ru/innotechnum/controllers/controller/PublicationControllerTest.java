package ru.innotechnum.controllers.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.innotechnum.controllers.database.HistoryUserRepository;
import ru.innotechnum.controllers.database.PublicationRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class PublicationControllerTest {
    UserRepository userRepository;
    PublicationRepository publicationRepository;

    private PublicationController publicationController;
    private List<User> users;
    private List<Publication> publications;
    private List<Comment> comments;

    private List<Publication> publicationsSorted = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        publicationRepository = mock(PublicationRepository.class);
        publicationController = new PublicationController(publicationRepository, userRepository);

        users = Arrays.asList(
                new User("Test1", "Test2")
        );
        users.get(0).setId(0);

        publications = Arrays.asList(
                new Publication("TEXT", "ABUBU", 5, users.get(0), users.get(0).getNickName()),
                new Publication("TEXT2", "ABUBU2", 2, users.get(0), users.get(0).getNickName()),
                new Publication("TEXT3", "ABUBU3", 1, users.get(0), users.get(0).getNickName())
        );
        users.get(0).setListPubl(publications);

        comments = Arrays.asList(
                new Comment(0, "TEST", 3, users.get(0).getListPubl().get(0), users.get(0)),
                new Comment(1, "TEST2", 5, users.get(0).getListPubl().get(0), users.get(0)),
                new Comment(2, "TEST2", 3, users.get(0).getListPubl().get(1), users.get(0)),
                new Comment(3, "TEST2", 6, users.get(0).getListPubl().get(1), users.get(0)),
                new Comment(4, "TEST2", 6, users.get(0).getListPubl().get(1), users.get(0)),
                new Comment(5, "TEST2", 2, users.get(0).getListPubl().get(2), users.get(0))
        );
        users.get(0).setListCom(comments);
        publications.get(0).setCommentList(Arrays.asList(comments.get(0), comments.get(1)));
        publications.get(1).setCommentList(Arrays.asList(comments.get(2), comments.get(3)));
        publications.get(2).setCommentList(Arrays.asList(comments.get(4)));

    }

    @Test
    void getPublication() {
        int id = 0;
        when(publicationRepository.findById(id)).thenReturn(publications.get(id));

        assertEquals(publicationController.getPublication(id), publications.get(id));
        verify(publicationRepository, times(1)).findById(id);
    }

    @Test
    void getCommentAllPublication() {
        int id = 0;
        when(publicationRepository.findById(id)).thenReturn(publications.get(id));
        assertEquals(publicationController.getCommentAllPublication(id), publications.get(id).getCommentList());
    }

    @Test
    void getPublicationPopular() {
        when(publicationRepository.findAll()).thenReturn(publications);

        //Вот так должен выглядить ответ
        publicationsSorted = Arrays.asList(
                publications.get(2),
                publications.get(0),
                publications.get(1)
        );

        assertEquals(publicationController.getPublicationPopular(), publicationsSorted);
    }

    @Test
    void addPub() {
        int id = 0;
        when(userRepository.findById(id)).thenReturn(users.get(id));
        when(publicationRepository.save(any(Publication.class))).thenReturn(null);

        publicationController.addPub(new Publication());
        //void метод
        verify(publicationRepository, times(1)).save(any(Publication.class));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void addPublicationRaiting() {
        int id = 0;
        when(publicationRepository.findById(id)).thenReturn(publications.get(id));
        assertEquals(java.util.Optional.of(publicationController.addPublicationRaiting(id)), java.util.Optional.of(6));
    }

    @Test
    void editPublicationTrue() {
        int id = 0;
        when(publicationRepository.findById(id)).thenReturn(publications.get(id));
        when(publicationRepository.existsById(id)).thenReturn(true);
        assertEquals(publicationController.editPublication(id, publications.get(2)), publications.get(id).toString());
    }

    @Test
    void editPublicationFalse() {
        int id = 0;
        when(publicationRepository.findById(id)).thenReturn(publications.get(id));
        when(publicationRepository.existsById(id)).thenReturn(false);

        assertEquals(publicationController.editPublication(id, publications.get(2)), "NotFound");
    }
}