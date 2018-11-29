package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class HasEqualValues<T> extends TypeSafeDiagnosingMatcher<T> {
    private final T expectedObject;
    private final List<FieldMatcher> fieldMatchers;

    public HasEqualValues(T expectedObject) {
        super(expectedObject.getClass());
        this.expectedObject = expectedObject;
        this.fieldMatchers = fieldMatchers(expectedObject);
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

    private static List<FieldMatcher> fieldMatchers(Object expectedObject) {
        final List<FieldMatcher> result = new ArrayList<>();
        for (Field field : expectedObject.getClass().getFields()) {
            result.add(new FieldMatcher(field, expectedObject));
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


}
