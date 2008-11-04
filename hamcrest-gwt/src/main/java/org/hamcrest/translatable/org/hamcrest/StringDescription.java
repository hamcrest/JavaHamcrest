package org.hamcrest;

/**
 * A {@link Description} that is stored as a string.
 */
// pmuetschard: This is the GWT translatable version, which does not use java.lang.Appendable
public class StringDescription extends BaseDescription {
    private final StringBuilder out;

    public StringDescription() {
        this(new StringBuilder());
    }

    public StringDescription(StringBuilder out) {
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
    public static String toString(SelfDescribing value) {
    	return new StringDescription().appendDescriptionOf(value).toString();
    }

    /**
     * Alias for {@link #toString(SelfDescribing)}.
     */
    public static String asString(SelfDescribing selfDescribing) {
        return toString(selfDescribing);
    }

    protected void append(String str) {
        out.append(str);
    }

    protected void append(char c) {
        out.append(c);
    }
    
    /**
     * Returns the description as a string.
     */
    public String toString() {
        return out.toString();
    }
}
