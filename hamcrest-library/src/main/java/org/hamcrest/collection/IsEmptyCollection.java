package org.hamcrest.collection;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Tests if collection is empty.
 */
public class IsEmptyCollection<E> extends TypeSafeMatcher<Collection<? extends E>> {

    @Override
    public boolean matchesSafely(Collection<? extends E> item) {
        return item.isEmpty();
    }

    @Override
    public void describeMismatchSafely(Collection<? extends E> item, Description mismatchDescription) {
      mismatchDescription.appendValue(item);
    }

  @Override
  public void describeTo(Description description) {
        description.appendText("an empty collection");
    }

  /**
   * Creates a matcher for {@link Collection}s matching examined collections whose <code>isEmpty</code>
   * method returns <code>true</code>.
   * <p/>
   * For example:
   * <pre>assertThat(new ArrayList&lt;String&gt;(), is(empty()))</pre>
   * 
   */
    @Factory
    public static <E> Matcher<Collection<? extends E>> empty() {
        return new IsEmptyCollection<E>();
    }
}
