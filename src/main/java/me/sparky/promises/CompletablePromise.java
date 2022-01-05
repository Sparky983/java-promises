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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Represents a promise that can be completed (resolved or rejected).
 *
 * @param <T> The type of the resolved value of the promise
 * @author Sparky
 * @since 1.0
 */
public class CompletablePromise<T> extends AbstractCompletablePromise<T> {
    
    /**
     * The thread pool that is used to execute {@link CompletablePromise#CompletablePromise(me.sparky.promises.Executor)}.
     *
     * @since 1.0
     */
    public static final Executor threadPool = Executors.newFixedThreadPool(3);
    
    /**
     * Constructs a new <code>CompletablePromise</code>
     *
     * @since 1.0
     */
    public CompletablePromise() { }
    
    /**
     * Constructs a new <code>CompletablePromise</code>
     *
     * @param executor A callback which takes in 1 argument the promise and reject or resolve the promise.
     *
     *                 Note: This operation is as asynchronous.
     * @since 1.0
     */
    public CompletablePromise(me.sparky.promises.Executor<T> executor) {
    
        threadPool.execute(() -> {
            
            try {
                executor.execute(this);
            } catch (Exception e) {
                reject(e);
            }
    
        });
        
    }
    
    @Override
    protected void runCallback(@NotNull Callback<? super T> fulfilCallback) {
        
        try {
            fulfilCallback.run(result);
        } catch (Exception e) {
            rejectNoCheck(e);
        }
        
    }
    
    @Override
    public synchronized void resolve(T result) { super.resolve(result); }
    
    @Override
    public synchronized void reject(@NotNull Throwable reason) { super.reject(reason); }
    
    @Override
    public synchronized void reject(@NotNull String reason) { super.reject(reason); }
    
}
