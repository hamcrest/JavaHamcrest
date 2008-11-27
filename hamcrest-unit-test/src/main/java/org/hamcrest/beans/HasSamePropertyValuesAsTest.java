/*  Copyright (c) 2000-2008 hamcrest.org
 */
package org.hamcrest.beans;

import static org.hamcrest.beans.HasSamePropertyValuesAs.hasSamePropertyValuesAs;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class HasSamePropertyValuesAsTest extends AbstractMatcherTest {
  private static final Value aValue = new Value("expected");
  private static final ExampleBean expectedBean = new ExampleBean("same", 1, aValue);
  private static final ExampleBean actualBean = new ExampleBean("same", 1, aValue);
  
  
  @Override
  protected Matcher<?> createMatcher() {
    return hasSamePropertyValuesAs(expectedBean);
  }

  public void testReportsMatchWhenAllPropertiesMatch() {
    assertMatches("matched properties", hasSamePropertyValuesAs(expectedBean), actualBean);
  }
  
  public void testReportsMismatchWhenActualTypeIsNotAssignableToExpectedType() {
    assertMismatchDescription("is incompatible type: ExampleBean", 
                              hasSamePropertyValuesAs((Object)aValue), actualBean);
  }

  public void testReportsMisatchOnFirstPropertyDifference() {
    assertMismatchDescription("string was \"different\"", 
        hasSamePropertyValuesAs(expectedBean), new ExampleBean("different", 1, aValue));
    assertMismatchDescription("int was <2>", 
        hasSamePropertyValuesAs(expectedBean), new ExampleBean("same", 2, aValue));
    assertMismatchDescription("value was <Value other>", 
        hasSamePropertyValuesAs(expectedBean), new ExampleBean("same", 1, new Value("other")));
  }

  public void testMatchesBeansWithInheritanceButNoExtraProperties() {
    assertMatches("sub type with same properties", 
        hasSamePropertyValuesAs(expectedBean), new SubBeanWithNoExtraProperties("same", 1, aValue));
  }

  public void testRejectsSubTypeThatHasExtraProperties() {
    assertMismatchDescription("has extra properties called [extra]", 
        hasSamePropertyValuesAs(expectedBean), new SubBeanWithExtraProperty("same", 1, aValue));
  }
  
  public void testDescribesItself() {
    assertDescription("has same property values: [int: <1>, string: \"same\", value: <Value expected>]", hasSamePropertyValuesAs(expectedBean));
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
    public String getExtra() { return "extra"; }
  }
}
