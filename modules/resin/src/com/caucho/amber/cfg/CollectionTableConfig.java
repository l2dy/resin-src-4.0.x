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
 * @author Scott Ferguson
 */

package com.caucho.amber.cfg;

import java.util.HashMap;
import javax.persistence.JoinColumn;
import javax.persistence.CollectionTable;


/**
 * The <join-table> tag in orm.xml
 */
public class CollectionTableConfig extends AbstractTableConfig
{
  // elements
  private HashMap<String, JoinColumnConfig> _joinColumnMap
    = new HashMap<String, JoinColumnConfig>();

  public CollectionTableConfig()
  {
  }

  public CollectionTableConfig(String sourceName, String fieldName)
  {
    setName(sourceName + "_" + fieldName);
  }

  public CollectionTableConfig(CollectionTable joinTable)
  {
    setName(joinTable.name());
    setCatalog(joinTable.catalog());
    setSchema(joinTable.schema());

    for (JoinColumn joinColumn : joinTable.joinColumns()) {
      JoinColumnConfig joinColumnConfig = new JoinColumnConfig(joinColumn);
      
      addJoinColumn(joinColumnConfig);
    }
  }

  public JoinColumnConfig getJoinColumn(String name)
  {
    return _joinColumnMap.get(name);
  }

  public void addJoinColumn(JoinColumnConfig joinColumn)
  {
    _joinColumnMap.put(joinColumn.getName(),
                       joinColumn);
  }

  public HashMap<String, JoinColumnConfig> getJoinColumnMap()
  {
    return _joinColumnMap;
  }
}
