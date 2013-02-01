package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsIterableContainingInOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;
    private final String itemName;
    private final String collectionName;

    public IsIterableContainingInOrder(List<Matcher<? super E>> matchers) {
        this(matchers, "iterable", "item");
    }

    public IsIterableContainingInOrder(List<Matcher<? super E>> matchers, String collectionName, String itemName) {
        this.matchers = matchers;
        this.itemName=itemName;
        this.collectionName=collectionName;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> iterable, Description mismatchDescription) {
        return new Matching(iterable, mismatchDescription).getResult();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(collectionName).appendText(" containing ").appendList("[", ", ", "]", matchers);
    }

    private class Matching {
        private final Iterator<Matcher<? super E>> matcherIterator;
        private final Description mismatchDescription;
        private final Description matchDescription=new StringDescription();
        private int itemsCount;
        private E unmatchedItem;
        private int unmatchedItemIndex;
        private Matcher<? super E> unmatchedMatcher;
        private boolean globalMatch;

        public Matching(Iterable<? extends E> iterable, Description mismatchDescription) {
            this.mismatchDescription=mismatchDescription;

            globalMatch=true;
            matcherIterator=matchers.iterator();
            for (E item : iterable) {
                if (globalMatch) {
                    tryMatch(item);
                }
                itemsCount++;
            }
            finish();
        }

        public void tryMatch(E item) {
            if (!matcherIterator.hasNext()) {
                globalMatch=false;
                return;
            }
            Matcher<? super E> matcher=matcherIterator.next();
            if (matcher.matches(item)) {
                if (itemsCount>0) {
                    matchDescription.appendText(" and ");
                }
                matchDescription.appendText(itemName).appendText(" ").appendValue(itemsCount).appendText(" ");
                matcher.describeMismatch(item, matchDescription);

            } else {
                globalMatch=false;
                unmatchedItem=item;
                unmatchedItemIndex=itemsCount;
                unmatchedMatcher=matcher;
                // describe later after we know the lengths match
            }
        }

        public void finish() {
            if (matchers.size()!=itemsCount) {
                globalMatch=false;
                mismatchDescription.appendText(collectionName).appendText(" contained ").appendValue(itemsCount).appendText(" ").appendText(itemName).appendText("s");

            } else if (globalMatch) {
                mismatchDescription.appendText(matchDescription.toString());

            } else {
                mismatchDescription.appendText(itemName).appendText(" ").appendValue(unmatchedItemIndex).appendText(" ");
                unmatchedMatcher.describeMismatch(unmatchedItem, mismatchDescription);
            }
        }

        public boolean getResult() {
            return globalMatch;
        }
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each logically equal to the
     * corresponding item in the specified items.  For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"))</pre>
     * 
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable}
     */
    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }

        return contains(matchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a single item that satisfies the specified matcher.
     * For a positive match, the examined iterable must only yield one item.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo"), contains(equalTo("foo")))</pre>
     * 
     * @param itemMatcher
     *     the matcher that must be satisfied by the single item provided by an
     *     examined {@link Iterable}
     */
    @SuppressWarnings("unchecked")
    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(final Matcher<? super E> itemMatcher) {
        return contains(new ArrayList<Matcher<? super E>>(asList(itemMatcher)));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified matchers.  For a positive match, the examined iterable
     * must be of the same length as the number of specified matchers.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(equalTo("foo"), equalTo("bar")))</pre>
     * 
     * @param itemMatchers
     *     the matchers that must be satisfied by the items provided by an examined {@link Iterable}
     */
    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... itemMatchers) {
        return contains(asList(itemMatchers));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified list of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified list of matchers.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     * 
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the corresponding item provided by
     *     an examined {@link Iterable}
     */
    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> itemMatchers) {
        return new IsIterableContainingInOrder<E>(itemMatchers);
    }
}
