package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingInAnyOrder<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final Collection<Matcher<? super T>> matchers;
    private final String itemName;
    private final String collectionName;

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> matchers) {
        this(matchers, "iterable", "item");
    }

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> matchers, String collectionName, String itemName) {
        this.matchers=matchers;
        this.itemName=itemName;
        this.collectionName=collectionName;
    }
    
    @Override
    protected boolean matchesSafely(Iterable<? extends T> items, Description mismatchDescription) {
        return new Matching(items, mismatchDescription).getResult();
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText(collectionName).appendText(" with ").appendText(itemName).appendText("s ")
            .appendList("[", ", ", "]", matchers)
            .appendText(" in any order");
    }

    private class Matching {
        private final Collection<Matcher<? super T>> remainingMatchers;
        private final Description mismatchDescription;
        private final Description matchDescription=new StringDescription();
        private final int matchersCount;
        private int itemsCount;
        private T unmatchedItem;
        private int unmatchedItemIndex;
        private boolean globalMatch;

        public Matching(Iterable<? extends T> items, Description mismatchDescription) {
            remainingMatchers=new ArrayList<Matcher<? super T>>(matchers);
            this.mismatchDescription=mismatchDescription;
            matchersCount=matchers.size();

            globalMatch=true;
            for (T item : items) {
                if (globalMatch) { 
                    tryMatch(item); 
                }
                itemsCount++;
            }
            finish();
        }

        private void tryMatch(T item) {
            if (remainingMatchers.isEmpty()) {
                globalMatch=false;
                return;
            }

            for (Matcher<? super T>  matcher : remainingMatchers) {
                if (matcher.matches(item)) {
                    remainingMatchers.remove(matcher);
                    if (itemsCount>0) {
                        matchDescription.appendText(" and "); 
                    }
                    matchDescription.appendText(itemName).appendText(" ").appendValue(itemsCount).appendText(" ");
                    matcher.describeMismatch(item, matchDescription);
                    return;
                }
            }
            globalMatch=false;
            unmatchedItem=item;
            unmatchedItemIndex=itemsCount;
        }

        private void finish() {
            if (matchersCount!=itemsCount) {
                globalMatch=false;
                mismatchDescription.appendText(collectionName).appendText(" contained ").appendValue(itemsCount).appendText(" ").appendText(itemName).appendText("s");
                
            } else if (globalMatch) {
                mismatchDescription.appendText(matchDescription.toString());
                
            } else {
                mismatchDescription.appendText(itemName).appendText(" ").appendValue(unmatchedItemIndex).appendText(" ");
                Set<String> descriptions=new LinkedHashSet<String>();
                for (Matcher<? super T>  matcher : remainingMatchers) {
                    StringDescription stringDescription=new StringDescription();
                    matcher.describeMismatch(unmatchedItem, stringDescription);
                    descriptions.add(stringDescription.toString());
                }
                boolean first=true;
                for (String description : descriptions) {
                    if (first) { 
                        first=false; 
                    } else { 
                        mismatchDescription.appendText(" and "); 
                    }
                    mismatchDescription.appendText(description);
                }
            }
        }       

        public boolean getResult() {
            return globalMatch;
        }
    }

    /**
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified matchers.  For a positive match, the examined iterable must be of the same
     * length as the number of specified matchers.
     * <p/>
     * N.B. each of the specified matchers will only be used once during a given examination, so be
     * careful when specifying matchers that may be satisfied by more than one entry in an examined
     * iterable.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
        return containsInAnyOrder(Arrays.asList(itemMatchers));
    }

    /**
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each logically equal to one item
     * anywhere in the specified items. For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * <p/>
     * N.B. each of the specified items will only be used once during a given examination, so be
     * careful when specifying items that may be equal to more than one entry in an examined
     * iterable.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder("bar", "foo"))</pre>
     * 
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable} in any order
     */
    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        List<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>();
        for (T item : items) {
            matchers.add(equalTo(item));
        }
        
        return new IsIterableContainingInAnyOrder<T>(matchers);
    }

    /**
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified collection of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified collection of matchers.
     * <p/>
     * N.B. each matcher in the specified collection will only be used once during a given
     * examination, so be careful when specifying matchers that may be satisfied by more than
     * one entry in an examined iterable.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return new IsIterableContainingInAnyOrder<T>(itemMatchers);
    }
}

