/**
 * BSD License
 *
 * Copyright (c) 2000-2021 www.hamcrest.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * Neither the name of Hamcrest nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
        return fileChecker(IS_DIRECTORY, "an existing directory", "is not a directory");
    }

    public static Matcher<File> anExistingFileOrDirectory() {
        return fileChecker(EXISTS, "an existing file or directory", "does not exist");
    }

    public static Matcher<File> anExistingFile() {
        return fileChecker(IS_FILE, "an existing File", "is not a file");
    }

    public static Matcher<File> aReadableFile() {
        return fileChecker(CAN_READ, "a readable File", "cannot be read");
    }

    public static Matcher<File> aWritableFile() {
        return fileChecker(CAN_WRITE, "a writable File", "cannot be written to");
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

    public static interface FileStatus {
        boolean check(File actual);
    }

    public static final FileStatus CAN_WRITE = new FileStatus() {
        @Override public boolean check(File actual) { return actual.canWrite(); }
    };
    public static final FileStatus CAN_READ = new FileStatus() {
        @Override public boolean check(File actual) { return actual.canRead(); }
    };

    public static final FileStatus IS_FILE = new FileStatus() {
        @Override public boolean check(File actual) { return actual.isFile(); }
    };

    public static final FileStatus IS_DIRECTORY = new FileStatus() {
        @Override public boolean check(File actual) { return actual.isDirectory(); }
    };

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
