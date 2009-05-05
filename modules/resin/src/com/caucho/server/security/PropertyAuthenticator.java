/*
 * Copyright (c) 1998-2008 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Resin Open Source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, or any warranty
 * of NON-INFRINGEMENT.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.server.security;

import com.caucho.config.*;
import com.caucho.security.BasicPrincipal;
import com.caucho.util.Alarm;
import com.caucho.vfs.Depend;
import com.caucho.vfs.Path;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.logging.*;
import java.io.*;

/**
 * The Property authenticator reads a properties file for authentication.
 *
 * <code><pre>
 * &lt;authenticator url="prop:path=WEB-INF/users.xml"/>
 * </pre></code>
 *
 * <p>The format of the static file is as follows:
 *
 * <code><pre>
 * h.potter=password,user,captain
 * </pre></code>
 *
 * <p>The authenticator can also be configured in the resin-web.xml:
 *
 * <code><pre>
 * &lt;authenticator url="prop:password-digest=none">
 *   &lt;init>
 *     Harry Potter=quidditch,user,captain
 *   &lt;/init>
 * &lt;/authenticator>
 * </pre></code>
 */
public class PropertyAuthenticator extends com.caucho.security.PropertiesAuthenticator {
}
