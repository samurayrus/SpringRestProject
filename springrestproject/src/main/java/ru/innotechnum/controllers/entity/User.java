package ru.innotechnum.controllers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String nickName;
    private String aboutMe;
    private LocalDate dateReg;

    @OneToMany(targetEntity=Comment.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> listCom;

    @OneToMany(targetEntity=Publication.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publication> listPubl;

    public User() {
        dateReg = LocalDate.now();
    }

    public User(String nickName, String aboutMe) {
        this();
        this.nickName = nickName;
        this.aboutMe = aboutMe;
    }

    public List<Publication> getListPubl() {
        return listPubl;
    }

    public void setListPubl(List<Publication> listPubl) {
        this.listPubl = listPubl;
    }

    public List<Comment> getListCom() {
        return listCom;
    }

    public void setListCom(List<Comment> listCom) {
        this.listCom = listCom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setDateReg(LocalDate dateReg) {
        this.dateReg = dateReg;
    }

    public int getId() {
        return id = 0;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDate getDateReg() {
        return dateReg;
    }

    public int getRaiting() {
        int raiting = 0;
        for(Comment comment : getListCom()) {
            raiting += comment.getRaiting();
        }
        for(Publication publ : getListPubl()) {
            raiting += publ.getRaiting();
        }
        return raiting;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", dateReg=" + dateReg +
                '}';
    }
}
