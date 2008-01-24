package com.apress.progwt.server.web.domain.validation;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.apress.progwt.server.domain.MailingListEntry;
import com.apress.progwt.server.service.InvitationService;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.service.impl.UserServiceImpl;
import com.apress.progwt.server.web.domain.CreateUserRequestCommand;

public class CreateUserRequestValidator implements Validator {
    private static final Logger log = Logger
            .getLogger(CreateUserRequestValidator.class);

    private static final int MIN_LENGTH = 3;

    private InvitationService invitationService;

    private UserService userService;

    private void doOpenIDValidation(CreateUserRequestCommand comm,
            Errors errors) {
        // Normalization happens in this getter
        if (!userService.couldBeOpenID(comm.getOpenIDusername())) {
            errors.rejectValue("openIDusername",
                    "invalid.openIDusername.nodots");
        }
        if (userService.exists(comm.getOpenIDusernameDoNormalization())) {
            errors.rejectValue("openIDusername",
                    "invalid.username.exists");
        }
        if (comm.getOpenIDusername().contains("=")) {
            errors.rejectValue("openIDusername",
                    "invalid.openIDusername.noinames");
        }
        if (userService.exists(comm.getOpenIDnickname())) {
            errors.rejectValue("openIDnickname",
                    "invalid.openIDnickname.exists");
        }
    }

    private void doStandardValidation(CreateUserRequestCommand comm,
            Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
                "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password2",
                "required");

        // username must have no '.' || '=' for openid compatibility
        if (userService.couldBeOpenID(comm.getUsername())) {
            errors.rejectValue("username", "invalid.username");
        }

        // spaces would break email functionality
        if (comm.getUsername().contains(" ")) {
            errors.rejectValue("username", "invalid.username.nospaces");
        }

        // generalemail compatibility
        if (!comm.getUsername().matches("([a-zA-Z0-9_\\-])+")) {
            errors.rejectValue("username", "invalid.username");
        }
        if (comm.getUsername().length() < MIN_LENGTH) {
            errors.rejectValue("username", "invalid.username.length");
        }
        if (comm.getPassword().length() < MIN_LENGTH) {
            errors.rejectValue("password", "invalid.password.length");
        }
        if (comm.getUsername().equals("anonymousUser")) {
            errors.rejectValue("username", "invalid.username");
        }

        // username != password
        if (comm.getPassword().equals(comm.getUsername())) {
            errors.rejectValue("username", "invalid.password.equalsuser");
        }

        // must have the same password
        if (!comm.getPassword().equals(comm.getPassword2())) {
            errors.rejectValue("password2", "invalid.password2");
        }

        if (userService.exists(comm.getUsername())) {
            errors.rejectValue("username", "invalid.username.exists");
        }
        if (UserServiceImpl.ANONYMOUS.equals(comm.getUsername())) {
            errors.rejectValue("username", "invalid.username.exists");
        }
    }

    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class clazz) {
        return clazz.equals(CreateUserRequestCommand.class);
    }

    /**
     * lookup messages from resource bundle
     * 
     * NOTE: topicService.createUser() .lowerCases() the username
     */
    public void validate(Object command, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "randomkey",
                "required");

        CreateUserRequestCommand comm = (CreateUserRequestCommand) command;

        log.info(comm.getOpenIDusername() + " " + comm.getUsername()
                + " " + comm.getRandomkey());

        boolean standard = comm.isStandard();
        boolean openID = comm.isOpenID();

        if (standard && openID) {
            errors.rejectValue("username", "invalid.username.both");
            errors.rejectValue("openIDusername", "invalid.username.both");
        }
        if (!standard && !openID) {
            errors.rejectValue("username", "invalid.username.oneorother");
            errors.rejectValue("openIDusername",
                    "invalid.username.oneorother");
        }

        if (standard) {
            doStandardValidation(comm, errors);
        } else if (openID) {
            doOpenIDValidation(comm, errors);
        }

        if (!invitationService.isKeyValid(comm.getRandomkey())) {
            errors.rejectValue("randomkey", "invalid");
        }
        MailingListEntry entry = invitationService.getEntryForKey(comm
                .getRandomkey());
        if (entry != null && entry.getSignedUpUser() != null) {
            errors.rejectValue("randomkey", "invalid.randomkey.exists");
        }

    }

}
