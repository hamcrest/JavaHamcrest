package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class IsMapContaining implements Matcher {
    private final Matcher keyMatcher;
    private final Matcher valueMatcher;

    public IsMapContaining(Matcher keyMatcher, Matcher valueMatcher) {
        this.keyMatcher = keyMatcher;
        this.valueMatcher = valueMatcher;
    }

    public boolean match(Object o) {
        if (o != null && o instanceof Map) {
            Map map = (Map) o;

            for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Entry) i.next();

                if (keyMatcher.match(entry.getKey()) && valueMatcher.match(entry.getValue())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void describeTo(Description description) {
        description.appendText("map containing [");
        keyMatcher.describeTo(description);
        description.appendText("->");
        valueMatcher.describeTo(description);
        description.appendText("]");
    }
}
