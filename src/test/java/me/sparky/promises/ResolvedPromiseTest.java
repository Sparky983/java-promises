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
