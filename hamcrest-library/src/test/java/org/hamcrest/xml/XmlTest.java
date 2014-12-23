package org.hamcrest.xml;

import static org.hamcrest.AbstractMatcherTest.assertDoesNotMatch;
import static org.hamcrest.AbstractMatcherTest.assertMatches;
import static org.hamcrest.xml.HasXPath.hasXPath;
import static org.hamcrest.xml.Xml.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class XmlTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	private final String VALID_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><elementRoot><otherElement /><elementSub>TextValue</elementSub></elementRoot>";
	private final String INVALID_XML = "ASDF12345";
	
	@Test
	public void matchesValidFile() throws IOException {
		File file = getFileWithContent(VALID_XML);
		
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertMatches(matcher, file);
	}
	
	@Test
	public void doesNotMatchInvalidFile() throws IOException {
		File file = getFileWithContent(INVALID_XML);
		
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertDoesNotMatch(matcher, file);
	}
	
	@Test
	public void matchesValidInputStream() throws IOException {
		File file = getFileWithContent(VALID_XML);
		
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertMatches(matcher, new FileInputStream(file));
	}
	
	@Test
	public void doesNotMatchInputStream() throws IOException {
		File file = getFileWithContent(INVALID_XML);
		
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertDoesNotMatch(matcher, new FileInputStream(file));
	}
	
	@Test
	public void matchesValidString() throws IOException, InterruptedException {
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertMatches(matcher, VALID_XML);
	}
	
	@Test
	public void doesNotMatchInvalidString() throws IOException {
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertDoesNotMatch(matcher, INVALID_XML);
	}

	@Test
	public void doesNotMatchNull() {
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertDoesNotMatch(matcher, null);
	}
	
	@Test
	public void matchesValidToStringOnUnknownXmlSource() {
		Object unknown = new Object() {
			@Override
			public String toString() {
				return VALID_XML;
			}
		};
		
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertMatches(matcher, unknown);
	}
	
	@Test
	public void doesNotMatchInvalidToStringOnUnknownXmlSource() {
		Object unknown = new Object() {
			@Override
			public String toString() {
				return INVALID_XML;
			}
		};
		
		Matcher<Object> matcher = xml(hasXPath("/elementRoot/elementSub"));
		
		assertDoesNotMatch(matcher, unknown);
	}
	
	private File getFileWithContent(String content) throws IOException {
		File file = tempFolder.newFile();
		FileOutputStream out = new FileOutputStream(file);
		out.write(content.getBytes());
		out.close();
		
		return file;
	}
}
