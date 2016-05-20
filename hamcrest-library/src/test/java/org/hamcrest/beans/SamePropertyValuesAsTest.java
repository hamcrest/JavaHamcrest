package org.hamcrest.beans;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

@SuppressWarnings("WeakerAccess")
public class SamePropertyValuesAsTest extends AbstractMatcherTest {
  private static final Value aValue = new Value("expected");
  private static final ExampleBean expectedBean = new ExampleBean("same", 1, aValue);
  private static final ExampleBean actualBean = new ExampleBean("same", 1, aValue);
  
  
  @Override
  protected Matcher<?> createMatcher() {
    return samePropertyValuesAs(expectedBean);
  }

  public void test_reports_match_when_all_properties_match() {
    assertMatches("matched properties", samePropertyValuesAs(expectedBean), actualBean);
  }
  
  public void test_reports_mismatch_when_actual_type_is_not_assignable_to_expected_type() {
    assertMismatchDescription("is incompatible type: ExampleBean", 
                              samePropertyValuesAs((Object)aValue), actualBean);
  }

  public void test_reports_mismatch_on_first_property_difference() {
    assertMismatchDescription("string was \"different\"", 
        samePropertyValuesAs(expectedBean), new ExampleBean("different", 1, aValue));
    assertMismatchDescription("int was <2>", 
        samePropertyValuesAs(expectedBean), new ExampleBean("same", 2, aValue));
    assertMismatchDescription("value was <Value other>", 
        samePropertyValuesAs(expectedBean), new ExampleBean("same", 1, new Value("other")));
  }

  public void test_matches_beans_with_inheritance_but_no_extra_properties() {
    assertMatches("sub type with same properties", 
        samePropertyValuesAs(expectedBean), new SubBeanWithNoExtraProperties("same", 1, aValue));
  }

  public void test_rejects_subtype_that_has_extra_properties() {
    assertMismatchDescription("has extra properties called [extra]", 
        samePropertyValuesAs(expectedBean), new SubBeanWithExtraProperty("same", 1, aValue));
  }
  
  public void testDescribesItself() {
    assertDescription("same property values as ExampleBean [int: <1>, string: \"same\", value: <Value expected>]", samePropertyValuesAs(expectedBean));
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
  
  public static class ExampleBean {
    private String stringProperty;
    private int intProperty;
    private Value valueProperty;

    public ExampleBean(String stringProperty, int intProperty, Value valueProperty) {
      this.stringProperty = stringProperty;
      this.intProperty = intProperty;
      this.valueProperty = valueProperty;
    }
    
    public String getString() {
      return stringProperty;
    }
    public int getInt() {
      return intProperty;
    }
    public Value getValue() {
      return valueProperty;
    }

    @Override public String toString() { return "an ExampleBean"; }
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
    public String getExtra() { return "extra"; }
  }
}
