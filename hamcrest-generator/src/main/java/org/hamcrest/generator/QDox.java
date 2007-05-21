package org.hamcrest.generator;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;

import java.io.File;
import java.io.Reader;

/**
 * Wraps QDox library. This is because to ease distribution, QDox is bundled into
 * hamcrest-generator.jar and has its package renamed to ensure there is no conflict
 * (using Jar Jar Links). This wrapper class removes all traces of QDox from Hamcrest's
 * public API, so there aren't any compatibility problems.
 *
 * @author Joe Walnes
 */
public class QDox {

    private final JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

    public void addSourceTree(File sourceDir) {
        javaDocBuilder.addSourceTree(sourceDir);
    }

    public void addSource(Reader reader) {
        javaDocBuilder.addSource(reader);
    }

    JavaClass getClassByName(String className) {
        return javaDocBuilder.getClassByName(className);
    }
}
