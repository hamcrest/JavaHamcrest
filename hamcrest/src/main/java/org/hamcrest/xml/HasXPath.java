/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hamcrest.xml;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
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
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese", equalTo("Cheddar")))</pre>
     * 
     * @param xPath
     *     the target xpath
     * @param valueMatcher
     *     matcher for the value at the specified xpath
     */
    public static Matcher<Node> hasXPath(String xPath, Matcher<String> valueMatcher) {
        return hasXPath(xPath, NO_NAMESPACE_CONTEXT, valueMatcher);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node has a value at the
     * specified <code>xPath</code>, within the specified <code>namespaceContext</code>, that satisfies
     * the specified <code>valueMatcher</code>.
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
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        return new HasXPath(xPath, namespaceContext, valueMatcher, STRING);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node contains a node
     * at the specified <code>xPath</code>, with any content.
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese"))</pre>
     * 
     * @param xPath
     *     the target xpath
     */
    public static Matcher<Node> hasXPath(String xPath) {
        return hasXPath(xPath, NO_NAMESPACE_CONTEXT);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node contains a node
     * at the specified <code>xPath</code> within the specified namespace context, with any content.
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese", myNs))</pre>
     * 
     * @param xPath
     *     the target xpath
     * @param namespaceContext
     *     the namespace for matching nodes
     */
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext) {
        return new HasXPath(xPath, namespaceContext, WITH_ANY_CONTENT, XPathConstants.NODE);
    }
}
