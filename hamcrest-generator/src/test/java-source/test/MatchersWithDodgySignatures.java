package test;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public static class MatchersWithDodgySignatures {

    @Factory
    public Matcher<String> notStatic() {
        return null;
    }

    @Factory
    static Matcher<String> notPublic() {
        return null;
    }

    public static Matcher<String> noAnnotation() {
        return null;
    }

    @Factory
    public static Matcher<String> goodMethod() {
        return null;
    }

    @Factory
    public static String anotherGoodMethod() {
        return null;
    }

    @Factory
    public static void wrongReturnType() {
    }

}
