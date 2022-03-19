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

import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown if promise is rejected with <code>String</code> for reason.
 *
 * @author Sparky
 * @since 1.0
 */
public class PromiseRejectionException extends RuntimeException {
    
    /**
     * Constructs a new <code>PromiseRejectionException</code>
     *
     * @since 1.0
     */
    public PromiseRejectionException() { super(); }
    
    /**
     * Constructs a new <code>PromiseRejectionException</code> with specified detail message.
     *
     * @param message The detail message
     * @since 1.0
     */
    public PromiseRejectionException(@Nullable String message) { super(message); }
    
    /**
     * Constructs a new <code>PromiseRejectionException</code> with specified detail message and
     * cause.
     *
     * @param message The detail message
     * @param cause The cause of this exception
     * @since 1.0
     */
    public PromiseRejectionException(@Nullable String message, @Nullable Throwable cause) {
        
        super(message, cause);
    
    }
    
    /**
     * Constructs a new <code>PromiseRejectionException</code> with specified cause.
     *
     * @param cause The cause of this exception
     * @since 1.0
     */
    public PromiseRejectionException(@Nullable Throwable cause) { super(cause); }
    
    /**
     * Constructs a new <code>PromiseRejectionException</code> with specified detail message, cause,
     * suppression enabled or disabled, and writeable stack trace enabled or disabled.
     *
     * @param message The detail message
     * @param cause The cause of this exception
     * @param enableSuppression whether suppression should be enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable or not
     * @since 1.0
     */
    public PromiseRejectionException(@Nullable String message,
                                     @Nullable Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        
        super(message, cause, enableSuppression, writableStackTrace);
    
    }
    
}
