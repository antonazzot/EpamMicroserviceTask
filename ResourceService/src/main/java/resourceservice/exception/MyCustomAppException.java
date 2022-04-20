package resourceservice.exception;

public class MyCustomAppException extends RuntimeException{
    public MyCustomAppException() {
        super();
    }

    public MyCustomAppException(String message) {
        super(message);
    }

    public MyCustomAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyCustomAppException(Throwable cause) {
        super(cause);
    }
}
