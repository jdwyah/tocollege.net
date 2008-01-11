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
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ForumApp<T extends ForumPost> extends GWTApp implements
        HistoryListener {

    private ForumControlPanel controlPanel;
    private int maxperpage = 3;

    private School school;
    private ForumDisplay forumDisplay;

    private ForumTopic originalTopic;
    private ForumTopic currentTopic;

    public ForumApp(int pageID) {
        super(pageID);

        long schoolID = Long.parseLong(getParam("schoolID"));
        String schoolName = getParam("schoolName");

        school = new School();
        school.setId(schoolID);
        school.setName(schoolName);
        originalTopic = school;

        initServices();

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setStylePrimaryName("SchoolForum");

        Label schoolNameL = new Label(schoolName);
        mainPanel.add(schoolNameL);

        forumDisplay = new ForumDisplay(this);
        mainPanel.add(forumDisplay);

        show(mainPanel);

        String initToken = History.getToken();
        if (initToken.length() == 0) {
            initToken = school.getUniqueForumID();
            History.newItem(initToken);
        }

        // onHistoryChanged() is not called when the application first
        // runs. Call it now in order to reflect the initial state.
        onHistoryChanged(initToken);

        History.addHistoryListener(this);
    }

    private void gotoSchool(final School school, final int start) {
        getSchoolService().getSchoolThreads(school.getId(), start,
                maxperpage,
                new StdAsyncCallback<PostsList>("Get School Threads") {

                    @Override
                    public void onSuccess(PostsList result) {
                        super.onSuccess(result);
                        load(start, result, school, school);
                    }
                });
    }

    protected void load(int start, PostsList result, ForumTopic original,
            ForumTopic current) {

        currentTopic = current;
        originalTopic = original;

        forumDisplay.load(start, result, current, originalTopic);

    }

    protected void refresh() {
        gotoSchool(school, 0);
    }

    /**
     * Create and save a new forum post.
     * 
     * @param author
     * @param replyThread
     * @param title
     * @param text
     */
    public void create(User author, String title, String text) {
        // ForumPost p = new ForumPost()

        ForumPost sfp = null;

        System.out.println("Create " + originalTopic + " cur "
                + currentTopic);

        // originalTopic should be a School or User
        if (originalTopic == null) {
            sfp = originalTopic.getReplyInstance(author, title, text,
                    currentTopic.getForumPost());
        } else {
            sfp = originalTopic.getReplyInstance(author, title, text,
                    null);
        }

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

    public void gotoThread(ForumPost post) {
        gotoThread(post, 0);
    }

    public void gotoThread(final ForumPost thread, final int start) {

        getSchoolService().getPostsForThread(thread, start, maxperpage,
                new StdAsyncCallback<PostsList>("Get Posts For Thread") {

                    @Override
                    public void onSuccess(PostsList result) {
                        super.onSuccess(result);
                        load(start, result, null, thread);
                    }
                });
    }

    public int getMaxperpage() {
        return maxperpage;
    }

    /**
     * #School:486:20
     * 
     * #ForumPost:12:0
     * 
     * #ForumPost:12
     */
    public void onHistoryChanged(String historyToken) {

        System.out.println("HISTORY CHANGE " + historyToken);

        String[] tok = historyToken.split(ForumTopic.SEP);
        int start = 0;
        long id = Long.parseLong(tok[1]);
        if (tok.length == 3) {
            start = Integer.parseInt(tok[2]);
        }
        if (tok[0].equals("School")) {
            School s = new School();
            s.setId(id);
            gotoSchool(s, start);
        } else if (tok[0].equals("ForumPost")) {
            ForumPost fp = new SchoolForumPost();
            fp.setId(id);
            gotoThread(fp, start);
        }
    }
}
