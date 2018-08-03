package org.hamcrest.io;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("ResultOfMethodCallIgnored")
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
    
    public void testAnExistingDirectory() {
        assertMatches("matches existing directory", FileMatchers.anExistingDirectory(), directory);
        assertDoesNotMatch("doesn't match existing file", FileMatchers.anExistingDirectory(), file);
        assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingDirectory(), new File("foo"));
    }

    public void testAnExistingFileOrDirectory() {
        assertMatches("matches existing file", FileMatchers.anExistingFileOrDirectory(), file);
        assertMatches("matches existing directory", FileMatchers.anExistingFileOrDirectory(), directory);
        assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingFileOrDirectory(), new File("foo"));
    }

    public void testAnExistingFile() {
        assertMatches("matches existing file", FileMatchers.anExistingFile(), file);
        assertDoesNotMatch("doesn't match existing directory", FileMatchers.anExistingFile(), directory);
        assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingFile(), new File("foo"));
    }

    public void testAReadableFile() throws IOException {
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
        perms.add(PosixFilePermission.OWNER_READ);
        Files.setPosixFilePermissions(file.toPath(), perms);
        assertMatches("matches readable file", FileMatchers.aReadableFile(), file);
        perms = Files.getPosixFilePermissions(file.toPath());
        perms.remove(PosixFilePermission.OWNER_READ);
        Files.setPosixFilePermissions(file.toPath(), perms);
        assertDoesNotMatch("doesn't match unreadable file", FileMatchers.aReadableFile(), file);
    }

    public void testAWritableFile() throws IOException {
        assertMatches("matches writable file", FileMatchers.aWritableFile(), file);
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
        perms.remove(PosixFilePermission.OWNER_WRITE);
        Files.setPosixFilePermissions(file.toPath(), perms);
        assertDoesNotMatch("doesn't match unwritable file", FileMatchers.aWritableFile(), file);
    }

    public void testAFileWithSizeLong() throws IOException {
        assertMatches("matches file size", FileMatchers.aFileWithSize(0L), file);
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
        perms.remove(PosixFilePermission.OWNER_WRITE);
        Files.setPosixFilePermissions(file.toPath(), perms);
        assertDoesNotMatch("doesn't match incorrect file size", FileMatchers.aFileWithSize(34L), file);
    }

    public void testAFileWithSizeMatcherOfLong() throws IOException {
        assertMatches("matches file size", FileMatchers.aFileWithSize(equalTo(0L)), file);
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
        perms.remove(PosixFilePermission.OWNER_WRITE);
        Files.setPosixFilePermissions(file.toPath(), perms);
        assertDoesNotMatch("doesn't match incorrect file size", FileMatchers.aFileWithSize(equalTo(23L)), file);
    }

    public void testAFileNamed() throws IOException {
        assertMatches("matches file name", FileMatchers.aFileNamed(equalTo(file.getName())), file);
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
        perms.remove(PosixFilePermission.OWNER_WRITE);
        Files.setPosixFilePermissions(file.toPath(), perms);
        assertDoesNotMatch("doesn't match incorrect file name", FileMatchers.aFileNamed(equalTo("foo")), file);
    }

    public void testAFileWithCanonicalPath() throws Exception {
        assertMatches("matches file canonical path", FileMatchers.aFileWithCanonicalPath(equalTo(file.getCanonicalPath())), file);
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
        perms.remove(PosixFilePermission.OWNER_WRITE);
        Files.setPosixFilePermissions(file.toPath(), perms);
        assertDoesNotMatch("doesn't match incorrect canonical path", FileMatchers.aFileWithCanonicalPath(equalTo("foo")), file);
    }

    public void testAFileWithAbsolutePath() throws IOException {
        assertMatches("matches file absolute path", FileMatchers.aFileWithAbsolutePath(equalTo(file.getAbsolutePath())), file);
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
        perms.remove(PosixFilePermission.OWNER_WRITE);
        Files.setPosixFilePermissions(file.toPath(), perms);
        assertDoesNotMatch("doesn't match incorrect absolute path", FileMatchers.aFileWithAbsolutePath(equalTo("foo")), file);
    }

    @Override
    protected Matcher<?> createMatcher() {
        return FileMatchers.aFileWithSize(1L);
    }

}
