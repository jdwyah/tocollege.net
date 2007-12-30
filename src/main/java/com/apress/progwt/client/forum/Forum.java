package com.apress.progwt.client.forum;

import com.apress.progwt.client.GWTApp;
import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.dto.SchoolThreads;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Forum<T extends ForumPost> extends GWTApp {

    private SimplePanel postsHolderP;
    private ControlPanel controlPanel;
    private int maxPerPage = 10;

    public Forum(int pageID) {
        super(pageID);

        Long schoolID = Long.parseLong(getParam("schoolID"));
        String schoolName = getParam("schoolName");

        initServices();

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setStylePrimaryName("SchoolForum");

        Label schoolNameL = new Label(schoolName);
        mainPanel.add(schoolNameL);

        postsHolderP = new SimplePanel();
        mainPanel.add(postsHolderP);

        controlPanel = new ControlPanel(this);
        mainPanel.add(controlPanel);

        CreatePostButton createB = new CreatePostButton(this, schoolID);

        mainPanel.add(createB);

        show(mainPanel);

        load(schoolID, 0);

    }

    private void load(long schoolID, final int start) {
        getSchoolService()
                .getThreads(
                        schoolID,
                        start,
                        maxPerPage,
                        new StdAsyncCallback<SchoolThreads>(
                                "Get School Threads") {

                            @Override
                            public void onSuccess(SchoolThreads result) {
                                super.onSuccess(result);
                                load(start, result);
                            }
                        });
    }

    protected void load(int start, SchoolThreads result) {
        controlPanel.setControls(start, maxPerPage, result
                .getTotalCount());

        VerticalPanel posts = new VerticalPanel();

        for (ForumPost post : result.getPosts()) {
            posts.add(new PostLink(post));
        }
        if (result.getPosts().size() == 0) {
            posts.add(new Label("No Posts Yet"));
        }

        postsHolderP.clear();
        postsHolderP.add(posts);

    }
}
