package org.hamcrest.xml;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.w3c.dom.Node;

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

    private final Matcher<String> valueMatcher;
    private final XPathExpression compiledXPath;
    private final String xpathString;

    /**
     * @param xPathExpression XPath expression.
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, Matcher<String> valueMatcher) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            compiledXPath = xPath.compile(xPathExpression);
            this.xpathString = xPathExpression;
            this.valueMatcher = valueMatcher;
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException("Invalid XPath : " + xPathExpression, e);
        }
    }

    public boolean matchesSafely(Node item) {
        try {
            String result = (String) compiledXPath.evaluate(item, XPathConstants.STRING);
            if (result == null) {
                return false;
            } else if (valueMatcher == null) {
                return !result.equals("");
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
        return new HasXPath(xPath, valueMatcher);
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath) {
        return hasXPath(xPath, null);
    }

}
