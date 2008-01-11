package com.apress.progwt.client.forum;

import com.apress.progwt.client.college.gui.UserLink;
import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.dto.PostsList;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ForumDisplay extends Composite {

    private static final DateTimeFormat df = DateTimeFormat
            .getFormat("MMM, d yyyy HH:mm");

    private VerticalPanel allPosts;
    private ForumApp<? extends ForumPost> forumApp;

    public ForumDisplay(ForumApp<? extends ForumPost> forumApp) {
        this.forumApp = forumApp;
        allPosts = new VerticalPanel();
        allPosts.setStylePrimaryName("ForumPosts");
        initWidget(allPosts);
    }

    public void load(int start, PostsList result, ForumTopic topic,
            ForumTopic originalTopic) {

        allPosts.clear();

        for (ForumPost post : result.getPosts()) {

            if (topic.showForumPostText()) {
                allPosts.add(new PostDisplay(post));
            } else {
                allPosts.add(new ShortPostDisplay(post));
            }
        }

        if (result.getPosts().size() == 0) {
            allPosts.add(new Label("No Posts Yet"));
        }

        boolean isReply = (originalTopic == null);
        CreatePostButton createB = new CreatePostButton(forumApp,
                isReply, topic);
        allPosts.add(createB);

        ForumControlPanel fcb = new ForumControlPanel(topic, result,
                start, forumApp.getMaxperpage());
        allPosts.add(fcb);

    }

    private class PostDisplay extends Composite {

        public PostDisplay(ForumPost post) {

            HorizontalPanel mainP = new HorizontalPanel();

            VerticalPanel authorSide = new VerticalPanel();
            authorSide.addStyleDependentName("AuthorSide");
            VerticalPanel postSide = new VerticalPanel();
            postSide.addStyleDependentName("PostSide");

            UserLink author = new UserLink(post.getAuthor());
            authorSide.add(author);

            Label date = new Label(df.format(post.getDate()));
            date.addStyleDependentName("Date");
            authorSide.add(date);

            Label postT = new Label(post.getPostTitle());
            postT.addStyleDependentName("title");
            postSide.add(postT);

            Label postS = new Label(post.getPostString());
            postSide.add(postS);

            mainP.add(authorSide);
            mainP.add(postSide);

            initWidget(mainP);
            mainP.setStylePrimaryName("ForumPost");
        }
    }

    private class ShortPostDisplay extends Composite implements
            ClickListener {
        private ForumPost post;

        public ShortPostDisplay(ForumPost post) {
            this.post = post;

            HorizontalPanel mainP = new HorizontalPanel();

            Hyperlink postT = new Hyperlink(post.getPostTitle(), post
                    .getUniqueForumID());
            mainP.add(postT);

            Label replies = new Label("" + post.getReplyCount());
            replies.addStyleDependentName("replies");
            mainP.add(replies);

            HorizontalPanel authorP = new HorizontalPanel();
            UserLink author = new UserLink(post.getAuthor());

            Label date = new Label(df.format(post.getDate()));
            date.addStyleDependentName("Date");
            authorP.add(date);
            authorP.add(new Label(" by "));
            authorP.add(author);

            mainP.add(authorP);

            initWidget(mainP);
            mainP.setStylePrimaryName("ForumPost");
            mainP.addStyleDependentName("Short");
        }

        public void onClick(Widget sender) {
            forumApp.gotoThread(post);
        }
    }

}
