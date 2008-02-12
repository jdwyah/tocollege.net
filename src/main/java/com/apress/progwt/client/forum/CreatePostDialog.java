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

import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.DialogBox;

public class CreatePostDialog extends DialogBox {
    public CreatePostDialog(ForumApp app, User author, boolean isReply,
            String selection) {
        super(false);
        if (isReply) {
            setText("Create Reply");
        } else {
            setText("Create Post");
        }
        setWidget(new CreatePostWidget(app, isReply, this, author,
                selection));
        addStyleDependentName("CreatePostDialog");
    }
}
