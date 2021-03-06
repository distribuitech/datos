/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datos.vfs.provider.compressed;

import java.util.Collection;

import com.datos.vfs.provider.AbstractFileName;
import com.datos.vfs.provider.AbstractFileSystem;
import com.datos.vfs.Capability;
import com.datos.vfs.FileName;
import com.datos.vfs.FileObject;
import com.datos.vfs.FileSystemException;
import com.datos.vfs.FileSystemOptions;

/**
 * A read-only file system for compressed files.
 */
public abstract class CompressedFileFileSystem
    extends AbstractFileSystem
{
    protected CompressedFileFileSystem(final FileName rootName,
                                       final FileObject parentLayer,
                                       final FileSystemOptions fileSystemOptions)
    {
        super(rootName, parentLayer, fileSystemOptions);
    }

    /**
     * Returns the capabilities of this file system.
     */
    @Override
    protected abstract void addCapabilities(final Collection<Capability> caps);

    /**
     * Creates a file object.
     */
    @Override
    protected abstract FileObject createFile(final AbstractFileName name) throws FileSystemException;
}
