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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TransformPromiseTest {

    Promise<Integer> transformPromise;
    
    @BeforeEach
    void setup() {
        
        transformPromise = new TransformPromise<>(Promise.resolve(4), (value) -> value + 4);
        
    }
    
    @Test
    void resolvesToTransformedValue() {
        
        transformPromise
                .then((value) -> assertEquals(value, 8));
        
        assertEquals(Promise.State.RESOLVED, transformPromise.getState());
        
    }
    
    @Test
    void rejects_WhenTransformFails() {
        
        Promise<Integer> transformPromise = new TransformPromise<>(Promise.resolve(4), (value) -> {
            throw new RuntimeException("fail");
        });
        
        transformPromise
                .then(() -> fail("Promise is rejected, cannot be in resolved state"))
                .catchException((reason) -> assertEquals("fail", reason.getMessage()));
        
        assertEquals(Promise.State.REJECTED, transformPromise.getState());
        
    }
    
    @Test
    void rejects_WhenFromRejects() {
        
        Promise<Integer> transformPromise = new TransformPromise<>(
                Promise.<Integer>reject("rejected"),
                (value) -> value
        );
        
        transformPromise
                .then(() -> fail("Promise is rejected, cannot be in resolved state"))
                .catchException((reason) -> assertEquals("rejected", reason.getMessage()));
        
        assertEquals(Promise.State.REJECTED, transformPromise.getState());
        
    }
    
}
