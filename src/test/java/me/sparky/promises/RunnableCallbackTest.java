package me.sparky.promises;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunnableCallbackTest {
    
    @Test
    void testSameAsRunnable() {
    
        val strList = new ArrayList<>();
        val runnable = (Runnable) () -> strList.add("1");
        val runnableCallback = new RunnableCallback<>(runnable);
        runnable.run();
        runnableCallback.run(null);
        
        assertEquals(Arrays.asList("1", "1"), strList);
        
    }
    
}