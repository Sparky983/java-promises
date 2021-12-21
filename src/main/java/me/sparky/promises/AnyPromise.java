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

import java.util.List;

/**
 * Represents a promise that resolves when any of its input promises are completed. This promise
 * will not be rejected if any of it's input promises are rejected.
 *
 * @param <T> The type of the promises
 * @author Sparky
 * @since 1.0
 */
public class AnyPromise<T> extends AbstractCompletablePromise<T> {
    
    /**
     * Constructs a new <code>AllPromise</code> with specified promises.
     *
     * @param promises The promises
     * @throws IllegalArgumentException if promises is null
     * @since 1.0
     */
    public AnyPromise(@NotNull List<Promise<T>> promises) {
    
        for (Promise<T> promise : promises)
            promise.then(value -> {
                if (state != State.RESOLVED) resolve(value);
            });
    
    }
    
    @Override
    protected void runCallback(@NotNull Callback<? super T> fulfilCallback) {
        
        try {
            fulfilCallback.run(result);
        } catch (Exception e) {
            reject(e);
        }
        
    }
    
}
