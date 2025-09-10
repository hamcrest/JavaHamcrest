package org.hamcrest.io;

import org.hamcrest.test.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.test.MatcherAssertions.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class PathMatchersTest extends AbstractMatcherTest {

    @TempDir Path tempDir;
    private Path directory;
    private Path file;
    private Path anotherFile;
    private Path symbolicLink;

    @BeforeEach
    protected void setUp() throws IOException {
        directory = Files.createDirectory(tempDir.resolve("myDir"));
        file = directory.resolve("myFile");
        anotherFile = directory.resolve("myAnotherFile");
        Files.createFile(file);
        Files.writeString(anotherFile, "world");
        Files.createFile(directory.resolve("mydirFile")); // Makes sure myDir is not empty.
        if (!OS.WINDOWS.isCurrentOs()) { // Can't do symbolic links on Windows unless admin privileges are available.
            symbolicLink = tempDir.resolve("mySymbolicLink");
            Files.createSymbolicLink(symbolicLink, file);
        }
    }

    @Test
    public void testAnExistingDirectory() {
        assertMatches("matches existing directory", PathMatchers.anExistingDirectory(), directory);
        assertDoesNotMatch("doesn't match existing file", PathMatchers.anExistingDirectory(), file);
        assertMismatchDescription("'foo' is not a directory", PathMatchers.anExistingDirectory(), Paths.get("foo"));
    }

    @Test
    public void testAnExistingFileOrDirectory() {
        assertMatches("matches existing file", PathMatchers.anExistingFileOrDirectory(), file);
        assertMatches("matches existing directory", PathMatchers.anExistingFileOrDirectory(), directory);
        assertMismatchDescription("'foo' does not exist", PathMatchers.anExistingFileOrDirectory(), Paths.get("foo"));
    }

    @Test
    public void testAnExistingFile() {
        assertMatches("matches existing file", PathMatchers.anExistingFile(), file);
        assertDoesNotMatch("doesn't match existing directory", PathMatchers.anExistingFile(), directory);
        assertMismatchDescription("'foo' is not a file", PathMatchers.anExistingFile(), Paths.get("foo"));
    }

    @Test
    public void testIsReadable() { // Not all OSes will allow setting readability so have to be forgiving here.
        file.toFile().setReadable(true);
        assertMatches("matches readable file", PathMatchers.isReadable(), file);

        if (file.toFile().setReadable(false)) {
            assertDoesNotMatch("doesn't match unreadable file", PathMatchers.isReadable(), file);
        }
    }

    @Test
    public void testIsWritable() {
        assertMatches("matches writable file", PathMatchers.isWritable(), file);

        assertTrue(file.toFile().setWritable(false), "set writable off " + file);
        assertDoesNotMatch("doesn't match unwritable file", PathMatchers.isWritable(), file);

        assertMatches("matches writable directory", PathMatchers.isWritable(), directory);

        // Directories cannot be set to read-only on Windows.
        if (!OS.WINDOWS.isCurrentOs()) {
            assertTrue(directory.toFile().setWritable(false), "set writable off " + file);
            assertDoesNotMatch("doesn't match unwritable file", PathMatchers.isWritable(), directory);
        }
    }

    @Test
    public void testIsHidden() throws Exception {
        Path hiddenFile = tempDir.resolve(Paths.get(".hidden_file"));
        Path hiddenDir = tempDir.resolve(Paths.get(".hidden_dir"));
        Files.createFile(hiddenFile);
        Files.createDirectory(hiddenDir);

        // Set the hidden attribute for the file and directory on Windows.
        if (OS.WINDOWS.isCurrentOs()) {
            Files.setAttribute(hiddenFile, "dos:hidden", true);
            Files.setAttribute(hiddenDir, "dos:hidden", true);
        }

        assertMatches("matches hidden file", PathMatchers.isHidden(), tempDir.resolve(hiddenFile));
        assertMatches("matches hidden directory", PathMatchers.isHidden(), hiddenDir);

        assertDoesNotMatch("doesn't match unhidden (i.e. visible) file", PathMatchers.isHidden(), file);
        assertDoesNotMatch("doesn't match unhidden (i.e. visible) directory", PathMatchers.isHidden(), directory);
    }

    @Test
    public void testIsSameFile() {
        // TODO: Needs work
        assertMatches("matches same file", PathMatchers.isSameFile(file.toAbsolutePath()), file);
        assertDoesNotMatch("doesn't match different file", PathMatchers.isSameFile(file), directory);
        assertMismatchDescription("'" + directory.toString() + "' is not the same file or directory",
                PathMatchers.isSameFile(file), directory);
        if (!OS.WINDOWS.isCurrentOs()) { // Windows does not support symbolic links without administrator privileges.
            assertMatches("matches same file through symbolic link", PathMatchers.isSameFile(file), symbolicLink);
            assertDoesNotMatch("doesn't match different file through symbolic link", PathMatchers.isSameFile(directory), symbolicLink);
        }
    }

    @DisabledOnOs(OS.WINDOWS) // Windows does not support creating symbolic links without administrator
                              // privileges.
    @Test
    public void testisSymbolicLink() {
        assertMatches("matches synbolic link", PathMatchers.isSymbolicLink(), symbolicLink);
        assertDoesNotMatch("doesn't match a file", PathMatchers.isSymbolicLink(), file);
        assertMismatchDescription("'foo' is not a symbolic link", PathMatchers.isSymbolicLink(), Paths.get("foo"));
    }

    @Test
    public void testHasSizeEqualToLong() {
        assertMatches("matches file size", PathMatchers.hasSizeEqualTo(0L), file);
        assertDoesNotMatch("doesn't match incorrect file size", PathMatchers.hasSizeEqualTo(34L), file);

        assertMatches("matches file size", PathMatchers.hasSizeEqualTo(OS.WINDOWS.isCurrentOs() ? 0L : 4096L), directory);
        assertDoesNotMatch("doesn't match incorrect file size", PathMatchers.hasSizeEqualTo(34L), directory);
    }

    @Test
    public void testHasSizeMatcherOfLong() {
        assertMatches("matches file size", PathMatchers.hasSize(equalTo(0L)), file);
        assertDoesNotMatch("doesn't match incorrect file size", PathMatchers.hasSize(equalTo(23L)), file);
    }

    @Test
    public void testHasFileName_Path() {
        assertMatches("matches file name", PathMatchers.hasFileName(equalTo(file.getFileName())), file);
        assertDoesNotMatch("doesn't match incorrect file name", PathMatchers.hasFileName(equalTo(Paths.get("foo"))), file);
    }

    @Test
    public void testHasFileNameString_String() {
        assertMatches("matches file name", PathMatchers.hasFileNameString(equalTo(file.getFileName().toString())), file);
        assertDoesNotMatch("doesn't match incorrect file name", PathMatchers.hasFileNameString(equalTo("foo")), file);
    }

    @Test
    public void testHasRealPath() throws Exception {
        assertMatches("matches file canonical path", PathMatchers.hasRealPath(equalTo(file.toRealPath())), file);
        assertDoesNotMatch("doesn't match incorrect canonical path", PathMatchers.hasRealPath(equalTo(Paths.get("foo"))), file);
    }

    @Test
    public void testHasRealPathString() throws Exception {
        assertMatches("matches file canonical path", PathMatchers.hasRealPathString(equalTo(file.toRealPath().toString())), file);
        assertDoesNotMatch("doesn't match incorrect canonical path", PathMatchers.hasRealPathString(equalTo("foo")), file);
    }

    @Test
    public void testHasCanonicalPathString() throws Exception {
        assertMatches("matches file canonical path", PathMatchers.hasCanonicalPathString(equalTo(file.toRealPath().toString())),
                file);
        assertDoesNotMatch("doesn't match incorrect canonical path", PathMatchers.hasCanonicalPathString(equalTo("foo")), file);
    }

    @Test
    public void testHasAbsolutePath() {
        assertMatches("matches file absolute path", PathMatchers.hasAbsolutePath(equalTo(file.toAbsolutePath())), file);
        assertDoesNotMatch("doesn't match incorrect absolute path", PathMatchers.hasAbsolutePath(equalTo(Paths.get("foo"))),
                file);
    }

    @Test
    public void testHasAbsolutePathString() {
        assertMatches("matches file absolute path", PathMatchers.hasAbsolutePathString(equalTo(file.toAbsolutePath().toString())),
                file);
        assertDoesNotMatch("doesn't match incorrect absolute path", PathMatchers.hasAbsolutePathString(equalTo("foo")), file);
    }

    @Test
    public void testHasFileSystem() {
        assertMatches("matches file system", PathMatchers.hasFileSystem(equalTo(file.getFileSystem())), file);
        // TODO: Maybe use JimFS to create a different FileSystem for this test?
//      assertDoesNotMatch("doesn't match incorrect file system",PathMatchers.hasFileSystem(equalTo(Paths.get("foo").getFileSystem())), file);
    }

    @Test
    public void testFileContentMatcher() {
        assertMatches("matches file content with a file", PathMatchers.matchesContentOf(file), file);
        assertDoesNotMatch("content of two files with different content won't match", PathMatchers.matchesContentOf(anotherFile), file);
    }

    @Test
    public void testFileContentMatcherDescription() {
        assertMismatchDescription("content was \"\"", PathMatchers.matchesContentOf(anotherFile), file);
    }

    @Test
    public void testAFileWithContent() {
        assertMatches("matches file content", PathMatchers.hasContent(equalTo("")), file);
        assertDoesNotMatch("doesn't match incorrect content", PathMatchers.hasContent(equalTo("world")), file);
    }

    @Test
    public void testAFileWithContentDescription() {
        assertMismatchDescription("content was \"\"", PathMatchers.hasContent(equalTo("world")), file);
    }

   @Override
    protected Matcher<?> createMatcher() {
        return PathMatchers.hasSizeEqualTo(1L);
//        return PathMatchers.isSymbolicLink();
    }
}
