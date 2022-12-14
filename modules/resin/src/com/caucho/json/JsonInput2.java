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

package com.caucho.json;

import com.caucho.util.Utf8;
import com.caucho.vfs.ReadStream;
import com.caucho.vfs.Vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Input stream for JSON requests.
 */
public class JsonInput2
{
  private ReadStream _is;
  private int _peek = -1;

  public JsonInput2()
  {
  }

  public JsonInput2(InputStream is)
  {
    init(is);
  }

  /**
   * Initialize the output with a new underlying stream.
   */
  public void init(InputStream is)
  {
    _is = Vfs.openRead(is);
  }

  public JsonValue readObject()
    throws IOException
  {
    InputStream is = _is;

    if (is == null)
      return null;

    int ch;

    while ((ch = read()) >= 0) {
      switch (ch) {
      case ' ': case '\n': case '\r': case '\t':
        break;

      case '0': case '1': case '2': case '3': case '4':
      case '5': case '6': case '7': case '8': case '9':
      case '.': case '+': case '-':
        return parseNumber(ch);

      case '"':
        return parseStringJson();

      case 'n':
        return parseNull(is);

      case 't':
        return parseTrue(is);

      case 'f':
        return parseFalse(is);

      case '[':
        return parseArray(is);

      case '{':
        return parseMap(is);
      }
    }

    return null;
  }

  public Object readObject(String type)
    throws IOException
  {
    return readObject();
  }

  public long readLong()
    throws IOException
  {
    Object value = readObject();

    if (value instanceof Number)
      return ((Number) value).longValue();
    else
      return 0; // XXX:error
  }

  public double readDouble()
    throws IOException
  {
    Object value = readObject();

    if (value instanceof Number)
      return ((Number) value).doubleValue();
    else
      return 0; // XXX:error
  }

  public String readString()
    throws IOException
  {
    Object value = readObject();

    return (String) value;
  }

  public boolean startPacket()
    throws IOException
  {
    int ch;

    while ((ch = read()) >= 0 && Character.isWhitespace((char) ch)) {
    }

    if (ch < 0)
      return false;
    else if (ch == 0)
      return true;
    else
      throw new IOException("0x" + Integer.toHexString(ch) + " is an illegal JmtpPacket start");
  }

  public void endPacket()
    throws IOException
  {
    int ch;

    while ((ch = read()) >= 0 && ch != 0xff) {
    }
  }

  //
  // utility
  //

  private JsonNull parseNull(InputStream is)
    throws IOException
  {
    int ch;

    if ((ch = is.read()) == 'u'
        && (ch = is.read()) == 'l'
        && (ch = is.read()) == 'l')
      return new JsonNull();

    if (ch == -1)
      throw new IOException(this + " parsing of null failed at <EOF>");

    throw new IOException(this + " parsing of null failed at " + (char) ch);
  }

  private JsonBoolean parseTrue(InputStream is)
    throws IOException
  {
    int ch;

    if ((ch = is.read()) == 'r'
        && (ch = is.read()) == 'u'
        && (ch = is.read()) == 'e')
      return JsonBoolean.TRUE;

    if (ch == -1)
      throw new IOException(this + " parsing of true failed at <EOF>");

    throw new IOException(this + " parsing of true failed at " + (char) ch);
  }

  private JsonBoolean parseFalse(InputStream is)
    throws IOException
  {
    int ch;

    if ((ch = is.read()) == 'a'
        && (ch = is.read()) == 'l'
        && (ch = is.read()) == 's'
        && (ch = is.read()) == 'e')
      return JsonBoolean.FALSE;

    if (ch == -1)
      throw new IOException(this + " parsing of false failed at <EOF>");

    throw new IOException(this + " parsing of false failed at " + (char) ch);
  }

  private JsonString parseStringJson()
    throws IOException
  {
    return new JsonString(parseString());
  }

  private String parseString()
    throws IOException
  {
    int ch;

    InputStream is = _is;

    StringBuilder sb = new StringBuilder();

    while ((ch = Utf8.read(is)) >= 0 && ch != '"') {
      if (ch == '\\') {
        ch = Utf8.read(is);

        switch (ch) {
        case 'r':
          sb.append('\r');
          break;
        case 'n':
          sb.append('\n');
          break;
        case 't':
          sb.append('\t');
          break;
        case 'f':
          sb.append('\f');
          break;
        default:
          sb.append((char) ch);
        }
      }
      else {
        sb.append((char) ch);
      }
    }

    if (ch == -1)
      throw new IOException(this + " closing '\"' expected at <EOF>");

    return sb.toString();
  }

  private JsonNumber parseNumber(int ch)
    throws IOException
  {
    InputStream is = _is;

    StringBuilder sb = new StringBuilder();
    boolean isDouble = false;

    loop:
    for (; ch >= 0; ch = is.read()) {
      switch (ch) {
      case '+':
        break;

      case '-':
      case '0': case '1': case '2': case '3': case '4':
      case '5': case '6': case '7': case '8': case '9':
        sb.append((char) ch);
        break;

      case '.': case 'e': case 'E':
        sb.append((char) ch);
        isDouble = true;
        break;

      default:
        _peek = ch;
        break loop;
      }
    }

    if (isDouble)
      return new JsonDouble(Double.parseDouble(sb.toString()));
    else
      return new JsonInt(Long.parseLong(sb.toString()));
  }

  private JsonArray parseArray(InputStream is)
    throws IOException
  {
    ArrayList<JsonValue> list = new ArrayList<JsonValue>();

    int ch;

    while ((ch = read()) >= 0) {
      switch (ch) {
      case ',':
      case ' ': case '\t': case '\r': case '\n':
        break;

      case ']':
        return new JsonArray(list.toArray(new JsonValue[list.size()]));

      default:
        _peek = ch;
        list.add(readObject());
      }
    }

    if (ch == -1)
      throw new IOException(this + " expected ']' at <EOF>");

    return new JsonArray(list.toArray(new JsonValue[list.size()]));
  }

  private JsonMap parseMap(InputStream is)
    throws IOException
  {
    LinkedHashMap map = new LinkedHashMap();

    int ch;

    while ((ch = read()) >= 0) {
      switch (ch) {
      case ',':
      case ' ': case '\t': case '\r': case '\n':
        break;

      case '}':
        return new JsonMap(map);

      case '"':
        String key = parseString();
        for (ch = read(); ch >= 0 && ch != ':' && ch != '}'; ch = is.read()) {
        }
        if (ch == ':') {
          Object value = readObject();
          map.put(key, value);
        }
        else
          return new JsonMap(map);
        break;

      default:
        _peek = ch;

        return new JsonMap(map);
      }
    }

    if (ch == -1)
      throw new IOException(this + " closing '}' expected at <EOF>");

    throw new IOException(this + " closing '}' expected at " + (char) ch);
  }

  private int read()
    throws IOException
  {
    int peek = _peek;
    if (peek >= 0) {
      _peek = -1;
      return peek;
    }

    return _is.read();
  }

  public void close()
    throws IOException
  {
    InputStream is = _is;
    _is = null;
  }

  @Override
  public String toString()
  {
    return JsonInput2.class.getSimpleName() + "[]";
  }
}
