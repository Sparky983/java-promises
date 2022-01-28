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

import java.util.function.Function;

/**
 * A promise that resolves to the return of the transform function when the current promise is
 * resolved, and is rejected when the current promise is rejected or if the transform function
 * fails.
 *
 * @param <T> The return of the transform function
 * @author Sparky
 * @since 1.3
 */
public class TransformPromise<T> extends AbstractCompletablePromise<T> {
    
    /**
     * Constructs a new <code>TransformPromise</code>
     *
     * @param from The promise being transformed from
     * @param transform The transform function
     * @param <F> The type of the promise being transformed
     * @throws IllegalArgumentException if from or transform are null
     * @since 1.3
     */
    public <F> TransformPromise(@NotNull Promise<F> from, @NotNull Function<F, T> transform) {
        
        from
                .then((value) -> {
                    try {
                        resolve(transform.apply(value));
                    } catch (Exception e) {
                        reject(e);
                    }
                })
                .catchException(this::reject);
        
    }
    
    @Override
    protected void runCallback(@NotNull Callback<? super T> fulfilCallback) {
    
        try {
            fulfilCallback.run(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
}
