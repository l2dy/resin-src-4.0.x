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

package com.caucho.server.distcache;

import java.util.Collection;
import java.util.Map;

import com.caucho.cache.Cache.Entry;

/**
 * Extended cache loader
 */
public class AbstractCacheBacking<K,V> implements CacheBacking<K,V>
{
  @Override
  public void load(DistCacheEntry entry, CacheLoaderCallback cb)
  {
    cb.onLoadFail(entry);
  }
  
  @Override
  public void write(DistCacheEntry entry)
  {
  }

  @Override
  public void delete(DistCacheEntry entry)
  {
  }

  @Override
  public void updateTime(DistCacheEntry distCacheEntry)
  {
  }
  
  @Override
  public Entry<K, V> load(Object key)
  {
    throw new UnsupportedOperationException(getClass().getName());
  }

  @Override
  public Map<K, V> loadAll(Iterable<? extends K> keys)
  {
    throw new UnsupportedOperationException(getClass().getName());
  }

  @Override
  public void write(Entry<K, V> entry)
  {
    throw new UnsupportedOperationException(getClass().getName());
  }

  @Override
  public void writeAll(Collection<Entry<? extends K, ? extends V>> entries)
  {
    throw new UnsupportedOperationException(getClass().getName());
  }

  @Override
  public void delete(Object key)
  {
    throw new UnsupportedOperationException(getClass().getName());
  }

  @Override
  public void deleteAll(Collection<?> entries)
  {
    throw new UnsupportedOperationException(getClass().getName());
  }
}
