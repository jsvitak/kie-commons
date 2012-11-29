/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.commons.java.nio.fs.file;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.kie.commons.java.nio.file.FileStore;
import org.kie.commons.java.nio.file.FileSystem;
import org.kie.commons.java.nio.file.Path;
import org.kie.commons.java.nio.file.spi.FileSystemProvider;
import org.kie.commons.java.nio.base.GeneralPathImpl;
import org.kie.commons.java.nio.fs.file.SimpleUnixFileSystem;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimpleUnixFileSystemTest {

    final FileSystemProvider fsProvider = mock(FileSystemProvider.class);

    @Test
    public void simpleTests() {
        final FileSystem fileSystem = new SimpleUnixFileSystem(fsProvider, "/");

        assertThat(fileSystem.isOpen()).isTrue();
        assertThat(fileSystem.isReadOnly()).isFalse();
        assertThat(fileSystem.getSeparator()).isEqualTo(System.getProperty("file.separator"));
        assertThat(fileSystem.provider()).isEqualTo(fsProvider);
        assertThat(fileSystem.supportedFileAttributeViews()).isNotEmpty().hasSize(1).contains("basic");

        assertThat(fileSystem.getPath("/path/to/file.txt")).isNotNull().isEqualTo(GeneralPathImpl.create(fileSystem, "/path/to/file.txt", false));
        assertThat(fileSystem.getPath("/path/to/file.txt", null)).isNotNull().isEqualTo(GeneralPathImpl.create(fileSystem, "/path/to/file.txt", false));
        assertThat(fileSystem.getPath("/path", "to", "file.txt")).isNotNull().isEqualTo(GeneralPathImpl.create(fileSystem, "/path/to/file.txt", false));

        try {
            fileSystem.close();
            fail("can't close this fileSystem");
        } catch (UnsupportedOperationException ex) {
        }

        assertThat(fileSystem.getFileStores()).isNotNull().hasSize(1);
        assertThat(fileSystem.getFileStores().iterator().next().name()).isEqualTo("/");

        assertThat(fileSystem.getRootDirectories()).isNotNull().hasSize(1);
        assertThat(fileSystem.getRootDirectories().iterator().next().toString()).isEqualTo("/");
        assertThat(fileSystem.getRootDirectories().iterator().next().isAbsolute()).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCOnstructorPath() {
        new SimpleUnixFileSystem(fsProvider, "home");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPathMatcherUnsupportedOp() {
        new SimpleUnixFileSystem(fsProvider, "/").getPathMatcher("*.*");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getUserPrincipalLookupServiceUnsupportedOp() {
        new SimpleUnixFileSystem(fsProvider, "/").getUserPrincipalLookupService();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void newWatchServiceUnsupportedOp() {
        new SimpleUnixFileSystem(fsProvider, "/").newWatchService();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeElementFromRootIteratorUnsupportedOp() {
        new SimpleUnixFileSystem(fsProvider, "/").getRootDirectories().iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void invalidElementFromRootIterator() {
        final Iterator<Path> iterator = new SimpleUnixFileSystem(fsProvider, "/").getRootDirectories().iterator();
        try {
            iterator.next();
        } catch (Exception e) {
            fail("first is valid");
        }
        iterator.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeElementFromFStoreIteratorUnsupportedOp() {
        new SimpleUnixFileSystem(fsProvider, "/").getFileStores().iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void invalidElementFromFStoreIterator() {
        final Iterator<FileStore> iterator = new SimpleUnixFileSystem(fsProvider, "/").getFileStores().iterator();
        try {
            iterator.next();
        } catch (Exception e) {
            fail("first is valid");
        }
        iterator.next();
    }

}