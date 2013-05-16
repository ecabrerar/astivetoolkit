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
package org.astivetoolkit.agi.command;

import java.io.Serializable;
import org.astivetoolkit.agi.annotation.AgiCommand;
import org.astivetoolkit.agi.annotation.Parameter;

/**
 * Sends <code>message</code> to the console via verbose message system.
 * <code>level</code> is the verbose level (1-4). Always returns 1.
 *
 * @since 1.0.0
 */
@AgiCommand(command = "VERBOSE")
public class Verbose implements Serializable {
  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 0x47dfb2d79b894cecL;

  /**
   * Verbosity level(1-4).
   */
  @Parameter(position = 0x1, optional = false)
  private Integer level;

  /**
   * Message to send.
   */
  @Parameter(optional = false)
  private String message;

  /**
   * Create a new Verbose object.
   *
   * @param message message to send.
   * @param level verbosity level(1-4).
   */
  public Verbose(String message, Integer level) {
    this.message = message;
    this.level = level;
  }

  /**
   * Get verbosity level.
   *
   * @return level verbosity level
   */
  public Integer getLevel() {
    return level;
  }

  /**
   * Get message to send.
   *
   * @return message to send.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Set verbosity level.
   *
   * @param level verbosity level of verbosity(1-4).
   */
  public void setLevel(Integer level) {
    this.level = level;
  }

  /**
   * Set message to send.
   *
   * @param message
   */
  public void setMessage(String message) {
    this.message = message;
  }
}