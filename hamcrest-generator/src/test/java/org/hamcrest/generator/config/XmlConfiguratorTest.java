package org.hamcrest.generator.config;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.generator.FactoryMethod;
import org.hamcrest.generator.FactoryWriter;
import org.hamcrest.generator.SugarConfiguration;
import org.junit.Test;
import org.xml.sax.InputSource;

public final class XmlConfiguratorTest {

    private final MockSugarConfiguration sugarConfiguration = new MockSugarConfiguration();
    private final XmlConfigurator config = new XmlConfigurator(sugarConfiguration);

    @Test public void
    addsMatcherFactoryMethodsToConfiguration() throws Exception {
        config.addSourceDir(new File("src/test/java-source"));
        config.load(createXml("" +
                "<matchers>" +
                "  <factory class='test.SomeMatcher'/>" +
                "  <factory class='test.AnotherMatcher'/>" +
                "</matchers>"));

        final List<FactoryMethod> result = sugarConfiguration.factoryMethods();
        assertTrue(result.contains(new FactoryMethod("test.SomeMatcher", "matcher1", "org.hamcrest.Matcher")));
        assertTrue(result.contains(new FactoryMethod("test.SomeMatcher", "matcher2", "org.hamcrest.Matcher")));
        assertTrue(result.contains(new FactoryMethod("test.AnotherMatcher", "matcher3", "org.hamcrest.MyMatcher")));
    }

    private static InputSource createXml(String xml) {
        return new InputSource(new StringReader(xml));
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
