package org.hamcrest.function;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static java.util.Objects.requireNonNull;

/**
 * <p>
 * Matcher which first transforms the matched object and hands over the comparison to the
 * delegate matcher.
 * </p>
 * <dl>
 * <dt><strong>Note:</strong></dt>
 * <dd>
 * In order not to report a different value on failure than used for comparison the previously
 * transformed value is stored per thread when matching is tried. As a consequence
 * {@link #describeMismatchSafely(Object, Description)} ignores the item expecting that it did
 * not change meanwhile.
 * </dd>
 * </dl>
 *
 * @param <F> type of the actual value in the assertion; typically the component under test
 * @param <T> type of the actual value to compare; typically the state of the component under test
 */
public class ApplyingMatcher<F, T> extends TypeSafeMatcher<F> {
    private final ThreadLocal<T> lastValue = new ThreadLocal<>();
    private final Function<F, T> function;
    private final Matcher<? super T> delegateMatcher;

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param function        the function to apply to convert the matched object to the target value
     * @param delegateMatcher matcher to apply to the transformed value
     */
    public ApplyingMatcher(Function<F, T> function, Matcher<? super T> delegateMatcher) {
        this.function = requireNonNull(function, "Function must not be null.");
        this.delegateMatcher = requireNonNull(delegateMatcher, "Delegate matcher must not be null.");
    }

    @Override
    protected boolean matchesSafely(F item) {
        lastValue.set(function.apply(item));
        return delegateMatcher.matches(lastValue.get());
    }

    @Override
    public void describeTo(Description description) {
        delegateMatcher.describeTo(description);
    }

    @Override
    protected void describeMismatchSafely(F item, Description mismatchDescription) {
        // Ignoring item, expecting that it did not change between call to describeMismatch and matches.
        delegateMatcher.describeMismatch(lastValue.get(), mismatchDescription);
    }

    /**
     * <p>
     * Applies a transformation to the value before comparing the transformed result with the given matcher.
     * </p>
     *
     * @param function        the function to apply to convert the asserted value to the target value
     * @param delegateMatcher matcher to apply to the transformed value; typically the state of the component under test
     * @param <F>             type to input into assertion
     * @param <T>             actual value type to compare
     * @return matcher which transforms input before comparison
     */
    public static <F, T> Matcher<F> applying(Function<F, T> function,
                                             Matcher<? super T> delegateMatcher) {
        return new ApplyingMatcher<>(function, delegateMatcher);
    }

}
