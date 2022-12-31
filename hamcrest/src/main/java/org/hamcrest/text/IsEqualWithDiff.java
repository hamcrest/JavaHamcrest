package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.github.difflib.text.DiffRowGenerator;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.text.DiffRow;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class IsEqualWithDiff extends TypeSafeMatcher<String> {
    private final String string;

    public IsEqualWithDiff(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required");
        }
        this.string = string;
    }

    @Override
    public boolean matchesSafely(String item) {
        return string.equals(item);
    }

    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("Diff: \n\t");
        List<String> expected = Arrays.asList(string.split("\n"));
        List<String> actual = Arrays.asList(item.split("\n"));

        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .mergeOriginalRevised(true)
                .reportLinesUnchanged(false)
                .inlineDiffByWord(true)
                .oldTag(new Function<Boolean, String>() {
                    @Override
                    public String apply(Boolean aBoolean) {
                        return "~";
                    }
                })
                .newTag(new Function<Boolean, String>() {
                    @Override
                    public String apply(Boolean aBoolean) {
                        return "**";
                    }
                })
                .build();
        List<DiffRow> rows = null;
        try {
            rows = generator.generateDiffRows(
                    actual,
                    expected
            );
        } catch (DiffException e) {
            e.printStackTrace();
        }

        for (int ii = 0; ii < rows.size(); ii++) {
            if (rows.get(ii).getTag() == DiffRow.Tag.CHANGE)
            {
                mismatchDescription.appendText(String.format("At line %d: ", ii));
                mismatchDescription.appendText(rows.get(ii).getOldLine()).appendText("\n\t");
            }
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string equal to ")
                .appendValue(string)
                .appendText(" with diff when mismatch");
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is equal to
     * the specified expectedString, with markdown style diff output message when mismatch.
     * For example:
     * <pre>assertThat("Foo", equalToIgnoringCase("FOO"))</pre>
     *
     * @param expectedString
     *     the expected value of matched strings
     */
    public static Matcher<String> equalToWithDiff(String expectedString) {
        return new IsEqualWithDiff(expectedString);
    }
}
