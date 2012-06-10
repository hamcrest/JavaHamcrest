package org.hamcrest.generator;

import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

/**
 * Represents a Matcher Factory method.
 * <p/>
 * <p>This class uses Strings to represent things instead of java.lang.reflect equivalents,
 * allowing methods to be defined from sources other than reflection of classes in the
 * classpath.
 *
 * @author Joe Walnes
 * @see SugarGenerator
 */
public class FactoryMethod {

    private final String matcherClass;
    private final String factoryMethod;
    private final String returnType;

    private String generifiedType;
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private List<String> exceptions = new ArrayList<String>();
    private List<String> genericTypeParameters = new ArrayList<String>();
    private String javaDoc;

    public FactoryMethod(String matcherClass, String factoryMethod, String returnType) {
        this.matcherClass = matcherClass;
        this.factoryMethod = factoryMethod;
        this.returnType = returnType;
    }

    /**
     * Original class this factory method came from.
     */
    public String getMatcherClass() {
        return matcherClass;
    }

    /**
     * @return The fully qualified name of the type returned by the method. This should be a
     * subclass of org.hamcrest.Matcher.
     */
    public String getReturnType() {
      return returnType;
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
    
    // Generated in Eclipse...
    // n.b. Doesn't include returnType
    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      FactoryMethod other = (FactoryMethod) obj;
      if (exceptions == null) {
        if (other.exceptions != null) return false;
      } else if (!exceptions.equals(other.exceptions)) return false;
      if (factoryMethod == null) {
        if (other.factoryMethod != null) return false;
      } else if (!factoryMethod.equals(other.factoryMethod)) return false;
      if (genericTypeParameters == null) {
        if (other.genericTypeParameters != null) return false;
      } else if (!genericTypeParameters.equals(other.genericTypeParameters)) return false;
      if (generifiedType == null) {
        if (other.generifiedType != null) return false;
      } else if (!generifiedType.equals(other.generifiedType)) return false;
      if (javaDoc == null) {
        if (other.javaDoc != null) return false;
      } else if (!javaDoc.equals(other.javaDoc)) return false;
      if (matcherClass == null) {
        if (other.matcherClass != null) return false;
      } else if (!matcherClass.equals(other.matcherClass)) return false;
      if (parameters == null) {
        if (other.parameters != null) return false;
      } else if (!parameters.equals(other.parameters)) return false;
      return true;
    }

    // Generated in Eclipse...
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((exceptions == null) ? 0 : exceptions.hashCode());
      result = prime * result + ((factoryMethod == null) ? 0 : factoryMethod.hashCode());
      result = prime * result + ((genericTypeParameters == null) ? 0 : genericTypeParameters.hashCode());
      result = prime * result + ((generifiedType == null) ? 0 : generifiedType.hashCode());
      result = prime * result + ((javaDoc == null) ? 0 : javaDoc.hashCode());
      result = prime * result + ((matcherClass == null) ? 0 : matcherClass.hashCode());
      result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
      return result;
    }

    @Override
    public String toString() {
        return "{FactoryMethod: \n" +
                "  matcherClass = " + matcherClass + "\n" +
                "  factoryMethod = " + factoryMethod + "\n" +
                "  generifiedType = " + generifiedType + "\n" +
                "  parameters = " + parameters + "\n" +
                "  exceptions = " + exceptions + "\n" +
                "  genericTypeParameters = " + genericTypeParameters + "\n" +
                "  javaDoc = " + javaDoc + "\n" +
                "}";
    }

    /**
     * Represents a parameter passed to a factory method.
     *
     * @see FactoryMethod
     */
    public static class Parameter {

        private final String type;
        private String name;

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

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return type + " " + name;
        }

        // Generated in Eclipse...
        @Override
        public int hashCode() {
          final int prime = 31;
          int result = 1;
          result = prime * result + ((name == null) ? 0 : name.hashCode());
          result = prime * result + ((type == null) ? 0 : type.hashCode());
          return result;
        }

        // Generated in Eclipse...
        @Override
        public boolean equals(Object obj) {
          if (this == obj) return true;
          if (obj == null) return false;
          if (getClass() != obj.getClass()) return false;
          Parameter other = (Parameter) obj;
          if (name == null) {
            if (other.name != null) return false;
          } else if (!name.equals(other.name)) return false;
          if (type == null) {
            if (other.type != null) return false;
          } else if (!type.equals(other.type)) return false;
          return true;
        }

    }

}
