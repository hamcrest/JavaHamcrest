package org.hamcrest.collection;

import static org.hamcrest.test.MatcherAssertions.*;

import static org.hamcrest.collection.IsUnmodifiable.*;

import org.hamcrest.test.AbstractMatcherTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import java.util.*;
import java.util.stream.Stream;


public class IsUnmodifiableTest extends AbstractMatcherTest {

    private static final String SET_INT_INDEX_E_ELEMENT = "set(int index, E element)";
    private static final String ADD_E_E = "add(E e)";
    private static final String ADD_INT_INDEX_E_ELEMENT = "add(int index, E element)";
    private static final String REMOVE_INT_INDEX = "remove(int index)";
    private static final String REMOVE_OBJECT_O = "remove(Object o)";
    private static final String ADD_ALL_COLLECTION_EXTENDS_E_C = "addAll(Collection<? extends E> c)";
    private static final String ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C = "addAll(int index, Collection<? extends E> c)";
    private static final String REMOVE_ALL_COLLECTION_C = "removeAll(Collection<?> c)";
    private static final String RETAIN_ALL_COLLECTION_C = "retainAll(Collection<?> c)";
    private static final String CLEAR = "clear()";
    private record ModificationErrorCondition(String errorMsg, Set<String> unsupportedMethods) {};
    
