package org.hamcrest.io;

import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;
import java.io.IOException;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class FileMatchers {

    @Factory
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

    @Factory
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

    @Factory
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

    @Factory
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

    @Factory
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

    @Factory
    public static Matcher<File> aFileWithSize(long size) {
        return aFileWithSize(equalTo(size));
    }

    @Factory
    public static Matcher<File> aFileWithSize(final Matcher<Long> size) {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final long length = actual.length();
                final boolean result = size.matches(length);
                if (!result) {
                    mismatchDescription.appendText("was a File whose size ");
                    size.describeMismatch(length, mismatchDescription);
                }
                return result;
            }

            public void describeTo(Description description) {
                description.appendText("a File whose size is ").appendDescriptionOf(size);
            }
        };
    }

    @Factory
    public static Matcher<File> aFileNamed(final Matcher<String> name) {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final String actualName = actual.getName();
                final boolean result = name.matches(actualName);
                if (!result) {
                    mismatchDescription.appendText("was a File whose name ");
                    name.describeMismatch(actualName, mismatchDescription);
                }
                return result;
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
                    final boolean result = path.matches(canonicalPath);
                    if (!result) {
                        mismatchDescription.appendText("was a File whose canonical path ");
                        path.describeMismatch(canonicalPath, mismatchDescription);
                    }
                    return result;
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
                final String absolute = actual.getAbsolutePath();
                final boolean result = path.matches(absolute);
                if (!result) {
                    mismatchDescription.appendText("was a File whose absolute path ");
                    path.describeMismatch(absolute, mismatchDescription);
                }
                return result;
            }

            public void describeTo(Description description) {
                description.appendText("a File whose absolute path is ").appendDescriptionOf(path);
            }
        };
    }
}
