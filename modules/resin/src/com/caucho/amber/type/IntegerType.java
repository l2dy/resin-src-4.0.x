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
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.amber.type;

import com.caucho.amber.manager.AmberPersistenceUnit;
import com.caucho.java.JavaWriter;
import com.caucho.util.L10N;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Represents a java.util.Integer type
 */
public class IntegerType extends AmberType {
  private static final L10N L = new L10N(IntegerType.class);

  private static final IntegerType INTEGER_TYPE = new IntegerType();

  private IntegerType()
  {
  }

  /**
   * Returns the singleton Integer type.
   */
  public static IntegerType create()
  {
    return INTEGER_TYPE;
  }

  /**
   * Returns the type name.
   */
  public String getName()
  {
    return "java.lang.Integer";
  }

  /**
   * Returns true for a numeric type.
   */
  public boolean isNumeric()
  {
    return true;
  }

  /**
   * Generates the type for the table.
   */
  public String generateCreateColumnSQL(AmberPersistenceUnit manager, int length, int precision, int scale)
  {
    return manager.getCreateColumnSQL(Types.INTEGER, length, precision, scale);
  }

  /**
   * Generates a string to load the property.
   */
  public int generateLoad(JavaWriter out, String rs,
                          String indexVar, int index)
    throws IOException
  {
    out.print("com.caucho.amber.type.IntegerType.toInt(" +
              rs + ".getInt(" + indexVar + " + " + index + "), " +
              rs + ".wasNull())");

    return index + 1;
  }

  /**
   * Generates a string to load the property.
   */
  public int generateLoadNative(JavaWriter out, int index)
    throws IOException
  {
    out.print("com.caucho.amber.type.IntegerType.toInt("
              + "rs.getInt(columnNames[" + index + "]),"
              + "rs.wasNull())");

    return index + 1;
  }

  /**
   * Generates a string to set the property.
   */
  public void generateSet(JavaWriter out, String pstmt,
                          String index, String value)
    throws IOException
  {
    out.println("if (" + value + " == null)");
    out.println("  " + pstmt + ".setNull(" + index + "++, java.sql.Types.INTEGER);");
    out.println("else");
    out.println("  " + pstmt + ".setInt(" + index + "++, " +
                value + ".intValue());");
  }

  /**
   * Generates a string to set the property.
   */
  public void generateSetNull(JavaWriter out, String pstmt,
                              String index)
    throws IOException
  {
    out.println(pstmt + ".setNull(" + index + "++, java.sql.Types.INTEGER);");
  }

  /**
   * Generates a string to set the property.
   */
  public void generateSetVersion(JavaWriter out,
                                 String pstmt,
                                 String index,
                                 String value)
    throws IOException
  {
    out.println("if (" + value + " == null)");
    out.println("  " + pstmt + ".setInt(" + index + "++, 1);");
    out.println("else");
    out.println("  " + pstmt + ".setInt(" + index + "++, " +
                value + ".intValue() + 1);");
  }

  /**
   * Generates the increment version.
   */
  public String generateIncrementVersion(String value)
    throws IOException
  {
    return value + ".intValue() + 1";
  }

  /**
   * Converts to an object.
   */
  public Object toObject(long value)
  {
    return new Integer((int) value);
  }

  /**
   * Converts a value to a int.
   */
  public static Integer toInt(int value, boolean wasNull)
  {
    if (wasNull)
      return null;
    else
      return new Integer(value);
  }

  /**
   * Sets the value.
   */
  public void setParameter(PreparedStatement pstmt, int index, Object value)
    throws SQLException
  {
    if (value == null)
      pstmt.setNull(index, Types.INTEGER);
    else
      pstmt.setInt(index, ((Integer) value).intValue());
  }

  /**
   * Gets the value.
   */
  public Object getObject(ResultSet rs, int index)
    throws SQLException
  {
    int value = rs.getInt(index);

    return rs.wasNull() ? null : new Integer(value);
  }
}
