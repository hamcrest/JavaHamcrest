/**
 * BSD License
 *
 * Copyright (c) 2000-2015 www.hamcrest.org
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
package org.hamcrest;

import org.hamcrest.core.IsIterableContaining;

/**
 * A collection of static factory methods for core matchers.
 */
public class CoreMatchers {

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matchers type
     * @param matchers the matchers to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> allOf(Iterable<org.hamcrest.Matcher<? super T>> matchers) {
        return org.hamcrest.core.AllOf.allOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matchers type
     * @param matchers the matchers to match
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<? super T>... matchers) {
        return org.hamcrest.core.AllOf.allOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param matchers the matchers to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(Iterable<org.hamcrest.Matcher<? super T>> matchers) {
        return org.hamcrest.core.AnyOf.anyOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param matchers the matchers to match
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(org.hamcrest.Matcher<? super T>... matchers) {
        return org.hamcrest.core.AnyOf.anyOf(matchers);
    }

    /**
     * Creates a matcher that matches when both of the specified matchers match the examined object.
     * For example:
     * <pre>assertThat("fab", both(containsString("a")).and(containsString("b")))</pre>
     * @param <T> the matcher type
     * @param matcher the matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.CombinableMatcher.CombinableBothMatcher<T> both(org.hamcrest.Matcher<? super T> matcher) {
        return org.hamcrest.core.CombinableMatcher.both(matcher);
    }

    /**
     * Creates a matcher that matches when either of the specified matchers match the examined object.
     * For example:
     * <pre>assertThat("fan", either(containsString("a")).or(containsString("b")))</pre>
     * @param <T> the matcher type
     * @param matcher the matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.CombinableMatcher.CombinableEitherMatcher<T> either(org.hamcrest.Matcher<? super T> matcher) {
        return org.hamcrest.core.CombinableMatcher.either(matcher);
    }

    /**
     * Wraps an existing matcher, overriding its description with that specified.  All other functions are
     * delegated to the decorated matcher, including its mismatch description.
     * For example:
     * <pre>describedAs("a big decimal equal to %0", equalTo(myBigDecimal), myBigDecimal.toPlainString())</pre>
     *
     * @param <T> the matcher type
     * @param description
     *     the new description for the wrapped matcher
     * @param matcher
     *     the matcher to wrap
     * @param values
     *     optional values to insert into the tokenised description
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> describedAs(String description, org.hamcrest.Matcher<T> matcher, Object... values) {
        return org.hamcrest.core.DescribedAs.describedAs(description, matcher, values);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields items that are all matched by the specified
     * <code>itemMatcher</code>.
     * For example:
     * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatcher
     *     the matcher to apply to every item provided by the examined {@link Iterable}
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<Iterable<? extends T>> everyItem(org.hamcrest.Matcher<T> itemMatcher) {
        return org.hamcrest.core.Every.everyItem(itemMatcher);
    }

    /**
     * Decorates another Matcher, retaining its behaviour, but allowing tests
     * to be slightly more expressive.
     * For example:
     * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
     * instead of:
     * <pre>assertThat(cheese, equalTo(smelly))</pre>
     * @param <T> the matcher type
     * @param matcher the matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> is(org.hamcrest.Matcher<T> matcher) {
        return org.hamcrest.core.Is.is(matcher);
    }

    /**
     * A shortcut to the frequently used <code>is(equalTo(x))</code>.
     * For example:
     * <pre>assertThat(cheese, is(smelly))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
     * @param <T> the matcher type
     * @param value the value to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> is(T value) {
        return org.hamcrest.core.Is.is(value);
    }

    /**
     * A shortcut to the frequently used <code>is(instanceOf(SomeClass.class))</code>.
     * For example:
     * <pre>assertThat(cheese, isA(Cheddar.class))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(instanceOf(Cheddar.class)))</pre>
     * @param <T> the matcher type
     * @param type the type to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> isA(Class<T> type) {
        return org.hamcrest.core.Is.isA(type);
    }

    /**
     * Creates a matcher that always matches, regardless of the examined object.
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<Object> anything() {
        return org.hamcrest.core.IsAnything.anything();
    }

    /**
     * Creates a matcher that always matches, regardless of the examined object, but describes
     * itself with the specified {@link String}.
     *
     * @param description
     *     a meaningful {@link String} used when describing itself
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<Object> anything(String description) {
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
     * @param <T> the matcher type
     * @param itemMatcher
     *     the matcher to apply to items provided by the examined {@link Iterable}
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<Iterable<? super T>> hasItem(org.hamcrest.Matcher<? super T> itemMatcher) {
        return IsIterableContaining.hasItem(itemMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is equal to the specified
     * <code>item</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem("bar"))</pre>
     *
     * @param <T> the matcher type
     * @param item
     *     the item to compare against the items provided by the examined {@link Iterable}
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<Iterable<? super T>> hasItem(T item) {
        return IsIterableContaining.hasItem(item);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is matched by the corresponding
     * matcher from the specified <code>itemMatchers</code>.  Whilst matching, each traversal of
     * the examined {@link Iterable} will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatchers
     *     the matchers to apply to items provided by the examined {@link Iterable}
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> org.hamcrest.Matcher<Iterable<T>> hasItems(org.hamcrest.Matcher<? super T>... itemMatchers) {
        return IsIterableContaining.hasItems(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is equal to the corresponding
     * item from the specified <code>items</code>.  Whilst matching, each traversal of the
     * examined {@link Iterable} will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"))</pre>
     *
     * @param <T> the matcher type
     * @param items
     *     the items to compare against the items provided by the examined {@link Iterable}
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> org.hamcrest.Matcher<Iterable<T>> hasItems(T... items) {
        return IsIterableContaining.hasItems(items);
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
     * @param <T> the matcher type
     * @param operand the operand to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> equalTo(T operand) {
        return org.hamcrest.core.IsEqual.equalTo(operand);
    }

    /**
     * Creates an {@link org.hamcrest.core.IsEqual} matcher that does not enforce the values being
     * compared to be of the same static type.
     * @param operand the operand to match
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<Object> equalToObject(Object operand) {
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
     * @param <T> the matcher type
     * @param type the type to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> any(Class<T> type) {
        return org.hamcrest.core.IsInstanceOf.any(type);
    }

    /**
     * Creates a matcher that matches when the examined object is an instance of the specified <code>type</code>,
     * as determined by calling the {@link java.lang.Class#isInstance(Object)} method on that type, passing the
     * the examined object.
     *
     * <p>The created matcher assumes no relationship between specified type and the examined object.</p>
     * For example:
     * <pre>assertThat(new Canoe(), instanceOf(Paddlable.class));</pre>
     * @param <T> the matcher type
     * @param type the type to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> instanceOf(Class<?> type) {
        return org.hamcrest.core.IsInstanceOf.instanceOf(type);
    }

    /**
     * Creates a matcher that wraps an existing matcher, but inverts the logic by which
     * it will match.
     * For example:
     * <pre>assertThat(cheese, is(not(equalTo(smelly))))</pre>
     *
     * @param <T> the matcher type
     * @param matcher the matcher to negate
     *     the matcher whose sense should be inverted
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> not(org.hamcrest.Matcher<T> matcher) {
        return org.hamcrest.core.IsNot.not(matcher);
    }

    /**
     * A shortcut to the frequently used <code>not(equalTo(x))</code>.
     * For example:
     * <pre>assertThat(cheese, is(not(smelly)))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(not(equalTo(smelly))))</pre>
     *
     * @param <T> the matcher type
     * @param value
     *     the value that any examined object should <b>not</b> equal
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> not(T value) {
        return org.hamcrest.core.IsNot.not(value);
    }

    /**
     * A shortcut to the frequently used <code>not(nullValue())</code>.
     * For example:
     * <pre>assertThat(cheese, is(notNullValue()))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(not(nullValue())))</pre>
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<Object> notNullValue() {
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
     * @param <T> the matcher type
     * @param type
     *     dummy parameter used to infer the generic type of the returned matcher
     * @return the created matcher
    */
    public static <T> org.hamcrest.Matcher<T> notNullValue(Class<T> type) {
        return org.hamcrest.core.IsNull.notNullValue(type);
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>.
     * For example:
     * <pre>assertThat(cheese, is(nullValue())</pre>
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<Object> nullValue() {
        return org.hamcrest.core.IsNull.nullValue();
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>. Accepts a
     * single dummy argument to facilitate type inference.
     * For example:
     * <pre>assertThat(cheese, is(nullValue(Cheese.class))</pre>
     *
     * @param <T> the matcher type
     * @param type
     *     dummy parameter used to infer the generic type of the returned matcher
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> nullValue(Class<T> type) {
        return org.hamcrest.core.IsNull.nullValue(type);
    }

    /**
     * Creates a matcher that matches only when the examined object is the same instance as
     * the specified target object.
     *
     * @param <T> the matcher type
     * @param target the target to match
     *     the target instance against which others should be assessed
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> sameInstance(T target) {
        return org.hamcrest.core.IsSame.sameInstance(target);
    }

    /**
     * Creates a matcher that matches only when the examined object is the same instance as
     * the specified target object.
     *
     * @param <T> the matcher type
     * @param target the target to match
     *     the target instance against which others should be assessed
     * @return the created matcher
     */
    public static <T> org.hamcrest.Matcher<T> theInstance(T target) {
        return org.hamcrest.core.IsSame.theInstance(target);
    }

    /**
     * Creates a matcher that matches if the examined {@link String} contains the specified
     * {@link String} anywhere.
     * For example:
     * <pre>assertThat("myStringOfNote", containsString("ring"))</pre>
     *
     * @param substring
     *     the substring that the returned matcher will expect to find within any examined string
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<String> containsString(String substring) {
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
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<String> containsStringIgnoringCase(String substring) {
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
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<String> startsWith(String prefix) {
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
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<String> startsWithIgnoringCase(String prefix) {
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
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<String> endsWith(String suffix) {
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
     * @return the created matcher
     */
    public static org.hamcrest.Matcher<String> endsWithIgnoringCase(String suffix) {
        return org.hamcrest.core.StringEndsWith.endsWithIgnoringCase(suffix);
    }

}
