// Generated source.
package org.hamcrest;

public class Matchers {

  public static <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<T>... param1) {
    return org.hamcrest.core.AllOf.allOf(param1);
  }

  public static <T> org.hamcrest.Matcher<T> allOf(java.lang.Iterable<org.hamcrest.Matcher<T>> param1) {
    return org.hamcrest.core.AllOf.allOf(param1);
  }

  public static <T> org.hamcrest.Matcher<T> anyOf(org.hamcrest.Matcher<T>... param1) {
    return org.hamcrest.core.AnyOf.anyOf(param1);
  }

  public static <T> org.hamcrest.Matcher<T> anyOf(java.lang.Iterable<org.hamcrest.Matcher<T>> param1) {
    return org.hamcrest.core.AnyOf.anyOf(param1);
  }

  public static <T> org.hamcrest.Matcher<T> not(org.hamcrest.Matcher<T> param1) {
    return org.hamcrest.core.IsNot.not(param1);
  }

  public static <T> org.hamcrest.Matcher<T> equalTo(T param1) {
    return org.hamcrest.core.IsEqual.equalTo(param1);
  }

  public static org.hamcrest.Matcher<java.lang.Boolean> isFalse() {
    return org.hamcrest.core.IsEqual.isFalse();
  }

  public static org.hamcrest.Matcher<java.lang.Boolean> isTrue() {
    return org.hamcrest.core.IsEqual.isTrue();
  }

  public static <T> org.hamcrest.Matcher<T> same(T param1) {
    return org.hamcrest.core.IsSame.same(param1);
  }

  public static <T> org.hamcrest.Matcher<T> anything() {
    return org.hamcrest.core.IsAnything.anything();
  }

  public static <T> org.hamcrest.Matcher<T> anything(java.lang.String param1) {
    return org.hamcrest.core.IsAnything.anything(param1);
  }

  public static <T> org.hamcrest.Matcher<T> isNotNull() {
    return org.hamcrest.core.IsNull.isNotNull();
  }

  public static <T> org.hamcrest.Matcher<T> isNull() {
    return org.hamcrest.core.IsNull.isNull();
  }

  public static <T> org.hamcrest.Matcher<T> isInCollection(java.util.Collection<T> param1) {
    return org.hamcrest.core.IsIn.isInCollection(param1);
  }

  public static <T> org.hamcrest.Matcher<T> isIn(T... param1) {
    return org.hamcrest.core.IsIn.isIn(param1);
  }

  public static <T> org.hamcrest.Matcher<T> isA(java.lang.Class<T> param1) {
    return org.hamcrest.core.IsInstanceOf.isA(param1);
  }

  public static <T> org.hamcrest.Matcher<T> alwaysPasses(java.lang.String param1) {
    return org.hamcrest.core.Always.alwaysPasses(param1);
  }

  public static <T> org.hamcrest.Matcher<T> alwaysPasses() {
    return org.hamcrest.core.Always.alwaysPasses();
  }

  public static <T> org.hamcrest.Matcher<T> alwaysFails(java.lang.String param1) {
    return org.hamcrest.core.Always.alwaysFails(param1);
  }

  public static <T> org.hamcrest.Matcher<T> alwaysFails() {
    return org.hamcrest.core.Always.alwaysFails();
  }

  public static <T> org.hamcrest.Matcher<T> describedAs(java.lang.String param1, org.hamcrest.Matcher<T> param2, java.lang.Object... param3) {
    return org.hamcrest.core.DescribedAs.describedAs(param1, param2, param3);
  }

  public static <T> org.hamcrest.Matcher<T[]> arrayContaining(org.hamcrest.Matcher<T> param1) {
    return org.hamcrest.collection.IsArrayContaining.arrayContaining(param1);
  }

  public static <T> org.hamcrest.Matcher<T[]> arrayContaining(T param1) {
    return org.hamcrest.collection.IsArrayContaining.arrayContaining(param1);
  }

  public static <T> org.hamcrest.Matcher<java.util.Collection<T>> collectionContaining(org.hamcrest.Matcher<T> param1) {
    return org.hamcrest.collection.IsCollectionContaining.collectionContaining(param1);
  }

  public static <T> org.hamcrest.Matcher<java.util.Collection<T>> collectionContaining(T param1) {
    return org.hamcrest.collection.IsCollectionContaining.collectionContaining(param1);
  }

  public static <T> org.hamcrest.Matcher<java.util.Collection<T>> collectionContainingAllOf(org.hamcrest.Matcher<T>... param1) {
    return org.hamcrest.collection.IsCollectionContaining.collectionContainingAllOf(param1);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> mapContaining(org.hamcrest.Matcher<K> param1, org.hamcrest.Matcher<V> param2) {
    return org.hamcrest.collection.IsMapContaining.mapContaining(param1, param2);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> mapContaining(K param1, V param2) {
    return org.hamcrest.collection.IsMapContaining.mapContaining(param1, param2);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> mapWithKey(org.hamcrest.Matcher<K> param1) {
    return org.hamcrest.collection.IsMapContaining.mapWithKey(param1);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> mapWithKey(K param1) {
    return org.hamcrest.collection.IsMapContaining.mapWithKey(param1);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> mapWithValue(org.hamcrest.Matcher<V> param1) {
    return org.hamcrest.collection.IsMapContaining.mapWithValue(param1);
  }

  public static <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> mapWithValue(V param1) {
    return org.hamcrest.collection.IsMapContaining.mapWithValue(param1);
  }

  public static org.hamcrest.Matcher<java.lang.Double> closeTo(double param1, double param2) {
    return org.hamcrest.number.IsCloseTo.closeTo(param1, param2);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThan(T param1) {
    return org.hamcrest.number.OrderingComparisons.greaterThan(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThanOrEqualTo(T param1) {
    return org.hamcrest.number.OrderingComparisons.greaterThanOrEqualTo(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThan(T param1) {
    return org.hamcrest.number.OrderingComparisons.lessThan(param1);
  }

  public static <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThanOrEqualTo(T param1) {
    return org.hamcrest.number.OrderingComparisons.lessThanOrEqualTo(param1);
  }

  public static org.hamcrest.Matcher<java.lang.String> equalToIgnoringCase(java.lang.String param1) {
    return org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase(param1);
  }

  public static org.hamcrest.Matcher<java.lang.String> equalToIgnoringWhiteSpace(java.lang.String param1) {
    return org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(param1);
  }

  public static org.hamcrest.Matcher<java.lang.String> containsString(java.lang.String param1) {
    return org.hamcrest.text.StringContains.containsString(param1);
  }

  public static org.hamcrest.Matcher<java.lang.String> endsWith(java.lang.String param1) {
    return org.hamcrest.text.StringEndsWith.endsWith(param1);
  }

  public static org.hamcrest.Matcher<java.lang.String> startsWith(java.lang.String param1) {
    return org.hamcrest.text.StringStartsWith.startsWith(param1);
  }

  public static <T> org.hamcrest.Matcher<T> asString(org.hamcrest.Matcher<java.lang.String> param1) {
    return org.hamcrest.object.HasToString.asString(param1);
  }

  public static <T> org.hamcrest.Matcher<java.lang.Class<?>> compatibleType(java.lang.Class<T> param1) {
    return org.hamcrest.object.IsCompatibleType.compatibleType(param1);
  }

  public static org.hamcrest.Matcher<java.util.EventObject> isEventFrom(java.lang.Class<? extends java.util.EventObject> param1, java.lang.Object param2) {
    return org.hamcrest.object.IsEventFrom.isEventFrom(param1, param2);
  }

  public static org.hamcrest.Matcher<java.util.EventObject> isEventFrom(java.lang.Object param1) {
    return org.hamcrest.object.IsEventFrom.isEventFrom(param1);
  }

  public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String param1) {
    return org.hamcrest.beans.HasProperty.hasProperty(param1);
  }

  public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String param1, org.hamcrest.Matcher param2) {
    return org.hamcrest.beans.HasPropertyWithValue.hasProperty(param1, param2);
  }

  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String param1, org.hamcrest.Matcher<java.lang.String> param2) {
    return org.hamcrest.xml.HasXPath.hasXPath(param1, param2);
  }

  public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String param1) {
    return org.hamcrest.xml.HasXPath.hasXPath(param1);
  }

}
