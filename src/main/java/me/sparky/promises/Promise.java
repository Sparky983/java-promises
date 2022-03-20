/*
 * Copyright 2022 Sparky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.sparky.promises;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Represents the completion of a value that has not yet been retrieved.
 *
 * @author Sparky
 * @since 1.0
 * @see AllPromise
 * @see AllSettledPromise
 * @see AnyPromise
 * @see RejectedPromise
 * @see ResolvedPromise
 * @see SettleablePromise
 * @see TransformPromise
 * @see WhenAllPromise
 */
public interface Promise<T> {
    
    /**
     * Creates a new resolved promise.
     *
     * @param result The result of the resolved promise
     * @param <T> The type of the resolved promise
     * @return The newly created promise
     * @since 1.0
     */
    @NotNull
    static <T> Promise<T> resolve(@Nullable T result) { return new ResolvedPromise<>(result); }
    
    /**
     * Creates a new void resolved promise.
     *
     * @return The newly created promise
     * @since 1.4.1
     */
    @NotNull
    static Promise<Void> resolve() { return new ResolvedPromise<>(null); }
    
    /**
     * Creates a new rejected promise.
     *
     * @param reason The reason for the rejection
     * @param <T> The type of the rejected promise
     * @return The newly created promise
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    @NotNull
    static <T> Promise<T> reject(@NotNull Throwable reason) { return new RejectedPromise<>(reason); }
    
    /**
     * Creates a new rejected promise.
     *
     * @param reason The reason for the rejection
     * @param <T> The type of the resolved promise
     * @return The newly created promise
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    @NotNull
    static <T> Promise<T> reject(@NotNull String reason) {
        
        return new RejectedPromise<>(reason);
    
    }
    
    /**
     * Creates a new <code>AllPromise</code>. This promise will be resolved when all the promise inputs are
     * resolved. It will be rejected if any of them are rejected.
     *
     * @param promises The input promises
     * @param <T> The type of the input promises
     * @return The newly created promise
     * @throws IllegalArgumentException if promises is null
     * @since 1.0
     */
    @NotNull
    static <T> Promise<List<? super T>> all(@NotNull List<@NotNull Promise<T>> promises) {
        
        return new AllPromise<>(promises);
    
    }
    
    /**
     * Creates a new <code>AllPromise</code>. This promise will be resolved when all the promise inputs are
     * resolved. It will be rejected if any of them are rejected.
     *
     * @param promises The input promises
     * @param <T> The type of the input promises
     * @return The newly created promise
     * @throws IllegalArgumentException if promises is null
     * @since 1.0
     */
    @SafeVarargs
    @NotNull
    static <T> Promise<List<? super T>> all(@NotNull Promise<T>... promises) {
        
        return all(Arrays.asList(promises));
    
    }
    
    /**
     * Creates a new <code>AnyPromise</code>. This promise will be resolved when any of the promise
     * inputs are resolved and will not be rejected if input promises are rejected.
     *
     * @param promises The input promises
     * @param <T> The type of the promise
     * @return The newly created promise
     * @throws IllegalArgumentException if promises is null
     * @since 1.0
     */
    @NotNull
    static <T> Promise<T> any(@NotNull List<@NotNull Promise<T>> promises) {
        
        return new AnyPromise<>(promises);
    
    }
    
    /**
     * Creates a new <code>AnyPromise</code>. This promise will be resolved when any of the promise
     * inputs are resolved and will not be rejected if input promises are rejected.
     *
     * @param promises The input promises
     * @param <T> The type of the promise
     * @return The newly created promise
     * @throws IllegalArgumentException if promises is null
     * @since 1.0
     */
    @SafeVarargs
    @NotNull
    static <T> Promise<T> any(@NotNull Promise<T>... promises) {
        
        return any(Arrays.asList(promises));
    
    }
    
    /**
     * Creates a new <code>WhenAllPromise</code>. This promise will be resolved when all input
     * promises are resolved and will be rejected if any of its inputs are resolved.
     *
     * @param promises The input promises
     * @return The newly created promise
     * @throws IllegalArgumentException if promises is null
     * @since 1.1
     */
    @NotNull
    static Promise<Void> whenAll(@NotNull Collection<@NotNull Promise<?>> promises) {
        
        return new WhenAllPromise(promises);
    
    }
    
    /**
     * Creates a new <code>WhenAllPromise</code>. This promise will be resolved when all input
     * promises are resolved and will be rejected if any of its inputs are resolved.
     *
     * @param promises The input promises
     * @return The newly created promise
     * @throws IllegalArgumentException if promises is null
     * @since 1.1
     */
    @NotNull
    static Promise<Void> whenAll(@NotNull Promise<?>... promises) {
        
        return whenAll(Arrays.asList(promises));
    
    }
    
