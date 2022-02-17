package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.*;

import static org.hamcrest.collection.IsUnmodifiableCollection.isUnmodifiable;

public class IsUnmodifiableCollectionTest extends AbstractMatcherTest {

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
    private static final List<String[]> ERROR_CONDITIONS = Arrays.asList(
            new String[]{"was able to add element on the list iterator", SET_INT_INDEX_E_ELEMENT},
            new String[]{"was able to perform addAll by index on the collection", SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT},
            new String[]{"was able to call remove by index from the collection", SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C},
            new String[]{"was able to add a value into the collection", SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX},
            new String[]{"was able to perform addAll on the collection", SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E},
            new String[]{"was able to call remove a value from the collection", SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C},
            new String[]{"was able to call removeAll on the collection", SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C, REMOVE_OBJECT_O},
            new String[]{"was able to call retainAll on the collection", SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C, REMOVE_OBJECT_O, REMOVE_ALL_COLLECTION_C},
            new String[]{"was able to clear the collection", SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C, REMOVE_OBJECT_O, REMOVE_ALL_COLLECTION_C, RETAIN_ALL_COLLECTION_C},
            new String[]{null, SET_INT_INDEX_E_ELEMENT, ADD_INT_INDEX_E_ELEMENT, ADD_ALL_INT_INDEX_COLLECTION_EXTENDS_E_C, REMOVE_INT_INDEX, ADD_E_E, ADD_ALL_COLLECTION_EXTENDS_E_C, REMOVE_OBJECT_O, REMOVE_ALL_COLLECTION_C, RETAIN_ALL_COLLECTION_C, CLEAR}
    );

    @Override
    protected Matcher<?> createMatcher() {
        return isUnmodifiable();
    }

    public void testMatchesUnmodifiableList() {
        assertMatches("truly unmodifiable list", isUnmodifiable(), Collections.unmodifiableList(Collections.emptyList()));
    }

    public void testMatchesUnmodifiableCustomList() {
        class CustomUnmodifiableList<E> implements List<E> {

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
        assertMatches("truly unmodifiable list", isUnmodifiable(), new CustomUnmodifiableList<>(Arrays.asList(1, 2, 3)));
    }

    public void testMatchesUnmodifiableSet() {
        assertMatches("truly unmodifiable set", isUnmodifiable(), Collections.unmodifiableSet(Collections.emptySet()));
    }

    public void testMatchesUnmodifiableCollection() {
        assertMatches("truly unmodifiable collection", isUnmodifiable(), Collections.unmodifiableCollection(Arrays.asList(1, 2, 3)));
    }

    public void testMismatchesArrayList() {
        assertMismatchDescription("was able to add a value into the list by index", isUnmodifiable(), new ArrayList<>());
    }

    public void testMismatchesArraysList() {
        assertMismatchDescription("was able to set an element of the collection", isUnmodifiable(), Arrays.asList(1, 2, 3));
    }

    public void testMismatchesHashSet() {
        assertMismatchDescription("was able to add a value into the collection", isUnmodifiable(), new HashSet<>());
    }

    public void testMismatches() {
        for (String[] errorCondition : ERROR_CONDITIONS) {
            String[] unsupportedMethods = new String[errorCondition.length - 1];
            System.arraycopy(errorCondition, 1, unsupportedMethods, 0, unsupportedMethods.length);
            ArrayListWrapper<Integer> arrayListWrapper = new ArrayListWrapper<>(Arrays.asList(1, 2, 3), unsupportedMethods);
            String error = errorCondition[0];
            if (error != null) {
                assertMismatchDescription(
                        error,
                        isUnmodifiable(),
                        arrayListWrapper
                );
            } else {
                assertMatches("truly unmodifiable collection", isUnmodifiable(), arrayListWrapper);
            }
        }
    }

    static class ArrayListWrapper<E> extends ArrayList<E> {
        private final Set<String> unsupportedMethods;

        @SuppressWarnings("unused") // Used by reflection
        public ArrayListWrapper(Collection<? extends E> c) {
            super(c);
            if (c instanceof ArrayListWrapper) {
                this.unsupportedMethods = new HashSet<>(((ArrayListWrapper<E>) c).unsupportedMethods);
            } else {
                throw new IllegalStateException();
            }
        }

        public ArrayListWrapper(List<E> list, String... unsupportedMethods) {
            super(list);
            this.unsupportedMethods = new HashSet<>(Arrays.asList(unsupportedMethods));
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

}
