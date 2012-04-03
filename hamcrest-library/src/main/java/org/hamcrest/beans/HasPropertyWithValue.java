/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.beans;

import org.hamcrest.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;
import static org.hamcrest.beans.PropertyUtil.NO_ARGUMENTS;

/**
 * Matcher that asserts that a JavaBean property on an argument passed to the
 * mock object meets the provided matcher. This is useful for when objects
 * are created within code under test and passed to a mock object, and you wish
 * to assert that the created object has certain properties.
 * <p/>
 * <h2>Example Usage</h2>
 * Consider the situation where we have a class representing a person, which
 * follows the basic JavaBean convention of having get() and possibly set()
 * methods for it's properties: <code>
 * public class Person {
 * private String name;
 * <p/>
 * public Person(String person) {
 * this.person = person;
 * }
 * <p/>
 * public String getName() {
 * return name;
 * }
 * }
 * </code> And that these person
 * objects are generated within a piece of code under test (a class named
 * PersonGenerator). This object is sent to one of our mock objects which
 * overrides the PersonGenerationListener interface: <code>
 * public interface PersonGenerationListener {
 * public void personGenerated(Person person);
 * }
 * </code>
 * In order to check that the code under test generates a person with name
 * "Iain" we would do the following:
 * <p/>
 * <code>
 * Mock personGenListenerMock = mock(PersonGenerationListener.class);
 * personGenListenerMock.expects(once()).method("personGenerated").with(and(isA(Person.class), hasProperty("Name", eq("Iain")));
 * PersonGenerationListener listener = (PersonGenerationListener)personGenListenerMock.proxy();
 * </code>
 * <p/>
 * If an exception is thrown by the getter method for a property, the property
 * does not exist, is not readable, or a reflection related exception is thrown
 * when trying to invoke it then this is treated as an evaluation failure and
 * the matches method will return false.
 * <p/>
 * This matcher class will also work with JavaBean objects that have explicit
 * bean descriptions via an associated BeanInfo description class. See the
 * JavaBeans specification for more information:
 * <p/>
 * http://java.sun.com/products/javabeans/docs/index.html
 *
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 */
public class HasPropertyWithValue<T> extends TypeSafeDiagnosingMatcher<T> {
    private static final Condition.Step<PropertyDescriptor,Method> WITH_READ_METHOD = withReadMethod();
    private final String propertyName;
    private final Matcher<Object> valueMatcher;

    public HasPropertyWithValue(String propertyName, Matcher<?> valueMatcher) {
        this.propertyName = propertyName;
        this.valueMatcher = nastyGenericsWorkaround(valueMatcher);
    }

    @Override
	public boolean matchesSafely(T bean, Description mismatch) {
        return propertyOn(bean, mismatch)
                  .and(WITH_READ_METHOD)
                  .and(withPropertyValue(bean))
                  .matching(valueMatcher, "property '" + propertyName + "' ");
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
                } catch (Exception e) {
                    mismatch.appendText(e.getMessage());
                    return notMatched();
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

    @Factory
    public static <T> Matcher<T> hasProperty(String propertyName, Matcher<?> value) {
        return new HasPropertyWithValue<T>(propertyName, value);
    }
}
