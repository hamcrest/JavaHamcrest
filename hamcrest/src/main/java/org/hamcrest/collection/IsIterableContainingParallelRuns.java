package org.hamcrest.collection;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher like that can check an iterable for parallel runs of a list of matchers. It is similar
 * to {@link org.hamcrest.collection.IsIterableContainingInRelativeOrder}, in fact behaving exactly
 * the same when {@code numRuns = 1}.
 *
 * @param <E> Type of items to match.
 * @see #containsParallelRunsOf(int, List)
 */
public class IsIterableContainingParallelRuns<E> extends TypeSafeDiagnosingMatcher<Iterable<E>> {

  private final int numRuns;
  private final List<Matcher<? super E>> matchers;

  /**
   * Construct a new matcher that will check for parallel runs.
   *
   * @see IsIterableContainingParallelRuns
   * @see #containsParallelRunsOf(int, List)
   */
  public IsIterableContainingParallelRuns(
      final int numRuns, final List<Matcher<? super E>> matchers
  ) {
    if (numRuns <= 0) {
      throw new IllegalArgumentException("The number of parallel runs must be strictly positive");
    }
    this.numRuns = numRuns;
    this.matchers = matchers;
  }

  @Override
  protected boolean matchesSafely(
      final Iterable<E> iterable, final Description mismatchDescription
  ) {
    final MatchParallelRuns<E> matchParallelRuns =
        new MatchParallelRuns<>(numRuns, matchers, mismatchDescription);
    matchParallelRuns.processItems(iterable);
    return matchParallelRuns.isFinished();
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("iterable containing ");
    if (numRuns > 1) {
      description.appendValue(numRuns).appendText(" parallel runs of ");
    }
    description.appendList("[", ", ", "]", matchers)
        .appendText(" in relative order");
  }

  static class MatchParallelRuns<F> {
    private final int numRuns;
    private final List<Matcher<? super F>> matchers;
    private final Description mismatchDescription;
    private final List<Integer> nextMatchIndexes;
    private final List<F> lastMatchedItems;

    public MatchParallelRuns(
        final int numRuns,
        final List<Matcher<? super F>> matchers,
        final Description mismatchDescription
    ) {
      this.numRuns = numRuns;
      if (matchers.isEmpty()) {
        throw new IllegalArgumentException("Should specify at least one expected element");
      } else {
        this.matchers = matchers;
      }
      this.mismatchDescription = mismatchDescription;
      this.nextMatchIndexes = new ArrayList<>(numRuns);
      this.lastMatchedItems = new ArrayList<>(numRuns);
      for (int i = 0; i < numRuns; ++i) {
        this.nextMatchIndexes.add(0);
        this.lastMatchedItems.add(null);
      }
    }

    public void processItems(Iterable<? extends F> iterable) {
      for (final F item : iterable) {
        for (int i = 0; i < numRuns; ++i) {
          final int nextMatchIndex = nextMatchIndexes.get(i);
          if (nextMatchIndex < matchers.size() && matchers.get(nextMatchIndex).matches(item)) {
            lastMatchedItems.set(i, item);
            nextMatchIndexes.set(i, nextMatchIndex + 1);
            break;
          }
        }
      }
    }

    public boolean isFinished() {
      boolean isFinished = true;
      for (int i = 0; i < numRuns; ++i) {
        final int nextMatchIndex = nextMatchIndexes.get(i);
        if (nextMatchIndex < matchers.size()) {
          if (!isFinished) {
            mismatchDescription.appendText("; and ");
          }
          isFinished = false;
          mismatchDescription.appendDescriptionOf(matchers.get(nextMatchIndex))
              .appendText(" was not found");
          if (lastMatchedItems.get(i) != null) {
            mismatchDescription.appendText(" after ").appendValue(lastMatchedItems.get(i));
          }
          if (numRuns > 1) {
            mismatchDescription.appendText(" in run ").appendValue(i + 1);
          }
        }
      }
      return isFinished;
    }
  }

  /**
   * Creates a matcher for {@link Iterable Iterables} that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, that contains items logically equal to the
   * corresponding item in the specified items, in the same relative order, with {@code numRuns}
   * occurrences of the specified series of items being matched (possibly interspersed).
   */
  @SafeVarargs
  public static <E> Matcher<Iterable<E>> containsParallelRunsOf(
      final int numRuns, final E... items
  ) {
    final List<Matcher<? super E>> matchers = new ArrayList<>(items.length);
    for (final Object item : items) {
      matchers.add(equalTo(item));
    }

    return containsParallelRunsOf(numRuns, matchers);
  }

  @SafeVarargs
  public static <E> Matcher<Iterable<E>> containsParallelRunsOf(
      final int numRuns, final Matcher<? super E>... matchers
  ) {
    return containsParallelRunsOf(numRuns, asList(matchers));
  }

  public static <E> Matcher<Iterable<E>> containsParallelRunsOf(
      final int numRuns, final List<Matcher<? super E>> matchers
  ) {
    return new IsIterableContainingParallelRuns<>(numRuns, matchers);
  }

}
