/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
public class HasPropertyWithValue<T> extends TypeSafeMatcher<T> {

    private static final Object[] NO_ARGUMENTS = new Object[0];

    private final String propertyName;
    private final Matcher value;

    public HasPropertyWithValue(String propertyName, Matcher value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    public boolean matchesSafely(T argument) {
        try {
            Method readMethod = getReadMethod(argument);
            return readMethod != null
                    && value.matches(readMethod.invoke(argument, NO_ARGUMENTS));
        } catch (IntrospectionException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        } catch (InvocationTargetException e) {
            return false;
        }
    }

    private Method getReadMethod(Object argument) throws IntrospectionException {
        PropertyDescriptor property = PropertyUtil.getPropertyDescriptor(propertyName, argument);
        return property == null ? null : property.getReadMethod();
    }

    public void describeTo(Description description) {
        description.appendText("hasProperty(");
        description.appendValue(propertyName);
        description.appendText(", ");
        description.appendDescriptionOf(value);
        description.appendText(")");
    }

    @Factory
    public static <T> Matcher<T> hasProperty(String propertyName, Matcher value) {
        return new HasPropertyWithValue<T>(propertyName, value);
    }
}
