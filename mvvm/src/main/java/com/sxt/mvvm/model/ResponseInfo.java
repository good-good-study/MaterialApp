package com.sxt.mvvm.model;

public class ResponseInfo {
    public static final int OK = 0;
    public static final int ERROR = 1;
    public static final int CANCELED = 2;

    private final int code;
    private String error;
    private String userName;

    public ResponseInfo(int code) {
        this.code = code;
    }
}