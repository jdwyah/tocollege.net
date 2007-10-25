package com.apress.progwt.server.service.impl;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.InfrastructureException;
import com.apress.progwt.server.dao.MailingListDAO;
import com.apress.progwt.server.domain.MailingListEntry;
import com.apress.progwt.server.service.InvitationService;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.util.CryptUtils;
import com.apress.progwt.server.web.domain.MailingListCommand;

import freemarker.template.Template;

@Transactional
public class InvitationServiceImpl implements InvitationService {
    private static final Logger log = Logger
            .getLogger(InvitationServiceImpl.class);

    private FreeMarkerConfigurer configurer = null;
    private String from;
    private String invitationTemplate;

    private MailingListDAO mailingListDAO;
    private JavaMailSender mailSender;
    private String masterkey;
    private String salt;

    private UserService userService;

    public MailingListEntry getEntryForKey(String randomkey) {
        return mailingListDAO.getEntryForKey(randomkey);
    }

    /**
     * PEND low SignupIfPossibleController.CHEAT should be a MD5(timestamp +
     * pass) that we check on our end, but...
     * 
     */
    public boolean isKeyValid(String randomkey) {
        if (randomkey == null) {
            return false;
        }
        return (getEntryForKey(randomkey) != null)
                || randomkey.equals(masterkey)
                || isValidTimestampKey(randomkey);
    }

    private boolean isValidTimestampKey(String randomkey) {
        Calendar c = Calendar.getInstance();
        c.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        String preCrypt = salt + c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        String crypt = CryptUtils.hashString(preCrypt);
        return crypt.equals(randomkey);
    }

    public void requestInvitation(MailingListCommand comm) {
        mailingListDAO.createEntry(comm);
    }

    public void saveSignedUpUser(String randomkey, User u) {
        // may be null for masterkey overrides of the system
        MailingListEntry entry = getEntryForKey(randomkey);
        if (entry != null) {
            entry.setSignedUpUser(u);
            mailingListDAO.save(entry);
        }
    }

    /**
     * See
     * http://opensource.atlassian.com/confluence/spring/display/DISC/Sending+FreeMarker-based+multipart+email+with+Spring
     */
    public void createAndSendInvitation(final String email,
            final User inviter) throws BusinessException,
            InfrastructureException {

        if (inviter.getInvitations() < 1) {
            throw new BusinessException("No invites available for user.");
        }

        log.debug("before create entry");

        final MailingListEntry invitation = mailingListDAO.createEntry(
                email, inviter);

        log.debug("subtract entry " + inviter.getInvitations());

        userService.addInvitationsTo(inviter, -1);

        log.debug("send invite " + inviter.getInvitations());

        sendInvite(invitation);

        log.debug("sent " + inviter.getInvitations());
    }

    public void sendInvite(final MailingListEntry invitation)
            throws InfrastructureException {
        // send mail
        try {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage)
                        throws Exception {
                    MimeMessageHelper message = new MimeMessageHelper(
                            mimeMessage);
                    message.setTo(invitation.getEmail());
                    message.setFrom(from);
                    message.setSubject("MyHippocampus Invitation");

                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("inviter", invitation.getInviter());
                    model.put("randomkey", invitation.getRandomkey());
                    model.put("email", invitation.getEmail());

                    Template textTemplate = configurer.getConfiguration()
                            .getTemplate(invitationTemplate);
                    final StringWriter textWriter = new StringWriter();

                    textTemplate.process(model, textWriter);

                    message.setText(textWriter.toString(), true);

                    log.info("Inviting: " + invitation.getEmail());
                    log.debug("From: " + from);
                    log.debug("Message: " + textWriter.toString());

                }
            };
            this.mailSender.send(preparator);

            invitation.setSentEmailOk(true);
            mailingListDAO.save(invitation);

        } catch (Exception e) {
            log.error(e);
            throw new InfrastructureException(e);
        }
    }

    @Required
    public void setConfigurer(FreeMarkerConfigurer configuration) {
        this.configurer = configuration;
    }

    @Required
    public void setFrom(String from) {
        this.from = from;
    }

    @Required
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Required
    public void setInvitationTemplate(String invitationTemplate) {
        this.invitationTemplate = invitationTemplate;
    }

    @Required
    public void setMailingListDAO(MailingListDAO mailingListDAO) {
        this.mailingListDAO = mailingListDAO;
    }

    @Required
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Required
    public void setMasterkey(String masterkey) {
        this.masterkey = masterkey;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public MailingListEntry getEntryById(Long id) {
        return mailingListDAO.getEntryById(id);
    }

    public List<MailingListEntry> getMailingList() {
        return mailingListDAO.getMailingList();
    }

    public String getSalt() {
        return salt;
    }

}
