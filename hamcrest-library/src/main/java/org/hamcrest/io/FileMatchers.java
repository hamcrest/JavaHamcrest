package org.hamcrest.io;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;

public final class FileMatchers {

    public static Matcher<File> anExistingDirectory() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final boolean result = actual.isDirectory();
                if (!result) {
                    mismatchDescription.appendText("was a File that either didn't exist, or was not a directory");
                }
                return result;
            }
 
            public void describeTo(Description description) {
                description.appendText("a File representing a directory that exists");
            }
        };
    }

    public static Matcher<File> anExistingFileOrDirectory() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final boolean exists = actual.exists();
                if (!exists) {
                    mismatchDescription.appendText("was a File that did not exist");
                }
                return exists;
            }

            public void describeTo(Description description) {
                description.appendText("a file or directory that exists");
            }
        };
    }

    public static Matcher<File> anExistingFile() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final boolean result = actual.isFile();
                if (!result) {
                    mismatchDescription.appendText("was a File that either didn't exist, or was a directory");
                }
                return result;
            }
 
            public void describeTo(Description description) {
                description.appendText("a File representing a file that exists");
            }
        };
    }

    public static Matcher<File> aReadableFile() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final boolean result = actual.canRead();
                if (!result) {
                    mismatchDescription.appendText("was a File that could not be read");
                }
                return result;
            }
 
            public void describeTo(Description description) {
                description.appendText("a File that can be read");
            }
        };
    }

    public static Matcher<File> aWritableFile() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final boolean result = actual.canWrite();
                if (!result) {
                    mismatchDescription.appendText("was a File that could not be written to");
                }
                return result;
            }
 
            public void describeTo(Description description) {
                description.appendText("a writable File");
            }
        };
    }

    public static Matcher<File> aFileWithSize(long size) {
        return aFileWithSize(equalTo(size));
    }

    public static Matcher<File> aFileWithSize(final Matcher<Long> expected) {
        return new FeatureMatcher<File, Long>(expected, "A file with size", "size") {
            @Override protected Long featureValueOf(File actual) { return actual.length(); }
        };
    }

    public static Matcher<File> aFileNamed(final Matcher<String> expected) {
        return new FeatureMatcher<File, String>(expected, "A file with name", "name") {
            @Override protected String featureValueOf(File actual) { return actual.getName(); }
        };
    }

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

    public static Matcher<File> aFileWithAbsolutePath(final Matcher<String> expected) {
        return new FeatureMatcher<File, String>(expected, "A file with absolute path", "path") {
            @Override protected String featureValueOf(File actual) { return actual.getAbsolutePath(); }
        };
    }
}
