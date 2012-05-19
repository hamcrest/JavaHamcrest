package org.hamcrest.beans;

import static org.hamcrest.beans.PropertyUtil.NO_ARGUMENTS;
import static org.hamcrest.beans.PropertyUtil.getPropertyDescriptor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * @author Sandro Mancuso
 */
public class BeanPropertyMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

	private String matchingPropertyName;
	private Matcher<?> valueMatcher;

	public BeanPropertyMatcher(String propertyName, Matcher<?> valueMatcher) {
		this.matchingPropertyName = propertyName;
		this.valueMatcher = valueMatcher;
	}

	@Factory
	public static <T> BeanPropertyMatcher<T> property(String propertyName,Matcher<?> value) {
		return new BeanPropertyMatcher<T>(propertyName, value);
	}

	@Override
	public boolean matchesSafely(T bean, Description mismatchDescription) {
		try {
			return matchesSafely(bean, matchingPropertyName, mismatchDescription);
		} catch (Exception e) {
			mismatchDescription.appendValue(e);
			return false;
		}
	}

	private boolean matchesSafely(Object bean, String propertyName, Description mismatchDescription) 
			throws Exception {
		Object parentObject = bean;
		if (isComposedProperty(propertyName)) {
			String memberObjectProperty = getMemberObjectProperty(propertyName);
			Object memberObject = getPropertyValue(parentObject, memberObjectProperty);
			String nextProperty = getNextProperty(propertyName);
			return matchesSafely(memberObject, nextProperty, mismatchDescription);
		} else {
			return matchProperty(bean, propertyName, mismatchDescription);
		}
	}

	private String getNextProperty(String composedPropertyName) {
		return composedPropertyName.substring(composedPropertyName.indexOf(".") + 1);
	}

	private Object getPropertyValue(Object parentObject, String memberObjectProperty) 
			throws Exception {
		PropertyDescriptor property = getPropertyDescriptor(memberObjectProperty, parentObject);
		Method readMethod = property.getReadMethod();
		return readMethod.invoke(parentObject, NO_ARGUMENTS);
	}

	private boolean isComposedProperty(String propertyName) {
		return propertyName.contains(".");
	}

	private String getMemberObjectProperty(String composedPropertyName) {
		return composedPropertyName.substring(0, composedPropertyName.indexOf("."));
	}

	private boolean matchProperty(Object bean, String propertyName, Description mismatchDescription) 
			throws Exception {
		Method readMethod = findReadMethod(bean, propertyName, mismatchDescription);
		return (readMethod != null) 
						? matchPropertyValue(bean, readMethod, mismatchDescription) 
						: false;
	}

	private boolean matchPropertyValue(Object bean, Method readMethod, Description mismatchDescription) 
			throws Exception {
		Object propertyValue = readMethod.invoke(bean, NO_ARGUMENTS);
		boolean valueMatches = valueMatcher.matches(propertyValue);
		if (!valueMatches) {
			appendSeparatorTo(mismatchDescription);
			mismatchDescription.appendText("property \'" + matchingPropertyName + "\' ");
			valueMatcher.describeMismatch(propertyValue, mismatchDescription);
		}
		return valueMatches;
	}

	private void appendSeparatorTo(Description description) {
		if (description.toString().length() > 0) {
			description.appendText(", ");
		}
	}

	private Method findReadMethod(Object argument, String propertyName, Description mismatchDescription) 
			throws IllegalArgumentException {
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor(propertyName, argument);
		if (null == propertyDescriptor) {
			mismatchDescription.appendText("No property \"" + matchingPropertyName + "\"");
			return null;
		}
		Method readMethod = propertyDescriptor.getReadMethod();
		if (null == readMethod) {
			mismatchDescription.appendText("property \"" + matchingPropertyName + "\" is not readable");
		}
		return readMethod;
	}

	public void describeTo(Description description) {
		appendSeparatorTo(description);
		description.appendText("property ");
		description.appendValue(matchingPropertyName);
		description.appendText(" = ");
		description.appendDescriptionOf(valueMatcher);
		description.appendText(" ");
	}

}
