package com.apress.progwt.server.service;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.InfrastructureException;
import com.apress.progwt.server.domain.MailingListEntry;
import com.apress.progwt.server.web.domain.MailingListCommand;

public interface InvitationService {

    void requestInvitation(MailingListCommand comm);

    void createAndSendInvitation(String email, User inviter)
            throws BusinessException, InfrastructureException;

    boolean isKeyValid(String randomkey);

    MailingListEntry getEntryForKey(String randomkey);

    void saveSignedUpUser(String randomkey, User u);

    void sendInvite(final MailingListEntry invitation)
            throws InfrastructureException;

    MailingListEntry getEntryById(Long id);

    List<MailingListEntry> getMailingList();

    void setMailSender(JavaMailSender sender);

    String getSalt();
}
