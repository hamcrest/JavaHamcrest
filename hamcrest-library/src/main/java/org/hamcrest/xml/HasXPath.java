package org.hamcrest.xml;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.w3c.dom.Node;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Applies a Matcher to a given XML Node in an existing XML Node tree, specified by an XPath expression.
 *
 * @author Joe Walnes
 */
public class HasXPath extends TypeSafeMatcher<Node> {
    private final Matcher<?> valueMatcher;
    private final XPathExpression compiledXPath;
    private final String xpathString;
    private final QName evaluationMode;

    /**
     * @param xPathExpression XPath expression.
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, Matcher<String> valueMatcher) {
        this(xPathExpression, null, valueMatcher);
    }

    /**
     * @param xPathExpression XPath expression.
     * @param namespaceContext Resolves XML namespace prefixes in the XPath expression
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        this(xPathExpression, namespaceContext, valueMatcher, XPathConstants.STRING);
    }

    private HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<?> valueMatcher, QName mode) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            if (namespaceContext != null) {
                xPath.setNamespaceContext(namespaceContext);
            }
            compiledXPath = xPath.compile(xPathExpression);
            this.xpathString = xPathExpression;
            this.valueMatcher = valueMatcher;
            this.evaluationMode = mode;
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException("Invalid XPath : " + xPathExpression, e);
        }
    }

    public boolean matchesSafely(Node item) {
        try {
            Object result = compiledXPath.evaluate(item, evaluationMode);
            if (result == null) {
                return false;
            } else if (valueMatcher == null) {
                return true;
            } else {
                return valueMatcher.matches(result);
            }
        } catch (XPathExpressionException e) {
            return false;
        }
    }

    public void describeTo(Description description) {
        description.appendText("an XML document with XPath ").appendText(xpathString);
        if (valueMatcher != null) {
            description.appendText(" ").appendDescriptionOf(valueMatcher);
        }
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath, Matcher<String> valueMatcher) {
        return hasXPath(xPath, null, valueMatcher);
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        return new HasXPath(xPath, namespaceContext, valueMatcher, XPathConstants.STRING);
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath) {
        return hasXPath(xPath, (NamespaceContext) null);
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext) {
        return new HasXPath(xPath, namespaceContext, null, XPathConstants.NODE);
    }
}
