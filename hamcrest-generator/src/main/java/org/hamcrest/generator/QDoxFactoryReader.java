package org.hamcrest.generator;

import java.util.ArrayList;
import java.util.Collection;
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

    private JavaClass annot;

    private static final Pattern GENERIC_REGEX = Pattern.compile("^<(.*)>$");

    public QDoxFactoryReader(QDox qdox, String className) {
        this.classSource = qdox.getClassByName(className);
        this.annot = qdox.getClassByName("org.hamcrest.Factory");
    }

    @Override
    public Iterator<FactoryMethod> iterator() {
        Collection<FactoryMethod> methods = new ArrayList<FactoryMethod>();

        for (JavaMethod jm : classSource.getMethods()) {
            if (!jm.isPublic() || !jm.isStatic())
                continue;

            if (jm.getReturnType().equals(JavaType.VOID))
                continue;

            boolean skip = true;

            for (JavaAnnotation a : jm.getAnnotations()) {
                if (a.getType().equals(annot)) {
                    skip = false;
                }
            }

            if (skip)
                continue;

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

        return methods.iterator();
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
