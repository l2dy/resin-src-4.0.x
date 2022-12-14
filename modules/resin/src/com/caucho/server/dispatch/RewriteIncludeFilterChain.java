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

package com.caucho.server.dispatch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caucho.server.webapp.RequestDispatcherImpl;

/**
 * Does an internal dispatch of the request.
 */
public class RewriteIncludeFilterChain implements FilterChain {
  private static final Logger log 
    = Logger.getLogger(RewriteIncludeFilterChain.class.getName());
  
  private String _url;

  /**
   * Create the dispatch filter chain servlet.
   *
   * @param url the request dispatcher to dispatch to.
   */
  public RewriteIncludeFilterChain(FilterChain next, String url)
  {
    _url = url;
  }

  /**
   * Dispatchs to the dispatch
   *
   * @param request the servlet request
   * @param response the servlet response
   */
  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response)
    throws ServletException, IOException
  {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    try {
      RequestDispatcherImpl disp
        = (RequestDispatcherImpl) req.getRequestDispatcher(_url);

      disp.include(request, response);
    } catch (FileNotFoundException e) {
      log.log(Level.FINE, e.toString(), e);
      
      res.sendError(404);
    }
  }

  public String toString()
  {
    return getClass().getSimpleName() + "[" + _url + "]";
  }
}
