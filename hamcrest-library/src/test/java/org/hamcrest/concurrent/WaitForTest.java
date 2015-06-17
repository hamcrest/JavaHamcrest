package org.hamcrest.concurrent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link WaitFor}.
 *
 * @author Olaf Kummer
 * @author Mark Michaelis
 */
@SuppressWarnings({"MagicNumber", "DuplicateStringLiteralInspection"})
public class WaitForTest {
    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void end_immediately_if_first_match_succeeds() throws Exception {
        TrackingMatcher<Object> originalMatcher = new AlwaysTrue<>();
        Matcher<Object> matcher = WaitFor.waitFor(originalMatcher, 30);
        assertThat(Integer.MAX_VALUE, matcher);
        assertThat("Original matcher should exactly have been queried once.", 1, equalTo(originalMatcher.getMatchesCalls()));
    }

    @Test
    public void retries_on_failure() throws Exception {
        TrackingMatcher<Object> originalMatcher = new TriesUntilTrue<>(1);
        Matcher<Object> matcher = new NeverTimeOutWaitFor<>(originalMatcher);
        assertThat(Integer.MAX_VALUE, matcher);
        assertThat("Original matcher should exactly have been queried twice.", 2, equalTo(originalMatcher.getMatchesCalls()));
    }

    @Test
    public void fail_on_continuous_failure() throws Exception {
        TrackingMatcher<Object> originalMatcher = new AlwaysFalse<>();
        Matcher<Object> matcher = new TimeOutImmediatelyWaitFor<>(originalMatcher);
        AssertionError caughtAssertionFailure = null;
        try {
            assertThat(Integer.MAX_VALUE, matcher);
        } catch (AssertionError e) {
            caughtAssertionFailure = e;
        }
        assertThat("Exception should have been caught.", caughtAssertionFailure, notNullValue());
        assertThat(
                "Original matcher should exactly have been queried once.",
                1,
                equalTo(originalMatcher.getMatchesCalls()));
    }

    @Test
    public void embedded_matcher_descriptions_bubble_up() throws Exception {
        String description = "SomeDescription";
        String mismatchDescription = "SomeMismatchDescription";

        Matcher<Object> originalMatcher = new AlwaysFalse<>(description, mismatchDescription);
        Matcher<Object> matcher = new TimeOutImmediatelyWaitFor<>(originalMatcher);
        AssertionError caughtAssertionFailure = null;
        try {
            assertThat(Integer.MAX_VALUE, matcher);
        } catch (AssertionError e) {
            caughtAssertionFailure = e;
        }
        assertThat("Exception should have been caught.", caughtAssertionFailure, notNullValue());
        errorCollector.checkThat(
                "Embedded matcher's description should be contained in failure message",
                caughtAssertionFailure.getMessage(),
                Matchers.containsString(description));
        errorCollector.checkThat(
                "Embedded matcher's mismatch description should be contained in failure message",
                caughtAssertionFailure.getMessage(),
                Matchers.containsString(mismatchDescription));
    }

    @Test
    public void polling_decelerates() throws Exception {
        TrackingMatcher<Object> originalMatcher = new TriesUntilTrue<>(2);
        TrackSleepsWaitFor<Object> matcher = new TrackSleepsWaitFor<>(originalMatcher, 30, TimeUnit.SECONDS);
        assertThat(Integer.MAX_VALUE, matcher);
        List<Long> sleeps = matcher.getSleeps();
        assertThat("Should have slept two times.", sleeps, Matchers.hasSize(2));
        assertThat("Sleep times should have accelerated.", sleeps.get(0), Matchers.lessThan(sleeps.get(1)));
    }

    @Test
    public void polling_should_give_a_last_chance_before_failure() throws Exception {
        TrackingMatcher<Object> originalMatcher = new TriesUntilTrue<>(2);
        TrackSleepsWaitFor<Object> matcher = new TrackSleepsWaitFor<>(originalMatcher, 4, TimeUnit.SECONDS);
        assertThat(Integer.MAX_VALUE, matcher);
        List<Long> sleeps = matcher.getSleeps();
        assertThat("Should have slept two times.", sleeps, Matchers.hasSize(2));
        assertThat(
                "Last sleep time should be lower to grant one additional try.",
                sleeps.get(0),
                Matchers.greaterThan(sleeps.get(1)));
    }

    @Test
    public void example_use_case_with_delegating_matcher_works() throws Exception {
        ComponentUnderTest componentUnderTest = new ComponentUnderTest(1, 2);
        Matcher<ComponentUnderTest> waitForMatcher = new NeverTimeOutWaitFor<>(new WaitFor.DelegatingWaitMatcher<>(new ToState(), Matchers.equalTo(2)));
        assertThat("Requested state should have been reached on second try.", componentUnderTest, waitForMatcher);
    }

