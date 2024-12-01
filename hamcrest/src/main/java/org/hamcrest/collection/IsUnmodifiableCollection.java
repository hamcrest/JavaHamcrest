package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Matches if collection is truly unmodifiable
 */
public class IsUnmodifiableCollection<E> extends TypeSafeDiagnosingMatcher<Collection<? extends E>> {

    private static final Map<Class, Object> DEFAULT_COLLECTIONS = new HashMap<>();
    private static final Set<String> KNOWN_UNMODIFIABLE_COLLECTIONS = new HashSet<>();
    private static final Set<String> KNOWN_MODIFIABLE_COLLECTIONS = new HashSet<>();

    static {
        final List<String> list = Arrays.asList("a", "b", "c");
        DEFAULT_COLLECTIONS.put(Collection.class, list);
        DEFAULT_COLLECTIONS.put(List.class, list);
        DEFAULT_COLLECTIONS.put(Set.class, new HashSet<>(list));

        KNOWN_UNMODIFIABLE_COLLECTIONS.add("java.util.ImmutableCollections");
        KNOWN_UNMODIFIABLE_COLLECTIONS.add("java.util.Collections$Unmodifiable");

        KNOWN_MODIFIABLE_COLLECTIONS.add("java.util.Arrays$ArrayList");
    }

