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
 * @author Emil Ong
 */

package com.caucho.mule;

import com.caucho.config.ConfigException;
import com.caucho.util.L10N;

import org.mule.MuleManager;
import org.mule.config.builders.MuleXmlBuilderContextListener;
import org.mule.umo.UMOException;
import org.mule.umo.manager.UMOManager;

import javax.servlet.ServletContextEvent;

public class MuleResinContextListener extends MuleXmlBuilderContextListener
{
  private static final L10N L = new L10N(MuleResinContextListener.class);

  private ResinContainerContext _containerContext = new ResinContainerContext();
  private UMOManager _muleManager = MuleManager.getInstance();
  
  public void contextInitialized(ServletContextEvent event)
  {
    try {
      _muleManager.setContainerContext(_containerContext);
    }
    catch (UMOException e) {
      throw new ConfigException(L.l("Exception while configuring Mule"), e);
    }

    super.contextInitialized(event);
  }
}
