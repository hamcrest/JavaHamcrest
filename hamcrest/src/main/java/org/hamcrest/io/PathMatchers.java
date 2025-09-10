package org.hamcrest.io;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.TypeSafeDiagnosingMatcher.matcher;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Matchers for properties of files.
 */
public final class PathMatchers {

    private PathMatchers() {
    }

    /**
     * A matcher that checks if a directory exists.
     * @return the file matcher
     */
    public static Matcher<Path> anExistingDirectory() {
        return matcher(Files::isDirectory, "an existing directory", "is not a directory", Path.class);
    }

    /**
     * A matcher that checks if a file or directory exists.
     * @return the file matcher
     */
    public static Matcher<Path> anExistingFileOrDirectory() {
        return matcher(Files::exists, "an existing file or directory", "does not exist", Path.class);
    }

    /**
     * A matcher that checks if a file exists.
     * @return the file matcher
     */
    public static Matcher<Path> anExistingFile() {
        return matcher(Files::isRegularFile, "an existing file", "is not a file", Path.class);
    }

    /**
     * A matcher that checks if a file or directory is readable.
     * @return the file matcher
     */
    public static Matcher<Path> isReadable() {
        return matcher(Files::isReadable, "a readable file or directory", "cannot be read", Path.class);
    }

    /**
     * A matcher that checks if a file/directory is writable.
     * @return the file matcher
     */
    public static Matcher<Path> isWritable() {
        return matcher(Files::isWritable, "a writable file or directory", "cannot be written to", Path.class);
    }

    /**
     * A matcher that checks if a file/directory is executable.
     * @return the file matcher
     */
    public static Matcher<Path> isExecutable() {
        return matcher(Files::isExecutable, "an executable file or directory", "is not executable", Path.class);
    }

    /**
     * A matcher that checks if a file/directory is the same file.
     * @return the file matcher
     */
    public static Matcher<Path> isSameFile(Path target) {
        return matcher(toUncheckedEx(p->Files.isSameFile(target, p)), "the same file or directory", "is not the same file or directory", Path.class);
    }

    /**
     * A matcher that checks if a file/directory is a symbolic link.
     * @return the file matcher
     */
    public static Matcher<Path> isSymbolicLink() {
        return matcher(Files::isSymbolicLink, "a file or directory is a symbolic link", "is not a symbolic link", Path.class);
    }

    /**
     * A matcher that checks if a file/directory is hidden.
     * @return the file matcher
     */
    public static Matcher<Path> isHidden() {
        return matcher(toUncheckedEx(p->PathMatchers.isHidden(p)), "a hidden file or directory", "is not hidden", Path.class);
    }

    /**
     * A matcher that checks if a file has a specific size.
     * @param size the expected size
     * @return the file matcher
     */
    public static Matcher<Path> hasSizeEqualTo(long size) {
        return hasSize(equalTo(size));
    }

    /**
     * A matcher that checks if a file size matches an expected size.
     * @param expected matcher for the expected size
     * @return the file matcher
     */
    public static Matcher<Path> hasSize(final Matcher<Long> expected) {
        return FeatureMatcher.matcher(expected, p -> toUncheckedEx(() -> Files.size(p)), "A file with size", "size", Path.class);
//        return new FeatureMatcher<Path, Long>(expected, "A file with size", "size") {
//            @Override protected Long featureValueOf(Path actual) { return toUncheckedEx(()->Files.size(actual)); }
//        };
    }

    /**
     * A matcher that checks if a file name matches an expected name.
     * @param expected the expected name
     * @return the file matcher
     */
    public static Matcher<Path> hasFileName(final Matcher<Path> expected) {
        return new FeatureMatcher<Path, Path>(expected, "A file with name", "name") {
            @Override protected Path featureValueOf(Path actual) { return actual.getFileName(); }
        };
    }

    /**
     * A matcher that checks if a file name matches an expected String.
     * @param expected the expected name
     * @return the file matcher
     */
    public static Matcher<Path> hasFileNameString(final Matcher<String> expected) {
        return new FeatureMatcher<Path, String>(expected, "A file with name", "name") {
            @Override protected String featureValueOf(Path actual) { return actual.getFileName().toString(); }
        };
    }

    /**
     * A matcher that checks if a file real path matches an expected path.
     * @param expected the expected path
     * @return the file matcher
     */
    public static Matcher<Path> hasRealPath(final Matcher<Path> expected) {
        return new FeatureMatcher<Path, Path>(expected, "A file with real path", "path") {
            @Override protected Path featureValueOf(Path actual) { return toUncheckedEx(()->actual.toRealPath()); }
        };
    }

    /**
     * A matcher that checks if a file real path matches an expected String.
     * @param expected the expected path
     * @return the file matcher
     */
    public static Matcher<Path> hasRealPathString(final Matcher<String> expected) {
        return new FeatureMatcher<Path, String>(expected, "A file with real path", "path") {
            @Override protected String featureValueOf(Path actual) { return toUncheckedEx(()->actual.toRealPath().toString()); }
        };
    }

