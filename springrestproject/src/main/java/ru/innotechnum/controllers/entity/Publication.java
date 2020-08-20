package ru.innotechnum.controllers.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PUBLICATION_TABLE")
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
        this.name = name;
        this.text = text;
        this.authorId = authorId;
        dateCreate = LocalDate.now();
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
        return "{\n id  =" + getId() + ", Name = " + getName() + ", Text = " + getText() + ", DateCreate = " + getDateCreate() + ", AuthorId = " + getAuthorId() +
                ", AuthorName = " + getAuthorName() + "}";
    }
}
