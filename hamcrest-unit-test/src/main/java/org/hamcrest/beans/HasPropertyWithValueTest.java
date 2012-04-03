/*  Copyright (c) 2000-20010 hamcrest.org
 */
package org.hamcrest.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

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
  private final BeanWithoutInfo shouldNotMatch = new BeanWithoutInfo("not expected");

  private final BeanWithInfo beanWithInfo = new BeanWithInfo("with info");

  @Override
  protected Matcher<?> createMatcher() {
    return hasProperty("irrelevant", anything());
  }

  public void testMatchesInfolessBeanWithMatchedNamedProperty() {
    assertMatches("with property", hasProperty("property", equalTo("is expected")), shouldMatch);
    assertMismatchDescription("property 'property' was \"not expected\"", 
                              hasProperty("property", equalTo("is expected")), shouldNotMatch);
  }

  public void testMatchesBeanWithInfoWithMatchedNamedProperty() {
    assertMatches("with bean info", hasProperty("property", equalTo("with info")), beanWithInfo);
    assertMismatchDescription("property 'property' was \"with info\"", 
        hasProperty("property", equalTo("without info")), beanWithInfo);
  }

  public void testDoesNotMatchInfolessBeanWithoutMatchedNamedProperty() {
    assertMismatchDescription("No property \"nonExistentProperty\"", 
                              hasProperty("nonExistentProperty", anything()), shouldNotMatch);
   }

  public void testDoesNotMatchWriteOnlyProperty() {
    assertMismatchDescription("property \"writeOnlyProperty\" is not readable",
                              hasProperty("writeOnlyProperty", anything()), shouldNotMatch); 
  }

  public void testDescribeTo() {
    assertDescription("hasProperty(\"property\", <true>)", hasProperty("property", equalTo(true)));
  }

  public void testMatchesPropertyAndValue() {
    assertMatches("property with value", hasProperty( "property", anything()), beanWithInfo);
  }
  
  public void testDoesNotWriteMismatchIfPropertyMatches() {
    Description description = new StringDescription();
    hasProperty( "property", anything()).describeMismatch(beanWithInfo, description);
    assertEquals("Expected mismatch description", "", description.toString());
  }

  public void testDescribesMissingPropertyMismatch() {
    assertMismatchDescription("No property \"honk\"", hasProperty( "honk", anything()), shouldNotMatch);
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

    public void setWriteOnlyProperty(@SuppressWarnings("unused") float property) {
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
        return new PropertyDescriptor[] { 
            new PropertyDescriptor("property", BeanWithInfo.class, "property", null) 
          };
      } catch (IntrospectionException e) {
        throw new RuntimeException("Introspection exception: " + e.getMessage());
      }
    }
  }
}
