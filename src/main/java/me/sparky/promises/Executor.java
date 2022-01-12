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

/**
 * This lambda is passed into {@link CompletablePromise#CompletablePromise(Executor)} to either
 * resolve or reject the promise. The argument is the promise itself.
 *
 * @param <T> The type of the promise
 * @author Sparky
 * @since 1.0
 */
@FunctionalInterface
public interface Executor<T> {
    
    /**
     * Called asynchronously from {@link CompletablePromise#CompletablePromise(Executor)}.
     *
     * @param promise The promise itself
     * @throws Exception if an error occurs during execution
     * @since 1.0
     */
    void execute(CompletablePromise<T> promise) throws Exception;

}
