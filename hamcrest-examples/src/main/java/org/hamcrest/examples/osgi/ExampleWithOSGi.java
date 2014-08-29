/*  Copyright (c) 2000-2010 hamcrest.org
 */
package org.hamcrest.examples.osgi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Demonstrates that the Hamcrest's OSGi bundles can be used by some consumer.
 *
 * @author Tibor Digana (tibor17)
 */
public class ExampleWithOSGi {
    private Framework framework;

    @Before
    public void init() throws Exception {
        // Create a framework
        Map<String, String> config = new HashMap<String, String>();
        // OSGi stores its persistent data:
        config.put(Constants.FRAMEWORK_STORAGE, new File("build/osgi-cache").getCanonicalPath());
        // Request OSGi to clean its storage area on startup
        config.put(Constants.FRAMEWORK_STORAGE_CLEAN, "onFirstInit");
        // current versions of JUnit and Easymock are not OSGi bundles in this project
        // load them from system class loader
        config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "org.junit,org.easymock");
        ServiceLoader<FrameworkFactory> service = ServiceLoader.load(FrameworkFactory.class);
        FrameworkFactory frameworkFactory = service.iterator().next();
        framework = frameworkFactory.newFramework(config);
        framework.init();
    }

    @After
    public void deinit() throws Exception {
        BundleContext context = framework.getBundleContext();
        for (Bundle bundle : context.getBundles()) {
            if (bundle.getSymbolicName().equals("hamcrest-test")) {
                bundle.stop();
            }
        }

        framework.stop();
        framework.waitForStop(0);
    }

    @Test
    public void testOSGi() throws Exception {
        BundleContext context = framework.getBundleContext();
        String coreRegex = "^hamcrest-core-([^-])+.jar$";
        String libRegex = "^hamcrest-library-([^-])+.jar$";
        String intRegex = "^hamcrest-integration-([^-])+.jar$";
        String consumerReges = "^hamcrest-osgiconsumer-([^-])+.jar$";
        for (String bundle : BundlesUtil.relativePathFiles("build", coreRegex, libRegex, intRegex, consumerReges)) {
            context.installBundle("file:" + bundle);
        }

        framework.start();

        System.setProperty("osgi.test.passed", "false");

        for (Bundle bundle : context.getBundles()) {
            bundle.start();
        }

        assertThat(System.getProperty("osgi.test.passed"), is("true"));
    }
}
