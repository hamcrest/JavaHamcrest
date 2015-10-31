package org.hamcrest;

import java.util.Collection;
import java.util.List;

public class Matchers {

  /**
   * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> allOf(java.lang.Iterable<Matcher<? super T>> matchers) {
    return org.hamcrest.core.AllOf.<T>allOf(matchers);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> allOf(Matcher<? super T>... matchers) {
    return org.hamcrest.core.AllOf.<T>allOf(matchers);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second) {
    return org.hamcrest.core.AllOf.<T>allOf(first, second);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third) {
    return org.hamcrest.core.AllOf.<T>allOf(first, second, third);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth) {
    return org.hamcrest.core.AllOf.<T>allOf(first, second, third, fourth);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth) {
    return org.hamcrest.core.AllOf.<T>allOf(first, second, third, fourth, fifth);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth, Matcher<? super T> sixth) {
    return org.hamcrest.core.AllOf.<T>allOf(first, second, third, fourth, fifth, sixth);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.core.AnyOf<T> anyOf(java.lang.Iterable<Matcher<? super T>> matchers) {
    return org.hamcrest.core.AnyOf.<T>anyOf(matchers);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.core.AnyOf<T> anyOf(Matcher<? super T>... matchers) {
    return org.hamcrest.core.AnyOf.<T>anyOf(matchers);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> first, Matcher<? super T> second) {
    return org.hamcrest.core.AnyOf.<T>anyOf(first, second);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third) {
    return org.hamcrest.core.AnyOf.<T>anyOf(first, second, third);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth) {
    return org.hamcrest.core.AnyOf.<T>anyOf(first, second, third, fourth);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth) {
    return org.hamcrest.core.AnyOf.<T>anyOf(first, second, third, fourth, fifth);
  }

  /**
   * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
   * For example:
   * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
   */
  public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth, Matcher<? super T> sixth) {
    return org.hamcrest.core.AnyOf.<T>anyOf(first, second, third, fourth, fifth, sixth);
  }

  /**
   * Creates a matcher that matches when both of the specified matchers match the examined object.
   * For example:
   * <pre>assertThat("fab", both(containsString("a")).and(containsString("b")))</pre>
   */
  public static <LHS> org.hamcrest.core.CombinableMatcher.CombinableBothMatcher<LHS> both(org.hamcrest.Matcher<? super LHS> matcher) {
    return org.hamcrest.core.CombinableMatcher.<LHS>both(matcher);
  }

  /**
   * Creates a matcher that matches when either of the specified matchers match the examined object.
   * For example:
   * <pre>assertThat("fan", either(containsString("a")).or(containsString("b")))</pre>
   */
  public static <LHS> org.hamcrest.core.CombinableMatcher.CombinableEitherMatcher<LHS> either(org.hamcrest.Matcher<? super LHS> matcher) {
    return org.hamcrest.core.CombinableMatcher.<LHS>either(matcher);
  }

  /**
   * Wraps an existing matcher, overriding its description with that specified.  All other functions are
   * delegated to the decorated matcher, including its mismatch description.
   * For example:
   * <pre>describedAs("a big decimal equal to %0", equalTo(myBigDecimal), myBigDecimal.toPlainString())</pre>
   * 
   * @param description
   *     the new description for the wrapped matcher
   * @param matcher
   *     the matcher to wrap
   * @param values
   *     optional values to insert into the tokenised description
   */
  public static <T> org.hamcrest.Matcher<T> describedAs(java.lang.String description, org.hamcrest.Matcher<T> matcher, java.lang.Object... values) {
    return org.hamcrest.core.DescribedAs.<T>describedAs(description, matcher, values);
  }

  /**
   * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
   * examined {@link Iterable} yields items that are all matched by the specified
   * <code>itemMatcher</code>.
   * For example:
   * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>
   * 
   * @param itemMatcher
   *     the matcher to apply to every item provided by the examined {@link Iterable}
   */
  public static <U> org.hamcrest.Matcher<java.lang.Iterable<? extends U>> everyItem(org.hamcrest.Matcher<U> itemMatcher) {
    return org.hamcrest.core.Every.<U>everyItem(itemMatcher);
  }

  /**
   * Decorates another Matcher, retaining its behaviour, but allowing tests
   * to be slightly more expressive.
   * For example:
   * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
   * instead of:
   * <pre>assertThat(cheese, equalTo(smelly))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> is(org.hamcrest.Matcher<T> matcher) {
    return org.hamcrest.core.Is.<T>is(matcher);
  }

  /**
   * A shortcut to the frequently used <code>is(equalTo(x))</code>.
   * For example:
   * <pre>assertThat(cheese, is(smelly))</pre>
   * instead of:
   * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> is(T value) {
    return org.hamcrest.core.Is.<T>is(value);
  }

  /**
   * A shortcut to the frequently used <code>is(instanceOf(SomeClass.class))</code>.
   * For example:
   * <pre>assertThat(cheese, isA(Cheddar.class))</pre>
   * instead of:
   * <pre>assertThat(cheese, is(instanceOf(Cheddar.class)))</pre>
   */
  public static <T> org.hamcrest.Matcher<T> isA(java.lang.Class<T> type) {
    return org.hamcrest.core.Is.<T>isA(type);
  }

  /**
   * Creates a matcher that always matches, regardless of the examined object.
   */
  public static org.hamcrest.Matcher<java.lang.Object> anything() {
    return org.hamcrest.core.IsAnything.anything();
  }

  /**
   * Creates a matcher that always matches, regardless of the examined object, but describes
   * itself with the specified {@link String}.
   * 
   * @param description
   *     a meaningful {@link String} used when describing itself
   */
  public static org.hamcrest.Matcher<java.lang.Object> anything(java.lang.String description) {
    return org.hamcrest.core.IsAnything.anything(description);
  }

  /**
   * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
   * examined {@link Iterable} yields at least one item that is matched by the specified
   * <code>itemMatcher</code>.  Whilst matching, the traversal of the examined {@link Iterable}
   * will stop as soon as a matching item is found.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem(startsWith("ba")))</pre>
   * 
   * @param itemMatcher
   *     the matcher to apply to items provided by the examined {@link Iterable}
   * @Deprecated use org.hamcrest.MatchingCollections instead
   */
  public static <T> org.hamcrest.Matcher<java.lang.Iterable<? super T>> hasItem(Matcher<? super T> itemMatcher) {
    return MatchingCollections.hasItem(itemMatcher);
  }

  /**
   * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
   * examined {@link Iterable} yields at least one item that is equal to the specified
   * <code>item</code>.  Whilst matching, the traversal of the examined {@link Iterable}
   * will stop as soon as a matching item is found.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem("bar"))</pre>
   * 
   * @param item
   *     the item to compare against the items provided by the examined {@link Iterable}
   * @Deprecated use org.hamcrest.MatchingCollections instead
   */
  public static <T> org.hamcrest.Matcher<java.lang.Iterable<? super T>> hasItem(T item) {
    return MatchingCollections.hasItem(item);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
   * examined {@link Iterable} yield at least one item that is matched by the corresponding
   * matcher from the specified <code>itemMatchers</code>.  Whilst matching, each traversal of
   * the examined {@link Iterable} will stop as soon as a matching item is found.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))</pre>
   * 
   * @param itemMatchers
   *     the matchers to apply to items provided by the examined {@link Iterable}
   * @Deprecated use org.hamcrest.MatchingCollections instead
   */
  public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(Matcher<? super T>... itemMatchers) {
    return MatchingCollections.hasItems(itemMatchers);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
   * examined {@link Iterable} yield at least one item that is equal to the corresponding
   * item from the specified <code>items</code>.  Whilst matching, each traversal of the
   * examined {@link Iterable} will stop as soon as a matching item is found.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"))</pre>
   * 
   * @param items
   *     the items to compare against the items provided by the examined {@link Iterable}
   * @Deprecated use org.hamcrest.MatchingCollections instead
   */
  public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(T... items) {
    return MatchingCollections.hasItems(items);
  }

  /**
   * Creates a matcher that matches when the examined object is logically equal to the specified
   * <code>operand</code>, as determined by calling the {@link java.lang.Object#equals} method on
   * the <b>examined</b> object.
   * 
   * <p>If the specified operand is <code>null</code> then the created matcher will only match if
   * the examined object's <code>equals</code> method returns <code>true</code> when passed a
   * <code>null</code> (which would be a violation of the <code>equals</code> contract), unless the
   * examined object itself is <code>null</code>, in which case the matcher will return a positive
   * match.</p>
   * 
   * <p>The created matcher provides a special behaviour when examining <code>Array</code>s, whereby
   * it will match if both the operand and the examined object are arrays of the same length and
   * contain items that are equal to each other (according to the above rules) <b>in the same
   * indexes</b>.</p> 
   * For example:
   * <pre>
   * assertThat("foo", equalTo("foo"));
   * assertThat(new String[] {"foo", "bar"}, equalTo(new String[] {"foo", "bar"}));
   * </pre>
   */
  public static <T> org.hamcrest.Matcher<T> equalTo(T operand) {
    return org.hamcrest.core.IsEqual.<T>equalTo(operand);
  }

  /**
   * Creates an {@link org.hamcrest.core.IsEqual} matcher that does not enforce the values being
   * compared to be of the same static type.
   */
  public static org.hamcrest.Matcher<java.lang.Object> equalToObject(java.lang.Object operand) {
    return org.hamcrest.core.IsEqual.equalToObject(operand);
  }

  /**
   * Creates a matcher that matches when the examined object is an instance of the specified <code>type</code>,
   * as determined by calling the {@link java.lang.Class#isInstance(Object)} method on that type, passing the
   * the examined object.
   * 
   * <p>The created matcher forces a relationship between specified type and the examined object, and should be
   * used when it is necessary to make generics conform, for example in the JMock clause
   * <code>with(any(Thing.class))</code></p>
   * For example:
   * <pre>assertThat(new Canoe(), instanceOf(Canoe.class));</pre>
   */
  public static <T> org.hamcrest.Matcher<T> any(java.lang.Class<T> type) {
    return org.hamcrest.core.IsInstanceOf.<T>any(type);
  }

  /**
   * Creates a matcher that matches when the examined object is an instance of the specified <code>type</code>,
   * as determined by calling the {@link java.lang.Class#isInstance(Object)} method on that type, passing the
   * the examined object.
   * 
   * <p>The created matcher assumes no relationship between specified type and the examined object.</p>
   * For example:
   * <pre>assertThat(new Canoe(), instanceOf(Paddlable.class));</pre>
   */
  public static <T> org.hamcrest.Matcher<T> instanceOf(java.lang.Class<?> type) {
    return org.hamcrest.core.IsInstanceOf.<T>instanceOf(type);
  }

  /**
   * Creates a matcher that wraps an existing matcher, but inverts the logic by which
   * it will match.
   * For example:
   * <pre>assertThat(cheese, is(not(equalTo(smelly))))</pre>
   * 
   * @param matcher
   *     the matcher whose sense should be inverted
   */
  public static <T> org.hamcrest.Matcher<T> not(org.hamcrest.Matcher<T> matcher) {
    return org.hamcrest.core.IsNot.<T>not(matcher);
  }

  /**
   * A shortcut to the frequently used <code>not(equalTo(x))</code>.
   * For example:
   * <pre>assertThat(cheese, is(not(smelly)))</pre>
   * instead of:
   * <pre>assertThat(cheese, is(not(equalTo(smelly))))</pre>
   * 
   * @param value
   *     the value that any examined object should <b>not</b> equal
   */
  public static <T> org.hamcrest.Matcher<T> not(T value) {
    return org.hamcrest.core.IsNot.<T>not(value);
  }

  /**
   * A shortcut to the frequently used <code>not(nullValue())</code>.
   * For example:
   * <pre>assertThat(cheese, is(notNullValue()))</pre>
   * instead of:
   * <pre>assertThat(cheese, is(not(nullValue())))</pre>
   */
  public static org.hamcrest.Matcher<java.lang.Object> notNullValue() {
    return org.hamcrest.core.IsNull.notNullValue();
  }

  /**
   * A shortcut to the frequently used <code>not(nullValue(X.class)). Accepts a
   * single dummy argument to facilitate type inference.</code>.
   * For example:
   * <pre>assertThat(cheese, is(notNullValue(X.class)))</pre>
   * instead of:
   * <pre>assertThat(cheese, is(not(nullValue(X.class))))</pre>
   * 
   * @param type
   *     dummy parameter used to infer the generic type of the returned matcher
   */
  public static <T> org.hamcrest.Matcher<T> notNullValue(java.lang.Class<T> type) {
    return org.hamcrest.core.IsNull.<T>notNullValue(type);
  }

  /**
   * Creates a matcher that matches if examined object is <code>null</code>.
   * For example:
   * <pre>assertThat(cheese, is(nullValue())</pre>
   */
  public static org.hamcrest.Matcher<java.lang.Object> nullValue() {
    return org.hamcrest.core.IsNull.nullValue();
  }

  /**
   * Creates a matcher that matches if examined object is <code>null</code>. Accepts a
   * single dummy argument to facilitate type inference.
   * For example:
   * <pre>assertThat(cheese, is(nullValue(Cheese.class))</pre>
   * 
   * @param type
   *     dummy parameter used to infer the generic type of the returned matcher
   */
  public static <T> org.hamcrest.Matcher<T> nullValue(java.lang.Class<T> type) {
    return org.hamcrest.core.IsNull.<T>nullValue(type);
  }

  /**
   * Creates a matcher that matches only when the examined object is the same instance as
   * the specified target object.
   * 
   * @param target
   *     the target instance against which others should be assessed
   */
  public static <T> org.hamcrest.Matcher<T> sameInstance(T target) {
    return org.hamcrest.core.IsSame.<T>sameInstance(target);
  }

  /**
   * Creates a matcher that matches only when the examined object is the same instance as
   * the specified target object.
   * 
   * @param target
   *     the target instance against which others should be assessed
   */
  public static <T> org.hamcrest.Matcher<T> theInstance(T target) {
    return org.hamcrest.core.IsSame.<T>theInstance(target);
  }

  /**
   * Creates a matcher that matches if the examined {@link String} contains the specified
   * {@link String} anywhere.
   * For example:
   * <pre>assertThat("myStringOfNote", containsString("ring"))</pre>
   * 
   * @param substring
   *     the substring that the returned matcher will expect to find within any examined string
   */
  public static org.hamcrest.Matcher<java.lang.String> containsString(java.lang.String substring) {
    return org.hamcrest.core.StringContains.containsString(substring);
  }

  /**
   * Creates a matcher that matches if the examined {@link String} contains the specified
   * {@link String} anywhere, ignoring case.
   * For example:
   * <pre>assertThat("myStringOfNote", containsString("ring"))</pre>
   * 
   * @param substring
   *     the substring that the returned matcher will expect to find within any examined string
   */
  public static org.hamcrest.Matcher<java.lang.String> containsStringIgnoringCase(java.lang.String substring) {
    return org.hamcrest.core.StringContains.containsStringIgnoringCase(substring);
  }

  /**
   * <p>
   * Creates a matcher that matches if the examined {@link String} starts with the specified
   * {@link String}.
   * </p>
   * For example:
   * <pre>assertThat("myStringOfNote", startsWith("my"))</pre>
   * 
   * @param prefix
   *      the substring that the returned matcher will expect at the start of any examined string
   */
  public static org.hamcrest.Matcher<java.lang.String> startsWith(java.lang.String prefix) {
    return org.hamcrest.core.StringStartsWith.startsWith(prefix);
  }

  /**
   * <p>
   * Creates a matcher that matches if the examined {@link String} starts with the specified
   * {@link String}, ignoring case
   * </p>
   * For example:
   * <pre>assertThat("myStringOfNote", startsWith("my"))</pre>
   * 
   * @param prefix
   *      the substring that the returned matcher will expect at the start of any examined string
   */
  public static org.hamcrest.Matcher<java.lang.String> startsWithIgnoringCase(java.lang.String prefix) {
    return org.hamcrest.core.StringStartsWith.startsWithIgnoringCase(prefix);
  }

  /**
   * Creates a matcher that matches if the examined {@link String} ends with the specified
   * {@link String}.
   * For example:
   * <pre>assertThat("myStringOfNote", endsWith("Note"))</pre>
   * 
   * @param suffix
   *      the substring that the returned matcher will expect at the end of any examined string
   */
  public static org.hamcrest.Matcher<java.lang.String> endsWith(java.lang.String suffix) {
    return org.hamcrest.core.StringEndsWith.endsWith(suffix);
  }

  /**
   * Creates a matcher that matches if the examined {@link String} ends with the specified
   * {@link String}, ignoring case.
   * For example:
   * <pre>assertThat("myStringOfNote", endsWith("Note"))</pre>
   * 
   * @param suffix
   *      the substring that the returned matcher will expect at the end of any examined string
   */
  public static org.hamcrest.Matcher<java.lang.String> endsWithIgnoringCase(java.lang.String suffix) {
    return org.hamcrest.core.StringEndsWith.endsWithIgnoringCase(suffix);
  }

  /**
   * Creates a matcher that matches arrays whose elements are satisfied by the specified matchers.  Matches
   * positively only if the number of matchers specified is equal to the length of the examined array and
   * each matcher[i] is satisfied by array[i].
   * For example:
   * <pre>assertThat(new Integer[]{1,2,3}, is(array(equalTo(1), equalTo(2), equalTo(3))))</pre>
   * 
   * @param elementMatchers
   *     the matchers that the elements of examined arrays should satisfy
   */
  public static <T> org.hamcrest.collection.IsArray<T> array(Matcher<? super T>... elementMatchers) {
    return org.hamcrest.collection.IsArray.<T>array(elementMatchers);
  }

  /**
   * Creates a matcher for arrays that matches when the examined array contains at least one item
   * that is matched by the specified <code>elementMatcher</code>.  Whilst matching, the traversal
   * of the examined array will stop as soon as a matching element is found.
   * For example:
   * <pre>assertThat(new String[] {"foo", "bar"}, hasItemInArray(startsWith("ba")))</pre>
   * 
   * @param elementMatcher
   *     the matcher to apply to elements in examined arrays
   */
  public static <T> org.hamcrest.Matcher<T[]> hasItemInArray(Matcher<? super T> elementMatcher) {
    return org.hamcrest.collection.IsArrayContaining.<T>hasItemInArray(elementMatcher);
  }

  /**
   * A shortcut to the frequently used <code>hasItemInArray(equalTo(x))</code>.
   * For example:
   * <pre>assertThat(hasItemInArray(x))</pre>
   * instead of:
   * <pre>assertThat(hasItemInArray(equalTo(x)))</pre>
   * 
   * @param element
   *     the element that should be present in examined arrays
   */
  public static <T> org.hamcrest.Matcher<T[]> hasItemInArray(T element) {
    return org.hamcrest.collection.IsArrayContaining.<T>hasItemInArray(element);
  }

  /**
   * Creates a matcher for arrays that matches when each item in the examined array is
   * logically equal to the corresponding item in the specified items.  For a positive match,
   * the examined array must be of the same length as the number of specified items.
   * For example:
   * <pre>assertThat(new String[]{"foo", "bar"}, contains("foo", "bar"))</pre>
   * 
   * @param items
   *     the items that must equal the items within an examined array
   */
  public static <E> org.hamcrest.Matcher<E[]> arrayContaining(E... items) {
    return org.hamcrest.collection.IsArrayContainingInOrder.<E>arrayContaining(items);
  }

  /**
   * Creates a matcher for arrays that matches when each item in the examined array satisfies the
   * corresponding matcher in the specified matchers.  For a positive match, the examined array
   * must be of the same length as the number of specified matchers.
   * For example:
   * <pre>assertThat(new String[]{"foo", "bar"}, contains(equalTo("foo"), equalTo("bar")))</pre>
   * 
   * @param itemMatchers
   *     the matchers that must be satisfied by the items in the examined array
   */
  public static <E> org.hamcrest.Matcher<E[]> arrayContaining(Matcher<? super E>... itemMatchers) {
    return org.hamcrest.collection.IsArrayContainingInOrder.<E>arrayContaining(itemMatchers);
  }

  /**
   * Creates a matcher for arrays that matches when each item in the examined array satisfies the
   * corresponding matcher in the specified list of matchers.  For a positive match, the examined array
   * must be of the same length as the specified list of matchers.
   * For example:
   * <pre>assertThat(new String[]{"foo", "bar"}, contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
   * 
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by the corresponding item in an examined array
   */
  public static <E> org.hamcrest.Matcher<E[]> arrayContaining(List<Matcher<? super E>> itemMatchers) {
    return org.hamcrest.collection.IsArrayContainingInOrder.<E>arrayContaining(itemMatchers);
  }

  /**
   * <p>
   * Creates an order agnostic matcher for arrays that matches when each item in the
   * examined array satisfies one matcher anywhere in the specified matchers.
   * For a positive match, the examined array must be of the same length as the number of
   * specified matchers.
   * </p>
   * <p>
   * N.B. each of the specified matchers will only be used once during a given examination, so be
   * careful when specifying matchers that may be satisfied by more than one entry in an examined
   * array.
   * </p>
   * <p>
   * For example:
   * </p>
   * <pre>assertThat(new String[]{"foo", "bar"}, arrayContainingInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
   * 
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by an entry in an examined array
   */
  public static <E> org.hamcrest.Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... itemMatchers) {
    return org.hamcrest.collection.IsArrayContainingInAnyOrder.<E>arrayContainingInAnyOrder(itemMatchers);
  }

  /**
   * <p>
   * Creates an order agnostic matcher for arrays that matches when each item in the
   * examined array satisfies one matcher anywhere in the specified collection of matchers.
   * For a positive match, the examined array must be of the same length as the specified collection
   * of matchers.
   * </p>
   * <p>
   * N.B. each matcher in the specified collection will only be used once during a given
   * examination, so be careful when specifying matchers that may be satisfied by more than
   * one entry in an examined array.
   * </p>
   * <p>
   * For example:
   * </p>
   * <pre>assertThat(new String[]{"foo", "bar"}, arrayContainingInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
   * 
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by an item provided by an examined array
   */
  public static <E> org.hamcrest.Matcher<E[]> arrayContainingInAnyOrder(java.util.Collection<Matcher<? super E>> itemMatchers) {
    return org.hamcrest.collection.IsArrayContainingInAnyOrder.<E>arrayContainingInAnyOrder(itemMatchers);
  }

  /**
   * <p>Creates an order agnostic matcher for arrays that matches when each item in the
   * examined array is logically equal to one item anywhere in the specified items.
   * For a positive match, the examined array must be of the same length as the number of
   * specified items.
   * </p>
   * <p>N.B. each of the specified items will only be used once during a given examination, so be
   * careful when specifying items that may be equal to more than one entry in an examined
   * array.
   * </p>
   * <p>
   * For example:
   * </p>
   * <pre>assertThat(new String[]{"foo", "bar"}, containsInAnyOrder("bar", "foo"))</pre>
   * 
   * @param items
   *     the items that must equal the entries of an examined array, in any order
   */
  public static <E> org.hamcrest.Matcher<E[]> arrayContainingInAnyOrder(E... items) {
    return org.hamcrest.collection.IsArrayContainingInAnyOrder.<E>arrayContainingInAnyOrder(items);
  }

  /**
   * Creates a matcher for arrays that matches when the <code>length</code> of the array
   * satisfies the specified matcher.
   * For example:
   * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(equalTo(2)))</pre>
   * 
   * @param sizeMatcher
   *     a matcher for the length of an examined array
   */
  public static <E> org.hamcrest.Matcher<E[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
    return org.hamcrest.collection.IsArrayWithSize.<E>arrayWithSize(sizeMatcher);
  }

  /**
   * Creates a matcher for arrays that matches when the <code>length</code> of the array
   * equals the specified <code>size</code>.
   * For example:
   * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(2))</pre>
   * 
   * @param size
   *     the length that an examined array must have for a positive match
   */
  public static <E> org.hamcrest.Matcher<E[]> arrayWithSize(int size) {
    return org.hamcrest.collection.IsArrayWithSize.<E>arrayWithSize(size);
  }

  /**
   * Creates a matcher for arrays that matches when the <code>length</code> of the array
   * is zero.
   * For example:
   * <pre>assertThat(new String[0], emptyArray())</pre>
   */
  public static <E> org.hamcrest.Matcher<E[]> emptyArray() {
    return org.hamcrest.collection.IsArrayWithSize.<E>emptyArray();
  }

  /**
   * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
   * a value that satisfies the specified matcher.
   * For example:
   * <pre>assertThat(myMap, is(aMapWithSize(equalTo(2))))</pre>
   * 
   * @param sizeMatcher
   *     a matcher for the size of an examined {@link java.util.Map}
   */
  public static <K, V> org.hamcrest.Matcher<java.util.Map<? extends K,? extends V>> aMapWithSize(Matcher<? super Integer> sizeMatcher) {
    return org.hamcrest.collection.IsMapWithSize.<K,V>aMapWithSize(sizeMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
   * a value equal to the specified <code>size</code>.
   * For example:
   * <pre>assertThat(myMap, is(aMapWithSize(2)))</pre>
   * 
   * @param size
   *     the expected size of an examined {@link java.util.Map}
   */
  public static <K, V> org.hamcrest.Matcher<java.util.Map<? extends K,? extends V>> aMapWithSize(int size) {
    return org.hamcrest.collection.IsMapWithSize.<K,V>aMapWithSize(size);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
   * zero.
   * For example:
   * <pre>assertThat(myMap, is(anEmptyMap()))</pre>
   */
  public static <K, V> org.hamcrest.Matcher<java.util.Map<? extends K,? extends V>> anEmptyMap() {
    return org.hamcrest.collection.IsMapWithSize.<K,V>anEmptyMap();
  }

  /**
   * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
   * a value that satisfies the specified matcher.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(equalTo(2)))</pre>
   * 
   * @param sizeMatcher
   *     a matcher for the size of an examined {@link java.util.Collection}
   */
  public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> hasSize(Matcher<? super Integer> sizeMatcher) {
    return org.hamcrest.collection.IsCollectionWithSize.<E>hasSize(sizeMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
   * a value equal to the specified <code>size</code>.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(2))</pre>
   * 
   * @param size
   *     the expected size of an examined {@link java.util.Collection}
   */
  public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> hasSize(int size) {
    return org.hamcrest.collection.IsCollectionWithSize.<E>hasSize(size);
  }

  /**
   * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
   * method returns <code>true</code>.
   * For example:
   * <pre>assertThat(new ArrayList&lt;String&gt;(), is(empty()))</pre>
   */
  public static <E> org.hamcrest.Matcher<java.util.Collection<? extends E>> empty() {
    return org.hamcrest.collection.IsEmptyCollection.<E>empty();
  }

  /**
   * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
   * method returns <code>true</code>.
   * For example:
   * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyCollectionOf(String.class)))</pre>
   * 
   * @param unusedToForceReturnType
   *     the type of the collection's content
   */
  public static <E> org.hamcrest.Matcher<java.util.Collection<E>> emptyCollectionOf(java.lang.Class<E> unusedToForceReturnType) {
    return org.hamcrest.collection.IsEmptyCollection.<E>emptyCollectionOf(unusedToForceReturnType);
  }

  /**
   * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
   * For example:
   * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterable()))</pre>
   */
  public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
    return org.hamcrest.collection.IsEmptyIterable.<E>emptyIterable();
  }

  /**
   * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
   * For example:
   * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterableOf(String.class)))</pre>
   * 
   * @param unusedToForceReturnType
   *     the type of the iterable's content
   */
  public static <E> Matcher<Iterable<E>> emptyIterableOf(java.lang.Class<E> unusedToForceReturnType) {
    return org.hamcrest.collection.IsEmptyIterable.<E>emptyIterableOf(unusedToForceReturnType);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, each logically equal to the
   * corresponding item in the specified items.  For a positive match, the examined iterable
   * must be of the same length as the number of specified items.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"))</pre>
   * 
   * @param items
   *     the items that must equal the items provided by an examined {@link Iterable}
   */
  public static <E> Matcher<Iterable<? extends E>> contains(E... items) {
    return MatchingCollections.<E>contains(items);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a single item that satisfies the specified matcher.
   * For a positive match, the examined iterable must only yield one item.
   * For example:
   * <pre>assertThat(Arrays.asList("foo"), contains(equalTo("foo")))</pre>
   * 
   * @param itemMatcher
   *     the matcher that must be satisfied by the single item provided by an
   *     examined {@link Iterable}
   */
  public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> itemMatcher) {
    return MatchingCollections.<E>contains(itemMatcher);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, each satisfying the corresponding
   * matcher in the specified matchers.  For a positive match, the examined iterable
   * must be of the same length as the number of specified matchers.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), contains(equalTo("foo"), equalTo("bar")))</pre>
   * 
   * @param itemMatchers
   *     the matchers that must be satisfied by the items provided by an examined {@link Iterable}
   */
  public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... itemMatchers) {
    return MatchingCollections.<E>contains(itemMatchers);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, each satisfying the corresponding
   * matcher in the specified list of matchers.  For a positive match, the examined iterable
   * must be of the same length as the specified list of matchers.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
   * 
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by the corresponding item provided by
   *     an examined {@link Iterable}
   */
  public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> itemMatchers) {
    return MatchingCollections.<E>contains(itemMatchers);
  }

  /**
   * <p>
   * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
   * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
   * in the specified matchers.  For a positive match, the examined iterable must be of the same
   * length as the number of specified matchers.
   * </p>
   * <p>
   * N.B. each of the specified matchers will only be used once during a given examination, so be
   * careful when specifying matchers that may be satisfied by more than one entry in an examined
   * iterable.
   * </p>
   * <p>
   * For example:
   * </p>
   * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
   * 
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
   */
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
    return MatchingCollections.<T>containsInAnyOrder(itemMatchers);
  }

  /**
   * <p>
   * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
   * the examined {@link Iterable} yields a series of items, each logically equal to one item
   * anywhere in the specified items. For a positive match, the examined iterable
   * must be of the same length as the number of specified items.
   * </p>
   * <p>
   * N.B. each of the specified items will only be used once during a given examination, so be
   * careful when specifying items that may be equal to more than one entry in an examined
   * iterable.
   * </p>
   * <p>
   * For example:
   * </p>
   * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder("bar", "foo"))</pre>
   * 
   * @param items
   *     the items that must equal the items provided by an examined {@link Iterable} in any order
   */
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
    return MatchingCollections.<T>containsInAnyOrder(items);
  }

  /**
   * <p>
   * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
   * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
   * in the specified collection of matchers.  For a positive match, the examined iterable
   * must be of the same length as the specified collection of matchers.
   * </p>
   * <p>
   * N.B. each matcher in the specified collection will only be used once during a given
   * examination, so be careful when specifying matchers that may be satisfied by more than
   * one entry in an examined iterable.
   * </p>
   * <p>For example:</p>
   * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
   * 
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
   */
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
    return MatchingCollections.<T>containsInAnyOrder(itemMatchers);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, that contains items logically equal to the
   * corresponding item in the specified items, in the same relative order
   * For example:
   * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder("b", "d"))</pre>
   * 
   * @param items
   *     the items that must be contained within items provided by an examined {@link Iterable} in the same relative order
   */
  public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(E... items) {
    return MatchingCollections.<E>containsInRelativeOrder(items);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, that each satisfying the corresponding
   * matcher in the specified matchers, in the same relative order.
   * For example:
   * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder(equalTo("b"), equalTo("d")))</pre>
   * 
   * @param itemMatchers
   *     the matchers that must be satisfied by the items provided by an examined {@link Iterable} in the same relative order
   */
  public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(Matcher<? super E>... itemMatchers) {
    return MatchingCollections.<E>containsInRelativeOrder(itemMatchers);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, that contains items satisfying the corresponding
   * matcher in the specified list of matchers, in the same relative order.
   * For example:
   * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), contains(Arrays.asList(equalTo("b"), equalTo("d"))))</pre>
   * 
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by the items provided by
   *     an examined {@link Iterable} in the same relative order
   */
  public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(List<Matcher<? super E>> itemMatchers) {
    return MatchingCollections.<E>containsInRelativeOrder(itemMatchers);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields an item count that satisfies the specified
   * matcher.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(equalTo(2)))</pre>
   * 
   * @param sizeMatcher
   *     a matcher for the number of items that should be yielded by an examined {@link Iterable}
   */
  public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
    return MatchingCollections.<E>iterableWithSize(sizeMatcher);
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields an item count that is equal to the specified
   * <code>size</code> argument.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(2))</pre>
   * 
   * @param size
   *     the number of items that should be yielded by an examined {@link Iterable}
   */
  public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
    return MatchingCollections.<E>iterableWithSize(size);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
   * at least one entry whose key satisfies the specified <code>keyMatcher</code> <b>and</b> whose
   * value satisfies the specified <code>valueMatcher</code>.
   * For example:
   * <pre>assertThat(myMap, hasEntry(equalTo("bar"), equalTo("foo")))</pre>
   * 
   * @param keyMatcher
   *     the key matcher that, in combination with the valueMatcher, must be satisfied by at least one entry
   * @param valueMatcher
   *     the value matcher that, in combination with the keyMatcher, must be satisfied by at least one entry
   */
  public static <K, V> org.hamcrest.Matcher<java.util.Map<? extends K,? extends V>> hasEntry(org.hamcrest.Matcher<? super K> keyMatcher, org.hamcrest.Matcher<? super V> valueMatcher) {
    return org.hamcrest.collection.IsMapContaining.<K,V>hasEntry(keyMatcher, valueMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
   * at least one entry whose key equals the specified <code>key</code> <b>and</b> whose value equals the
   * specified <code>value</code>.
   * For example:
   * <pre>assertThat(myMap, hasEntry("bar", "foo"))</pre>
   * 
   * @param key
   *     the key that, in combination with the value, must be describe at least one entry
   * @param value
   *     the value that, in combination with the key, must be describe at least one entry
   */
  public static <K, V> org.hamcrest.Matcher<java.util.Map<? extends K,? extends V>> hasEntry(K key, V value) {
    return org.hamcrest.collection.IsMapContaining.<K,V>hasEntry(key, value);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map}'s set of entries
   * satisfies the specified <code>entriesMatcher</code>.
   * For example:
   * <pre>assertThat(myMap, hasEntries(hasSize(2)))</pre>
   *
   * @param entriesMatcher
   *     the matcher that must be satisfied by the set of entries
   */
  public static <K, V> org.hamcrest.Matcher<java.util.Map<? extends K,? extends V>> hasEntries(Matcher<? super java.util.Set<? extends java.util.Map.Entry<? extends K, ? extends V>>> entriesMatcher) {
    return org.hamcrest.collection.IsMapWithEntries.hasEntries(entriesMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map}'s set of entries
   * contains, in any order, entries satisfying the specified <code>entriesMatchers</code>.
   * For example:
   * <pre>assertThat(myMap, hasEntries(entry("a key"), entry("another key")))</pre>
   *
   * @param entriesMatchers
   *     the matchers that must be satisfied by the entries
   */
  @SafeVarargs
  public static <K, V> Matcher<java.util.Map<? extends K, ? extends V>> hasEntries(Matcher<? super java.util.Map.Entry<? extends K, ? extends V>>... entriesMatchers) {
    return org.hamcrest.collection.IsMapWithEntries.hasEntries(entriesMatchers);
  }

  /**
   * Creates a matcher for {@link java.util.Map.Entry}s matching when the examined {@link java.util.Map.Entry} has a key which satisfies
   * the specified <code>keyMatcher</code>, and a value which satisfies the specified <code>valueMatcher</code>.
   * For example:
   * <pre>assertThat(myMap.keySet(), hasItem(entry(equalTo("key"), notNullValue())))</pre>
   *
   * @param keyMatcher
   *     the matcher that must be satisfied by the key
   * @param valueMatcher
   *     the matcher that must be satisfied by the value
   */
  public static <K, V> Matcher<? super java.util.Map.Entry<? extends K, ? extends V>> entry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
    return org.hamcrest.collection.IsMapEntry.entry(keyMatcher, valueMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Map.Entry}s matching when the examined {@link java.util.Map.Entry} has a key which satisfies
   * the specified <code>keyMatcher</code>; the value is ignored.
   * For example:
   * <pre>assertThat(myMap.keySet(), hasItem(entry(equalTo("key"))))</pre>
   *
   * @param keyMatcher
   *     the matcher that must be satisfied by the key
   */
  public static <K> Matcher<? super java.util.Map.Entry<? extends K, ?>> entry(Matcher<? super K> keyMatcher) {
    return org.hamcrest.collection.IsMapEntry.entry(keyMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Map.Entry}s matching when the examined {@link java.util.Map.Entry} has the specified
   * <code>key</code>, and a value which satisfies the specified <code>valueMatcher</code>.
   * For example:
   * <pre>assertThat(myMap.keySet(), hasItem(entry("key", notNullValue())))</pre>
   *
   * @param key
   *     the required key
   * @param valueMatcher
   *     the matcher that must be satisfied by the value
   */
  public static <K, V> Matcher<? super java.util.Map.Entry<? extends K, ? extends V>> entry(K key, Matcher<? super V> valueMatcher) {
    return org.hamcrest.collection.IsMapEntry.entry(key, valueMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Map.Entry}s matching when the examined {@link java.util.Map.Entry} has the specified
   * <code>key</code>; the value is ignored.
   * For example:
   * <pre>assertThat(myMap.keySet(), hasItem(entry("key")))</pre>
   *
   * @param key
   *     the required key
   */
  public static <K> Matcher<? super java.util.Map.Entry<? extends K, ?>> entry(K key) {
    return org.hamcrest.collection.IsMapEntry.entry(key);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
   * at least one key that satisfies the specified matcher.
   * For example:
   * <pre>assertThat(myMap, hasKey(equalTo("bar")))</pre>
   * 
   * @param keyMatcher
   *     the matcher that must be satisfied by at least one key
   */
  public static <K> org.hamcrest.Matcher<java.util.Map<? extends K,?>> hasKey(org.hamcrest.Matcher<? super K> keyMatcher) {
    return org.hamcrest.collection.IsMapContaining.<K>hasKey(keyMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
   * at least one key that is equal to the specified key.
   * For example:
   * <pre>assertThat(myMap, hasKey("bar"))</pre>
   * 
   * @param key
   *     the key that satisfying maps must contain
   */
  public static <K> org.hamcrest.Matcher<java.util.Map<? extends K,?>> hasKey(K key) {
    return org.hamcrest.collection.IsMapContaining.<K>hasKey(key);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
   * at least one value that satisfies the specified valueMatcher.
   * For example:
   * <pre>assertThat(myMap, hasValue(equalTo("foo")))</pre>
   * 
   * @param valueMatcher
   *     the matcher that must be satisfied by at least one value
   */
  public static <V> org.hamcrest.Matcher<java.util.Map<?,? extends V>> hasValue(org.hamcrest.Matcher<? super V> valueMatcher) {
    return org.hamcrest.collection.IsMapContaining.<V>hasValue(valueMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
   * at least one value that is equal to the specified value.
   * For example:
   * <pre>assertThat(myMap, hasValue("foo"))</pre>
   * 
   * @param value
   *     the value that satisfying maps must contain
   */
  public static <V> org.hamcrest.Matcher<java.util.Map<?,? extends V>> hasValue(V value) {
    return org.hamcrest.collection.IsMapContaining.<V>hasValue(value);
  }

  /**
   * Creates a matcher that matches when the examined object is found within the
   * specified collection.
   * For example:
   * <pre>assertThat("foo", is(in(Arrays.asList("bar", "foo"))))</pre>
   * 
   * @param collection
   *     the collection in which matching items must be found
   */
  public static <T> org.hamcrest.Matcher<T> in(java.util.Collection<T> collection) {
    return org.hamcrest.collection.IsIn.<T>in(collection);
  }

  /**
   * Creates a matcher that matches when the examined object is found within the
   * specified array.
   * For example:
   * <pre>assertThat("foo", is(in(new String[]{"bar", "foo"})))</pre>
   * 
   * @param elements
   *     the array in which matching items must be found
   */
  public static <T> org.hamcrest.Matcher<T> in(T[] elements) {
    return org.hamcrest.collection.IsIn.<T>in(elements);
  }

  /**
   * Creates a matcher that matches when the examined object is found within the
   * specified collection.
   * For example:
   * <pre>assertThat("foo", isIn(Arrays.asList("bar", "foo")))</pre>
   * 
   * @deprecated use is(in(...)) instead
   * @param collection
   *     the collection in which matching items must be found
   */
  public static <T> org.hamcrest.Matcher<T> isIn(java.util.Collection<T> collection) {
    return org.hamcrest.collection.IsIn.<T>isIn(collection);
  }

  /**
   * Creates a matcher that matches when the examined object is found within the
   * specified array.
   * For example:
   * <pre>assertThat("foo", isIn(new String[]{"bar", "foo"}))</pre>
   * 
   * @deprecated use is(in(...)) instead
   * @param elements
   *     the array in which matching items must be found
   */
  public static <T> org.hamcrest.Matcher<T> isIn(T[] elements) {
    return org.hamcrest.collection.IsIn.<T>isIn(elements);
  }

  /**
   * Creates a matcher that matches when the examined object is equal to one of the
   * specified elements.
   * For example:
   * <pre>assertThat("foo", isOneOf("bar", "foo"))</pre>
   * 
   * @deprecated use is(oneOf(...)) instead
   * @param elements
   *     the elements amongst which matching items will be found
   */
  public static <T> org.hamcrest.Matcher<T> isOneOf(T... elements) {
    return org.hamcrest.collection.IsIn.<T>isOneOf(elements);
  }

  /**
   * Creates a matcher that matches when the examined object is equal to one of the
   * specified elements.
   * For example:
   * <pre>assertThat("foo", is(oneOf("bar", "foo")))</pre>
   * 
   * @param elements
   *     the elements amongst which matching items will be found
   */
  public static <T> org.hamcrest.Matcher<T> oneOf(T... elements) {
    return org.hamcrest.collection.IsIn.<T>oneOf(elements);
  }

  /**
   * Creates a matcher of {@link Double}s that matches when an examined double is equal
   * to the specified <code>operand</code>, within a range of +/- <code>error</code>.
   * For example:
   * <pre>assertThat(1.03, is(closeTo(1.0, 0.03)))</pre>
   * 
   * @param operand
   *     the expected value of matching doubles
   * @param error
   *     the delta (+/-) within which matches will be allowed
   */
  public static org.hamcrest.Matcher<java.lang.Double> closeTo(double operand, double error) {
    return org.hamcrest.number.IsCloseTo.closeTo(operand, error);
  }

  /**
   * Creates a matcher of {@link Double}s that matches when an examined double is not a number.
   * For example:
   * <pre>assertThat(Double.NaN, is(notANumber()))</pre>
   */
  public static org.hamcrest.Matcher<java.lang.Double> notANumber() {
    return org.hamcrest.number.IsNaN.notANumber();
  }

  /**
   * Creates a matcher of {@link java.math.BigDecimal}s that matches when an examined BigDecimal is equal
   * to the specified <code>operand</code>, within a range of +/- <code>error</code>. The comparison for equality
   * is done by BigDecimals {@link java.math.BigDecimal#compareTo(java.math.BigDecimal)} method.
   * For example:
   * <pre>assertThat(new BigDecimal("1.03"), is(closeTo(new BigDecimal("1.0"), new BigDecimal("0.03"))))</pre>
   * 
   * @param operand
   *     the expected value of matching BigDecimals
   * @param error
   *     the delta (+/-) within which matches will be allowed
   */
  public static org.hamcrest.Matcher<java.math.BigDecimal> closeTo(java.math.BigDecimal operand, java.math.BigDecimal error) {
    return org.hamcrest.number.BigDecimalCloseTo.closeTo(operand, error);
  }

  /**
   * Creates a matcher of {@link Comparable} object that matches when the examined object is
   * equal to the specified value, as reported by the <code>compareTo</code> method of the
   * <b>examined</b> object.
   * For example:
   * <pre>assertThat(1, comparesEqualTo(1))</pre>
   * 
   * @param value the value which, when passed to the compareTo method of the examined object, should return zero
   */
  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> comparesEqualTo(T value) {
    return org.hamcrest.number.OrderingComparison.<T>comparesEqualTo(value);
  }

  /**
   * Creates a matcher of {@link Comparable} object that matches when the examined object is
   * greater than the specified value, as reported by the <code>compareTo</code> method of the
   * <b>examined</b> object.
   * For example:
   * <pre>assertThat(2, greaterThan(1))</pre>
   * 
   * @param value the value which, when passed to the compareTo method of the examined object, should return greater
   *              than zero
   */
  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThan(T value) {
    return org.hamcrest.number.OrderingComparison.<T>greaterThan(value);
  }

  /**
   * Creates a matcher of {@link Comparable} object that matches when the examined object is
   * greater than or equal to the specified value, as reported by the <code>compareTo</code> method
   * of the <b>examined</b> object.
   * For example:
   * <pre>assertThat(1, greaterThanOrEqualTo(1))</pre>
   * 
   * @param value the value which, when passed to the compareTo method of the examined object, should return greater
   *              than or equal to zero
   */
  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThanOrEqualTo(T value) {
    return org.hamcrest.number.OrderingComparison.<T>greaterThanOrEqualTo(value);
  }

  /**
   * Creates a matcher of {@link Comparable} object that matches when the examined object is
   * less than the specified value, as reported by the <code>compareTo</code> method of the
   * <b>examined</b> object.
   * For example:
   * <pre>assertThat(1, lessThan(2))</pre>
   * 
   * @param value the value which, when passed to the compareTo method of the examined object, should return less
   *              than zero
   */
  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThan(T value) {
    return org.hamcrest.number.OrderingComparison.<T>lessThan(value);
  }

  /**
   * Creates a matcher of {@link Comparable} object that matches when the examined object is
   * less than or equal to the specified value, as reported by the <code>compareTo</code> method
   * of the <b>examined</b> object.
   * For example:
   * <pre>assertThat(1, lessThanOrEqualTo(1))</pre>
   * 
   * @param value the value which, when passed to the compareTo method of the examined object, should return less
   *              than or equal to zero
   */
  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThanOrEqualTo(T value) {
    return org.hamcrest.number.OrderingComparison.<T>lessThanOrEqualTo(value);
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string is equal to
   * the specified expectedString, ignoring case.
   * For example:
   * <pre>assertThat("Foo", equalToIgnoringCase("FOO"))</pre>
   * 
   * @param expectedString
   *     the expected value of matched strings
   */
  public static org.hamcrest.Matcher<java.lang.String> equalToIgnoringCase(java.lang.String expectedString) {
    return org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase(expectedString);
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string is equal to
   * the specified expectedString, when whitespace differences are (mostly) ignored.  To be
   * exact, the following whitespace rules are applied:
   * <ul>
   *   <li>all leading and trailing whitespace of both the expectedString and the examined string are ignored</li>
   *   <li>any remaining whitespace, appearing within either string, is collapsed to a single space before comparison</li>
   * </ul>
   * For example:
   * <pre>assertThat("   my\tfoo  bar ", equalToIgnoringWhiteSpace(" my  foo bar"))</pre>
   * 
   * @param expectedString
   *     the expected value of matched strings
   */
  public static org.hamcrest.Matcher<java.lang.String> equalToIgnoringWhiteSpace(java.lang.String expectedString) {
    return org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(expectedString);
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
   * has zero length.
   * For example:
   * <pre>assertThat(((String)null), is(emptyOrNullString()))</pre>
   */
  public static org.hamcrest.Matcher<java.lang.String> emptyOrNullString() {
    return org.hamcrest.text.IsEmptyString.emptyOrNullString();
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string has zero length.
   * For example:
   * <pre>assertThat("", is(emptyString()))</pre>
   */
  public static org.hamcrest.Matcher<java.lang.String> emptyString() {
    return org.hamcrest.text.IsEmptyString.emptyString();
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
   * has zero length.
   * For example:
   * <pre>assertThat(((String)null), isEmptyOrNullString())</pre>
   * 
   * @deprecated use is(emptyOrNullString()) instead
   */
  public static org.hamcrest.Matcher<java.lang.String> isEmptyOrNullString() {
    return org.hamcrest.text.IsEmptyString.isEmptyOrNullString();
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string has zero length.
   * For example:
   * <pre>assertThat("", isEmptyString())</pre>
   * 
   * @deprecated use is(emptyString()) instead
   */
  public static org.hamcrest.Matcher<java.lang.String> isEmptyString() {
    return org.hamcrest.text.IsEmptyString.isEmptyString();
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
   * contains zero or more whitespace characters and nothing else.
   * For example:
   * <pre>assertThat(((String)null), is(blankOrNullString()))</pre>
   */
  public static org.hamcrest.Matcher<java.lang.String> blankOrNullString() {
    return org.hamcrest.text.IsBlankString.blankOrNullString();
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string contains
   * zero or more whitespace characters and nothing else.
   * For example:
   * <pre>assertThat("  ", is(blankString()))</pre>
   */
  public static org.hamcrest.Matcher<java.lang.String> blankString() {
    return org.hamcrest.text.IsBlankString.blankString();
  }

  /**
   * Creates a matcher of {@link java.lang.String} that matches when the examined string
   * exactly matches the given {@link java.util.regex.Pattern}.
   */
  public static org.hamcrest.Matcher<java.lang.String> matchesPattern(java.util.regex.Pattern pattern) {
    return org.hamcrest.text.MatchesPattern.matchesPattern(pattern);
  }

  /**
   * Creates a matcher of {@link java.lang.String} that matches when the examined string
   * exactly matches the given regular expression, treated as a {@link java.util.regex.Pattern}.
   */
  public static org.hamcrest.Matcher<java.lang.String> matchesPattern(java.lang.String regex) {
    return org.hamcrest.text.MatchesPattern.matchesPattern(regex);
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string contains all of
   * the specified substrings, considering the order of their appearance.
   * For example:
   * <pre>assertThat("myfoobarbaz", stringContainsInOrder(Arrays.asList("bar", "foo")))</pre>
   * fails as "foo" occurs before "bar" in the string "myfoobarbaz"
   * 
   * @param substrings
   *     the substrings that must be contained within matching strings
   */
  public static org.hamcrest.Matcher<java.lang.String> stringContainsInOrder(java.lang.Iterable<java.lang.String> substrings) {
    return org.hamcrest.text.StringContainsInOrder.stringContainsInOrder(substrings);
  }

  /**
   * Creates a matcher of {@link String} that matches when the examined string contains all of
   * the specified substrings, considering the order of their appearance.
   * For example:
   * <pre>assertThat("myfoobarbaz", stringContainsInOrder("bar", "foo"))</pre>
   * fails as "foo" occurs before "bar" in the string "myfoobarbaz"
   * 
   * @param substrings
   *     the substrings that must be contained within matching strings
   */
  public static org.hamcrest.Matcher<java.lang.String> stringContainsInOrder(java.lang.String... substrings) {
    return org.hamcrest.text.StringContainsInOrder.stringContainsInOrder(substrings);
  }

  /**
   * Creates a matcher that matches any examined object whose <code>toString</code> method
   * returns a value that satisfies the specified matcher.
   * For example:
   * <pre>assertThat(true, hasToString(equalTo("TRUE")))</pre>
   * 
   * @param toStringMatcher
   *     the matcher used to verify the toString result
   */
  public static <T> org.hamcrest.Matcher<T> hasToString(org.hamcrest.Matcher<? super java.lang.String> toStringMatcher) {
    return org.hamcrest.object.HasToString.<T>hasToString(toStringMatcher);
  }

  /**
   * Creates a matcher that matches any examined object whose <code>toString</code> method
   * returns a value equalTo the specified string.
   * For example:
   * <pre>assertThat(true, hasToString("TRUE"))</pre>
   * 
   * @param expectedToString
   *     the expected toString result
   */
  public static <T> org.hamcrest.Matcher<T> hasToString(java.lang.String expectedToString) {
    return org.hamcrest.object.HasToString.<T>hasToString(expectedToString);
  }

  /**
   * Creates a matcher of {@link Class} that matches when the specified baseType is
   * assignable from the examined class.
   * For example:
   * <pre>assertThat(Integer.class, typeCompatibleWith(Number.class))</pre>
   * 
   * @param baseType
   *     the base class to examine classes against
   */
  public static <T> org.hamcrest.Matcher<java.lang.Class<?>> typeCompatibleWith(java.lang.Class<T> baseType) {
    return org.hamcrest.object.IsCompatibleType.<T>typeCompatibleWith(baseType);
  }

  /**
   * Creates a matcher of {@link java.util.EventObject} that matches any object
   * derived from <var>eventClass</var> announced by <var>source</var>.
   * For example:
   * <pre>assertThat(myEvent, is(eventFrom(PropertyChangeEvent.class, myBean)))</pre>
   * 
   * @param eventClass
   *     the class of the event to match on
   * @param source
   *     the source of the event
   */
  public static org.hamcrest.Matcher<java.util.EventObject> eventFrom(java.lang.Class<? extends java.util.EventObject> eventClass, java.lang.Object source) {
    return org.hamcrest.object.IsEventFrom.eventFrom(eventClass, source);
  }

  /**
   * Creates a matcher of {@link java.util.EventObject} that matches any EventObject
   * announced by <var>source</var>.
   * For example:
   * <pre>assertThat(myEvent, is(eventFrom(myBean)))</pre>
   * 
   * @param source
   *     the source of the event
   */
  public static org.hamcrest.Matcher<java.util.EventObject> eventFrom(java.lang.Object source) {
    return org.hamcrest.object.IsEventFrom.eventFrom(source);
  }

  /**
   * Creates a matcher that matches when the examined object has a JavaBean property
   * with the specified name.
   * For example:
   * <pre>assertThat(myBean, hasProperty("foo"))</pre>
   * 
   * @param propertyName
   *     the name of the JavaBean property that examined beans should possess
   */
  public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String propertyName) {
    return org.hamcrest.beans.HasProperty.<T>hasProperty(propertyName);
  }

  /**
   * Creates a matcher that matches when the examined object has a JavaBean property
   * with the specified name whose value satisfies the specified matcher.
   * For example:
   * <pre>assertThat(myBean, hasProperty("foo", equalTo("bar"))</pre>
   * 
   * @param propertyName
   *     the name of the JavaBean property that examined beans should possess
   * @param valueMatcher
   *     a matcher for the value of the specified property of the examined bean
   */
  public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String propertyName, org.hamcrest.Matcher<?> valueMatcher) {
    return org.hamcrest.beans.HasPropertyWithValue.<T>hasProperty(propertyName, valueMatcher);
  }

  /**
   * Creates a matcher that matches when the examined object has values for all of
   * its JavaBean properties that are equal to the corresponding values of the
   * specified bean.
   * For example:
   * <pre>assertThat(myBean, samePropertyValuesAs(myExpectedBean))</pre>
   * 
   * @param expectedBean
   *     the bean against which examined beans are compared
   */
  public static <T> org.hamcrest.Matcher<T> samePropertyValuesAs(T expectedBean) {
    return org.hamcrest.beans.SamePropertyValuesAs.<T>samePropertyValuesAs(expectedBean);
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
  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String xPath, org.hamcrest.Matcher<java.lang.String> valueMatcher) {
    return org.hamcrest.xml.HasXPath.hasXPath(xPath, valueMatcher);
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
  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String xPath, javax.xml.namespace.NamespaceContext namespaceContext, org.hamcrest.Matcher<java.lang.String> valueMatcher) {
    return org.hamcrest.xml.HasXPath.hasXPath(xPath, namespaceContext, valueMatcher);
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
  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String xPath) {
    return org.hamcrest.xml.HasXPath.hasXPath(xPath);
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
  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String xPath, javax.xml.namespace.NamespaceContext namespaceContext) {
    return org.hamcrest.xml.HasXPath.hasXPath(xPath, namespaceContext);
  }

}
