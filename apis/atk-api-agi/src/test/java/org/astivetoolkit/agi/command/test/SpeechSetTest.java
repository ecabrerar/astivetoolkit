/* 
 * Copyright (C) 2010-2016 by Fonoster Inc (http://fonoster.com)
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
package org.astivetoolkit.agi.command.test;

import junit.framework.TestCase;
import org.astivetoolkit.agi.AgiException;
import org.astivetoolkit.agi.CommandProcessor;
import org.astivetoolkit.agi.command.SpeechSet;

/**
 * Test case for command {@link org.astivetoolkit.agi.command.SpeechSet}.
 * 
 * @since 1.0
 */
public class SpeechSetTest extends TestCase {
  /**
   * Creates a new SpeechSetTest object.
   *
   * @param testName {@inheritDoc}.
   */
  public SpeechSetTest(String testName) {
    super(testName);
  }

  /**
   * Test method.
   *
   * @throws AgiException if command is malformed.
   */
  public void testCommand() throws AgiException {
    String name = "var";
    String value = "val";

    StringBuilder b = new StringBuilder("SPEECH SET");
    b.append(" ");
    b.append("\"");
    b.append(name);
    b.append("\"");
    b.append(" ");
    b.append("\"");
    b.append(value);
    b.append("\"");

    SpeechSet command = new SpeechSet(name, value);
    assertEquals(b.toString(), CommandProcessor.buildCommand(command));
  }
}
