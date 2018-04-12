package hu.elte.closet.exception;

public class ClosetException extends RuntimeException {

    public ClosetException() {
    }

    public ClosetException(String s) {
        super(s);
    }

    public ClosetException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ClosetException(Throwable throwable) {
        super(throwable);
    }

    public ClosetException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
