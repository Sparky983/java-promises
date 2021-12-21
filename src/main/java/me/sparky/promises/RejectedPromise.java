package me.sparky.promises;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a rejected promise. This promise is more efficient than using
 * {@link CompletablePromise} and rejecting it.
 *
 * @param <T> The type of the promise
 * @author Sparky
 * @since 1.0
 */
public class RejectedPromise<T> implements Promise<T>{
    
    private final Throwable reason;
    
    /**
     * Constructs a rejected promise with specified reason.
     *
     * @param reason The reason for the rejection
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    public RejectedPromise(@NotNull Throwable reason) {
        
        this.reason = reason;
        
    }
    
    /**
     * Constructs a rejected promise with specified reason.
     *
     * @param reason The reason for the rejection
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    public RejectedPromise(@NotNull String reason) {
        
        this(new PromiseRejectionException(reason));
        
    }
    
    @Override
    @NotNull
    public Promise<T> catchException(@NotNull Callback<Throwable> reject) {
        
        reject.run(reason);
        return this;
        
    }
    
    @Override
    @NotNull
    public Promise<T> after(@NotNull Runnable runnable) {
        
        runnable.run();
        return this;
        
    }
    
    @Override
    @NotNull
    public State getState() { return State.REJECTED; }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Callback<? super T> fulfil, @NotNull Callback<Throwable> reject) { return this; }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Callback<? super T> fulfil) { return this; }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Runnable fulfil) { return this; }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Runnable fulfil, @NotNull Callback<Throwable> reject) { return this; }
    
    @Override
    public void await() { }
    
}
