package org.hamcrest.examples.osgi;

import org.hamcrest.JMock1Matchers;
import org.hamcrest.StringDescription;
import org.hamcrest.collection.IsArrayContaining;
import org.hamcrest.core.Is;
import org.hamcrest.integration.EasyMock2Adapter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import static org.junit.Assert.fail;

/**
 * Attempts to fail the OSGi framework as soon as core's classes
 * which are supposed to be successfully imported cannot be loaded.
 *
 * @author Tibor Digana (tibor17)
 */
public class TestActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        new Is(null);
        // see the section Require-Bundle in library-consumer.jar!/META-INF/MANIFEST.MF
        new StringDescription();
        try {
            Class.forName("org.hamcrest.internal.ArrayIterator");
            fail();
        } catch (ClassNotFoundException e) {
            // expected: org.hamcrest.internal is private package in osgi bundle
        }

        new IsArrayContaining(null);

        new EasyMock2Adapter(null);
        // see the section Require-Bundle in library-consumer.jar!/META-INF/MANIFEST.MF
        new JMock1Matchers();
        System.setProperty("osgi.test.passed", "true");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    }
}
