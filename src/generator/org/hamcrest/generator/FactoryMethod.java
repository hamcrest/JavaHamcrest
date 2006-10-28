package org.hamcrest.generator;


import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

/**
 * Represents a Matcher Factory method.
 *
 * <p>This class uses Strings to represent things instead of java.lang.reflect equivalents,
 * allowing methods to be defined from sources other than reflection of classes in the
 * classpath.
 *
 * @author Joe Walnes
 * @see SugarGenerator
 * @see org.hamcrest.Factory
 */
public class FactoryMethod {

    private final String matcherClass;
    private final String factoryMethod;

    private String generifiedType;
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private List<String> exceptions = new ArrayList<String>();
    private List<String> genericTypeParameters = new ArrayList<String>();
    private String javaDoc;

    public FactoryMethod(String matcherClass, String factoryMethod) {
        this.matcherClass = matcherClass;
        this.factoryMethod = factoryMethod;
    }

    /**
     * Original class this factory method came from.
     */
    public String getMatcherClass() {
        return matcherClass;
    }

    /**
     * Original name of factory method.
     */
    public String getName() {
        return factoryMethod;
    }

    public void setGenerifiedType(String generifiedType) {
        this.generifiedType = generifiedType;
    }

    /**
     * Generified type of matcher.
     * ie. 'public Matcher&lt;THISBIT&gt; blah(...)'
     */
    public String getGenerifiedType() {
        return generifiedType;
    }

    public void addParameter(String type, String name) {
        parameters.add(new Parameter(type, name));
    }

    /**
     * List of Parameters passed to factory method.
     * ie. 'public Matcher&lt;...&ht;> blah(THIS, AND, THAT)'
     */
    public List<Parameter> getParameters() {
        return unmodifiableList(parameters);
    }

    public void addException(String exception) {
        exceptions.add(exception);
    }

    /**
     * List of exceptions thrown by factory method.
     * ie. 'public Matcher&lt;...&gt; blah(...) throws THIS, THAT'
     */
    public List<String> getExceptions() {
        return unmodifiableList(exceptions);
    }

    public void addGenericTypeParameter(String genericTypeParameter) {
        genericTypeParameters.add(genericTypeParameter);
    }

    /**
     * List of generic type parameters for factory method definition.
     * ie. 'public &lt;THIS,THAT&gt; Matcher&lt;...&gt; blah(...)'
     *
     * @return
     */
    public List<String> getGenericTypeParameters() {
        return unmodifiableList(genericTypeParameters);
    }

    public void setJavaDoc(String javaDoc) {
        this.javaDoc = javaDoc;
    }

    /**
     * JavaDoc definition of factory method.
     * Excludes surrounding comment markers.
     * Note that using standard Java reflection it is not possible to obtain this,
     * however source code parsers can read this.
     */
    public String getJavaDoc() {
        return javaDoc;
    }

    /**
     * Represents a parameter passed to a factory method.
     *
     * @see FactoryMethod
     */
    public static class Parameter {

        private final String type;
        private final String name;

        public Parameter(String type, String name) {
            this.type = type;
            this.name = name;
        }

        /**
         * Type of parameter, including any generic declarations.
         */
        public String getType() {
            return type;
        }

        /**
         * Name of parameter, if it can be obtained. If it cannot
         * be obtained, a sensible default will returned instead.
         */
        public String getName() {
            return name;
        }

    }

}
