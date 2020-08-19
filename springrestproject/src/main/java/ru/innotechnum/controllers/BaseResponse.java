package ru.innotechnum.controllers;

public class BaseResponse {

    private  String status;
    private  String code;

    /*public BaseResponse(String status, Integer code) {
        this.status = status;
        this.code = code;
    }
*/

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}