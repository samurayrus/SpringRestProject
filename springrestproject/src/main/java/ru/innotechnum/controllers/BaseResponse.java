package ru.innotechnum.controllers;

public class BaseResponse {

    private String Status;
    private String code;

    public BaseResponse(String status, String code) {
        Status = status;
        this.code = code;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "{" +
                "Status='" + Status + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}