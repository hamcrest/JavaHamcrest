package test;

import java.io.IOException;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public static class ExceptionalMatchers {

    @Factory
    public static Matcher<String> withExceptions() throws Error, IOException, RuntimeException {
        return null;
    }

}
