package muse.community.exception;

//找不到对应序号的问题详情页异常
public class CustomizeException extends RuntimeException {
    //不继承RuntimeException的话就要在controller中try/catch
    private Integer code;
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) { //异常时throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
