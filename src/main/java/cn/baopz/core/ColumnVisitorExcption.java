package cn.baopz.core;

/**
 * @author baopz
 * @date 2018.08.16
 */
public class ColumnVisitorExcption extends Exception {

    public ColumnVisitorExcption() {
    }

    public ColumnVisitorExcption(String message) {
        super(message);
    }

    public ColumnVisitorExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public ColumnVisitorExcption(Throwable cause) {
        super(cause);
    }

    public ColumnVisitorExcption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
