package com.apress.progwt.server.dao.hibernate;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class AbstractHibernateTransactionalTest extends
        AbstractTransactionalDataSourceSpringContextTests {

    @Override
    protected String[] getConfigLocations() {

        PropertyConfigurator.configure(getClass().getResource(
                "/log4j.properties"));
        //
        // URL u = getClass().getResource("/ehcache.xml");
        // System.out.println(u);
        // File f = new File(u.getFile());
        // System.out.println("Exists: " + f.exists());
        // EhCacheManagerFactoryBean factory = new
        // EhCacheManagerFactoryBean();
        // try {
        //
        // CacheManager.create("/ehcache.xml");
        // System.out.println("OK");
        // } catch (Exception e) {
        // System.out.println("EX " + e);
        // }
        // try {
        // CacheManager.create(u);
        //
        // System.out.println("OK");
        // } catch (Exception e) {
        // System.out.println("EX " + e);
        // }
        // factory.setConfigLocation(new
        // ClassPathResource("ehcache.xml"));
        // EhCacheFactoryBean bean = new EhCacheFactoryBean();
        // bean.setCacheManager(factory);
        // bean
        // .setCacheName("com.apress.progwt.server.service.UserTokenCache");

        String path = "src/main/webapp/WEB-INF/";
        String pathh = "file:" + path;
        return new String[] {
                pathh + "applicationContext-acegi-security.xml",
                pathh + "applicationContext-hibernate.xml",
                pathh + "applicationContext.xml" };

    }
}
