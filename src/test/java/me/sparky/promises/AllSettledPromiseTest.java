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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllSettledPromiseTest {

    CompletablePromise<String> promise1;
    CompletablePromise<String> promise2;
    CompletablePromise<String> promise3;
    Promise<List<Promise.State>> allSettledPromise;
    
    @BeforeEach
    void setup() {
        
        promise1 = new CompletablePromise<>();
        promise2 = new CompletablePromise<>();
        promise3 = new CompletablePromise<>();
        allSettledPromise = Promise.allSettled(promise1, promise2, promise3);
        
    }
    
    @Test
    void allSettledPromise_Resolves_WhenAllPromisesAreResolved() {
        
        assertEquals(Promise.State.PENDING, allSettledPromise.getState());
        
        promise1.resolve("1");
        promise2.resolve("2");
        promise3.resolve("3");
        
        assertEquals(Promise.State.RESOLVED, allSettledPromise.getState());
        
    }
    
    @Test
    void allSettledPromise_Resolves_WhenAllPromisesAreRejected() {
    
        assertEquals(Promise.State.PENDING, allSettledPromise.getState());
    
        promise1.reject("1");
        promise2.reject("2");
        promise3.reject("3");
    
        assertEquals(Promise.State.RESOLVED, allSettledPromise.getState());
        
    }
    
    @Test
    void allSettledPromise_Resolves_WhenAllPromisesAreMixtureOfResolvedAndRejected() {
    
        assertEquals(Promise.State.PENDING, allSettledPromise.getState());
    
        promise1.resolve("1");
        promise2.reject("2");
        promise3.reject("3");
    
        assertEquals(Promise.State.RESOLVED, allSettledPromise.getState());
        
    }
    
    @Test
    void allSettledPromise_ResolvesToListOfPromiseStates() {
        
        promise1.reject("1");
        promise2.reject("2");
        promise3.resolve("3");
    
        allSettledPromise
                .then((states) ->
                    assertEquals(Arrays.asList(
                            Promise.State.REJECTED,
                            Promise.State.REJECTED,
                            Promise.State.RESOLVED
                    ), states)
                );
        
    }
    
}
