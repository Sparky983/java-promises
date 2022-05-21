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

import java.util.Collection;

/**
 * A void promise that resolves when all input promises are resolved and
 * rejected if any input promises are rejected.
 *
 * @since 1.1
 * @author Sparky
 * @see Promise#whenAll(Collection)
 * @see Promise#whenAll(Promise[])
 */
public class WhenAllPromise extends AbstractCompletablePromise<Void> {
    
    private final int total;
    private int completed = 0;
    
    /**
     * Constructs a new <code>WhenAllPromise</code>
     *
     * @param promises The input promises
     * @throws IllegalArgumentException if promises is null
     * @since 1.1
     */
    public WhenAllPromise(@NotNull Collection<@NotNull Promise<?>> promises) {
        
        this.total = promises.size();
        
        incrementAndCheckCompletion();
        
        for (Promise<?> promise1 : promises)
            promise1
                    .then(this::incrementAndCheckCompletion)
                    .catchException(this::rejectIfPending);
        
    }
    
    private void incrementAndCheckCompletion() {
        
        if (state == State.REJECTED) return;
        synchronized (this) {
            if (++completed > total) resolve(null);
        }
        
    }
    
    private void rejectIfPending(@NotNull Throwable reason) {
        
        if (state == State.PENDING) reject(reason);
        
    }
    
}
