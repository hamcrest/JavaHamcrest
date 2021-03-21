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
package org.hamcrest;

/**
 * A description of a Matcher. A Matcher will describe itself to a description
 * which can later be used for reporting.
 *
 * @see Matcher#describeTo(Description)
 */
public interface Description {
  /**
   * A description that consumes input but does nothing.
   */
  static final Description NONE = new NullDescription();
  
    /**
     * Appends some plain text to the description.
     */
    Description appendText(String text);

    /**
     * Appends the description of a {@link SelfDescribing} value to this description.
     */
    Description appendDescriptionOf(SelfDescribing value);

    /**
     * Appends an arbitrary value to the description.
     */
    Description appendValue(Object value);

    /**
     * Appends a list of values to the description.
     */
    <T> Description appendValueList(String start, String separator, String end,
                                    T... values);

    /**
     * Appends a list of values to the description.
     */
    <T> Description appendValueList(String start, String separator, String end,
                                    Iterable<T> values);

    /**
     * Appends a list of {@link org.hamcrest.SelfDescribing} objects
     * to the description.
     */
    Description appendList(String start, String separator, String end,
                           Iterable<? extends SelfDescribing> values);


    public static final class NullDescription implements Description {
      @Override
      public Description appendDescriptionOf(SelfDescribing value) {
        return this;
      }

      @Override
      public Description appendList(String start, String separator,
          String end, Iterable<? extends SelfDescribing> values) {
        return this;
      }

      @Override
      public Description appendText(String text) {
        return this;
      }

      @Override
      public Description appendValue(Object value) {
        return this;
      }

      @Override
      public <T> Description appendValueList(String start, String separator,
          String end, T... values) {
        return this;
      }

      @Override
      public <T> Description appendValueList(String start, String separator,
          String end, Iterable<T> values) {
        return this;
      }

      @Override
        public String toString() {
          return "";
        }
    }
}
