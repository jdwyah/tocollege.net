package com.apress.progwt.server.web.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.apress.progwt.server.service.InvitationService;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.util.CryptUtils;
import com.apress.progwt.server.web.domain.CreateUserRequestCommand;

public class SignupIfPossibleController extends AbstractController {

    private static final Logger log = Logger
            .getLogger(SignupIfPossibleController.class);

    private InvitationService invitationService;
    private UserService userService;
    private String signupView;
    private String mailingListView;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        if (userService.nowAcceptingSignups()) {
            Map<String, Object> model = new HashMap<String, Object>();
            CreateUserRequestCommand comm = new CreateUserRequestCommand();

            Calendar c = Calendar.getInstance();
            c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
            comm.setRandomkey(CryptUtils.hashString(invitationService
                    .getSalt()
                    + c.get(Calendar.DAY_OF_WEEK_IN_MONTH)));

            model.put("hideSecretKey", true);
            model.put("command", comm);
            return new ModelAndView(getSignupView(), model);
        } else {
            return new ModelAndView(getMailingListView());
        }
    }

    public String getSignupView() {
        return signupView;
    }

    @Required
    public void setSignupView(String signupView) {
        this.signupView = signupView;
    }

    public String getMailingListView() {
        return mailingListView;
    }

    @Required
    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @Required
    public void setMailingListView(String mailingListView) {
        this.mailingListView = mailingListView;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
