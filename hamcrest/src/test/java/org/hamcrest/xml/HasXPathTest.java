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

import org.hamcrest.Matcher;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Iterator;

import static org.hamcrest.AbstractMatcherTest.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.xml.HasXPath.hasXPath;
import static org.junit.Assert.fail;

/**
 * @author Joe Walnes
 * @author Tom Denley
 */
public final class HasXPathTest {

    private final Document xml = parse(""
            + "<root type='food'>\n"
            + "  <something id='a'><cheese>Edam</cheese></something>\n"
            + "  <something id='b'><cheese>Cheddar</cheese></something>\n"
            + "  <f:foreignSomething xmlns:f=\"http://cheese.com\" milk=\"camel\">Caravane</f:foreignSomething>\n"
            + "  <emptySomething />\n"
            + "  <f:emptySomething xmlns:f=\"http://cheese.com\" />"
            + "</root>\n"
            );

    private final NamespaceContext ns = new NamespaceContext() {
        @Override
        public String getNamespaceURI(String prefix) {
            return ("cheese".equals(prefix) ? "http://cheese.com" : null);
        }

        @Override
        public String getPrefix(String namespaceURI) {
            return ("http://cheese.com".equals(namespaceURI) ? "cheese" : null);
        }

        @Override
        public Iterator<String> getPrefixes(String namespaceURI) {
            HashSet<String> prefixes = new HashSet<String>();
            String prefix = getPrefix(namespaceURI);
            if (prefix != null) {
                prefixes.add(prefix);
            }
            return prefixes.iterator();
        }
    };

    @Test public void
    copesWithNullsAndUnknownTypes() {
        Matcher<Node> matcher = hasXPath("//irrelevant");
        
        assertNullSafe(matcher);
        assertUnknownTypeSafe(matcher);
    }

    @Test public void
    appliesMatcherToXPathInDocument() {
        assertMatches(hasXPath("/root/something[2]/cheese", equalTo("Cheddar")), xml);
        assertMatches(hasXPath("//something[1]/cheese", containsString("dam")), xml);
        assertMatches(hasXPath("//something[2]/cheese", not(containsString("dam"))), xml);
        assertMatches(hasXPath("/root/@type", equalTo("food")), xml);
        assertMatches(hasXPath("//something[@id='b']/cheese", equalTo("Cheddar")), xml);
        assertMatches(hasXPath("//something[@id='b']/cheese"), xml);
    }

    @Test public void
    matchesEmptyElement() {
        assertMatches(hasXPath("//emptySomething"), xml);
    }

    @Test public void
    matchesEmptyElementInNamespace() {
        assertMatches(hasXPath("//cheese:emptySomething", ns), xml);
    }

    @Test public void
    failsIfNodeIsMissing() {
        assertDoesNotMatch(hasXPath("/root/something[3]/cheese", ns, equalTo("Cheddar")), xml);
        assertDoesNotMatch(hasXPath("//something[@id='c']/cheese", ns), xml);
    }

    @Test public void
    failsIfNodeIsMissingInNamespace() {
        assertDoesNotMatch(hasXPath("//cheese:foreignSomething", equalTo("Badger")), xml);
        assertDoesNotMatch(hasXPath("//cheese:foreignSomething"), xml);
    }

    @Test public void
    matchesWithNamespace() {
        assertMatches(hasXPath("//cheese:foreignSomething", ns), xml);
        assertMatches(hasXPath("//cheese:foreignSomething/@milk", ns, equalTo("camel")), xml);
        assertMatches(hasXPath("//cheese:foreignSomething/text()", ns, equalTo("Caravane")), xml);
    }

    @Test public void
    throwsIllegalArgumentExceptionIfGivenIllegalExpression() {
        try {
            hasXPath("\\g:dfgd::DSgf/root/something[2]/cheese", equalTo("blah"));
            fail("Expected exception");
        } catch (IllegalArgumentException expectedException) {
            // expected exception
        }
    }

    @Test public void
    describesItself() {
        assertDescription("an XML document with XPath /some/path \"Cheddar\"",
                          hasXPath("/some/path", equalTo("Cheddar")));
        
        assertDescription("an XML document with XPath /some/path",
                          hasXPath("/some/path"));
    }

    @Test public void
    describesMissingNodeMismatch() {
        assertMismatchDescription("xpath returned no results.", hasXPath("//honky"), xml);
    }

    @Test public void
    describesIncorrectNodeValueMismatch() {
        assertMismatchDescription("was \"Edam\"", hasXPath("//something[1]/cheese", equalTo("parmesan")), xml);
    }

    private static Document parse(String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
