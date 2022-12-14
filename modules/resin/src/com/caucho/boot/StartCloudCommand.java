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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.caucho.VersionFactory;
import com.caucho.config.ConfigException;
import com.caucho.util.L10N;


/**
 * Command to start Resin server
 * bin/resin.sh start-cloud
 */
public class StartCloudCommand extends AbstractStartCommand
{
  private static final L10N L = new L10N(StartCloudCommand.class);
  private static final Logger log
    = Logger.getLogger(StartCloudCommand.class.getName());
  
  @Override
  public String getDescription()
  {
    return "starts all servers listening to the machine's IP interfaces";
  }

  @Override
  protected WatchdogClient findLocalClient(ResinBoot boot, WatchdogArgs args)
  {
    return findLocalClientImpl(boot, args);
  }

  @Override
  protected WatchdogClient findWatchdogClient(ResinBoot boot, WatchdogArgs args)
  {
    return findWatchdogClientImpl(boot, args);
  }

  @Override
  public int doCommand(WatchdogArgs args, WatchdogClient client)
    throws BootArgumentException
  {
    try {
      client.startAllWatchdog(args.getArgv(), true);

      System.out.println(L.l("Resin/{0} started{1} with watchdog at {2}:{3}",
                             VersionFactory.getVersion(),
                             getServerUsageArg(args, client.getId()),
                             client.getWatchdogAddress(),
                             client.getWatchdogPort()));
    } catch (Exception e) {
      String eMsg;

      if (e instanceof ConfigException)
        eMsg = e.getMessage();
      else
        eMsg = e.toString();

      System.out.println(L.l(
        "Resin/{0} can't start-cloud for watchdog at {1}:{2}.\n  {3}",
        VersionFactory.getVersion(),
        client.getWatchdogAddress(),
        client.getWatchdogPort(),
        eMsg));

      log.log(Level.FINE, e.toString(), e);

      System.exit(1);
    }

    return 0;
  }

  @Override
  public void doWatchdogStart(WatchdogManager manager)
  {
    WatchdogArgs args = manager.getArgs();
    
    String serverId = args.getClientServerId();
    
    if (serverId != null && args.isElasticServer()) {
      manager.startServer(serverId, args.getArgv());
      return;
    }
    
    ArrayList<WatchdogClient> clientList;
    
    do {
      clientList = manager.getManagerConfig().findLocalClients(null);
      
      if (clientList.size() == 0) {
        try {
          log.info("No local IP address found, waiting...");
          Thread.sleep(10000);
        } catch (Exception e) {
        }
      }
    } while (clientList.size() == 0);

    for (WatchdogClient client : clientList) {
      try {
        manager.startServer(client.getId(), args.getArgv());
      } catch (Exception e) {
        String eMsg;

        if (e instanceof ConfigException)
          eMsg = e.getMessage();
        else
          eMsg = e.toString();

        System.out.println(L.l("Resin/{0} can't start -server '{1}' for watchdog at {2}:{3}.\n  {4}",
                                 VersionFactory.getVersion(),
                                 client.getId(),
                                 client.getWatchdogAddress(),
                                 client.getWatchdogPort(),
                                 eMsg));

        log.log(Level.FINE, e.toString(), e);
      }
    }
  }
}
