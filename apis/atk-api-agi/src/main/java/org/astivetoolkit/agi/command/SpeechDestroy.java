/* 
 * Copyright (C) 2017 by Fonoster Inc (http://fonoster.com)
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
package org.astivetoolkit.agi.command;

import java.io.Serializable;
import org.astivetoolkit.agi.annotation.AgiCommand;

/**
 * Destroy the speech object created by {@link SpeechCreate}.
 *
 * @since 1.0
 * @see SpeechCreate
 */
@AgiCommand(command = "SPEECH DESTROY")
public class SpeechDestroy implements Serializable {
  private static final long serialVersionUID = -4115626736069817017L;

  /**
   * Create a new SpeechDestroy object.
   */
  public SpeechDestroy() {
  }
}
