package org.hamcrest.beans;

import org.hamcrest.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.beans.PropertyUtil.NO_ARGUMENTS;
import static org.hamcrest.beans.PropertyUtil.propertyDescriptorsFor;
import static org.hamcrest.core.IsEqual.equalTo;

public class SamePropertyValuesAs<T> extends TypeSafeDiagnosingMatcher<T> {
    private final T expectedBean;
    private final Set<String> propertyNames;
    private final List<PropertyMatcher> propertyMatchers;


    public SamePropertyValuesAs(T expectedBean) {
        PropertyDescriptor[] descriptors = propertyDescriptorsFor(expectedBean, Object.class);
        this.expectedBean = expectedBean;
        this.propertyNames = propertyNamesFrom(descriptors);
        this.propertyMatchers = propertyMatchersFor(expectedBean, descriptors);
    }

    @Override
    public boolean matchesSafely(T bean, Description mismatch) {
        return isCompatibleType(bean, mismatch)
                && hasNoExtraProperties(bean, mismatch)
                && hasMatchingValues(bean, mismatch);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("same property values as " + expectedBean.getClass().getSimpleName())
                   .appendList(" [", ", ", "]", propertyMatchers);
    }


    private boolean isCompatibleType(T item, Description mismatchDescription) {
        if (!expectedBean.getClass().isAssignableFrom(item.getClass())) {
            mismatchDescription.appendText("is incompatible type: " + item.getClass().getSimpleName());
            return false;
        }
        return true;
    }

    private boolean hasNoExtraProperties(T item, Description mismatchDescription) {
        Set<String> actualPropertyNames = propertyNamesFrom(propertyDescriptorsFor(item, Object.class));
        actualPropertyNames.removeAll(propertyNames);
        if (!actualPropertyNames.isEmpty()) {
            mismatchDescription.appendText("has extra properties called " + actualPropertyNames);
            return false;
        }
        return true;
    }

    private boolean hasMatchingValues(T item, Description mismatchDescription) {
        for (PropertyMatcher propertyMatcher : propertyMatchers) {
            if (!propertyMatcher.matches(item)) {
                propertyMatcher.describeMismatch(item, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    private static <T> List<PropertyMatcher> propertyMatchersFor(T bean, PropertyDescriptor[] descriptors) {
        List<PropertyMatcher> result = new ArrayList<PropertyMatcher>(descriptors.length);
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            result.add(new PropertyMatcher(propertyDescriptor, bean));
        }
        return result;
    }

    private static Set<String> propertyNamesFrom(PropertyDescriptor[] descriptors) {
        HashSet<String> result = new HashSet<String>();
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            result.add(propertyDescriptor.getDisplayName());
        }
        return result;
    }

    public static class PropertyMatcher extends DiagnosingMatcher<Object> {
        private final Method readMethod;
        private final Matcher<Object> matcher;
        private final String propertyName;

        public PropertyMatcher(PropertyDescriptor descriptor, Object expectedObject) {
            this.propertyName = descriptor.getDisplayName();
            this.readMethod = descriptor.getReadMethod();
            this.matcher = equalTo(readProperty(readMethod, expectedObject));
        }

        @Override
        public boolean matches(Object actual, Description mismatch) {
            final Object actualValue = readProperty(readMethod, actual);
            if (!matcher.matches(actualValue)) {
                mismatch.appendText(propertyName + " ");
                matcher.describeMismatch(actualValue, mismatch);
                return false;
            }
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(propertyName + ": ").appendDescriptionOf(matcher);
        }
    }

    private static Object readProperty(Method method, Object target) {
        try {
            return method.invoke(target, NO_ARGUMENTS);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not invoke " + method + " on " + target, e);
        }
    }

    /**
     * Creates a matcher that matches when the examined object has values for all of
     * its JavaBean properties that are equal to the corresponding values of the
     * specified bean.
     * <p/>
     * For example:
     * <pre>assertThat(myBean, samePropertyValuesAs(myExpectedBean))</pre>
     * 
     * @param expectedBean
     *     the bean against which examined beans are compared
     */
    @Factory
    public static <T> Matcher<T> samePropertyValuesAs(T expectedBean) {
        return new SamePropertyValuesAs<T>(expectedBean);
    }

}
