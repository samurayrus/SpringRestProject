package ru.innotechnum.controllers.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.innotechnum.controllers.database.HistoryUserRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.HistoryUser;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class TUserRepository {

    UserRepository userRepository;
    UserController userController;
    HistoryUserRepository historyUserRepository;


    private List<User> users;

    @Before
    public void setupMock() {
        userRepository = mock(UserRepository.class);
        //userController = mock(UserController.class);
        userController = new UserController(userRepository,historyUserRepository);
        users = Arrays.asList(
                new User("Test1", "Test2"),
                new User("Test2", "Test2"),
                new User("Test3", "Test2"),
                new User("Test4", "Test2"),
                new User("Test5", "Test2")
        );
        users.get(0).setId(0);
        Publication publ = new Publication();
        publ.setUser(users.get(0));
        publ.setAuthorName("Test1");
        publ.setText("asd");
        publ.setName("testPubn");
        publ.setRaiting(5);
        Publication publ2 = new Publication();
        publ2.setUser(users.get(0));
        publ2.setAuthorName("Test1");
        publ2.setText("asasdd");
        publ2.setName("testasdPubn");
        publ2.setRaiting(2);
        users.get(0).setListPubl(Arrays.asList(publ,publ2));
        Comment comment = new Comment();
        comment.setUser(users.get(0));
        comment.setRaiting(3);
        comment.setText("asd");
        users.get(0).setListCom(Arrays.asList(comment));
       // MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCall() {
        when(userRepository.findById(0)).thenReturn(users.get(0));
        when(userRepository.existsById(0)).thenReturn(true);

        assertEquals(userRepository.findById(0), users.get(0));
        assertEquals(userController.getUserInfo(0), users.get(0));
        assertEquals(userController.deleteUser(0), "ok");
        assertEquals(java.util.Optional.of(userController.getRaiting(0)), java.util.Optional.of(10));

        verify(userRepository, times(4)).findById(0);
    }

}
