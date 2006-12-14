package org.hamcrest.generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * {@link FactoryWriter} that outputs Java code which simply delegates all
 * Hamcrest factory methods to factory methods elsewhere. This is useful for
 * grouping lots of matcher classes into one class, so you only have to look
 * in one place for matchers.
 *
 * @author Joe Walnes
 * @see FactoryWriter
 */
public class HamcrestFactoryWriter implements FactoryWriter {

    private final PrintWriter output;
    private final String javaPackageName;
    private final String javaClassName;

    private String indentationString = "  ";
    private String newLine = "\n";

    private int indentation = 1;

    public HamcrestFactoryWriter(String javaPackageName, String javaClassName, Writer output) {
        this.javaPackageName = javaPackageName;
        this.javaClassName = javaClassName;
        this.output = new PrintWriter(output);
    }

    public void writeHeader() throws IOException {
        output.append("// Generated source.").append(newLine)
                .append("package ").append(javaPackageName).append(';').append(newLine).append(newLine);
        output.append("public class ").append(javaClassName).append(" {").append(newLine).append(newLine);
    }

    public void writeFooter() throws IOException {
        output.append('}').append(newLine);
    }

    public void close() throws IOException {
        output.close();
    }

    public void flush() throws IOException {
        output.flush();
    }

    public void writeMethod(String generatedMethodName, FactoryMethod factoryMethodToDelegateTo)
            throws IOException {
        writeJavaDoc(factoryMethodToDelegateTo);
        indent();
        output.append("public static ");
        writeGenericTypeParameters(factoryMethodToDelegateTo);
        output.append("org.hamcrest.Matcher");
        if (factoryMethodToDelegateTo.getGenerifiedType() != null) {
            output.append('<').append(factoryMethodToDelegateTo.getGenerifiedType()).append('>');
        }
        output.append(' ').append(generatedMethodName);
        writeParameters(factoryMethodToDelegateTo);
        writeExceptions(factoryMethodToDelegateTo);
        output.append(" {").append(newLine);
        indentation++;
        writeMethodBody(factoryMethodToDelegateTo);
        indentation--;
        indent();
        output.append('}').append(newLine).append(newLine);
    }

    private void writeGenericTypeParameters(FactoryMethod factoryMethod) {
        if (!factoryMethod.getGenericTypeParameters().isEmpty()) {
            output.append('<');
            boolean seenFirst = false;
            for (String type : factoryMethod.getGenericTypeParameters()) {
                if (seenFirst) {
                    output.append(", ");
                } else {
                    seenFirst = true;
                }
                output.append(type);
            }
            output.append("> ");
        }
    }

    private void writeMethodBody(FactoryMethod factoryMethod) {
        indent();
        output.append("return ").append(factoryMethod.getMatcherClass());
        output.append('.').append(factoryMethod.getName());
        output.append('(');
        boolean seenFirst = false;
        for (FactoryMethod.Parameter parameter : factoryMethod.getParameters()) {
            if (seenFirst) {
                output.append(", ");
            } else {
                seenFirst = true;
            }
            output.append(parameter.getName());
        }
        output.append(')');
        output.append(';').append(newLine);
    }

    private void writeExceptions(FactoryMethod factoryMethod) {
        boolean seenFirst = false;
        for (String exception : factoryMethod.getExceptions()) {
            if (seenFirst) {
                output.append(", ");
            } else {
                output.append(" throws ");
                seenFirst = true;
            }
            output.append(exception);
        }
    }

    private void writeParameters(FactoryMethod factoryMethod) {
        output.append('(');
        boolean seenFirst = false;
        for (FactoryMethod.Parameter parameter : factoryMethod.getParameters()) {
            if (seenFirst) {
                output.append(", ");
            } else {
                seenFirst = true;
            }
            output.append(parameter.getType()).append(' ').append(parameter.getName());
        }
        output.append(')');
    }

    private void writeJavaDoc(FactoryMethod factoryMethod) {
        if (factoryMethod.getJavaDoc() != null) {
            String[] lines = factoryMethod.getJavaDoc().split("\n");
            if (lines.length > 0) {
                indent();
                output.append("/**").append(newLine);
                for (String line : lines) {
                    indent();
                    output.append(" * ").append(line).append(newLine);
                }
                indent();
                output.append(" */").append(newLine);
            }
        }
    }

    private void indent() {
        for (int i = 0; i < indentation; i++) {
            output.append(indentationString);
        }
    }
}
