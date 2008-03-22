package com.apress.progwt.server.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.activation.FileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * 
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
 *
 * @author Jeff Dwyer
 *
 * FileServlet is used as a surrogate for simple jetty/tomcat file serving capabilities. 
 * The problem we're trying to solve is that the dispatcher-servlet maps all .html files 
 * and /service/* This works ok, except that our dispattcher servlet then matches the GWT
 * HTML files as well. This servlet overrides that and just serves those files directly. 
 * 
 * Ideall this would be done within the dispatcher servlet or with a theoretical <servlet-mapping><excludes> element.  
 *
 */
public class FileServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(FileServlet.class);
    
    private int bufferSize = 1024;
    private String rootPath;

    /**
     * Initialize the servlet, and determine the webapp root.
     */
    public void init() throws ServletException {        
        // determine the root of this webapp
        ServletContext context = getServletContext();
        rootPath = context.getRealPath("/");
        if (rootPath == null) {
            throw new ServletException("Cannot find the real path of this webapp, probably using a non-extracted WAR");
        }
        if (rootPath.endsWith("/")) {
            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }
    }

    /**
     * Transfer the file.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        FileChannel in = null;
        try {

            // find the location of the file
            String contextPath = request.getContextPath();
            String relativePath = request.getRequestURI().substring(contextPath.length());
            
            log.debug("Real Path "+rootPath+"||"+relativePath);
            
            // check the file and open it
            File file = new File(rootPath,relativePath);
            
            log.debug("File "+file.getAbsolutePath());
            if (!file.isFile()) {
                log.info("File not found: " + file.getAbsolutePath());
                log("File not found: " + file.getAbsolutePath());
                throw new FileNotFoundException(file.getAbsolutePath());
            }

            // check for modifications
            long ifModifiedSince = request.getDateHeader("If-Modified-Since");
            long lastModified = file.lastModified();
            if (ifModifiedSince != -1 && lastModified < ifModifiedSince) {
                throw new NotModifiedException();
            }

            // setup IO streams
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            in = new FileInputStream(file).getChannel();

            
            // start returning the response
            //response.setContentType(getContentTypeFor(file.getName()));
            OutputStream out = response.getOutputStream();

            // read the bytes, returning them in the response
            while (in.read(buffer) != -1) {
                out.write(buffer.array(), 0, buffer.position());
                buffer.clear();
            }
            out.close();

        } catch (NotModifiedException e) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        } catch (FileNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException ignore) {
            }
        }
    }

    /**
     * Static files treat GET and POST requests the same way.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Return the content-type the would be returned for this file name.
     */
    public String getContentTypeFor(String fileName) {
  
        // everything else
        FileTypeMap typeMap = FileTypeMap.getDefaultFileTypeMap();
        return typeMap.getContentType(fileName);
    }



    /**
     * An exception when the source object has not been modified.
     * While this condition is not a failure, it is a break from the normal flow of execution.
     */
    private static class NotModifiedException extends IOException {
        public NotModifiedException() {
        }
    }

}