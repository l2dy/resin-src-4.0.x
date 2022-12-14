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

package com.caucho.jms.memory;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.caucho.jms.queue.MessageTopicSubscriber;

/**
 * Implements a memory queue.
 */
public class MemorySubscriberQueue<E> extends MemoryQueueImpl<E>
  implements MessageTopicSubscriber<E>
{
  private static final Logger log
    = Logger.getLogger(MemorySubscriberQueue.class.getName());

  private Object _publisher;
  private boolean _isNoLocal;

  public MemorySubscriberQueue(Object publisher, boolean noLocal)
  {
    _publisher = publisher;
    _isNoLocal = noLocal;
  }


  @Override
  public void send(String msgId,
                   E msg,
                   int priority,
                   long timeout,
                   String publisher)
  {
    if (_isNoLocal && _publisher == publisher)
      return;

    if (log.isLoggable(Level.FINE))
      log.fine(this + " send message " + msg);

    super.send(msgId, msg, priority, timeout, publisher);
  }

}

