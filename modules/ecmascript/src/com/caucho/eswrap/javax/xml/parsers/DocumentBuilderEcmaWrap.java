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
 *   Free SoftwareFoundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.eswrap.javax.xml.parsers;

import com.caucho.es.Call;
import com.caucho.vfs.Path;
import com.caucho.vfs.ReadStream;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.io.InputStream;

public class DocumentBuilderEcmaWrap {
  public static Document parse(DocumentBuilder parser, Call call, int length)
    throws Throwable
  {
    Object obj = call.getArgObject(0, length);
    String systemId = null;

    if (length > 1)
      systemId = call.getArgString(1, length);
    
    if (obj instanceof InputStream)
      return parser.parse((InputStream) obj, systemId);
    else if (obj instanceof Path) {
      Path path = (Path) obj;
      ReadStream is = path.openRead();
      try {
        return parser.parse(is);
      } finally {
        is.close();
      }
    }
    else if (obj instanceof String) {
      return parser.parse((String) obj);
    }
    else
      throw new IOException();
  }
}
