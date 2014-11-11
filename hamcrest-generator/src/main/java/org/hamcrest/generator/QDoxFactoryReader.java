package org.hamcrest.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaType;
import com.thoughtworks.qdox.model.JavaTypeVariable;

/**
 * Wraps an existing sequence of FactoryMethods, and attempts to pull in
 * parameter names and JavaDoc (which aren't available using reflection) using
 * QDox.
 *
 * @see <a href="http://qdox.codehaus.org/">QDox</a>
 * @author Joe Walnes
 */
public class QDoxFactoryReader implements Iterable<FactoryMethod> {

    private final JavaClass classSource;

    private JavaClass factoryAnnotation;

    private static final Pattern GENERIC_REGEX = Pattern.compile("^<(.*)>$");

    public QDoxFactoryReader(QDox qdox, String className) {
        this.classSource = qdox.getClassByName(className);
        this.factoryAnnotation = qdox.getClassByName("org.hamcrest.Factory");
    }

    @Override
    public Iterator<FactoryMethod> iterator() {
        List<FactoryMethod> methods = new ArrayList<FactoryMethod>();

        for (JavaMethod jm : classSource.getMethods()) {
            if (!isFactoryMethod(jm)) {
                continue;
            }

            FactoryMethod fm = new FactoryMethod(typeToString(classSource), jm.getName(), typeToString(jm.getReturnType()));

            for (JavaTypeVariable<?> tv : jm.getTypeParameters()) {
                String decl = tv.getGenericFullyQualifiedName();
                decl = GENERIC_REGEX.matcher(decl).replaceFirst("$1");
                fm.addGenericTypeParameter(decl);
            }

            for (JavaParameter p : jm.getParameters()) {
                String type = typeToString(p.getType());

                // Special case for var args methods.... String[] -> String...
                if (p.isVarArgs()) {
                    type += "...";
                }

                fm.addParameter(type, p.getName());
            }

            for (JavaType exception : jm.getExceptions()) {
                fm.addException(typeToString(exception));
            }

            fm.setJavaDoc(createJavaDocComment(jm));

            String generifiedType = GENERIC_REGEX.matcher(fm.getReturnType()).replaceFirst("$1");
            if (!generifiedType.equals(fm.getReturnType())) {
                fm.setGenerifiedType(generifiedType);
            }

            methods.add(fm);
        }

        Collections.sort(methods, new Comparator<FactoryMethod>() {
            @Override public int compare(FactoryMethod o1, FactoryMethod o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return methods.iterator();
    }

    /**
     * Determine whether a particular method is classified as a matcher factory method.
     * <p/>
     * <p>The rules for determining this are:
     * 1. The method must be public static.
     * 2. It must have a return type of org.hamcrest.Matcher (or something that extends this).
     * 3. It must be marked with the org.hamcrest.Factory annotation.
     * <p/>
     * <p>To use another set of rules, override this method.
     */
    protected boolean isFactoryMethod(JavaMethod javaMethod) {
        return javaMethod.isStatic()
                && javaMethod.isPublic()
                && hasFactoryAnnotation(javaMethod)
                && !javaMethod.getReturnType().equals(JavaType.VOID);
    }

    private boolean hasFactoryAnnotation(JavaMethod javaMethod) {
        for (JavaAnnotation a : javaMethod.getAnnotations()) {
            if (a.getType().equals(factoryAnnotation)) {
                return true;
            }
        }
        return false;
    }

    private static String typeToString(JavaType type) {
        return type.getGenericFullyQualifiedName().replace('$', '.');
    }

    /**
     * Reconstructs the JavaDoc as a string for a particular method.
     */
    private static String createJavaDocComment(JavaMethod methodSource) {
        String comment = methodSource.getComment();
        List<DocletTag> tags = methodSource.getTags();
        if ((comment == null || comment.trim().length() == 0) && tags.isEmpty()) {
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
