package org.hamcrest.internal;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.currentThread;

/**
 * A facade for assumption failures that uses the Java services framework to let implementations plug in their own
 * implementations.
 * Java does not define an assumption failure like it does {@link AssertionError} for an assertion failure.
 * Therefore, testing frameworks such as JUnit 4 and JUnit 5 (OpenTest4J) define their own.
 * This class lets the different test frameworks to plug in their own assumption failures.
 */
public abstract class AssumptionProvider {

    private static final AssumptionProvider INSTANCE = new CompositeAssumptionProvider(loadAssumptionProviders());

    /**
     * Assumes that an object is matching a matcher, throwing on violation of that assumption.
     *
     * @param message a violation message for when the assumption is violated, which may be {@code null}.
     * @param actual  an object to match, which may be {@code null}.
     * @param matcher a matcher to match an object with, which must not be {@code null}.
     * @param <T>     an object's generic type.
     */
    public abstract <T> void assumeThat(String message, T actual, Matcher<T> matcher);

    /**
     * Gets the singleton instance assumption provider, which combines 3 assumption providers:
     * <ol>
     *     <li>An optional assumption provider for JUnit 5, only if the runtime classpath contains JUnit 5.</li>
     *     <li>An optional assumption provider for JUnit 4, only if the runtime classpath contains JUnit 4.</li>
     *     <li>A default assumption provider which throws {@link AssertionError} on violation.</li>
     * </ol>
     *
     * @return the singleton assumption provider, never {@code null}.
     */
    public static AssumptionProvider getInstance() {
        return INSTANCE;
    }

    private static List<AssumptionProvider> loadAssumptionProviders() {
        ArrayList<AssumptionProvider> providers = new ArrayList<>(3);
        try {
            providers.add(new JUnit5AssumptionProvider());
        } catch (NoClassDefFoundError ignored) {
            // Optional JUnit 5 dependency is not on runtime class path. Continue without it.
        }
        try {
            providers.add(new JUnit4AssumptionProvider());
        } catch (NoClassDefFoundError ignored) {
            // Optional JUnit 4 dependency is not on runtime class path. Continue without it.
        }
        providers.add(new AssertionAssumptionProvider());
        providers.trimToSize();
        return providers;
    }

    /**
     * Searches the stack trace top-down for an occurrence of a package name prefix to reject or accept.
     * If the first hit is to reject, then this method returns {@code false}.
     * If the first hit is to accept, then this method returns {@code true}.
     * If there is no hit at all, then this method returns {@code false}.
     *
     * @param packageNamePrefixToReject a package name prefix to reject, which must not be {@code null}.
     * @param packageNamePrefixToAccept a package name prefix to accept, which must not be {@code null}.
     * @return {@code true} if the stack trace contains an entry matching the package name prefix to accept before it
     * contains that to reject, {@code false} otherwise.
     */
    protected static boolean stackTraceContains(String packageNamePrefixToReject, String packageNamePrefixToAccept) {
        StackTraceElement[] stackTrace = currentThread().getStackTrace();
        for (int i = stackTrace.length; --i > -1; ) {
            StackTraceElement stackTraceElement = stackTrace[i];
            if (stackTraceElement.getClassName().startsWith(packageNamePrefixToReject)) {
                return false;
            } else if (stackTraceElement.getClassName().startsWith(packageNamePrefixToAccept)) {
                return true;
            }
        }
        return false;
    }
}
