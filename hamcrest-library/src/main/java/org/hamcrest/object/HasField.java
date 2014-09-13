package org.hamcrest.object;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

/**
 * A Matcher that asserts that an object field matches the given matcher.
 * The property is extracted from the object using a {@linkplain FieldExtractor}.
 * The idea for this is to use a lamda to easily extract the property from the bean.
 */
public class HasField<T, U> extends FeatureMatcher<T, U> {

    private final FieldExtractor<T, U> field;

    public HasField(final String fieldName, final FieldExtractor<T, U> field, final Matcher<U> valueMatcher) {
        super(valueMatcher, "field " + fieldName + " is", fieldName);
        this.field = field;
    }

    @Override
    protected U featureValueOf(final T actual) {
        return field.apply(actual);
    }

    /**
     * Creates a matcher that matches when the examined object has a field
     * with the specified name whose value satisfies the specified matcher.
     * <p/>
     * For example, in Java 8:
     * <pre>assertThat(myBean, hasField("foo", Bean::getFoo, equalTo("bar"))</pre>
     *
     * @param fieldName name or identifier of the field in the bean.
     * @param field Object used to extract the field from the object
     * @param fieldMatcher
     *     a matcher for the value of the specified property of the examined bean
     */
    @Factory
    public static <T, U> Matcher<T> hasField(final String fieldName, final FieldExtractor<T, U> field, final Matcher<U> fieldMatcher) {
        return new HasField<T, U>(fieldName, field, fieldMatcher);
    }

}
