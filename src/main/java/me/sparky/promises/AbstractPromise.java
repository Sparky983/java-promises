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

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides basic implementation of {@link Promise} which most implementations should extend.
 *
 * @param <T> The type of the resolved value of the promise
 * @author Sparky
 * @since 1.0
 */
public abstract class AbstractPromise<T> implements Promise<T> {
    
    /**
     * Current state of the promise, {@link State#PENDING} by default.
     *
     * @since 1.0
     */
    @Getter protected State state = State.PENDING;
    
    /**
     * The result of the promise, null of still pending or rejected.
     *
     * @since 1.0
     */
    @Nullable protected T result;
    
    /**
     * The rejection reason, null if resolved or pending.
     *
     * @since 1.0
     */
    @Nullable protected Throwable reason;
    
    /**
     * List of all fulfil callbacks.
     *
     * @since 1.0
     */
    @NotNull
    protected List<@NotNull Callback<? super T>> fulfilCallbacks = new ArrayList<>(3);
    
    /**
     *
     * List of all rejection callbacks.
     *
     * @since 1.0
     */
    @NotNull
    protected List<@NotNull Callback<@NotNull Throwable>> rejectCallbacks = new ArrayList<>(3);
    
    /**
     * List of all any callbacks.
     *
     * @since 1.0
     */
    @NotNull
    protected List<@NotNull Runnable> anyCallbacks =
            Collections.synchronizedList(new ArrayList<>(3));
    
    /**
     * Constructor for subclasses.
     *
     * @since 1.0
     */
    protected AbstractPromise() { }
    
    /**
     * Adds additional logic to running a callback that is defined by subclass. For
     * example one may want to reject the callback if an exception occurs or simply swallow it.
     *
     * @param fulfilCallback The callback to run
     * @throws IllegalArgumentException if fulfilCallback is null
     * @since 1.0
     */
    protected void runCallback(@NotNull Callback<? super T> fulfilCallback) {
        
        try {
            fulfilCallback.run(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Callback<? super T> fulfil,
                           @NotNull Callback<@NotNull Throwable> reject) {
        
        return then(fulfil)
                .catchException(reject);
        
    }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Callback<? super T> fulfil) {
        
        if (state == State.RESOLVED) runCallback(fulfil);
        else fulfilCallbacks.add(fulfil);
        
        return this;
        
    }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Runnable fulfil) {
        
        return then(new RunnableCallback<>(fulfil));
        
    }
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Runnable fulfil, @NotNull Callback<@NotNull Throwable> reject) {
        
        return then(new RunnableCallback<>(fulfil), reject);
        
    }
    
    @Override
    @NotNull
    public Promise<T> catchException(@NotNull Callback<@NotNull Throwable> reject) {
    
        if (state == State.REJECTED) {
            
            if (reason == null)
                throw new AssertionError("Reason must not be null when state is REJECTED");
            
            try {
                reject.run(reason);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        rejectCallbacks.add(reject);
        
        return this;
        
    }
    
    @Override
    @NotNull
    public Promise<T> after(@NotNull Runnable runnable) {
        
        if (state != State.PENDING) runnable.run();
        else anyCallbacks.add(runnable);
        
        return this;
        
    }
    
    @Override
    public void await() {
        
        while (state == State.PENDING)
            Thread.onSpinWait();
        
    }
    
}
