package org.hamcrest.generator.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.CombinableMatcher;
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

        assertThat(sugarConfiguration.factoryMethods(),
            hasItem(new FactoryMethod(SomeMatcher.class.getName(), "matcher1", "org.hamcrest.Matcher")));
        assertThat(sugarConfiguration.factoryMethods(),
            hasItem(new FactoryMethod(SomeMatcher.class.getName(), "matcher2", "org.hamcrest.Matcher")));
        assertThat(sugarConfiguration.factoryMethods(),
            hasItem(new FactoryMethod(AnotherMatcher.class.getName(), "matcher3", "org.hamcrest.CombinableMatcher")));
    }

    private InputSource createXml(String xml) {
        return new InputSource(new StringReader(xml));
    }

    // Sample Matchers

    @SuppressWarnings("unchecked")
    public static class SomeMatcher {
        @Factory
        public static Matcher matcher1() {
            return null;
        }

        @Factory
        public static Matcher matcher2() {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static class AnotherMatcher {
        @Factory
        public static CombinableMatcher matcher3() {
            return null;
        }
    }

    /**
     * Simple 'record and check' style mock. Not using a mocking library to avoid
     * cyclical dependency between mocking library and hamcrest.
     */
    private static class MockSugarConfiguration implements SugarConfiguration {

        private final List<FactoryMethod> seenFactoryMethods = new ArrayList<FactoryMethod>();
        private final List<FactoryWriter> seenFactoryWriters = new ArrayList<FactoryWriter>();

        public void addWriter(FactoryWriter factoryWriter) {
            seenFactoryWriters.add(factoryWriter);
        }

        public void addFactoryMethod(FactoryMethod method) {
            seenFactoryMethods.add(method);
        }

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
