package org.hamcrest.generator.config;

import org.hamcrest.generator.ReflectiveFactoryReader;
import org.hamcrest.generator.SugarConfiguration;
import org.hamcrest.generator.SugarGenerator;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class XmlConfigurator {

    private final SugarConfiguration sugarConfiguration;
    private final ClassLoader classLoader;
    private final SAXParserFactory saxParserFactory;

    public XmlConfigurator(SugarConfiguration sugarConfiguration, ClassLoader classLoader) {
        this.sugarConfiguration = sugarConfiguration;
        this.classLoader = classLoader;
        saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
    }

    public void load(InputSource inputSource) throws ParserConfigurationException, SAXException, IOException {
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(inputSource, new DefaultHandler() {
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (localName.equals("factory")) {
                    String className = attributes.getValue("class");
                    try {
                        addClass(className);
                    } catch (ClassNotFoundException e) {
                        throw new SAXException("Cannot find Matcher class : " + className);
                    }
                } else {
                    System.err.println("uri=" + uri + ", localname=" + localName + ", qname=" + qName);

                }
            }
        });
    }

    private void addClass(String className) throws ClassNotFoundException {
        Class cls = classLoader.loadClass(className);
        sugarConfiguration.addFactoryMethods(new ReflectiveFactoryReader(cls));
    }

    public static void main(String[] args) throws Exception {
        XmlConfigurator config = new XmlConfigurator(new SugarGenerator(), XmlConfigurator.class.getClassLoader());
        config.load(new InputSource("matchers.xml"));
    }
}
