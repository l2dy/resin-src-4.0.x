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

package com.caucho.amp.actor;

import java.util.logging.Logger;

import com.caucho.amp.mailbox.AmpMailboxBuilder;
import com.caucho.amp.stream.AmpEncoder;
import com.caucho.amp.stream.AmpError;

/**
 * Handles the context for an actor, primarily including its
 * query map.
 */
public final class AmpProxyActor implements AmpActor {
  private static final Logger log
    = Logger.getLogger(AmpProxyActor.class.getName());

  private final ActorContextImpl _actorContext;

  public AmpProxyActor(String address,
                       AmpMailboxBuilder mailboxFactory)
  {
    _actorContext = new ActorContextImpl(address, this, mailboxFactory);
  }
  
  public ActorContextImpl getActorContext()
  {
    return _actorContext;
  }

  @Override
  public void send(AmpActorRef to, 
                   AmpActorRef from,
                   AmpEncoder encoder,
                   String methodName,
                   Object... args)
  {
    log.warning(this + " send from " + from);
  }

  @Override
  public void error(AmpActorRef to, 
                    AmpActorRef from,
                    AmpEncoder encoder, 
                    AmpError error)
  {
    log.finer(this + " error from " + from.getAddress());
  }

  @Override
  public void query(long id, 
                    AmpActorRef to, 
                    AmpActorRef from,
                    AmpEncoder encoder, 
                    String methodName,
                    Object... args)
  {
    log.warning(this + " query from " + from.getAddress());
  }

  @Override
  public void queryResult(long id, 
                          AmpActorRef to, 
                          AmpActorRef from,
                          AmpEncoder encoder,
                          Object result)
  {
    log.warning(this + " query result");
  }

  @Override
  public void queryError(long id, 
                         AmpActorRef to, 
                         AmpActorRef from,
                         AmpEncoder encoder, 
                         AmpError error)
  {
    log.warning(this + " query error");
  }

  @Override
  public AmpMethodRef getMethod(String methodName, AmpEncoder encoder)
  {
    throw new UnsupportedOperationException(getClass().getName());
  }
}
