/*
 * Copyright (c) 1998-2018 Caucho Technology -- all rights reserved
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
 *   Free SoftwareFoundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.vfs;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract socket to handle both normal sockets and bin/resin sockets.
 */
public class SSLFactoryBase
{
  private static final Logger log
    = Logger.getLogger(SSLFactoryBase.class.getName());

  private static final Class<?> _sslFactoryType;
  
  static {
    Class<?> sslFactoryType = null;
    
    try {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      sslFactoryType = Class.forName("com.caucho.vfs.OpenSSLFactory", false, loader);
    } catch (Throwable e) {
      log.log(Level.FINER, e.toString(), e);
    }
    
    _sslFactoryType = sslFactoryType;
  }
}

