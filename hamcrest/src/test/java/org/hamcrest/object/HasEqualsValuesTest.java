package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.Collections;

@SuppressWarnings("WeakerAccess")
public class HasEqualsValuesTest extends AbstractMatcherTest {

    private static final WithPublicFields WITH_PUBLIC_FIELDS = new WithPublicFields('x', 666, "a string", false);
    private static final HasEqualValues<WithPublicFields> WITH_PUBLIC_FIELDS_MATCHER = new HasEqualValues<>(WITH_PUBLIC_FIELDS, Collections.singleton("ignorable"));

    @Override
    protected Matcher<?> createMatcher() {
        return WITH_PUBLIC_FIELDS_MATCHER;
    }

    public void test_describes_itself() {
        assertDescription(
                "WithPublicFields has values [i: <666>, s: \"a string\", c: \"x\"]",
                WITH_PUBLIC_FIELDS_MATCHER);
    }

    public void test_matches_equivalent_object() {
        assertMatches(WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields('x', 666, "a string", true));
    }

    public void test_mismatches_on_first_field_inequality() {
        assertMismatchDescription(
                "'s' was \"different\"",
                WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields('x', 666, "different", true));
    }

    public void test_mismatches_on_inherited_field() {
        assertMismatchDescription(
                "'c' was \"y\"",
                WITH_PUBLIC_FIELDS_MATCHER, new WithPublicFields('y', 666, "a string", true));
    }

    public static class WithPublicFields extends Parent {
        public final int i;
        public final String s;
        public final boolean ignorable;

        public WithPublicFields(char c, int i, String s, boolean ignorable) {
            super(c);
            this.i = i;
            this.s = s;
            this.ignorable = ignorable;
        }
    }

    public static class Parent {
        public final char c;

        public Parent(char c) {
            this.c = c;
        }
    }

}
