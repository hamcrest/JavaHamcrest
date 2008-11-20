package org.hamcrest.xml;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.w3c.dom.Node;

/**
 * Applies a Matcher to a given XML Node in an existing XML Node tree, specified by an XPath expression.
 *
 * @author Joe Walnes
 */
public class HasXPath extends TypeSafeDiagnosingMatcher<Node> {
    private final Matcher<? super String> valueMatcher;
    private final XPathExpression compiledXPath;
    private final String xpathString;
    private final QName evaluationMode;

    /**
     * @param xPathExpression XPath expression.
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, Matcher<? super String> valueMatcher) {
        this(xPathExpression, null, valueMatcher);
    }

    /**
     * @param xPathExpression XPath expression.
     * @param namespaceContext Resolves XML namespace prefixes in the XPath expression
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<? super String> valueMatcher) {
        this(xPathExpression, namespaceContext, valueMatcher, XPathConstants.STRING);
    }

    private HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<? super String> valueMatcher, QName mode) {
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

    @Override
	public boolean matchesSafely(Node item, Description mismatchDescription) {
        try {
            return matchesResult(compiledXPath.evaluate(item, evaluationMode), mismatchDescription);
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

    private boolean matchesResult(Object result, Description mismatchDescription) {
      if (result == null) {
          mismatchDescription.appendText("xpath returned no results.");
          return false;
      } else if (valueMatcher == null) {
          return true;
      } else {
          boolean valueMatched = valueMatcher.matches(result);
          if (!valueMatched) {
            mismatchDescription.appendText("xpath result ");
            valueMatcher.describeMismatch(result, mismatchDescription);
          }
          return valueMatched;
      }
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath, Matcher<? super String> valueMatcher) {
        return hasXPath(xPath, null, valueMatcher);
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext, Matcher<? super String> valueMatcher) {
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
