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
package com.apress.progwt.client.domain;

import java.io.Serializable;
import java.util.Date;

import com.apress.progwt.client.domain.generated.AbstractProcessValue;

public class ProcessValue extends AbstractProcessValue implements
        Serializable {

    public ProcessValue() {
        setDueDate(new Date());
        setPctComplete(0);
    }

    public void increment(ProcessType type) {
        if (type.isPercentage()) {
            setPctComplete(getPctComplete() + .25);
            if (getPctComplete() > 1) {
                setPctComplete(0);
            }
        } else {
            if (getPctComplete() == 1) {
                setPctComplete(0);
            } else {
                setPctComplete(1);
            }
        }
    }

    public String getString(ProcessType type) {

        if (type.isPercentage()) {
            return 100 * getPctComplete() + "%";
        } else {
            if (getPctComplete() == 0) {
                return "No";
            } else {
                return "Yes";
            }
        }

    }
}
