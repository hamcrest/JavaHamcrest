/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
    assertMismatchDescription("stringProperty was \"different\"",
        samePropertyValuesAs(expectedBean), new ExampleBean("different", 1, aValue));
    assertMismatchDescription("intProperty was <2>",
        samePropertyValuesAs(expectedBean), new ExampleBean("same", 2, aValue));
    assertMismatchDescription("valueProperty was <Value other>",
        samePropertyValuesAs(expectedBean), new ExampleBean("same", 1, new Value("other")));
  }

  public void test_matches_beans_with_inheritance_but_no_extra_properties() {
    assertMatches("sub type with same properties", 
        samePropertyValuesAs(expectedBean), new SubBeanWithNoExtraProperties("same", 1, aValue));
  }

  public void test_rejects_subtype_that_has_extra_properties() {
    assertMismatchDescription("has extra properties called [extraProperty]",
        samePropertyValuesAs(expectedBean), new SubBeanWithExtraProperty("same", 1, aValue));
  }

  public void test_ignores_extra_subtype_properties() {
    final SubBeanWithExtraProperty withExtraProperty = new SubBeanWithExtraProperty("same", 1, aValue);
    assertMatches("extra property", samePropertyValuesAs(expectedBean, "extraProperty"), withExtraProperty);
  }

  public void test_ignores_different_properties() {
    final ExampleBean differentBean = new ExampleBean("different", 1, aValue);
    assertMatches("different property", samePropertyValuesAs(expectedBean, "stringProperty"), differentBean);
  }

  public void test_accepts_missing_properties_to_ignore() {
    assertMatches("ignored property", samePropertyValuesAs(expectedBean, "notAProperty"), actualBean);
  }

  public void test_can_ignore_all_properties() {
    final ExampleBean differentBean = new ExampleBean("different", 2, new Value("not expected"));
    assertMatches(
            "different property",
            samePropertyValuesAs(expectedBean, "stringProperty", "intProperty", "valueProperty"),
            differentBean);
  }


  public void testDescribesItself() {
    assertDescription(
            "same property values as ExampleBean [intProperty: <1>, stringProperty: \"same\", valueProperty: <Value expected>]",
            samePropertyValuesAs(expectedBean));

    assertDescription(
            "same property values as ExampleBean [intProperty: <1>, stringProperty: \"same\", valueProperty: <Value expected>] ignoring [\"ignored1\", \"ignored2\"]",
            samePropertyValuesAs(expectedBean, "ignored1", "ignored2"));
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