    /**
     * Creates a new <code>AllSettledPromise</code>. This promise is resolved when all the inputs
     * settle (complete).
     *
     * @param promises The input promises
     * @return The newly created promise
     * @throws IllegalArgumentException if promises is null
     * @since 1.2
     */
    @NotNull
    static Promise<List<State>> allSettled(@NotNull Collection<@NotNull Promise<?>> promises) {
        
        return new AllSettledPromise(promises);
    
    }
    
    /**
     * Creates a new <code>AllSettledPromise</code>. This promise is resolved when all the inputs
     * settle (complete).
     *
     * @param promises The input promises
     * @return The newly created promise
     * @throws IllegalArgumentException if promises is null
     * @since 1.2
     */
    @NotNull
    static Promise<List<State>> allSettled(@NotNull Promise<?>... promises) {
        
        return allSettled(Arrays.asList(promises));
    
    }
    
    /**
     * Represents the state of a promise.
     *
     * @author Sparky
     * @since 1.0
     */
    enum State {
        
        /**
         * Represents the state of a promise whose value has not been retrieved yet.
         *
         * @since 1.0
         */
        PENDING,
        
        /**
         * Represents the state of a promise that has been resolved.
         *
         * @since 1.0
         */
        RESOLVED,
        
        /**
         * Represents the state of a promise that was rejected.
         *
         * @since 1.0
         */
        REJECTED

    }
    
    /**
     * Adds a callback to be called on fulfil and on rejection.
     *
     * @param fulfil The fulfil-callback
     * @param reject The reject-callback
     * @return The promise instance (for chaining)
     * @throws IllegalArgumentException if fulfil or reject are null
     * @since 1.0
     */
    @NotNull
    Promise<T> then(@NotNull Callback<? super T> fulfil,
                    @NotNull Callback<@NotNull Throwable> reject);
    
    /**
     * Adds a callback to be called on fulfil.
     *
     * @param fulfil The fulfil-callback
     * @return The promise instance (for chaining)
     * @throws IllegalArgumentException if fulfil is null
     * @since 1.0
     */
    @NotNull
    Promise<T> then(@NotNull Callback<? super T> fulfil);
    
    /**
     * Adds a callback to be called on fulfil.
     *
     * @param fulfil The fulfil-callback
     * @return The promise instance (for chaining)
     * @throws IllegalArgumentException if fulfil is null
     * @since 1.0
     */
    @NotNull
    Promise<T> then(@NotNull Runnable fulfil);
    
    /**
     * Adds a callback to be called on fulfil and on rejection.
     *
     * @param fulfil The fulfil-callback
     * @param reject The reject-callback
     * @return The promise instance (for chaining)
     * @throws IllegalArgumentException if fulfil or reject are null
     * @since 1.0
     */
    @NotNull
    Promise<T> then(@NotNull Runnable fulfil, @NotNull Callback<@NotNull Throwable> reject);
    
    /**
     * Adds a transform function to be applied on fulfil that returns a new value from the input
     * value.
     *
     * Returns a new promise that resolves to the return of the transform function when the current
     * promise is resolved, and is rejected when the current promise is rejected or if the transform
     * function fails.
     *
     * @param transform The transform function
     * @param <R> The type of the transformed value
     * @return The transform promise
     * @throws IllegalArgumentException if transform is null
     * @since 1.3
     */
    @NotNull
    default <R> Promise<R> transform(@NotNull Function<T, R> transform) {
        
        return new TransformPromise<>(this, transform);
        
    }
    
    /**
     * Adds a callback to be called when the promise is rejected.
     *
     * @param reject The reject callback
     * @return The promise instance (for chaining)
     * @throws IllegalArgumentException if reject is null
     * @since 1.0
     */
    @NotNull
    Promise<T> catchException(@NotNull Callback<@NotNull Throwable> reject);
    
    /**
     * Adds a callback to be called when promise is either completed (resolved or rejected).
     * In most languages this method would be called <code>finally</code>.
     *
     * @param runnable The run callback
     * @return The promise instance (for chaining)
     * @throws IllegalArgumentException if runnable is null
     * @since 1.0
     */
    @NotNull
    Promise<T> after(@NotNull Runnable runnable);
    
    /**
     * Gets the current state of the promise.
     *
     * @return The current state of the promise
     * @since 1.0
     */
    @NotNull
    State getState();
    
    /**
     * Waits until the promise is completed (resolved or rejected).
     *
     * @since 1.0
     */
    void await();
    
}
