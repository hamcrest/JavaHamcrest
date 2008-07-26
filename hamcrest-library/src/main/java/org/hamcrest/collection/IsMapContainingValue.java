package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.equalTo;
import java.util.Map;

public class IsMapContainingValue<V> extends TypeSafeMatcher<Map<?,V>>{
    private final Matcher<V> valueMatcher;

    public IsMapContainingValue(Matcher<V> valueMatcher) {
        this.valueMatcher = valueMatcher;
    }
    
    @Override
    public boolean matchesSafely(Map<?, V> item) {  
        for (V value : item.values()) {
            if (valueMatcher.matches(value)) {
                return true;
            }
        }
        return false;
    }

    public void describeTo(Description description) {
        description.appendText("map with value ")
                   .appendDescriptionOf(valueMatcher);
    }
    
    @Factory
    public static <V> Matcher<Map<?,V>> hasValue(V value) {
        return hasValue(equalTo(value)); 
    }
    
    @Factory
    public static <V> Matcher<Map<?,V>> hasValue(Matcher<V> valueMatcher) {
        return new IsMapContainingValue<V>(valueMatcher);
    }
}
