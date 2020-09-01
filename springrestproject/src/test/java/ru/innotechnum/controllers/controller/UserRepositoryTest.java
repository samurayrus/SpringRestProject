package ru.innotechnum.controllers.controller;

import org.junit.Before;
import org.junit.Test;
import ru.innotechnum.controllers.database.HistoryUserRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Publication;
import ru.innotechnum.controllers.entity.User;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.HistoryUser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class UserRepositoryTest {

    UserRepository userRepository;
    HistoryUserRepository historyUserRepository;

    private UserController userController;
    private List<User> users;

    @Before
    public void setupMock() {
        userRepository = mock(UserRepository.class);
        historyUserRepository = mock(HistoryUserRepository.class);
        userController = new UserController(userRepository, historyUserRepository);
        users = Arrays.asList(
                new User("Test1", "Test2"),
                new User("Test2", "Test2")
        );

        users.get(0).setId(0);
        users.get(1).setId(1);
        users.get(1).setDeleted(true);
        users.get(0).setListPubl(Arrays.asList(
                new Publication("TEXT", "ABUBU", 5, users.get(0), users.get(0).getNickName()),
                new Publication("TEXT2", "ABUBU2", 2, users.get(0), users.get(0).getNickName())
        ));

        users.get(0).setListCom(Arrays.asList(
                new Comment(0, "TEST", 3, users.get(0).getListPubl().get(0), users.get(0)),
                new Comment(1, "TEST2", 5, users.get(0).getListPubl().get(1), users.get(0))
        ));
         //MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetRepositoryUnit() {
        int id = 0;
        when(userRepository.findById(id)).thenReturn(users.get(id));

        assertEquals(userRepository.findById(id), users.get(id));
        assertEquals(userController.getUserInfo(id), users.get(id));
        assertEquals(java.util.Optional.of(userController.getRaiting(id)), java.util.Optional.of(10));

        verify(userRepository, times(3)).findById(id);
    }

    //Попытка удаления и записи в историю изменения данных о пользователе
    @Test
    public void whenDeleteSaveSaveHistory() {
        int id = 0;
        when(userRepository.findById(id)).thenReturn(users.get(id));
        when(userRepository.existsById(id)).thenReturn(true);
        when(historyUserRepository.save(any(HistoryUser.class))).thenReturn(new HistoryUser());  //thenAnswer(i->i.getArguments()[0]); //Mockito.any()  --- Варианты. Запомнить

        assertEquals(userController.deleteUser(id), "ok");
        verify(historyUserRepository, times(1)).save(any(HistoryUser.class));
    }

    //Попытка удаления уже удаленного пользователя
    @Test
    public void whenDeleteDeletedUnit() {
        int id = 1;
        when(userRepository.findById(id)).thenReturn(users.get(id));
        when(userRepository.existsById(id)).thenReturn(true);

        assertEquals(userController.deleteUser(id), "DELETED");
    }

}
