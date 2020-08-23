package ru.innotechnum.controllers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "text")
    private String text;
    @Column(name = "raiting")
    private int raiting;
    @Column(name = "authname")
    private String authName;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "publicationid")
    private Publication publication;

    @Column(name = "datecreate")
    private LocalDate dateCreate;

    @ManyToOne  //(cascade = CascadeType.ALL)
    @JoinColumn(name = "parentid")
    private Comment comment;

    @OneToMany(targetEntity=Comment.class, mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private int authorId;
    @Transient
    private int publicationId;
    @Transient
    private int parentId;

    public Comment() {
        dateCreate = LocalDate.now();
        raiting = 0;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
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

    public String getAuthName() {
        return authName;
    }


    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public int getParentId() {
        return parentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }


    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", raiting=" + raiting +
                ", authorId=" + authorId +
                ", authName='" + authName + '\'' +
                ", DateCreate=" + dateCreate +
                ", parentId=" + parentId +
                '}';
    }
}