    @SuppressWarnings("unused")
    private static final List<ModificationErrorCondition> ERROR_CONDITIONS = List.of(
            new ModificationErrorCondition("was able to add element on the list iterator", Set.of(SET_INT_INDEX_E_ELEMENT)),
            new ModificationErrorCondition("was able to perform addAll by index on the collection", Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT)),
            new ModificationErrorCondition("was able to call remove by index from the collection", Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C)),
            new ModificationErrorCondition("was able to add a value into the collection", Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX)),
            new ModificationErrorCondition("was able to perform addAll on the collection", Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E)),
            new ModificationErrorCondition("was able to call remove a value from the collection", Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C)),
            new ModificationErrorCondition("was able to call removeAll on the collection", Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C, REMOVE_OBJECT_O)),
            new ModificationErrorCondition("was able to call retainAll on the collection", Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C, REMOVE_OBJECT_O, REMOVE_ALL_COLLECTION_C)),
            new ModificationErrorCondition("was able to clear the collection", Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C, REMOVE_OBJECT_O, REMOVE_ALL_COLLECTION_C, RETAIN_ALL_COLLECTION_C)),
            new ModificationErrorCondition(null, Set.of(SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C, REMOVE_OBJECT_O, REMOVE_ALL_COLLECTION_C, RETAIN_ALL_COLLECTION_C, CLEAR))
    );

    // TODO: Should I include Map.of().values()?
    @SuppressWarnings("unused")
    private static final List<Arguments> JDK_KNOWN_UNMODIFIABLE_COLLECTIONS = Stream.<Collection<?>>of(
            // List of collections that we know are unmodifiable
            Set.of(), List.of(), 
            Collections.emptyList(), Collections.emptySet(), Collections.emptySortedSet(), 
            Collections.unmodifiableCollection(new ArrayList<>()), Collections.unmodifiableList(new ArrayList<>()), Collections.unmodifiableSet(new HashSet<>())
            )
            .map(c->Arguments.of(c, c.getClass().getName()))
            .toList();
    @SuppressWarnings("unused")
    private static final List<Arguments> JDK_KNOWN_MODIFIABLE_COLLECTIONS = Stream.<Collection<?>>of(
            // List of collections that we know are modifiable
            new ArrayList(), new LinkedList(), new HashSet(), new LinkedHashSet(), new TreeSet(), new PriorityQueue(), new ArrayDeque(),
            Arrays.asList(1, 2, 3)
            )
            .map(c->Arguments.of(c, c.getClass().getName()))
            .toList();
    
    // isUnmodifiable() tests
    
    @Override
    protected Matcher<?> createMatcher() {
        return isUnmodifiableCollection();
    }

    @DisplayName("IsUnmodifiableCollection() matches known unmodifiable JDK collections")
    @ParameterizedTest(name = "{1}")
    @FieldSource("JDK_KNOWN_UNMODIFIABLE_COLLECTIONS")
    public void testIsUnmodifiableMatchesKnownJdkUnmodifiableCollections(Collection<?> collection, String className) {
        assertMatches("truly unmodifiable JDK Collection (" + className + ")", isUnmodifiableCollection(), collection);
    }

    @DisplayName("IsUnmodifiableCollection() mismatches known modifiable JDK collections")
    @ParameterizedTest(name = "{1}")
    @FieldSource("JDK_KNOWN_MODIFIABLE_COLLECTIONS")
    public void testIsUnmodifiableMismatchesKnownJdkModifiableCollections(Collection<?> collection, String className) {
        assertMismatchDescription(className + " is a known modifiable JDK collection", isUnmodifiableCollection(), collection);
    }
    
    @DisplayName("IsUnmodifiableCollection() matches unmodifiable custom collection")
    @Test
    public void testIsUnmodifiableMatchesUnmodifiableCustomList() {
         assertMatches("truly unmodifiable list", isUnmodifiableCollection(), new CustomUnmodifiableList<>(Arrays.asList(1, 2, 3)));
    }

    @DisplayName("IsUnmodifiableCollection() mismatches modifiable custom collection")
    @ParameterizedTest
    @FieldSource("ERROR_CONDITIONS")
    public void testIsUnmodifiableMismatchesModifiableCustomList(ModificationErrorCondition errorCondition) {
        CustomModifiableList<Integer> arrayListWrapper = new CustomModifiableList<>(List.of(1, 2, 3), errorCondition.unsupportedMethods);
        if (errorCondition.errorMsg != null) {
            assertMismatchDescription(
                    errorCondition.errorMsg,
                    isUnmodifiableCollection(),
                    arrayListWrapper
            );
        } else {
            assertMatches("truly unmodifiable collection", isUnmodifiableCollection(), arrayListWrapper);
        }
    }

    // isUnmodifiableJdkCollection() tests

    @DisplayName("IsUnmodifiableJdkCollection() matches known unmodifiable JDK collections")
    @ParameterizedTest(name = "{1}")
    @FieldSource("JDK_KNOWN_UNMODIFIABLE_COLLECTIONS")
    public void testIsUnmodifiableJdkCollectionMatchesKnownJdkUnmodifiableCollections(Collection<?> collection, String className) {
        assertMatches("truly unmodifiable JDK Collection (" + className + ")", isUnmodifiableJdkCollection(), collection);
    }
    
    @DisplayName("IsUnmodifiableJdkCollection() mismatches known modifiable JDK collections")
    @ParameterizedTest(name = "{1}")
    @FieldSource("JDK_KNOWN_MODIFIABLE_COLLECTIONS")
    public void testIsUnmodifiableJdkCollectionMismatchesKnownJdkModifiableCollections(Collection<?> collection, String className) {
        assertMismatchDescription(className + " is not a known unmodifiable JDK collection", isUnmodifiableJdkCollection(), collection);
    }
    
    @DisplayName("IsUnmodifiableJdkCollection() mismatches unmodifiable custom collection")
    @Test
    public void testIsUnmodifiableJdkCollectionMismatchesUnmodifiableCustomList() {
         CustomUnmodifiableList<Integer> testList = new CustomUnmodifiableList<>(Arrays.asList(1, 2, 3));
         assertMismatchDescription(CustomUnmodifiableList.class.getName() + " is not a known unmodifiable JDK collection", isUnmodifiableJdkCollection(), testList);
    }

    // isModifiableJdkCollection() tests
    
    @DisplayName("IsModifiableJdkCollection() mismatches known unmodifiable JDK collections")
    @ParameterizedTest(name = "{1}")
    @FieldSource("JDK_KNOWN_UNMODIFIABLE_COLLECTIONS")
    public void testIsModifiableJdkCollectionMatchesKnownJdkUnmodifiableCollections(Collection<?> collection, String className) {
        assertMismatchDescription(className + " is not a known modifiable JDK collection", isModifiableJdkCollection(), collection);
    }
    
    @DisplayName("IsModifiableJdkCollection() matches known modifiable JDK collections")
    @ParameterizedTest(name = "{1}")
    @FieldSource("JDK_KNOWN_MODIFIABLE_COLLECTIONS")
    public void testIsModifiableJdkCollectionMatchesKnownJdkModifiableCollections(Collection<?> collection, String className) {
        assertMatches("truly unmodifiable JDK Collection (" + className + ")", isModifiableJdkCollection(), collection);
    }
    
    @DisplayName("IsModifiableJdkCollection() mismatches modifiable custom collection")
    @Test
    public void testIsModifiableJdkCollectionMismatchesModifiableCustomList() {
         CustomModifiableList<Integer> testList = new CustomModifiableList<>(Arrays.asList(1, 2, 3), Set.of());
         assertMismatchDescription(CustomModifiableList.class.getName() + " is not a known modifiable JDK collection", isModifiableJdkCollection(), testList);
    }

    // isUnmodifiableCustomCollection() tests
    @DisplayName("IsUnmodifiableCustomCollection() matches unmodifiable custom collection")
    @Test
    public void testisUnmodifiableCustomCollectionMatchesUnmodifiableCustomList() {
         assertMatches("truly unmodifiable list", isUnmodifiableCustomCollection(), new CustomUnmodifiableList<>(Arrays.asList(1, 2, 3)));
    }

    @DisplayName("IsUnmodifiableCustomCollection() mismatches modifiable custom collection")
    @ParameterizedTest
    @FieldSource("ERROR_CONDITIONS")
    public void testIsUnmodifiableCustomCollectionMismatchesModifiableCustomList(ModificationErrorCondition errorCondition) {
        CustomModifiableList<Integer> arrayListWrapper = new CustomModifiableList<>(List.of(1, 2, 3), errorCondition.unsupportedMethods);
        if (errorCondition.errorMsg != null) {
            assertMismatchDescription(
                    errorCondition.errorMsg,
                    isUnmodifiableCustomCollection(),
                    arrayListWrapper
            );
        } else {
            assertMatches("truly unmodifiable collection", isUnmodifiableCustomCollection(), arrayListWrapper);
        }
    }

    @SuppressWarnings("serial")
    static class CustomModifiableList<E> extends ArrayList<E> {
        private final Set<String> unsupportedMethods;

        @SuppressWarnings("unused") // Used by reflection
        public CustomModifiableList(Collection<? extends E> c) {
            super(c);
            if (c instanceof CustomModifiableList) {
                this.unsupportedMethods = new HashSet<>(((CustomModifiableList<E>) c).unsupportedMethods);
            } else {
                throw new IllegalStateException();
            }
        }

        public CustomModifiableList(List<E> list, Set<String> unsupportedMethods) {
            super(list);
            this.unsupportedMethods = unsupportedMethods;
        }

        @Override
        public E set(int index, E element) {
            if (unsupportedMethods.contains(SET_INT_INDEX_E_ELEMENT)) throw new UnsupportedOperationException();
            return super.set(index, element);
        }

        @Override
        public boolean add(E e) {
            if (unsupportedMethods.contains(ADD_E_E)) throw new UnsupportedOperationException();
            return super.add(e);
        }

        @Override
        public void add(int index, E element) {
            if (unsupportedMethods.contains(ADD_INT_INDEX_E_ELEMENT)) throw new UnsupportedOperationException();
            super.add(index, element);
        }

        @Override
        public E remove(int index) {
            if (unsupportedMethods.contains(REMOVE_INT_INDEX)) throw new UnsupportedOperationException();
            return super.remove(index);
        }

        @Override
        public boolean remove(Object o) {
            if (unsupportedMethods.contains(REMOVE_OBJECT_O)) throw new UnsupportedOperationException();
            return super.remove(o);
        }

        @Override
        public void clear() {
            if (unsupportedMethods.contains(CLEAR)) throw new UnsupportedOperationException();
            super.clear();
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            if (unsupportedMethods.contains(ADD_ALL_COLLECTION_EXTENDS_E_C)) throw new UnsupportedOperationException();
            return super.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            if (unsupportedMethods.contains(ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C))
                throw new UnsupportedOperationException();
            return super.addAll(index, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            if (unsupportedMethods.contains(REMOVE_ALL_COLLECTION_C)) throw new UnsupportedOperationException();
            return super.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            if (unsupportedMethods.contains(RETAIN_ALL_COLLECTION_C)) throw new UnsupportedOperationException();
            return super.retainAll(c);
        }
    }

    private static class CustomUnmodifiableList<E> implements List<E> {

        private List<E> list;

        public CustomUnmodifiableList(List<E> list) {
            this.list = Collections.unmodifiableList(list);
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return list.contains(o);
        }

        @Override
        public Iterator<E> iterator() {
            return list.iterator();
        }

        @Override
        public Object[] toArray() {
            return list.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return list.toArray(a);
        }

        @Override
        public boolean add(E e) {
            return list.add(e);
        }

        @Override
        public boolean remove(Object o) {
            return list.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return list.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return list.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            return list.addAll(index, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return list.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return list.retainAll(c);
        }

        @Override
        public void clear() {
            list.clear();
        }

        @Override
        public E get(int index) {
            return list.get(index);
        }

        @Override
        public E set(int index, E element) {
            return list.set(index, element);
        }

        @Override
        public void add(int index, E element) {
            list.add(index, element);
        }

        @Override
        public E remove(int index) {
            return list.remove(index);
        }

        @Override
        public int indexOf(Object o) {
            return list.indexOf(o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return list.lastIndexOf(o);
        }

        @Override
        public ListIterator<E> listIterator() {
            return list.listIterator();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return list.listIterator(index);
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return list.subList(fromIndex, toIndex);
        }
    }
}
