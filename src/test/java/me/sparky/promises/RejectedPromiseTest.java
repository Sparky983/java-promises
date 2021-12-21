package me.sparky.promises;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RejectedPromiseTest {
    
    final Throwable REASON = new RuntimeException("Rejected");
    Promise<String> promise;
    
    @BeforeEach
    void setup() {
        
        promise = Promise.reject(REASON);
        
    }
    
    @Test
    void shouldNotRunThen_BecauseRejected() {
        
        val ran = new AtomicBoolean(false);
        
        promise
                .then((value) -> fail("Promise is rejected, shouldn't run"));
        
        promise
                .catchException((reason) -> ran.set(true));
        
        assertTrue(ran.get());
        
    }
    
}

