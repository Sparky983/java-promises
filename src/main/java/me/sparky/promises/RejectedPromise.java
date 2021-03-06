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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a rejected promise. This promise is more efficient than using
 * {@link SettleablePromise} and rejecting it.
 *
 * @param <T> The type of the promise
 * @author Sparky
 * @since 1.0
 * @see Promise#reject(Throwable)
 * @see Promise#reject(String)
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
    @Contract("_ -> this")
    public Promise<T> catchException(@NotNull Callback<@NotNull Throwable> reject) {
        
        try {
            reject.run(reason);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
        
    }
    
    @Override
    @NotNull
    @Contract("_ -> this")
    public Promise<T> after(@NotNull Runnable runnable) {
        
        runnable.run();
        return this;
        
    }
    
    @Override
    @NotNull
    public State getState() { return State.REJECTED; }
    
    @Override
    @NotNull
    @Contract("_, _ -> this")
    public Promise<T> then(@NotNull Callback<? super T> fulfil,
                           @NotNull Callback<@NotNull Throwable> reject) {
    
        return this;
    
    }
    
    @Override
    @NotNull
    @Contract("_ -> this")
    public Promise<T> then(@NotNull Callback<? super T> fulfil) { return this; }
    
    @Override
    @NotNull
    @Contract("_ -> this")
    public Promise<T> then(@NotNull Runnable fulfil) { return this; }
    
    @Override
    @NotNull
    @Contract("_, _ -> this")
    public Promise<T> then(@NotNull Runnable fulfil, @NotNull Callback<@NotNull Throwable> reject) {
        
        return this;
    
    }
    
    @Override
    @NonBlocking
    public void await() { }
    
}
