/*
 * Copyright (C) 2010-2012 PhonyTive LLC
 * http://astive.phonytive.com
 *
 * This file is part of Astive Toolkit
 *
 * Astive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Astive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Astive.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.astivetoolkit.ami.action;


/**
 *
 * @since 1.0.0
 */
public class ReloadAction extends ActionMessage {
  private String module;

  /**
   * Creates a new ReloadAction object.
   */
  public ReloadAction() {
    super(ActionType.RELOAD);
  }

  /**
   * Creates a new ReloadAction object.
   *
   * @param module DOCUMENT ME!
   */
  public ReloadAction(String module) {
    super(ActionType.RELOAD);
    this.module = module;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getModule() {
    return module;
  }

  /**
   * DOCUMENT ME!
   *
   * @param module DOCUMENT ME!
   */
  public void setModule(String module) {
    this.module = module;
  }
}