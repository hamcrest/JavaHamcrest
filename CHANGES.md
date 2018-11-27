# Hamcrest Changes

## Version 2.1-rc2 (27th November 2018)

* Publish pom-only artifacts for hamcrest-core and hamcrest-library.

## Version 2.1-rc1 (25th November 2018)

### Overview for 2.1-rc1

After a long hiatus without releases, this version simplifies the packaging of
Hamcrest into a single jar: `hamcrest-<version>.jar`. Other big changes include
Java 9 module compatibility, along with numerous other improvements and bug
fixes.

### Breaking Changes for 2.1-rc1

Although the class API has not changed since Hamcrest 1.3, the way that the
project is packaged has changed. Refer to the [Hamcrest Distributables](http://hamcrest.org/JavaHamcrest/distributables.html)
documentation for more information. 

### Changes for 2.1-rc1

* Publish a single jar `hamcrest-2.1.jar`
* Documentation updates
* Add implementation for CharSequence length matcher
* Fix for TypeSafeDiagnosingMatcher can't detect generic types for subclass
* Renamed IsCollectionContaining to IsIterableContaining
* Make Hamcrest an OSGI bundle
* Add StringRegularExpression matcher
* Fix StringContainsInOrder to detect if a repeated pattern is missing 
* Add ArrayAsIterableMatcher
* Fix description for IsEqualIgnoringCase
* Fix JavaDoc examples

## Version 2.0.0.0 (18th January 2015)

This version was the first release after migrating from the old Google Code hosting to GitHub.

* Upgraded to Java 7
* Build with Gradle
* Publish a single jar java-hamcrest-2.0.0.0.jar
* Removed deprecated methods from previous release
* Improve mismatch description of hasItem/hasItems
* General improvements to mismatch descriptions
* Several JavaDoc improvements and corrections
* Deprecated several matcher factory methods of the for "isXyz"
* Fix [GH issue #75](https://github.com/hamcrest/JavaHamcrest/issues/75) - address doclint errors reported in JDK 1.8
* Fix [GH issue #69](https://github.com/hamcrest/JavaHamcrest/issues/69) - Iterable contains in order is null-safe
* Fix [GH issue #59](https://github.com/hamcrest/JavaHamcrest/issues/59) - added equalToObject() (i.e. unchecked) method
* Fix [GH issue #25](https://github.com/hamcrest/JavaHamcrest/issues/25) - arrayContaining(null, null) cause NullPointerException
* Fix [GH issue #36](https://github.com/hamcrest/JavaHamcrest/issues/36) - string matching on regular expressions
* Fix [GH issue #8](https://github.com/hamcrest/JavaHamcrest/issues/8) - isCloseTo() shows wrong delta in mismatch description
* Fix [GH issue #59](https://github.com/hamcrest/JavaHamcrest/issues/59) - add untyped version of equalTo, named equalToObject
* Fix [GC issue #131](https://code.google.com/archive/p/hamcrest/issues/131) - Implement IsEmptyMap, IsMapWithSize
* Fix [GC issue #187](https://code.google.com/archive/p/hamcrest/issues/187) - IsArray.describeMismatchSafely() should use Matcher.describeMismatch
* Fix [GC issue #155](https://code.google.com/archive/p/hamcrest/issues/155) - Add Matcher implementation for files
* Fix [GC issue #69](https://code.google.com/archive/p/hamcrest/issues/69) - fix NPE in IsIterableContainingInOrder

## Version 1.3 (9th July 2012)

* Introduce Condition class to ease the implementation of multi-step matches
* Upgrade qdox (included in the generator) to the latest stable version
* Correct inadvertent deprecation of the Is.isA factory method
* Fix [issue #179](https://code.google.com/archive/p/hamcrest/issues/179) - AllOf does not output mismatch description
* Fix [issue #177](https://code.google.com/archive/p/hamcrest/issues/177) - Introduced closeTo matcher for BigDecimals
* Fix [issue #152](https://code.google.com/archive/p/hamcrest/issues/152) - Factory classes missing from matchers.xml
* Fix [issue #144](https://code.google.com/archive/p/hamcrest/issues/144) - OrderingComparison doesn't describe mismatch of comparables that return values other than (-1,0,1)
* Fix [issue #134](https://code.google.com/archive/p/hamcrest/issues/134) - DescribedAs does not delegate describeMismatch
* Fix [issue #106](https://code.google.com/archive/p/hamcrest/issues/106) - deprecation warning when writing custom matchers
* Fix [issue #101](https://code.google.com/archive/p/hamcrest/issues/101) - Added theInstance alias for sameInstance factory method

## Version 1.3 RC2 (22nd October 2010)

* Added FeatureMatcher
* distinguish between instanceOf() and any()

## Version 1.2 (16th May 2009)

* Added mismatch reporting
* Added WithSamePropertyValuesAs matcher
* Moved any() from IsAnything to IsInstanceOf. It now checks the type of the matched object
* Moved MatcherAssert from integration to core
* Tightened up generics.  
* Added IsMapContainingKey and IsMapContainingValue matchers to resolve a 
  generics bug in hasKey and hasValue static factories previously declared
  in IsMapContaining (ngd)
* Added IsCollectionOnlyContaining and IsArrayOnlyContaining which matches 
  collections (and arrays) where all match a given matcher. E.g onlyContains(3,4,5) 
  or onlyContains(lessThan(9))
* text module moved to separate project, hamcrest-text-patterns
* added more colection matchers: xContainingInAnyOrder, xContainingInOrder, xWithSize
* new text Matcher: IsEmptyString
* hamcrest generator uses method return type 

## Version 1.1 (30th June 2007)

* Hamcrest Generator now includes JavaDoc and parameter names in generated code
  by using QDox to parse the source code.
* Created hamcrest-core.jar (and removed hamcrest-api.jar).
  Moved core set of matchers (and, eq, not, etc)
  to this package to make it more practical for external libraries
  to embed Hamcrest.
* Created CoreMatchers (static import sugar) in hamcrest-core.jar.
* StringBuilder can use any Appendable (not just StringBuffer).
* Added sensible toString() method to BaseMatcher.
* Created StringDescription.asString() alias (because toString() caused issues
  with static imports).
* Relaxed isInstanceOf() matcher generic type so it can be used on any kind of
  object. e.g. assertThat(someUnknownObject, isInstanceOf(String.class));
* Added any(Class<T>), null(Class<T>) and notNull(Class<T>) matchers, which returns
  Matcher<T>. Helpful when the compiler struggles with type inference.
* Modified anyOf() and allOf() to accept mixed-types.
* TypeSafeMatcher.matchesSafely() is now public.
* Generator recognizes @Factory methods that return subclass of Matcher.
  (Fix by David Saff)

## Version 1.0 (15th Dececmber 2006)

Initial release.

* Support for namespaces in HasXPath
* Bug fix for matching empty elements with HasXPath
