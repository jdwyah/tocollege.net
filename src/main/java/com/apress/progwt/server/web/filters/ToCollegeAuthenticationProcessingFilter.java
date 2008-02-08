package com.apress.progwt.server.web.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

public class ToCollegeAuthenticationProcessingFilter extends
        AuthenticationProcessingFilter {

    private String gwtLoginTargetURL;

    @Override
    protected String determineTargetUrl(HttpServletRequest request) {

        String gwt = request.getParameter("gwt");
        if (gwt != null) {
            return gwtLoginTargetURL;
        }

        return super.determineTargetUrl(request);
    }

    public void setGwtLoginTargetURL(String gwtLoginTargetURL) {
        this.gwtLoginTargetURL = gwtLoginTargetURL;
    }

}
