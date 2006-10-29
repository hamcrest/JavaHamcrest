package org.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsIn.isIn;
import static org.hamcrest.core.IsIn.isInCollection;
import static org.hamcrest.core.IsNot.not;

import java.util.Arrays;
import java.util.Collection;

public class IsInTest extends AbstractMatcherTest {

    protected Matcher<?> createMatcher() {
        return isIn("irrelevant");
    }

    public void testReturnsTrueIfArgumentIsInCollection() {
        Collection<String> collection = Arrays.asList("a", "b", "c");

        assertThat("a", isInCollection(collection));
        assertThat("b", isInCollection(collection));
        assertThat("c", isInCollection(collection));
        assertThat("d", not(isInCollection(collection)));
    }

    public void testReturnsTrueIfArgumentIsInArray() {
        assertThat("a", isIn("a", "b", "c"));
        assertThat("b", isIn("a", "b", "c"));
        assertThat("c", isIn("a", "b", "c"));
        assertThat("d", not(isIn("a", "b", "c")));
    }

    public void testHasReadableDescription() {
        assertDescription("one of {\"a\", \"b\", \"c\"}", isIn("a", "b", "c"));
    }
}
