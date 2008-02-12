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
