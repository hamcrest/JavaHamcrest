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
     * Appends an arbitary value to the description.
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
