package muse.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    //用接口是便于不同业务类型的erroe code统一格式（业务类型错误、系统类型错误）
    //错误码

    QUESTION_NOT_FOUND("你找的问题不在了，换个试试？");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
