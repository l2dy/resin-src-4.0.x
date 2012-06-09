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
 * @author Nam Nguyen
 */

package com.caucho.quercus.lib.spl;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.vfs.Path;

public class RecursiveDirectoryIterator
  extends FilesystemIterator
  implements RecursiveIterator
{
  public RecursiveDirectoryIterator(Env env,
                                    StringValue fileName,
                                    @Optional("-1") int flags)
  {
    super(env, fileName, flags);
  }

  protected RecursiveDirectoryIterator(Path path, int flags)
  {
    super(path, flags);
  }

  @Override
  public boolean hasChildren(Env env)
  {
    DirectoryIterator current = getCurrent(env);

    if (current == null) {
      return false;
    }

    return current.isDir(env);
  }

  @Override
  public RecursiveIterator getChildren(Env env)
  {
    return (RecursiveIterator) getCurrent(env);
  }

  @Override
  protected DirectoryIterator createCurrentIterator(Env env, Path path)
  {
    if (path.isDirectory()) {
      return new RecursiveDirectoryIterator(path, getFlags());
    }
    else {
      return super.createCurrentIterator(env, path);
    }
  }
}
