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

package com.caucho.sql.spy;

import com.caucho.util.L10N;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Spying on a connection.
 */
public class SpyXAConnection extends SpyPooledConnection
  implements javax.sql.XAConnection {
  protected final static Logger log
    = Logger.getLogger(SpyXAConnection.class.getName());
  protected final static L10N L = new L10N(SpyXAConnection.class);

  // The underlying connection
  private XAConnection _conn;

  /**
   * Creates a new SpyConnection.
   */
  public SpyXAConnection(XAConnection conn, String id)
  {
    super(conn, id);

    _conn = conn;
  }

  /**
   * Returns the XAResource.
   */
  @Override
  public XAResource getXAResource()
    throws SQLException
  {
    return new SpyXAResource(_id, _conn.getXAResource());
  }

  public String toString()
  {
    return "SpyXAConnection[id=" + _id + ",conn=" + _conn + "]";
  }
}
