package me.sparky.promises;

/**
 * This lambda is passed into {@link CompletablePromise#CompletablePromise(Executor)} to either
 * resolve or reject the promise. The argument is the promise itself.
 *
 * @param <T> The type of the promise
 */
@FunctionalInterface
public interface Executor<T> {
    
    /**
     * Called asynchronously from {@link CompletablePromise#CompletablePromise(Executor)}.
     *
     * @param promise The promise itself
     * @throws Exception if an error occurs during execution
     */
    void execute(CompletablePromise<T> promise) throws Exception;

}
