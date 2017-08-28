package com.xupt.splider.rule;

/**
 * Created by 梁峻磊 on 2017/8/27.
 */
public class RuleException extends RuntimeException {
    public RuleException() {
        super();
    }

    public RuleException(String message) {
        super(message);
    }

    public RuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleException(Throwable cause) {
        super(cause);
    }

    public RuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
