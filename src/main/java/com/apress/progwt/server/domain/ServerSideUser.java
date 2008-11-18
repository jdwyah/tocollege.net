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
package com.apress.progwt.server.domain;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.apress.progwt.client.domain.User;

/**
 * 
 * server side wrapper only. does NOT extend User
 * 
 * @author Jeff Dwyer
 * 
 */
public class ServerSideUser implements UserDetails {

    private static final Logger log = Logger
            .getLogger(ServerSideUser.class);

    private User user;

    public ServerSideUser(User u) {
        this.user = u;
    }

    public GrantedAuthority[] getAuthorities() {
        GrantedAuthority[] rtn = new GrantedAuthority[1];
        if (user.isSupervisor()) {
            log.debug("adding supervisor permissions");
            rtn[0] = new MyAuthority("ROLE_SUPERVISOR");
            return rtn;
        } else {
            rtn[0] = new MyAuthority("");
            return rtn;
        }

    }

    private class MyAuthority implements GrantedAuthority {
        private String auth;

        public MyAuthority(String auth) {
            this.auth = auth;
        }

        public String getAuthority() {
            return auth;
        }

        public int compareTo(Object o) {
            throw new RuntimeException("Unimplemented");
        }
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    public boolean isEnabled() {
        return user.isEnabled();
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return user.getId();
    }

    public Date getDateCreated() {
        return user.getDateCreated();
    }

}
