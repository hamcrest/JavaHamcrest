package org.hamcrest;


/**
 * Utility class for writing one off matchers.
 * For example:
 * <pre>
 * Matcher&lt;String&gt; aNonEmptyString = new CustomTypeSafeMatcher&lt;String&gt;("a non empty string") {
 *   public boolean matchesSafely(String string) {
 *     return !string.isEmpty();
 *   }
 *   public void describeMismatchSafely(String string, Description mismatchDescription) {
 *     mismatchDescription.appendText("was empty");
 *   }
 * };
 * </pre>
 * This is a variant of {@link CustomMatcher} that first type checks
 * the argument being matched. By the time {@link TypeSafeMatcher#matchesSafely} is
 * is called the argument is guaranteed to be non-null and of the correct
 * type.
 *
 * @author Neil Dunn
 * @param <T> The type of object being matched
 */
public abstract class CustomTypeSafeMatcher<T> extends TypeSafeMatcher<T> {
    private final String fixedDescription;

    public CustomTypeSafeMatcher(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description must be non null!");
        }
        this.fixedDescription = description;
    }

    @Override
    public final void describeTo(Description description) {
        description.appendText(fixedDescription);
    }
}
