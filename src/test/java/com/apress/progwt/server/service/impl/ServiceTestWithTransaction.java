package com.apress.progwt.server.service.impl;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.TestingAuthenticationToken;

import com.apress.progwt.server.dao.hibernate.HibernateTransactionalTest;

public class ServiceTestWithTransaction extends
        HibernateTransactionalTest {

    private String username = "test";

    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        createSecureContext();
    }

    @Override
    protected void onTearDownInTransaction() throws Exception {
        super.onTearDownInTransaction();
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
