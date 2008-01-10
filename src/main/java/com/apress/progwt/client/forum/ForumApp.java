package com.apress.progwt.client.forum;

import com.apress.progwt.client.GWTApp;
import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolForumPost;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.SaveForumPostCommand;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ForumApp<T extends ForumPost> extends GWTApp {

    private SimplePanel postsHolderP;
    private ForumControlPanel controlPanel;
    private int maxPerPage = 10;

    private School school;

    public ForumApp(int pageID) {
        super(pageID);

        long schoolID = Long.parseLong(getParam("schoolID"));
        String schoolName = getParam("schoolName");

        school = new School();
        school.setId(schoolID);
        school.setName(schoolName);

        initServices();

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setStylePrimaryName("SchoolForum");

        Label schoolNameL = new Label(schoolName);
        mainPanel.add(schoolNameL);

        postsHolderP = new SimplePanel();
        mainPanel.add(postsHolderP);

        controlPanel = new ForumControlPanel(this);
        mainPanel.add(controlPanel);

        CreatePostButton createB = new CreatePostButton(this, null);

        mainPanel.add(createB);

        show(mainPanel);

        load(schoolID, 0);

    }

    private void load(long schoolID, final int start) {
        getSchoolService().getSchoolThreads(schoolID, start, maxPerPage,
                new StdAsyncCallback<PostsList>("Get School Threads") {

                    @Override
                    public void onSuccess(PostsList result) {
                        super.onSuccess(result);
                        load(start, result);
                    }
                });
    }

    protected void load(int start, PostsList result) {
        controlPanel.setControls(start, maxPerPage, result
                .getTotalCount());

        VerticalPanel posts = new VerticalPanel();

        for (ForumPost post : result.getPosts()) {
            posts.add(new PostLink(this, post));
        }
        if (result.getPosts().size() == 0) {
            posts.add(new Label("No Posts Yet"));
        }

        postsHolderP.clear();
        postsHolderP.add(posts);

    }

    protected void refresh() {
        load(school.getId(), 0);
    }

    /**
     * Create and save a new forum post.
     * 
     * @param author
     * @param thread
     * @param title
     * @param text
     */
    public void create(User author, ForumPost thread, String title,
            String text) {
        // ForumPost p = new ForumPost()

        SchoolForumPost sfp = new SchoolForumPost(school, author, title,
                text, thread);

        getSchoolService().executeAndSaveCommand(
                new SaveForumPostCommand(sfp),
                new StdAsyncCallback<SiteCommand>("SaveForumPost") {
                    @Override
                    public void onSuccess(SiteCommand result) {
                        super.onSuccess(result);
                        refresh();
                    }
                });

    }
}
