package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.beans.PropertyAccessor.PropertyReadLens;

import java.beans.FeatureDescriptor;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * A matcher that checks if a given bean has the same property values
 * as an example bean.
 * @param <T> the matcher value type.
 * @see #samePropertyValuesAs(Object, String...)
 */
public class SamePropertyValuesAs<T> extends DiagnosingMatcher<T> {

    private final T expectedBean;
    private final Set<String> propertyNames;
    private final List<PropertyMatcher> propertyMatchers;
    private final List<String> ignoredFields;

    /**
     * Constructor, best called from {@link #samePropertyValuesAs(Object, String...)}.
     * @param expectedBean the bean object with the expected values
     * @param ignoredProperties list of property names that should be excluded from the match
     */
    @SuppressWarnings("WeakerAccess")
    public SamePropertyValuesAs(T expectedBean, List<String> ignoredProperties) {
        PropertyAccessor accessor = new PropertyAccessor(expectedBean);
        this.expectedBean = expectedBean;
        this.ignoredFields = ignoredProperties;
        this.propertyNames = propertyNamesFrom(accessor, ignoredProperties);
        this.propertyMatchers = propertyMatchersFor(expectedBean, accessor, ignoredProperties);
    }

    @Override
    protected boolean matches(Object actual, Description mismatch) {
        return isNotNull(actual, mismatch)
                && isCompatibleType(actual, mismatch)
                && hasNoExtraProperties(actual, mismatch)
                && hasMatchingValues(actual, mismatch);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("same property values as " + expectedBean.getClass().getSimpleName())
                   .appendList(" [", ", ", "]", propertyMatchers);
        if (! ignoredFields.isEmpty()) {
            description.appendText(" ignoring ")
                    .appendValueList("[", ", ", "]", ignoredFields);
        }
    }

    private boolean isCompatibleType(Object actual, Description mismatchDescription) {
        if (expectedBean.getClass().isAssignableFrom(actual.getClass())) {
            return true;
        }

        mismatchDescription.appendText("is incompatible type: " + actual.getClass().getSimpleName());
        return false;
    }

    private boolean hasNoExtraProperties(Object actual, Description mismatchDescription) {
        PropertyAccessor accessor = new PropertyAccessor(actual);
        Set<String> actualPropertyNames = propertyNamesFrom(accessor, ignoredFields);
        propertyNames.forEach(actualPropertyNames::remove);
        if (!actualPropertyNames.isEmpty()) {
            mismatchDescription.appendText("has extra properties called " + actualPropertyNames);
            return false;
        }
        return true;
    }

    private boolean hasMatchingValues(Object actual, Description mismatchDescription) {
        for (PropertyMatcher propertyMatcher : propertyMatchers) {
            if (!propertyMatcher.matches(actual)) {
                propertyMatcher.describeMismatch(actual, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    private static <T> List<PropertyMatcher> propertyMatchersFor(T bean, PropertyAccessor accessor, List<String> ignoredFields) {
        return accessor.readLenses().stream()
                .filter(lens -> !ignoredFields.contains(lens.getName()))
                .map(lens -> new PropertyMatcher(lens, bean))
                .collect(Collectors.toList());
    }

    private static Set<String> propertyNamesFrom(PropertyAccessor accessor, List<String> ignoredFields) {
        Set<String> fieldNames = new HashSet<>(accessor.fieldNames());
        ignoredFields.forEach(fieldNames::remove);
        return fieldNames;
    }

    private static boolean isNotIgnored(List<String> ignoredFields, FeatureDescriptor propertyDescriptor) {
        return ! ignoredFields.contains(propertyDescriptor.getDisplayName());
    }

    @SuppressWarnings("WeakerAccess")
    private static class PropertyMatcher extends DiagnosingMatcher<Object> {
        private final PropertyReadLens expectedReadLens;
        private final Matcher<Object> matcher;

        public PropertyMatcher(PropertyReadLens expectedReadLens, Object expectedObject) {
            this.expectedReadLens = expectedReadLens;
            this.matcher = equalTo(expectedReadLens.getValue());
        }

        @Override
        public boolean matches(Object actual, Description mismatch) {
            PropertyAccessor actualAccessor = new PropertyAccessor(actual);
            Object actualValue = actualAccessor.fieldValue(expectedReadLens.getName());
            if (!matcher.matches(actualValue)) {
                mismatch.appendText(expectedReadLens.getName()+ " ");
                matcher.describeMismatch(actualValue, mismatch);
                return false;
            }
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(expectedReadLens.getName() + ": ").appendDescriptionOf(matcher);
        }
    }

    /**
     * <p>Creates a matcher that matches when the examined object has values for all of
     * its JavaBean properties that are equal to the corresponding values of the
     * specified bean. If any properties are marked as ignored, they will be dropped from
     * both the expected and actual bean. Note that the ignored properties use JavaBean
     * display names, for example "<code>age</code>" rather than method names such as
     * "<code>getAge</code>".
     * </p>
     * For example:
     * <pre>{@code
     * assertThat(myBean, samePropertyValuesAs(myExpectedBean))
     * assertThat(myBean, samePropertyValuesAs(myExpectedBean), "age", "height")
     * }</pre>
     *
     * @param <B> the matcher value type.
     * @param expectedBean the bean against which examined beans are compared
     * @param ignoredProperties do not check any of these named properties.
     * @return The matcher.
     */
    public static <B> Matcher<B> samePropertyValuesAs(B expectedBean, String... ignoredProperties) {
        return new SamePropertyValuesAs<>(expectedBean, asList(ignoredProperties));
    }

}
