package me.sparky.promises;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a resolved, completed promise. This promise is more efficient than using
 * {@link CompletablePromise} and completing it.
 *
 * @param <T> The type of the resolved value of the promise
 * @author Sparky
 * @since 1.0
 */
public class ResolvedPromise<T> implements Promise<T> {
    
    private final List<Callback<Throwable>> rejectCallbacks = new ArrayList<>(3);
    private final T result;
    
    /**
     * Constructs a <code>ResolvedPromise</code> with specified result.
     *
     * @param result The result of the promise
     * @throws IllegalArgumentException if result is null
     * @since 1.0
     */
    public ResolvedPromise(@Nullable T result) {
        
        this.result = result;
        
    }
    
    @Override
    @NotNull
    public State getState() { return State.RESOLVED; }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Callback<? super T> fulfil, @NotNull Callback<Throwable> reject) {
        
        return then(fulfil)
                .catchException(reject);
    
    }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Callback<? super T> fulfil) {
        
        try {
            fulfil.run(result);
        } catch (Exception e) {
            reject(e);
        }
        return this;
    
    }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Runnable fulfil) {
        
//        return then(new RunnableCallback<>(fulfil));
        
        // More readable and clean but slower.
        
        try {
            fulfil.run();
        } catch (Exception e) {
            reject(e);
        }
        // Faster method
        
        return this;
        
    }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Runnable fulfil, @NotNull Callback<Throwable> reject) { return then(fulfil); }
    
    @Override
    @NotNull
    public Promise<T> catchException(@NotNull Callback<Throwable> reject) {
        
        rejectCallbacks.add(reject);
        return this;
    
    }
    
    @Override
    @NotNull
    public Promise<T> after(@NotNull Runnable runnable) {
        
        runnable.run();
        return this;
        
    }
    
    /**
     * Rejects the promise with specified reason.
     *
     * @param reason The reason for the rejection
     * @throws IllegalStateException if state is not {@link State#PENDING}
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    private void reject(@NotNull Throwable reason) {
        
        for (Callback<Throwable> callback : rejectCallbacks)
            callback.run(reason);
        
    }
    
    @Override
    public void await() { }
    
}
