package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

import java.lang.reflect.Field;
import java.util.*;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class HasEqualValues<T> extends TypeSafeDiagnosingMatcher<T> {

    private final T expectedObject;
    private final List<FieldMatcher> fieldMatchers;

    public HasEqualValues(T expectedObject) {
        this(expectedObject, Collections.<String>emptySet());
    }

    public HasEqualValues(T expectedObject, Set<String> ignored) {
        super(expectedObject.getClass());
        this.expectedObject = expectedObject;
        this.fieldMatchers = fieldMatchers(expectedObject, ignored);
    }

    @Override
    protected boolean matchesSafely(T item, Description mismatch) {
        for (FieldMatcher fieldMatcher : fieldMatchers) {
            if (!fieldMatcher.matches(item, mismatch)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expectedObject.getClass().getSimpleName())
                .appendText(" has values ")
                .appendList("[", ", ", "]", fieldMatchers);
    }

    private static class FieldMatcher extends DiagnosingMatcher<Object> {
        private final Field field;
        private final Matcher<Object> matcher;

        public FieldMatcher(Field field, Object expectedObject) {
            this.field = field;
            this.matcher = IsEqual.equalTo(uncheckedGet(field, expectedObject));
        }
        @Override
        protected boolean matches(Object item, Description mismatch) {
            final Object actual = uncheckedGet(field, item);
            if (!matcher.matches(actual)) {
                mismatch.appendText("'").appendText(field.getName()).appendText("' ");
                matcher.describeMismatch(actual, mismatch);
                return false;
            }
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(field.getName())
                    .appendText(": ")
                    .appendDescriptionOf(matcher);
        }
    }

    private static List<FieldMatcher> fieldMatchers(Object expectedObject, Set<String> ignored) {
        final List<FieldMatcher> result = new ArrayList<>();
        for (Field field : expectedObject.getClass().getFields()) {
            if (!ignored.contains(field.getName())) {
                result.add(new FieldMatcher(field, expectedObject));
            }
        }
        return result;
    }

    private static Object uncheckedGet(Field field, Object object) {
        try {
            return field.get(object);
        } catch (Exception e) {
            throw new AssertionError(format("IllegalAccess, reading field '%s' from %s", field.getName(), object));
        }
    }

    /**
     * Creates a matcher that matches when the examined object has values for all of
     * its fields that are equal to the corresponding values of the
     * specified object. If any fields are marked as ignored, they will be dropped from
     * both the expected and actual object.
     * For example:
     * <pre>assertThat(myObject, hasEqualValues(myExpectedObject))</pre>
     * <pre>assertThat(myObject, hasEqualValues(myExpectedObject), "age", "height")</pre>
     *
     * @param expectedObject
     *     the object against which examined objects are compared
     * @param ignoredFields
     *     do not check any of these named fields.
     */
    public static <B> Matcher<B> hasEqualValues(B expectedObject, String... ignoredFields) {
        return new HasEqualValues<>(expectedObject, new HashSet<>(asList(ignoredFields)));
    }
}
