package me.sparky.promises;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a callback that can be created from a runnable. You should use this in situations
 * where you have a {@link Runnable} lambda, and want to use it as a callback.
 *
 * @param <T> The type of the callback
 * @author Sparky
 * @since 1.0
 */
public class RunnableCallback<T> implements Callback<T> {
    
    private final Runnable runnable;
    
    /**
     * Constructs a new <code>RunnableCallback</code> from specified runnable.
     *
     * @param runnable The runnable
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    public RunnableCallback(@NotNull Runnable runnable) {
        
        this.runnable = runnable;
        
    }
    
    @Override
    public final void run(T ignored) { runnable.run(); }
    
}
