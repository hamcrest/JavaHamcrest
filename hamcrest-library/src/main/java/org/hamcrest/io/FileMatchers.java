package org.hamcrest.io;

import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;
import java.io.IOException;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class FileMatchers {

    private static void describeBasically(File file, Description mismatchDescription) {
        if (!file.exists()) {
            mismatchDescription.appendText("was nonexistent");
        } else if (file.isDirectory()) {
            mismatchDescription.appendText("was a directory");
        } else if (file.isFile()) {
            mismatchDescription.appendText("was a file");
        } else {
            mismatchDescription.appendText("was special (not a file or a directory)");
        }
    }
    
    @Factory
    public static Matcher<File> anExistingDirectory() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                describeBasically(actual, mismatchDescription);
                return actual.isDirectory();
            }
 
            public void describeTo(Description description) {
                description.appendText("an existing directory");
            }
        };
    }

    @Factory
    public static Matcher<File> anExistingFileOrDirectory() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                describeBasically(actual, mismatchDescription);
                return actual.exists();
            }

            public void describeTo(Description description) {
                description.appendText("an existing file or directory");
            }
        };
    }

    @Factory
    public static Matcher<File> anExistingFile() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                describeBasically(actual, mismatchDescription);
                return actual.isFile();
            }
 
            public void describeTo(Description description) {
                description.appendText("an existing file");
            }
        };
    }

    @Factory
    public static Matcher<File> aReadableFile() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                if (!actual.isFile()) {
                    describeBasically(actual, mismatchDescription);
                    return false;
                }
                boolean result = actual.canRead();
                mismatchDescription.appendText(result?"was a readable file":"was an unreadable file");
                return result;
            }
 
            public void describeTo(Description description) {
                description.appendText("a readable file");
            }
        };
    }

    @Factory
    public static Matcher<File> aWritableFile() {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                if (!actual.isFile()) {
                    describeBasically(actual, mismatchDescription);
                    return false;
                }
                boolean result = actual.canWrite();
                mismatchDescription.appendText(result?"was a writable file":"was an unwritable file");
                return result;
            }
 
            public void describeTo(Description description) {
                description.appendText("a writable file");
            }
        };
    }

    @Factory
    public static Matcher<File> aFileWithSize(long size) {
        return aFileWithSize(equalTo(size));
    }

    @Factory
    public static Matcher<File> aFileWithSize(final Matcher<Long> size) {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                if (!actual.isFile()) {
                    describeBasically(actual, mismatchDescription);
                    return false;
                }
                long length = actual.length();
                mismatchDescription.appendText("was a file whose size ");
                size.describeMismatch(length, mismatchDescription);
                return size.matches(length);
            }

            public void describeTo(Description description) {
                description.appendText("a file whose size is ").appendDescriptionOf(size);
            }
        };
    }

    @Factory
    public static Matcher<File> aFileNamed(final Matcher<String> name) {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                String actualName = actual.getName();
                mismatchDescription.appendText("was a File whose name ");
                name.describeMismatch(actualName, mismatchDescription);
                return name.matches(actualName);
            }

            public void describeTo(Description description) {
                description.appendText("a File whose name is ").appendDescriptionOf(name);
            }
        };
    }

    @Factory
    public static Matcher<File> aFileWithCanonicalPath(final Matcher<String> path) {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                try {
                    String canonicalPath = actual.getCanonicalPath();
                    mismatchDescription.appendText("was a File whose canonical path ");
                    path.describeMismatch(canonicalPath, mismatchDescription);
                    return path.matches(canonicalPath);
                } catch (IOException e) {
                    mismatchDescription.appendText("was a File whose canonical path was underivable (exception: ").appendValue(e).appendText(")");
                    return false;
                }
            }

            public void describeTo(Description description) {
                description.appendText("a File whose canonical path is ").appendDescriptionOf(path);
            }
        };
    }

    @Factory
    public static Matcher<File> aFileWithAbsolutePath(final Matcher<String> path) {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                String absolute = actual.getAbsolutePath();
                mismatchDescription.appendText("was a File whose absolute path ");
                path.describeMismatch(absolute, mismatchDescription);
                return path.matches(absolute);
            }

            public void describeTo(Description description) {
                description.appendText("a File whose absolute path is ").appendDescriptionOf(path);
            }
        };
    }
}
