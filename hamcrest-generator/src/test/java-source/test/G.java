package test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public static class G {

    @Factory
    public static <T, V extends List<String> & Comparable<String>> Matcher<Map<T, V[]>> x(Set<T> t, V v) {
        return null;
    }

}
