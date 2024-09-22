package org.hamcrest.collection;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsCollectionWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasSize(7);
    }

    @Test
    public void testMatchesWhenSizeIsCorrect() {
        assertMatches("correct size", hasSize(equalTo(2)), asList(null, null));
        assertMismatchDescription("collection size was <3>", hasSize(equalTo(2)), asList(null, null, null));
    }

    @Test
    public void testMatchesCollectionWhenSizeIsCorrectUsingObjectElementType() {
        Collection<Object> list = asList(null, null);
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    @Test
    public void testMatchesCollectionWhenSizeIsCorrectUsingStringElementType() {
        Collection<String> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    @Test
    public void testMatchesCollectionWhenSizeIsCorrectUsingWildcardElementType() {
        Collection<?> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    @Test
    public void testMatchesListWhenSizeIsCorrectUsingObjectElementType() {
        List<Object> list = asList(null, null);
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    @Test
    public void testMatchesListWhenSizeIsCorrectUsingStringElementType() {
        List<String> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    @Test
    public void testMatchesListWhenSizeIsCorrectUsingWildcardElementType() {
        List<?> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    @Test
    public void testProvidesConvenientShortcutForHasSizeEqualTo() {
        assertMatches("correct size", hasSize(2), asList(null, null));
        assertMismatchDescription("collection size was <3>", hasSize(2), asList(null, null, null));
    }

    @Test
    public void testHasAReadableDescription() {
        assertDescription("a collection with size <3>", hasSize(equalTo(3)));
    }

    @Test
    public void testCompilesWithATypedCollection() {
      // To prove Issue 43
      ArrayList<String> arrayList = new ArrayList<>();
      MatcherAssert.assertThat(arrayList, hasSize(0));
    }

}
