---
title: Hamcrest Distributables
layout: default
---
[Java Hamcrest Home](index)

# Hamcrest Distributables
{:.no_toc}

## Introduction
{:.no_toc}

This document describes the current and previous versions of the various Hamcrest
distributables, and the functionality contained in each of them.

The latest version of Hamcrest consists of a single jar file which contains base
classes and a library of useful matcher implementations. This is different from
older versions.

Older versions of Hamcrest consisted of a number of different jars matching the
different needs of applications. The change in the jar packaging requires care
when upgrading.

## Table of Contents
{:.no_toc}

* TOC
{:toc}

## The Hamcrest Jar

All the base classes and standard matcher implementations are contained in a
single jar file called `hamcrest-2.1.jar`.

### Using Hamcrest in a Gradle Project

Add `"org.hamcrest:hamcrest:2.1"` to the dependencies section of your
`build.gradle`, for example:

```gradle
apply plugin: 'java'

dependencies {
    testImplementation 'org.hamcrest:hamcrest:2.1'
}
```

Note: older versions of Gradle use the `testCompile` configuration
instead of the `testImplementation` configuration.

### Using Hamcrest in a Maven Project

Add the following to the `<dependencies>` section in your `pom.xml`:

```xml
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest</artifactId>
    <version>2.1</version>
    <scope>test</scope>
</dependency>
```

### Download the Hamcrest Jar

You can download the jars directly from Maven Central. You can find the jars by searching Maven Central for groupId `org.hamcrest`
using the following link:

> [https://search.maven.org/search?q=g:org.hamcrest](https://search.maven.org/search?q=g:org.hamcrest) 

## Previous Versions of Hamcrest

Prior to version 2.x, Hamcrest was distributed through multiple jars, described
below. 

* **`hamcrest-core.jar`**: This was the core API to be used by third-party framework
providers. This includes a foundation set of matcher implementations for common
operations. This library was used as a dependency for many third-party libraries, 
including JUnit 4.x. From Hamcrest version 2.x, all the classes in
`hamcrest-core.jar` were moved into `hamcrest.jar`.
* **`hamcrest-library.jar`**: The library of Matcher implementations which are based
on the core functionality in hamcrest-core.jar. From Hamcrest version 2.x, all the
classes in `hamcrest-core.jar` were moved into `hamcrest.jar`.
* **`hamcrest-integration.jar`**: Provides integration between Hamcrest and other
testing tools, such as jMock and EasyMock. It depends upon `hamcrest-core.jar` and
`hamcrest-library.jar`. There are no new releases of this library since version 1.3.
* **`hamcrest-generator.jar`**: A tool to allow many Matcher implementations to be
combined into a single class with static methods returning the different matchers 
so users don't have to remember many classes/packages to import. Generates code.
This library is only used internally at compile time. It is not necessary for the
use of any of the other hamcrest libraries at runtime. There are no new releases of
this library since version 1.3.
* **`hamcrest-all.jar`**: One jar containing all classes of all the other jars.
There are no new releases of this library since version 1.3. Please use the single
`hamcrest.jar` instead.

### Upgrading from Hamcrest 1.x

Care must be taken when upgrading from Hamcrest 1.3 or earlier. Due to the change in
packaging, the version conflict resolution that happens in dependency management
tools won't happen automatically. A common example is projects that depend upon
JUnit 4. JUnit 4 declares a transitive dependency upon `hamcrest-core-1.3.jar`.
Because `hamcrest-core` is not the same artifact as `hamcrest`, it will not be
upgraded.

To address this issue, Hamcrest 2.1 and later also publish artifacts for
`hamcrest-core` and `hamcrest-library`. Although these jars contain no classes,
they trigger the version conflict upgrade in the dependency manager, and correctly
declare transitive dependencies upon the new `hamcrest` packaging. Users can
directly declare a dependency upon these shim jars to force the upgrade.

#### Gradle Upgrade Example

```gradle
apply plugin: 'java'

dependencies {
    testImplementation 'org.hamcrest:hamcrest:2.1'
    testImplementation 'org.hamcrest:hamcrest-library:2.1'
    testImplementation 'junit:junit:4.12'
}
```

#### Maven Upgrade Example

**Warning**:
Maven users should declare a dependency upon `hamcrest-library` **before** other
dependencies, otherwise the older version will take precedence.  

```xml
<dependencies>
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest-library</artifactId>
        <version>2.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
