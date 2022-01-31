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
 * A promise that resolves to a list of each input promise's state when they are all settled
 * (completed).
 *
 * @author Sparky
 * @since 1.2
 */
public class AllSettledPromise extends AbstractCompletablePromise<List<Promise.State>> {
    
    private final List<State> outcomes;
    private int i = 0;
    
    /**
     * Constructs an <code>AllSettledPromise</code> with specified promises.
     *
     * @param promises The promises
     * @throws IllegalArgumentException if promises is null
     * @since 1.2
     */
    public AllSettledPromise(Collection<Promise<?>> promises) {
        
        this.outcomes = new ArrayList<>(Collections.nCopies(promises.size(), null));
        
        int i = 0;
        
        for (Promise<?> promise : promises) {
    
            int finalI = i;
            promise
                    .then(() -> settle(finalI, State.RESOLVED))
                    .catchException((unused) -> settle(finalI, State.REJECTED));
            i++;
            
        }
        
    }
    
    @Override
    protected void runCallback(@NotNull Callback<? super List<State>> fulfilCallback) {
        
        try {
            fulfilCallback.run(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
    private void settle(int i, State state) {
        
        outcomes.set(i, state);
        this.i++;
        
        if (this.i >= outcomes.size())
            resolve(outcomes);
            
    }
    
}
