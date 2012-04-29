package org.hamcrest.core;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertEquals;

public class CombinableTest {
  private static final CombinableMatcher<Integer> EITHER_3_OR_4 = CombinableMatcher.<Integer>either(equalTo(3)).or(equalTo(4));
  private static final CombinableMatcher<Integer> NOT_3_AND_NOT_4 = CombinableMatcher.<Integer>both(not(equalTo(3))).and(not(equalTo(4)));

  @Test
  public void bothAcceptsAndRejects() {
    assertThat(2, NOT_3_AND_NOT_4);
    assertThat(3, not(NOT_3_AND_NOT_4));
  }

  @Test
  public void acceptsAndRejectsThreeAnds() {
    CombinableMatcher<? super Integer> tripleAnd = NOT_3_AND_NOT_4.and(equalTo(2));
    assertThat(2, tripleAnd);
    assertThat(3, not(tripleAnd));
  }


  @Test
  public void bothDescribesItself() {
    assertEquals("(not <3> and not <4>)", NOT_3_AND_NOT_4.toString());
    assertMismatchDescription("not <3> was <3>", NOT_3_AND_NOT_4, 3);
  }

  @Test
  public void eitherAcceptsAndRejects() {
    assertThat(3, EITHER_3_OR_4);
    assertThat(6, not(EITHER_3_OR_4));
  }

  @Test
  public void acceptsAndRejectsThreeOrs() {
    final CombinableMatcher<Integer> orTriple = EITHER_3_OR_4.or(greaterThan(10));
    assertThat(11, orTriple);
    assertThat(9, not(orTriple));
  }

  @Test
  public void eitherDescribesItself() {
    Assert.assertEquals("(<3> or <4>)", EITHER_3_OR_4.toString());
    assertMismatchDescription("was <6>", EITHER_3_OR_4, 6);
  }

  @Test
  public void picksUpTypeFromLeftHandSideOfExpression() {
    assertThat("yellow", both(equalTo("yellow")).and(notNullValue()));
  }
}