    /**
     * Creates matcher that matches when collection is truly unmodifiable
     */
    public static <E> Matcher<Collection<? extends E>> isUnmodifiable() {
        return new IsUnmodifiableCollection<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean matchesSafely(final Collection collection, final Description mismatchDescription) {
        final Class<? extends Collection> collectionClass = collection.getClass();
        String collectionClassName = collectionClass.getName();
        for (String knownUnmodifiableCollection : KNOWN_UNMODIFIABLE_COLLECTIONS) {
            if (collectionClassName.startsWith(knownUnmodifiableCollection)) {
                return true;
            }
        }
        for (String knownModifiableCollection : KNOWN_MODIFIABLE_COLLECTIONS) {
            if (collectionClassName.startsWith(knownModifiableCollection)) {
                mismatchDescription.appendText(collectionClassName + " is a known modifiable collection");
                return false;
            }
        }
        final Collection item = getInstanceOfType(collectionClass, collection);
        if (item == null) {
            throw failedToInstantiateItem(collectionClass, null);
        }
        final Object testObject = new Object();
        final Set<Object> singletonList = Collections.singleton(testObject);

        if (collection instanceof List) {
            // This is an operation on the original collection, but it is safe, since it sets the same element
            List originalList = (List) collection;
            if (checkMethod_set(originalList, mismatchDescription)) return false;

            List copiedList = (List) item;
            if (checkMethod_listIterator_remove(copiedList, mismatchDescription)) return false;
            if (checkMethod_listIterator_set(copiedList, testObject, mismatchDescription)) return false;
            if (checkMethod_listIterator_add(copiedList, testObject, mismatchDescription)) return false;
            if (checkMethod_listIterator_index(copiedList, mismatchDescription)) return false;
            if (checkMethod_add_index(copiedList, testObject, mismatchDescription)) return false;
            if (checkMethod_add_all_index(copiedList, singletonList, mismatchDescription)) return false;
            if (checkMethod_remove_index(copiedList, mismatchDescription)) return false;
        }

        if (checkMethod_add(item, testObject, mismatchDescription)) return false;
        if (checkMethod_add_all(item, singletonList, mismatchDescription)) return false;
        if (checkMethod_remove(item, testObject, mismatchDescription)) return false;
        if (checkMethod_remove_all(item, singletonList, mismatchDescription)) return false;
        if (checkMethod_retail_all(item, singletonList, mismatchDescription)) return false;
        if (checkMethod_clear(item, mismatchDescription)) return false;
        if (checkMethod_iterator(item, mismatchDescription)) return false;

        return true;
    }

    private boolean checkMethod_iterator(Collection item, Description mismatchDescription) {
        try {
            Iterator iterator = item.iterator();
            iterator.remove();
            mismatchDescription.appendText("was able to remove an element from the iterator");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_clear(Collection item, Description mismatchDescription) {
        try {
            item.clear();
            mismatchDescription.appendText("was able to clear the collection");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_retail_all(Collection item, Set<Object> singletonList, Description mismatchDescription) {
        try {
            item.retainAll(singletonList);
            mismatchDescription.appendText("was able to call retainAll on the collection");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_remove_all(Collection item, Set<Object> singletonList, Description mismatchDescription) {
        try {
            item.removeAll(singletonList);
            mismatchDescription.appendText("was able to call removeAll on the collection");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_remove(Collection item, Object testObject, Description mismatchDescription) {
        try {
            item.remove(testObject);
            mismatchDescription.appendText("was able to call remove a value from the collection");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_remove_index(List item, Description mismatchDescription) {
        try {
            item.remove(0);
            mismatchDescription.appendText("was able to call remove by index from the collection");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_add_all(Collection item, Set<Object> singletonList, Description mismatchDescription) {
        try {
            item.addAll(singletonList);
            mismatchDescription.appendText("was able to perform addAll on the collection");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_add_all_index(List item, Set<Object> singletonList, Description mismatchDescription) {
        try {
            item.addAll(0, singletonList);
            mismatchDescription.appendText("was able to perform addAll by index on the collection");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_add(Collection item, Object testObject, Description mismatchDescription) {
        try {
            item.add(testObject);
            mismatchDescription.appendText("was able to add a value into the collection");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_add_index(List item, Object testObject, Description mismatchDescription) {
        try {
            item.add(0, testObject);
            mismatchDescription.appendText("was able to add a value into the list by index");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_listIterator_remove(List item, Description mismatchDescription) {
        List list = item;
        try {
            ListIterator iterator = list.listIterator();
            iterator.remove();
            mismatchDescription.appendText("was able to remove an element from the list iterator");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_listIterator_set(List item, Object testObject, Description mismatchDescription) {
        List list = item;
        try {
            ListIterator iterator = list.listIterator();
            iterator.next();
            iterator.set(testObject);
            mismatchDescription.appendText("was able to set element on the list iterator");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_listIterator_add(List item, Object testObject, Description mismatchDescription) {
        List list = item;
        try {
            ListIterator iterator = list.listIterator();
            iterator.next();
            iterator.add(testObject);
            mismatchDescription.appendText("was able to add element on the list iterator");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_listIterator_index(List item, Description mismatchDescription) {
        List list = item;
        try {
            Iterator iterator = list.listIterator(0);
            iterator.remove();
            mismatchDescription.appendText("was able to remove an element from the list iterator with index");
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean checkMethod_set(List list, Description mismatchDescription) {
        if (list.size() > 0) {
            try {
                list.set(0, list.get(0));
                mismatchDescription.appendText("was able to set an element of the collection");
                return true;
            } catch (Exception ignore) {
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private <T> T getInstanceOfType(final Class clazz, Collection collection) {
        if (clazz.isArray()) {
            return (T) Array.newInstance(clazz, 0);
        }

        if (clazz.isPrimitive()) {
            if (Byte.TYPE.isAssignableFrom(clazz)) {
                return (T) Byte.valueOf((byte) 1);
            }
            if (Short.TYPE.isAssignableFrom(clazz)) {
                return (T) Short.valueOf((short) 1);
            }
            if (Integer.TYPE.isAssignableFrom(clazz)) {
                return (T) Integer.valueOf(1);
            }
            if (Long.TYPE.isAssignableFrom(clazz)) {
                return (T) Long.valueOf(1L);
            }
            if (Float.TYPE.isAssignableFrom(clazz)) {
                return (T) Float.valueOf(1L);
            }
            if (Double.TYPE.isAssignableFrom(clazz)) {
                return (T) Double.valueOf(1L);
            }
            if (Boolean.TYPE.isAssignableFrom(clazz)) {
                return (T) Boolean.valueOf(true);
            }
            if (Character.TYPE.isAssignableFrom(clazz)) {
                return (T) Character.valueOf(' ');
            }
        }

        if (clazz.isInterface()) {
            Object defaultCollection = DEFAULT_COLLECTIONS.get(clazz);
            if (defaultCollection != null) {
                return (T) defaultCollection;
            }
            return null;
        }

        // For the most part of implementations there probably won't be any default constructor
        final Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();

        Constructor<?> constructorForCollection = findConstructorForCollection(declaredConstructors);

        Exception lastException = null;
        if (constructorForCollection != null) {
            try {
                return (T) constructorForCollection.newInstance(collection);
            } catch (Exception e) {
                lastException = e;
            }
        }

        // First take constructor with fewer number of arguments
        Arrays.sort(declaredConstructors, new Comparator<Constructor<?>>() {
            @Override
            public int compare(Constructor<?> o1, Constructor<?> o2) {
                return Integer.compare(o2.getParameterTypes().length, o1.getParameterTypes().length);
            }
        });

        for (Constructor<?> declaredConstructor : declaredConstructors) {
            try {
                declaredConstructor.setAccessible(true);
            } catch (Exception ignore) {
                // Since Java 17 it is impossible to make jdk* classes accessible without manipulation with modules:
                // module java.base does not "opens java.util" to unnamed module
            }
            final int parametersNumber = declaredConstructor.getParameterTypes().length;

            Object[] arguments = new Object[parametersNumber];
            for (int argumentIndex = 0; argumentIndex < arguments.length; argumentIndex++) {
                arguments[argumentIndex] = getInstanceOfType(declaredConstructor.getParameterTypes()[argumentIndex], collection);
            }
            try {
                return (T) declaredConstructor.newInstance(arguments);
            } catch (Exception e) {
                lastException = e;
            }

        }
        throw failedToInstantiateItem(clazz, lastException);
    }

    private Constructor<?> findConstructorForCollection(Constructor<?>[] declaredConstructors) {
        for (Constructor<?> constructor : declaredConstructors) {
            if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0].isAssignableFrom(Collection.class)) {
                return constructor;
            }
        }
        return null;
    }

    private <T> IllegalStateException failedToInstantiateItem(Class<T> clazz, Exception e) {
        return new IllegalStateException("Failed to create an instance of <" + clazz + "> class.", e);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Expected to be unmodifiable collection, but ");
    }

}
