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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.datos.vfs.FileSystemConfigBuilder;
import org.apache.commons.httpclient.HttpClient;
import com.datos.vfs.Capability;
import com.datos.vfs.FileName;
import com.datos.vfs.FileSystem;
import com.datos.vfs.FileSystemException;
import com.datos.vfs.FileSystemOptions;
import com.datos.vfs.UserAuthenticationData;
import com.datos.vfs.provider.AbstractOriginatingFileProvider;
import com.datos.vfs.provider.GenericFileName;
import com.datos.vfs.util.UserAuthenticatorUtils;

/**
 * An HTTP provider that uses commons-httpclient.
 */
public class HttpFileProvider
    extends AbstractOriginatingFileProvider
{
    /** Authenticator information. */
    public static final UserAuthenticationData.Type[] AUTHENTICATOR_TYPES = new UserAuthenticationData.Type[]
        {
            UserAuthenticationData.USERNAME, UserAuthenticationData.PASSWORD
        };

    static final Collection<Capability> capabilities = Collections.unmodifiableCollection(Arrays.asList(new Capability[]
    {
        Capability.GET_TYPE,
        Capability.READ_CONTENT,
        Capability.URI,
        Capability.GET_LAST_MODIFIED,
        Capability.ATTRIBUTES,
        Capability.RANDOM_ACCESS_READ,
        Capability.DIRECTORY_READ_CONTENT,
    }));

    /**
     * Constructs a new provider.
     */
    public HttpFileProvider()
    {
        super();
        setFileNameParser(HttpFileNameParser.getInstance());
    }

    /**
     * Creates a {@link FileSystem}.
     */
    @Override
    protected FileSystem doCreateFileSystem(final FileName name, final FileSystemOptions fileSystemOptions)
        throws FileSystemException
    {
        // Create the file system
        final GenericFileName rootName = (GenericFileName) name;

        UserAuthenticationData authData = null;
        HttpClient httpClient;
        try
        {
            authData = UserAuthenticatorUtils.authenticate(fileSystemOptions, AUTHENTICATOR_TYPES);

            httpClient = HttpClientFactory.createConnection(
                rootName.getScheme(),
                rootName.getHostName(),
                rootName.getPort(),
                UserAuthenticatorUtils.toString(UserAuthenticatorUtils.getData(authData,
                    UserAuthenticationData.USERNAME, UserAuthenticatorUtils.toChar(rootName.getUserName()))),
                UserAuthenticatorUtils.toString(UserAuthenticatorUtils.getData(authData,
                    UserAuthenticationData.PASSWORD, UserAuthenticatorUtils.toChar(rootName.getPassword()))),
                fileSystemOptions);
        }
        finally
        {
            UserAuthenticatorUtils.cleanup(authData);
        }

        return new HttpFileSystem(rootName, httpClient, fileSystemOptions);
    }

    @Override
    public FileSystemConfigBuilder getConfigBuilder()
    {
        return HttpFileSystemConfigBuilder.getInstance();
    }

    @Override
    public Collection<Capability> getCapabilities()
    {
        return capabilities;
    }
}
