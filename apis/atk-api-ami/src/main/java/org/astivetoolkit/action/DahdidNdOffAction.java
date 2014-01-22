/* 
 * Copyright (C) 2010-2014 by PhonyTive LLC (http://phonytive.com)
 * http://astivetoolkit.org
 *
 * This file is part of Astive Toolkit(ATK)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.astivetoolkit.ami.action;


/**
 *
 * @since 1.1
 */
public class DahdidNdOffAction extends ActionMessage {
  private String dahdiChannel;

  /**
   * Creates a new DahdidNdOffAction object.
   *
   * @param dahdiChannel DOCUMENT ME!
   */
  public DahdidNdOffAction(String dahdiChannel) {
    super(ActionType.DAHDID_ND_OFF);
    this.dahdiChannel = dahdiChannel;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getDahdiChannel() {
    return dahdiChannel;
  }

  /**
   * DOCUMENT ME!
   *
   * @param dahdiChannel DOCUMENT ME!
   */
  public void setDahdiChannel(String dahdiChannel) {
    this.dahdiChannel = dahdiChannel;
  }
}
