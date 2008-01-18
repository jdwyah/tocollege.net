package com.apress.progwt.client.forum;

import com.apress.progwt.client.college.gui.UserLink;
import com.apress.progwt.client.college.gui.ext.TableWithHeaders;
import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.dto.PostsList;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ForumDisplay extends Composite {

    private static final DateTimeFormat df = DateTimeFormat
            .getFormat("MMM, d yyyy HH:mm");

    private VerticalPanel allPosts;
    private ForumApp forumApp;

    public ForumDisplay(ForumApp forumApp) {
        this.forumApp = forumApp;
        allPosts = new VerticalPanel();
        allPosts.setStylePrimaryName("ForumPosts");
        initWidget(allPosts);
    }

    public void load(int start, PostsList result, ForumTopic original,
            ForumTopic topic, boolean isReply, int maxPerPage) {

        allPosts.clear();

        System.out.println("ORIGINAL " + original);

        if (original != null) {
            Hyperlink originalL = new Hyperlink("Forum: "
                    + original.getUniqueForumID(), original
                    .getUniqueForumID());
            allPosts.add(originalL);
        }

        if (topic.doThreadListView()) {
            for (ForumPost post : result.getPosts()) {
                allPosts.add(new PostDisplay(post));
            }
        } else {
            TableWithHeaders table = new TableWithHeaders(result
                    .getPosts().size(), "Thread", "Replies", "Date",
                    "Author");
            int row = 0;
            for (ForumPost post : result.getPosts()) {
                addShortDisplay(table, row, post);
                row++;
            }
            allPosts.add(table);
        }

        if (result.getPosts().size() == 0) {
            allPosts.add(new Label("No Posts Yet"));
        }

        CreatePostButton createB = new CreatePostButton(forumApp,
                isReply, topic);
        allPosts.add(createB);

        ForumControlPanel fcb = new ForumControlPanel(topic, result,
                start, maxPerPage);
        allPosts.add(fcb);

    }

    private void addShortDisplay(TableWithHeaders table, int row,
            ForumPost post) {

        Hyperlink postT = new Hyperlink(post.getPostTitle(), post
                .getUniqueForumID());

        Label replies = new Label("" + post.getReplyCount());

        UserLink author = new UserLink(post.getAuthor());

        Label date = new Label(df.format(post.getDate()));
        date.setStylePrimaryName("date");

        table.setWidget(row, 0, postT);
        table.setWidget(row, 1, replies);
        table.setWidget(row, 2, date);
        table.setWidget(row, 3, author);
        table.getColumnFormatter().setStyleName(0, "title");
        table.getColumnFormatter().setStyleName(1, "replies");
        table.getColumnFormatter().setStyleName(3, "author");
        if (row % 2 == 1) {
            table.getRowFormatter().setStyleName(row, "Odd");
        }

    }

    /**
     * Note the use of new HTML(). This exposes us to XSS attacks. We need
     * to be very sure that the postString() is not going to bite us here.
     * See the HTMLInputFilter usage in SaveForumPostCommand() for our
     * attempt to stop these attacks.
     * 
     * @author Jeff Dwyer
     * 
     */
    private class PostDisplay extends Composite {

        public PostDisplay(ForumPost post) {

            HorizontalPanel mainP = new HorizontalPanel();

            FlowPanel authorSide = new FlowPanel();
            authorSide.setStylePrimaryName("AuthorSide");
            VerticalPanel postSide = new VerticalPanel();
            postSide.setStylePrimaryName("PostSide");

            authorSide.add(new Label("Author: "));
            UserLink author = new UserLink(post.getAuthor());
            authorSide.add(author);

            Label date = new Label(df.format(post.getDate()));
            date.addStyleDependentName("Date");
            authorSide.add(date);

            Label postT = new Label(post.getPostTitle());
            postT.addStyleDependentName("title");
            postSide.add(postT);

            Label postS = new HTML(post.getPostString());
            postSide.add(postS);

            mainP.add(authorSide);
            mainP.add(postSide);

            initWidget(mainP);
            mainP.setStylePrimaryName("ForumPost");
        }
    }

}
