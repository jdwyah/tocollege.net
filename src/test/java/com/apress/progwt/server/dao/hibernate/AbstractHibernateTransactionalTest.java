package com.apress.progwt.server.dao.hibernate;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class AbstractHibernateTransactionalTest extends
        AbstractTransactionalDataSourceSpringContextTests {

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

}
