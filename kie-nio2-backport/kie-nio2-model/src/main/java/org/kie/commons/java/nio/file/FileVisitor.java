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

package org.kie.commons.java.nio.file;

import org.kie.commons.java.nio.IOException;
import org.kie.commons.java.nio.file.attribute.BasicFileAttributes;

public interface FileVisitor<T> {

    FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) throws IOException;

    FileVisitResult visitFile(T file, BasicFileAttributes attrs) throws IOException;

    FileVisitResult visitFileFailed(T file, IOException exc) throws IOException;

    FileVisitResult postVisitDirectory(T dir, IOException exc) throws IOException;
}