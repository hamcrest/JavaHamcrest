package org.hamcrest;

/**
 * This Hamcrest Matcher is used to create Matchers that can be chained similarly
 * to AssertJ assertions. It uses the decorator pattern to do so.
 * <p>
 * In order to use this class, you create an abstract class that extends from
 * this one that doesn't implement any of these methods, but lists all the methods
 * required to do the different decorations.</p>
 * <p>
 * For example, if you want to design a chaining set of matchers for testing a
 * {@code Person} class that has {@code name} and {@code age} properties, this
 * abstract class would look something like this:</p>
 * <pre><code>
 * public abstract class ChainablePersonMatcher extends ChainableMatcher&lt;Person&gt; {
 * 
 *    protected ChainablePersonMatcher(ChainablePersonMatcher toBeDecorated) {
 *       super(toBeDecorated);
 *    }
 *    
 *    public ChainablePersonMatcher hasName(String expectedName) {
 *       return new NameChainablePersonMatcher(this, expectedName);
 *    }
 *    
 *    public ChainablePersonMatcher hasAge(int expectedAge) {
 *       return new AgeChainablePersonMatcher(this, expectedAge);
 *    }
 * }
 * </code></pre>
 * <p>
 * After that, you design your individual Matchers, {@code NameChainablePersonMatcher}
 * and {@code AgeChainablePersonMatcher}, to extend from this abstract class and
 * implement {@code chainDescribeTo()}, {@code chainMatches()}, and 
 * {@code chainDescribeMismatch()} the same way you normally would do {@code describeTo()},
 * {@code matches()}, and {@code describeMismatch()}. Don't forget the
 * factory methods, though.</p>
 * <p>
 * Once that's done, you can use the set of Matchers as follows:<br>
 * {@code assertThat(person, hasName("Bob").hasAge(32));}</p>
 * <p>
 * This does break the decorator pattern a little, since it doesn't have a base
 * object to decorate. You could set one up, though. To do so, you would have to
 * make it so that the base matcher is the only one with a factory method, and
 * that it passes {@code null} into the {@code super} constructor. </p>
 * 
 * @param <T> the same T as in {@link org.hamcrest.TypeSafeMatcher TypeSafeMatcher}&lt;T&gt;
 */
public abstract class ChainableMatcher<T> extends BaseMatcher<T>
{
	//***************************************************************************
	// Protected constructor
	//***************************************************************************
	protected ChainableMatcher(ChainableTypeSafeMatcher<? super T> decoratedMatcher)
	{
		this.decoratedMatcher = decoratedMatcher;
		this.thisMatches = true;
	}

	//***************************************************************************
	// Methods to implement
	//***************************************************************************
	/**
	 * Implemented the same as describeTo() would be, but this is a method called
	 * by ChainingMatcher's describeTo()
	 * @see #describeTo() 
	 */
	public abstract void chainDescribeTo(Description description);
	
	/**
	 * Implemented the same as matchesSafely() would be, but this is a method 
	 * called by ChainingMatcher's matches()
	 * @see #matchesSafely()
	 */
	protected abstract boolean chainMatches(Object item);
	
	/**
	 * Implemented the same as describeMismatchSafely() would be, but this is a
	 * method call by ChainingMatcher's
	 * @see #describeMismatch() 
	 */
	protected abstract void chainDescribeMismatch(Object item, Description mismatchDescription);

	//***************************************************************************
	// Template methods
	//***************************************************************************
	@Override
	public final void describeTo(Description description)
	{
		if(decoratedMatcher != null)
		{
			decoratedMatcher.describeTo(description);
			description.appendText("\n              ");
		}
		chainDescribeTo(description);
	}
	
	@Override
	public final boolean matches(Object item)
	{
		thisMatches = chainMatches(item);
		
		if(decoratedMatcher == null || decoratedMatcher.matches(item))
			return thisMatches;
		else
			return false;
	}
	
	@Override
	public final void describeMismatch(Object item, Description mismatchDescription)
	{
		//if applicable, we want to get the match/mismatch descriptions of all the
		// decorated matchers, too
		if(decoratedMatcher != null)
		{
			decoratedMatcher.describeMismatch(item, mismatchDescription);
			mismatchDescription.appendText("\n             ");
		}
		
		if(thisMatches)
			chainDescribeTo(mismatchDescription); //explicitly state that this one matched
		else
			chainDescribeMismatch(item, mismatchDescription);	
	}
	
	//***************************************************************************
	// Private fields
	//***************************************************************************
	private ChainableTypeSafeMatcher<? super T> decoratedMatcher;
	private boolean thisMatches;
}