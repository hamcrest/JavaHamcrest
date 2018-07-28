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

import java.util.Collection;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.hamcrest.collection.ArrayMatching;
import org.hamcrest.core.IsIterableContaining;
import org.hamcrest.core.StringRegularExpression;
import org.hamcrest.text.IsEqualCompressingWhiteSpace;

/**
 * Factory methods for matchers.
 */
public class Matchers {

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param matchers the matchers to match
     * @return the created matcher
     */
    public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> matchers) {
        return org.hamcrest.core.AllOf.allOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param matchers the matchers to match
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<T> allOf(Matcher<? super T>... matchers) {
        return org.hamcrest.core.AllOf.allOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @return the created matcher
     */
    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second) {
        return org.hamcrest.core.AllOf.allOf(first, second);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @param third the third matcher to match
     * @return the created matcher
     */
    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third) {
        return org.hamcrest.core.AllOf.allOf(first, second, third);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @param third the third matcher to match
     * @param fourth the forth matcher to match
     * @return the created matcher
     */
    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth) {
        return org.hamcrest.core.AllOf.allOf(first, second, third, fourth);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @param third the third matcher to match
     * @param fourth the forth matcher to match
     * @param fifth the fifth matcher to match
     * @return the created matcher
     */
    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth) {
        return org.hamcrest.core.AllOf.allOf(first, second, third, fourth, fifth);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @param third the third matcher to match
     * @param fourth the forth matcher to match
     * @param fifth the fifth matcher to match
     * @param sixth the sixth matcher to match
     * @return the created matcher
     */
    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth, Matcher<? super T> sixth) {
        return org.hamcrest.core.AllOf.allOf(first, second, third, fourth, fifth, sixth);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param matchers the matchers to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(Iterable<Matcher<? super T>> matchers) {
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
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(Matcher<? super T>... matchers) {
        return org.hamcrest.core.AnyOf.anyOf(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(Matcher<? super T> first, Matcher<? super T> second) {
        return org.hamcrest.core.AnyOf.anyOf(first, second);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @param third the third matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third) {
        return org.hamcrest.core.AnyOf.anyOf(first, second, third);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @param third the third matcher to match
     * @param fourth the forth matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth) {
        return org.hamcrest.core.AnyOf.anyOf(first, second, third, fourth);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @param third the third matcher to match
     * @param fourth the forth matcher to match
     * @param fifth the fifth matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth) {
        return org.hamcrest.core.AnyOf.anyOf(first, second, third, fourth, fifth);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ANY</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
     * @param <T> the matcher type
     * @param first the first matcher to match
     * @param second the second matcher to match
     * @param third the third matcher to match
     * @param fourth the forth matcher to match
     * @param fifth the fifth matcher to match
     * @param sixth the sixth matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth, Matcher<? super T> sixth) {
        return org.hamcrest.core.AnyOf.anyOf(first, second, third, fourth, fifth, sixth);
    }

   /**
     * Creates a matcher that matches when both of the specified matchers match the examined object.
     * For example:
     * <pre>assertThat("fab", both(containsString("a")).and(containsString("b")))</pre>
     * @param <T> the matcher type
     * @param matcher the matcher to match
     * @return the created matcher
     */
    public static <T> org.hamcrest.core.CombinableMatcher.CombinableBothMatcher<T> both(Matcher<? super T> matcher) {
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
    public static <T> org.hamcrest.core.CombinableMatcher.CombinableEitherMatcher<T> either(Matcher<? super T> matcher) {
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
     *     optional values to insert into the tokenized description
     * @return the created matcher
     */
    public static <T> Matcher<T> describedAs(String description, Matcher<T> matcher, Object... values) {
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
    public static <T> Matcher<Iterable<? extends T>> everyItem(Matcher<T> itemMatcher) {
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
     * @param matcher the matcher to wrap
     * @return the created matcher
     */
    public static <T> Matcher<T> is(Matcher<T> matcher) {
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
    public static <T> Matcher<T> is(T value) {
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
    public static <T> Matcher<T> isA(Class<?> type) {
        return org.hamcrest.core.Is.isA(type);
    }

    /**
     * Creates a matcher that always matches, regardless of the examined object.
     * @return the created matcher
     */
    public static Matcher<Object> anything() {
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
    public static Matcher<Object> anything(String description) {
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
    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> itemMatcher) {
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
    public static <T> Matcher<Iterable<? super T>> hasItem(T item) {
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
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... itemMatchers) {
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
    public static <T> Matcher<Iterable<T>> hasItems(T... items) {
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
    public static <T> Matcher<T> equalTo(T operand) {
        return org.hamcrest.core.IsEqual.equalTo(operand);
    }

    /**
     * Creates an {@link org.hamcrest.core.IsEqual} matcher that does not enforce the values being
     * compared to be of the same static type.
     * @param operand the operand to match
     * @return the created matcher
     */
    public static Matcher<Object> equalToObject(Object operand) {
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
    public static <T> Matcher<T> any(Class<T> type) {
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
    public static <T> Matcher<T> instanceOf(Class<?> type) {
        return org.hamcrest.core.IsInstanceOf.instanceOf(type);
    }

    /**
     * Creates a matcher that wraps an existing matcher, but inverts the logic by which
     * it will match.
     * For example:
     * <pre>assertThat(cheese, is(not(equalTo(smelly))))</pre>
     *
     * @param <T> the matcher type
     * @param matcher
     *     the matcher whose sense should be inverted
     * @return the created matcher
     */
    public static <T> Matcher<T> not(Matcher<T> matcher) {
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
    public static <T> Matcher<T> not(T value) {
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
    public static Matcher<Object> notNullValue() {
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
    public static <T> Matcher<T> notNullValue(Class<T> type) {
        return org.hamcrest.core.IsNull.notNullValue(type);
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>.
     * For example:
     * <pre>assertThat(cheese, is(nullValue())</pre>
     * @return the created matcher
     */
    public static Matcher<Object> nullValue() {
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
    public static <T> Matcher<T> nullValue(Class<T> type) {
        return org.hamcrest.core.IsNull.nullValue(type);
    }

    /**
     * Creates a matcher that matches only when the examined object is the same instance as
     * the specified target object.
     *
     * @param <T> the matcher type
     * @param target
     *     the target instance against which others should be assessed
     * @return the created matcher
     */
    public static <T> Matcher<T> sameInstance(T target) {
        return org.hamcrest.core.IsSame.sameInstance(target);
    }

    /**
     * Creates a matcher that matches only when the examined object is the same instance as
     * the specified target object.
     *
     * @param <T> the matcher type
     * @param target
     *     the target instance against which others should be assessed
     * @return the created matcher
     */
    public static <T> Matcher<T> theInstance(T target) {
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
    public static Matcher<String> containsString(String substring) {
        return org.hamcrest.core.StringContains.containsString(substring);
    }

    /**
     * Creates a matcher that matches if the examined {@link String} contains the specified
     * {@link String} anywhere, ignoring case.
     * For example:
     * <pre>assertThat("myStringOfNote", containsStringIgnoringCase("Ring"))</pre>
     *
     * @param substring
     *     the substring that the returned matcher will expect to find within any examined string
     * @return the created matcher
     */
    public static Matcher<String> containsStringIgnoringCase(String substring) {
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
    public static Matcher<String> startsWith(String prefix) {
        return org.hamcrest.core.StringStartsWith.startsWith(prefix);
    }

    /**
     * <p>
     * Creates a matcher that matches if the examined {@link String} starts with the specified
     * {@link String}, ignoring case
     * </p>
     * For example:
     * <pre>assertThat("myStringOfNote", startsWithIgnoringCase("My"))</pre>
     *
     * @param prefix
     *      the substring that the returned matcher will expect at the start of any examined string
     * @return the created matcher
     */
    public static Matcher<String> startsWithIgnoringCase(String prefix) {
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
    public static Matcher<String> endsWith(String suffix) {
        return org.hamcrest.core.StringEndsWith.endsWith(suffix);
    }

    /**
     * Creates a matcher that matches if the examined {@link String} ends with the specified
     * {@link String}, ignoring case.
     * For example:
     * <pre>assertThat("myStringOfNote", endsWithIgnoringCase("note"))</pre>
     *
     * @param suffix
     *      the substring that the returned matcher will expect at the end of any examined string
     * @return the created matcher
     */
    public static Matcher<String> endsWithIgnoringCase(String suffix) {
        return org.hamcrest.core.StringEndsWith.endsWithIgnoringCase(suffix);
    }

    /**
     * Validate a string with a {@link java.util.regex.Pattern}.
     *
     * <pre>
     * assertThat(&quot;abc&quot;, matchesRegex(Pattern.compile(&quot;&circ;[a-z]$&quot;));
     * </pre>
     *
     * @param pattern
     *            the pattern to be used.
     * @return the created matcher
     */
    public static Matcher<String> matchesRegex(Pattern pattern) {
        return StringRegularExpression.matchesRegex(pattern);
    }

    /**
     * Validate a string with a regex.
     *
     * <pre>
     * assertThat(&quot;abc&quot;, matchesRegex(&quot;&circ;[a-z]+$&quot;));
     * </pre>
     *
     * @param regex
     *            The regex to be used for the validation.
     * @return the created matcher
     */
    public static Matcher<String> matchesRegex(String regex) {
        return StringRegularExpression.matchesRegex(Pattern.compile(regex));
    }

    /**
     * Creates a matcher that matches arrays whose elements are satisfied by the specified matchers.  Matches
     * positively only if the number of matchers specified is equal to the length of the examined array and
     * each matcher[i] is satisfied by array[i].
     * For example:
     * <pre>assertThat(new Integer[]{1,2,3}, is(array(equalTo(1), equalTo(2), equalTo(3))))</pre>
     *
     * @param <T> the matcher type
     * @param elementMatchers
     *     the matchers that the elements of examined arrays should satisfy
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> org.hamcrest.collection.IsArray<T> array(Matcher<? super T>... elementMatchers) {
        return org.hamcrest.collection.IsArray.array(elementMatchers);
    }

    /**
     * Creates a matcher for arrays that matches when the examined array contains at least one item
     * that is matched by the specified <code>elementMatcher</code>.  Whilst matching, the traversal
     * of the examined array will stop as soon as a matching element is found.
     * For example:
     * <pre>assertThat(new String[] {"foo", "bar"}, hasItemInArray(startsWith("ba")))</pre>
     *
     * @param <T> the matcher type
     * @param elementMatcher
     *     the matcher to apply to elements in examined arrays
     * @return the created matcher
     */
    public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> elementMatcher) {
        return ArrayMatching.hasItemInArray(elementMatcher);
    }

    /**
     * A shortcut to the frequently used <code>hasItemInArray(equalTo(x))</code>.
     * For example:
     * <pre>assertThat(hasItemInArray(x))</pre>
     * instead of:
     * <pre>assertThat(hasItemInArray(equalTo(x)))</pre>
     *
     * @param <T> the matcher type
     * @param element
     *     the element that should be present in examined arrays
     * @return the created matcher
     */
    public static <T> Matcher<T[]> hasItemInArray(T element) {
        return ArrayMatching.hasItemInArray(element);
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array is
     * logically equal to the corresponding item in the specified items.  For a positive match,
     * the examined array must be of the same length as the number of specified items.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayContaining("foo", "bar"))</pre>
     *
     * @param <T> the matcher type
     * @param items
     *     the items that must equal the items within an examined array
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<T[]> arrayContaining(T... items) {
        return ArrayMatching.arrayContaining(items);
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array satisfies the
     * corresponding matcher in the specified matchers.  For a positive match, the examined array
     * must be of the same length as the number of specified matchers.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayContaining(equalTo("foo"), equalTo("bar")))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatchers
     *     the matchers that must be satisfied by the items in the examined array
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<T[]> arrayContaining(Matcher<? super T>... itemMatchers) {
        return ArrayMatching.arrayContaining(itemMatchers);
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array satisfies the
     * corresponding matcher in the specified list of matchers.  For a positive match, the examined array
     * must be of the same length as the specified list of matchers.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayContaining(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the corresponding item in an examined array
     * @return the created matcher
     */
    public static <T> Matcher<T[]> arrayContaining(List<Matcher<? super T>> itemMatchers) {
        return ArrayMatching.arrayContaining(itemMatchers);
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
     * @param <T> the matcher type
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an entry in an examined array
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<T[]> arrayContainingInAnyOrder(Matcher<? super T>... itemMatchers) {
        return ArrayMatching.arrayContainingInAnyOrder(itemMatchers);
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
     * @param <T> the matcher type
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined array
     * @return the created matcher
     */
    public static <T> Matcher<T[]> arrayContainingInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return ArrayMatching.arrayContainingInAnyOrder(itemMatchers);
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
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayContainingInAnyOrder("bar", "foo"))</pre>
     *
     * @param <T> the matcher type
     * @param items
     *     the items that must equal the entries of an examined array, in any order
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<T[]> arrayContainingInAnyOrder(T... items) {
        return ArrayMatching.arrayContainingInAnyOrder(items);
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * satisfies the specified matcher.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(equalTo(2)))</pre>
     *
     * @param <T> the matcher type
     * @param sizeMatcher
     *     a matcher for the length of an examined array
     * @return the created matcher
     */
    public static <T> Matcher<T[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return org.hamcrest.collection.IsArrayWithSize.arrayWithSize(sizeMatcher);
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * equals the specified <code>size</code>.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(2))</pre>
     *
     * @param <T> the matcher type
     * @param size
     *     the length that an examined array must have for a positive match
     * @return the created matcher
     */
    public static <T> Matcher<T[]> arrayWithSize(int size) {
        return org.hamcrest.collection.IsArrayWithSize.arrayWithSize(size);
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * is zero.
     * For example:
     * <pre>assertThat(new String[0], emptyArray())</pre>
     * @param <T> the matcher type
     * @return the created matcher
     */
    public static <T> Matcher<T[]> emptyArray() {
        return org.hamcrest.collection.IsArrayWithSize.emptyArray();
    }

    /**
     * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
     * a value that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(myMap, is(aMapWithSize(equalTo(2))))</pre>
     *
     * @param <K> the map key type
     * @param <V> the map value type
     * @param sizeMatcher
     *     a matcher for the size of an examined {@link java.util.Map}
     * @return the created matcher
     */
    public static <K, V> Matcher<Map<? extends K,? extends V>> aMapWithSize(Matcher<? super Integer> sizeMatcher) {
        return org.hamcrest.collection.IsMapWithSize.aMapWithSize(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
     * a value equal to the specified <code>size</code>.
     * For example:
     * <pre>assertThat(myMap, is(aMapWithSize(2)))</pre>
     *
     * @param <K> the map key type
     * @param <V> the map value type
     * @param size
     *     the expected size of an examined {@link java.util.Map}
     * @return the created matcher
     */
    public static <K, V> Matcher<Map<? extends K,? extends V>> aMapWithSize(int size) {
        return org.hamcrest.collection.IsMapWithSize.aMapWithSize(size);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
     * zero.
     * For example:
     * <pre>assertThat(myMap, is(anEmptyMap()))</pre>
     * @param <K> the map key type
     * @param <V> the map value type
     * @return the created matcher
     */
    public static <K, V> Matcher<Map<? extends K,? extends V>> anEmptyMap() {
        return org.hamcrest.collection.IsMapWithSize.anEmptyMap();
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
     * a value that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(equalTo(2)))</pre>
     *
     * @param <T> the matcher type
     * @param sizeMatcher
     *     a matcher for the size of an examined {@link java.util.Collection}
     * @return the created matcher
     */
    public static <T> Matcher<Collection<? extends T>> hasSize(Matcher<? super Integer> sizeMatcher) {
        return org.hamcrest.collection.IsCollectionWithSize.hasSize(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
     * a value equal to the specified <code>size</code>.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(2))</pre>
     *
     * @param <T> the matcher type
     * @param size
     *     the expected size of an examined {@link java.util.Collection}
     * @return the created matcher
     */
    public static <T> Matcher<Collection<? extends T>> hasSize(int size) {
        return org.hamcrest.collection.IsCollectionWithSize.hasSize(size);
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
     * method returns <code>true</code>.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(empty()))</pre>
     * @param <T> the matcher type
     * @return the created matcher
     */
    public static <T> Matcher<Collection<? extends T>> empty() {
        return org.hamcrest.collection.IsEmptyCollection.empty();
    }

    /**
     * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
     * method returns <code>true</code>.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyCollectionOf(String.class)))</pre>
     *
     * @param <T> the matcher type
     * @param unusedToForceReturnType
     *     the type of the collection's content
     * @return the created matcher
     */
    public static <T> Matcher<Collection<T>> emptyCollectionOf(Class<T> unusedToForceReturnType) {
        return org.hamcrest.collection.IsEmptyCollection.emptyCollectionOf(unusedToForceReturnType);
    }

    /**
     * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterable()))</pre>
     * @param <T> the matcher type
     * @return the created matcher
     */
    public static <T> Matcher<Iterable<? extends T>> emptyIterable() {
        return org.hamcrest.collection.IsEmptyIterable.emptyIterable();
    }

    /**
     * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterableOf(String.class)))</pre>
     *
     * @param <T> the matcher type
     * @param unusedToForceReturnType
     *     the type of the iterable's content
     * @return the created matcher
     */
    public static <T> Matcher<Iterable<T>> emptyIterableOf(Class<T> unusedToForceReturnType) {
        return org.hamcrest.collection.IsEmptyIterable.emptyIterableOf(unusedToForceReturnType);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each logically equal to the
     * corresponding item in the specified items.  For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"))</pre>
     *
     * @param <T> the matcher type
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable}
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> contains(T... items) {
        return org.hamcrest.collection.IsIterableContainingInOrder.contains(items);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a single item that satisfies the specified matcher.
     * For a positive match, the examined iterable must only yield one item.
     * For example:
     * <pre>assertThat(Arrays.asList("foo"), contains(equalTo("foo")))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatcher
     *     the matcher that must be satisfied by the single item provided by an
     *     examined {@link Iterable}
     * @return the created matcher
     */
    public static <T> Matcher<Iterable<? extends T>> contains(Matcher<? super T> itemMatcher) {
        return org.hamcrest.collection.IsIterableContainingInOrder.contains(itemMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified matchers.  For a positive match, the examined iterable
     * must be of the same length as the number of specified matchers.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(equalTo("foo"), equalTo("bar")))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatchers
     *     the matchers that must be satisfied by the items provided by an examined {@link Iterable}
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> contains(Matcher<? super T>... itemMatchers) {
        return org.hamcrest.collection.IsIterableContainingInOrder.contains(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified list of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified list of matchers.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the corresponding item provided by
     *     an examined {@link Iterable}
     * @return the created matcher
     */
    public static <T> Matcher<Iterable<? extends T>> contains(List<Matcher<? super T>> itemMatchers) {
        return org.hamcrest.collection.IsIterableContainingInOrder.contains(itemMatchers);
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
     * @param <T> the matcher type
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
        return org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder(itemMatchers);
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
     * @param <T> the matcher type
     * @param items
     *     the items that must equal the items provided by an examined {@link Iterable} in any order
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        return org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder(items);
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
     * @param <T> the matcher type
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     * @return the created matcher
     */
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that contains items logically equal to the
     * corresponding item in the specified items, in the same relative order
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder("b", "d"))</pre>
     *
     * @param <T> the matcher type
     * @param items
     *     the items that must be contained within items provided by an examined {@link Iterable} in the same relative order
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInRelativeOrder(T... items) {
        return org.hamcrest.collection.IsIterableContainingInRelativeOrder.containsInRelativeOrder(items);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that each satisfying the corresponding
     * matcher in the specified matchers, in the same relative order.
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder(equalTo("b"), equalTo("d")))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatchers
     *     the matchers that must be satisfied by the items provided by an examined {@link Iterable} in the same relative order
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInRelativeOrder(Matcher<? super T>... itemMatchers) {
        return org.hamcrest.collection.IsIterableContainingInRelativeOrder.containsInRelativeOrder(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that contains items satisfying the corresponding
     * matcher in the specified list of matchers, in the same relative order.
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), contains(Arrays.asList(equalTo("b"), equalTo("d"))))</pre>
     *
     * @param <T> the matcher type
     * @param itemMatchers
     *     a list of matchers, each of which must be satisfied by the items provided by
     *     an examined {@link Iterable} in the same relative order
     * @return the created matcher
     */
    public static <T> Matcher<Iterable<? extends T>> containsInRelativeOrder(List<Matcher<? super T>> itemMatchers) {
        return org.hamcrest.collection.IsIterableContainingInRelativeOrder.containsInRelativeOrder(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that satisfies the specified
     * matcher.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(equalTo(2)))</pre>
     *
     * @param <T> the matcher type
     * @param sizeMatcher
     *     a matcher for the number of items that should be yielded by an examined {@link Iterable}
     * @return the created matcher
     */
    public static <T> Matcher<Iterable<T>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return org.hamcrest.collection.IsIterableWithSize.iterableWithSize(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that is equal to the specified
     * <code>size</code> argument.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(2))</pre>
     *
     * @param <T> the matcher type
     * @param size
     *     the number of items that should be yielded by an examined {@link Iterable}
     * @return the created matcher
     */
    public static <T> Matcher<Iterable<T>> iterableWithSize(int size) {
        return org.hamcrest.collection.IsIterableWithSize.iterableWithSize(size);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one entry whose key satisfies the specified <code>keyMatcher</code> <b>and</b> whose
     * value satisfies the specified <code>valueMatcher</code>.
     * For example:
     * <pre>assertThat(myMap, hasEntry(equalTo("bar"), equalTo("foo")))</pre>
     *
     * @param <K> the map key type
     * @param <V> the map value type
     * @param keyMatcher
     *     the key matcher that, in combination with the valueMatcher, must be satisfied by at least one entry
     * @param valueMatcher
     *     the value matcher that, in combination with the keyMatcher, must be satisfied by at least one entry
     * @return the created matcher
     */
    public static <K, V> Matcher<Map<? extends K,? extends V>> hasEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return org.hamcrest.collection.IsMapContaining.hasEntry(keyMatcher, valueMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one entry whose key equals the specified <code>key</code> <b>and</b> whose value equals the
     * specified <code>value</code>.
     * For example:
     * <pre>assertThat(myMap, hasEntry("bar", "foo"))</pre>
     *
     * @param <K> the map key type
     * @param <V> the map value type
     * @param key
     *     the key that, in combination with the value, must be describe at least one entry
     * @param value
     *     the value that, in combination with the key, must be describe at least one entry
     * @return the created matcher
     */
    public static <K, V> Matcher<Map<? extends K,? extends V>> hasEntry(K key, V value) {
        return org.hamcrest.collection.IsMapContaining.hasEntry(key, value);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one key that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(myMap, hasKey(equalTo("bar")))</pre>
     *
     * @param <K> the map key type
     * @param keyMatcher
     *     the matcher that must be satisfied by at least one key
     * @return the created matcher
     */
    public static <K> Matcher<Map<? extends K,?>> hasKey(Matcher<? super K> keyMatcher) {
        return org.hamcrest.collection.IsMapContaining.hasKey(keyMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one key that is equal to the specified key.
     * For example:
     * <pre>assertThat(myMap, hasKey("bar"))</pre>
     *
     * @param <K> the map key type
     * @param key
     *     the key that satisfying maps must contain
     * @return the created matcher
     */
    public static <K> Matcher<Map<? extends K,?>> hasKey(K key) {
        return org.hamcrest.collection.IsMapContaining.hasKey(key);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one value that satisfies the specified valueMatcher.
     * For example:
     * <pre>assertThat(myMap, hasValue(equalTo("foo")))</pre>
     *
     * @param <V> the map value type
     * @param valueMatcher
     *     the matcher that must be satisfied by at least one value
     * @return the created matcher
     */
    public static <V> Matcher<Map<?,? extends V>> hasValue(Matcher<? super V> valueMatcher) {
        return org.hamcrest.collection.IsMapContaining.hasValue(valueMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one value that is equal to the specified value.
     * For example:
     * <pre>assertThat(myMap, hasValue("foo"))</pre>
     *
     * @param <V> the map value type
     * @param value
     *     the value that satisfying maps must contain
     * @return the created matcher
     */
    public static <V> Matcher<java.util.Map<?,? extends V>> hasValue(V value) {
        return org.hamcrest.collection.IsMapContaining.hasValue(value);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified collection.
     * For example:
     * <pre>assertThat("foo", is(in(Arrays.asList("bar", "foo"))))</pre>
     *
     * @param <T> the matcher type
     * @param collection
     *     the collection in which matching items must be found
     * @return the created matcher
     */
    public static <T> Matcher<T> in(java.util.Collection<T> collection) {
        return org.hamcrest.collection.IsIn.in(collection);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified array.
     * For example:
     * <pre>assertThat("foo", is(in(new String[]{"bar", "foo"})))</pre>
     *
     * @param <T> the matcher type
     * @param elements
     *     the array in which matching items must be found
     * @return the created matcher
     */
    public static <T> Matcher<T> in(T[] elements) {
        return org.hamcrest.collection.IsIn.in(elements);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified collection.
     * For example:
     * <pre>assertThat("foo", isIn(Arrays.asList("bar", "foo")))</pre>
     *
     * @param <T> the matcher type
     * @deprecated use is(in(...)) instead
     * @param collection
     *     the collection in which matching items must be found
     * @return the created matcher
     */
    @SuppressWarnings("deprecation")
    public static <T> Matcher<T> isIn(java.util.Collection<T> collection) {
        return org.hamcrest.collection.IsIn.isIn(collection);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified array.
     * For example:
     * <pre>assertThat("foo", isIn(new String[]{"bar", "foo"}))</pre>
     *
     * @param <T> the matcher type
     * @deprecated use is(in(...)) instead
     * @param elements
     *     the array in which matching items must be found
     * @return the created matcher
     */
    @SuppressWarnings("deprecation")
    public static <T> Matcher<T> isIn(T[] elements) {
        return org.hamcrest.collection.IsIn.isIn(elements);
    }

    /**
     * Creates a matcher that matches when the examined object is equal to one of the
     * specified elements.
     * For example:
     * <pre>assertThat("foo", isOneOf("bar", "foo"))</pre>
     *
     * @param <T> the matcher type
     * @deprecated use is(oneOf(...)) instead
     * @param elements
     *     the elements amongst which matching items will be found
     * @return the created matcher
     */
    @SuppressWarnings("deprecation")
    @SafeVarargs
    public static <T> Matcher<T> isOneOf(T... elements) {
        return org.hamcrest.collection.IsIn.isOneOf(elements);
    }

    /**
     * Creates a matcher that matches when the examined object is equal to one of the
     * specified elements.
     * For example:
     * <pre>assertThat("foo", is(oneOf("bar", "foo")))</pre>
     *
     * @param <T> the matcher type
     * @param elements
     *     the elements amongst which matching items will be found
     * @return the created matcher
     */
    @SafeVarargs
    public static <T> Matcher<T> oneOf(T... elements) {
        return org.hamcrest.collection.IsIn.oneOf(elements);
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
     * @return the created matcher
     */
    public static Matcher<Double> closeTo(double operand, double error) {
        return org.hamcrest.number.IsCloseTo.closeTo(operand, error);
    }

    /**
     * Creates a matcher of {@link Double}s that matches when an examined double is not a number.
     * For example:
     * <pre>assertThat(Double.NaN, is(notANumber()))</pre>
     * @return the created matcher
     */
    public static Matcher<Double> notANumber() {
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
     * @return the created matcher
     */
    public static Matcher<java.math.BigDecimal> closeTo(java.math.BigDecimal operand, java.math.BigDecimal error) {
        return org.hamcrest.number.BigDecimalCloseTo.closeTo(operand, error);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * equal to the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, comparesEqualTo(1))</pre>
     *
     * @param <T> the matcher type
     * @param value the value which, when passed to the compareTo method of the examined object, should return zero
     * @return the created matcher
     */
    public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T value) {
        return org.hamcrest.number.OrderingComparison.comparesEqualTo(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * greater than the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(2, greaterThan(1))</pre>
     *
     * @param <T> the matcher type
     * @param value the value which, when passed to the compareTo method of the examined object, should return greater
     *              than zero
     * @return the created matcher
     */
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
        return org.hamcrest.number.OrderingComparison.greaterThan(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * greater than or equal to the specified value, as reported by the <code>compareTo</code> method
     * of the <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, greaterThanOrEqualTo(1))</pre>
     *
     * @param <T> the matcher type
     * @param value the value which, when passed to the compareTo method of the examined object, should return greater
     *              than or equal to zero
     * @return the created matcher
     */
    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * less than the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, lessThan(2))</pre>
     *
     * @param <T> the matcher type
     * @param value the value which, when passed to the compareTo method of the examined object, should return less
     *              than zero
     * @return the created matcher
     */
    public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
        return org.hamcrest.number.OrderingComparison.lessThan(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * less than or equal to the specified value, as reported by the <code>compareTo</code> method
     * of the <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, lessThanOrEqualTo(1))</pre>
     *
     * @param <T> the matcher type
     * @param value the value which, when passed to the compareTo method of the examined object, should return less
     *              than or equal to zero
     * @return the created matcher
     */
    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T value) {
        return org.hamcrest.number.OrderingComparison.lessThanOrEqualTo(value);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is equal to
     * the specified expectedString, ignoring case.
     * For example:
     * <pre>assertThat("Foo", equalToIgnoringCase("FOO"))</pre>
     *
     * @param expectedString
     *     the expected value of matched strings
     * @return the created matcher
     */
    public static Matcher<String> equalToIgnoringCase(String expectedString) {
        return org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase(expectedString);
    }

    /**
     * @param expectedString
     *     the expected value of matched strings
     * @return the created matcher
     * @deprecated {@link #equalToCompressingWhiteSpace(String)}
     */
    public static Matcher<String> equalToIgnoringWhiteSpace(String expectedString) {
        return equalToCompressingWhiteSpace(expectedString);
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
     * @return the created matcher
     */
    public static Matcher<String> equalToCompressingWhiteSpace(String expectedString) {
        return IsEqualCompressingWhiteSpace.equalToCompressingWhiteSpace(expectedString);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * For example:
     * <pre>assertThat(((String)null), is(emptyOrNullString()))</pre>
     * @return the created matcher
     */
    public static Matcher<String> emptyOrNullString() {
        return org.hamcrest.text.IsEmptyString.emptyOrNullString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * For example:
     * <pre>assertThat("", is(emptyString()))</pre>
     * @return the created matcher
     */
    public static Matcher<String> emptyString() {
        return org.hamcrest.text.IsEmptyString.emptyString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * For example:
     * <pre>assertThat(((String)null), isEmptyOrNullString())</pre>
     *
     * @deprecated use is(emptyOrNullString()) instead
     * @return the created matcher
     */
    @SuppressWarnings("deprecation")
    public static Matcher<String> isEmptyOrNullString() {
        return org.hamcrest.text.IsEmptyString.isEmptyOrNullString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * For example:
     * <pre>assertThat("", isEmptyString())</pre>
     *
     * @deprecated use is(emptyString()) instead
     * @return the created matcher
     */
    @SuppressWarnings("deprecation")
    public static Matcher<String> isEmptyString() {
        return org.hamcrest.text.IsEmptyString.isEmptyString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * contains zero or more whitespace characters and nothing else.
     * For example:
     * <pre>assertThat(((String)null), is(blankOrNullString()))</pre>
     * @return the created matcher
     */
    public static Matcher<String> blankOrNullString() {
        return org.hamcrest.text.IsBlankString.blankOrNullString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains
     * zero or more whitespace characters and nothing else.
     * For example:
     * <pre>assertThat("  ", is(blankString()))</pre>
     * @return the created matcher
     */
    public static Matcher<String> blankString() {
        return org.hamcrest.text.IsBlankString.blankString();
    }

    /**
     * Creates a matcher of {@link java.lang.String} that matches when the examined string
     * exactly matches the given {@link java.util.regex.Pattern}.
     * @param pattern the pattern to match
     * @return the created matcher
     */
    public static Matcher<String> matchesPattern(java.util.regex.Pattern pattern) {
        return org.hamcrest.text.MatchesPattern.matchesPattern(pattern);
    }

    /**
     * Creates a matcher of {@link java.lang.String} that matches when the examined string
     * exactly matches the given regular expression, treated as a {@link java.util.regex.Pattern}.
     * @param regex the pattern to match
     * @return the created matcher
     */
    public static Matcher<String> matchesPattern(String regex) {
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
     * @return the created matcher
     */
    public static Matcher<String> stringContainsInOrder(Iterable<String> substrings) {
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
     * @return the created matcher
     */
    public static Matcher<String> stringContainsInOrder(String... substrings) {
        return org.hamcrest.text.StringContainsInOrder.stringContainsInOrder(substrings);
    }

    /**
     * Creates a matcher of {@link CharSequence} that matches when a char sequence has the length
     * of the specified <code>argument</code>.
     * For example:
     *
     * <pre>
     * assertThat("text", length(4))
     * </pre>
     *
     * @param length the expected length of the string
     * @return the created matcher
     */
    public static Matcher<CharSequence> hasLength(int length) {
        return org.hamcrest.text.CharSequenceLength.hasLength(length);
    }

    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(true, hasToString(equalTo("TRUE")))</pre>
     *
     * @param <T> the matcher type
     * @param toStringMatcher
     *     the matcher used to verify the toString result
     * @return the created matcher
     */
    public static <T> Matcher<T> hasToString(Matcher<? super String> toStringMatcher) {
        return org.hamcrest.object.HasToString.hasToString(toStringMatcher);
    }

    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value equalTo the specified string.
     * For example:
     * <pre>assertThat(true, hasToString("TRUE"))</pre>
     *
     * @param <T> the matcher type
     * @param expectedToString
     *     the expected toString result
     * @return the created matcher
     */
    public static <T> Matcher<T> hasToString(String expectedToString) {
        return org.hamcrest.object.HasToString.hasToString(expectedToString);
    }

    /**
     * Creates a matcher of {@link Class} that matches when the specified baseType is
     * assignable from the examined class.
     * For example:
     * <pre>assertThat(Integer.class, typeCompatibleWith(Number.class))</pre>
     *
     * @param <T> the matcher type
     * @param baseType
     *     the base class to examine classes against
     * @return the created matcher
     */
    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> baseType) {
        return org.hamcrest.object.IsCompatibleType.typeCompatibleWith(baseType);
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
     * @return the created matcher
     */
    public static Matcher<java.util.EventObject> eventFrom(Class<? extends java.util.EventObject> eventClass, Object source) {
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
     * @return the created matcher
     */
    public static Matcher<EventObject> eventFrom(Object source) {
        return org.hamcrest.object.IsEventFrom.eventFrom(source);
    }

    /**
     * Creates a matcher that matches when the examined object has a JavaBean property
     * with the specified name.
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo"))</pre>
     *
     * @param <T> the matcher type
     * @param propertyName
     *     the name of the JavaBean property that examined beans should possess
     * @return the created matcher
     */
    public static <T> Matcher<T> hasProperty(String propertyName) {
        return org.hamcrest.beans.HasProperty.hasProperty(propertyName);
    }

    /**
     * Creates a matcher that matches when the examined object has a JavaBean property
     * with the specified name whose value satisfies the specified matcher.
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo", equalTo("bar"))</pre>
     *
     * @param <T> the matcher type
     * @param propertyName
     *     the name of the JavaBean property that examined beans should possess
     * @param valueMatcher
     *     a matcher for the value of the specified property of the examined bean
     * @return the created matcher
     */
    public static <T> Matcher<T> hasProperty(String propertyName, Matcher<?> valueMatcher) {
        return org.hamcrest.beans.HasPropertyWithValue.hasProperty(propertyName, valueMatcher);
    }

    /**
     * Creates a matcher that matches when the examined object has values for all of
     * its JavaBean properties that are equal to the corresponding values of the
     * specified bean. If any properties are marked as ignored, they will be dropped from
     * both the expected and actual bean. Note that the ignored properties use JavaBean
     * display names, for example <pre>age</pre> rather than method names such as <pre>getAge</pre>.
     * For example:
     * <pre>assertThat(myBean, samePropertyValuesAs(myExpectedBean))</pre>
     * <pre>assertThat(myBean, samePropertyValuesAs(myExpectedBean), "age", "height")</pre>
     *
     * @param <T> the matcher type
     * @param expectedBean
     *     the bean against which examined beans are compared
     * @param ignoredProperties
     *     do not check any of these named properties.
     * @return the created matcher
     */
    public static <T> Matcher<T> samePropertyValuesAs(T expectedBean, String... ignoredProperties) {
        return org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs(expectedBean);
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
     * @return the created matcher
     */
    public static Matcher<org.w3c.dom.Node> hasXPath(String xPath, Matcher<String> valueMatcher) {
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
     * @return the created matcher
     */
    public static Matcher<org.w3c.dom.Node> hasXPath(String xPath, javax.xml.namespace.NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
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
     * @return the created matcher
     */
    public static Matcher<org.w3c.dom.Node> hasXPath(String xPath) {
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
     * @return the created matcher
     */
    public static Matcher<org.w3c.dom.Node> hasXPath(String xPath, javax.xml.namespace.NamespaceContext namespaceContext) {
        return org.hamcrest.xml.HasXPath.hasXPath(xPath, namespaceContext);
    }
}
