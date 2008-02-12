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
package com.apress.progwt.server.domain;

import com.apress.progwt.server.domain.generated.AbstractMailingListEntry;
import com.apress.progwt.server.util.RandomUtils;
import com.apress.progwt.server.web.domain.MailingListCommand;

public class MailingListEntry extends AbstractMailingListEntry {

    private static final int KEY_LENGTH = 20;

    public MailingListEntry() {
        setRandomkey(RandomUtils.randomstring(KEY_LENGTH));
    };

    /**
     * Change the command into a DB serializable class.
     * 
     * @param comm
     */
    public MailingListEntry(MailingListCommand comm) {
        this();
        setEmail(comm.getEmail());
        setUserAgent(comm.getUserAgent());
        setHost(comm.getHost());
        setReferer(comm.getReferer());
    }

}
