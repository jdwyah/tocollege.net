package com.apress.progwt.server.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.apress.progwt.server.service.InvitationService;
import com.apress.progwt.server.web.domain.MailingListCommand;
import com.apress.progwt.server.web.domain.validation.MailingListCommandValidator;

public class MailingListController extends SimpleFormController {
    private static final Logger log = Logger
            .getLogger(MailingListController.class);

    private InvitationService invitationService;

    public MailingListController() {
        setCommandClass(MailingListCommand.class);
        setValidator(new MailingListCommandValidator());
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest req,
            HttpServletResponse arg1, Object obj, BindException arg3)
            throws Exception {

        MailingListCommand comm = (MailingListCommand) obj;

        comm.setUserAgent(req.getHeader("User-Agent"));
        comm.setReferer(req.getHeader("Referer"));
        comm.setHost(req.getHeader("Host"));

        log.debug("email: " + comm.getEmail());
        log.debug("user-agent: " + comm.getUserAgent());

        invitationService.requestInvitation(comm);

        String successStr = "Thanks "
                + comm.getEmail()
                + " we'll let you know when you can sign up for an account.";

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", successStr);
        model.put("command", new MailingListCommand());

        return new ModelAndView(getSuccessView(), model);

    }

    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

}
