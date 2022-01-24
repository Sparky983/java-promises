# Java Promises 

Java Promises is a Java promise library aimed to have syntax which is very similar to JavaScript. 

## Table of Contents

[1. ](#installation) Installation \
[2. ](#what-is-a-promise) What is a promise? \
[3. ](#syntax) Getting Started \
[4. ](#built-in-promises) Built in promises

## Installation

JDK 11 or higher and Maven is required to build Java Promises. 

To install run the following command:

```mvn clean install```

Then add the following to your project

Maven 
```xml
<dependency>
    <groupId>me.sparky</groupId>
    <artifactId>java-promises</artifactId>
    <version>1.1</version>
</dependency>
```

Gradle
```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation("me.sparky:promises:1.0")
}
```

## What Is A Promise?

A promise is an object that represents an asynchronous operation. A promise can be in one of the 
following states. 

<li>Pending: Operation hasn't been completed yet</li>
<li>Resolved: Operation completed successfully</li>
<li>Rejected: Operation failed</li>

If the operation completes successfully the promise call all callbacks added using the
Promise#then() and Promise#after() methods. If it fails all callbacks added using the
Promise#catchException() and Promise#after() methods. If a callback is added to an 
already completed callback, the callback will be ran straight away or not at all depending on if it
was resolved or rejected. 

## Syntax

In this section you will learn how to resolve, and respond to the promises. 
To create a promise, start by creating a new `CompletablePromise`. 

```java
Promise<String> promise = new CompletablePromise<>();
```

To resolve this promise add an executor to the constructor parameters. An executor asynchronous and
somewhere in its body it should resolve or reject the promise. 

```java
Executor<String> fetchData = promise -> {
    Thread.sleep(1000);
    // sleep represents some blocking function.

    promise.resolve("some data");
};

Promise<String> promise = new CompletablePromise<>(fetchData);
```

We have now created a promise, but it doesn't actually do anything yet because we haven't responded
to the completion of the promise, we are just ignoring it. We also want to make sure our data is 
valid, for this example our data will be prefixed by its length. If it is not valid we will reject 
the promise by throwing an exception. We will then catch the exception and print the exception 
to the console. 

```java
Executor<String> fetchData = promise -> {
    Thread.sleep(1000);
    // sleep represents some blocking function.
    
    promise.resolve("some data");
    // Throws an exception
    
    promise.resolve("1HelloWorld");
    // Throws an exception
    
    promise.resolve("4Hello");
    // Works fine, no exceptions

};

Promise<String> promise = new CompletablePromise<>(fetchData);

promise
    .then(data -> {
        if (!Character.isDigit(data.charAt(0))) 
            throw new RuntimeException("data must be prefixed by its length");
        
        int length = Integer.parseInt(data.substring(0, 1));
        
        if (length != data.length() - 1) 
            throw new RuntimeException("data is prefixed by incorrect length");
        System.out.println("Data received " + data);
    })
    .catchException(reason -> System.out.println(reason.getMessage()))
    .after(() -> System.out.println("Promise has completed"));
    
}
```

## Built-in Promises

Java Promises has a few promises built in, they can all be accessed via a static method in the
me.sparky.promises.Promise class. 

### All Built-in Promises

[Promise.resolve(result)](#resolved-promise) \
[Promise.reject(reason)](#rejected-promise) \
[Promise.all(promises)](#all-promise) \
[Promise.any(promises)](#any-promise)

#### Resolved Promise

```java
Promise.resolve(@Nullable result);
```
Description: Returns a resolved promise. 

#### Rejected Promise

```java
Promise.reject(@NotNull reason);
```
Description: Returns a rejected promise. 

#### All Promise
```java
Promise.all(@NotNull promises);
```
Description: Returns a promise that is resolved when all input promises are resolved. 

#### Any Promise
```java
Promise.any(@NotNull promises);
```
Description: Returns a promise that ios resolved when any input promises are resolved. 

