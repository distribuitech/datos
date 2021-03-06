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
package com.datos.vfs.provider.http;

import java.util.Collection;

import com.datos.vfs.FileSystemOptions;
import com.datos.vfs.provider.AbstractFileSystem;
import com.datos.vfs.provider.GenericFileName;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import com.datos.vfs.Capability;
import com.datos.vfs.FileObject;
import com.datos.vfs.provider.AbstractFileName;

/**
 * An HTTP file system.
 */
public class HttpFileSystem
    extends AbstractFileSystem
{
    private final HttpClient client;

    protected HttpFileSystem(final GenericFileName rootName, final HttpClient client,
                             final FileSystemOptions fileSystemOptions)
    {
        super(rootName, null, fileSystemOptions);
        this.client = client;
    }

    /**
     * Adds the capabilities of this file system.
     */
    @Override
    protected void addCapabilities(final Collection<Capability> caps)
    {
        caps.addAll(HttpFileProvider.capabilities);
    }

    protected HttpClient getClient()
    {
        return client;
    }

    /** @since 2.0 */
    @Override
    public void closeCommunicationLink()
    {
        if (getClient() != null)
        {
            final HttpConnectionManager mgr = getClient().getHttpConnectionManager();
            if (mgr instanceof MultiThreadedHttpConnectionManager)
            {
                ((MultiThreadedHttpConnectionManager) mgr).shutdown();
            }
        }
    }

    /**
     * Creates a file object.  This method is called only if the requested
     * file is not cached.
     */
    @Override
    protected FileObject createFile(final AbstractFileName name)
        throws Exception
    {
        return new HttpFileObject(name, this);
    }
}
