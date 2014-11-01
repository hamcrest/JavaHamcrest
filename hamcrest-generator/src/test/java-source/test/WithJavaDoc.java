package test;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public static class WithJavaDoc {

    /**
     * Look at me!
     *
     * @return something
     */
    @Factory
    public static Matcher<String> documented() {
        return null;
    }

}
