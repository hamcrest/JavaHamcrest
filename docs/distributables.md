---
title: Hamcrest Distributables
layout: default
---
[Java Hamcrest Home](index)

# Hamcrest Distributables

## Introduction

Hamcrest consists of different jars matching the different needs of
applications. This document describes these distributables and the
functionality contained in each of them.

## The Java Hamcrest Jars

### `hamcrest-core.jar`

This is the core API to be used by third-party framework providers.
This includes a foundation set of matcher implementations for common
operations. This API is stable and will rarely change. You will
need this library as a minimum.

### `hamcrest-library.jar`

The library of Matcher implementations which are based on the core 
functionality in hamcrest-core.jar.

### `hamcrest-integration.jar`

Provides integration between Hamcrest and other testing tools, such as jMock
and EasyMock. Uses hamcrest-core.jar and hamcrest-library.jar.

### `hamcrest-generator.jar`

A tool to allow many Matcher implementations to be combined into
a single class with static methods returning the different matchers 
so users don't have to remember many classes/packages to import. 
Generates code. This library is only used internally at compile time.
It is not necessary for the use of any of the other hamcrest libraries 
at runtime.

### `hamcrest-all.jar`

One jar containing all classes of all the other jars.


## Adding Hamcrest to your Project

The dependencies of the jars (`hamcrest-integration` uses `hamcrest-library`
which uses `hamcrest-core`) is represented by the Maven dependency mechanism.
There is no `hamcrest-all` library in the Maven repo prior to version 1.3. Just
use hamcrest-integration which references all the other hamcrest libraries.
 

### Gradle

Add `"org.hamcrest:hamcrest-library:1.3"` to the dependencies section of your
`build.gradle`, for example:

```gradle
apply plugin: 'java'

dependencies {
    testImplementation 'org.hamcrest:hamcrest-library:1.3'
}
```

Note: older versions of Gradle use the `testCompile` configuration
instead of the `testImplementation` configuration.

### Maven

Add the following dependency declaration to your `pom.xml`:

```xml
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest-library</artifactId>
    <version>1.3</version>
    <scope>test</scope>
</dependency>
```

### Download

You can download the jars directly from Maven Central. At a minimum, you will
need `hamcrest-core-1.3.jar`, though most people will also use
`hamcrest-library-1.3.jar` too. If you want to integrate Hamcrest Matchers with
other 

You can find the jars by searching Maven Central for groupId `org.hamcrest` using the following link:

> [https://search.maven.org/search?q=g:org.hamcrest](https://search.maven.org/search?q=g:org.hamcrest) 

Maven central has some extra artifacts called `java-hamcrest` and `hamcrest-java`,
with a version of `2.0.0.0`. Please do not use these, as they are an aborted effort
at repackaging the different jars.
