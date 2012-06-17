package org.hamcrest.generator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Iterator;

/**
 * Reads a list of Hamcrest factory methods from a class, using standard Java reflection.
 * <h3>Usage</h3>
 * <pre>
 * for (FactoryMethod method : new ReflectiveFactoryReader(MyMatchers.class)) {
 *   ...
 * }
 * </pre>
 * <p>All methods matching signature '@Factory public static Matcher<blah> blah(blah)' will be
 * treated as factory methods. To change this behavior, override {@link #isFactoryMethod(Method)}.
 * <p>Caveat: Reflection is hassle-free, but unfortunately cannot expose method parameter names or JavaDoc
 * comments, making the sugar slightly more obscure.
 *
 * @author Joe Walnes
 * @see SugarGenerator
 * @see FactoryMethod
 */
public class ReflectiveFactoryReader implements Iterable<FactoryMethod> {

    private final Class<?> cls;

    private final ClassLoader classLoader;

    public ReflectiveFactoryReader(Class<?> cls) {
        this.cls = cls;
        this.classLoader = cls.getClassLoader();
    }

    @Override
    public Iterator<FactoryMethod> iterator() {
        return new Iterator<FactoryMethod>() {

            private int currentMethod = -1;
            private Method[] allMethods = cls.getMethods();

            @Override
            public boolean hasNext() {
                while (true) {
                    currentMethod++;
                    if (currentMethod >= allMethods.length) {
                        return false;
                    } else if (isFactoryMethod(allMethods[currentMethod])) {
                        return true;
                    } // else carry on looping and try the next one.
                }
            }

            @Override
            public FactoryMethod next() {
                if (outsideArrayBounds()) {
                  throw new IllegalStateException("next() called without hasNext() check.");
                }
                return buildFactoryMethod(allMethods[currentMethod]);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private boolean outsideArrayBounds() {
              return currentMethod < 0 || allMethods.length <= currentMethod;
            }
        };
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
    protected boolean isFactoryMethod(Method javaMethod) {
        return isStatic(javaMethod.getModifiers())
                && isPublic(javaMethod.getModifiers())
                && hasFactoryAnnotation(javaMethod)
                && !Void.TYPE.equals(javaMethod.getReturnType());
    }

    @SuppressWarnings("unchecked")
    private boolean hasFactoryAnnotation(Method javaMethod) {
      // We dynamically load the Factory class, to avoid a compile time
      // dependency on org.hamcrest.Factory. This gets around
      // a circular bootstrap issue (because generator is required to
      // compile core).
      try {
        final Class<?> factoryClass = classLoader.loadClass("org.hamcrest.Factory");
        if (!Annotation.class.isAssignableFrom(factoryClass)) {
          throw new RuntimeException("Not an annotation class: " + factoryClass.getCanonicalName());
        }
        return javaMethod.getAnnotation((Class<? extends Annotation>)factoryClass) != null;        
      } catch (ClassNotFoundException e) {
        throw new RuntimeException("Cannot load hamcrest core", e);
      }
    }
    
    private static FactoryMethod buildFactoryMethod(Method javaMethod) {
        FactoryMethod result = new FactoryMethod(
                classToString(javaMethod.getDeclaringClass()),
                javaMethod.getName(), 
                classToString(javaMethod.getReturnType()));

        for (TypeVariable<Method> typeVariable : javaMethod.getTypeParameters()) {
            boolean hasBound = false;
            StringBuilder s = new StringBuilder(typeVariable.getName());
            for (Type bound : typeVariable.getBounds()) {
                if (bound != Object.class) {
                    if (hasBound) {
                        s.append(" & ");
                    } else {
                        s.append(" extends ");
                        hasBound = true;
                    }
                    s.append(typeToString(bound));
                }
            }
            result.addGenericTypeParameter(s.toString());
        }
        Type returnType = javaMethod.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type generifiedType = parameterizedType.getActualTypeArguments()[0];
            result.setGenerifiedType(typeToString(generifiedType));
        }

        int paramNumber = 0;
        for (Type paramType : javaMethod.getGenericParameterTypes()) {
            String type = typeToString(paramType);
            // Special case for var args methods.... String[] -> String...
            if (javaMethod.isVarArgs()
                    && paramNumber == javaMethod.getParameterTypes().length - 1) {
                type = type.replaceFirst("\\[\\]$", "...");
            }
            result.addParameter(type, "param" + (++paramNumber));
        }

        for (Class<?> exception : javaMethod.getExceptionTypes()) {
            result.addException(typeToString(exception));
        }

        return result;
    }

    /*
     * Get String representation of Type (e.g. java.lang.String or Map&lt;Stuff,? extends Cheese&gt;).
     * <p/>
     * Annoyingly this method wouldn't be needed if java.lang.reflect.Type.toString() behaved consistently
     * across implementations. Rock on Liskov.
     */
    private static String typeToString(Type type) {
        return type instanceof Class<?> ?  classToString((Class<?>) type): type.toString();
    }

    private static String classToString(Class<?> cls) {
      final String name = cls.isArray() ? cls.getComponentType().getName() + "[]" : cls.getName();
      return name.replace('$', '.');
    }

}