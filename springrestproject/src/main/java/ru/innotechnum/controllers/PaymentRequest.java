package ru.innotechnum.controllers;

public class PaymentRequest {

    private int userId;
    private String itemId;
    private double discount;
    private String name;
    private String text;
    private int authorid;
    public PaymentRequest(){
        System.out.println("CREATED_______________________");
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getText() {
        return text;
    }

    public int getAuthorid() {
        return authorid;
    }

    public String getName() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}