/*
 * Copyright (c) 1998-2010 Caucho Technology -- all rights reserved
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

package com.caucho.ejb.cfg;

import com.caucho.util.L10N;

import javax.ejb.*;
import javax.enterprise.inject.spi.AnnotatedType;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Configuration for an ejb stateless session bean.
 */
public class EjbStatelessBean<X> extends EjbSessionBean<X> {
  private static final L10N L = new L10N(EjbStatelessBean.class);

  /**
   * Creates a new session bean configuration.
   */
  public EjbStatelessBean(EjbConfig ejbConfig, String ejbModuleName)
  {
    super(ejbConfig, ejbModuleName);
  }

  /**
   * Creates a new session bean configuration.
   */
  public EjbStatelessBean(EjbConfig ejbConfig,
			 AnnotatedType annType,
			 Stateless stateless)
  {
    super(ejbConfig, annType, stateless.name());
  }

  /**
   * Returns the kind of bean.
   */
  public String getEJBKind()
  {
    return "stateless";
  }
  
  @Override
  public Class<? extends Annotation> getSessionType()
  {
    return Stateless.class;
  }
}
