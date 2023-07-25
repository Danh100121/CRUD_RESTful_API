package com.globits.da.response;


import com.globits.da.validate.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Response<T>{
    private T data;
    private int errorCode;
    private String errorMessage;
    public Response(){
        this.errorCode = ResponseStatus.SUCCESS.getCode();
        this.errorMessage = ResponseStatus.SUCCESS.getMessage();
    }
    public Response(T data){
        this.data = data;
        this.errorCode = ResponseStatus.SUCCESS.getCode();
        this.errorMessage = ResponseStatus.SUCCESS.getMessage();
    }
    public Response(T data, ResponseStatus status) {
        this.data = data;
        this.errorCode = status.getCode();
        this.errorMessage = status.getMessage();
    }

    public Response(ResponseStatus status) {
        this.data = null;
        this.errorCode = status.getCode();
        this.errorMessage = status.getMessage();
    }

    public void setResponseStatus(ResponseStatus status) {
        this.errorCode = status.getCode();
        this.errorMessage = status.getMessage();
    }

}
