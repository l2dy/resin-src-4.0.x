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

package com.caucho.server.admin;

import java.util.logging.Level;

@SuppressWarnings("serial")
public class LogLevelQuery implements java.io.Serializable
{
  private String[] _loggers;
  private Level _level;
  private long _period;

  public LogLevelQuery(String[] loggers, Level level, long period)
  {
    _loggers = loggers;
    _level = level;
    _period = period;
  }

  public String[] getLoggers()
  {
    return _loggers;
  }

  public Level getLevel()
  {
    return _level;
  }

  public long getPeriod()
  {
    return _period;
  }

  @Override
  public String toString()
  {
    return getClass().getSimpleName() + "[]";
  }
}
