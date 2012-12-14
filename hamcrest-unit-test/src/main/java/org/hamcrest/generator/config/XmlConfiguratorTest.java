package org.hamcrest.generator.config;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.generator.FactoryMethod;
import org.hamcrest.generator.FactoryWriter;
import org.hamcrest.generator.SugarConfiguration;
import org.xml.sax.InputSource;

public class XmlConfiguratorTest extends TestCase {

    private MockSugarConfiguration sugarConfiguration;
    private XmlConfigurator config;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sugarConfiguration = new MockSugarConfiguration();
        config = new XmlConfigurator(sugarConfiguration, getClass().getClassLoader());
    }

    public void testAddsMatcherFactoryMethodsToConfiguration() throws Exception {
        config.load(createXml("" +
                "<matchers>" +
                "  <factory class='org.hamcrest.generator.config.XmlConfiguratorTest$SomeMatcher'/>" +
                "  <factory class='org.hamcrest.generator.config.XmlConfiguratorTest$AnotherMatcher'/>" +
                "</matchers>"));

        final List<FactoryMethod> result = sugarConfiguration.factoryMethods();
        assertTrue(result.contains(new FactoryMethod(SomeMatcher.class.getName().replace('$', '.'), "matcher1", "org.hamcrest.Matcher")));
        assertTrue(result.contains(new FactoryMethod(SomeMatcher.class.getName().replace('$', '.'), "matcher2", "org.hamcrest.Matcher")));
        assertTrue(result.contains(new FactoryMethod(AnotherMatcher.class.getName().replace('$', '.'), "matcher3", "org.hamcrest.MyMatcher")));
    }

    private static InputSource createXml(String xml) {
        return new InputSource(new StringReader(xml));
    }

    // Sample Matchers

    @SuppressWarnings("rawtypes")
    public static class SomeMatcher {
        @Factory public static Matcher matcher1() { return null; }
        @Factory public static Matcher matcher2() { return null; }
    }

    @SuppressWarnings("rawtypes")
    public static class AnotherMatcher<T> implements Matcher<T> {
        @Override public void describeTo(Description description) { }
        @Override public boolean matches(Object item) { return false; }
        @Override public void describeMismatch(Object item, Description mismatchDescription) { }
        @Override @Deprecated public void _dont_implement_Matcher___instead_extend_BaseMatcher_() { }
        @Factory public static AnotherMatcher matcher3() { return null; }
    }

    /**
     * Simple 'record and check' style mock. Not using a mocking library to avoid
     * cyclic dependency between mocking library and hamcrest.
     */
    private static final class MockSugarConfiguration implements SugarConfiguration {

        private final List<FactoryMethod> seenFactoryMethods = new ArrayList<FactoryMethod>();
        private final List<FactoryWriter> seenFactoryWriters = new ArrayList<FactoryWriter>();

        @Override
        public void addWriter(FactoryWriter factoryWriter) {
            seenFactoryWriters.add(factoryWriter);
        }

        @Override
        public void addFactoryMethod(FactoryMethod method) {
            seenFactoryMethods.add(method);
        }

        @Override
        public void addFactoryMethods(Iterable<FactoryMethod> methods) {
            for (FactoryMethod method : methods) {
                addFactoryMethod(method);
            }
        }

        public List<FactoryMethod> factoryMethods() {
            return seenFactoryMethods;
        }
    }
}
