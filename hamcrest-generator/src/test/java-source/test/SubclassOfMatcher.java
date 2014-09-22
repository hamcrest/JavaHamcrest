package test;

import org.hamcrest.Factory;
import org.hamcrest.generator.SubMatcher;

public static class SubclassOfMatcher {
    @Factory public static SubMatcher<?> subclassMethod() { return null; }
}
