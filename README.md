# Java Promises 

<img src=https://github.com/Sparky983/java-promises/workflows/CI/badge.svg alt="build-status">

Java Promises is a Java promise library aimed to have syntax which is very similar to JavaScript. 

note: Although many methods may have the same name as a JavaScript method, they may do different 
things. 

## Table of Contents

[1. ](#installation) Installation \
[2. ](#what-is-a-promise) What is a promise? \
[3. ](#quick-start) Getting Started \
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
    <version>1.3</version>
</dependency>
```

Gradle
```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation("me.sparky:promises:1.3")
}
```

## What Is A Promise?

A promise is an object that represents an asynchronous operation. We use them avoid blocking 
operations. 

## Quick Start

### Install

Follow the [installation](#installation) steps. 

### Create and resolve a promise

You can create a new promise by using constructing a new `CompletablePromise`. 

Note that a `Promise` and `CompletablePromise` are different. 
`Promise` is given to the user of your api to for them to use `Promise#then()` and 
`Promise#catchException` to handle the completion of the promise. On the other hand a 
`CompletablePromise` should be used internally for your api to make asynchronous calls. 

```java
Promise<String> promise = new CompletablePromise<>((completablePromise) -> {
    completablePromise.resolve("Resolved!")
});
```

The first argument is the executor. It is used to resolve or reject the promise asynchronously. You
can also do this operation synchronously without an executor by explicitly calling 
`CompletablePromise#resolve(T)` on any `CompletablePromise`

```java
CompletablePromise<String> promise = new CompletablePromise<>();
promise.resolve("Resolved!")
```

To reject the promise call `CompletablePromise#reject(Throwable)`. You may also optionally throw an
Exception inside the executor. 

```java
throw new Exception("Rejected!")

completablePromise.reject(new Exception("Rejected!"));
```

### Handling promise completion

To handle a promise's completion you attach a `.then()` or `.catchException()` with a callback as 
the argument. Unlike JavaScript any exceptions thrown in the `.then()` callback will not be caught
in `.catchException()`. You either have to handle them yourself or they will be printed to the 
system output and swallowed. 

```java
promise
        .then((value) -> System.out.println("Promise resolved with value" + value))
        .catchException((reason) -> reason.printStackTrace());
```

These callbacks will be called once the promise is completed. `.then()` will be called asynchronously
if the promise resolves and `.catchException()` if the promise is rejected. They will be called 
immediately if the promise has already been completed. `.then()` may also optionally be a `Runnable`.

### Advanced

This section is going to cover more advanced topics such as promise transformation. 

Transforming a promise is where you transform the value of a promise into a new one. You can 
transform promise `Promise#transform(Function<T, R>)` method. Here is an example with fetching
json data. 

```java
public static void main(String... args) {
    get(args[0])
        .then((data) -> System.out.println("Recieved json data \n" + json));
}

public Promise<JSON> get(@NotNull String url) {
    return get(url).transform(JSON::parse);
}
```

## Built-in Promises

There are a few built in promise classes. You can access them via a static method in the
`me.sparky.promises.Promise` class. 

### All Built-in Promises

[Promise.resolve(result)](#resolved-promise) \
[Promise.reject(reason)](#rejected-promise) \
[Promise.all(promises)](#all-promise) \
[Promise.any(promises)](#any-promise) \
[Promise.whenAll(promises)](#when-all-promise) \
[Promise.allSettled(promises)](#all-settled-promise)

#### Resolved Promise

```java
Promise.resolve(@Nullable T);
```
Description: Returns a resolved promise. 

#### Rejected Promise

```java
Promise.reject(@NotNull Throwable);
```
Description: Returns a rejected promise. 

#### All Promise
```java
Promise.all(@NotNull Promise<T>...);
```
Description: Returns a promise that is resolved when all input promises are resolved. 

#### Any Promise
```java
Promise.any(@NotNull Promise<T>...);
```
Description: Returns a promise that is resolved when any input promises are resolved. 

#### When All Promise
```java
Promise.whenAll(@NotNull Promise<?>...);
```
Description: Returns a promise that resolves when all input promises are resolved. 

#### All Settled Promise
```java
Promise.allSettled(@NotNull Promise<?>...);
```
Description: Returns a promise that resolves when all the inputs are settled (completed).
