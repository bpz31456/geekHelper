package cn.baopz.core;

/**
 * @author baopz
 * @date 2018.08.16
 */
public class PaidContentBrowseException extends Exception {
    public PaidContentBrowseException() {
    }

    public PaidContentBrowseException(String message) {
        super(message);
    }

    public PaidContentBrowseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaidContentBrowseException(Throwable cause) {
        super(cause);
    }

    public PaidContentBrowseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
