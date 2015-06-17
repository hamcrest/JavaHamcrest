package org.hamcrest.function;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.function.ApplyingMatcher.applying;
import static org.junit.Assert.*;

/**
 * Tests {@link ApplyingMatcher}.
 *
 * @author Olaf Kummer
 * @author Mark Michaelis
 */
public class ApplyingMatcherTest {
    @Test
    public void main_use_case_example_works() throws Exception {
        String expectedProfession = "plumber";
        Person kurt = new Person(expectedProfession);
        assertThat(kurt, applying(new GetProfession(), equalTo(expectedProfession)));
    }

    @Test
    public void state_is_saved_from_match_to_mismatch_message() throws Exception {
        String firstShape = "dog";
        String secondShape = "cat";
        Shapeshifter shapeshifter = new Shapeshifter(firstShape, secondShape);
        AssertionError caughtError = null;

        try {
            assertThat(shapeshifter, applying(new GetShape(), equalTo(secondShape)));
        } catch (AssertionError e) {
            caughtError = e;
        }

        assertThat("Comparison should have failed.", caughtError, notNullValue());
        assertThat("Message should contain shape from match.", caughtError.getMessage(), Matchers.containsString(firstShape));
    }

    private static class Shapeshifter {
        private final List<String> shapes;


        private Shapeshifter(String... shapes) {
            this.shapes = new ArrayList<>(Arrays.asList(shapes));
        }

        public String getShape() {
            return shapes.remove(0);
        }
    }

    private static class Person {
        private final String profession;

        private Person(final String profession) {
            this.profession = profession;
        }

        public String getProfession() {
            return profession;
        }
    }

    private static class GetProfession implements Function<Person, String> {
        @Override
        public String apply(final Person input) {
            return input.getProfession();
        }
    }

    private static class GetShape implements Function<Shapeshifter, String> {
        @Override
        public String apply(final Shapeshifter input) {
            return input.getShape();
        }
    }
}
