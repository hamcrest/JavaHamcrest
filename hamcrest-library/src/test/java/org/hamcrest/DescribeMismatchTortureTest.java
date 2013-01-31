package org.hamcrest;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
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
        assertEquals("Expected (\"good\" or \"bad\" or \"ugly\") but was \"other\".", describeMismatch(anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly")), "other"));
        assertEquals("Expected (\"good\" or an instance of java.lang.Number) but was \"other\" and was a java.lang.String.", describeMismatch(anyOf(equalTo("good"), instanceOf(Number.class)), "other"));
        assertEquals("Expected not (\"good\" or \"bad\" or \"ugly\") but was \"bad\".", describeMismatch(not(anyOf(equalTo("good"), equalTo("bad"), equalTo("ugly"))), "bad"));
        assertEquals("Expected not (\"good\" or an instance of java.lang.Number) but was <5L> and was a java.lang.Long.", describeMismatch(not(anyOf(equalTo("good"), instanceOf(Number.class))), 5L));
    }

    @Test public void testAllOf() {
        assertEquals("Expected (\"bad\" and \"good\") but was \"bad\".", describeMismatch(allOf(equalTo("bad"), equalTo("good")), "bad"));
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
        assertEquals("Expected \"good\" but was \"other\".", describeMismatch(equalTo("good"), "other"));
        assertEquals("Expected not \"good\" but was \"good\".", describeMismatch(not(equalTo("good")), "good"));
    }

    @Test public void testIsInstanceOf() {
        assertEquals("Expected \"good\" but was \"other\".", describeMismatch(equalTo("good"), "other"));
        assertEquals("Expected not \"good\" but was \"good\".", describeMismatch(not(equalTo("good")), "good"));
    }

    //@Test public void testIsNot() {
    //    //   plenty of tests elsewhere
    //}

    @Test public void testIsNull() {
        assertEquals("Expected null but was \"other\".", describeMismatch(nullValue(), "other"));
        assertEquals("Expected not null but was null.", describeMismatch(not(nullValue()), null));
    }

    @Test public void testIsSame() {
        assertEquals("Expected sameInstance(\"good\") but was \"other\".", describeMismatch(sameInstance("good"), "other"));
        assertEquals("Expected sameInstance(\"good\") but was null.", describeMismatch(sameInstance("good"), null));
        assertEquals("Expected not sameInstance(<0>) but was <0>.", describeMismatch(not(sameInstance(0)), 0));
    }

    @Test public void testStringContains() {
        assertEquals("Expected a string containing \"good\" but was \"bad\".", describeMismatch(containsString("good"), "bad"));
        assertEquals("Expected a string containing \"good\" but was null.", describeMismatch(containsString("good"), null));
        assertEquals("Expected a string containing \"good\" but was a java.lang.Integer (<5>).", describeMismatch(containsString("good"), 5));
        assertEquals("Expected not a string containing \"good\" but was \"oh my goodness\".", describeMismatch(not(containsString("good")), "oh my goodness"));
    }

    @Test public void testStringEndsWith() {
        assertEquals("Expected a string ending with \"good\" but was \"other\".", describeMismatch(endsWith("good"), "other"));
        assertEquals("Expected not a string ending with \"good\" but was \"very good\".", describeMismatch(not(endsWith("good")), "very good"));
    }

    @Test public void testStringStartsWith() {
        assertEquals("Expected a string starting with \"good\" but was \"other\".", describeMismatch(startsWith("good"), "other"));
        assertEquals("Expected not a string starting with \"good\" but was \"goodness\".", describeMismatch(not(startsWith("good")), "goodness"));
    }

    //################################################################
    // org.hamcrest.beans (alphabetical order)

    //################################################################
    // org.hamcrest.collection (alphabetical order)

    //################################################################
    // org.hamcrest.io (alphabetical order)

    //################################################################
    // org.hamcrest.number (alphabetical order)

    //################################################################
    // org.hamcrest.object (alphabetical order)

    //################################################################
    // org.hamcrest.text (alphabetical order)

    //################################################################
    // org.hamcrest.xml (alphabetical order)

    //################################################################
    
    private String describeMismatch(Matcher<?> matcher, Object obj) {
        assertFalse("Expected object not to match.", matcher.matches(obj));
        Description description = new StringDescription();
        description.appendText("Expected ");
        matcher.describeTo(description);
        description.appendText(" but ");
        matcher.describeMismatch(obj, description);
        description.appendText(".");
        return description.toString();
    }
}
