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


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResolvedPromiseTest {

    final static String RESOLVED_VALUE = "Resolved value";
    
    Promise<String> promise;
    
    @BeforeEach
    void setup() {
    
        promise = Promise.resolve(RESOLVED_VALUE);
        
    }
    
    @Test
    void shouldRun_Then_Immediately_IfResolved() {
    
        val ran = new AtomicBoolean(false);
        
        promise.then(value -> {
            assertEquals(RESOLVED_VALUE, value);
            ran.set(true);
        });
        
        assertTrue(ran.get());
    
    }
    
}
