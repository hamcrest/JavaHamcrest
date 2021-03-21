package org.hamcrest.beans;

import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.beans.HasPropertyWithValue.hasPropertyAtPath;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 * @since 1.1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class HasPropertyWithValueTest extends AbstractMatcherTest {
  private final BeanWithoutInfo shouldMatch = new BeanWithoutInfo("is expected", true);
  private final BeanWithoutInfo shouldNotMatch = new BeanWithoutInfo("not expected", false);

  private final BeanWithInfo beanWithInfo = new BeanWithInfo("with info");

  @Override
  protected Matcher<?> createMatcher() {
    return hasProperty("irrelevant", anything());
  }

  public void testMatchesBeanWithoutInfoWithMatchedNamedProperty() {
    final Matcher<BeanWithoutInfo> propertyMatcher = hasProperty("property", equalTo("is expected"));

    assertMatches("with property", propertyMatcher, shouldMatch);
    assertMismatchDescription("property 'property' was \"not expected\"", propertyMatcher, shouldNotMatch);
  }

  public void testMatchesBeanWithoutInfoWithMatchedNamedBooleanProperty() {
    final Matcher<BeanWithoutInfo> booleanPropertyMatcher = hasProperty("booleanProperty", is(true));

    assertMatches("with property", booleanPropertyMatcher, shouldMatch);
    assertMismatchDescription("property 'booleanProperty' was <false>", booleanPropertyMatcher, shouldNotMatch);
  }

  public void testMatchesBeanWithInfoWithMatchedNamedProperty() {
    assertMatches("with bean info", hasProperty("property", equalTo("with info")), beanWithInfo);
    assertMismatchDescription("property 'property' was \"with info\"", 
        hasProperty("property", equalTo("without info")), beanWithInfo);
  }

  public void testDoesNotMatchBeanWithoutInfoOrMatchedNamedProperty() {
    assertMismatchDescription("No property \"nonExistentProperty\"", 
                              hasProperty("nonExistentProperty", anything()), shouldNotMatch);
   }

  public void testDoesNotMatchWriteOnlyProperty() {
    assertMismatchDescription("property \"writeOnlyProperty\" is not readable",
                              hasProperty("writeOnlyProperty", anything()), shouldNotMatch); 
  }

  public void testMatchesPath() {
    assertMatches("1-step path", hasPropertyAtPath("property", equalTo("is expected")), shouldMatch);
    //assertMatches("2-step path", hasPropertyAtPath("inner.property", equalTo("is expected")), new BeanWithInner(shouldMatch));
    //assertMatches("3-step path", hasPropertyAtPath("inner.inner.property", equalTo("is expected")), new BeanWithInner(new BeanWithInner(shouldMatch)));

    //assertMismatchDescription("inner.No property \"wrong\"", hasPropertyAtPath("inner.wrong.property", anything()), new BeanWithInner(new BeanWithInner(shouldMatch)));
    //assertMismatchDescription("inner.inner.property.was \"not expected\"", hasPropertyAtPath("inner.inner.property", equalTo("something")), new BeanWithInner(new BeanWithInner(shouldNotMatch)));
        //@TODO: unclear how to understand and imitate chain of matchers passed
        //for matching along property graph
  }

  public void testDescribeTo() {
    assertDescription("hasProperty(\"property\", <true>)", hasProperty("property", equalTo(true)));
  }

  public void testMatchesPropertyAndValue() {
    assertMatches("property with value", hasProperty("property", anything()), beanWithInfo);
  }
  
  public void testDoesNotWriteMismatchIfPropertyMatches() {
    Description description = new StringDescription();
    hasProperty( "property", anything()).describeMismatch(beanWithInfo, description);
    assertEquals("Expected mismatch description", "", description.toString());
  }

  public void testDescribesMissingPropertyMismatch() {
    assertMismatchDescription("No property \"honk\"", hasProperty("honk", anything()), shouldNotMatch);
  }

  public void testExceptionsInBeanMethodsShouldBeReportedCorrectly() {
    assertMismatchDescription(
      "Calling 'public java.lang.String org.hamcrest.beans.HasPropertyWithValueTest$BeanWithBug.getBroken()': \"bean failed\"",
      hasProperty("broken", anything()),
      new BeanWithBug());
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
    private final boolean booleanProperty;

    public BeanWithoutInfo(String property, boolean booleanProperty) {
      this.property = property;
      this.booleanProperty = booleanProperty;
    }

    public String getProperty() {
      return property;
    }

    public void setProperty(String property) {
      this.property = property;
    }

    public boolean isBooleanProperty() { return booleanProperty; }

    public void setWriteOnlyProperty(@SuppressWarnings("unused") float property) {
    }

    @Override
    public String toString() {
      return "[Person: " + property + "]";
    }
  }

  public static class BeanWithInner {
    private final Object inner;

    public BeanWithInner(Object inner) { this.inner = inner; }
    public Object getInner() { return inner; }
  }

  public static class BeanWithInfo {
    private final String propertyValue;

    public BeanWithInfo(String propertyValue) { this.propertyValue = propertyValue; }
    public String property() { return propertyValue; }
  }

  public static class BeanWithInfoBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
      try {
        return new PropertyDescriptor[] { 
            new PropertyDescriptor("property", BeanWithInfo.class, "property", null) 
          };
      } catch (IntrospectionException e) {
        throw new AssertionError("Introspection exception", e);
      }
    }
  }

  public static class BeanWithBug {
    public String getBroken() {
      throw new BeanFailed();
    }
  }

  public static class BeanFailed extends RuntimeException {
    public BeanFailed() { super("bean failed"); }
  }
}
