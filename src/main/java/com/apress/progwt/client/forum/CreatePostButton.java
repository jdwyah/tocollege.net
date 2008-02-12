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

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.gui.ext.JSUtil;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.forum.ForumTopic;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class CreatePostButton extends Button implements ClickListener {

    private ForumApp app;
    private boolean isReply;

    // private ForumPost thread;

    /**
     * 
     * @param app
     * @param buttonText
     * @param topic -
     *            can be null for new thread creation
     */
    public CreatePostButton(ForumApp app, boolean isReply,
            ForumTopic topic) {
        super("Create New Thread");
        if (isReply) {
            setText("Reply");
        }
        addClickListener(this);
        this.app = app;
        this.isReply = isReply;
    }

    public void onClick(Widget sender) {

        String secureTargetURL = "site/secure/forums.html";

        // let the /secure/ page know that we should
        // fire the create action
        ForumCommand fc = app.getCurrentCommand();
        fc.setCreate(true);
        secureTargetURL += fc.getAsQueryString();

        app.getLoginService().getUserOrDoLogin(secureTargetURL,
                new StdAsyncCallback<User>("Login For Create Post") {
                    public void onSuccess(User author) {
                        super.onSuccess(author);

                        Log.info("success " + author);
                        openCreatePost(author);
                    }
                });

        // app.getUserService().getCurrentUser(
        // new StdAsyncCallback<UserAndToken>(
        // "Login For Create Post") {
        // public void onSuccess(UserAndToken author) {
        // super.onSuccess(author);
        //
        // // force a login
        // if (author.getUser() == null) {
        //
        // String base = "site/secure/forums.html";
        //
        // // let the /secure/ page know that we should
        // // fire the create action
        // ForumCommand fc = app.getCurrentCommand();
        // fc.setCreate(true);
        // base += fc.getAsQueryString();
        //
        // Location.replace(Interactive
        // .getRelativeURL(base));
        // } else {
        // Log.info("success " + author.getUser());
        // openCreatePost(author.getUser());
        // }
        // }
        // });

    }

    protected void openCreatePost(User author) {
        String selection = JSUtil.getTextSelection();

        CreatePostDialog cpd = new CreatePostDialog(app, author, isReply,
                selection);
        cpd.center();
    }

}
