package ru.innotechnum.controllers.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PublicationTable")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;
    private int raiting;

    private int authorId;
    private String authorName;
    private LocalDate dateCreate;

    public Publication() {}

    public Publication(String name, String text, int authorId) {
        raiting = 0;
        this.name=name;
        this.text = text;
        this.authorId = authorId;
        dateCreate = LocalDate.now();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getRaiting() {
        return raiting;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getText() {
        return text;
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

    @Override
    public String toString() {
        return getName() + getText() + getAuthorName() + getAuthorId() + getDateCreate();
    }
}
