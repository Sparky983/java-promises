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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WhenAllPromiseTest {
    
    SettleablePromise<Void> promise1;
    SettleablePromise<Void> promise2;
    Promise<Void> whenAllCompletedPromise;
    
    @BeforeEach
    void setUp() {
        
        promise1 = new SettleablePromise<>();
        promise2 = new SettleablePromise<>();
        whenAllCompletedPromise = Promise.whenAll(promise1, promise2);
        
    }
    
    @Test
    void onlyThen_Runs_WhenAllPromisesAreResolved() {
    
        val resolved = new AtomicBoolean(false);
        whenAllCompletedPromise
                .then(() -> resolved.set(true))
                .catchException(Assertions::fail);
        
        assertFalse(resolved.get());
        assertEquals(Promise.State.PENDING, whenAllCompletedPromise.getState());
    
        promise1.resolve(null);
        
        assertFalse(resolved.get());
        assertEquals(Promise.State.PENDING, whenAllCompletedPromise.getState());
        
        promise2.resolve(null);
        
        assertTrue(resolved.get());
        assertEquals(Promise.State.RESOLVED, whenAllCompletedPromise.getState());
        
    }
    
    @Test
    void rejects_WhenAtLeast1PromiseIsRejected() {
        
        assertEquals(Promise.State.PENDING, whenAllCompletedPromise.getState());
        
        promise1.reject("Rejected");
        
        assertEquals(Promise.State.REJECTED, whenAllCompletedPromise.getState());
        
    }
    
}
