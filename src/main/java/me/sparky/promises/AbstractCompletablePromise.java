/*
 * Copyright 2021 Sparky
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

/**
 * Provides implementation for a {@link Promise} which can be resolved.
 *
 * Note: All methods have an access modifier of <code>protected</code>.
 *
 * @param <T> The type of the promise
 * @author Sparky
 * @since 1.0
 */
public abstract class AbstractCompletablePromise<T> extends AbstractPromise<T> {
    
    /**
     * Constructor for subclasses.
     *
     * @since 1.0
     */
    protected AbstractCompletablePromise() { }
    
    /**
     * Resolves the promise with specified value. Assuming the state of the promise is not pending
     * all the {@link Promise#then} callbacks will.
     *
     * @param result The result of the promise
     * @throws IllegalStateException if state is not {@link State#PENDING}
     * @since 1.0
     */
    protected synchronized void resolve(T result) {
        
        checkNotPending();
        
        this.result = result;
        state = State.RESOLVED;
        
        for (Callback<? super T> callback : fulfilCallbacks)
            callback.run(result);
        
        either();
        
        fulfilCallbacks.clear();
        
    }
    
    /**
     * Rejects the promise with specified reason.
     *
     * @param reason The reason for the rejection
     * @throws IllegalStateException if state is not {@link State#PENDING}
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    protected synchronized void reject(@NotNull Throwable reason) {
        
        checkNotPending();
        
        // Check, then run no check rejection is used to not repeat code
        rejectNoCheck(reason);
        
    }
    
    /**
     * Rejects the promise with specified reason. The exception is passed into
     * {@link Promise#catchException(Callback)} is {@link PromiseRejectionException}.
     *
     * @param reason The reason for the rejection
     * @throws IllegalStateException if state is not {@link State#PENDING}
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    protected synchronized void reject(@NotNull String reason) {
        
        reject(new PromiseRejectionException(reason));
        
    }
    
    /**
     * Runs {@link Promise#after(Runnable)} callbacks.
     *
     * @since 1.0
     */
    protected void either() {
        
        for (Runnable runnable : anyCallbacks)
            runnable.run();
        
        anyCallbacks.clear();
        
    }
    
    /**
     * Rejects the promise without checking the current state.
     *
     * @param reason The reason for the rejection
     * @throws IllegalArgumentException if reason is null
     * @since 1.0
     */
    protected synchronized void rejectNoCheck(@NotNull Throwable reason) {
        
        this.reason = reason;
        
        for (Callback<Throwable> callback : rejectCallbacks)
            try {
                callback.run(reason);
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        either();
        state = State.REJECTED;
        fulfilCallbacks.clear();
        
    }
    
    /**
     * Helper function to check if state is pending, if so throw {@link IllegalStateException}.
     *
     * @throws IllegalStateException if state is {@link State#PENDING}
     * @since 1.0
     */
    protected void checkNotPending() {
        
        if (state != State.PENDING) throw new IllegalStateException("Promise has already been completed");
        
    }
    
}
