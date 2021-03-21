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

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileMatchersTest extends AbstractMatcherTest {

    private File directory;
    private File file;

    @Override
    protected void setUp() throws IOException {
        directory = File.createTempFile("myDir", "");
        assertTrue("deleting " + directory, directory.delete());
        assertTrue("mkdir " + directory, directory.mkdirs());
        
        file = new File(directory, "myFile");
        file.createNewFile();
    }
    
    public void testAnExistingDirectory() {
        assertMatches("matches existing directory", FileMatchers.anExistingDirectory(), directory);
        assertDoesNotMatch("doesn't match existing file", FileMatchers.anExistingDirectory(), file);
        assertMismatchDescription("'foo' is not a directory", FileMatchers.anExistingDirectory(), new File("foo"));
    }

    public void testAnExistingFileOrDirectory() {
        assertMatches("matches existing file", FileMatchers.anExistingFileOrDirectory(), file);
        assertMatches("matches existing directory", FileMatchers.anExistingFileOrDirectory(), directory);
        assertMismatchDescription("'foo' does not exist", FileMatchers.anExistingFileOrDirectory(), new File("foo"));
    }

    public void testAnExistingFile() {
        assertMatches("matches existing file", FileMatchers.anExistingFile(), file);
        assertDoesNotMatch("doesn't match existing directory", FileMatchers.anExistingFile(), directory);
        assertMismatchDescription("'foo' is not a file", FileMatchers.anExistingFile(), new File("foo"));
    }

    public void testAReadableFile() { // Not all OSes will allow setting readability so have to be forgiving here.
        file.setReadable(true);
        assertMatches("matches readable file", FileMatchers.aReadableFile(), file);

        if (file.setReadable(false)) {
            assertDoesNotMatch("doesn't match unreadable file", FileMatchers.aReadableFile(), file);
        }
    }

    public void testAWritableFile() {
        assertMatches("matches writable file", FileMatchers.aWritableFile(), file);

        assertTrue("set writable off " + file, file.setWritable(false));
        assertDoesNotMatch("doesn't match unwritable file", FileMatchers.aWritableFile(), file);
    }

    public void testAFileWithSizeLong() {
        assertMatches("matches file size", FileMatchers.aFileWithSize(0L), file);
        assertDoesNotMatch("doesn't match incorrect file size", FileMatchers.aFileWithSize(34L), file);
    }

    public void testAFileWithSizeMatcherOfLong() {
        assertMatches("matches file size", FileMatchers.aFileWithSize(equalTo(0L)), file);
        assertDoesNotMatch("doesn't match incorrect file size", FileMatchers.aFileWithSize(equalTo(23L)), file);
    }

    public void testAFileNamed() {
        assertMatches("matches file name", FileMatchers.aFileNamed(equalTo(file.getName())), file);
        assertDoesNotMatch("doesn't match incorrect file name", FileMatchers.aFileNamed(equalTo("foo")), file);
    }

    public void testAFileWithCanonicalPath() throws Exception {
        assertMatches("matches file canonical path", FileMatchers.aFileWithCanonicalPath(equalTo(file.getCanonicalPath())), file);
        assertDoesNotMatch("doesn't match incorrect canonical path", FileMatchers.aFileWithCanonicalPath(equalTo("foo")), file);
    }

    public void testAFileWithAbsolutePath() {
        assertMatches("matches file absolute path", FileMatchers.aFileWithAbsolutePath(equalTo(file.getAbsolutePath())), file);
        assertDoesNotMatch("doesn't match incorrect absolute path", FileMatchers.aFileWithAbsolutePath(equalTo("foo")), file);
    }

    @Override
    protected Matcher<?> createMatcher() {
        return FileMatchers.aFileWithSize(1L);
    }

}
