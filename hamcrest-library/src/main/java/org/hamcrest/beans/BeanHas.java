package org.hamcrest.beans;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * <p>Matches multiple attributes of an object within a single assertion</p>
 * 
 * <p>How to use it: <p>
 * 
 * // Static imports <br/>
 * import static org.craftedsw.beanpropertymatcher.matcher.BeanMatcher.has; <br/>
 * import static org.craftedsw.beanpropertymatcher.matcher.BeanPropertyMatcher.property; <br/>
 * <b>import static org.hamcrest.MatcherAssert.assertThat;</b><br/>
 * import static org.hamcrest.Matchers.equalTo;<br/>
 * import static org.hamcrest.Matchers.greaterThan;<br/>
 * <p>
 * Person person = new Person();<br/>
 * person.setFirstName("Sandro");<br/>
 * person.setAge(35);<br/>
 * </p>   
 * <p>   
 * Country uk = new Country();<br/>
 * uk.setName("United Kingdom");<br/>
 * </p>
 * <p>      
 * Address address = new Address();<br/>
 * address.setPostcode("1234556");<br/>
 * address.setCity("London");<br/>
 * address.setCountry(uk);<br/>
 * </p> 
 * <p>     
 * person.setAddress(address);<br/>
 * </p>
 * <p>     
 * assertThat(person, has(<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; property("firstName", equalTo("Sandro")),<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; property("age", greaterThan(18)),<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; property("address.city", equalTo("London")),<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; property("address.postcode", equalTo("1234556")),<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; property("address.country.name", equalTo("United Kingdom"))));<br/>
 * </p>
 * 
 * @author Sandro Mancuso
 */
public class BeanHas<T> extends BaseMatcher<T> {
	
	private BeanProperty<?>[] propertyMatchers;
	private Description expectedDescription = new StringDescription();
	private Description mismatchDescription = new StringDescription();

	public BeanHas(BeanProperty<?>... propertyMatchers) {
		this.propertyMatchers = propertyMatchers;
	}

	@Factory
	public static <T> BeanHas<T> has(BeanProperty<?>... propertyMatchers) {
		return new BeanHas<T>(propertyMatchers);
	}
	
	public boolean matches(Object item) {
		boolean matches = true;
		for (BeanProperty<?> matcher : propertyMatchers) {
			if (!matcher.matches(item)) {
				matches = false;
				appendDescriptions(item, matcher);
			}
		}
		return matches;
	}

	public void describeTo(Description description) {
		description.appendText(expectedDescription.toString());
	}

	@Override
	public void describeMismatch(Object item, Description description) {
		description.appendText(mismatchDescription.toString());
	}

	private void appendDescriptions(Object item, Matcher<?> matcher) {
		matcher.describeTo(expectedDescription);
		matcher.describeMismatch(item, mismatchDescription);
	}
	
}
