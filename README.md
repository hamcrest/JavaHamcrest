[![][logo]][website]
Java Hamcrest
=============
Licensed under [BSD License][].

What is Hamcrest?
-----------------
Hamcrest is a library of matchers, which can be combined in to create flexible expressions of intent in tests.

Downloads
---------
You can obtain Hamcrest binaries from [maven central][], or from [google code downloads][].

Binaries
--------
Depending on how you intend to use Hamcrest, you should use different Jars.

  * __hamcrest-core.jar__ -- This is the core API to be used by third-party framework providers. This includes the a foundation set of matcher implementations for common operations. This API is stable and will rarely change. You will need this library as a minimum.
  * __hamcrest-library.jar__ -- The ever-growing library of Matcher implementations. This will grow between releases.
  * __hamcrest-generator.jar__ -- A tool to allow many Matcher implementations to be combined into a single class so users don't have to remember many classes/packages to import. Generates code.
  * __hamcrest-integration.jar__ -- Provides integration between Hamcrest and other testing tools, including JUnit (3 and 4), TestNG, jMock and EasyMock.

Alternatively, if you don't care:

 * __hamcrest-all.jar__ -- Includes all of the above.

Sources and JavaDoc jars are available for all of the above.

Dependencies
------------
All libraries in the `lib` directory are used at build time, or are optional extras used for tighter integration with third party libraries.

ALL OF THE DEPENDENCIES ARE OPTIONAL.

Documentation
-------------
Documentation can be found on the [google code wiki][].

Source
------
The complete source for Hamcrest is bundled. This includes:
  * Matcher core classes [src/core]
  * Matcher library [src/library]
  * Matcher integrations [src/integration]
  * Syntactic sugar generator [src/generator]
  * Unit tests [src/unit-test]
  * Ant build file [build.xml]
  * Dependencies [lib]

To build, please read BUILDING.txt

Acknowledgements
----------------
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

Also, thanks to everyone who has worked on DynaMock, nMock, jMock, EasyMock and MiniMock! These libraries inspired Hamcrest.


[logo]: https://raw.github.com/hamcrest/JavaHamcrest/master/doc/images/logo.jpg
[website]: http://code.google.com/p/hamcrest
[BSD License]: http://opensource.org/licenses/BSD-3-Clause
[Maven central]: http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.hamcrest
[google code downloads]: http://code.google.com/p/hamcrest/downloads/list?can=2&q=label%3AJava
[google code wiki]: http://code.google.com/p/hamcrest/wiki/Tutorial
