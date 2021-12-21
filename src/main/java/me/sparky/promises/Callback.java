package me.sparky.promises;

/**
 * Represents a lambda that can be called back later.
 *
 * @param <T> The type of the callback's argument
 * @author Sparky
 * @since 1.0
 */
@FunctionalInterface
public interface Callback<T> {
    
    /**
     * This is the method to call back.
     *
     * @param result The result
     */
    void run(T result);
    
}
