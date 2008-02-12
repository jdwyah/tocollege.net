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
package com.apress.progwt.client.forum;

import com.google.gwt.user.client.Window.Location;

/**
 * wrap our location in a way that we can use it with URL variables, since
 * when acegi passes us around it will forget the #name part of the URL
 * 
 * @author Jeff Dwyer
 * 
 */
public class ForumCommand {

    public static ForumCommand getFromLocation() {

        ForumCommand rtn = new ForumCommand();

        rtn.setType(Location.getParameter("type"));

        if (rtn.getType() == null) {
            // must have a type
            return null;
        }

        String ids = Location.getParameter("id");
        if (ids != null) {
            rtn.setId(Integer.parseInt(ids));
        }
        String s = Location.getParameter("start");
        if (s != null) {
            rtn.setStart(Integer.parseInt(s));
        }

        String c = Location.getParameter("create");
        if (c != null) {
            // No GWT Boolean.parseBoolean for some reason
            rtn.setCreate(c.equals("true"));
        }

        return rtn;
    }

    private boolean create = false;
    private long id = 0;
    private int start = 0;

    private String type = "";

    public ForumCommand() {
    }

    public ForumCommand(String type, int start, long id, boolean create) {
        super();
        this.type = type;
        this.start = start;
        this.id = id;
        this.create = create;
    }

    public String getAsQueryString() {
        StringBuffer sb = new StringBuffer("?type=");
        sb.append(type);
        sb.append("&id=");
        sb.append(id);
        sb.append("&create=");
        sb.append(create);
        sb.append("&start=");
        sb.append(start);
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public int getStart() {
        return start;
    }

    public String getType() {
        return type;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getAsQueryString();
    }

}
