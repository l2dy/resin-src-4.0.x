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

package com.caucho.xsl.java;

import com.caucho.java.JavaWriter;
import com.caucho.xml.QName;
import com.caucho.xsl.XslParseException;

/**
 * Returns the value of an expression.
 */
public class XslOutput extends XslNode implements XslTopNode {
  /**
   * Returns the tag name.
   */
  public String getTagName()
  {
    return "xsl:output";
  }
  
  /**
   * Adds an attribute.
   */
  public void addAttribute(QName name, String value)
    throws XslParseException
  {
    if (name.getName().equals("indent")) {
      if (value.equals("yes"))
        _gen.setOutputAttribute(name.getName(), value);
      else if (value.equals("no"))
        _gen.setOutputAttribute(name.getName(), value);
      else
        throw error(L.l("'{0}' is an illegal indent value.  <xsl:output> indent must be 'yes' or 'no'.",
                        value));
    }
    else if (name.getName().equals("disable-output-escaping") ||
             name.getName().equals("resin:disable-output-escaping")) {
      if (value.equals("yes") || value.equals("true"))
        _gen.setDisableOutputEscaping(true);
      else if (value.equals("no") || value.equals("false"))
        _gen.setDisableOutputEscaping(false);
      else
        throw error(L.l("'{0}' is an illegal disable-output-escaping value.  <xsl:output> disable-output-escaping must be 'yes' or 'no'.",
                        value));
    }
    else
      _gen.setOutputAttribute(name.getName(), value);
  }

  /**
   * Generates the code for the tag
   *
   * @param out the output writer for the generated java.
   */
  public void generate(JavaWriter out)
    throws Exception
  {
  }
}
