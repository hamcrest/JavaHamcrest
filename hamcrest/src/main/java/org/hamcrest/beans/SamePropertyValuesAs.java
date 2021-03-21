/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.beans.PropertyUtil.NO_ARGUMENTS;
import static org.hamcrest.beans.PropertyUtil.propertyDescriptorsFor;
import static org.hamcrest.core.IsEqual.equalTo;

public class SamePropertyValuesAs<T> extends DiagnosingMatcher<T> {
    private final T expectedBean;
    private final Set<String> propertyNames;
    private final List<PropertyMatcher> propertyMatchers;
    private final List<String> ignoredFields;

    @SuppressWarnings("WeakerAccess")
    public SamePropertyValuesAs(T expectedBean, List<String> ignoredProperties) {
        PropertyDescriptor[] descriptors = propertyDescriptorsFor(expectedBean, Object.class);
        this.expectedBean = expectedBean;
        this.ignoredFields = ignoredProperties;
        this.propertyNames = propertyNamesFrom(descriptors, ignoredProperties);
        this.propertyMatchers = propertyMatchersFor(expectedBean, descriptors, ignoredProperties);
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
        Set<String> actualPropertyNames = propertyNamesFrom(propertyDescriptorsFor(actual, Object.class), ignoredFields);
        actualPropertyNames.removeAll(propertyNames);
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

    private static <T> List<PropertyMatcher> propertyMatchersFor(T bean, PropertyDescriptor[] descriptors, List<String> ignoredFields) {
        List<PropertyMatcher> result = new ArrayList<>(descriptors.length);
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            if (isIgnored(ignoredFields, propertyDescriptor)) {
                result.add(new PropertyMatcher(propertyDescriptor, bean));
            }
        }
        return result;
    }

    private static Set<String> propertyNamesFrom(PropertyDescriptor[] descriptors, List<String> ignoredFields) {
        HashSet<String> result = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            if (isIgnored(ignoredFields, propertyDescriptor)) {
                result.add(propertyDescriptor.getDisplayName());
            }
        }
        return result;
    }

    private static boolean isIgnored(List<String> ignoredFields, PropertyDescriptor propertyDescriptor) {
        return ! ignoredFields.contains(propertyDescriptor.getDisplayName());
    }

    @SuppressWarnings("WeakerAccess")
    private static class PropertyMatcher extends DiagnosingMatcher<Object> {
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
     * specified bean. If any properties are marked as ignored, they will be dropped from
     * both the expected and actual bean. Note that the ignored properties use JavaBean
     * display names, for example <pre>age</pre> rather than method names such as <pre>getAge</pre>.
     * For example:
     * <pre>assertThat(myBean, samePropertyValuesAs(myExpectedBean))</pre>
     * <pre>assertThat(myBean, samePropertyValuesAs(myExpectedBean), "age", "height")</pre>
     *
     * @param expectedBean
     *     the bean against which examined beans are compared
     * @param ignoredProperties
     *     do not check any of these named properties.
     */
    public static <B> Matcher<B> samePropertyValuesAs(B expectedBean, String... ignoredProperties) {
        return new SamePropertyValuesAs<>(expectedBean, asList(ignoredProperties));
    }

}
