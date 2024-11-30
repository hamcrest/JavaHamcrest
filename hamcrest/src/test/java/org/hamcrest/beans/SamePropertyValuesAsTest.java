package org.hamcrest.beans;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

@SuppressWarnings("WeakerAccess")
public class SamePropertyValuesAsTest extends AbstractMatcherTest {

  private static final Value aValue = new Value("expected");
  private static final ExampleBean expectedBean = new ExampleBean("same", 1, aValue);
  private static final ExampleBean actualBean = new ExampleBean("same", 1, aValue);
  private static final ExampleRecord expectedRecord = new ExampleRecord("same", 1, aValue);
  private static final ExampleRecord actualRecord = new ExampleRecord("same", 1, aValue);


  @Override
  protected Matcher<?> createMatcher() {
    return samePropertyValuesAs(expectedBean);
  }

  @Test
  public void test_reports_match_when_all_properties_match() {
    assertMatches("matched properties", samePropertyValuesAs(expectedBean), actualBean);
    assertMatches("matched properties", samePropertyValuesAs(expectedRecord), actualRecord);
  }

  @Test
  public void test_reports_mismatch_when_actual_type_is_not_assignable_to_expected_type() {
    assertMismatchDescription("is incompatible type: ExampleBean",
                              samePropertyValuesAs((Object)aValue), actualBean);
    assertMismatchDescription("is incompatible type: ExampleRecord",
                              samePropertyValuesAs((Object)aValue), actualRecord);
  }

  @Test
  public void test_reports_mismatch_on_first_property_difference() {
    assertMismatchDescription("stringProperty was \"different\"",
        samePropertyValuesAs(expectedBean), new ExampleBean("different", 1, aValue));
    assertMismatchDescription("intProperty was <2>",
        samePropertyValuesAs(expectedBean), new ExampleBean("same", 2, aValue));
    assertMismatchDescription("valueProperty was <Value other>",
        samePropertyValuesAs(expectedBean), new ExampleBean("same", 1, new Value("other")));

    assertMismatchDescription("stringProperty was \"different\"",
        samePropertyValuesAs(expectedRecord), new ExampleRecord("different", 1, aValue));
    assertMismatchDescription("intProperty was <2>",
        samePropertyValuesAs(expectedRecord), new ExampleRecord("same", 2, aValue));
    assertMismatchDescription("valueProperty was <Value other>",
        samePropertyValuesAs(expectedRecord), new ExampleRecord("same", 1, new Value("other")));
  }

  @Test
  public void test_matches_beans_with_inheritance_but_no_extra_properties() {
    assertMatches("sub type with same properties",
        samePropertyValuesAs(expectedBean), new SubBeanWithNoExtraProperties("same", 1, aValue));
  }

  @Test
  public void test_rejects_subtype_that_has_extra_properties() {
    assertMismatchDescription("has extra properties called [extraProperty]",
        samePropertyValuesAs(expectedBean), new SubBeanWithExtraProperty("same", 1, aValue));
  }

  @Test
  public void test_ignores_extra_subtype_properties() {
    final SubBeanWithExtraProperty withExtraProperty = new SubBeanWithExtraProperty("same", 1, aValue);
    assertMatches("extra property", samePropertyValuesAs(expectedBean, "extraProperty"), withExtraProperty);
  }

  @Test
  public void test_ignores_different_properties() {
    final ExampleBean differentBean = new ExampleBean("different", 1, aValue);
    final ExampleRecord differentRecord = new ExampleRecord("different", 1, aValue);
    assertMatches("different property", samePropertyValuesAs(expectedBean, "stringProperty"), differentBean);
    assertMatches("different property", samePropertyValuesAs(expectedRecord, "stringProperty"), differentRecord);
  }

  @Test
  public void test_accepts_missing_properties_to_ignore() {
    assertMatches("ignored property", samePropertyValuesAs(expectedBean, "notAProperty"), actualBean);
    assertMatches("ignored property", samePropertyValuesAs(expectedRecord, "notAProperty"), actualRecord);
  }

