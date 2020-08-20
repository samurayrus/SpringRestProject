package ru.innotechnum.controllers.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "text")
    private String text;
    @Column(name = "raiting")
    private int raiting;
    @Column(name = "authorid")
    private int authorId;
    @Column(name = "authname")
    private String authName;
    @Column(name = "publicationid")
    private int publicationId;
    @Column(name = "datecreate")
    private LocalDate DateCreate;
    @Column(name = "parentid")
    private int parentId;

    public Comment() {}

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

    public int getPublicationId() {
        return publicationId;
    }

    public LocalDate getDateCreate() {
        return DateCreate;
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

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public void setDateCreate(LocalDate dateCreate) {
        DateCreate = dateCreate;
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
                ", publicationId=" + publicationId +
                ", DateCreate=" + DateCreate +
                ", parentId=" + parentId +
                '}';
    }
}
