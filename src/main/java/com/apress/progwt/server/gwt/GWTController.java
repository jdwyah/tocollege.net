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
package com.apress.progwt.server.gwt;

/**
 * Updated by Jeff Dwyer to add HibernateFilter and allow explicit
 * Serialization
 * 
 * Copyright 2006 George Georgovassilis <g.georgovassilis[at]gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.apress.progwt.client.domain.GWTSerializer;
import com.apress.progwt.client.exception.InfrastructureException;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;

/**
 * Simple spring controller that merges GWT's {@link RemoteServiceServlet},
 * the {@link Controller} and also implements the {@link RemoteService}
 * interface so as to be able to directly delegate RPC calls to extending
 * classes.
 * 
 * @author g.georgovassilis
 * 
 */

public class GWTController extends RemoteServiceServlet implements
ServletContextAware, Controller, RemoteService, GWTSerializer {

    private static final Logger log = Logger
    .getLogger(GWTController.class);

    private static final long serialVersionUID = 5399966488983189122L;

    private boolean serializeEverything = false;

    public void setSerializeEverything(boolean serializeEverything) {
        this.serializeEverything = serializeEverything;
    }

    @Override
    public String processCall(String payload)
    throws SerializationException {
        try {

            RPCRequest rpcRequest = RPC.decodeRequest(payload, this
                    .getClass(), this);
            ServerSerializationStreamWriter_1_5_3 writer = getWriter(rpcRequest);

            return RPC1524.invokeAndEncodeResponse(this, rpcRequest
                    .getMethod(), rpcRequest.getParameters(), writer);

        } catch (IncompatibleRemoteServiceException ex) {
            log
            .error(
                    "An IncompatibleRemoteServiceException was thrown while processing this call.",
                    ex);
            return RPC.encodeResponseForFailure(null, ex);
        } catch (Exception e){
            log
            .error(
                    "An Exception was thrown while processing this call.",
                    e);
            return RPC.encodeResponseForFailure(null, e);
        }
    }

    private ServerSerializationStreamWriter_1_5_3 getWriter(
            RPCRequest rpcRequest) {
        return getWriter(rpcRequest.getSerializationPolicy());
    }

    /**
     * would prefer to call doGetSerializationPolicy() so that we could
     * use the new serializer policies, but not sure how to get the
     * necessary parameters
     * 
     * @return
     */
    private ServerSerializationStreamWriter_1_5_3 getWriter() {
        return getWriter(OneFourTenSerializationPolicy.getInstance());
    }

    private ServerSerializationStreamWriter_1_5_3 getWriter(
            SerializationPolicy serializationPolicy) {

        ServerSerializationStreamWriter_1_5_3 writer = new ServerSerializationStreamWriter_1_5_3(
                serializationPolicy);

        writer.setValueWriter(Object.class, new ValueWriter() {
            public void write(ServerSerializationStreamWriter_1_5_3 stream,
                    Object instance) throws SerializationException {
                stream.writeObject(HibernateFilter.filter(instance));
            }
        });
        return writer;
    }

    /**
     * implement GWTSerializer. Used for GWT host pages that want to
     * serialize objects to bootstrap GWT and prevent needing a startup
     * async call.
     */
    public String serializeObject(Object object, Class<?> clazz)
    throws InfrastructureException {

        ServerSerializationStreamWriter_1_5_3 serializer = getWriter();

        try {
            serializer.serializeValue(object, clazz);
        } catch (SerializationException e) {
            throw new InfrastructureException(e);
        }
        String bufferStr = "//OK" + serializer.toString();
        return bufferStr;
    }

    /**
     * Normal GWT Serialization requires that we do a GWT compile to
     * create the serialization whitelist. Unfortunately this means we
     * can't just restart jetty and have this Controller Serialize, unless
     * we do a gwt compile, which slows us down considerably. Solutions is
     * to use our funky laissez faire 1.4.10 (RC1) style serialization
     * policy to serialize everything which means we don't need to
     * recompile all the gwt stuff just to restart jetty.
     * 
     * Use the 'serializeEverything' variable which is set differently on
     * test and deployment machines to go to regular 1.5 serialization
     * when deployed.
     */
    @Override
    protected SerializationPolicy doGetSerializationPolicy(
            HttpServletRequest request, String moduleBaseURL,
            String strongName) {
        if (serializeEverything) {
            log.warn("Using 1.4.10 (RC1) style serializaion.");
            return OneFourTenSerializationPolicy.getInstance();
        } else {
            log.debug("Using Standard Serialization.");
            return super.doGetSerializationPolicy(request, moduleBaseURL,
                    strongName);
        }
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try{
            doPost(request, response);            
            return null;
        }catch (Exception ex) {
            log.error("handleRequest error "+ex);
            //ex.printStackTrace();
            return null;
        }
    }

    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

}
