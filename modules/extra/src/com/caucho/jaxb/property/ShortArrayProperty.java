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
 * ashort with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Emil Ong
 */

package com.caucho.jaxb.property;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.caucho.jaxb.BinderImpl;
import com.caucho.jaxb.JAXBUtil;
import com.caucho.jaxb.NodeIterator;
import com.caucho.jaxb.mapping.Namer;
import com.caucho.util.L10N;

/**
 * a property for serializing/deserializing arrays
 */
public class ShortArrayProperty extends ArrayProperty {
  private static final L10N L = new L10N(ShortArrayProperty.class);

  public static final ShortArrayProperty PROPERTY = new ShortArrayProperty();

  public ShortArrayProperty()
  {
    super(ShortProperty.PRIMITIVE_PROPERTY);
  }

  public Object read(Unmarshaller u, XMLStreamReader in, Object previous)
    throws IOException, XMLStreamException, JAXBException
  {
    short[] array = (short[]) previous;

    if (array == null)
      array = new short[1];
    else {
      short[] newArray = new short[array.length + 1];
      System.arraycopy(array, 0, newArray, 0, array.length);

      array = newArray;
    }

    array[array.length - 1] =
      ((Short) _componentProperty.read(u, in, null)).shortValue();

    return array;
  }

  public Object bindFrom(BinderImpl binder, NodeIterator node, Object previous)
    throws IOException, JAXBException
  {
    short[] array = (short[]) previous;

    if (array == null)
      array = new short[1];
    else {
      short[] newArray = new short[array.length + 1];
      System.arraycopy(array, 0, newArray, 0, array.length);

      array = newArray;
    }

    Short b = (Short) _componentProperty.bindFrom(binder, node, null);

    array[array.length - 1] = b.shortValue();

    return array;
  }

  public void write(Marshaller m, XMLStreamWriter out, 
                    Object value, Namer namer)
    throws IOException, XMLStreamException, JAXBException
  {
    if (value != null) {
      short[] array = (short[]) value;

      for (int i = 0; i < array.length; i++) 
        ShortProperty.PRIMITIVE_PROPERTY.write(m, out, array[i], namer);
    }
  }

  public Node bindTo(BinderImpl binder, Node node,
                     Object value, Namer namer)
    throws IOException, JAXBException
  {
    QName qname = namer.getQName(value);
    QName name = JAXBUtil.qnameFromNode(node);
    Document doc = node.getOwnerDocument(); 

    if (! name.equals(qname))
      node = JAXBUtil.elementFromQName(qname, doc);

    binder.bind(value, node);

    if (value != null) {
      short[] array = (short[]) value;

      for (int i = 0; i < array.length; i++) {
        Node child = JAXBUtil.elementFromQName(qname, doc);
        node.appendChild(ShortProperty.PRIMITIVE_PROPERTY.bindTo(binder, 
                                                                 child, 
                                                                 array[i], 
                                                                 namer));
      }
    }

    return node;
  }
}
