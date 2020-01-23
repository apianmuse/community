package muse.community.exception;

//找不到对应序号的问题详情页异常
public class CustomizeException extends RuntimeException {
    //不继承RuntimeException的话就要在controller中try/catch
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
