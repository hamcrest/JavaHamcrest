package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HasEqualsValuesTest extends AbstractMatcherTest {
    private static final WithPublicFields WITH_PUBLIC_FIELDS = new WithPublicFields(666, "a string");
    public static final HasEqualValues<WithPublicFields> WITH_PUBLIC_FIELDS_MATCHER = new HasEqualValues<>(WITH_PUBLIC_FIELDS);

    public static class  HasEqualValues<T> extends TypeSafeDiagnosingMatcher<T> {

        private final T expectedObject;

        public HasEqualValues(T expectedObject) {
            this.expectedObject = expectedObject;
        }

        @Override
        protected boolean matchesSafely(T item, Description mismatchDescription) {

            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("has equal values to ")
                    .appendText(expectedObject.getClass().getSimpleName())
                    .appendValueList("[", ",", "]", fieldNames());
        }

        private List<String> fieldNames() {
            final List<String> result = new ArrayList<>();
            for (Field field : expectedObject.getClass().getFields()) {
                result.add(field.getName());
            }
            return result;
        }
    }

    @Override
    protected Matcher<?> createMatcher() {
        return WITH_PUBLIC_FIELDS_MATCHER;
    }

    public void testDescribesItself() {
        assertDescription(
                "has equal values to WithPublicFields[\"i\",\"s\"]",
                new HasEqualValues<>(WITH_PUBLIC_FIELDS));
    }

    public void testMisMatchesDifferentType() {
        assertMatches(WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields(666, "a string"));
    }


    public static class WithPublicFields {
        public final int i;
        public final String s;

        public WithPublicFields(int i, String s) {
            this.i = i;
            this.s = s;
        }
    }
}
