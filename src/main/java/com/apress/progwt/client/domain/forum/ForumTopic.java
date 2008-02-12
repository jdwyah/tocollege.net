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
package com.apress.progwt.client.domain.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;

public interface ForumTopic {

    /**
     * unique ID to specify any forum page.
     * 
     * @return
     */
    String getUniqueForumID();

    /**
     * if false, alternative is doPostListView
     * 
     * @return
     */
    boolean doThreadListView();

    ForumPost getReplyInstance(User author, String title, String text,
            ForumPost thread);

    ForumPost getForumPost();

    public static String SEP = "~";
    public static String CREATE = "~Create";

    String getForumDisplayName();

    long getId();

}
