package com.apress.progwt.server.service.impl;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.TestingAuthenticationToken;

import com.apress.progwt.server.dao.hibernate.AbstractHibernateTransactionalTest;

public abstract class AbstractServiceTestWithTransaction extends
        AbstractHibernateTransactionalTest {

    private String username = "unit-test";

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
