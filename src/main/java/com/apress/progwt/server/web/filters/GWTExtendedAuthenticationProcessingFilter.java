package com.apress.progwt.server.web.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

/**
 * The basic AuthenticationProcessingFilter class will either forward us
 * to the 'savedRequest' page on login, or it will send us to the
 * defaultTargetURL. This is ok normally, but we'd like to be able to post
 * a from from GWT to login. When we do that, it's inefficeint (and plays
 * havoc with hosted mode) to return the entire defaultTargetURL (in this
 * case the MyPage). Let's give ourselves the ability to forward to a
 * different page if there's a get parameter in the URL. There we can
 * simply return 'OK' so that GWT knows it worked. See the appropriate
 * mapping in SimpleAnnotatedController.java
 * 
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public class GWTExtendedAuthenticationProcessingFilter extends
        AuthenticationProcessingFilter {

    private String gwtLoginTargetURL;

    /**
     * if there's a special gwt property, use the GWT target. Otherwise
     * just call super().
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request) {

        String gwt = request.getParameter("gwt");
        if (gwt != null) {
            return gwtLoginTargetURL;
        }

        return super.determineTargetUrl(request);
    }

    /**
     * URL that we'll pass success to if ?gwt= is appended to login URL
     * 
     * @param gwtLoginTargetURL
     */
    public void setGwtLoginTargetURL(String gwtLoginTargetURL) {
        this.gwtLoginTargetURL = gwtLoginTargetURL;
    }

}
