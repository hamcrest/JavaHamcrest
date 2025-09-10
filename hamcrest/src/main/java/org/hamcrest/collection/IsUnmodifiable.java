package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * This class contains static factory methods for creating Matchers that test if a collection is unmodifiable.
 * 
 * <p>
 * The main matcher is {@link #isUnmodifiableCollection()} which tries to determine if a collection is unmodifiable.
 * Other matchers are provided to test specific cases, such as {@link #isUnmodifiableJdkCollection()}, 
 * {@link #isModifiableJdkCollection()} and {@link #isUnmodifiableCustomCollection()}.
 * </p>
 * 
 */
public class IsUnmodifiable {

    // Prevent instantiation
    private IsUnmodifiable() {
    }

    /**
     * Creates matcher that matches when collection is unmodifiable.
     * 
     * It looks for any of the known modifiable or unmodifiable JDK collections, if not found it tries to instantiate
     * the collection and call all modification methods to see if any of them succeed.  This instantiation can fail if
     * the collection does not have a constructor that can be called with default values.
     * 
     * @param <E> the type of elements in the collection
     * @return The matcher
     */
    public static <E> Matcher<Collection<? extends E>> isUnmodifiableCollection() {
        return new IsUnmodifiableCollectionMatcher<>();
    }

    /**
     * Creates a matcher that matches when the collection is one of the known unmodifiable JDK collections.
     * 
     * @param <E> the type of elements in the collection
     * @return The matcher
     */
    public static <E> Matcher<Collection<? extends E>> isUnmodifiableJdkCollection() {
        return new IsUnmodifiableJdkCollectionMatcher<>();
    }

    /**
     * Creates a matcher that matches when the collection is one of the known modifiable JDK collections.
     * 
     * @param <E> the type of elements in the collection
     * @return The matcher
     */
    public static <E> Matcher<Collection<? extends E>> isModifiableJdkCollection() {
        return new IsModifiableJdkCollectionMatcher<>();
    }

    /**
     * Creates a matcher that matches when the collection is unmodifiable by calling all modification methods to see if
     * any of them succeed. This instantiation can fail if the collection does not have a constructor that can be called
     * with default values.
     * 
     * @param <E> the type of elements in the collection
     * @return The matcher
     */
    public static <E> Matcher<Collection<? extends E>> isUnmodifiableCustomCollection() {
        return new IsUnmodifiableCustomCollectionMatcher<>();
    }

    private static class IsUnmodifiableCollectionMatcher<E> extends TypeSafeDiagnosingMatcher<Collection<? extends E>> {
        private final Matcher<Collection<? extends E>> isUnmodifiableJdkCollection;
        private final Matcher<Collection<? extends E>> isModifiableJdkCollection;
        private final Matcher<Collection<? extends E>> isUnmodifiableCustomCollection;
        
        private IsUnmodifiableCollectionMatcher() {
            this(IsUnmodifiable.isUnmodifiableJdkCollection(), IsUnmodifiable.isModifiableJdkCollection(), IsUnmodifiable.isUnmodifiableCustomCollection());
        }
        
        private IsUnmodifiableCollectionMatcher(Matcher<Collection<? extends E>> isUnmodifiableJdkCollection,
                                        Matcher<Collection<? extends E>> isModifiableJdkCollection,
                                        Matcher<Collection<? extends E>> isUnmodifiableCustomCollection) {
            this.isUnmodifiableJdkCollection = isUnmodifiableJdkCollection;
            this.isModifiableJdkCollection = isModifiableJdkCollection;
            this.isUnmodifiableCustomCollection = isUnmodifiableCustomCollection;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("unmodifiable collection");
        }

        @Override
        protected boolean matchesSafely(Collection<? extends E> collection, Description mismatchDescription) {
            if (isUnmodifiableJdkCollection.matches(collection)) {
                return true;    // It's a known unmodifiable collection, so shortcut the remaining tests
            }
            if (isModifiableJdkCollection.matches(collection)) {
                // If it's a known modifiable collection, then fail
                mismatchDescription.appendText(collection.getClass().getName() + " is a known modifiable JDK collection");
               return false;
            }
            if (!isUnmodifiableCustomCollection.matches(collection)) {
                // If we are able to modify the collection, then fail
                isUnmodifiableCustomCollection.describeMismatch(collection, mismatchDescription);
                return false;
            }
            return true;
        }
    }
    
    private static class IsUnmodifiableJdkCollectionMatcher<E> extends TypeSafeDiagnosingMatcher<Collection<? extends E>> {
        private static final Set<String> KNOWN_UNMODIFIABLE_COLLECTIONS = 
                Set.of("java.util.ImmutableCollections", 
                       "java.util.Collections$Unmodifiable", 
                       "java.util.Collections$Empty"
                       );
  
        @Override
        public void describeTo(Description description) {
            description.appendText("unmodifiable JDK collection");
        }

        @Override
        protected boolean matchesSafely(Collection<? extends E> collection, Description mismatchDescription) {
            @SuppressWarnings("rawtypes")
            final Class<? extends Collection> collectionClass = collection.getClass();
            String collectionClassName = collectionClass.getName();
            for (String knownUnmodifiableCollection : KNOWN_UNMODIFIABLE_COLLECTIONS) {
                if (collectionClassName.startsWith(knownUnmodifiableCollection)) {
                    return true;
                }
            }
            mismatchDescription.appendText(collectionClassName + " is not a known unmodifiable JDK collection");
            return false;
        }
    }

    private static class IsModifiableJdkCollectionMatcher<E> extends TypeSafeDiagnosingMatcher<Collection<? extends E>> {
        private static final Set<String> KNOWN_MODIFIABLE_COLLECTIONS = 
                Set.of("java.util.Arrays$ArrayList",
                        "java.util.ArrayList",
                        "java.util.LinkedList",
                        "java.util.HashSet",
                        "java.util.LinkedHashSet",
                        "java.util.TreeSet",
                        "java.util.PriorityQueue",
                        "java.util.ArrayDeque"
                        );
        @Override
        public void describeTo(Description description) {
            description.appendText("modifiable JDK collection");
        }
       
        @Override
        protected boolean matchesSafely(Collection<? extends E> collection, Description mismatchDescription) {
            @SuppressWarnings("rawtypes")
            final Class<? extends Collection> collectionClass = collection.getClass();
            String collectionClassName = collectionClass.getName();
            for (String knownModifiableCollection : KNOWN_MODIFIABLE_COLLECTIONS) {
                if (collectionClassName.startsWith(knownModifiableCollection)) {
                    return true;
                }
            }
            mismatchDescription.appendText(collectionClassName + " is not a known modifiable JDK collection");
            return false;
        }
    }

    /**
     * Exercises a collection, tests all methods to see if they are modifiable. 
     */
    private interface MethodChecker {
        /**
         * Tries to modify a collection by calling all the methods.
         * 
         * @return true if collection is unable to be modified.
         */
        boolean checkMethods();
    }
    
    
    /**
     * 
     */
    private static class CollectionMethodChecker implements MethodChecker {
        protected static final Object TEST_OBJECT = new Object();
        protected static final Set<Object> TEST_COLLECTION = Collections.singleton(TEST_OBJECT);

        protected final Collection<Object> item;
        protected final Description mismatchDescription;
        
        public CollectionMethodChecker(Collection<Object> item, Description mismatchDescription) {
            this.item = item;
            this.mismatchDescription = mismatchDescription;
        }

        @Override
        public boolean checkMethods() {
            if (checkMethod_add(item, TEST_OBJECT, mismatchDescription)) return false;
            if (checkMethod_add_all(item, TEST_COLLECTION, mismatchDescription)) return false;
            if (checkMethod_remove(item, TEST_OBJECT, mismatchDescription)) return false;
            if (checkMethod_remove_all(item, TEST_COLLECTION, mismatchDescription)) return false;
            if (checkMethod_retail_all(item, TEST_COLLECTION, mismatchDescription)) return false;
            if (checkMethod_clear(item, mismatchDescription)) return false;
            if (checkMethod_iterator(item, mismatchDescription)) return false;
            
            return true;
        }
        
        private boolean checkMethod_add(Collection<Object> item, Object testObject, Description mismatchDescription) {
            try {
                item.add(testObject);
                mismatchDescription.appendText("was able to add a value into the collection");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_add_all(Collection<Object> item, Set<Object> singletonList, Description mismatchDescription) {
            try {
                item.addAll(singletonList);
                mismatchDescription.appendText("was able to perform addAll on the collection");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_iterator(Collection<Object> item, Description mismatchDescription) {
            try {
                item.iterator().remove();
                mismatchDescription.appendText("was able to remove an element from the iterator");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_clear(Collection<Object> item, Description mismatchDescription) {
            try {
                item.clear();
                mismatchDescription.appendText("was able to clear the collection");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_retail_all(Collection<Object> item, Set<Object> singletonList, Description mismatchDescription) {
            try {
                item.retainAll(singletonList);
                mismatchDescription.appendText("was able to call retainAll on the collection");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_remove_all(Collection<Object> item, Set<Object> singletonList, Description mismatchDescription) {
            try {
                item.removeAll(singletonList);
                mismatchDescription.appendText("was able to call removeAll on the collection");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_remove(Collection<Object> item, Object testObject, Description mismatchDescription) {
            try {
                item.remove(testObject);
                mismatchDescription.appendText("was able to call remove a value from the collection");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    }

    private static class ListMethodChecker extends CollectionMethodChecker {
        protected final List<Object> list;

        public ListMethodChecker(Collection<Object> item, Description mismatchDescription) {
            super(item, mismatchDescription);
            if (item instanceof List<Object> originalList) {
                this.list = originalList;
            } else {
                throw new IllegalArgumentException("collection provided is not a list.");
            }
      }

        @Override
        public boolean checkMethods() {
            // This is an operation on the original collection, but it is safe, since it sets the same element
            if (checkMethod_set(list, mismatchDescription)) return false;

            if (checkMethod_listIterator_remove(list, mismatchDescription)) return false;
            if (checkMethod_listIterator_set(list, TEST_OBJECT, mismatchDescription)) return false;
            if (checkMethod_listIterator_add(list, TEST_OBJECT, mismatchDescription)) return false;
            if (checkMethod_listIterator_index(list, mismatchDescription)) return false;
            if (checkMethod_add_index(list, TEST_OBJECT, mismatchDescription)) return false;
            if (checkMethod_add_all_index(list, TEST_COLLECTION, mismatchDescription)) return false;
            if (checkMethod_remove_index(list, mismatchDescription)) return false;

            return super.checkMethods();
        }
        
        private boolean checkMethod_remove_index(List<Object> item, Description mismatchDescription) {
            try {
                item.remove(0);
                mismatchDescription.appendText("was able to call remove by index from the collection");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_add_all_index(List<Object> item, Set<Object> singletonList, Description mismatchDescription) {
            try {
                item.addAll(0, singletonList);
                mismatchDescription.appendText("was able to perform addAll by index on the collection");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_add_index(List<Object> item, Object testObject, Description mismatchDescription) {
            try {
                item.add(0, testObject);
                mismatchDescription.appendText("was able to add a value into the list by index");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_listIterator_remove(List<Object> item, Description mismatchDescription) {
            try {
                item.listIterator().remove();
                mismatchDescription.appendText("was able to remove an element from the list iterator");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_listIterator_set(List<Object> item, Object testObject, Description mismatchDescription) {
            try {
                ListIterator<Object> iterator = item.listIterator();
                iterator.next();
                iterator.set(testObject);
                mismatchDescription.appendText("was able to set element on the list iterator");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_listIterator_add(List<Object> item, Object testObject, Description mismatchDescription) {
            try {
                ListIterator<Object> iterator = item.listIterator();
                iterator.next();
                iterator.add(testObject);
                mismatchDescription.appendText("was able to add element on the list iterator");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_listIterator_index(List<Object> item, Description mismatchDescription) {
            try {
                Iterator<Object> iterator = item.listIterator(0);
                iterator.remove();
                mismatchDescription.appendText("was able to remove an element from the list iterator with index");
                return true;
            } catch (Exception ignore) {
            }
            return false;
        }
    
        private boolean checkMethod_set(List<Object> list, Description mismatchDescription) {
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
    }
    
    private static class IsUnmodifiableCustomCollectionMatcher<E> extends TypeSafeDiagnosingMatcher<Collection<? extends E>> {
        @SuppressWarnings("rawtypes")
        private static final Map<Class<? extends Collection>, Object> DEFAULT_COLLECTIONS = new HashMap<>();
        static {
            final List<String> list = Arrays.asList("a", "b", "c");
            DEFAULT_COLLECTIONS.put(Collection.class, list);
            DEFAULT_COLLECTIONS.put(List.class, list);
            DEFAULT_COLLECTIONS.put(Set.class, new HashSet<>(list));
        }


        @Override
        protected boolean matchesSafely(final Collection<? extends E> collection, final Description mismatchDescription) {
            @SuppressWarnings("rawtypes")
            final Class<? extends Collection> collectionClass = collection.getClass();
            final Collection<Object> item = getInstanceOfType(collectionClass, collection);
            if (item == null) {
                throw failedToInstantiateItem(collectionClass, null);
            }

            MethodChecker methodChecker = item instanceof List<?> ? new ListMethodChecker(item, mismatchDescription) : new CollectionMethodChecker(item, mismatchDescription);
           
            return methodChecker.checkMethods();
        }
    
    
    
        @SuppressWarnings("unchecked")
        private <T> T getInstanceOfType(final Class<?> clazz, @SuppressWarnings("rawtypes") Collection collection) {
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
            description.appendText("unmodifiable collection");
        }
    }
}