    /**
     * A matcher that checks if a file canonical path matches an expected path.
     * 
     * @deprecated Use {@link #hasRealPath(Matcher)} instead. Provided for backward compatibility with FileMatchers.
     * 
     * @param expected the expected path
     * @return the file matcher
     */
    @Deprecated
    public static Matcher<Path> hasCanonicalPathString(final Matcher<String> expected) {
        return hasRealPathString(expected); //
    }

    /**
     * A matcher that checks if a file absolute path matches an expected path.
     * 
     * @param expected the expected path
     * @return the file matcher
     */
    public static Matcher<Path> hasAbsolutePath(final Matcher<Path> expected) {
        return new FeatureMatcher<Path, Path>(expected, "A file with absolute path", "path") {
            @Override protected Path featureValueOf(Path actual) { return actual.toAbsolutePath(); }
        };
    }

    /**
     * A matcher that checks if a file absolute path matches an expected String.
     * 
     * @param expected the expected path String
     * @return the file matcher
     */
    public static Matcher<Path> hasAbsolutePathString(final Matcher<String> expected) {
        return new FeatureMatcher<Path, String>(expected, "A file with absolute path", "path") {
            @Override protected String featureValueOf(Path actual) { return actual.toAbsolutePath().toString(); }
        };
    }

    /**
     * A matcher that checks if a file's FileSystem matches an expected FileSystem.
     * 
     * @param expected
     * @return
     */
    public static Matcher<Path> hasFileSystem(final Matcher<FileSystem> expected) {
        return new FeatureMatcher<Path, FileSystem>(expected, "A file with file system", "file system") {
            @Override protected java.nio.file.FileSystem featureValueOf(Path actual) { return actual.getFileSystem(); }
        };
    }

    /**
     * Matcher for matching file content with given file path.
     * 
     * <p>
     * <i>Note:</i> line endings in the file are preserved in their platform dependent form, 
     * so both files must contain the same line endings to match.
     * </p>
     * 
     * @param expected The file with the expected content
     * @return A FeatureMatcher that takes the content of a file path as feature
     */
    public static Matcher<Path> matchesContentOf(Path expected) {
        String expectedContent =  toUncheckedEx(()->Files.readString(expected));
        return new FeatureMatcher<Path, String>(equalTo(expectedContent), "A file with content", "content") {
            @Override protected String featureValueOf(Path actual) { return toUncheckedEx(()->Files.readString(actual));}
        };
    }

    /**
     * Matcher for matching file content with given String Matcher.
     * 
     * <p>
     * <i>Note:</i> line endings in the file are converted to '\n' to avoid platform dependent test results
     * </p>
     * 
     * @param expected The expected content Matcher
     * @return A FeatureMatcher that takes the content of a file as feature
     */
    public static Matcher<Path> hasContent(Matcher<String> expected) {
        return new FeatureMatcher<Path, String>(expected, "A file with content", "content") {
            @Override protected String featureValueOf(Path actual) {
                // use collector to avoid platform dependent line endings
                return toUncheckedEx(()->Files.lines(actual).collect(Collectors.joining("\n")));
                }
        };
    }

    // Possible additions:
    // - hasParent(Matcher<Path>)
    // - hasRoot(Matcher<Path>)
    // - hasAttributes(Matcher<String>>...)
    // - hasLastModifiedTime(Matcher<Instant>)
    // - hasOwner(Matcher<UserPrincipal>)
    // - hasPosixPermissions(Matcher<Set<PosixFilePermission>>)

    // - hasCreationTime(Matcher<Instant>)
    // - hasGroup(Matcher<GroupPrincipal>)
    // - hasFileKey(Matcher<FileKey>)
    // - hasFileAttribute(String, Matcher<Object>)
    // - hasProvider(Matcher<FileSystemProvider>)

    // - containsStrings(String...)

    // Workaround for JDK 8 not supporting Files.isHidden(Path) for directories
    // (JDK-8215467). Fixed in Java 13.
    private static boolean isHidden(Path path) throws IOException {
        if (path.getFileSystem().provider().getClass().getName().contains("WindowsFileSystemProvider")) {
            // WindowsFileSystemProvider does not support isHidden(Path) for directories
            return Files.readAttributes(path, "dos:hidden", java.nio.file.LinkOption.NOFOLLOW_LINKS).get("hidden")
                    .equals(Boolean.TRUE);
        } else {
            return Files.isHidden(path);
        }
    }

    @FunctionalInterface
    private interface Predicate_WithExceptions<T, E extends Exception> {
        boolean test(T t) throws E;
    }

    private static <T, E extends IOException> Predicate<T> toUncheckedEx(Predicate_WithExceptions<T, E> predicate) {
        return value -> {
            try {
                return predicate.test(value);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    @FunctionalInterface
    private interface Supplier_WithExceptions<T, E extends IOException> {
        T get() throws E;
    }

    private static <T> T toUncheckedEx(Supplier_WithExceptions<T, ?> supplier) {
        try {
            return supplier.get();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
