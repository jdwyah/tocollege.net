package com.apress.progwt.server.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.InvitationService;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.web.domain.CreateUserRequestCommand;

public class CreateUserController extends SimpleFormController {
    private static final Logger log = Logger
            .getLogger(CreateUserController.class);

    private UserService userService;
    private InvitationService invitationService;

    @Override
    protected Object formBackingObject(HttpServletRequest req)
            throws Exception {
        CreateUserRequestCommand com = new CreateUserRequestCommand();

        com.setEmail(req.getParameter("email"));
        com.setRandomkey(req.getParameter("secretkey"));

        return com;
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request,
            Object command, BindException errors) throws Exception {
        log.debug("OnBindAndValidate");
        log.debug("error count:" + errors.getAllErrors().size());

        for (Object b : errors.getAllErrors()) {
            log.debug("err: " + b);
        }

    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command,
            BindException errors) throws Exception {

        CreateUserRequestCommand comm = (CreateUserRequestCommand) command;

        log.debug("SUBMIT " + comm.isOpenID());

        User u = userService.createUser(comm);

        invitationService.saveSignedUpUser(comm.getRandomkey(), u);

        String successStr = "Thanks " + u.getUsername()
                + " your account is setup and you're ready to login!";

        log.info("returning message " + successStr + " to "
                + getSuccessView());

        return new ModelAndView(getSuccessView(), "message", successStr);

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

}
