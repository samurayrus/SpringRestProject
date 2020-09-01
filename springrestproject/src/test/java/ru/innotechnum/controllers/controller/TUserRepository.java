package ru.innotechnum.controllers.controller;

import org.junit.Before;
import org.junit.Test;
import ru.innotechnum.controllers.database.HistoryUserRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.HistoryUser;

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
        userController = mock(UserController.class);

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
    }

    @Test
    public void whenCall() {
        when(userRepository.findById(0)).thenReturn(users.get(0));
        //when(userRepository.save(users.get(0))).thenReturn(users.get(0));
       // when(historyUserRepository).thenReturn(null);
       // when(historyUserRepository.save).thenReturn();

        System.out.println(userRepository.findById(0).toString());
        System.out.println(userController.getUserInfo(0));
        //System.out.println(userController.getRaiting(0));

       // userController.addUser(users.get(0));
    }

}
