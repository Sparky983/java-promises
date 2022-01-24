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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A promise that is resolved with all the input promises are resolved. The promise is resolves to a list  of each resolved value of each promise in the order
 * they were inputted when all of its inputs promises are resolved and rejected if
 * any of its inputs are rejected.
 *
 * @param <T> The type of the promises
 * @author Sparky
 * @since 1.0
 */
public class AllPromise<T> extends AbstractCompletablePromise<List<? super T>> {

    private final List<T> results;
    private int amountOfResolvedPromises;
    
    /**
     * Constructs an <code>AllPromise</code> with specified promises.
     *
     * @param promises The promises
     * @throws IllegalArgumentException if promises is null
     * @since 1.0
     */
    public AllPromise(@NotNull List<Promise<T>> promises) {
        
        checkCollectionForNull(promises);
        
        this.amountOfResolvedPromises = promises.size();
        this.results = new ArrayList<>(Collections.nCopies(promises.size(), null));
        
        for (int i = 0; i < promises.size(); i++) {
            int finalI = i;
            promises.get(i)
                    .then(value -> {
                        if (state != State.PENDING) return;
                        results.set(finalI, value);
                        if (--amountOfResolvedPromises == 0) resolve(results);
                    })
                    .catchException((reason) -> {
                        if (state == State.PENDING)
                            reject(reason);
                    });
        }
            
    }
    
    @Override
    protected void runCallback(@NotNull Callback<? super List<? super T>> fulfilCallback) {
        
        try {
            fulfilCallback.run(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Checks if any elements inside the input list are null and if so throws
     * {@link IllegalArgumentException}.
     *
     * @param list The list to check
     * @throws IllegalArgumentException if list is null or any of the input list's elements are null
     * @since 1.0
     */
    private void checkCollectionForNull(@NotNull Collection<?> list) {
        
        for (Object o : list) {
            
            if (o == null)
                throw new IllegalArgumentException("Collection has null value inside of it");
        
        }
        
    }
    
}
