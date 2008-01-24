package com.apress.progwt.server.web.domain;

import org.springframework.util.StringUtils;

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
     */
    public String getOpenIDusernameDoNormalization() {
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
