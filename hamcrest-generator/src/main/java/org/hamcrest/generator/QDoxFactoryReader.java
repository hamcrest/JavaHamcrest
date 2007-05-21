package org.hamcrest.generator;

import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Wraps an existing sequence of FactoryMethods, and attempts to pull in
 * parameter names and JavaDoc (which aren't available using reflection) using
 * QDox.
 *
 * @see <a href="http://qdox.codehaus.org/">QDox</a>
 * @author Joe Walnes
 */
public class QDoxFactoryReader implements Iterable<FactoryMethod> {

    private final Iterable<FactoryMethod> wrapped;
    private final JavaClass classSource;

    private static final Pattern GENERIC_REGEX = Pattern.compile("<.*>");
    private static final Pattern VARARGS_REGEX = Pattern.compile("...", Pattern.LITERAL);

    public QDoxFactoryReader(Iterable<FactoryMethod> wrapped, QDox qdox, String className) {
        this.wrapped = wrapped;
        this.classSource = qdox.getClassByName(className);
    }

    public Iterator<FactoryMethod> iterator() {
        final Iterator<FactoryMethod> iterator = wrapped.iterator();
        return new Iterator<FactoryMethod>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }

            public FactoryMethod next() {
                return enhance(iterator.next());
            }

            public void remove() {
                iterator.remove();
            }
        };
    }

    private FactoryMethod enhance(FactoryMethod factoryMethod) {
        JavaMethod methodSource = findMethodInSource(factoryMethod);
        if (methodSource != null) {
            factoryMethod.setJavaDoc(createJavaDocComment(methodSource));
            JavaParameter[] parametersFromSource
                    = methodSource.getParameters();
            List<FactoryMethod.Parameter> parametersFromReflection
                    = factoryMethod.getParameters();

            if (parametersFromReflection.size() == parametersFromSource.length) {
                for (int i = 0; i < parametersFromSource.length; i++) {
                    parametersFromReflection.get(i).setName(
                            parametersFromSource[i].getName());
                }
            }
        }
        return factoryMethod;
    }

    /**
     * Attempts to locate the source code for a specific method, by cross-referencing
     * the signature returned by reflection with the list of methods parsed by QDox.
     */
    private JavaMethod findMethodInSource(FactoryMethod factoryMethod) {
        // Note, this doesn't always work - it struggles with some kinds of generics.
        // This seems to cover most cases though.
        List<FactoryMethod.Parameter> params = factoryMethod.getParameters();
        Type[] types = new Type[params.size()];
        for (int i = 0; i < types.length; i++) {
            // QDox ignores varargs and generics, so we strip them out to help QDox.
            String type = params.get(i).getType();
            type = GENERIC_REGEX.matcher(type).replaceAll("");
            type = VARARGS_REGEX.matcher(type).replaceAll("");
            types[i] = new Type(type);
        }
        JavaMethod[] methods = classSource.getMethodsBySignature(
                factoryMethod.getName(), types, false);
        if (methods.length == 1) {
            return methods[0];
        } else {
            return null;
        }
    }

    /**
     * Reconstructs the JavaDoc as a string for a particular method.
     */
    private String createJavaDocComment(JavaMethod methodSource) {
        String comment = methodSource.getComment();
        DocletTag[] tags = methodSource.getTags();
        if ((comment == null || comment.trim().length() == 0) && tags.length == 0) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        result.append(comment);
        result.append("\n\n");
        for (DocletTag tag : tags) {
            result.append('@').append(tag.getName())
                    .append(' ').append(tag.getValue())
                    .append('\n');
        }
        return result.toString();
    }

}
