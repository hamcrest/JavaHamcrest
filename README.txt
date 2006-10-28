
                     *************
********************** Ham Crest **********************
                     *************

                 "TODO: catchy phrase"

                 <http://hamcrest.org>

--[ What is Ham Crest? ]-------------------------------------

TODO

--[ Binaries ]-----------------------------------------------

Depending on how you intend to use Ham Crest, you should use
different Jars.

 * [hamcrest-api.jar]
   This is the core API to be used by third-party framework
   providers. This excludes the implementations of Matchers.
   This API is stable and will rarely change.

 * [hamcrest-library.jar]
   The ever-growing library of Matcher implementations.
   This should be

TODO: Finish this

All libraries in the 'lib' directory are used at build time,
or are optional extras used for tighter integration with
third party libraries.

ALL OF THE DEPENDENCIES ARE OPTIONAL.


--[ Documentation ]------------------------------------------

Documentation can be found at http://hamcrest.org/


--[ Source ]-------------------------------------------------

The complete source for Ham Crest is bundled. This includes:
 * Matcher API [src/api]
 * Matcher libary [src/library]
 * Matcher integrations [src/integration]
 * Syntactic sugar generator [src/generator]
 * Unit tests [src/unit-test]
 * Ant build file [build.xml]
 * Dependencies [lib]

To build, please read BUILDING.txt

-------------------------------------------------------------
-Nat Pryce and Joe Walnes
http://hamcrest.org/

