package org.hamcrest.collection;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsMapContainingKey<K> extends TypeSafeMatcher<Map<? super K,?>> {
    private final Matcher<? super K> keyMatcher;
    
    public IsMapContainingKey(Matcher<? super K> keyMatcher) {
        this.keyMatcher = keyMatcher;
    }
    
    @Override
    public boolean matchesSafely(Map<? super K, ?> item) {
        for (Object key : item.keySet()) {
            if (keyMatcher.matches(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeMismatchSafely(Map<? super K, ?> map, Description mismatchDescription) {
      mismatchDescription.appendText("map was ").appendValueList("[", ", ", "]", map.entrySet());
    }

    public void describeTo(Description description) {
        description.appendText("map with key ")
                   .appendDescriptionOf(keyMatcher);
    }

    @Factory
    public static <K> Matcher<Map<? super K,?>> hasKey(K key) {
      // does not forward to other hasKey method to help compiler cope with type inference
      return new IsMapContainingKey<K>(equalTo(key));
    }
    
    @Factory
    public static <K> Matcher<Map<? super K,?>> hasKey(Matcher<? super K> keyMatcher) {
      return new IsMapContainingKey<K>(keyMatcher);
    }
}
