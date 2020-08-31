package ru.innotechnum.controllers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.innotechnum.controllers.database.HistoryUserRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.HistoryUser;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE) //Для /test
public class TestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HistoryUserRepository historyUserRepository;

    /*
     * Находим имя пользователя в таблице с историей изменения, приписываем его комментарию и возвращаем.
     * addNicknameToComments - для работы со списком
     * addNickname - для работы с одним комментарием
     * getTest - функционал
     */

    @GetMapping("/{id}/comments")
    public List<Comment> getTestCom(@PathVariable int id) { //Возврат никнейма по дате изменения
        return addNicknameToComments(userRepository.findById(id).getListCom());
    }

    private List<Comment> addNicknameToComments(List<Comment> list) {
        for (Comment com : list) {
            addNickname(com);
        }
        return list;
    }

    private Comment addNickname(Comment com) {
        com.setAuthName(getTest(com.getUser().getId()));
        return com;
    }

    private String getTest(@PathVariable int id) { //Возврат никнейма по дате изменения
        LocalDate ld = LocalDate.now();
        List<HistoryUser> list = userRepository.findById(id).getHistoryUsers();
        for (HistoryUser historyUser : list) {
            if (historyUser.getDateBegin().compareTo(ld) < 0 && historyUser.getDateEnd().compareTo(ld) > 0) {
                return historyUser.getNickName();
            }
        }
        return "NotFound";
    }


    /*
    Транзакция удаления
    */
    @DeleteMapping("/del/{id}")
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void testDelUser(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
            //В качестве примера транзакции - после ввода всех изменений, выбрасываем исключение и делаем откат изменений
            dowork();
        } catch (InterruptedException ex) { }
    }

    void dowork() throws InterruptedException {
        Thread.sleep(3000);
        throw new RuntimeException();
    }
}
