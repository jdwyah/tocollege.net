package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.client.forum.ForumTopic;

/**
 * Because this will reference many objects that are stored in the DB, we
 * need to do loads on all of those objects in order to ensure that we
 * don't get 'multiple obejcts in the session' errors from hibernate.
 * 
 * Take care of those loads using the AbstractCommand pattern that we've
 * developed.
 * 
 * If it's an edit, we need to be sure to copy over all important,
 * changable fields to the toSave object which we'll retrieve from the DB.
 * 
 * @author Jeff Dwyer
 * 
 */
public class SaveForumPostCommand extends AbstractCommand implements
        Serializable {

    private ForumPost forumPost;

    private long authorID;

    private long topicID = -1;

    private long threadID = -1;

    private ForumPost toSave;

    public SaveForumPostCommand() {
    }

    public SaveForumPostCommand(ForumPost forumPost) {
        super(forumPost, forumPost.getAuthor(), forumPost.getTopic(),
                forumPost.getThreadPost());

        this.forumPost = forumPost;
        this.topicID = forumPost.getTopic().getId();
        this.authorID = forumPost.getAuthor().getId();

        if (forumPost.getThreadPost() != null) {
            this.threadID = forumPost.getThreadPost().getId();
        }
    }

    @Override
    public String toString() {
        return "SaveForumPostCommand ForumPost " + forumPost;
    }

    public void execute(CommandService commandService)
            throws SiteException {

        commandService.save(forumPost);

        User author = commandService.get(User.class, authorID);

        ForumTopic loadedTopic = commandService.get(forumPost
                .getTopicClass(), topicID);
        ForumPost threadP = commandService.get(ForumPost.class, threadID);

        System.out.println(toString());

        toSave = commandService.get(ForumPost.class, forumPost.getId());

        // if it's a new creation, just use the one we wanted to save
        if (toSave == null) {
            toSave = forumPost;
        }

        toSave.setAuthor(author);
        toSave.setTopic(loadedTopic);
        toSave.setThreadPost(threadP);

        toSave.setDate(forumPost.getDate());
        toSave.setPostString(forumPost.getPostString());
        toSave.setPostTitle(forumPost.getPostTitle());

        // update the inverse side of the relationship
        if (threadP != null) {
            threadP.getReplies().add(toSave);
        }

        commandService.save(toSave);

    }

    /**
     * used in testing accessor
     * 
     * @return
     */
    public ForumPost getToSave() {
        return toSave;
    }

}
