package ru.innotechnum.controllers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PUBLICATIONS")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;
    private int raiting;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private User user;

    private String authorName;
    private LocalDate dateCreate;

    @OneToMany(targetEntity = Comment.class, mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @Transient
    private int authorId;

    public Publication() {
        raiting = 0;
        dateCreate = LocalDate.now();
    }

    @JsonIgnore
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public int getRaiting() {
        return raiting;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", raiting=" + raiting +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", dateCreate=" + dateCreate +
                '}';
    }
}
