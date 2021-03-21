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

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;
import static org.hamcrest.beans.PropertyUtil.NO_ARGUMENTS;

/**
 * <p>Matcher that asserts that a JavaBean property on an argument passed to the
 * mock object meets the provided matcher. This is useful for when objects
 * are created within code under test and passed to a mock object, and you wish
 * to assert that the created object has certain properties.
 * </p>
 *
 * <h2>Example Usage</h2>
 * Consider the situation where we have a class representing a person, which
 * follows the basic JavaBean convention of having get() and possibly set()
 * methods for it's properties:
 * <pre>
 * public class Person {
 *   private String name;
 *   public Person(String person) {
 *     this.person = person;
 *   }
 *   public String getName() {
 *     return name;
 *   }
 * }</pre>
 * 
 * And that these person objects are generated within a piece of code under test
 * (a class named PersonGenerator). This object is sent to one of our mock objects
 * which overrides the PersonGenerationListener interface:
 * <pre>
 * public interface PersonGenerationListener {
 *   public void personGenerated(Person person);
 * }</pre>
 * 
 * In order to check that the code under test generates a person with name
 * "Iain" we would do the following:
 * <pre>
 * Mock personGenListenerMock = mock(PersonGenerationListener.class);
 * personGenListenerMock.expects(once()).method("personGenerated").with(and(isA(Person.class), hasProperty("Name", eq("Iain")));
 * PersonGenerationListener listener = (PersonGenerationListener)personGenListenerMock.proxy();</pre>
 * 
 * <p>If an exception is thrown by the getter method for a property, the property
 * does not exist, is not readable, or a reflection related exception is thrown
 * when trying to invoke it then this is treated as an evaluation failure and
 * the matches method will return false.
 * </p>
 * <p>This matcher class will also work with JavaBean objects that have explicit
 * bean descriptions via an associated BeanInfo description class. See the
 * JavaBeans specification for more information:
 * http://java.sun.com/products/javabeans/docs/index.html
 * </p>
 *
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @author cristcost at github
 */
public class HasPropertyWithValue<T> extends TypeSafeDiagnosingMatcher<T> {
    private static final Condition.Step<PropertyDescriptor,Method> WITH_READ_METHOD = withReadMethod();
    private final String propertyName;
    private final Matcher<Object> valueMatcher;
    private final String messageFormat;

    public HasPropertyWithValue(String propertyName, Matcher<?> valueMatcher) {
        this(propertyName, valueMatcher, " property '%s' ");
    }

    public HasPropertyWithValue(String propertyName, Matcher<?> valueMatcher, String messageFormat) {
        this.propertyName = propertyName;
        this.valueMatcher = nastyGenericsWorkaround(valueMatcher);
        this.messageFormat = messageFormat;
    }

    @Override
    public boolean matchesSafely(T bean, Description mismatch) {
        return propertyOn(bean, mismatch)
                  .and(WITH_READ_METHOD)
                  .and(withPropertyValue(bean))
                  .matching(valueMatcher, String.format(messageFormat, propertyName));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(propertyName).appendText(", ")
                   .appendDescriptionOf(valueMatcher).appendText(")");
    }

    private Condition<PropertyDescriptor> propertyOn(T bean, Description mismatch) {
        PropertyDescriptor property = PropertyUtil.getPropertyDescriptor(propertyName, bean);
        if (property == null) {
            mismatch.appendText("No property \"" + propertyName + "\"");
            return notMatched();
        }

        return matched(property, mismatch);
    }

    private Condition.Step<Method, Object> withPropertyValue(final T bean) {
        return new Condition.Step<Method, Object>() {
            @Override
            public Condition<Object> apply(Method readMethod, Description mismatch) {
                try {
                    return matched(readMethod.invoke(bean, NO_ARGUMENTS), mismatch);
                } catch (InvocationTargetException e) {
                    mismatch
                      .appendText("Calling '")
                      .appendText(readMethod.toString())
                      .appendText("': ")
                      .appendValue(e.getTargetException().getMessage());
                    return notMatched();
                } catch (Exception e) {
                    throw new IllegalStateException(
                      "Calling: '" + readMethod + "' should not have thrown " + e);
                }
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static Matcher<Object> nastyGenericsWorkaround(Matcher<?> valueMatcher) {
        return (Matcher<Object>) valueMatcher;
    }

    private static Condition.Step<PropertyDescriptor,Method> withReadMethod() {
        return new Condition.Step<PropertyDescriptor, java.lang.reflect.Method>() {
            @Override
            public Condition<Method> apply(PropertyDescriptor property, Description mismatch) {
                final Method readMethod = property.getReadMethod();
                if (null == readMethod) {
                    mismatch.appendText("property \"" + property.getName() + "\" is not readable");
                    return notMatched();
                }
                return matched(readMethod, mismatch);
            }
        };
    }

    /**
     * Creates a matcher that matches when the examined object has a JavaBean property
     * with the specified name whose value satisfies the specified matcher.
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo", equalTo("bar"))</pre>
     * 
     * @param propertyName
     *     the name of the JavaBean property that examined beans should possess
     * @param valueMatcher
     *     a matcher for the value of the specified property of the examined bean
     */
    public static <T> Matcher<T> hasProperty(String propertyName, Matcher<?> valueMatcher) {
        return new HasPropertyWithValue<>(propertyName, valueMatcher);
    }

    /**
     * Creates a matcher that matches when the examined object is a graph of
     * JavaBean objects that can be navigated along the declared dot-separated path
     * and the final element of that path is a JavaBean property whose value satisfies the
     * specified matcher.
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo.bar.baz", equalTo("a property value"))</pre>
     *
     * @param path
     *     the dot-separated path from the examined object to the JavaBean property
     * @param valueMatcher
     *     a matcher for the value of the specified property of the examined bean
     */
    public static <T> Matcher<T> hasPropertyAtPath(String path, Matcher<T> valueMatcher) {
        List<String> properties = Arrays.asList(path.split("\\."));
            ListIterator<String> iterator =
                properties.listIterator(properties.size());

            Matcher<T> ret = valueMatcher;
            while (iterator.hasPrevious()) {
                ret = new HasPropertyWithValue<>(iterator.previous(), ret, "%s.");
            }
            return ret;
    }

}
