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

    static {
        final List<String> list = Arrays.asList("a", "b", "c");
        DEFAULT_COLLECTIONS.put(Collection.class, list);
        DEFAULT_COLLECTIONS.put(List.class, list);
        DEFAULT_COLLECTIONS.put(Set.class, new HashSet<>(list));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean matchesSafely(final Collection collection, final Description mismatchDescription) {
        final Class<? extends Collection> collectionClass = collection.getClass();
        final Collection item = getInstanceOfType(collectionClass);
        if (item == null) {
            throw failedToInstantiateItem(collectionClass, null);
        }
        final Object testObject = new Object();
        final Set<Object> singletonList = Collections.singleton(testObject);

        try {
            item.add(testObject);
            mismatchDescription.appendText("was able to add a value into the collection");
            return false;
        } catch (Exception ignore) {
        }

        try {
            item.addAll(singletonList);
            mismatchDescription.appendText("was able to perform addAll on the collection");
            return false;
        } catch (Exception ignore) {
        }

        try {
            item.remove(testObject);
            mismatchDescription.appendText("was able to remove a value from the collection");
            return false;
        } catch (Exception ignore) {
        }

        try {
            item.removeAll(singletonList);
            mismatchDescription.appendText("was able to perform removeAll on the collection");
            return false;
        } catch (Exception ignore) {
        }

        try {
            item.retainAll(singletonList);
            mismatchDescription.appendText("was able to perform retainAll on the collection");
            return false;
        } catch (Exception ignore) {
        }

        try {
            item.clear();
            mismatchDescription.appendText("was able to clear the collection");
            return false;
        } catch (Exception ignore) {
        }

        return true;
    }


    @SuppressWarnings("unchecked")
    private <T> T getInstanceOfType(final Class<T> clazz) {
        Exception lastException = null;

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
        // First take constructor with fewer number of arguments
        Arrays.sort(declaredConstructors, new Comparator<Constructor<?>>() {
            @Override
            public int compare(Constructor<?> o1, Constructor<?> o2) {
                return Integer.compare(o2.getParameterTypes().length, o1.getParameterTypes().length);
            }
        });
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            declaredConstructor.setAccessible(true);
            final int parametersNumber = declaredConstructor.getParameterTypes().length;

            Object[] arguments = new Object[parametersNumber];
            for (int argumentIndex = 0; argumentIndex < arguments.length; argumentIndex++) {
                arguments[argumentIndex] = getInstanceOfType(declaredConstructor.getParameterTypes()[argumentIndex]);
            }
            try {
                return (T) declaredConstructor.newInstance(arguments);
            } catch (Exception e) {
                lastException = e;
            }

        }
        throw failedToInstantiateItem(clazz, lastException);
    }

    private <T> IllegalStateException failedToInstantiateItem(Class<T> clazz, Exception e) {
        return new IllegalStateException("Failed to create an instance of <" + clazz + "> class.", e);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Expected to be unmodifiable collection, but ");
    }

    /**
     * Creates matcher that matches when collection is truly unmodifiable
     */
    public static <E> Matcher<Collection<? extends E>> isUnmodifiable() {
        return new IsUnmodifiableCollection<>();
    }
}
