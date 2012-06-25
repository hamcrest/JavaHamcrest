package org.hamcrest.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.BeanProperty.property;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.beans.HasPropertyWithValueTest.BeanWithInfo;
import org.hamcrest.beans.HasPropertyWithValueTest.BeanWithoutInfo;
import org.hamcrest.core.IsEqual;

public class BeanPropertyTest extends AbstractMatcherTest {
	
	private final BeanWithoutInfo shouldMatch = new BeanWithoutInfo("is expected");
	private final BeanWithoutInfo shouldNotMatch = new BeanWithoutInfo("not expected");

	private final BeanWithInfo beanWithInfo = new BeanWithInfo("with info");
	private final ComposedBean composedBean = new ComposedBean("with info", beanWithInfo);
	
	@Override
	protected Matcher<?> createMatcher() {
		return property("irrelevant", anything());
	}
	
	public void testMatchesInfolessBeanWithMatchedNamedProperty() {
		assertMatches("with property", property("property", equalTo("is expected")), shouldMatch);
		assertMismatchDescription("property 'property' was \"not expected\"", 
		                              property("property", equalTo("is expected")), shouldNotMatch);
	}
	
	public void testMatchesBeanWithInfoWithMatchedNamedProperty() {
		assertMatches("with bean info", property("property", equalTo("with info")), beanWithInfo);
		assertMismatchDescription("property 'property' was \"with info\"", 
				property("property", equalTo("without info")), beanWithInfo);
	}

	public void testDoesNotMatchInfolessBeanWithoutMatchedNamedProperty() {
		assertMismatchDescription("No property \"nonExistentProperty\"", 
		                              property("nonExistentProperty", anything()), shouldNotMatch);
	}
	
	public void testDoesNotMatchWriteOnlyProperty() {
		assertMismatchDescription("property \"writeOnlyProperty\" is not readable",
		                              property("writeOnlyProperty", anything()), shouldNotMatch); 
	}
	
	public void testDescribeTo() {
		assertDescription("property \"property\" = <true>", property("property", equalTo(true)));
	}
	
	public void testAccumulateMismatchDescriptions() {
		Description mismatchDescription = new StringDescription();
		mismatchDescription.appendText("previous mismatch");
		assertMismatchDescription("previous mismatch, property 'property' was \"with info\"", 
				property("property", equalTo("without info")), beanWithInfo, mismatchDescription);
	}	
	
	public void testMatchesPropertyAndValue() {
		assertMatches("property with value", property( "property", anything()), beanWithInfo);
	}
	
	public void testMatchesPropertyAndValueForMemberObject() {
		assertMatches("property with value", property("beanWithInfo.property", anything()), composedBean);
	}
	
	public void testDoesNotWriteMismatchIfPropertyMatches() {
		Description description = new StringDescription();
		property( "property", anything()).describeMismatch(beanWithInfo, description);
		assertEquals("Expected mismatch description", "", description.toString());
	}
	
	public void testDescribesMissingPropertyMismatch() {
		assertMismatchDescription("No property \"honk\"", property( "honk", anything()), shouldNotMatch);
	}
	
	public void testCanAccessAnAnonymousInnerClass() {
		class X implements IX {
			@Override
			public int getTest() {
				return 1;
			}
		}

		assertThat(new X(), HasPropertyWithValue.hasProperty("test", IsEqual.equalTo(1)));
	}

	interface IX {
		int getTest();
	}
	
	public class ComposedBean {

		private String propertyValue;
		private BeanWithInfo beanWithInfo;

		public ComposedBean(String propertyValue, BeanWithInfo beanWithInfo) {
			this.propertyValue = propertyValue;
			this.beanWithInfo = beanWithInfo;
		}

		public String getProperty() {
			return this.propertyValue;
		}
		
		public BeanWithInfo getBeanWithInfo() {
			return this.beanWithInfo;
		}
	}	
	
}
