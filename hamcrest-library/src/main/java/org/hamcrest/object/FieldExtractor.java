package org.hamcrest.object;

/**
 * An interface used to extract a field from an object for the {@linkplain HasField} Matcher.
 */
// @FunctionalInterface
public interface FieldExtractor<T, U> {
    U apply(T t);
}
