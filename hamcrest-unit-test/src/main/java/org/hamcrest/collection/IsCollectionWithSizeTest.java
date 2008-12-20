package org.hamcrest.collection;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

public class IsCollectionWithSizeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasSize(7);
    }

    public void testMatchesWhenSizeIsCorrect() {
        assertMatches("correct size", hasSize(equalTo(2)), asList(null, null));
        assertMismatchDescription("collection size was <3>", hasSize(equalTo(2)), asList(null, null, null));
    }

    public void testMatchesCollectionWhenSizeIsCorrectUsingObjectElementType() {
        Collection<Object> list = asList(null, null);
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesCollectionWhenSizeIsCorrectUsingStringElementType() {
        Collection<String> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesCollectionWhenSizeIsCorrectUsingWildcardElementType() {
        Collection<?> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesListWhenSizeIsCorrectUsingObjectElementType() {
        List<Object> list = asList(null, null);
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesListWhenSizeIsCorrectUsingStringElementType() {
        List<String> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testMatchesListWhenSizeIsCorrectUsingWildcardElementType() {
        List<?> list = asList("a", "b");
        assertMatches("correct size", hasSize(equalTo(2)), list);
        assertMismatchDescription("collection size was <2>", hasSize(equalTo(3)), list);
    }

    public void testProvidesConvenientShortcutForHasSizeEqualTo() {
        assertMatches("correct size", hasSize(2), asList(null, null));
        assertMismatchDescription("collection size was <3>", hasSize(2), asList(null, null, null));
    }

    public void testHasAReadableDescription() {
        assertDescription("a collection with size <3>", hasSize(equalTo(3)));
    }
    
    public void testCompilesWithATypedCollection() {
      // To prove Issue 43
      ArrayList<String> arrayList = new ArrayList<String>();
      MatcherAssert.assertThat(arrayList, hasSize(0));
    }
}
