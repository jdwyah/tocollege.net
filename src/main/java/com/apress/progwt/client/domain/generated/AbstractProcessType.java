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
package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

public abstract class AbstractProcessType implements Serializable {
    private long id;
    private String name;
    private boolean useByDefault;
    private int status_order;
    private boolean percentage;
    private boolean dated;
    private String imageName;
    private boolean dueAlert;

    public AbstractProcessType() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUseByDefault() {
        return useByDefault;
    }

    public void setUseByDefault(boolean useByDefault) {
        this.useByDefault = useByDefault;
    }

    public int getStatus_order() {
        return status_order;
    }

    public void setStatus_order(int status_order) {
        this.status_order = status_order;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public boolean isDated() {
        return dated;
    }

    public void setDated(boolean dated) {
        this.dated = dated;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isDueAlert() {
        return dueAlert;
    }

    public void setDueAlert(boolean dueAlert) {
        this.dueAlert = dueAlert;
    }

}
