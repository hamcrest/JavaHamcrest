![JavaHamcrest](http://hamcrest.org/images/logo.jpg)

[![Build Status](https://github.com/hamcrest/JavaHamcrest/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/hamcrest/JavaHamcrest/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/org.hamcrest/hamcrest.svg?label=Maven%20Central)](https://search.maven.org/artifact/org.hamcrest/hamcrest)
[![License](https://img.shields.io/github/license/hamcrest/JavaHamcrest.svg)](LICENSE)


# Java Hamcrest

## What is Hamcrest?

Hamcrest is a library of matchers, which can be combined in to create flexible expressions of intent in tests.
They've also been used for other purposes.

The [tutorial](http://hamcrest.org/JavaHamcrest/tutorial) is good place to see how Hamcrest can be used.

## Downloads

You can obtain Hamcrest binaries from [maven central](https://search.maven.org/artifact/org.hamcrest/hamcrest). If you
are using build tooling such as Maven, Gradle, etc, you can simply add a dependency declaration to your build
definition. Learn more at [Hamcrest Distributables](http://hamcrest.org/JavaHamcrest/distributables).

## Documentation

Documentation can be found on the [Hamcrest site](http://hamcrest.org). For a detailed list of recent changes,
see [CHANGES.md](CHANGES.md)

## Reporting Bugs/Issues

If you find an issue with Java Hamcrest, please report it via the
[GitHub issue tracker](https://github.com/hamcrest/JavaHamcrest/issues),
after first checking that it hasn't been raised already.

## Build from Source

Building Hamcrest from source requires a minimum of JDK 1.8.

Clone the repository, and from the top level directory in the repo workspace
run the following command:

```shell
./gradlew clean build javadoc
```
This will download the correct version of Gradle, do a full clean build,
run all tests and (if successful) package the compiled classes in a jar
file. The resulting look under the `hamcrest/build/libs` directory.

## Acknowledgements

Developers:

* Joe Walnes
* Nat Pryce
* Steve Freeman

Contributors:

* Robert Chatley
* Tom White
* Neil Dunn
* Dan North
* Magne Rasmussen
* David Saff
* Tom Denley
* Joe Schmetzer

Also, thanks to everyone who has worked on DynaMock, nMock, jMock, EasyMock
and MiniMock! These libraries inspired Hamcrest.
