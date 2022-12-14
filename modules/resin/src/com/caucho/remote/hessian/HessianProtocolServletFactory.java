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

package com.caucho.remote.hessian;

import javax.servlet.Servlet;

import com.caucho.hessian.server.HessianServlet;
import com.caucho.remote.ServiceException;
import com.caucho.remote.server.AbstractProtocolServletFactory;

/**
 * Hessian factory for creating @HessianService servlets. 
 */
public class HessianProtocolServletFactory
  extends AbstractProtocolServletFactory
{
  /**
   * Creates a new servlet skeleton based on an API and an object
   *
   * @param serviceClass the remoteApi exposed to the server
   * @param service the managed service object
   */
  @Override
  public Servlet createServlet(Class<?> serviceClass, Object service)
    throws ServiceException
  {
    HessianServlet servlet = new HessianServlet();

    servlet.setHome(service);
    servlet.setHomeAPI(getRemoteAPI(serviceClass));
    
    return servlet;
  }
}
