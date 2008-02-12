/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
