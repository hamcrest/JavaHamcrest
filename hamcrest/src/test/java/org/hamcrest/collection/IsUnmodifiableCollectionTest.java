package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.util.*;

import static org.hamcrest.collection.IsUnmodifiableCollection.isUnmodifiable;

public class IsUnmodifiableCollectionTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return isUnmodifiable();
    }

    public void testMatchesUnmodifiableList() {
        assertMatches("truly unmodifiable list", isUnmodifiable(), Collections.unmodifiableList(Collections.emptyList()));
    }

    public void testMatchesUnmodifiableSet() {
        assertMatches("truly unmodifiable set", isUnmodifiable(), Collections.unmodifiableSet(Collections.emptySet()));
    }

    public void testMatchesUnmodifiableCollection() {
        assertMatches("truly unmodifiable collection", isUnmodifiable(), Collections.unmodifiableCollection(Arrays.asList(1,2,3)));
    }

    public void testMismatchesArrayList() {
        assertMismatchDescription("was able to add a value into the collection", isUnmodifiable(), new ArrayList<>());
    }

    public void testMismatchesArraysList() {
        assertMismatchDescription("was able to remove a value from the collection", isUnmodifiable(), Arrays.asList(1,2,3));
    }

    public void testMismatchesHashSet() {
        assertMismatchDescription("was able to add a value into the collection", isUnmodifiable(), new HashSet<>());
    }

    public void testMismatchesPartiallyUnmodifiableListAllowingAddAll() {
        assertMismatchDescription("was able to perform addAll on the collection", isUnmodifiable(), new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                throw new UnsupportedOperationException();
            }
        });
    }

    public void testMismatchesPartiallyUnmodifiableListAllowingRemove() {
        assertMismatchDescription("was able to remove a value from the collection", isUnmodifiable(), new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends String> c) {
                throw new UnsupportedOperationException();
            }
        });
    }

    public void testMismatchesPartiallyUnmodifiableListAllowingRemoveAll() {
        assertMismatchDescription("was able to perform removeAll on the collection", isUnmodifiable(), new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends String> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean remove(Object o) {
                throw new UnsupportedOperationException();
            }
        });
    }

    public void testMismatchesPartiallyUnmodifiableListAllowingRetainAll() {
        assertMismatchDescription("was able to perform retainAll on the collection", isUnmodifiable(), new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends String> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean remove(Object o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                throw new UnsupportedOperationException();
            }
        });
    }

    public void testMismatchesPartiallyUnmodifiableListAllowingClear() {
        assertMismatchDescription("was able to clear the collection", isUnmodifiable(), new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends String> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean remove(Object o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                throw new UnsupportedOperationException();
            }
        });
    }
}