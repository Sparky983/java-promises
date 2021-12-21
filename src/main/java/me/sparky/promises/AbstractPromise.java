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

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
    @Getter protected volatile State state = State.PENDING;
    
    /**
     * The result of the promise, null of still pending or rejected.
     *
     * @since 1.0
     */
    @Nullable protected volatile T result;
    
    /**
     * The rejection reason, null if resolved or pending.
     *
     * @since 1.0
     */
    @Nullable protected volatile Throwable reason;
    
    /**
     * List of all fulfil callbacks.
     *
     * @since 1.0
     */
    @NotNull
    protected List<Callback<? super T>> fulfilCallbacks = new ArrayList<>(3);
    
    /**
     *
     * List of all rejection callbacks.
     *
     * @since 1.0
     */
    @NotNull
    protected List<Callback<Throwable>> rejectCallbacks = new ArrayList<>(3);
    
    /**
     * List of all any callbacks.
     *
     * @since 1.0
     */
    @NotNull
    protected List<Runnable> anyCallbacks = new ArrayList<>(3);
    
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
    protected abstract void runCallback(@NotNull Callback<? super T> fulfilCallback);
    
    @Override
    @NotNull
    public Promise<T> then(@NotNull Callback<? super T> fulfil, @NotNull Callback<Throwable> reject) {
        
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
    public Promise<T> then(@NotNull Runnable fulfil, @NotNull Callback<Throwable> reject) {
        
        return then(new RunnableCallback<>(fulfil), reject);
        
    }
    
    @Override
    @NotNull
    public Promise<T> catchException(@NotNull Callback<Throwable> reject) {
        
        if (state == State.REJECTED) reject.run(reason);
        else rejectCallbacks.add(reject);
        
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
