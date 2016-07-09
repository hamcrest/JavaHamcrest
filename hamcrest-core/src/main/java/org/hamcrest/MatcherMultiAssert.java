package org.hamcrest;

import java.util.ArrayList;
import static org.hamcrest.core.Is.is;

import org.hamcrest.internal.Assertion;

/**
 * This class allows the user to make multiple assertions and have all of them checked even if one fails.  
 * The assertions are checked when the resolve method is called.  The enclosed assertThat and andThat can be
 * used just like the assertThat from MatcherAssert.
 * 
 * @author Andrew Lijewski
 *
 */
public class MatcherMultiAssert {
    private ArrayList<Assertion<?>> assertions;

	public static <T> MatcherMultiAssert assertThat(T actual, Matcher<? super T> matcher) {
    	return new MatcherMultiAssert(new Assertion<T>(actual, matcher));
    }
    
    public static <T> MatcherMultiAssert assertThat(String reason, T actual, Matcher<? super T> matcher) {
    	return new MatcherMultiAssert(new Assertion<T>(reason, actual, matcher));
    }
    
    public static MatcherMultiAssert assertThat(String reason, Boolean assertion) {
    	return new MatcherMultiAssert(new Assertion<Boolean>(reason, assertion, is(Boolean.TRUE)));
    }

	private MatcherMultiAssert(Assertion<?> assertion) {
    	assertions = new ArrayList<Assertion<?>>();
        assertions.add(assertion);
	}
	
	public <T> MatcherMultiAssert andThat(T actual, Matcher<? super T> matcher) {
		assertions.add(new Assertion<T>(actual, matcher));
        return this;
    }
    
    public <T> MatcherMultiAssert andThat(String reason, T actual, Matcher<? super T> matcher) {
    	assertions.add(new Assertion<T>(reason, actual, matcher));
        return this;
    }
    
    public MatcherMultiAssert andThat(String reason, Boolean assertion) {
    	assertions.add(new Assertion<Boolean>(reason, assertion, is(Boolean.TRUE)));
        return this;
    }
    
    public void resolve(){
        StringBuilder assertionFailures = new StringBuilder()
                .append("\n");
        long assertionNumber = 0;
        boolean hasFailed = false;
        for(Assertion<?> assertion : assertions){
            assertionNumber++;
            if(assertion.hasFailed()){
                hasFailed = true;
                assertionFailures
                .append("[").append(assertionNumber).append("] ")
                .append(assertion.getErrorMessage())
                .append("\n");
            }
        }
        if(hasFailed){
            throw new AssertionError(assertionFailures.toString());
        }
    }
}
