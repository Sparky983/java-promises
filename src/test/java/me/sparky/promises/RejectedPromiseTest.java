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

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class RejectedPromiseTest {
    
    Promise<String> promise;
    
    @BeforeEach
    void setup() {
        
        promise = Promise.reject("reason");
        
    }
    
    @Test
    void then_DoesNotRun() {
        
        promise
                .then(() -> fail("Promise is rejected, cannot run then"));
        
    }
    
    @Test
    void catchException_Runs() {
        
        val ran = new AtomicBoolean(false);
        
        promise
                .catchException((reason) -> ran.set(true));
        
        assertTrue(ran.get());
        
    }
    
}
