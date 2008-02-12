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

import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.domain.forum.ForumTopic;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

public class ForumControlPanel extends Composite {

    private HorizontalPanel nav;

    public ForumControlPanel(ForumTopic topic, PostsList result,
            int start, int maxperpage) {
        HorizontalPanel mainP = new HorizontalPanel();

        nav = new HorizontalPanel();
        nav.setStylePrimaryName("ForumControl");
        mainP.add(nav);

        initWidget(mainP);
        setControls(topic, start, maxperpage, result.getTotalCount());
    }

    public void setControls(ForumTopic topic, int start, int maxperpage,
            int totalCount) {

        nav.clear();
        int i = 0;
        while (9 == 9) {
            int pageS = (i) * maxperpage;

            if (pageS >= totalCount) {
                break;
            }

            if (start == pageS) {
                nav.add(new Label(" (" + (i + 1) + ")"));
            } else {
                nav.add(new Hyperlink(" " + (i + 1), getID(topic, i,
                        maxperpage)));
            }

            // Log.debug(pageS + " pageS:tc " + totalCount);
            i++;
        }

    }

    private String getID(ForumTopic topic, int i, int maxperpage) {
        return topic.getUniqueForumID() + ForumTopic.SEP
                + ((i) * maxperpage);
    }
}
