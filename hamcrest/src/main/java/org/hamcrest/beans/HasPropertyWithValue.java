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
import org.hamcrest.Matchers;
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
public class HasPropertyWithValue<V, T> extends TypeSafeDiagnosingMatcher<T> {
    private static final Condition.Step<PropertyDescriptor,Method> WITH_READ_METHOD = withReadMethod();
    private final String propertyName;
    private final Matcher<V> valueMatcher;
    private final String messageFormat;

    public HasPropertyWithValue(String propertyName, Matcher<V> valueMatcher) {
        this(propertyName, valueMatcher, " property '%s' ");
    }

    public HasPropertyWithValue(String propertyName, Matcher<V> valueMatcher, String messageFormat) {
        if(propertyName == null) {
            throw new IllegalArgumentException("propertyName mustn't be null");
        }
        this.propertyName = propertyName;
        if(valueMatcher == null) {
            throw new IllegalArgumentException("valueMatcher mustn't be null");
        }
        this.valueMatcher = valueMatcher;
        if(messageFormat == null) {
            throw new IllegalArgumentException("messageFormat mustn't be null");
        }
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

    private Condition.Step<Method, V> withPropertyValue(final T bean) {
        return new Condition.Step<Method, V>() {
            @Override
            public Condition<V> apply(Method readMethod, Description mismatch) {
                try {
                    return matched((V)readMethod.invoke(bean, NO_ARGUMENTS), mismatch);
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
    public static <V, T> Matcher<T> hasPropertyAtPath(String path, Matcher<V> valueMatcher) {
        List<String> properties = Arrays.asList(path.split("\\."));
            ListIterator<String> iterator =
                properties.listIterator(properties.size());
            if(!iterator.hasPrevious()) {
                throw new IllegalArgumentException(String.format("path '%s' "
                        + "defines no properties",
                        path));
            }

            Matcher<T> ret = null;
            while (iterator.hasPrevious()) {
                ret = new HasPropertyWithValue<>(iterator.previous(),
                        iterator.hasPrevious()
                                ? Matchers.not(Matchers.anything())
                                : valueMatcher,
                        "%s.");
            }
            return ret;
    }

}
