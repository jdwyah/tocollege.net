package com.apress.progwt.server.web.domain.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.apress.progwt.server.web.domain.PasswordChangeCommand;

public class PasswordChangeCommandValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(PasswordChangeCommand.class);
    }

    /**
     * lookup messages from resource bundle
     */
    public void validate(Object command, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword",
                "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword",
                "required");

        PasswordChangeCommand comm = (PasswordChangeCommand) command;

        if (comm.getNewPassword().equals(comm.getOldPassword())) {
            errors.rejectValue("newPassword", "invalid");
        }

    }

}
