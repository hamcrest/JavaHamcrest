package org.hamcrest.object;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.AbstractMatcherTest.assertDescription;
import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.AbstractMatcherTest.assertMismatchDescription;
import static org.hamcrest.AbstractMatcherTest.assertNullSafe;
import static org.hamcrest.AbstractMatcherTest.assertUnknownTypeSafe;
import static org.hamcrest.object.HasField.hasField;
import static org.hamcrest.beans.HasProperty.hasProperty;
import static org.hamcrest.core.IsEqual.equalTo;

public class HasFieldTest {

    @Test
    public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Object> matcher = hasProperty("irrelevant");

        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test
    public void matchesWhenTheFieldMatches() {

        final String value = "this is a value";

        assertMatches(hasField("property", FIELD_EXTRACTOR, equalTo(value)), new Bean(value));
    }

    @Test
    public void doesNotMatchTheFieldDoesNotMatch() {
        assertDoesNotMatch(hasField("property", FIELD_EXTRACTOR, equalTo("one value")), new Bean("another value"));
    }

    @Test
    public void describesItself() {

        final String fieldName = "property";
        final String fieldValue = "value";

        assertDescription("field "+ fieldName +" is \""+ fieldValue +"\"", hasField(fieldName, FIELD_EXTRACTOR, equalTo(fieldValue)));
    }

    @Test
    public void describesAMismatch() {
        final String fieldName = "property";
        final String fieldValue = "another value";
        assertMismatchDescription(fieldName + " was \"" + fieldValue + "\"",
                hasField("property", FIELD_EXTRACTOR, equalTo("value")), new Bean("another value"));
    }

    private static final FieldExtractor<Bean, String> FIELD_EXTRACTOR = new FieldExtractor<Bean, String>() {
        @Override
        public String apply(final Bean b) {
            return b.property;
        }
    };

    private static class Bean {
        private final String property;

        private Bean(final String property) {
            this.property = property;
        }
    }

}