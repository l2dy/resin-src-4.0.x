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

package com.caucho.ejb.session;

import javax.ejb.TimerService;

import com.caucho.config.inject.CreationalContextImpl;
import com.caucho.inject.Module;
import com.caucho.util.L10N;

/**
 * Abstract base class for an session context
 */
@Module
public class StatefulContext<X,T> extends AbstractSessionContext<X,T> {
  private static final L10N L = new L10N(StatefulContext.class);
  
  private ThreadLocal<T> _businessLocal = new ThreadLocal<T>();
  
  public StatefulContext(StatefulManager<X> manager, Class<T> api)
  {
    super(manager, api);
  }

  /**
   * Returns the server which owns this bean.
   */
  @Override
  public StatefulManager<X> getServer()
  {
    return (StatefulManager<X>) super.getServer();
  }
  
  @Override
  public T createProxy(CreationalContextImpl<T> env)
  {
    T proxy = super.createProxy(env);
    
    getServer().initProxy(proxy, env);
    
    return proxy;
  }
 
  /**
   * Returns the timer service.
   */
  @Override
  public TimerService getTimerService()
    throws IllegalStateException
  {
    throw new IllegalStateException(L.l("Stateful session beans cannot call SessionContext.getTimerService()"));
  }
  
  public T startLocal(T local)
  {
    T oldLocal = _businessLocal.get();
    
    _businessLocal.set(local);
    
    return oldLocal;
  }
  
  public void endLocal(T oldLocal)
  {
    _businessLocal.set(oldLocal);
  }
  
  protected T getLocal()
  {
    return _businessLocal.get();
  }
}
