package org.hamcrest.generator;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * API for syntactic sugar and wrapper code generation framework.
 * <p>Generally, Matcher factory methods are defined all over the place, which makes it
 * really hard to remember which methods need to be imported and doesn't allow you
 * to easily browse a list of all of them.
 * <p>This generates one uber matcher factory that delegates to all the respective classes.
 * <p>Additionally, this allows the uber-classes to do things like wrap matchers in adapters
 * (e.g. to make them EasyMock friendly) or even generate wrappers in other languages
 * (such as JRuby or Jython).
 * <p>You need to add at least one writer and at least one factory method for this to be
 * any help.
 * <h3>Usage</h3>
 * <pre>
 * SugarGenerator sugarGenerator = new SugarGenerator();
 * try {
 *   // Write out sugar as standard Hamcrest factories.
 *   sugarGenerator.addWriter(
 *     new HamcrestFactoryWriter("com.some.package", "MyMatcherClass", new FileWriter(...)));
 *   // Also write out sugar as EasyMock compatible factories.
 *   sugarGenerator.addWriter(
 *     new EasyMockFactoryWriter("com.some.package", "MyEasyMatcherClass", new FileWriter(...)));
 *   // Add a load of Matcher factories to the generated sugar. The factory methods
 *   // are read via reflection.
 *   sugarGenerator.addFactoryMethods(new ReflectiveFactoryReader(IsIn.class));
 *   sugarGenerator.addFactoryMethods(new ReflectiveFactoryReader(IsSame.class));
 *   sugarGenerator.addFactoryMethods(new ReflectiveFactoryReader(IsNot.class));
 *   // Generate everything!
 *   sugarGenerator.generate();
 * } finally {
 *   // Clean up... close all streams.
 *   sugarGenerator.close();
 * }</pre>
 *
 * @author Joe Walnes
 * @see FactoryWriter
 * @see HamcrestFactoryWriter
 * @see ReflectiveFactoryReader
 */
public class SugarGenerator implements Closeable, SugarConfiguration {

    private final List<FactoryWriter> factoryWriters = new ArrayList<FactoryWriter>();
    private final List<FactoryMethod> factoryMethods = new ArrayList<FactoryMethod>();

    /**
     * Add a writer of factories.
     */
    @Override
    public void addWriter(FactoryWriter factoryWriter) {
        factoryWriters.add(factoryWriter);
    }

    /**
     * Add a FactoryMethod that will be generated in the sugar.
     *
     * @see ReflectiveFactoryReader
     * @see FactoryMethod
     */
    @Override
    public void addFactoryMethod(FactoryMethod method) {
        factoryMethods.add(method);
    }

    /**
     * Convient way to add multiple FactoryMethods.
     *
     * @see #addFactoryMethod(FactoryMethod)
     */
    @Override
    public void addFactoryMethods(Iterable<FactoryMethod> methods) {
        for (FactoryMethod method : methods) {
            addFactoryMethod(method);
        }
    }

    /**
     * Generate all the factory methods using all the writers.
     *
     * This should always happen AFTER adding factory methods and writers.
     */
    public void generate() throws IOException {
        for (FactoryWriter factoryWriter : factoryWriters) {
            factoryWriter.writeHeader();
            for (FactoryMethod factoryMethod : factoryMethods) {
                factoryWriter.writeMethod(factoryMethod.getName(), factoryMethod);
            }
            factoryWriter.writeFooter();
            factoryWriter.flush();
        }
    }

    /**
     * Close all underlying streams. Calling this means you don't have to explicitly
     * keep track of all the File streams and close them.
     */
    @Override
    public void close() throws IOException {
        for (FactoryWriter factoryWriter : factoryWriters) {
            factoryWriter.close();
        }
    }

}
