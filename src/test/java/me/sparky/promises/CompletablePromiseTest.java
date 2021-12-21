package me.sparky.promises;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class CompletablePromiseTest {
    
    @Test
    void catchExceptionRunsWithCorrectReason_IfExceptionRaisedInThen() {
    
        val promise = new CompletablePromise<String>();
        val reason = "reason";
        val ran = new AtomicBoolean(false);
        
        promise.resolve("");
        promise
                .then(() -> { throw new RuntimeException(reason); })
                .catchException(throwable -> {
                    assertEquals(reason, throwable.getMessage());
                    ran.set(true);
                });
        
        assertTrue(ran.get());
        
    }
    
    @SneakyThrows
    @Test
    void executorRunsAsyncAndResolves() {
        
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
    void isResolved_AfterAwaited() {
        
        val promise = new CompletablePromise<String>((completablePromise) -> {
            Thread.sleep(1000);
            completablePromise.resolve("resolved");
        });
        
        promise.await();
        
        assertNotEquals(Promise.State.PENDING, promise.getState());
        
    }
    
}
