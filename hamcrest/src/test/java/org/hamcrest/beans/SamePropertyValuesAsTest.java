/**
 * BSD License
 *
 * Copyright (c) 2000-2015 www.hamcrest.org
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

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

/**
 * Same property values as test.
 */
@SuppressWarnings("WeakerAccess")
public class SamePropertyValuesAsTest extends AbstractMatcherTest {
    private static final Value A_VALUE = new Value("expected");
    private static final ExampleBean EXPECTED_BEAN = new ExampleBean("same", 1, A_VALUE);
    private static final ExampleBean ACTUAL_BEAN = new ExampleBean("same", 1, A_VALUE);

    @Override
    protected Matcher<?> createMatcher() {
        return samePropertyValuesAs(EXPECTED_BEAN);
    }

    public void testReportsMatchWhenAllPropertiesMatch() {
        assertMatches("matched properties", samePropertyValuesAs(EXPECTED_BEAN), ACTUAL_BEAN);
    }

    public void testReportsMismatchWhenActualTypeIsNotAssignableToExpectedType() {
        assertMismatchDescription("is incompatible type: ExampleBean",
                samePropertyValuesAs((Object)A_VALUE), ACTUAL_BEAN);
    }

    public void testReportsMismatchOnFirstPropertyDifference() {
        assertMismatchDescription("stringProperty was \"different\"",
                samePropertyValuesAs(EXPECTED_BEAN), new ExampleBean("different", 1, A_VALUE));
        assertMismatchDescription("intProperty was <2>",
                samePropertyValuesAs(EXPECTED_BEAN), new ExampleBean("same", 2, A_VALUE));
        assertMismatchDescription("valueProperty was <Value other>",
                samePropertyValuesAs(EXPECTED_BEAN), new ExampleBean("same", 1, new Value("other")));
    }

    public void testMatchesBeansWithInheritanceButNoExtraProperties() {
        assertMatches("sub type with same properties",
                samePropertyValuesAs(EXPECTED_BEAN), new SubBeanWithNoExtraProperties("same", 1, A_VALUE));
    }

    public void testRejectsSubtypeThatHasExtraProperties() {
        assertMismatchDescription("has extra properties called [extraProperty]",
                samePropertyValuesAs(EXPECTED_BEAN), new SubBeanWithExtraProperty("same", 1, A_VALUE));
    }

    public void testIgnoresExtraSubtypeProperties() {
        final SubBeanWithExtraProperty withExtraProperty = new SubBeanWithExtraProperty("same", 1, A_VALUE);
        assertMatches("extra property", samePropertyValuesAs(EXPECTED_BEAN, "extraProperty"), withExtraProperty);
    }

    public void testIgnoresDifferentProperties() {
        final ExampleBean differentBean = new ExampleBean("different", 1, A_VALUE);
        assertMatches("different property", samePropertyValuesAs(EXPECTED_BEAN, "stringProperty"), differentBean);
    }

    public void testAcceptsMissingPropertiesToIgnore() {
        assertMatches("ignored property", samePropertyValuesAs(EXPECTED_BEAN, "notAProperty"), ACTUAL_BEAN);
    }

    public void testCanIgnoreAllProperties() {
        final ExampleBean differentBean = new ExampleBean("different", 2, new Value("not expected"));
        assertMatches(
                "different property",
                samePropertyValuesAs(EXPECTED_BEAN, "stringProperty", "intProperty", "valueProperty"),
                differentBean);
    }

    public void testDescribesItself() {
        assertDescription(
                "same property values as ExampleBean [intProperty: <1>, stringProperty: \"same\", valueProperty: <Value expected>]",
                samePropertyValuesAs(EXPECTED_BEAN));

        assertDescription(
                "same property values as ExampleBean [intProperty: <1>, stringProperty: \"same\", valueProperty: <Value expected>] ignoring [\"ignored1\", \"ignored2\"]",
                samePropertyValuesAs(EXPECTED_BEAN, "ignored1", "ignored2"));
    }

    /**
     * The value class.
     */
    public static class Value {
        public final Object value;

        public Value(Object value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Value " + value;
        }
    }

    /**
     * The example bean class.
     */
    @SuppressWarnings("unused")
    public static class ExampleBean {
        private String stringProperty;
        private int intProperty;
        private Value valueProperty;

        public ExampleBean(String stringProperty, int intProperty, Value valueProperty) {
            this.stringProperty = stringProperty;
            this.intProperty = intProperty;
            this.valueProperty = valueProperty;
        }

        public String getStringProperty() {
            return stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public Value getValueProperty() {
            return valueProperty;
        }

        @Override
        public String toString() {
            return "an ExampleBean";
        }
    }

    /**
     * The sub bean with no extra properties implementation.
     */
    public static class SubBeanWithNoExtraProperties extends ExampleBean {

        public SubBeanWithNoExtraProperties(String stringProperty, int intProperty, Value valueProperty) {
            super(stringProperty, intProperty, valueProperty);
        }
    }

    /**
     * The sub bean with extra properties implementation.
     */
    public static class SubBeanWithExtraProperty extends ExampleBean {
        public SubBeanWithExtraProperty(String stringProperty, int intProperty, Value valueProperty) {
            super(stringProperty, intProperty, valueProperty);
        }

        @SuppressWarnings("unused")
        public String getExtraProperty() {
            return "extra";
        }
    }
}
