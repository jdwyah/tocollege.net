package com.apress.progwt.server.web.controllers;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.apress.progwt.server.util.HostPrecedingPropertyPlaceholderConfigurer;

public class PropertiesSupport {
    
    @Autowired
    @Qualifier(value = "propertyConfigurer2")
    private HostPrecedingPropertyPlaceholderConfigurer hostConfigurer;

    @Autowired
    private Properties properties;
    

    public void setHostConfigurer(
            HostPrecedingPropertyPlaceholderConfigurer hostConfigurer) {
        this.hostConfigurer = hostConfigurer;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    
    protected String getProperty(String name){
        return hostConfigurer.resolvePlaceholder(
                name, properties);
    }

}
