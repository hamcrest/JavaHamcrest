package org.hamcrest.io;

import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.junit.Test;

public class FileMatchersTest extends AbstractMatcherTest {

    private File directory;
    private File file;

    @Override
    protected void setUp() throws Exception {
        directory = File.createTempFile("myDir", "");
        directory.delete();
        directory.mkdirs();
        
        file = new File(directory, "myFile");
        file.createNewFile();
    }
    
    @Test
    public void testAnExistingDirectory() {
        assertMatches("matches existing directory", FileMatchers.anExistingDirectory(), directory);
        assertDoesNotMatch("doesn't match existing file", FileMatchers.anExistingDirectory(), file);
        assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingDirectory(), new File("foo"));
    }

    @Test
    public void testAnExistingFileOrDirectory() {
        assertMatches("matches existing file", FileMatchers.anExistingFileOrDirectory(), file);
        assertMatches("matches existing directory", FileMatchers.anExistingFileOrDirectory(), directory);
        assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingFileOrDirectory(), new File("foo"));
    }

    @Test
    public void testAnExistingFile() {
        assertMatches("matches existing file", FileMatchers.anExistingFile(), file);
        assertDoesNotMatch("doesn't match existing directory", FileMatchers.anExistingFile(), directory);
        assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingFile(), new File("foo"));
    }

    @Test
    public void testAReadableFile() {
        file.setReadable(true);
        assertMatches("matches readable file", FileMatchers.aReadableFile(), file);
        if (file.setReadable(false)) {
            assertDoesNotMatch("doesn't match unreadable file", FileMatchers.aReadableFile(), file);
        }
    }

    @Test
    public void testAWritableFile() {
        assertMatches("matches writable file", FileMatchers.aWritableFile(), file);
        file.setWritable(false);
        assertDoesNotMatch("doesn't match unwritable file", FileMatchers.aWritableFile(), file);
    }

    @Test
    public void testAFileWithSizeLong() {
        assertMatches("matches file size", FileMatchers.aFileWithSize(0L), file);
        file.setWritable(false);
        assertDoesNotMatch("doesn't match incorrect file size", FileMatchers.aFileWithSize(34L), file);
    }

    @Test
    public void testAFileWithSizeMatcherOfLong() {
        assertMatches("matches file size", FileMatchers.aFileWithSize(equalTo(0L)), file);
        file.setWritable(false);
        assertDoesNotMatch("doesn't match incorrect file size", FileMatchers.aFileWithSize(equalTo(23L)), file);
    }

    @Test
    public void testAFileNamed() {
        assertMatches("matches file name", FileMatchers.aFileNamed(equalTo(file.getName())), file);
        file.setWritable(false);
        assertDoesNotMatch("doesn't match incorrect file name", FileMatchers.aFileNamed(equalTo("foo")), file);
    }

    @Test
    public void testAFileWithCanonicalPath() throws Exception {
        assertMatches("matches file canonical path", FileMatchers.aFileWithCanonicalPath(equalTo(file.getCanonicalPath())), file);
        file.setWritable(false);
        assertDoesNotMatch("doesn't match incorrect canonical path", FileMatchers.aFileWithCanonicalPath(equalTo("foo")), file);
    }

    @Test
    public void testAFileWithAbsolutePath() {
        assertMatches("matches file absolute path", FileMatchers.aFileWithAbsolutePath(equalTo(file.getAbsolutePath())), file);
        file.setWritable(false);
        assertDoesNotMatch("doesn't match incorrect absolute path", FileMatchers.aFileWithAbsolutePath(equalTo("foo")), file);
    }

    @Override
    protected Matcher<?> createMatcher() {
        return FileMatchers.aFileWithSize(1L);
    }

}
