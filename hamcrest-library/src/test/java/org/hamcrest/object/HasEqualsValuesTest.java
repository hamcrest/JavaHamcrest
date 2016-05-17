package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class HasEqualsValuesTest extends AbstractMatcherTest {
    private static final WithPublicFields WITH_PUBLIC_FIELDS = new WithPublicFields(666, "a string");
    private static final HasEqualValues<WithPublicFields> WITH_PUBLIC_FIELDS_MATCHER = new HasEqualValues<>(WITH_PUBLIC_FIELDS);

    @Override
    protected Matcher<?> createMatcher() {
        return WITH_PUBLIC_FIELDS_MATCHER;
    }

    public void testDescribesItself() {
        assertDescription(
                "WithPublicFields has values [i: <666>, s: \"a string\"]",
                WITH_PUBLIC_FIELDS_MATCHER);
    }

    public void testMatchesEquivalentObject() {
        assertMatches(WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields(666, "a string"));
    }

    public void testMisMatchesFieldInequality() {
        assertMismatchDescription("'s' was \"different\"", WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields(666, "different"));
    }


    public static class WithPublicFields {
        public final int i;
        public final String s;

        public WithPublicFields(int i, String s) {
            this.i = i;
            this.s = s;
        }
    }
}
