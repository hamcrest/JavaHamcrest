package org.hamcrest.generator;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

public final class QuickReferenceWriterTest {

    private final ByteArrayOutputStream actualBuffer = new ByteArrayOutputStream();
    private final ByteArrayOutputStream expectedBuffer = new ByteArrayOutputStream();
    private final PrintStream expected = new PrintStream(expectedBuffer);
    private final QuickReferenceWriter writer = new QuickReferenceWriter(new PrintStream(actualBuffer));

    @Test public void
    writesSimplifiedSummaryOfMatchers() throws IOException {
        FactoryMethod namedMethod = new FactoryMethod("SomeClass", "someMethod", "unusedReturnType");
        namedMethod.addParameter("Cheese", "a");
        namedMethod.addParameter("int", "b");
        namedMethod.setGenerifiedType("String");
        writer.writeMethod("namedMethod", namedMethod);

        FactoryMethod anotherMethod = new FactoryMethod("SomeClass", "anotherMethod", "unusedReturnType");
        anotherMethod.setGenerifiedType("int");
        writer.writeMethod("anotherMethod", anotherMethod);

        expected.println("        [String] namedMethod(Cheese a, int b)");
        expected.println("           [int] anotherMethod()");
        verify();
    }

    @Test public void
    removesPackageNames() throws IOException {
        FactoryMethod namedMethod = new FactoryMethod("SomeClass", "someMethod", "unusedReturnType");
        namedMethod.addParameter("com.blah.Foo", "a");
        namedMethod.addParameter("com.foo.Cheese<x.y.Zoo>", "b");
        namedMethod.setGenerifiedType("java.lang.Cheese");
        writer.writeMethod("namedMethod", namedMethod);

        expected.println("        [Cheese] namedMethod(Foo a, Cheese<Zoo> b)");
        verify();
    }

    private void verify() {
        assertEquals(new String(expectedBuffer.toByteArray()), new String(actualBuffer.toByteArray()));
    }
}
