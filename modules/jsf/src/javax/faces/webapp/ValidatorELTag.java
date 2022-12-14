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
  * @author Alex Rojkov
  */


package javax.faces.webapp;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.faces.validator.Validator;
import javax.faces.component.UIComponent;
import javax.faces.component.EditableValueHolder;

public abstract class ValidatorELTag
  extends TagSupport
{
  public int doStartTag()
    throws JspException
  {
    UIComponentClassicTagBase parent
      = UIComponentClassicTagBase.getParentUIComponentClassicTagBase(
      pageContext);

    if (parent == null)
      throw new JspException(
        "ValidatorELTag must be nested inside a UIComponent tag.");

    UIComponent comp = parent.getComponentInstance();

    if (parent.getCreated()) {
      if (!(comp instanceof EditableValueHolder))
        throw new JspException(
          "UIComponent parent of validator must be a EditableValueHolder.");

      EditableValueHolder valueHolder = (EditableValueHolder) comp;

      Validator validator = createValidator();

      valueHolder.addValidator(validator);
    }

    return SKIP_BODY;

  }

  protected abstract Validator createValidator()
    throws JspException;
}
