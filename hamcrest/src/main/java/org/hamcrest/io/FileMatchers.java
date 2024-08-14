package org.hamcrest.io;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matchers for properties of files.
 */
public final class FileMatchers {

    private FileMatchers() {
    }

    /**
     * A matcher that checks if a directory exists.
     * @return the file matcher
     */
    public static Matcher<File> anExistingDirectory() {
        return fileChecker(IS_DIRECTORY, "an existing directory", "is not a directory");
    }

    /**
     * A matcher that checks if a file or directory exists.
     * @return the file matcher
     */
    public static Matcher<File> anExistingFileOrDirectory() {
        return fileChecker(EXISTS, "an existing file or directory", "does not exist");
    }

    /**
     * A matcher that checks if a file exists.
     * @return the file matcher
     */
    public static Matcher<File> anExistingFile() {
        return fileChecker(IS_FILE, "an existing File", "is not a file");
    }

    /**
     * A matcher that checks if a file is readable.
     * @return the file matcher
     */
    public static Matcher<File> aReadableFile() {
        return fileChecker(CAN_READ, "a readable File", "cannot be read");
    }

    /**
     * A matcher that checks if a directory is writable.
     * @return the file matcher
     */
    public static Matcher<File> aWritableFile() {
        return fileChecker(CAN_WRITE, "a writable File", "cannot be written to");
    }

    /**
     * A matcher that checks if a file has a specific size.
     * @param size the expected size
     * @return the file matcher
     */
    public static Matcher<File> aFileWithSize(long size) {
        return aFileWithSize(equalTo(size));
    }

    /**
     * A matcher that checks if a file size matches an expected size.
     * @param expected matcher for the expected size
     * @return the file matcher
     */
    public static Matcher<File> aFileWithSize(final Matcher<Long> expected) {
        return new FeatureMatcher<File, Long>(expected, "A file with size", "size") {
            @Override protected Long featureValueOf(File actual) { return actual.length(); }
        };
    }

    /**
     * A matcher that checks if a file name matches an expected name.
     * @param expected the expected name
     * @return the file matcher
     */
    public static Matcher<File> aFileNamed(final Matcher<String> expected) {
        return new FeatureMatcher<File, String>(expected, "A file with name", "name") {
            @Override protected String featureValueOf(File actual) { return actual.getName(); }
        };
    }

    /**
     * A matcher that checks if a file canonical path matches an expected path.
     * @param expected the expected path
     * @return the file matcher
     */
    public static Matcher<File> aFileWithCanonicalPath(final Matcher<String> expected) {
        return new FeatureMatcher<File, String>(expected, "A file with canonical path", "path") {
            @Override protected String featureValueOf(File actual) {
                try {
                    return actual.getCanonicalPath();
                } catch (IOException e) {
                    return "Exception: " + e.getMessage();
                }
            }
        };
    }

    /**
     * A matcher that checks if a file absolute path matches an expected path.
     * @param expected the expected path
     * @return the file matcher
     */
    public static Matcher<File> aFileWithAbsolutePath(final Matcher<String> expected) {
        return new FeatureMatcher<File, String>(expected, "A file with absolute path", "path") {
            @Override protected String featureValueOf(File actual) { return actual.getAbsolutePath(); }
        };
    }

    /**
     * Checks the status of a {@link File}.
     */
    public interface FileStatus {
        /**
         * Checks the give file against a status.
         * @param actual the file to check
         * @return true if the file status matches, otherwise false.
         */
        boolean check(File actual);
    }

    /**
     * Checks if a {@link File} is writable.
     */
    public static final FileStatus CAN_WRITE = new FileStatus() {
        @Override public boolean check(File actual) { return actual.canWrite(); }
    };

    /**
     * Checks if a {@link File} is readable.
     */
    public static final FileStatus CAN_READ = new FileStatus() {
        @Override public boolean check(File actual) { return actual.canRead(); }
    };

    /**
     * Checks if a {@link File} is a file.
     */
    public static final FileStatus IS_FILE = new FileStatus() {
        @Override public boolean check(File actual) { return actual.isFile(); }
    };

    /**
     * Checks if a {@link File} is a directory.
     */
    public static final FileStatus IS_DIRECTORY = new FileStatus() {
        @Override public boolean check(File actual) { return actual.isDirectory(); }
    };

    /**
     * Checks if a {@link File} is a exists.
     */
    public static final FileStatus EXISTS = new FileStatus() {
        @Override public boolean check(File actual) { return actual.exists(); }
    };

    private static Matcher<File> fileChecker(final FileStatus fileStatus, final String successDescription, final String failureDescription) {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final boolean result = fileStatus.check(actual);
                if (!result) {
                    mismatchDescription.appendText(String.format("'%s' %s", actual, failureDescription));
                }
                return result;
            }

            public void describeTo(Description description) {
                description.appendText(successDescription);
            }
        };
    }

}
