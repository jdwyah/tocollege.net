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
package com.apress.progwt.server.web.controllers;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class ErrorController extends BasicController {

    private static final Logger log = Logger
    .getLogger(ErrorController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        log.error("\n\n\nErrorController req "+req.getPathInfo()+"\n"+req.getParameterNames().toString()+" "+req.getQueryString()+"\n");


        if(log.isDebugEnabled()){
            for(Object s :req.getParameterMap().keySet()){
                log.debug("param "+s);
            }
            Enumeration attrs = req.getAttributeNames();
            while(attrs.hasMoreElements()){
                String attr = (String) attrs.nextElement();
                log.debug("attr: "+attr+" "+req.getAttribute(attr));
            }
        }


        ModelAndView m = super.handleRequestInternal(req, arg1);        
        String code=req.getParameter("code");
        if(code != null){
            Object uri = req.getAttribute("javax.servlet.error.request_ uri");
            m.addObject("message", code+" error for page "+uri);
        }
        return m;
    }

}