    @Test
    public void delegating_matcher_remembers_previous_state_on_failure() throws Exception {
        ComponentUnderTest componentUnderTest = new ComponentUnderTest(42, 43);
        Matcher<ComponentUnderTest> waitForMatcher =
                new TimeOutImmediatelyWaitFor<>(WaitFor.stateMatches(new ToState(), Matchers.equalTo(43)));
        AssertionError caughtAssertionFailure = null;
        try {
            assertThat("Provoke failure because of timeout.", componentUnderTest, waitForMatcher);
        } catch (AssertionError e) {
            caughtAssertionFailure = e;
        }
        assertThat("Exception should have been caught.", caughtAssertionFailure, notNullValue());
        assertThat(
                "Failure message should refer to previous and not current state.",
                caughtAssertionFailure.getMessage(),
                Matchers.containsString("42"));
    }

  /*
   * -------------------------------------------------------------------------------------------------------------------
   * Helper Classes
   * -------------------------------------------------------------------------------------------------------------------
   */

    /**
     * Some component under test which changes its state each time the state is queried.
     */
    private static class ComponentUnderTest {
        private final List<Integer> states;

        private ComponentUnderTest(Integer... states) {
            this(Arrays.asList(states));
        }

        private ComponentUnderTest(List<Integer> states) {
            this.states = new ArrayList<>(states);
        }

        public int getState() {
            return states.remove(0);
        }
    }

    /**
     * Function to retrieve state from the component under test.
     */
    private static class ToState implements WaitFor.WaitForFunction<ComponentUnderTest, Integer> {
        @Override
        public Integer apply(ComponentUnderTest input) {
            return input.getState();
        }
    }

    private static class TrackingMatcher<T> extends TypeSafeMatcher<T> {
        private int matchesCalls = 0;

        @Override
        protected boolean matchesSafely(T item) {
            matchesCalls++;
            return false;
        }

        @Override
        public void describeTo(Description description) {
        }

        public int getMatchesCalls() {
            return matchesCalls;
        }
    }

    /**
     * Matcher which eventually will succeed.
     */
    private static class TriesUntilTrue<T> extends TrackingMatcher<T> {
        private final int untilCount;
        private int currentCount = 0;

        private TriesUntilTrue(int untilCount) {
            this.untilCount = untilCount;
        }

        @Override
        protected boolean matchesSafely(T item) {
            super.matchesSafely(item);
            return untilCount == currentCount++;
        }
    }

    /**
     * Matcher which denotes success always.
     */
    private static class AlwaysTrue<T> extends TrackingMatcher<T> {
        @Override
        protected boolean matchesSafely(T item) {
            super.matchesSafely(item);
            return true;
        }
    }

    /**
     * Matcher which denotes failure always.
     */
    private static class AlwaysFalse<T> extends TrackingMatcher<T> {
        private final String fixedDescription;
        private final String fixedMismatchDescription;

        private AlwaysFalse() {
            this(null, null);
        }

        public AlwaysFalse(String fixedDescription, String fixedMismatchDescription) {
            this.fixedDescription = fixedDescription;
            this.fixedMismatchDescription = fixedMismatchDescription;
        }

        @Override
        protected boolean matchesSafely(T item) {
            super.matchesSafely(item);
            return false;
        }

        @Override
        public void describeTo(Description description) {
            super.describeTo(description);
            if (fixedDescription != null) {
                description.appendText(fixedDescription);
            }
        }

        @Override
        protected void describeMismatchSafely(T item, Description mismatchDescription) {
            super.describeMismatchSafely(item, mismatchDescription);
            if (fixedMismatchDescription != null) {
                mismatchDescription.appendText(fixedMismatchDescription);
            }
        }
    }

    /**
     * Special WaitFor matcher which will never time out.
     */
    private static class NeverTimeOutWaitFor<T> extends WaitFor<T> {
        public NeverTimeOutWaitFor(Matcher<T> originalMatcher) {
            super(originalMatcher, 24, TimeUnit.HOURS);
        }

        @Override
        protected long nowMillis() {
            return 0;
        }

        @Override
        protected void sleep(long millis) throws InterruptedException {
            // do nothing, don't sleep
        }
    }

    /**
     * Special WaitFor matcher which will time out immediately.
     */
    private static class TimeOutImmediatelyWaitFor<T> extends WaitFor<T> {
        private long count = 0;

        public TimeOutImmediatelyWaitFor(Matcher<T> originalMatcher) {
            super(originalMatcher, 0L, TimeUnit.MILLISECONDS);
        }

        @Override
        protected long nowMillis() {
            return count++;
        }

        @Override
        protected void sleep(long millis) throws InterruptedException {
            // do nothing, don't sleep
        }
    }

    /**
     * WaitFor matcher which tracks sleep-calls for later assertions regarding the decelerating behavior.
     */
    private static class TrackSleepsWaitFor<T> extends WaitFor<T> {
        private long count = 0;
        private List<Long> sleeps = new ArrayList<>();

        public TrackSleepsWaitFor(Matcher<T> originalMatcher, long timeout, TimeUnit timeUnit) {
            super(originalMatcher, timeout, timeUnit);
        }

        @Override
        protected long nowMillis() {
            count += 1000;
            return count;
        }

        @Override
        protected void sleep(long millis) throws InterruptedException {
            // don't sleep, just track
            sleeps.add(millis);
        }

        public List<Long> getSleeps() {
            return Collections.unmodifiableList(sleeps);
        }
    }
}
