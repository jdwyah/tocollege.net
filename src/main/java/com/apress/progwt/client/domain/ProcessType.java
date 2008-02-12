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

import com.apress.progwt.client.domain.generated.AbstractProcessType;
import com.apress.progwt.client.json.JSONSerializable;

public class ProcessType extends AbstractProcessType implements
        Serializable, Loadable, Comparable<ProcessType>, JSONSerializable {

    public ProcessType() {
    }

    public ProcessType(String name) {
        setName(name);
    }

    @Override
    public String toString() {
        return "ProcessType: " + getName() + " " + getId();
    }

    public int compareTo(ProcessType o) {
        if (o != null) {
            return getStatus_order() - o.getStatus_order();
        } else {
            return 1;
        }

    }

}
