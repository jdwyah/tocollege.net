package com.apress.progwt.client.forum;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.GWTApp;
import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolForumPost;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.UserForumPost;
import com.apress.progwt.client.domain.commands.SaveForumPostCommand;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.ForumBootstrap;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.domain.forum.ForumTopic;
import com.apress.progwt.client.domain.forum.RecentForumPostTopic;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ForumApp extends GWTApp implements HistoryListener {

    private ForumTopic currentTopic;

    private ForumDisplay forumDisplay;

    private static final int FORUM_POST_MAX = 5;
    private static final int FORUM_THREAD_MAX = 10;
    private ForumTopic originalTopic;

    public ForumApp(int pageID) {
        super(pageID);

        initServices();

        String uniqueForumID = getParam("uniqueForumID");

        ForumBootstrap bootstrapped = (ForumBootstrap) getBootstrapped();

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setStylePrimaryName("Forum");

        forumDisplay = new ForumDisplay(this);
        mainPanel.add(forumDisplay);

        show(mainPanel);

        String initToken = History.getToken();
        if (initToken.length() == 0 && uniqueForumID != null) {
            initToken = uniqueForumID;
            History.newItem(initToken);
        }

        if (bootstrapped != null) {
            Log.info("Running off Bootstrap");
            School s = new School();
            s.setId(486);

            load(0, bootstrapped.getPostsList(), false, bootstrapped
                    .getForumTopic(), FORUM_POST_MAX);

            History.newItem(bootstrapped.getForumTopic()
                    .getUniqueForumID());

        } else if (initToken.length() > 0) {
            // onHistoryChanged() is not called when the application first
            // runs. Call it now in order to reflect the initial state.
            // this will affect a load
            onHistoryChanged(initToken);
        }

        History.addHistoryListener(this);
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

        System.out.println("Create! " + originalTopic + " cur "
                + currentTopic);

        // originalTopic should be a School or User, currentTopic
        // shouldn't be null unless load hasn't completed.
        if (currentTopic != null) {
            sfp = originalTopic.getReplyInstance(author, title, text,
                    currentTopic.getForumPost());
        } else {
            sfp = originalTopic.getReplyInstance(author, title, text,
                    null);
        }

        System.out.println("Going to Save " + sfp);

        getServiceCache().executeCommand(new SaveForumPostCommand(sfp),
                new StdAsyncCallback<SiteCommand>("SaveForumPost") {
                    @Override
                    public void onSuccess(SiteCommand result) {
                        super.onSuccess(result);
                        onHistoryChanged(History.getToken());
                    }
                });

    }

    private void gotoUser(final User user, final int start) {
        originalTopic = user;
        gotoForum(user, start, false, FORUM_THREAD_MAX);
    }

    private void gotoSchool(final School school, final int start) {
        originalTopic = school;
        gotoForum(school, start, false, FORUM_THREAD_MAX);
    }

    public void gotoThread(ForumPost post) {
        gotoThread(post, 0);
    }

    public void gotoThread(final ForumPost thread, final int start) {
        gotoForum(thread, start, true, FORUM_POST_MAX);
    }

    private void gotoForum(final ForumTopic forumTopic, final int start,
            final boolean isReply, final int max) {

        getServiceCache().getForum(forumTopic, start, max,
                new StdAsyncCallback<PostsList>("Get Posts For Thread") {
                    @Override
                    public void onSuccess(PostsList result) {
                        super.onSuccess(result);
                        load(start, result, true, forumTopic, max);
                    }
                });
    }

    protected void load(int start, PostsList result, boolean isReply,
            ForumTopic current, int maxPerPage) {

        currentTopic = current;

        forumDisplay.load(start, result, originalTopic, current, isReply,
                maxPerPage);

    }

    /**
     * #School~486~20
     * 
     * #SchoolForumPost~12~0
     * 
     * #SchoolForumPost~12
     */
    public void onHistoryChanged(String historyToken) {

        Log.debug("HISTORY CHANGE " + historyToken);

        try {
            String[] tok = historyToken.split(ForumTopic.SEP);
            int start = 0;
            long id = Long.parseLong(tok[1]);
            if (tok.length == 3) {
                start = Integer.parseInt(tok[2]);
            }
            if (tok[0].equals("School")) {
                School s = new School(id);
                gotoSchool(s, start);
            } else if (tok[0].equals("SchoolForumPost")) {
                ForumPost fp = new SchoolForumPost();
                fp.setId(id);
                gotoThread(fp, start);
            } else if (tok[0].equals("User")) {
                User u = new User();
                u.setId(id);
                gotoUser(u, start);
            } else if (tok[0].equals("UserForumPost")) {
                ForumPost fp = new UserForumPost();
                fp.setId(id);
                gotoThread(fp, start);
            } else if (tok[0].equals("RecentForumPost")) {
                gotoForum(new RecentForumPostTopic(), start, false,
                        FORUM_THREAD_MAX);
            }
        } catch (Exception e) {
            Log.warn("Problem parsing token:" + historyToken);
        }
    }

}
