package me.sparky.promises;

/**
 * Exception thrown if promise is rejected with <code>String</code> for reason.
 *
 * @author Sparky
 * @since 1.0
 */
public class PromiseRejectionException extends RuntimeException {
    
    /**
     * Constructs a new <code>PromiseRejectionException</code>
     *
     * @since 1.0
     */
    public PromiseRejectionException() { super(); }
    
    /**
     * Constructs a new <code>PromiseRejectionException</code> with specified detail message.
     *
     * @param message The detail message
     * @since 1.0
     */
    public PromiseRejectionException(String message) { super(message); }
    
    /**
     * Constructs a new <code>PromiseRejectionException</code> with specified detail message and cause.
     *
     * @param message The detail message
     * @param cause The cause of this exception
     * @since 1.0
     */
    public PromiseRejectionException(String message, Throwable cause) { super(message, cause); }
    
    /**
     * Constructs a new <code>PromiseRejectionException</code> with specified cause.
     *
     * @param cause The cause of this exception
     * @since 1.0
     */
    public PromiseRejectionException(Throwable cause) { super(cause); }
    
    
    /**
     * Constructs a new <code>PromiseRejectionException</code> with specified detail message, cause,
     * suppression enabled or disabled, and writeable stack trace enabled or disabled.
     *
     * @param message The detail message
     * @param cause The cause of this exception
     * @param enableSuppression whether suppression should be enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable or not
     * @since 1.0
     */
    public PromiseRejectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        
        super(message, cause, enableSuppression, writableStackTrace);
    
    }
    
    
}
