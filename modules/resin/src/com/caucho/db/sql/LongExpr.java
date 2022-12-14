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

package com.caucho.db.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

class LongExpr extends Expr {
  private long _value;

  LongExpr(long value)
  {
    _value = value;
  }

  /**
   * Returns the type of the expression.
   */
  public Class getType()
  {
    return long.class;
  }

  /**
   * Returns the cost based on the given FromList.
   */
  public long subCost(ArrayList<FromItem> fromList)
  {
    return 0;
  }

  /**
   * Evaluates the expression as a long
   *
   * @param row the current data tuple
   *
   * @return the long value
   */
  public long evalLong(QueryContext context)
    throws SQLException
  {
    return _value;
  }

  /**
   * Evaluates the expression as a date
   *
   * @param row the current data tuple
   *
   * @return the date value
   */
  public long evalDate(QueryContext context)
    throws SQLException
  {
    return _value;
  }

  /**
   * Evaluates the expression as a double
   *
   * @param row the current data tuple
   *
   * @return the double value
   */
  public double evalDouble(QueryContext context)
    throws SQLException
  {
    return _value;
  }

  /**
   * Evaluates the expression as a string
   *
   * @param row the current data tuple
   *
   * @return the string value
   */
  public String evalString(QueryContext context)
    throws SQLException
  {
    return String.valueOf(_value);
  }

  /**
   * Evaluates the expression, writing to the result stream.
   *
   * @param context the query context
   * @param result the output result
   */
  public void evalToResult(QueryContext context, SelectResult result)
  {
    result.writeLong(_value);
  }

  public String toString()
  {
    return String.valueOf(_value);
  }
}
