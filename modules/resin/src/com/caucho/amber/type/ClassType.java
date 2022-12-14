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

import com.caucho.amber.AmberException;
import com.caucho.java.JavaWriter;
import com.caucho.util.L10N;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A property containing a class
 */
public class ClassType extends AmberType {
  private static final L10N L = new L10N(ClassType.class);

  private static final ClassType CLASS_TYPE = new ClassType();

  private ClassType()
  {
  }

  /**
   * Returns the singleton Class type.
   */
  public static ClassType create()
  {
    return CLASS_TYPE;
  }

  /**
   * Returns the type name.
   */
  public String getName()
  {
    return "java.lang.Class";
  }

  /**
   * Generates a string to load the property.
   */
  public int generateLoad(JavaWriter out, String rs,
                          String indexVar, int index)
    throws IOException
  {
    out.print("com.caucho.amber.type.ClassType.toClass(" + rs + ".getString(" + indexVar + " + " + index + "))");

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
    out.println("  " + pstmt + ".setNull(" + index + "++, java.sql.Types.VARCHAR);");
    out.println("else");
    out.println("  " + pstmt + ".setString(" + index + "++, " + value + ".getName());");
  }

  /**
   * Generates a string to set the property.
   */
  public void generateSetNull(JavaWriter out, String pstmt, String index)
    throws IOException
  {
    out.println(pstmt + ".setNull(" + index + "++, java.sql.Types.VARCHAR);");
  }

  /**
   * Gets the value.
   */
  public static Class toClass(String name)
    throws SQLException
  {
    if (name == null)
      return null;

    try {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      
      return Class.forName(name, false, loader);
    } catch (ClassNotFoundException e) {
      throw new AmberException(e);
    }
  }

  /**
   * Gets the value.
   */
  public Object getObject(ResultSet rs, int index)
    throws SQLException
  {
    return toClass(rs.getString(index));
  }
}
