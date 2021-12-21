package me.sparky.promises;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class AnyPromiseTest {
    
    CompletablePromise<String> completablePromise;
    
    @BeforeEach
    void setup() {
        
        completablePromise = new CompletablePromise<>();
        
    }
    
    @Test
    void runThen_IfOneIsCompleted() {
    
        AtomicInteger thenCount = new AtomicInteger(0);
        Promise<String> anyPromise = Promise.any(Arrays.asList(completablePromise, Promise.resolve("resolved promise")));
        anyPromise
                .then(thenCount::incrementAndGet);
        
        assertEquals(1, thenCount.get());
        
    }
    
    @Test
    void dontRun_IfNotCompleted() {
    
        AtomicInteger thenCount = new AtomicInteger(0);
        Promise<String> anyPromise = Promise.any(Arrays.asList(completablePromise, Promise.reject(new RuntimeException("rejected"))));
        anyPromise
                .then(thenCount::incrementAndGet);
    
        assertEquals(0, thenCount.get());
        completablePromise.resolve("Resolved");
        assertEquals(1, thenCount.get());
        
    }
    
    @Test
    void dontRunCatch_Ever() {
        
        Promise<String> anyPromise = Promise.any(Collections.singletonList(Promise.reject("Rejected")));
        
        anyPromise
                .catchException((reason) -> fail("AnyPromise cannot be rejected"));
        
        assertNotEquals(Promise.State.REJECTED, anyPromise.getState());
        
    }
    
}
