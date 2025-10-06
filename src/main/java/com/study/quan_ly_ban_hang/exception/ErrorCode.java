package com.study.quan_ly_ban_hang.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    USER_EXISTED(1001, "User already exists"),
    INVALID_KEY_ERROR(1002, "Invalid key error"),
    INVALID_PASSWORD(1003, "Password must be at least 8 characters"),
    INVALID_USERNAME(1004, "Username must be at least 3 characters");

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
