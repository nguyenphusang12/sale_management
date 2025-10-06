package com.study.quan_ly_ban_hang.exception;

public enum ErrorCode {
    USER_EXISTED(1001, "User already exists"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
