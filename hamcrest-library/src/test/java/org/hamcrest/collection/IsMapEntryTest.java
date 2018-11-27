package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.AbstractMap;
import java.util.Map;

import static org.hamcrest.collection.IsMapEntry.entry;

public class IsMapEntryTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return entry(Matchers.equalTo("key"), Matchers.equalTo(23));
    }

    public void testDoesNotMatchNull() {
        assertMismatchDescription("was null", entry(Matchers.equalTo(23), Matchers.equalTo("key")), null);
    }

    public void testDoesNotMatchAnEntryWithTheWrongKey() {
        assertMismatchDescription("key was \"jey\"", entry(Matchers.equalTo("key"), Matchers.equalTo(23)), new AbstractMap.SimpleEntry<>("jey", 23));
    }

    public void testDoesNotMatchAnEntryWithTheWrongValue() {
        assertMismatchDescription("value was <24>", entry(Matchers.equalTo("key"), Matchers.equalTo(23)), new AbstractMap.SimpleEntry<>("key", 24));
    }

    public void testDoesNotMatchAnEntryWithTheWrongKeyAndValue() {
        assertMismatchDescription("key was \"jey\" and value was <24>", entry(Matchers.equalTo("key"), Matchers.equalTo(23)), new AbstractMap.SimpleEntry<>("jey", 24));
    }

    public void testMatchesAnEntryWithTheRightKeyAndValue() {
        assertMatches(entry(Matchers.equalTo("key"), Matchers.equalTo(23)), new AbstractMap.SimpleEntry<>("key", 23));
    }

    public void testHasReadableDescription() {
        assertDescription("an entry with key \"key\" and value a value greater than <22>", entry(Matchers.equalTo("key"), Matchers.greaterThan(22)));
    }

    public void testCanCreateWithLiteralKey() {
        Matcher<? super Map.Entry<? extends String, ? extends Integer>> matcher = IsMapEntry.entry("key", Matchers.greaterThan(22));
        assertMatches(matcher, new AbstractMap.SimpleEntry<>("key", 23));
        assertDescription("an entry with key \"key\" and value a value greater than <22>", matcher);
    }

    public void testCanCreateWithKeyOnly() {
        Matcher<? super Map.Entry<? extends String, ?>> matcher = IsMapEntry.entry(Matchers.equalTo("key"));
        assertMatches(matcher, new AbstractMap.SimpleEntry<>("key", 99));
        assertDescription("an entry with key \"key\"", matcher);
    }

    public void testCanCreateWithLiteralKeyOnly() {
        Matcher<? super Map.Entry<? extends String, ?>> matcher = IsMapEntry.entry("key");
        assertMatches(matcher, new AbstractMap.SimpleEntry<>("key", 99));
        assertDescription("an entry with key \"key\"", matcher);
    }

}
