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
package com.apress.progwt.server.web.domain;

import org.openid4java.discovery.DiscoveryException;
import org.springframework.util.StringUtils;

import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.service.impl.UserServiceImpl;

public class CreateUserRequestCommand {

    private String username;
    private String openIDusername;
    private String openIDnickname;
    private String password;
    private String password2;
    private String email;
    private String randomkey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getOpenIDnickname() {
        return openIDnickname;
    }

    public void setOpenIDnickname(String openIDnickname) {
        this.openIDnickname = openIDnickname;
    }

    /**
     * NOTE: this getter does NOT OpenID normalize
     * 
     * @return
     */
    public String getOpenIDusername() {
        return openIDusername;
    }

    /**
     * NOTE: this getter does OpenID normalization
     * 
     * @return
     * @throws DiscoveryException
     */
    public String getOpenIDusernameDoNormalization() throws SiteException {
        return UserServiceImpl.normalizeUrl(openIDusername);
    }

    public void setOpenIDusername(String openIDusername) {
        this.openIDusername = openIDusername;
    }

    public String getRandomkey() {
        return randomkey;
    }

    public void setRandomkey(String randomkey) {
        this.randomkey = randomkey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOpenID() {
        return StringUtils.hasText(getOpenIDusername());
    }

    public boolean isStandard() {
        return StringUtils.hasText(getUsername());
    }

}
