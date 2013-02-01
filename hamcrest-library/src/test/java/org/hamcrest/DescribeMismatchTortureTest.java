package org.hamcrest;

import org.hamcrest.beans.HasProperty;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.number.BigDecimalCloseTo;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.hamcrest.collection.IsArray.array;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIn.oneOf;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.collection.IsMapContaining.hasValue;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.CombinableMatcher.either;
import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.number.IsNaN.notANumber;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/** 
 * This is a "torture test" for the describeMismatch method. The goal
 * is look at the output for all the matchers, and in particular, to
 * make sure the output is still reasonable when matchers are nested
 * using not(), anyOf(), allOf(), and other aggregating matchers.
 */
@SuppressWarnings("unchecked")
public class DescribeMismatchTortureTest {

    //################################################################
    // org.hamcrest.core (alphabetical order)

    @Test public void testAnyOf() {
        assertEquals("Expected (\"good\" or \"bad\" or \"ugly\") but was null.", describeMismatch(anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly")), null));
        assertEquals("Expected (\"good\" or \"bad\" or \"ugly\") but was <5>.", describeMismatch(anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly")), 5));
        assertEquals("Expected (\"good\" or \"bad\" or \"ugly\") but was \"other\".", describeMismatch(anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly")), "other"));
        assertEquals("Expected (\"good\" or an instance of java.lang.Number) but was null.", describeMismatch(anyOf(equalTo("good"), instanceOf(Number.class)), null));
        assertEquals("Expected (\"good\" or an instance of java.lang.Number) but was <true> and was a java.lang.Boolean.", describeMismatch(anyOf(equalTo("good"), instanceOf(Number.class)), true));
        assertEquals("Expected (\"good\" or an instance of java.lang.Number) but was \"other\" and was a java.lang.String.", describeMismatch(anyOf(equalTo("good"), instanceOf(Number.class)), "other"));
        assertEquals("Expected not (\"good\" or \"bad\" or \"ugly\") but was \"bad\".", describeMismatch(not(anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly"))), "bad"));
        assertEquals("Expected not (\"good\" or an instance of java.lang.Number) but was <5L> and was a java.lang.Long.", describeMismatch(not(anyOf(equalTo("good"), instanceOf(Number.class))), 5L));
    }

    @Test public void testAllOf() {
        assertEquals("Expected (\"bad\" and \"good\") but was null.", describeMismatch(allOf(equalTo("bad"), equalTo("good")), null));
        assertEquals("Expected (\"bad\" and \"good\") but was <5>.", describeMismatch(allOf(equalTo("bad"), equalTo("good")), 5));
        assertEquals("Expected (\"bad\" and \"good\") but was \"bad\".", describeMismatch(allOf(equalTo("bad"), equalTo("good")), "bad"));
        assertEquals("Expected (an instance of java.lang.String and \"bad\") but was null.", describeMismatch(allOf(instanceOf(String.class), equalTo("bad")), null));
        assertEquals("Expected (an instance of java.lang.String and \"bad\") but was a java.lang.Integer.", describeMismatch(allOf(instanceOf(String.class), equalTo("bad")), 5));
        assertEquals("Expected (an instance of java.lang.String and \"bad\") but was a java.lang.String and was \"ugly\".", describeMismatch(allOf(instanceOf(String.class), equalTo("bad")), "ugly"));
        assertEquals("Expected not (an instance of java.lang.String and \"bad\") but was a java.lang.String and was \"bad\".", describeMismatch(not(allOf(instanceOf(String.class), equalTo("bad"))), "bad"));
    }

    @Test public void testCombinableMatcher() {
        // both
        assertEquals("Expected (\"bad\" and \"good\") but was \"bad\".", describeMismatch(both(equalTo("bad")).and(equalTo("good")), "bad"));
        assertEquals("Expected (an instance of java.lang.String and \"bad\") but was a java.lang.String and was \"ugly\".", describeMismatch(both(any(String.class)).and(equalTo("bad")), "ugly"));
        assertEquals("Expected not (an instance of java.lang.String and \"bad\") but was a java.lang.String and was \"bad\".", describeMismatch(not(both(any(String.class)).and(equalTo("bad"))), "bad"));

        // either
        assertEquals("Expected ((\"good\" or \"bad\") or \"ugly\") but was \"other\".", describeMismatch(either(equalTo("good")).or(equalTo("bad")).or(equalTo("ugly")), "other"));
        assertEquals("Expected (\"good\" or an instance of java.lang.Number) but was \"other\" and was a java.lang.String.", describeMismatch(either(equalTo("good")).or(instanceOf(Number.class)), "other"));
        assertEquals("Expected not ((\"good\" or \"bad\") or \"ugly\") but was \"bad\".", describeMismatch(not(either(equalTo("good")).or(equalTo("bad")).or(equalTo("ugly"))), "bad"));
        assertEquals("Expected not (\"good\" or an instance of java.lang.Number) but was <5L> and was a java.lang.Long.", describeMismatch(not(either(equalTo("good")).or(instanceOf(Number.class))), 5L));
    }

    @Test public void testDescribedAs() {
        assertEquals("Expected a GOOD answer but was \"other\".", describeMismatch(describedAs("a GOOD answer", equalTo("good")), "other"));
        assertEquals("Expected not a GOOD answer but was \"good\".", describeMismatch(not(describedAs("a GOOD answer", equalTo("good"))), "good"));
        assertEquals("Expected a BAD answer but was \"good\".", describeMismatch(describedAs("a BAD answer", not(equalTo("good"))), "good"));
    }

    @Test public void testEvery() {
        assertEquals("Expected every item is a string starting with \"a\" but item <2> was \"other\".", describeMismatch(everyItem(startsWith("a")), Arrays.asList("alpha", "aleph", "other", "apple")));
        assertEquals("Expected every item is an instance of java.lang.Number but item <2> was a java.lang.Character.", describeMismatch(everyItem(instanceOf(Number.class)), Arrays.asList((byte)0, (short)0, (char)0, 0)));
        assertEquals("Expected not every item is a string starting with \"a\" but item <0> was \"alpha\" and item <1> was \"aleph\" and item <2> was \"any\" and item <3> was \"apple\".", describeMismatch(not(everyItem(startsWith("a"))), Arrays.asList("alpha", "aleph", "any", "apple")));
        assertEquals("Expected every item is not a string starting with \"a\" but item <1> was \"alpha\".", describeMismatch(everyItem(not(startsWith("a"))), Arrays.asList("first", "alpha", "aleph")));
    }

    @Test public void testIs() {
        assertEquals("Expected is \"good\" but was \"other\".", describeMismatch(is("good"), "other"));
        assertEquals("Expected is not \"good\" but was \"good\".", describeMismatch(is(not("good")), "good"));
        assertEquals("Expected not is \"good\" but was \"good\".", describeMismatch(not(is("good")), "good"));
    }

    @Test public void testIsAnything() {
        assertEquals("Expected not ANYTHING but was \"something\".", describeMismatch(not(anything()), "something"));
        assertEquals("Expected not any possible value but was \"something\".", describeMismatch(not(anything("any possible value")), "something"));
    }

    @Test public void testIsCollectionContaining() {
        // hasItem
        assertEquals("Expected a collection containing \"good\" but item <0> was \"other\" and item <1> was \"bad\" and item <2> was \"ugly\".", describeMismatch(hasItem("good"), Arrays.asList("other", "bad", "ugly")));
        assertEquals("Expected not a collection containing \"good\" but item <2> was \"good\".", describeMismatch(not(hasItem("good")), Arrays.asList("ugly", "bad", "good", "other")));
        assertEquals("Expected a collection containing (\"good\" or \"bad\") but item <0> was \"apple\" and item <1> was \"pear\" and item <2> was \"orange\".", describeMismatch(hasItem(anyOf(equalTo("good"), equalTo("bad"))), Arrays.asList("apple", "pear", "orange")));
        assertEquals("Expected a collection containing (\"good\" or an instance of java.lang.Number) but item <0> was \"bad\" and was a java.lang.String and item <1> was <true> and was a java.lang.Boolean and item <2> was 'A' and was a java.lang.Character.", describeMismatch(hasItem(anyOf(equalTo("good"), instanceOf(Number.class))), Arrays.asList("bad", true, 'A')));
        assertEquals("Expected not a collection containing (\"good\" or an instance of java.lang.Number) but item <1> was <5> and was a java.lang.Integer.", describeMismatch(not(hasItem(anyOf(equalTo("good"), instanceOf(Number.class)))), Arrays.asList("bad", 5, 'A')));

        // hasItems
        assertEquals("Expected (a collection containing \"good\" and a collection containing \"bad\") but item <0> was \"apple\" and item <1> was \"pear\" and item <2> was \"orange\".", describeMismatch(hasItems(equalTo("good"), equalTo("bad")), Arrays.asList("apple", "pear", "orange")));
        assertEquals("Expected (a collection containing \"good\" and a collection containing \"bad\") but item <1> was \"good\" and item <0> was \"apple\" and item <1> was \"good\" and item <2> was \"orange\".", describeMismatch(hasItems(equalTo("good"), equalTo("bad")), Arrays.asList("apple", "good", "orange")));
        assertEquals("Expected not (a collection containing \"good\" and a collection containing \"bad\") but item <1> was \"good\" and item <2> was \"bad\".", describeMismatch(not(hasItems(equalTo("good"), equalTo("bad"))), Arrays.asList("apple", "good", "bad")));
    }

    @Test public void testIsEqual() {
        assertEquals("Expected \"good\" but was null.", describeMismatch(equalTo("good"), null));
        assertEquals("Expected \"good\" but was <5>.", describeMismatch(equalTo("good"), 5));
        assertEquals("Expected \"good\" but was \"other\".", describeMismatch(equalTo("good"), "other"));
        assertEquals("Expected not \"good\" but was \"good\".", describeMismatch(not(equalTo("good")), "good"));
    }

    @Test public void testIsInstanceOf() {
        assertEquals("Expected an instance of java.lang.Number but was null.", describeMismatch(instanceOf(Number.class), null));
        assertEquals("Expected an instance of java.lang.Number but was a java.lang.String.", describeMismatch(instanceOf(Number.class), "other"));
        assertEquals("Expected not an instance of java.lang.Number but was a java.lang.Integer.", describeMismatch(not(instanceOf(Number.class)), 5));
    }

    @Test public void testIsNot() {
        assertEquals("Expected not \"good\" but was \"good\".", describeMismatch(not(equalTo("good")), "good"));
        assertEquals("Expected not not \"good\" but was \"bad\".", describeMismatch(not(not(equalTo("good"))), "bad"));
    }

    @Test public void testIsNull() {
        assertEquals("Expected null but was \"other\".", describeMismatch(nullValue(), "other"));
        assertEquals("Expected not null but was null.", describeMismatch(not(nullValue()), null));
    }

    @Test public void testIsSame() {
        assertEquals("Expected sameInstance(\"good\") but was null.", describeMismatch(sameInstance("good"), null));
        assertEquals("Expected sameInstance(\"good\") but was <5>.", describeMismatch(sameInstance("good"), 5));
        assertEquals("Expected sameInstance(\"good\") but was \"other\".", describeMismatch(sameInstance("good"), "other"));
        assertEquals("Expected not sameInstance(<0>) but was <0>.", describeMismatch(not(sameInstance(0)), 0));
    }

    @Test public void testStringContains() {
        assertEquals("Expected a string containing \"good\" but was null.", describeMismatch(containsString("good"), null));
        assertEquals("Expected a string containing \"good\" but was a java.lang.Integer (<5>).", describeMismatch(containsString("good"), 5));
        assertEquals("Expected a string containing \"good\" but was \"bad\".", describeMismatch(containsString("good"), "bad"));
        assertEquals("Expected not a string containing \"good\" but was \"oh my goodness\".", describeMismatch(not(containsString("good")), "oh my goodness"));
    }

    @Test public void testStringEndsWith() {
        assertEquals("Expected a string ending with \"good\" but was null.", describeMismatch(endsWith("good"), null));
        assertEquals("Expected a string ending with \"good\" but was a java.lang.Integer (<5>).", describeMismatch(endsWith("good"), 5));
        assertEquals("Expected a string ending with \"good\" but was \"other\".", describeMismatch(endsWith("good"), "other"));
        assertEquals("Expected not a string ending with \"good\" but was \"very good\".", describeMismatch(not(endsWith("good")), "very good"));
    }

    @Test public void testStringStartsWith() {
        assertEquals("Expected a string starting with \"good\" but was null.", describeMismatch(startsWith("good"), null));
        assertEquals("Expected a string starting with \"good\" but was a java.lang.Integer (<5>).", describeMismatch(startsWith("good"), 5));
        assertEquals("Expected a string starting with \"good\" but was \"other\".", describeMismatch(startsWith("good"), "other"));
        assertEquals("Expected not a string starting with \"good\" but was \"goodness\".", describeMismatch(not(startsWith("good")), "goodness"));
    }

    //################################################################
    // org.hamcrest.beans (alphabetical order)
    
    @Test public void testHasProperty() {
        assertEquals("Expected has property \"good\" but was null.", describeMismatch(HasProperty.hasProperty("good"), null));
        assertEquals("Expected has property \"good\" but didn't have property \"good\".", describeMismatch(HasProperty.hasProperty("good"), 5));
        assertEquals("Expected not has property \"bytes\" but had property \"bytes\".", describeMismatch(not(HasProperty.hasProperty("bytes")), "good"));
    }

    @Test public void testHasPropertyWithValue() {
        assertEquals("Expected has property \"message\" that is \"good\" but was null.", describeMismatch(HasPropertyWithValue.hasProperty("message", equalTo("good")), null));
        assertEquals("Expected has property \"message\" that is \"good\" but didn't have property \"message\".", describeMismatch(HasPropertyWithValue.hasProperty("message", equalTo("good")), 5));
        assertEquals("Expected has property \"message\" that is \"good\" but property \"message\" was \"bad\".", describeMismatch(HasPropertyWithValue.hasProperty("message", equalTo("good")), new Exception("bad")));
        assertEquals("Expected not has property \"message\" that is \"good\" but property \"message\" was \"good\".", describeMismatch(not(HasPropertyWithValue.hasProperty("message", equalTo("good"))), new Exception("good")));
        assertEquals("Expected has property \"message\" that is (a string starting with \"g\" and an instance of java.lang.Number) but property \"message\" was \"good\" and was a java.lang.String.", describeMismatch(HasPropertyWithValue.hasProperty("message", allOf(startsWith("g"), instanceOf(Number.class))), new Exception("good")));
    }

    @Test public void testSamePropertyValuesAs() {
        assertEquals("Expected same property values as String [bytes: [<103>, <111>, <111>, <100>], empty: <false>] but was null.", describeMismatch(samePropertyValuesAs("good"), null));
        assertEquals("Expected same property values as String [bytes: [<103>, <111>, <111>, <100>], empty: <false>] but was of incompatible type Integer.", describeMismatch(samePropertyValuesAs("good"), 5));
        assertEquals("Expected same property values as String [bytes: [<103>, <111>, <111>, <100>], empty: <false>] but bytes was [].", describeMismatch(samePropertyValuesAs("good"), ""));
        assertEquals("Expected not same property values as String [bytes: [<103>, <111>, <111>, <100>], empty: <false>] but bytes was [<103>, <111>, <111>, <100>] and empty was <false>.", describeMismatch(not(samePropertyValuesAs("good")), "good"));
    }

    //################################################################
    // org.hamcrest.collection (alphabetical order)

    @Test public void testIsArray() {
        assertEquals("Expected array [\"good\", \"bad\", \"ugly\"] but was null.", describeMismatch(array(equalTo("good"), equalTo("bad"), equalTo("ugly")), null));
        assertEquals("Expected array [\"good\", \"bad\", \"ugly\"] but was a java.lang.Integer (<5>).", describeMismatch(array(equalTo("good"), equalTo("bad"), equalTo("ugly")), 5));
        assertEquals("Expected array [\"good\", \"bad\", \"ugly\"] but array length was <2>.", describeMismatch(array(equalTo("good"), equalTo("bad"), equalTo("ugly")), new Object[]{"good", "bad"}));
        assertEquals("Expected array [\"good\", \"bad\", \"ugly\"] but element <2> was \"indifferent\".", describeMismatch(array(equalTo("good"), equalTo("bad"), equalTo("ugly")), new Object[]{"good", "bad", "indifferent"}));
        assertEquals("Expected array [\"good\", \"bad\", not \"ugly\"] but element <2> was \"ugly\".", describeMismatch(array(equalTo("good"), equalTo("bad"), not(equalTo("ugly"))), new Object[]{"good", "bad", "ugly"}));
        assertEquals("Expected not array [\"good\", \"bad\", \"ugly\"] but was [\"good\", \"bad\", \"ugly\"].", describeMismatch(not(array(equalTo("good"), equalTo("bad"), equalTo("ugly"))), new Object[]{"good", "bad", "ugly"}));
    }

    @Test public void testIsArrayContaining() {
        assertEquals("Expected an array containing \"ugly\" but element <0> was \"good\" and element <1> was \"bad\".", describeMismatch(hasItemInArray("ugly"), new Object[]{"good", "bad"}));
        assertEquals("Expected an array containing a string starting with \"ugly\" but element <0> was \"good\" and element <1> was \"bad\".", describeMismatch(hasItemInArray(startsWith("ugly")), new Object[]{"good", "bad"}));
        assertEquals("Expected an array containing not a string starting with \"a\" but element <0> was \"alpha\" and element <1> was \"aleph\" and element <2> was \"apple\".", describeMismatch(hasItemInArray(not(startsWith("a"))), new Object[]{"alpha", "aleph", "apple"}));
        assertEquals("Expected not an array containing \"ugly\" but element <2> was \"ugly\".", describeMismatch(not(hasItemInArray(equalTo("ugly"))), new Object[]{"good", "bad", "ugly"}));
    }

    @Test public void testIsArrayContainingInAnyOrder() {
        assertEquals("Expected array with elements [\"ugly\"] in any order but was null.", describeMismatch(arrayContainingInAnyOrder("ugly"), null));
        assertEquals("Expected array with elements [\"ugly\"] in any order but was a java.lang.Integer (<5>).", describeMismatch(arrayContainingInAnyOrder("ugly"), 5));
        assertEquals("Expected array with elements [\"ugly\"] in any order but array contained <2> elements.", describeMismatch(arrayContainingInAnyOrder("ugly"), new Object[]{"good", "bad"}));
        assertEquals("Expected array with elements [\"ugly\"] in any order but array contained <2> elements.", describeMismatch(arrayContainingInAnyOrder("ugly"), new Object[]{"ugly", "bad"}));
        assertEquals("Expected array with elements [\"ugly\"] in any order but array contained <0> elements.", describeMismatch(arrayContainingInAnyOrder("ugly"), new Object[]{}));
        assertEquals("Expected array with elements [\"ugly\", \"good\"] in any order but element <1> was \"bad\".", describeMismatch(arrayContainingInAnyOrder("ugly", "good"), new Object[]{"good", "bad"}));
        assertEquals("Expected array with elements [an instance of java.lang.Boolean, \"bad\", \"good\"] in any order but element <1> was a java.lang.Integer and was <5>.", describeMismatch(arrayContainingInAnyOrder(instanceOf(Boolean.class), equalTo("bad"), equalTo("good")), new Object[]{"good", 5, "bad"}));
        assertEquals("Expected not array with elements [an instance of java.lang.Boolean, \"bad\", \"good\"] in any order but element <0> was \"good\" and element <1> was a java.lang.Boolean and element <2> was \"bad\".", describeMismatch(not(arrayContainingInAnyOrder(instanceOf(Boolean.class), equalTo("bad"), equalTo("good"))), new Object[]{"good", true, "bad"}));
    }

    @Test public void testIsArrayContainingInOrder() {
        assertEquals("Expected array containing [\"ugly\"] but was null.", describeMismatch(arrayContaining("ugly"), null));
        assertEquals("Expected array containing [\"ugly\"] but was a java.lang.Integer (<5>).", describeMismatch(arrayContaining("ugly"), 5));
        assertEquals("Expected array containing [\"ugly\"] but array contained <2> elements.", describeMismatch(arrayContaining("ugly"), new Object[]{"good", "bad"}));
        assertEquals("Expected array containing [\"ugly\"] but array contained <2> elements.", describeMismatch(arrayContaining("ugly"), new Object[]{"ugly", "bad"}));
        assertEquals("Expected array containing [\"ugly\"] but array contained <0> elements.", describeMismatch(arrayContaining("ugly"), new Object[]{}));
        assertEquals("Expected array containing [\"ugly\", \"good\"] but element <1> was \"bad\".", describeMismatch(arrayContaining("ugly", "good"), new Object[]{"ugly", "bad"}));
        assertEquals("Expected array containing [an instance of java.lang.Boolean, \"bad\", \"good\"] but element <0> was a java.lang.Integer.", describeMismatch(arrayContaining(instanceOf(Boolean.class), equalTo("bad"), equalTo("good")), new Object[]{5, "bad", "good"}));
        assertEquals("Expected not array containing [an instance of java.lang.Boolean, \"bad\", \"good\"] but element <0> was a java.lang.Boolean and element <1> was \"bad\" and element <2> was \"good\".", describeMismatch(not(arrayContaining(instanceOf(Boolean.class), equalTo("bad"), equalTo("good"))), new Object[]{true, "bad", "good"}));
    }

    @Test public void testIsArrayWithSize() {
        assertEquals("Expected an array with size <2> but was null.", describeMismatch(arrayWithSize(2), null));
        assertEquals("Expected an array with size <2> but was a java.lang.Integer (<5>).", describeMismatch(arrayWithSize(2), 5));
        assertEquals("Expected an array with size <2> but array size was <3>.", describeMismatch(arrayWithSize(2), new Object[]{"good", "bad", "ugly"}));
        assertEquals("Expected an array with size greater than <2> but array size was equal to <2>.", describeMismatch(arrayWithSize(greaterThan(2)), new Object[]{"good", "bad"}));
        assertEquals("Expected an array with size (greater than <2> or less than <1>) but array size was equal to <2> and was greater than <1>.", describeMismatch(arrayWithSize(anyOf(greaterThan(2), lessThan(1))), new Object[]{"good", "bad"}));
        assertEquals("Expected not an array with size <2> but array size was <2>.", describeMismatch(not(arrayWithSize(2)), new Object[]{"good", "bad"}));
    }

    @Test public void testIsCollectionWithSize() {
        assertEquals("Expected a collection with size <2> but was null.", describeMismatch(hasSize(2), null));
        assertEquals("Expected a collection with size <2> but was a java.lang.Integer (<5>).", describeMismatch(hasSize(2), 5));
        assertEquals("Expected a collection with size <2> but collection size was <3>.", describeMismatch(hasSize(2), Arrays.asList("good", "bad", "ugly")));
        assertEquals("Expected a collection with size greater than <2> but collection size was equal to <2>.", describeMismatch(hasSize(greaterThan(2)), Arrays.asList("good", "bad")));
        assertEquals("Expected a collection with size (greater than <2> or less than <1>) but collection size was equal to <2> and was greater than <1>.", describeMismatch(hasSize(anyOf(greaterThan(2), lessThan(1))), Arrays.asList("good", "bad")));
        assertEquals("Expected not a collection with size <2> but collection size was <2>.", describeMismatch(not(hasSize(2)), Arrays.asList("good", "bad")));
    }

    @Test public void testIsEmptyCollection() {
        assertEquals("Expected an empty collection but was null.", describeMismatch(empty(), null));
        assertEquals("Expected an empty collection but was a java.lang.Integer (<5>).", describeMismatch(empty(), 5));
        assertEquals("Expected an empty collection but was a collection of size <3>.", describeMismatch(empty(), Arrays.asList("good", "bad", "ugly")));
        assertEquals("Expected not an empty collection but was a collection of size <0>.", describeMismatch(not(empty()), Arrays.asList()));
    }

    @Test public void testIsEmptyIterable() {
        assertEquals("Expected an empty iterable but was null.", describeMismatch(emptyIterable(), null));
        assertEquals("Expected an empty iterable but was a java.lang.Integer (<5>).", describeMismatch(emptyIterable(), 5));
        assertEquals("Expected an empty iterable but was an iterable of size <3>.", describeMismatch(emptyIterable(), Arrays.asList("good", "bad", "ugly")));
        assertEquals("Expected not an empty iterable but was an iterable of size <0>.", describeMismatch(not(emptyIterable()), Arrays.asList()));
    }

    @Test public void testIsIn() {
        assertEquals("Expected one of [\"good\", \"bad\"] but was null.", describeMismatch(oneOf("good", "bad"), null));
        assertEquals("Expected one of [\"good\", \"bad\"] but was <5>.", describeMismatch(oneOf("good", "bad"), 5));
        assertEquals("Expected one of [\"good\", \"bad\"] but was \"ugly\".", describeMismatch(oneOf("good", "bad"), "ugly"));
        assertEquals("Expected not one of [\"good\", \"bad\"] but was \"good\".", describeMismatch(not(oneOf("good", "bad")), "good"));
    }

    @Test public void testIsIterableContainingInAnyOrder() {
        assertEquals("Expected iterable with items [\"ugly\"] in any order but was null.", describeMismatch(containsInAnyOrder("ugly"), null));
        assertEquals("Expected iterable with items [\"ugly\"] in any order but was a java.lang.Integer (<5>).", describeMismatch(containsInAnyOrder("ugly"), 5));
        assertEquals("Expected iterable with items [\"ugly\"] in any order but iterable contained <2> items.", describeMismatch(containsInAnyOrder("ugly"), Arrays.asList("good", "bad")));
        assertEquals("Expected iterable with items [\"ugly\"] in any order but iterable contained <2> items.", describeMismatch(containsInAnyOrder("ugly"), Arrays.asList("ugly", "bad")));
        assertEquals("Expected iterable with items [\"ugly\"] in any order but iterable contained <0> items.", describeMismatch(containsInAnyOrder("ugly"), Arrays.asList()));
        assertEquals("Expected iterable with items [\"ugly\", \"good\"] in any order but item <1> was \"bad\".", describeMismatch(containsInAnyOrder("ugly", "good"), Arrays.asList("good", "bad")));
        assertEquals("Expected iterable with items [an instance of java.lang.Boolean, \"bad\", \"good\"] in any order but item <1> was a java.lang.Integer and was <5>.", describeMismatch(containsInAnyOrder(instanceOf(Boolean.class), equalTo("bad"), equalTo("good")), Arrays.asList("good", 5, "bad")));
        assertEquals("Expected not iterable with items [an instance of java.lang.Boolean, \"bad\", \"good\"] in any order but item <0> was \"good\" and item <1> was a java.lang.Boolean and item <2> was \"bad\".", describeMismatch(not(containsInAnyOrder(instanceOf(Boolean.class), equalTo("bad"), equalTo("good"))), Arrays.asList("good", true, "bad")));
    }

    @Test public void testIsIterableContainingInOrder() {
        assertEquals("Expected iterable containing [\"ugly\"] but was null.", describeMismatch(contains("ugly"), null));
        assertEquals("Expected iterable containing [\"ugly\"] but was a java.lang.Integer (<5>).", describeMismatch(contains("ugly"), 5));
        assertEquals("Expected iterable containing [\"ugly\"] but iterable contained <2> items.", describeMismatch(contains("ugly"), Arrays.asList("good", "bad")));
        assertEquals("Expected iterable containing [\"ugly\"] but iterable contained <2> items.", describeMismatch(contains("ugly"), Arrays.asList("ugly", "bad")));
        assertEquals("Expected iterable containing [\"ugly\"] but iterable contained <0> items.", describeMismatch(contains("ugly"), Arrays.asList()));
        assertEquals("Expected iterable containing [\"ugly\", \"good\"] but item <1> was \"bad\".", describeMismatch(contains("ugly", "good"), Arrays.asList("ugly", "bad")));
        assertEquals("Expected iterable containing [an instance of java.lang.Boolean, \"bad\", \"good\"] but item <0> was a java.lang.Integer.", describeMismatch(contains(instanceOf(Boolean.class), equalTo("bad"), equalTo("good")), Arrays.asList(5, "bad", "good")));
        assertEquals("Expected not iterable containing [an instance of java.lang.Boolean, \"bad\", \"good\"] but item <0> was a java.lang.Boolean and item <1> was \"bad\" and item <2> was \"good\".", describeMismatch(not(contains(instanceOf(Boolean.class), equalTo("bad"), equalTo("good"))), Arrays.asList(true, "bad", "good")));
    }

    @Test public void testIsIterableWithSize() {
        assertEquals("Expected an iterable with size <2> but was null.", describeMismatch(iterableWithSize(2), null));
        assertEquals("Expected an iterable with size <2> but was a java.lang.Integer (<5>).", describeMismatch(iterableWithSize(2), 5));
        assertEquals("Expected an iterable with size <2> but iterable size was <3>.", describeMismatch(iterableWithSize(2), Arrays.asList("good", "bad", "ugly")));
        assertEquals("Expected an iterable with size greater than <2> but iterable size was equal to <2>.", describeMismatch(iterableWithSize(greaterThan(2)), Arrays.asList("good", "bad")));
        assertEquals("Expected an iterable with size (greater than <2> or less than <1>) but iterable size was equal to <2> and was greater than <1>.", describeMismatch(iterableWithSize(anyOf(greaterThan(2), lessThan(1))), Arrays.asList("good", "bad")));
        assertEquals("Expected not an iterable with size <2> but iterable size was <2>.", describeMismatch(not(iterableWithSize(2)), Arrays.asList("good", "bad")));
    }

    @Test public void testIsMapContaining() {
        // hasEntry
        assertEquals("Expected map containing {\"ugly\"=\"bird\"} but was null.", describeMismatch(hasEntry("ugly", "bird"), null));
        assertEquals("Expected map containing {\"ugly\"=\"bird\"} but was a java.lang.Integer (<5>).", describeMismatch(hasEntry("ugly", "bird"), 5));
        assertEquals("Expected map containing {\"ugly\"=\"bird\"} but was <{good=times, bad=news}>.", describeMismatch(hasEntry("ugly", "bird"), mapFromArray(String.class, String.class, "good", "times", "bad", "news")));
        assertEquals("Expected map containing {a string starting with \"a\"=a string starting with \"bird\"} but was <{good=times, bad=news}>.", describeMismatch(hasEntry(startsWith("a"), startsWith("bird")), mapFromArray(String.class, String.class, "good", "times", "bad", "news")));
        assertEquals("Expected not map containing {\"ugly\"=\"bird\"} but was <{good=times, ugly=bird}>.", describeMismatch(not(hasEntry("ugly", "bird")), mapFromArray(String.class, String.class, "good", "times", "ugly", "bird")));

        // hasKey
        assertEquals("Expected map containing {\"ugly\"=ANYTHING} but was <{good=times, bad=news}>.", describeMismatch(hasKey("ugly"), mapFromArray(String.class, String.class, "good", "times", "bad", "news")));
        assertEquals("Expected not map containing {\"ugly\"=ANYTHING} but was <{good=times, ugly=bird}>.", describeMismatch(not(hasKey("ugly")), mapFromArray(String.class, String.class, "good", "times", "ugly", "bird")));
        
        // hasValue
        assertEquals("Expected map containing {ANYTHING=\"bird\"} but was <{good=times, bad=news}>.", describeMismatch(hasValue("bird"), mapFromArray(String.class, String.class, "good", "times", "bad", "news")));
        assertEquals("Expected not map containing {ANYTHING=\"bird\"} but was <{good=times, ugly=bird}>.", describeMismatch(not(hasValue("bird")), mapFromArray(String.class, String.class, "good", "times", "ugly", "bird")));
    }

    @Test public void testIsMapWithSize() {
        assertEquals("Expected a map with size <2> but was null.", describeMismatch(aMapWithSize(2), null));
        assertEquals("Expected a map with size <2> but was a java.lang.Integer (<5>).", describeMismatch(aMapWithSize(2), 5));
        assertEquals("Expected a map with size <2> but map size was <3>.", describeMismatch(aMapWithSize(2), mapFromArray(String.class, String.class, "good", "times", "bad", "news", "ugly", "bird")));
        assertEquals("Expected a map with size greater than <2> but map size was equal to <2>.", describeMismatch(aMapWithSize(greaterThan(2)), mapFromArray(String.class, String.class, "good", "times", "bad", "news")));
        assertEquals("Expected a map with size (greater than <2> or less than <1>) but map size was equal to <2> and was greater than <1>.", describeMismatch(aMapWithSize(anyOf(greaterThan(2), lessThan(1))), mapFromArray(String.class, String.class, "good", "times", "bad", "news")));
        assertEquals("Expected not a map with size <2> but map size was <2>.", describeMismatch(not(aMapWithSize(2)), mapFromArray(String.class, String.class, "good", "times", "bad", "news")));
    }

    //################################################################
    // org.hamcrest.io (alphabetical order)

    //################################################################
    // org.hamcrest.number (alphabetical order)
    
    @Test public void testBigDecimalCloseTo() {
        assertEquals("Expected within <1> of <5> but was null.", describeMismatch(BigDecimalCloseTo.closeTo(new BigDecimal("5"), new BigDecimal("1")), null));
        assertEquals("Expected within <1> of <5> but was a java.lang.String (\"bad\").", describeMismatch(BigDecimalCloseTo.closeTo(new BigDecimal("5"), new BigDecimal("1")), "bad"));
        assertEquals("Expected within <1> of <5> but differed from <5> by <5>.", describeMismatch(BigDecimalCloseTo.closeTo(new BigDecimal("5"), new BigDecimal("1")), new BigDecimal("10")));
        assertEquals("Expected not within <1> of <5> but differed from <5> by <0.2>.", describeMismatch(not(BigDecimalCloseTo.closeTo(new BigDecimal("5"), new BigDecimal("1"))), new BigDecimal("5.2")));
        assertEquals("Expected not within <1> of <5> but differed from <5> by <0>.", describeMismatch(not(BigDecimalCloseTo.closeTo(new BigDecimal("5"), new BigDecimal("1"))), new BigDecimal("5")));
    }

    @Test public void testIsCloseTo() {
        assertEquals("Expected within <1.0> of <5.0> but was null.", describeMismatch(IsCloseTo.closeTo(5, 1), null));
        assertEquals("Expected within <1.0> of <5.0> but was a java.lang.String (\"bad\").", describeMismatch(IsCloseTo.closeTo(5, 1), "bad"));
        assertEquals("Expected within <1.0> of <5.0> but differed from <5.0> by <5.0>.", describeMismatch(IsCloseTo.closeTo(5, 1), 10.0));
        assertEquals("Expected not within <1.0> of <5.0> but differed from <5.0> by <0.20000000000000018>.", describeMismatch(not(IsCloseTo.closeTo(5, 1)), 5.2));
        assertEquals("Expected not within <1.0> of <5.0> but differed from <5.0> by <0.0>.", describeMismatch(not(IsCloseTo.closeTo(5, 1)), 5.0));
    }

    @Test public void testIsNaN() {
        assertEquals("Expected NaN but was null.", describeMismatch(notANumber(), null));
        assertEquals("Expected NaN but was a java.lang.String (\"bad\").", describeMismatch(notANumber(), "bad"));
        assertEquals("Expected NaN but was <5.0>.", describeMismatch(notANumber(), 5.0));
        assertEquals("Expected NaN but was <Infinity>.", describeMismatch(notANumber(), Double.POSITIVE_INFINITY));
        assertEquals("Expected not NaN but was <NaN>.", describeMismatch(not(notANumber()), Double.NaN));
    }

    @Test public void testOrderingComparison() {
        assertEquals("Expected greater than <5> but was null.", describeMismatch(greaterThan(5), null));
        assertEquals("Expected greater than <5> but was a java.lang.String (\"bad\").", describeMismatch(greaterThan(5), "bad"));
        assertEquals("Expected greater than <5> but was a java.lang.Double (<4.0>).", describeMismatch(greaterThan(5), 4.0));
        assertEquals("Expected greater than \"b\" but was less than \"b\".", describeMismatch(greaterThan("b"), "a"));
        assertEquals("Expected greater than <5.0> but was less than <5.0>.", describeMismatch(greaterThan(5.0), 4.0));
        assertEquals("Expected greater than <5.0> but was less than <5.0>.", describeMismatch(greaterThan(new BigDecimal("5.0")), new BigDecimal("4.0")));

        assertEquals("Expected equal to <5> but was less than <5>.", describeMismatch(comparesEqualTo(5), 4));
        assertEquals("Expected not equal to <5> but was equal to <5>.", describeMismatch(not(comparesEqualTo(5)), 5));
        assertEquals("Expected equal to <5> but was greater than <5>.", describeMismatch(comparesEqualTo(5), 6));
        
        assertEquals("Expected greater than <5> but was less than <5>.", describeMismatch(greaterThan(5), 4));
        assertEquals("Expected greater than <5> but was equal to <5>.", describeMismatch(greaterThan(5), 5));
        assertEquals("Expected not greater than <5> but was greater than <5>.", describeMismatch(not(greaterThan(5)), 6));
        
        assertEquals("Expected equal to or greater than <5> but was less than <5>.", describeMismatch(greaterThanOrEqualTo(5), 4));
        assertEquals("Expected not equal to or greater than <5> but was equal to <5>.", describeMismatch(not(greaterThanOrEqualTo(5)), 5));
        assertEquals("Expected not equal to or greater than <5> but was greater than <5>.", describeMismatch(not(greaterThanOrEqualTo(5)), 6));
        
        assertEquals("Expected not less than <5> but was less than <5>.", describeMismatch(not(lessThan(5)), 4));
        assertEquals("Expected less than <5> but was equal to <5>.", describeMismatch(lessThan(5), 5));
        assertEquals("Expected less than <5> but was greater than <5>.", describeMismatch(lessThan(5), 6));
        
        assertEquals("Expected not less than or equal to <5> but was less than <5>.", describeMismatch(not(lessThanOrEqualTo(5)), 4));
        assertEquals("Expected not less than or equal to <5> but was equal to <5>.", describeMismatch(not(lessThanOrEqualTo(5)), 5));
        assertEquals("Expected less than or equal to <5> but was greater than <5>.", describeMismatch(lessThanOrEqualTo(5), 6));
    }

    //################################################################
    // org.hamcrest.object (alphabetical order)

    //################################################################
    // org.hamcrest.text (alphabetical order)

    //################################################################
    // org.hamcrest.xml (alphabetical order)

    //################################################################
    
    private static String describeMismatch(Matcher<?> matcher, Object obj) {
        assertFalse("Expected object not to match.", matcher.matches(obj));
        Description description = new StringDescription();
        description.appendText("Expected ");
        matcher.describeTo(description);
        description.appendText(" but ");
        matcher.describeMismatch(obj, description);
        description.appendText(".");
        return description.toString();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private static <K, V> Map<K, V> mapFromArray(Class<K> typeK, Class<V> typeV, Object... data) {
        if (null==data) { throw new IllegalArgumentException("Required null!=data, instead data=null."); }
        if (0!=data.length%2) { throw new IllegalArgumentException("Required data.length is even, instead data.length="+data.length+"."); }
        Map<K, V> map=new LinkedHashMap<K, V>(((data.length/2)+1)*4/3);
        for (int nIndex=0; nIndex<data.length; nIndex+=2) {
            Object key=data[nIndex];
            if (null!=key && !typeK.isInstance(key)) { throw new IllegalArgumentException("Required data["+nIndex+"] instanceOf "+typeK+", instead data["+nIndex+"].class="+key.getClass()+"."); }
            if (map.containsKey(key)) { throw new IllegalArgumentException("Required !map.containsKey(data["+nIndex+"]), instead data["+nIndex+"]="+key+"."); }
            Object value=data[nIndex+1];
            if (null!=value && !typeV.isInstance(value)) { throw new IllegalArgumentException("Required data["+nIndex+"] instanceOf "+typeV+", instead data["+nIndex+"].class="+value.getClass()+"."); }
            map.put((K)key, (V)value);
        }
        return map;
    }
}
