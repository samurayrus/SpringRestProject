package ru.innotechnum.controllers.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "USER_TABLE")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String nickName;
    private String aboutMe;
    private LocalDate dateReg;

    ///@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Publication> list;

    public User() {}

    public User(String nickName, String aboutMe) {
        this.nickName = nickName;
        this.aboutMe = aboutMe;
        dateReg = LocalDate.now();
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

    @Override
    public String toString() {
        return "\n{ NickName =" + getNickName() + ", AboutMe = " + getAboutMe() + ", DateReg = " + getDateReg() + ", id = " + getId() + "}";
    }
}
