package cn.baopz.core;


/**
 * @author baopz
 * @date 2018.08.16
 */
public class ArticleReadException extends Exception{
    public ArticleReadException() {
    }

    public ArticleReadException(String message) {
        super(message);
    }

    public ArticleReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleReadException(Throwable cause) {
        super(cause);
    }

    public ArticleReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
