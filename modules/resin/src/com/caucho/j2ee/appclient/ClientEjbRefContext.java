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
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.j2ee.appclient;

import java.util.ArrayList;

import com.caucho.loader.EnvironmentLocal;

/**
 * Configuration for the ejb-ref.
 *
 * An ejb-ref is used to make an ejb available within the environment
 * in which the ejb-ref is declared.
 */
public class ClientEjbRefContext
{
  private static final EnvironmentLocal<ClientEjbRefContext> _local
     = new EnvironmentLocal<ClientEjbRefContext>();

  private ArrayList<ClientEjbRef> _ejbRefList = new ArrayList<ClientEjbRef>();

  private ClientEjbRefContext()
  {
  }

  public static ClientEjbRefContext createLocal()
  {
    ClientEjbRefContext local = _local.getLevel();

    if (local == null) {
      local = new ClientEjbRefContext();
      _local.set(local);
    }
    
    return local;
  }

  void add(ClientEjbRef ejbRef)
  {
    _ejbRefList.add(ejbRef);
  }

  public String toString()
  {
    return "ClientEjbRefContext[]";
  }
}
