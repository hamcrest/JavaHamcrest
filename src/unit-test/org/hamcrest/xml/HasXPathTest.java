package org.hamcrest.xml;

import org.hamcrest.AbstractMatcherTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.eq;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.StringContains.containsString;
import static org.hamcrest.xml.HasXPath.hasXPath;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

/**
 * @author Joe Walnes
 */
public class HasXPathTest extends AbstractMatcherTest {
    private Document xml;

    protected void setUp() throws Exception {
        super.setUp();
        xml = parse(""
                + "<root type='food'>\n"
                + "  <something id='a'><cheese>Edam</cheese></something>\n"
                + "  <something id='b'><cheese>Cheddar</cheese></something>\n"
                + "</root>\n"
        );
    }

    public void testAppliesMatcherToXPathInDocument() throws Exception {
        assertThat(xml, hasXPath("/root/something[2]/cheese", eq("Cheddar")));
        assertThat(xml, hasXPath("//something[1]/cheese", containsString("dam")));
        assertThat(xml, hasXPath("//something[2]/cheese", not(containsString("dam"))));
        assertThat(xml, hasXPath("/root/@type", eq("food")));
        assertThat(xml, hasXPath("//something[@id='b']/cheese", eq("Cheddar")));
        assertThat(xml, hasXPath("//something[@id='b']/cheese"));
    }

    public void testFailsIfNodeIsMissing() throws Exception {
        assertThat(xml, not(hasXPath("/root/something[3]/cheese", eq("Cheddar"))));
        assertThat(xml, not(hasXPath("//something[@id='c']/cheese")));
    }

    public void testThrowsIllegalArgumentExceptionIfGivenIllegalExpression() {
        try {
            hasXPath("\\g:dfgd::DSgf/root/something[2]/cheese", eq("blah"));
            fail("Expected exception");
        } catch (IllegalArgumentException expectedException) {
            // expected exception
        }
    }

    public void testDescribesItself() throws Exception {
        assertDescription("an XML document with XPath /some/path eq(\"Cheddar\")",
                hasXPath("/some/path", eq("Cheddar")));
        assertDescription("an XML document with XPath /some/path",
                hasXPath("/some/path"));
    }

    private Document parse(String xml) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
    }
}
