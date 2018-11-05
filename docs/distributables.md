---
title: Hamcrest Distributables
layout: default
---
# Hamcrest Distributables

## Introduction

Hamcrest consists of a single jar, called `java-hamcrest-<version>.jar` (where `<version>` refers to the specific version of Hamcrest).

Currently, the latest version of Java Hamcrest is `2.0.0.0`, and so the binary distributable is called `java-hamcrest-2.0.0.0.jar`.

## Adding Hamcrest to your Project

### Gradle

Add `"org.hamcrest:java-hamcrest:$hamcrestVersion"` to the dependencies section of your `build.gradle`, for example:

```gradle
apply plugin: 'java'

dependencies {
    testImplementation 'org.hamcrest:java-hamcrest:2.0.0.0'
}
```

Note: older versions of Gradle use the `testCompile` configuration
instead of the `testImplementation` configuration.

### Maven

Add the following dependency declaration to your `pom.xml`:

```xml
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>java-hamcrest</artifactId>
    <version>2.0.0.0</version>
    <scope>test</scope>
</dependency>
```

### Download

You can download the jar directly from Maven Central. Hamcrest does
not have any transitive dependencies, so you only need to download
a single jar.

You can find the jar by searching Maven Central for groupId `org.hamcrest`
and artifactId `java-hamcrest` using the following link:

> [https://search.maven.org/search?q=g:org.hamcrest%20AND%20a:java-hamcrest](https://search.maven.org/search?q=g:org.hamcrest%20AND%20a:java-hamcrest) 

## Previous Versions 

Prior to version 2.0.0.0, Java Hamcrest was distributed as different
jars matching the different needs of applications. This document
describes these distributables and the functionality contained in
each of them.

### `hamcrest-core-1.3.jar`

This is the core API to be used by third-party framework providers.
This includes a foundation set of matcher implementations for common
operations. This API is stable and will rarely change. You will
need this library as a minimum. From version 2.0.0.0, this is
included in `java-hamcrest.jar`.

### `hamcrest-library-1.3.jar`

The library of Matcher implementations which are based on the core 
functionality in hamcrest-core.jar. From version 2.0.0.0, this is
included in `java-hamcrest.jar`.

### `hamcrest-generator-1.3.jar`

A tool to allow many Matcher implementations to be combined into
a single class with static methods returning the different matchers 
so users don't have to remember many classes/packages to import. 
Generates code. This library is only used internally at compile time.
It is not necessary for the use of any of the other hamcrest libraries 
at runtime.

### `hamcrest-integration-1.3.jar`

Provides integration between Hamcrest and other testing tools, 
including JUnit (3 and 4), TestNG, jMock and EasyMock. Uses 
hamcrest-core.jar and hamcrest-library.jar.

### `hamcrest-all.jar`

One jar containing all classes of all the other jars.

### Previous Maven Versions

The description above also applies to the hamcrest Maven
artifacts. The dependencies of the jars (`hamcrest-integration`
uses `hamcrest-library` which uses `hamcrest-core`) is represented
by the Maven dependency mechanism. There is no `hamcrest-all`
library in the Maven repo prior to version 2.0.0.0. Just use hamcrest-integration which
references all the other hamcrest libraries. After version 2.0.0.0,
all the specified jars have been replaced by `java-hamcrest`.
