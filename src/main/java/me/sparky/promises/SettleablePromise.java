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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/**
 * Represents a promise that can be completed (resolved or rejected).
 *
 * @param <T> The type of the resolved value of the promise
 * @author Sparky
 * @since 1.0
 */
public class SettleablePromise<T> extends AbstractCompletablePromise<T> {
    
    /**
     * The default thread pool that is used to execute.
     *
     * @since 1.0
     */
    public static final ExecutorService threadPool = ForkJoinPool.commonPool();
    
    /**
     * Constructs a new <code>SettleablePromise</code>
     *
     * @since 1.0
     */
    public SettleablePromise() { }
    
    /**
     * Constructs a new <code>SettleablePromise</code>
     *
     * @param executor A callback which takes in 1 argument the promise and
     *                 reject or resolve the promise.
     *
     *                 Note: This operation is as asynchronous.
     * @throws IllegalArgumentException if executor is null
     * @since 1.0
     * @see SettleablePromise#threadPool
     */
    public SettleablePromise(@NotNull me.sparky.promises.Executor<T> executor) {
        
        this(executor, threadPool);
        
    }
    
    /**
     * Constructs a new <code>SettleablePromise</code>
     *
     * @param executor A callback which takes in 1 argument the promise and
     *                 reject or resolve the promise.
     *
     *                 Note: This operation is as asynchronous.
     * @param threadPool The executor that executes
     * @throws IllegalArgumentException if executor or threadPool is null
     * @since 1.0
     */
    public SettleablePromise(@NotNull me.sparky.promises.Executor<@NotNull T> executor, @NotNull Executor threadPool) {
        
        threadPool.execute(() -> {
            
            try {
                executor.execute(this);
            } catch (Exception e) {
                reject(e);
            }
    
        });
        
    }
    
    @Override
    public void resolve(T result) { super.resolve(result); }
    
    @Override
    public void reject(@NotNull Throwable reason) { super.reject(reason); }
    
    @Override
    public void reject(@NotNull String reason) { super.reject(reason); }
    
}