  @Test
  public void test_can_ignore_all_properties() {
    final ExampleBean differentBean = new ExampleBean("different", 2, new Value("not expected"));
    final ExampleRecord differentRecord = new ExampleRecord("different", 2, new Value("not expected"));
    assertMatches(
            "different property",
            samePropertyValuesAs(expectedBean, "stringProperty", "intProperty", "valueProperty"),
            differentBean);
    assertMatches(
            "different property",
            samePropertyValuesAs(expectedRecord, "stringProperty", "intProperty", "valueProperty"),
            differentRecord);
  }

  @Test
  public void testDescribesItself() {
    assertDescription(
            "same property values as ExampleBean [intProperty: <1>, stringProperty: \"same\", valueProperty: <Value expected>]",
            samePropertyValuesAs(expectedBean));
    assertDescription(
            "same property values as ExampleRecord [valueProperty: <Value expected>, stringProperty: \"same\", intProperty: <1>]",
            samePropertyValuesAs(expectedRecord));

    assertDescription(
            "same property values as ExampleBean [intProperty: <1>, stringProperty: \"same\", valueProperty: <Value expected>] ignoring [\"ignored1\", \"ignored2\"]",
            samePropertyValuesAs(expectedBean, "ignored1", "ignored2"));
    assertDescription(
            "same property values as ExampleRecord [valueProperty: <Value expected>, stringProperty: \"same\", intProperty: <1>] ignoring [\"ignored1\", \"ignored2\"]",
            samePropertyValuesAs(expectedRecord, "ignored1", "ignored2"));
  }

  public static class Value {
    public Value(Object value) {
      this.value = value;
    }

    public final Object value;
    @Override
    public String toString() {
      return "Value " + value;
    }
  }

  @SuppressWarnings("unused")
  public static class ExampleBean {
    private String stringProperty;
    private int intProperty;
    private Value valueProperty;

    public ExampleBean(String stringProperty, int intProperty, Value valueProperty) {
      this.stringProperty = stringProperty;
      this.intProperty = intProperty;
      this.valueProperty = valueProperty;
    }

    public String getStringProperty() {
      return stringProperty;
    }
    public int getIntProperty() {
      return intProperty;
    }
    public Value getValueProperty() {
      return valueProperty;
    }

    @Override public String toString() { return "an ExampleBean"; }
  }

  /**
   * A Java Record-like class to test the functionality of
   * {@link SamePropertyValuesAs} with Java Records in JDK 8 environment.
   * The basic property structure is the same as {@link ExampleBean ExampleBean} for the exact comparison.
   *
   * @see ExampleBean ExampleBean
   * @see <a href="https://docs.oracle.com/en/java/javase/17/language/records.html">https://docs.oracle.com/en/java/javase/17/language/records.html</a>
   */
  @SuppressWarnings("unused")
  public static final class ExampleRecord {
    private final String stringProperty;
    private final int intProperty;
    private final Value valueProperty;

    public ExampleRecord(String stringProperty, int intProperty, Value valueProperty) {
      this.stringProperty = stringProperty;
      this.intProperty = intProperty;
      this.valueProperty = valueProperty;
    }

    public String stringProperty() { return stringProperty; }
    public int intProperty() { return intProperty; }
    public Value valueProperty() { return valueProperty; }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof ExampleRecord)) return false;
      ExampleRecord that = (ExampleRecord) o;
      return this.intProperty == that.intProperty && Objects.equals(this.stringProperty, that.stringProperty) && Objects.equals(this.valueProperty, that.valueProperty);
    }

    @Override
    public int hashCode() {
      return Objects.hash(stringProperty, intProperty, valueProperty);
    }

    @Override public String toString() { return "an ExampleRecord"; }
  }

  public static class SubBeanWithNoExtraProperties extends ExampleBean {
    public SubBeanWithNoExtraProperties(String stringProperty, int intProperty, Value valueProperty) {
      super(stringProperty, intProperty, valueProperty);
    }
  }

  public static class SubBeanWithExtraProperty extends ExampleBean {
    public SubBeanWithExtraProperty(String stringProperty, int intProperty, Value valueProperty) {
      super(stringProperty, intProperty, valueProperty);
    }
    @SuppressWarnings("unused")
    public String getExtraProperty() { return "extra"; }
  }

}
