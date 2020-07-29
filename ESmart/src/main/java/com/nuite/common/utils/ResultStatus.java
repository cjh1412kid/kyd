package com.nuite.common.utils;

public enum ResultStatus {
    SUCCESS(1, "操作成功"),
    DATA_ERROR(0, "数据级错误"),
    FAILED(-1, "操作失败"),
    REQUIRED(-10001, "必填参数为空"),
    DATA_NOT_EXIST(-10002, "数据不存在"),
    DATA_EXIST(-10003, "数据存在"),
    USER_ERROR(-10004, "用户错误"),
    SIGN_ERROR(-10005, "非法的请求签名"),
    DATA_FORMAT_ERROR(-10006, "参数格式错误");
    private final int value;
    private final String reasonPhrase;

    private ResultStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
