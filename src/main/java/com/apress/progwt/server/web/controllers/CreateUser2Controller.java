/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.server.web.controllers;

import org.apache.log4j.Logger;

/**
 * Extend basic CreateUserController, but we'll use it from a different URL for A/B testing with Google Optimizer
 * @author jdwyah
 *
 */
public class CreateUser2Controller extends CreateUserController {
    private static final Logger log = Logger
            .getLogger(CreateUser2Controller.class);

}
