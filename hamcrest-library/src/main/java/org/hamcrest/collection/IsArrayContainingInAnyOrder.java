package org.hamcrest.collection;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsArrayContainingInAnyOrder<E> extends TypeSafeMatcher<E[]> {
    private final IsIterableContainingInAnyOrder<E> iterableMatcher;
    private final Collection<Matcher<? super E>> matchers;

    public IsArrayContainingInAnyOrder(Collection<Matcher<? super E>> matchers) {
        this.iterableMatcher = new IsIterableContainingInAnyOrder<E>(matchers);
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(E[] item) {
        return iterableMatcher.matches(Arrays.asList(item));
    }
    
    @Override
    public void describeMismatchSafely(E[] item, Description mismatchDescription) {
      iterableMatcher.describeMismatch(Arrays.asList(item), mismatchDescription);
    };

    public void describeTo(Description description) {
        description.appendList("[", ", ", "]", matchers)
            .appendText(" in any order");
    }

    @Factory
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... matchers) {
        return arrayContainingInAnyOrder(Arrays.asList(matchers));
    }


    @Factory
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> matchers) {
        return new IsArrayContainingInAnyOrder<E>(matchers);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... items) {
      List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
      for (E item : items) {
          matchers.add(equalTo(item));
      }
      return new IsArrayContainingInAnyOrder<E>(matchers);
    }
}
