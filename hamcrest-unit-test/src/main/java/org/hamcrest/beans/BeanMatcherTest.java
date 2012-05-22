package org.hamcrest.beans;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

public class BeanMatcherTest extends TestCase {
	
	private static final String MISMATCH_DESCRIPTION = "MISMATCH DESCRIPTION";
	private static final String OTHER_MISMATCH_DESCRIPTION = "OTHER MISMATCH DESCRIPTION";
	private static final String EXPECTED_DESCRIPTION = "EXPECTED DESCRIPTION";
	private static final String OTHER_EXPECTED_DESCRIPTION = "OTHER EXPECTED DESCRIPTION";
	private static final boolean MATCHES = true;
	private static final boolean DOES_NOT_MATCH = false;
	
	private Object bean;
	private BeanPropertyMatcher<Object> unmatchingProperty;
	private BeanPropertyMatcher<Object> otherUnmatchingProperty;
	private BeanPropertyMatcher<Object> matchingProperty;
	
	private Description expectedDescription;
	private Description mismatchDescription;

	@Override
	protected void setUp() {
		bean = new Object();
		matchingProperty = new MockPropertyMatcher<Object>(MATCHES, EXPECTED_DESCRIPTION, MISMATCH_DESCRIPTION);
		unmatchingProperty = new MockPropertyMatcher<Object>(DOES_NOT_MATCH, EXPECTED_DESCRIPTION, MISMATCH_DESCRIPTION);
		otherUnmatchingProperty = new MockPropertyMatcher<Object>(DOES_NOT_MATCH, OTHER_EXPECTED_DESCRIPTION, OTHER_MISMATCH_DESCRIPTION);
		expectedDescription = new StringDescription();
		mismatchDescription = new StringDescription();
	}
	
	public void testNoExpectedDescriptionWhenPropertyIsAMatch() {
		BeanMatcher<Object> beanMatcher = BeanMatcher.has(matchingProperty);
		
		beanMatcher.matches(bean);
		beanMatcher.describeTo(expectedDescription);
		
		assertThat(expectedDescription.toString(), isEmptyString());
	}

	public void testNoMismatchingDescriptionsWhenPropertyIsAMatch() {
		BeanMatcher<?> beanMatcher = BeanMatcher.has(matchingProperty);
		
		beanMatcher.matches(bean);
		beanMatcher.describeMismatch(bean, mismatchDescription);
		
		assertThat(mismatchDescription.toString(), isEmptyString());
	}
	
	public void testPopulateExpectedDescriptionWhenPropertiesDoNotMatch() {
		BeanMatcher<Object> beanMatcher = BeanMatcher.has(unmatchingProperty);
		
		beanMatcher.matches(bean);
		beanMatcher.describeTo(expectedDescription);
		
		assertThat(expectedDescription.toString(), is(EXPECTED_DESCRIPTION));
	}
	
	public void testPopulateMismatchDescriptionWhenPropertiesDoNotMatch() {
		BeanMatcher<Object> beanMatcher = BeanMatcher.has(unmatchingProperty);
		
		beanMatcher.matches(bean);
		beanMatcher.describeMismatch(bean, mismatchDescription);
		
		assertThat(mismatchDescription.toString(), is(MISMATCH_DESCRIPTION));
	}
	
	public void testExpectedDescriptionIsAppendedWhenMultiplePropertiesFail() {
		BeanMatcher<Object> beanMatcher = BeanMatcher.has(unmatchingProperty, 
														  matchingProperty,
														  otherUnmatchingProperty);
		
		beanMatcher.matches(bean);
		beanMatcher.describeTo(expectedDescription);
		
		assertThat(expectedDescription.toString(), is(EXPECTED_DESCRIPTION + OTHER_EXPECTED_DESCRIPTION));
	}
	
	public void testMismatchDescriptionIsAppendedWhenMultiplePropertiesFail() {
		BeanMatcher<Object> beanMatcher = BeanMatcher.has(unmatchingProperty, 
														  matchingProperty,
														  otherUnmatchingProperty);
		
		beanMatcher.matches(bean);
		beanMatcher.describeMismatch(bean, expectedDescription);
		
		assertThat(expectedDescription.toString(), is(MISMATCH_DESCRIPTION + OTHER_MISMATCH_DESCRIPTION));
	}
	
	private class MockPropertyMatcher<T> extends BeanPropertyMatcher<T> {
		
		private boolean matches;
		private String expectedDescription;
		private String mismatchDescription;

		public MockPropertyMatcher(boolean matches, String expectedDescription, String mismatchDescription) {
			super("any property", anything());
			this.matches = matches;
			this.expectedDescription = expectedDescription;
			this.mismatchDescription = mismatchDescription;
		}

		@Override
		public boolean matchesSafely(T bean, Description mismatchDescription) {
			mismatchDescription.appendText(this.mismatchDescription);
			return matches;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(expectedDescription);
		}
		
	}
	
}
