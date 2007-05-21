package org.hamcrest.generator;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Dumps a quick list of factory methods. Designed to be read by users, as a cheatsheet.
 *
 * @author Joe Walnes
 */
public class QuickReferenceWriter implements FactoryWriter {

    private final PrintStream out;
    private int columnPosition = 14;

    public QuickReferenceWriter(PrintStream out) {
        this.out = out;
    }

    public QuickReferenceWriter() {
        this(System.out);
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }
    
    public void writeHeader() throws IOException {
    }

    public void writeMethod(String generatedMethodName, FactoryMethod factoryMethod) throws IOException {
        String actsOn = removePackageNames(factoryMethod.getGenerifiedType());
        for (int i = actsOn.length(); i < columnPosition; i++) {
            out.append(' ');
        }
        out.append('[').append(actsOn).append("] ");
        out.append(generatedMethodName);
        out.append('(');
        boolean seenFirst = false;
        for (FactoryMethod.Parameter parameter : factoryMethod.getParameters()) {
            if (seenFirst) {
                out.append(", ");
            } else {
                seenFirst = true;
            }
            out.append(removePackageNames(parameter.getType()));
            out.append(' ');
            out.append(parameter.getName());
        }
        out.append(')');
        out.println();
    }

    private String removePackageNames(String in) {
        // Simplify fully qualified names (allowing for trailing '...').
        return in == null ? "" : in.replaceAll("[^<>]*\\.([^\\.])", "$1");
    }

    public void writeFooter() throws IOException {
    }

    public void close() throws IOException {
    }

    public void flush() throws IOException {
    }
}
