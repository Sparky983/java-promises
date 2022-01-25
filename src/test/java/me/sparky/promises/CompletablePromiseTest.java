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

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompletablePromiseTest {
    
    @SneakyThrows
    @Test
    void executor_RunsAsync() {
        
        val ran = new AtomicBoolean(false);
        
        val promise = new CompletablePromise<String>(resolvablePromise -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                resolvablePromise.resolve("Resolved");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        promise
                .then(() -> ran.set(true));
        
        assertFalse(ran.get());
        
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        // Two seconds should guarantee that promise has completed
        
        assertEquals(Promise.State.RESOLVED, promise.getState());
        assertTrue(ran.get());
        
    }
    
    @Test
    void await_WaitsUntilCompletion() {
        
        val promise = new CompletablePromise<String>((completablePromise) -> {
            Thread.sleep(1000);
            completablePromise.resolve("resolved");
        });
        
        promise.await();
        
        assertNotEquals(Promise.State.PENDING, promise.getState());
        
    }
    
}
