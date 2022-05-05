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

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

class AnyPromiseTest {
    
    SettleablePromise<String> completablePromise;
    
    @BeforeEach
    void setUp() {
        
        completablePromise = new SettleablePromise<>();
        
    }
    
    @Test
    void then_Runs_WhenAnyPromiseResolves() {
    
        val thenCount = new AtomicInteger(0);
        val anyPromise = Promise.any(completablePromise, Promise.resolve("resolved promise"));
        
        anyPromise
                .then(thenCount::incrementAndGet);
        
        assertEquals(1, thenCount.get());
        
    }
    
    @Test
    void catchException_DoesNotRun_WhenInputIsRejected() {
        
        val anyPromise = Promise.any(Collections.singletonList(Promise.reject("Rejected")));
        
        anyPromise
                .catchException((reason) -> fail("AnyPromise cannot be rejected"));
        
        assertNotEquals(Promise.State.REJECTED, anyPromise.getState());
        
    }
    
}
