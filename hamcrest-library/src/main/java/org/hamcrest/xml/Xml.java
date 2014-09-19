package org.hamcrest.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsNull;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class Xml extends BaseMatcher<Object> {

	private Matcher<Node> matcherParam;
	private Matcher<?> delegateMatcher;
	
	
	private Xml(Matcher<Node> matcherParam) {
		this.matcherParam = matcherParam;
	}
	
	private void populateDelegateMatcher(Object item) {
		if (null == this.delegateMatcher) {
			this.delegateMatcher = getDelegateMatcher(item);
		}
	}
	
	private Matcher<?> getDelegateMatcher(Object item) {
		Matcher<?> delegateMatcher = null;
		
		if (item instanceof File) {
			delegateMatcher = new FileXmlSourceMatcher(this.matcherParam);
		} else if (item instanceof String) {
			delegateMatcher = new StringXmlSourceMatcher(this.matcherParam);
		} else if (item instanceof InputStream) {
			delegateMatcher = new InputStreamXmlSourceMatcher(this.matcherParam);
		} else if (null == item) {
			delegateMatcher = IsNull.notNullValue();
		} else {
			delegateMatcher = new UnknownXmlSourceMatcher(this.matcherParam);
		}
		
		return delegateMatcher;
	}
	
	@Override
	public boolean matches(Object item) {
		populateDelegateMatcher(item);
		return this.delegateMatcher.matches(item);
	}

	@Override
	public void describeTo(Description description) {
		this.delegateMatcher.describeTo(description);
	}

	@Override
	public void describeMismatch(Object item, Description description) {
		populateDelegateMatcher(item);
		this.delegateMatcher.describeMismatch(item, description);
	}

	@Factory
	public static Matcher<Object> xml(Matcher<Node> matcher) {
		return new Xml(matcher);
	}
	
	private abstract class AbstractXmlSourceMatcher<T> extends TypeSafeMatcher<T> {
		
		private Matcher<Node> matcher;
		
		public AbstractXmlSourceMatcher(Matcher<Node> matcher) {
			this.matcher = matcher;
		}

		@Override
		final public void describeTo(Description description) {
			this.matcher.describeTo(description);
		}
		
		@Override
		abstract protected void describeMismatchSafely(T item, Description mismatchDescription);
		
		@Override
		abstract protected boolean matchesSafely(T item);

		protected DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		
		protected boolean matches(Node node) {
			return this.matcher.matches(node);
		}
	}
	
	private class FileXmlSourceMatcher extends AbstractXmlSourceMatcher<File> {
		
		public FileXmlSourceMatcher(Matcher<Node> matcher) {
			super(matcher);
		}
		
		@Override
		protected void describeMismatchSafely(File item, Description mismatchDescription) {
			mismatchDescription.appendText("a match was not found");
		}

		@Override
		protected boolean matchesSafely(File item) {
			boolean matches = false;
			
			try {
				Node node = getDocumentBuilder().parse(item);
				matches = super.matches(node);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return matches;
		}
	}
	
	private class StringXmlSourceMatcher extends AbstractXmlSourceMatcher<String> {
		
		public StringXmlSourceMatcher(Matcher<Node> matcher) {
			super(matcher);
		}
		
		@Override
		protected void describeMismatchSafely(String item, Description mismatchDescription) {
			mismatchDescription.appendText("a match was not found");
		}

		@Override
		protected boolean matchesSafely(String item) {
			boolean matches = false;
			
			try {
				Node node = getDocumentBuilder().parse(new ByteArrayInputStream(item.getBytes()));
				matches = super.matches(node);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return matches;
		}
	}
	
	private class InputStreamXmlSourceMatcher extends AbstractXmlSourceMatcher<InputStream> {
		
		public InputStreamXmlSourceMatcher(Matcher<Node> matcher) {
			super(matcher);
		}
		
		@Override
		protected void describeMismatchSafely(InputStream item, Description mismatchDescription) {
			mismatchDescription.appendText("a match was not found");
		}

		@Override
		protected boolean matchesSafely(InputStream item) {
			boolean matches = false;
			
			try {
				Node node = getDocumentBuilder().parse(item);
				matches = super.matches(node);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return matches;
		}
	}
	
	private class UnknownXmlSourceMatcher extends AbstractXmlSourceMatcher<Object> {
		
		public UnknownXmlSourceMatcher(Matcher<Node> matcher) {
			super(matcher);
		}
		
		@Override
		protected void describeMismatchSafely(Object item, Description mismatchDescription) {
			mismatchDescription.appendText("a match was not found");
		}

		@Override
		protected boolean matchesSafely(Object item) {
			boolean matches = false;
			
			try {
				Node node = getDocumentBuilder().parse(new ByteArrayInputStream(item.toString().getBytes()));
				matches = super.matches(node);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return matches;
		}
	}
}
