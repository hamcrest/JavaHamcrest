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
package org.hamcrest.collection;

import org.hamcrest.Matcher;
import org.hamcrest.internal.NullSafety;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Steve Freeman 2016 http://www.hamcrest.com
 * Collected helper code for converting matchers between lists and iterables.
 */
public class ArrayMatching {


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
  public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> elementMatcher) {
      return new HasItemInArray<>(elementMatcher);
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
  public static <T> Matcher<T[]> hasItemInArray(T element) {
    return hasItemInArray(equalTo(element));
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
  @SafeVarargs
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... itemMatchers) {
      return arrayContainingInAnyOrder(asList(itemMatchers));
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
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> itemMatchers) {
    return new ArrayAsIterableMatcher<>(new IsIterableContainingInAnyOrder<>(itemMatchers), itemMatchers, "in any order");
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
  @SafeVarargs
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... items) {
    return arrayContainingInAnyOrder(asEqualMatchers(items));
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
  @SafeVarargs
  public static <E> Matcher<E[]> arrayContaining(E... items) {
    return arrayContaining(asEqualMatchers(items));
  }
  /**
   * Creates a matcher for arrays that matches when each item in the examined array satisfies the
   * corresponding matcher in the specified matchers.  For a positive match, the examined array
   * must be of the same length as the number of specified matchers.
   * For example:
   * <pre>assertThat(new String[]{"foo", "bar"}, arrayContaining(equalTo("foo"), equalTo("bar")))</pre>
   *
   * @param itemMatchers
   *     the matchers that must be satisfied by the items in the examined array
   */
  @SafeVarargs
  public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... itemMatchers) {
      //required for JDK 1.6
      //noinspection RedundantTypeArguments
      final List<Matcher<? super E>> nullSafeWithExplicitTypeMatchers = NullSafety.<E>nullSafe(itemMatchers);

      return arrayContaining(nullSafeWithExplicitTypeMatchers);
  }

  /**
   * Creates a matcher for arrays that matches when each item in the examined array satisfies the
   * corresponding matcher in the specified list of matchers.  For a positive match, the examined array
   * must be of the same length as the specified list of matchers.
   * For example:
   * <pre>assertThat(new String[]{"foo", "bar"}, arrayContaining(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
   *
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by the corresponding item in an examined array
   */
  public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> itemMatchers) {
      return new ArrayAsIterableMatcher<>(new IsIterableContainingInOrder<>(itemMatchers), itemMatchers, "");
  }

  public static <E> List<Matcher<? super E>> asEqualMatchers(E[] items) {
    final List<Matcher<? super E>> matchers = new ArrayList<>();
    for (E item : items) {
      matchers.add(equalTo(item));
    }
    return matchers;
  }
}

