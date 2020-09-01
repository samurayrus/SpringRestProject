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
        userController = new UserController(userRepository, historyUserRepository);
        users = Arrays.asList(
                new User("Test1", "Test2"),
                new User("Test2", "Test2"),
                new User("Test3", "Test2")
        );

        users.get(0).setId(0);
        users.get(0).setListPubl(Arrays.asList(
                new Publication("TEXT", "ABUBU", 5, users.get(0), users.get(0).getNickName()),
                new Publication("TEXT2", "ABUBU2", 2, users.get(0), users.get(0).getNickName())
        ));

        users.get(0).setListCom(Arrays.asList(
                new Comment(0, "TEST", 3, users.get(0).getListPubl().get(0), users.get(0))
        ));
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
