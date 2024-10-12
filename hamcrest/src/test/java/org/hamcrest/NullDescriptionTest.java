package org.hamcrest;

import org.hamcrest.Description.NullDescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class NullDescriptionTest {

    private final NullDescription nullDescription = new Description.NullDescription();

    @Test public void
    isUnchangedByAppendedText() {
        nullDescription.appendText("myText");
        assertEquals("", nullDescription.toString());
    }

}
