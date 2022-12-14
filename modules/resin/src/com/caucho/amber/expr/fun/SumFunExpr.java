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
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Rodrigo Westrupp
 */

package com.caucho.amber.expr.fun;

import com.caucho.amber.query.QueryParser;
import com.caucho.amber.expr.AmberExpr;
import com.caucho.amber.type.*;
import com.caucho.util.L10N;

import java.util.ArrayList;


/**
 * SUM() function expression
 */
public class SumFunExpr extends FunExpr {
  private static final L10N L = new L10N(SumFunExpr.class);

  private AmberExpr _arg;

  /**
   * Creates a new function expression
   */
  protected SumFunExpr(QueryParser parser,
                       String id,
                       ArrayList<AmberExpr> args,
                       boolean distinct)
  {
    super(parser, id, args, distinct);

    // XXX: needs to throw IllegalArgument for empty args
    _arg = args.get(0);
  }

  public static SumFunExpr create(QueryParser parser,
                                  String id,
                                  ArrayList<AmberExpr> args,
                                  boolean distinct)
  {
    return new SumFunExpr(parser, id, args, distinct);
  }

  /**
   * Returns the expr type.
   */
  public AmberType getType()
  {
    // int/Integer -> Long and float/Float -> Double
    AmberType argType = _arg.getType();

    if ((argType instanceof IntegerType) ||
        (argType instanceof PrimitiveIntType))
      return LongType.create();

    if ((argType instanceof FloatType) ||
        (argType instanceof PrimitiveFloatType))
      return DoubleType.create();

    return argType;
  }
}
