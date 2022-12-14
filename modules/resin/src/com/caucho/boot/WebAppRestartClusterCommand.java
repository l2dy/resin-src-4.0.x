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

import com.caucho.server.admin.WebAppDeployClient;
import com.caucho.server.deploy.DeployControllerState;
import com.caucho.util.L10N;

public class WebAppRestartClusterCommand extends WebAppCommand
{
  private static final L10N L = new L10N(WebAppRestartClusterCommand.class);

  @Override
  public String getDescription()
  {
    return "restarts a deployed application";
  }

  @Override
  protected int doCommand(WebAppDeployClient deployClient,
                          String tag)
  {
    DeployControllerState result = deployClient.restartCluster(tag);

    System.out.println(L.l("'{0}' is restarted", tag));

    return 0;
  }

  @Override
  public String getUsageArgs()
  {
    return " <name>";
  }

  @Override
  public boolean isDefaultArgsAccepted()
  {
    return true;
  }

  /*
   @Override
  public void usage()
  {
    System.err.println(L.l("usage: bin/resin.sh [-conf <file>] [-server <id>] deploy-restart -user <user> -password <password> [options] <name>"));
    System.err.println(L.l(""));
    System.err.println(L.l("description:"));
    System.err.println(L.l("   restarts application context specified in a <name>"));
    System.err.println(L.l(""));
    System.err.println(L.l("options:"));
    System.err.println(L.l("   -conf <file>          : resin configuration file"));
    System.err.println(L.l("   -server <id>          : id of a server"));
    System.err.println(L.l("   -address <address>    : ip or host name of the server"));
    System.err.println(L.l("   -port <port>          : server http port"));
    System.err.println(L.l("   -user <user>          : user name used for authentication to the server"));
    System.err.println(L.l("   -password <password>  : password used for authentication to the server"));
    System.err.println(L.l("   -host <host>          : virtual host to make application available on"));
    System.err.println(L.l("   -stage <stage>        : name of the stage, for servers running in staging mode"));
    System.err.println(L.l("   -version <version>    : version of application formatted as <major.minor.micro.qualifier>"));
  }
   */
}
