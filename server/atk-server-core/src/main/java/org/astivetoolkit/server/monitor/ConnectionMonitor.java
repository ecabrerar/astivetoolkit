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
package org.astivetoolkit.server.monitor;

import org.astivetoolkit.AstiveException;
import org.astivetoolkit.agi.Connection;

/**
 * Manage incoming connections from a telephone engine.
 * 
 * @since 1.0
 * @see FastAgiConnectionMonitor
 * @see SimpleConnectionMonitor
 */
public interface ConnectionMonitor {

    /**
     * Process all incoming connection.
     * 
     * @param conn incoming connection.
     * @throws AstiveException 
     */
    void processConnection(Connection conn) throws AstiveException;
}
