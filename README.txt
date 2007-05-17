
                        ************
************************* Hamcrest *************************
                        ************

                    <http://hamcrest.org>

--[ What is Hamcrest? ]-------------------------------------

Hamcrest is a library of matchers, which can be combined in
to create flexible expressions of intent in tests.


--[ Binaries ]-----------------------------------------------

Depending on how you intend to use Hamcrest, you should use
different Jars.

 * [hamcrest-core.jar]
   This is the core API to be used by third-party framework
   providers. This includes the a foundation set of matcher
   implementations for common operations.
   This API is stable and will rarely change. You will need
   this library as a minimum.

 * [hamcrest-library.jar]
   The ever-growing library of Matcher implementations. This
   will grow between releases.

 * [hamcrest-generator.jar]
   A tool to allow many Matcher implementations to be
   combined into a single class so users don't have to
   remember many classes/packages to import. Generates
   code.

 * [hamcrest-integration.jar]
   Provides integration between Hamcrest and other testing
   tools, including JUnit (3 and 4), TestNG, jMock and
   EasyMock.

Alternatively, if you don't care:

 * [hamcrest-all.jar]
   Includes all of the above.

For convenience, all the Jars also include source code.


--[ Dependencies ]-------------------------------------------

All libraries in the 'lib' directory are used at build time,
or are optional extras used for tighter integration with
third party libraries.

ALL OF THE DEPENDENCIES ARE OPTIONAL.


--[ Documentation ]------------------------------------------

Documentation can be found at:
  http://hamcrest.org/


--[ Source ]-------------------------------------------------

The complete source for Hamcrest is bundled. This includes:
 * Matcher core classes [src/core]
 * Matcher libary [src/library]
 * Matcher integrations [src/integration]
 * Syntactic sugar generator [src/generator]
 * Unit tests [src/unit-test]
 * Ant build file [build.xml]
 * Dependencies [lib]

To build, please read BUILDING.txt

-------------------------------------------------------------

Developers:
- Joe Walnes
- Nat Pryce
- Steve Freeman

Contributors:
- Robert Chatley
- Tom White
- Neil Dunn
- Dan North
- Magne Rasmussen
- David Saff

Also, thanks to everyone who has worked on DynaMock, nMock,
jMock, EasyMock and MiniMock! These libraries inspired
Hamcrest.
