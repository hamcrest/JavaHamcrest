package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;

public class IsCollectionContainingTest extends AbstractMatcherTest<Iterable<String>> {
    @Override
    protected Matcher<Iterable<String>> createMatcher() {
        return hasItem(equalTo("irrelevant"));
    }

    public void testMatchesACollectionThatContainsAnElementMatchingTheGivenMatcher() {
        Matcher<Iterable<String>> itemMatcher = hasItem(equalTo("a"));
        
        assertMatches("should match list that contains 'a'",
                itemMatcher, asList("a", "b", "c"));
    }

    public void testDoesNotMatchCollectionThatDoesntContainAnElementMatchingTheGivenMatcher() {
        final Matcher<Iterable<String>> matcher1 = hasItem(mismatchable("a"));
        assertMismatchDescription("was a collection that did not contain mismatchable: a -- mismatches were: [mismatched: b, mismatched: c]",
                                  matcher1, asList("b", "c"));
        
        final Matcher<Iterable<String>> matcher2 = hasItem(equalTo("a"));
        assertMismatchDescription("was an empty collection", matcher2, new ArrayList<String>());
    }

    public void testDoesNotMatchNull() {
        assertDoesNotMatch("should not matches null", hasItem(equalTo("a")), null);
    }

    public void testHasAReadableDescription() {
        assertDescription("a collection containing \"a\"", hasItem(equalTo("a")));
    }
    
    public void testCanMatchItemWhenCollectionHoldsSuperclass() // Issue 24
    {
      final Set<Number> s = new HashSet<Number>();
      s.add(Integer.valueOf(2));
      assertThat(s, new IsCollectionContaining<Collection<Number>>(new IsEqual<Number>(Integer.valueOf(2))));
      assertThat(s, IsCollectionContaining.hasItem((Number) Integer.valueOf(2)));
    }

    public void testMatchesAllItemsInCollection() {
        final Matcher<Iterable<String>> matcher1 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items",
                matcher1,
                asList("a", "b", "c"));
        
        final Matcher<Iterable<String>> matcher2 = hasItems("a", "b", "c");
        assertMatches("should match list containing all items (without matchers)",
                matcher2,
                asList("a", "b", "c"));
        
        final Matcher<Iterable<String>> matcher3 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items in any order",
                matcher3,
                asList("c", "b", "a"));
        
        final Matcher<Iterable<String>> matcher4 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertMatches("should match list containing all items plus others",
                matcher4,
                asList("e", "c", "b", "a", "d"));
        
        final Matcher<Iterable<String>> matcher5 = hasItems(equalTo("a"), equalTo("b"), equalTo("c"));
        assertDoesNotMatch("should not match list unless it contains all items",
                matcher5,
                asList("e", "c", "b", "d")); // 'a' missing
    }
    
    public void testReportsMismatchWithAReadableDescription() {
        final Matcher<Iterable<Integer>> matcher = hasItems(3, 4);
        
        assertMismatchDescription("a collection containing <4> was a collection that did not contain <4> -- mismatches were: [was <1>, was <2>, was <3>]",
                                  matcher, asList(1, 2, 3));
    }
    
    private static Matcher<? super String> mismatchable(final String string) {
      return new TypeSafeDiagnosingMatcher<String>() {
        @Override
        protected boolean matchesSafely(String item, Description mismatchDescription) {
          if (string.equals(item)) 
            return true;
          
          mismatchDescription.appendText("mismatched: " + item);
          return false;
        }

        @Override
        public void describeTo(Description description) {
          description.appendText("mismatchable: " + string);
        }
      };
    }
}

