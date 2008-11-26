/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;

/**
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @since 1.1.0
 */
public class HasPropertyWithValueTest extends AbstractMatcherTest {
  private final BeanWithoutInfo shouldMatch = new BeanWithoutInfo("is expected");

  private final BeanWithoutInfo shouldNotMatch = new BeanWithoutInfo(
      "not expected");

  private final BeanWithInfo beanWithInfo = new BeanWithInfo("with info");

  @Override
  protected Matcher<?> createMatcher() {
    return hasProperty("irrelevant", anything());
  }

  public void testMatchesInfolessBeanWithMatchedNamedProperty() {
    assertThat(shouldMatch, hasProperty("property", equalTo("is expected")));
    assertThat(shouldNotMatch, not(hasProperty("property", equalTo("is expected"))));
  }

  public void testMatchesBeanWithInfoWithMatchedNamedProperty() {
    assertThat(beanWithInfo, hasProperty("property", equalTo("with info")));
  }

  public void testDoesNotMatchInfolessBeanWithoutMatchedNamedProperty() {
    assertThat(shouldNotMatch, not(hasProperty("nonExistentProperty", anything())));
  }

  public void testDoesNotMatchWriteOnlyProperty() {
    assertThat(shouldNotMatch,
        not(hasProperty("writeOnlyProperty", anything())));
  }

  public void testDescribeTo() {
    Matcher<?> matcher = equalTo(true);

    assertDescription("hasProperty(\"property\", "
        + StringDescription.asString(matcher) + ")", hasProperty("property", matcher));
  }

  public void testDoesNotWriteMismatchIfPropertyMatches() {
    HasPropertyWithValue<Object> matcher = hasProperty( "property", anything());
    assertTrue("Precondtion: Matcher should match item.", matcher.matches(beanWithInfo));
    Description description = new StringDescription();
    matcher.describeMismatch(beanWithInfo, description);
    assertEquals("Expected mismatch description", "", description.toString());
  }

  public void testDescribesMissingPropertyMismatch() {
    assertMismatchDescription("missing.", hasProperty( "honk", anything()), shouldNotMatch);
  }

  public void testDescribesMismatchingPropertyValueMismatch() {
    assertMismatchDescription("was \"not expected\".",
        hasProperty("property", equalTo("foo")), shouldNotMatch);
  }

  public void testCanAccessAnAnonymousInnerClass() {
    class X implements IX {
      public int getTest() {
        return 1;
      }
    }

    assertThat(new X(), HasPropertyWithValue.hasProperty("test", IsEqual.equalTo(1)));
  }

  interface IX {
    int getTest();
  }

  public static class BeanWithoutInfo {
    private String property;

    public BeanWithoutInfo(String property) {
      this.property = property;
    }

    public String getProperty() {
      return property;
    }

    public void setProperty(String property) {
      this.property = property;
    }

    public void setWriteOnlyProperty(float property) {
    }

    @Override
    public String toString() {
      return "[Person: " + property + "]";
    }
  }

  public static class BeanWithInfo {
    private final String propertyValue;

    public BeanWithInfo(String propertyValue) {
      this.propertyValue = propertyValue;
    }

    public String property() {
      return propertyValue;
    }
  }

  public static class BeanWithInfoBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
      try {
        return new PropertyDescriptor[] { new PropertyDescriptor("property",
            BeanWithInfo.class, "property", null) };
      } catch (IntrospectionException e) {
        throw new RuntimeException("Introspection exception: " + e.getMessage());
      }
    }
  }
}
