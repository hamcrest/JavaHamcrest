package org.hamcrest.generator;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * Writes syntactic sugar code for factories.
 * <p>Implementations of this could include vanilla factory methods for
 * Hamcrest matchers, wrapped factories for other libraries or factories
 * in other languages (jython, jruby, groovy, etc).
 * <h3>Usage:</h3>
 * <pre>
 * writer.writeHeader(...);
 * <p/>
 * writer.writeMethod(...);
 * writer.writeMethod(...);
 * writer.writeMethod(...);
 * ...
 * writer.writeFooter(...);
 * writer.close();
 * </pre>
 *
 * @author Joe Walnes
 * @see FactoryMethod
 * @see SugarGenerator
 * @see HamcrestFactoryWriter
 */
public interface FactoryWriter extends Closeable, Flushable {

    /**
     * Write the code header.
     */
    void writeHeader() throws IOException;

    /**
     * Writes code that delegates to a method.
     *
     * @param generatedMethodName
     * @param factoryMethodToDelegateTo
     */
    void writeMethod(String generatedMethodName, FactoryMethod factoryMethodToDelegateTo) throws IOException;

    /**
     * Write any necessary code to finish the output.
     */
    void writeFooter() throws IOException;

}
