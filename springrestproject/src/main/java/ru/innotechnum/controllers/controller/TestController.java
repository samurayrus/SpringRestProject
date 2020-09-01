package ru.innotechnum.controllers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.innotechnum.controllers.database.CommentRepository;
import ru.innotechnum.controllers.database.HistoryUserRepository;
import ru.innotechnum.controllers.database.PublicationRepository;
import ru.innotechnum.controllers.database.UserRepository;
import ru.innotechnum.controllers.entity.Comment;
import ru.innotechnum.controllers.entity.HistoryUser;
import ru.innotechnum.controllers.entity.Publication;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE) //Для /test
public class TestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HistoryUserRepository historyUserRepository;
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private CommentRepository commentRepository;

    /*
     * Находим имя пользователя в таблице с историей изменения, приписываем его комментарию и возвращаем.
     * getTestComHQL - Возвращаем комментарий через HQL запрос поиска в истории изменения данных юзера.
     * addNicknameToComments - для работы со списком
     * addNickname - для работы с одним комментарием
     * getTest - функционал. Выгружаем истроию изменения пользователя и через for ищем нужный.
     */

    @GetMapping("/{id}/comments/HQL") //Возврат никнейма по дате изменения через HQL
    public Comment getTestComHQL(@PathVariable int id) {
       Comment com = commentRepository.findById(id);
       com.setAuthName(historyUserRepository.findByUser(com.getDateCreate(), com.getUser()).getNickName());
       return com;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getTestCom(@PathVariable int id) { //Возврат никнейма по дате изменения через выгрузку
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
