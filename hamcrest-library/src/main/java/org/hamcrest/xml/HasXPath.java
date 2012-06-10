package org.hamcrest.xml;

import org.hamcrest.*;
import org.hamcrest.core.IsAnything;
import org.w3c.dom.Node;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.*;

import static javax.xml.xpath.XPathConstants.STRING;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

/**
 * Applies a Matcher to a given XML Node in an existing XML Node tree, specified by an XPath expression.
 *
 * @author Joe Walnes
 * @author Steve Freeman
 */
public class HasXPath extends TypeSafeDiagnosingMatcher<Node> {
    public static final NamespaceContext NO_NAMESPACE_CONTEXT = null;
    private static final IsAnything<String> WITH_ANY_CONTENT = new IsAnything<String>("");
    private static final Condition.Step<Object,String> NODE_EXISTS = nodeExists();
    private final Matcher<String> valueMatcher;
    private final XPathExpression compiledXPath;
    private final String xpathString;
    private final QName evaluationMode;

    /**
     * @param xPathExpression XPath expression.
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, Matcher<String> valueMatcher) {
        this(xPathExpression, NO_NAMESPACE_CONTEXT, valueMatcher);
    }

    /**
     * @param xPathExpression XPath expression.
     * @param namespaceContext Resolves XML namespace prefixes in the XPath expression
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        this(xPathExpression, namespaceContext, valueMatcher, STRING);
    }

    private HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<String> valueMatcher, QName mode) {
        this.compiledXPath = compiledXPath(xPathExpression, namespaceContext);
        this.xpathString = xPathExpression;
        this.valueMatcher = valueMatcher;
        this.evaluationMode = mode;
    }

    @Override
    public boolean matchesSafely(Node item, Description mismatch) {
        return evaluated(item, mismatch)
               .and(NODE_EXISTS)
               .matching(valueMatcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an XML document with XPath ").appendText(xpathString);
        if (valueMatcher != null) {
            description.appendText(" ").appendDescriptionOf(valueMatcher);
        }
    }

    private Condition<Object> evaluated(Node item, Description mismatch) {
        try {
            return matched(compiledXPath.evaluate(item, evaluationMode), mismatch);
        } catch (XPathExpressionException e) {
            mismatch.appendText(e.getMessage());
        }
        return notMatched();
    }

    private static Condition.Step<Object, String> nodeExists() {
        return new Condition.Step<Object, String>() {
            @Override
            public Condition<String> apply(Object value, Description mismatch) {
                if (value == null) {
                    mismatch.appendText("xpath returned no results.");
                    return notMatched();
                }
                return matched(String.valueOf(value), mismatch);
            }
        };
    }

    private static XPathExpression compiledXPath(String xPathExpression, NamespaceContext namespaceContext) {
        try {
            final XPath xPath = XPathFactory.newInstance().newXPath();
            if (namespaceContext != null) {
                xPath.setNamespaceContext(namespaceContext);
            }
            return xPath.compile(xPathExpression);
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException("Invalid XPath : " + xPathExpression, e);
        }
    }


    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node has a value at the
     * specified <code>xPath</code> that satisfies the specified <code>valueMatcher</code>.
     * <p/>
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese", equalTo("Cheddar")))</pre>
     * 
     * @param xPath
     *     the target xpath
     * @param valueMatcher
     *     matcher for the value at the specified xpath
     */
    @Factory
    public static Matcher<Node> hasXPath(String xPath, Matcher<String> valueMatcher) {
        return hasXPath(xPath, NO_NAMESPACE_CONTEXT, valueMatcher);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node has a value at the
     * specified <code>xPath</code>, within the specified <code>namespaceContext</code>, that satisfies
     * the specified <code>valueMatcher</code>.
     * <p/>
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese", myNs, equalTo("Cheddar")))</pre>
     * 
     * @param xPath
     *     the target xpath
     * @param namespaceContext
     *     the namespace for matching nodes
     * @param valueMatcher
     *     matcher for the value at the specified xpath
     */
    @Factory
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        return new HasXPath(xPath, namespaceContext, valueMatcher, STRING);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node contains a node
     * at the specified <code>xPath</code>, with any content.
     * <p/>
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese"))</pre>
     * 
     * @param xPath
     *     the target xpath
     */
    @Factory
    public static Matcher<Node> hasXPath(String xPath) {
        return hasXPath(xPath, NO_NAMESPACE_CONTEXT);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node contains a node
     * at the specified <code>xPath</code> within the specified namespace context, with any content.
     * <p/>
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese", myNs))</pre>
     * 
     * @param xPath
     *     the target xpath
     * @param namespaceContext
     *     the namespace for matching nodes
     */
    @Factory
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext) {
        return new HasXPath(xPath, namespaceContext, WITH_ANY_CONTENT, XPathConstants.NODE);
    }
}
