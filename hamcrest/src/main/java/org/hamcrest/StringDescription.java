package org.hamcrest;

import java.io.IOException;

/**
 * A {@link Description} that is stored as a string.
 */
public class StringDescription extends BaseDescription {
    private final Appendable out;

    public StringDescription() {
        this(new StringBuilder());
    }

    public StringDescription(Appendable out) {
        this.out = out;
    }
    
    /**
     * Return the description of a {@link SelfDescribing} object as a String.
     * 
     * @param selfDescribing
     *   The object to be described.
     * @return
     *   The description of the object.
     */
    public static String toString(SelfDescribing selfDescribing) {
        return new StringDescription().appendDescriptionOf(selfDescribing).toString();
    }

    /**
     * Alias for {@link #toString(SelfDescribing)}.
     */
    public static String asString(SelfDescribing selfDescribing) {
        return toString(selfDescribing);
    }

    @Override
    protected void append(String str) {
        try {
            out.append(str);
        } catch (IOException e) {
            throw new RuntimeException("Could not write description", e);
        }
    }

    @Override
    protected void append(char c) {
        try {
            out.append(c);
        } catch (IOException e) {
            throw new RuntimeException("Could not write description", e);
        }
    }
    
    /**
     * Returns the description as a string.
     */
    @Override
    public String toString() {
        return out.toString();
    }
}
