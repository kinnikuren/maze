package util;

public class NullArgumentException extends IllegalArgumentException {
    private static final long serialVersionUID = 1114657687654L;

    public NullArgumentException() {
    }

    public NullArgumentException(String s) {
        super(s);
    }

    public NullArgumentException(Throwable cause) {
        super(cause);
    }

    public NullArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
