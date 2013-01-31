package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsCollectionContaining<T> extends TypeSafeDiagnosingMatcher<Iterable<? super T>> {
    private final Matcher<? super T> elementMatcher;

    public IsCollectionContaining(Matcher<? super T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    @Override
    protected boolean matchesSafely(Iterable<? super T> collection, Description mismatchDescription) {
        if (isEmpty(collection)) {
          mismatchDescription.appendText("was empty");
          return false;
        }

        StringDescription stringDescription=new StringDescription();
        int count=0;
        for (Object item : collection) {
            if (elementMatcher.matches(item)) {
                mismatchDescription.appendText("item ").appendValue(count).appendText(" ");
                elementMatcher.describeMismatch(item, mismatchDescription);
                return true;
            }
            if (count!=0) { stringDescription.appendText(" and "); }
            stringDescription.appendText("item ").appendValue(count).appendText(" ");
            elementMatcher.describeMismatch(item, stringDescription);
            count++;
        }
        mismatchDescription.appendText(stringDescription.toString());
        return false;
    }

    private boolean isEmpty(Iterable<? super T> iterable) {
      return ! iterable.iterator().hasNext();
    }

    @Override
    public void describeTo(Description description) {
        description
            .appendText("a collection containing ")
            .appendDescriptionOf(elementMatcher);
    }

    
    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is matched by the specified
     * <code>itemMatcher</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem(startsWith("ba")))</pre>
     * 
     * @param itemMatcher
     *     the matcher to apply to items provided by the examined {@link Iterable}
     */
    @Factory
    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> itemMatcher) {
        return new IsCollectionContaining<T>(itemMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is equal to the specified
     * <code>item</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem("bar"))</pre>
     * 
     * @param item
     *     the item to compare against the items provided by the examined {@link Iterable}
     */
    @Factory
    public static <T> Matcher<Iterable<? super T>> hasItem(T item) {
        // Doesn't forward to hasItem() method so compiler can sort out generics.
        return new IsCollectionContaining<T>(equalTo(item));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is matched by the corresponding
     * matcher from the specified <code>itemMatchers</code>.  Whilst matching, each traversal of
     * the examined {@link Iterable} will stop as soon as a matching item is found.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))</pre>
     * 
     * @param itemMatchers
     *     the matchers to apply to items provided by the examined {@link Iterable}
     */
    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... itemMatchers) {
        List<Matcher<? super Iterable<T>>> all = new ArrayList<Matcher<? super Iterable<T>>>(itemMatchers.length);
        
        for (Matcher<? super T> elementMatcher : itemMatchers) {
          // Doesn't forward to hasItem() method so compiler can sort out generics.
          all.add(new IsCollectionContaining<T>(elementMatcher));
        }
        
        return allOf(all);
    }
    
    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is equal to the corresponding
     * item from the specified <code>items</code>.  Whilst matching, each traversal of the
     * examined {@link Iterable} will stop as soon as a matching item is found.
     * <p/>
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"))</pre>
     * 
     * @param items
     *     the items to compare against the items provided by the examined {@link Iterable}
     */
    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(T... items) {
        List<Matcher<? super Iterable<T>>> all = new ArrayList<Matcher<? super Iterable<T>>>(items.length);
        for (T item : items) {
            all.add(hasItem(item));
        }
        
        return allOf(all);
    }

}
