package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.equalTo;
import java.util.Map;

public class IsMapContainingValue<V> extends TypeSafeMatcher<Map<?, V>>{
    private final Matcher<? super V> valueMatcher;

    public IsMapContainingValue(Matcher<? super V> valueMatcher) {
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

    @Override
    public void describeMismatchSafely(Map<?, V> map, Description mismatchDescription) {
      mismatchDescription.appendText("map was ").appendValueList("[", ", ", "]", map.entrySet());
    }

    public void describeTo(Description description) {
        description.appendText("map with value ")
                   .appendDescriptionOf(valueMatcher);
    }
    
    @Factory
    public static <V> Matcher<? super Map<?,V>> hasValue(V value) {
        return IsMapContainingValue.<V>hasValue(equalTo(value));
    }
    
    @Factory
    public static <V> Matcher<? super Map<?,V>> hasValue(Matcher<? super V> valueMatcher) {
        return new IsMapContainingValue<V>(valueMatcher);
    }
}
