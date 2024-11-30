package org.hamcrest.beans;

import org.hamcrest.*;
import org.hamcrest.core.IsEqual;
import org.hamcrest.test.AbstractMatcherTest;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.beans.HasPropertyWithValue.hasPropertyAtPath;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

  private final RecordLikeBeanWithoutInfo recordShouldMatch = new RecordLikeBeanWithoutInfo("is expected", true);
  private final RecordLikeBeanWithoutInfo recordShouldNotMatch = new RecordLikeBeanWithoutInfo("not expected", false);

  private final BeanWithInfo beanWithInfo = new BeanWithInfo("with info");

  @Override
  protected Matcher<?> createMatcher() {
    return hasProperty("irrelevant", anything());
  }

  @Test
  public void testMatchesBeanWithoutInfoWithMatchedNamedProperty() {
    final Matcher<BeanWithoutInfo> propertyMatcher = hasProperty("property", equalTo("is expected"));

    assertMatches("with property", propertyMatcher, shouldMatch);
    assertMismatchDescription("property 'property' was \"not expected\"", propertyMatcher, shouldNotMatch);
  }

  @Test
  public void testMatchesRecordLikeBeanWithoutInfoWithMatchedNamedProperty() {
    final Matcher<RecordLikeBeanWithoutInfo> propertyMatcher = hasProperty("property", equalTo("is expected"));

    assertMatches("with property", propertyMatcher, recordShouldMatch);
    assertMismatchDescription("property 'property' was \"not expected\"", propertyMatcher, recordShouldNotMatch);
  }

  @Test
  public void testMatchesBeanWithoutInfoWithMatchedNamedBooleanProperty() {
    final Matcher<BeanWithoutInfo> booleanPropertyMatcher = hasProperty("booleanProperty", is(true));

    assertMatches("with property", booleanPropertyMatcher, shouldMatch);
    assertMismatchDescription("property 'booleanProperty' was <false>", booleanPropertyMatcher, shouldNotMatch);
  }

  @Test
  public void testMatchesRecordLikeBeanWithoutInfoWithMatchedNamedBooleanProperty() {
    final Matcher<RecordLikeBeanWithoutInfo> booleanPropertyMatcher = hasProperty("booleanProperty", is(true));

    assertMatches("with property", booleanPropertyMatcher, recordShouldMatch);
    assertMismatchDescription("property 'booleanProperty' was <false>", booleanPropertyMatcher, recordShouldNotMatch);
  }

  @Test
  public void testMatchesBeanWithInfoWithMatchedNamedProperty() {
    assertMatches("with bean info", hasProperty("property", equalTo("with info")), beanWithInfo);
    assertMismatchDescription("property 'property' was \"with info\"",
        hasProperty("property", equalTo("without info")), beanWithInfo);
  }

  @Test
  public void testDoesNotMatchBeanWithoutInfoOrMatchedNamedProperty() {
    assertMismatchDescription("No property \"nonExistentProperty\"",
                              hasProperty("nonExistentProperty", anything()), shouldNotMatch);
  }

  @Test
  public void testDoesNotMatchRecordLikeBeanWithoutInfoOrMatchedNamedProperty() {
    assertMismatchDescription("No property \"nonExistentProperty\"",
                              hasProperty("nonExistentProperty", anything()), recordShouldNotMatch);
  }

  @Test
  public void testDoesNotMatchWriteOnlyProperty() {
    assertMismatchDescription("property \"writeOnlyProperty\" is not readable",
                              hasProperty("writeOnlyProperty", anything()), shouldNotMatch);
  }

  @Test
  public void testMatchesPath() {
    assertMatches("1-step path", hasPropertyAtPath("property", equalTo("is expected")), shouldMatch);
    assertMatches("2-step path", hasPropertyAtPath("inner.property", equalTo("is expected")), new BeanWithInner(shouldMatch));
    assertMatches("3-step path", hasPropertyAtPath("inner.inner.property", equalTo("is expected")), new BeanWithInner(new BeanWithInner(shouldMatch)));

    assertMismatchDescription("inner.No property \"wrong\"", hasPropertyAtPath("inner.wrong.property", anything()), new BeanWithInner(new BeanWithInner(shouldMatch)));
    assertMismatchDescription("inner.inner.property.was \"not expected\"", hasPropertyAtPath("inner.inner.property", equalTo("something")), new BeanWithInner(new BeanWithInner(shouldNotMatch)));
  }

  @Test
  public void testMatchesPathForJavaRecords() {
    assertMatches("1-step path", hasPropertyAtPath("property", equalTo("is expected")), recordShouldMatch);
    assertMatches("2-step path", hasPropertyAtPath("inner.property", equalTo("is expected")), new BeanWithInner(recordShouldMatch));
    assertMatches("3-step path", hasPropertyAtPath("inner.inner.property", equalTo("is expected")), new BeanWithInner(new BeanWithInner(recordShouldMatch)));

    assertMismatchDescription("inner.No property \"wrong\"", hasPropertyAtPath("inner.wrong.property", anything()), new BeanWithInner(new BeanWithInner(recordShouldMatch)));
    assertMismatchDescription("inner.inner.property.was \"not expected\"", hasPropertyAtPath("inner.inner.property", equalTo("something")), new BeanWithInner(new BeanWithInner(recordShouldNotMatch)));
  }

  @Test
  public void testDescribeTo() {
    assertDescription("hasProperty(\"property\", <true>)", hasProperty("property", equalTo(true)));
  }

  @Test
  public void testMatchesPropertyAndValue() {
    assertMatches("property with value", hasProperty("property", anything()), beanWithInfo);
  }

  @Test
  public void testMatchesPropertyAndValueWithJavaRecords() {
    assertMatches("property with value", hasProperty("property", anything()), recordShouldMatch);
  }

  @Test
  public void testDoesNotWriteMismatchIfPropertyMatches() {
    Description description = new StringDescription();
    hasProperty( "property", anything()).describeMismatch(beanWithInfo, description);
    assertEquals("", description.toString(), "Expected mismatch description");
  }

  @Test
  public void testDescribesMissingPropertyMismatch() {
    assertMismatchDescription("No property \"honk\"", hasProperty("honk", anything()), shouldNotMatch);
  }

  @Test
  public void testExceptionsInBeanMethodsShouldBeReportedCorrectly() {
    assertMismatchDescription(
      "Calling 'public java.lang.String org.hamcrest.beans.HasPropertyWithValueTest$BeanWithBug.getBroken()': \"bean failed\"",
      hasProperty("broken", anything()),
      new BeanWithBug());
  }

  @Test
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

  @SuppressWarnings("WeakerAccess")
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

  /**
   * A Java Record-like class to test the functionality of
   * {@link HasProperty}, {@link HasPropertyWithValue}
   * with Java Records in JDK 8 environment.
   *
   * @see <a href="https://docs.oracle.com/en/java/javase/17/language/records.html">https://docs.oracle.com/en/java/javase/17/language/records.html</a>
   */
  public static final class RecordLikeBeanWithoutInfo {
    private final String property;
    private final boolean booleanProperty;

    public RecordLikeBeanWithoutInfo(String property, boolean booleanProperty) {
      this.property = property;
      this.booleanProperty = booleanProperty;
    }

    public String property() { return this.property; }
    public boolean booleanProperty() { return this.booleanProperty; }
    public void notAGetterMethod() {}

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof RecordLikeBeanWithoutInfo)) return false;
      RecordLikeBeanWithoutInfo that = (RecordLikeBeanWithoutInfo) o;
      return Objects.equals(this.property, that.property) && this.booleanProperty == that.booleanProperty;
    }

    @Override
    public int hashCode() {
      return Objects.hash(property, booleanProperty);
    }

    @Override
    public String toString() {
      return "[Person: " + property + "]";
    }
  }

  @SuppressWarnings("WeakerAccess")
  public static class BeanWithInner {
    private final Object inner;

    public BeanWithInner(Object inner) { this.inner = inner; }
    public Object getInner() { return inner; }
  }

  @SuppressWarnings("WeakerAccess")
  public static class BeanWithInfo {
    private final String propertyValue;

    public BeanWithInfo(String propertyValue) { this.propertyValue = propertyValue; }
    public String property() { return propertyValue; }
  }

  public static class BeanWithInfoBeanInfo extends SimpleBeanInfo { // TODO: No usage. Can be removed.
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

  @SuppressWarnings("WeakerAccess")
  public static class BeanWithBug {
    public String getBroken() {
      throw new BeanFailed();
    }
  }

  @SuppressWarnings("WeakerAccess")
  public static class BeanFailed extends RuntimeException {
    public BeanFailed() { super("bean failed"); }
  }

}
