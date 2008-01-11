package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.User;

public class UserLink extends ExternalLink {
    public UserLink(User user) {
        super(user.getNickname(), "site/user/" + user.getNickname(), true);
    }

    public UserLink(User user, String prepend) {
        super(prepend + user.getNickname(), "site/user/"
                + user.getNickname(), true);
    }
}
