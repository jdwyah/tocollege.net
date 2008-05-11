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
package com.apress.progwt.server.web.controllers.facebook;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.util.HostPrecedingPropertyPlaceholderConfigurer;
import com.apress.progwt.server.web.controllers.ControllerUtil;
import com.facebook.api.FacebookException;
import com.facebook.api.FacebookParam;
import com.facebook.api.FacebookRestClient;
import com.facebook.api.schema.FriendsGetResponse;

/**
 * 
 * @author Jeff Dwyer
 */
@Controller
public class FacebookController {
    private static final Logger log = Logger
            .getLogger(FacebookController.class);

    private UserService userService;
    private SchoolService schoolService;

    @Autowired
    @Qualifier(value = "propertyConfigurer")
    private HostPrecedingPropertyPlaceholderConfigurer hostConfigurer;

    

    public void setHostConfigurer(
            HostPrecedingPropertyPlaceholderConfigurer hostConfigurer) {
        this.hostConfigurer = hostConfigurer;
    }
    
    @RequestMapping("/facebook/infinite.html")
    public String infiniteHandler(ModelMap map,HttpServletRequest req,HttpServletResponse resp) throws FacebookException, IOException {
        
        String apiKey = hostConfigurer.resolvePlaceholder("env.facebook.apikey");
        String secret = hostConfigurer.resolvePlaceholder("env.facebook.secret");
        
        log.debug("apikey: "+apiKey);
        
        Facebook f = new Facebook(req,resp,apiKey,secret);
        
        if(f.requireLogin("")){
            log.info("require login redirect");
        }
        
        map.addAttribute("userID", f.getUser());
        
        map.addAttribute("sessionID", f.getFacebookRestClient().get_sessionKey());
        
        return "facebook/infinite";
        
    }
    @RequestMapping("/facebook/updateprofile.html")
    public ModelAndView updateprofileHandler(ModelMap map,HttpServletRequest req,HttpServletResponse resp) throws FacebookException, IOException {
        
        String apiKey = hostConfigurer.resolvePlaceholder("env.facebook.apikey");
        String secret = hostConfigurer.resolvePlaceholder("env.facebook.secret");
        
        log.debug("apikey: "+apiKey);
        
        Facebook f = new Facebook(req,resp,apiKey,secret);
      
        updateFBML(f);
        
        return new ModelAndView("facebook/canvas","message","profile update ok");
    }

    
    @RequestMapping("/facebook/canvas.html")
    public ModelAndView facebookHandler(HttpServletRequest req,HttpServletResponse resp) throws FacebookException, IOException {
        
        String apiKey = hostConfigurer.resolvePlaceholder("env.facebook.apikey");
        String secret = hostConfigurer.resolvePlaceholder("env.facebook.secret");
        
        log.debug("apikey: "+apiKey);
        
        Facebook f = new Facebook(req,resp,apiKey,secret);
        
        
        log.debug("User: "+f.getUser());
        log.debug("Is Added: "+f.isAdded());
        log.debug("fbparams size: "+f.fbParams.size());
        for(String key : f.fbParams.keySet()){
            log.debug(key+":"+f.fbParams.get(key));
        }
        log.debug("AAAA: "+f.fbParams.get(FacebookParam.ADDED.getSignatureName()));
        
        
        if(f.requireLogin("")){
            log.info("require login redirect");
            return null;
        }
       
        
        
        
        FacebookRestClient client = f.getFacebookRestClient();
       
        
        client.friends_get();
        
        FriendsGetResponse response = (FriendsGetResponse)client.getResponsePOJO();
        List<Long> friends = response.getUid();
        
        ModelMap rtn = ControllerUtil.getModelMap(req, userService);

        User user = userService.getUserByNicknameFullFetch("test");

        rtn.addAttribute("viewUser", user);
        rtn.addAttribute("friends", friends);

        return new ModelAndView("facebook/canvas",rtn);

    }
    
    
    
    
    /**
     * update profile of the logged in user
     * 
     * @param client
     * @throws IOException 
     */
    private void updateFBML(Facebook f) throws IOException {
        

        
        if(f.requireAdd("")){
            log.info("require add redirect");      
        }                
        
        
        FacebookRestClient client = f.getFacebookRestClient();
        
        Long userID = client.get_userId();
        
        String profileFBML = "profile from tocollege";

        String profileActionFBML = "action fbml";
        
        log.debug("User ID "+userID);

        try {
            log.debug("setFBML");
            client.profile_setFBML(profileFBML, profileActionFBML);

        } catch (FacebookException e) {
            if(e.getCode() == 102){
                log.warn("102 error requiring add...");
            }
            log.error(e);
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }
    
}
