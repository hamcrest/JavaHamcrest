package org.hamcrest.optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.optional.OptionalEmpty.emptyOptional;
import static org.hamcrest.optional.OptionalWithValue.optionalWithValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class OptionalMatchersTest {
    private static final String LS = System.lineSeparator();

    @Test
    public void checkEmptyOptional() {
        assertThat(Optional.empty(), is(emptyOptional()));
        assertThat(Optional.of(1), not(emptyOptional()));
    }

    @Test
    public void checkEmptyOptionalFailure() {
        AssertionError failure = assertThrows(AssertionError.class, () -> {
            assertThat(Optional.of(1), emptyOptional());
        });
        assertEquals(LS +
                "Expected: empty" + LS +
                "     but: is Optional[1]", failure.getMessage());
    }

    @Test
    public void checkEmptyOptionalIsFailure() {
        AssertionError failure = assertThrows(AssertionError.class, () -> {
            assertThat(Optional.of(1), is(emptyOptional()));
        });
        assertEquals(LS +
                "Expected: is empty" + LS +
                "     but: is Optional[1]", failure.getMessage());
    }

    @Test
    public void checkEmptyOptionalIsNotFailure() {
        AssertionError failure = assertThrows(AssertionError.class, () -> {
            assertThat(Optional.empty(), is(not(emptyOptional())));
        });
        assertEquals(LS +
                "Expected: is not empty" + LS +
                "     but: was <Optional.empty>", failure.getMessage());
    }

    @Test
    public void checkWithValue() {
        assertThat(Optional.empty(), not(optionalWithValue()));
        assertThat(Optional.of(1), is(optionalWithValue()));
    }

    @Test
    public void checkWithMatchingValue() {
        assertThat(Optional.empty(), not(optionalWithValue(equalTo(1))));
        assertThat(Optional.of(1), is(optionalWithValue(equalTo(1))));
        assertThat(Optional.of(1), not(optionalWithValue(equalTo(1L))));
    }

    @Test
    public void checkWithLiteralValue() {
        assertThat(Optional.empty(), not(optionalWithValue(1)));
        assertThat(Optional.of(1), is(optionalWithValue(1)));
        assertThat(Optional.of(1), not(optionalWithValue(1L)));
    }

    @Test
    public void checkWithValueFailure() {
        AssertionError failure = assertThrows(AssertionError.class, () -> {
            assertThat(Optional.empty(), is(optionalWithValue()));
        });
        assertEquals(LS +
                "Expected: is present and matches any" + LS +
                "     but: is Optional.empty", failure.getMessage());
    }

    @Test
    public void checkWithMatchingValueFailure() {
        AssertionError failure = assertThrows(AssertionError.class, () -> {
            assertThat(Optional.empty(), is(optionalWithValue(equalTo(1))));
        });
        assertEquals(LS +
                "Expected: is present and matches <1>" + LS +
                "     but: is Optional.empty", failure.getMessage());
    }

    @Test
    public void checkWithLiteralValueFailure() {
        AssertionError failure = assertThrows(AssertionError.class, () -> {
            assertThat(Optional.of("text"), is(optionalWithValue("Hello, world")));
        });
        assertEquals(LS +
                "Expected: is present and matches \"Hello, world\"" + LS +
                "     but: is Optional[text]", failure.getMessage());
    }
}
