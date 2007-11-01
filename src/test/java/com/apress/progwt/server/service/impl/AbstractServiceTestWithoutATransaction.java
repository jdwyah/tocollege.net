package com.apress.progwt.server.service.impl;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public abstract class AbstractServiceTestWithoutATransaction extends
        AbstractDependencyInjectionSpringContextTests {

    private String username = "test-with-data";

    @Override
    protected String[] getConfigLocations() {

        PropertyConfigurator.configure(getClass().getResource(
                "/log4j.properties"));

        String path = "src/main/webapp/WEB-INF/";
        String pathh = "file:" + path;
        return new String[] {
                pathh + "applicationContext-acegi-security.xml",
                pathh + "applicationContext-hibernate.xml",
                pathh + "applicationContext.xml" };

    }

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        createSecureContext();
    }

    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
        destroySecureContext();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Creates an Acegi SecureContext and stores it on the ContextHolder
     */
    private void createSecureContext() {

        TestingAuthenticationToken auth = new TestingAuthenticationToken(
                username,
                username,
                new GrantedAuthority[] {
                        new GrantedAuthorityImpl("ROLE_TELLER"),
                        new GrantedAuthorityImpl("ROLE_PERMISSION_LIST") });

        SecurityContext secureContext = new SecurityContextImpl();
        secureContext.setAuthentication(auth);
        SecurityContextHolder.setContext(secureContext);

    }

    /**
     * Removed the Acegi SecureContext from the ContextHolder
     */
    private void destroySecureContext() {
        SecurityContextHolder.setContext(new SecurityContextImpl());
    }
}
