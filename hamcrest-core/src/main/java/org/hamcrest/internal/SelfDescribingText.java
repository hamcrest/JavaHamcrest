package org.hamcrest.internal;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class SelfDescribingText implements SelfDescribing {

    private final String text;

    public SelfDescribingText(String text) {
        this.text = text;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(text);
    }
}
