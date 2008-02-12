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
package com.apress.progwt.client.calculator;

/**
 * Represents an action that the calculator can perform.
 * 
 * Actions will be given the current and previous values as doubles and
 * should return a result to write to the result textbox.
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public abstract class ControlAction {

    private String displayString;
    private Calculator calculator;

    public ControlAction(Calculator calculator, String displayString) {
        this.displayString = displayString;
        this.calculator = calculator;
    }

    public String getDisplayString() {
        return displayString;
    }

    public abstract double performAction(ControlAction lastAction,
            double previous, double current);

    /**
     * Does this action change the result right away? or does it wait for
     * another argument and then an equals sign? eg (+/-/*)
     * 
     * if true, we'll wait for the = sign
     * 
     * @return
     */
    public boolean isMultiArg() {
        return true;
    }

}
