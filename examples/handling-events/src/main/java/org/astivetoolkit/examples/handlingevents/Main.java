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
package org.astivetoolkit.examples.handlingevents;

import java.io.IOException;
import org.astivetoolkit.server.SimpleAstiveServer;
import org.astivetoolkit.server.SystemException;

/**
 *
 * @since 1.0
 */
public class Main {

    static public void main(String... args) {
        try {
            SimpleAstiveServer server = new SimpleAstiveServer(new App());
            server.start();
        } catch (SystemException ex) {
        } catch (IOException ex) {}
    }
}
