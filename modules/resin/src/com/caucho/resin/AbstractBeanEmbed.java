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

package com.caucho.resin;

import com.caucho.config.*;
import com.caucho.config.types.*;
import com.caucho.config.xml.XmlConfigContext;
import com.caucho.config.program.*;
import com.caucho.server.cluster.*;
import com.caucho.server.dispatch.*;
import com.caucho.server.webapp.*;

import java.util.*;

import javax.enterprise.context.spi.CreationalContext;

/**
 * Abstract base class for bean-like embedding objects.
 */
abstract class AbstractBeanEmbed
{
  private ArrayList<ConfigProgram> _propertyList
    = new ArrayList<ConfigProgram>();

  /**
   * Adds a property.
   */
  public void addProperty(String name, Object value)
  {
    _propertyList.add(new PropertyValueProgram(name, value));
  }

  /**
   * Configures the object
   */
  protected <T> void configure(T bean, CreationalContext<T> env)
  {
    for (ConfigProgram program : _propertyList) {
      program.inject(bean, env);
    }
  }
}
