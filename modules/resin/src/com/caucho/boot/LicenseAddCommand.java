/*
 * Copyright (c) 1998-2012 Caucho Technology -- all rights reserved
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
 */

package com.caucho.boot;

import java.io.IOException;

import com.caucho.config.ConfigException;
import com.caucho.server.admin.ManagerClient;
import com.caucho.util.CharBuffer;
import com.caucho.util.L10N;
import com.caucho.vfs.Path;
import com.caucho.vfs.ReadStream;
import com.caucho.vfs.Vfs;

public class LicenseAddCommand extends AbstractManagementCommand
{
  private static final L10N L = new L10N(LicenseAddCommand.class);

  public LicenseAddCommand()
  {
    addValueOption("license", "license file", "path to license file to add (required)");
    addValueOption("to", "filename","file name license will be written to (defaults to name of license file)");
    addFlagOption("overwrite", "overwrite existing license file if exists");
    addFlagOption("restart", "restart Resin after license is added");
  }

  @Override
  public String getDescription()
  {
    return "adds a Resin-Professional license to an installation";
  }

  @Override
  public int doCommand(WatchdogArgs args,
                       WatchdogClient client,
                       ManagerClient managerClient)
  {
    String licensePath = args.getArg("-license");
    
    if (licensePath == null && args.getTailArgs().size() == 1) {
      licensePath = args.getTailArgs().get(0);
    }
    
    if (licensePath == null) {
      System.err.println(L.l("-license is required"));
      usage();
      return 1;
    }
    
    String fileName = args.getArg("-to");
    
    boolean overwrite = args.hasOption("-overwrite");
    boolean restart = args.hasOption("-restart");
    
    Path path = Vfs.lookup(licensePath);
    
    if (fileName == null) {
      fileName = path.getTail();
    }
    
    if (! fileName.endsWith(".license")) {
      System.err.println(L.l("license file name must end with .license"));
      return 1;
    }
      
    String licenseContent = null;
    
    ReadStream is = null;    
    try {
      is = path.openRead();
    } catch (IOException e) {
      throw new ConfigException(L.l("Could not open {0} for read: {1}",
                                    path,
                                    e.toString()), e);
    }

    CharBuffer cb = new CharBuffer();
    try {
      int ch;
      while ((ch = is.read()) >= 0)
        cb.append((char) ch);
      
      licenseContent = cb.toString();
    } catch (IOException e) {
      throw new ConfigException(L.l("Failed to read {0}: {1}",
                                    path,
                                    e.toString()), e);
    } finally {
      if (cb != null)
        cb.close();
      if (is != null)
        is.close();
    }
    
    if (licenseContent == null || licenseContent.isEmpty()) {
      throw new ConfigException(L.l("Failed to read {0}: empty", path));
    }
    
    String message = managerClient.addLicense(licenseContent, 
                                              fileName,
                                              overwrite,
                                              restart);

    System.out.println(message);
    
    return 0;
  }

  /*
  @Override
  public void usage()
  {
    System.err.println(L.l(
      "usage: bin/resin.sh [-conf <file>] license-add -license <license file> [-to <filename>] [-overwrite] [-restart]"));
    System.err.println();
    System.err.println(L.l("description:"));
    System.err.println(L.l("   copies a license file to the appropriate license directory"));
    System.err.println();
    System.err.println(L.l("options:"));
    System.err.println(L.l("   -license <license file> : path to license file to add (required)"));
    System.err.println(L.l("   -to <filename>          : file name license will be written to (defaults to name of license file)"));
    System.err.println(L.l("   -overwrite              : overwrite existing license file if exists"));
    System.err.println(L.l("   -restart                : restart Resin after license is added"));
  }
   */
}
