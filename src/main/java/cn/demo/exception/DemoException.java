package cn.demo.exception;

public class DemoException extends Exception {

    private static final long serialVersionUID = -1912705045325386160L;
    /**
     * 错误码
     */
    private int code;

    public DemoException(String message) {
        super(message);
        this.code = -1;
    }

    public DemoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DemoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DemoException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public DemoException(Throwable throwable) {
        super(throwable);
    }

    public int getCode()
    {
        return code;
    }
}
