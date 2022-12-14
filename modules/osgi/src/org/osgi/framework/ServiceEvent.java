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

package org.osgi.framework;

import java.util.EventListener;
import java.util.EventObject;

/**
 * A service event
 */
public class ServiceEvent extends EventObject
{
  public static final int REGISTERED = 0x00000001;
  public static final int MODIFIED = 0x00000002;
  public static final int UNREGISTERING = 0x00000004;
  
  private final int _type;

  public ServiceEvent(int type, ServiceReference service)
  {
    super(service);

    _type = type;
  }

  public ServiceReference getServiceReference()
  {
    return (ServiceReference) getSource();
  }

  public int getType()
  {
    return _type;
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getName()).append("[");
    
    switch (_type) {
    case REGISTERED:
      sb.append("registered");
      break;
      
    case MODIFIED:
      sb.append("modified");
      break;
      
    case UNREGISTERING:
      sb.append("unregistering");
      break;
      
    default:
      sb.append("type=").append(getType());
      break;
    }

    sb.append(",").append(getSource()).append("]");

    return sb.toString();
  }
}
