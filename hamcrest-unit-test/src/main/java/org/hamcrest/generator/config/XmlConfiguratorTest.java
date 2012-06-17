package org.hamcrest.generator.config;

import junit.framework.TestCase;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.CombinableMatcher;
import org.hamcrest.generator.FactoryMethod;
import org.hamcrest.generator.FactoryWriter;
import org.hamcrest.generator.SugarConfiguration;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

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
            hasItem(new FactoryMethod(SomeMatcher.class.getName().replace('$', '.'), "matcher1", "org.hamcrest.Matcher")));
        assertThat(sugarConfiguration.factoryMethods(),
            hasItem(new FactoryMethod(SomeMatcher.class.getName().replace('$', '.'), "matcher2", "org.hamcrest.Matcher")));
        assertThat(sugarConfiguration.factoryMethods(),
            hasItem(new FactoryMethod(AnotherMatcher.class.getName().replace('$', '.'), "matcher3", "org.hamcrest.CombinableMatcher")));
    }

    private static InputSource createXml(String xml) {
        return new InputSource(new StringReader(xml));
    }

    // Sample Matchers

    @SuppressWarnings("rawtypes")
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

    @SuppressWarnings("rawtypes")
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
