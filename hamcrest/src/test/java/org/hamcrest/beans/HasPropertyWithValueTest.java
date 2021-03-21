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
    assertMatches("2-step path", hasPropertyAtPath("inner.property", equalTo("is expected")), new BeanWithInner(shouldMatch));
    assertMatches("3-step path", hasPropertyAtPath("inner.inner.property", equalTo("is expected")), new BeanWithInner(new BeanWithInner(shouldMatch)));

    assertMismatchDescription("inner.No property \"wrong\"", hasPropertyAtPath("inner.wrong.property", anything()), new BeanWithInner(new BeanWithInner(shouldMatch)));
    assertMismatchDescription("inner.inner.property.was \"not expected\"", hasPropertyAtPath("inner.inner.property", equalTo("something")), new BeanWithInner(new BeanWithInner(shouldNotMatch)));
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
