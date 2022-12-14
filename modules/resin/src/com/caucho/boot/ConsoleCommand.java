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
 * @author Alex Rojkov
 */

package com.caucho.boot;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to start Resin server in console mode
 * bin/resin.sh console -server a
 */
public class ConsoleCommand extends AbstractStartCommand
{
  private static final Logger log
    = Logger.getLogger(ConsoleCommand.class.getName());
  
  @Override
  public String getDescription()
  {
    return "start Resin in console mode";
  }
  
  @Override
  public boolean isConsole()
  {
    return true;
  }
  
  @Override
  public boolean isStart()
  {
    return true;
  }

  @Override
  public boolean isRetry()
  {
    return true;
  }
  
  @Override
  public int retryCount()
  {
    return Integer.MAX_VALUE;
  }

  @Override
  public int doCommand(WatchdogArgs args, WatchdogClient client)
    throws BootArgumentException
  {
    try {
      return client.startConsole().ordinal();
    } catch (IOException e) {
      log.log(Level.FINE, e.getMessage(), e);

      return 1;
    }
  }
}
