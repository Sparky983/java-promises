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

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

class AllPromiseTest {
    
    Promise<List<? super String>> allPromise;
    SettleablePromise<String> completablePromise;
    
    @BeforeEach
    void setup() {
        
        completablePromise = new SettleablePromise<>();
        allPromise = Promise.all(Promise.resolve("1"), completablePromise, Promise.resolve("3"));
        
    }
    
    @Test
    void resolvedValuesInOrderOfInputs_WhenResolved() {
        
        completablePromise.resolve("2");
        // All promises should be resolved now
        
        val values = new AtomicReference<>();
        
        allPromise
                .then(values::set)
                .catchException((reason) ->
                    fail("Promise was incorrectly rejected for reason " + reason)
                );
        
        assertNotEquals(null, values.get(),
                        "All input promises have been resolved, allPromise.then() should've ran");
        assertEquals(Arrays.asList("1", "2", "3"), values.get());
        
    }
    
    @Test
    void resolveOnInput_ResolvesAllPromise() {
        
        completablePromise.resolve("resolve");
        
        assertEquals(Promise.State.RESOLVED, allPromise.getState());
        
    }
    
    @Test
    void rejectPromise_WhenOneOfThePromisesAreRejected() {
        
        completablePromise.reject("reason");
        
        val reason = new AtomicReference<>();
        
        allPromise
                .then(() -> fail("Not possible, input promise was rejected"))
                .catchException((throwable) -> reason.set(throwable.getMessage()));
        
        assertEquals(Promise.State.REJECTED, allPromise.getState());
        assertEquals("reason", reason.get());
        
    }
    
}
