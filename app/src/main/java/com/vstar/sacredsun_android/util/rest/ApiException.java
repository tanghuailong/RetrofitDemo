package com.vstar.sacredsun_android.util.rest;

/**
 * Created by tanghuailong on 2017/1/11.
 */

public class ApiException extends RuntimeException{

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }
    public ApiException(String message) {
        super(message);
    }
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            default:
                message="未知错误";
        }
        return message;
    }
}
