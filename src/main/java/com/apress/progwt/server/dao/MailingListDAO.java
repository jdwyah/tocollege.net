package com.apress.progwt.server.dao;

import java.util.List;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.domain.MailingListEntry;
import com.apress.progwt.server.web.domain.MailingListCommand;

public interface MailingListDAO {

    void createEntry(MailingListCommand comm);

    MailingListEntry createEntry(String email, User inviter);

    List<MailingListEntry> getMailingList();

    void save(MailingListEntry invitation);

    MailingListEntry getEntryForKey(String randomkey);

    MailingListEntry getEntryById(Long id);

}
