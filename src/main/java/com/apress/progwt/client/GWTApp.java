package com.apress.progwt.client;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.LoginService;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.status.StatusPanel;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.apress.progwt.client.service.remote.GWTSchoolService;
import com.apress.progwt.client.service.remote.GWTSchoolServiceAsync;
import com.apress.progwt.client.service.remote.GWTUserService;
import com.apress.progwt.client.service.remote.GWTUserServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gears.core.client.GearsException;
import com.google.gwt.gears.localserver.client.LocalServer;
import com.google.gwt.gears.localserver.client.ManagedResourceStore;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamReader;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GWTApp {

    private static final String MANIFEST_URL = "site/manifest.json";

    private int pageID;

    private GWTSchoolServiceAsync schoolService;
    private GWTUserServiceAsync userService;

    private ServiceCache serviceCache;
    private LoginService loginService;

    public GWTApp(int pageID) {
        this.pageID = pageID;

        // setup the StatusPanel. There will be just one DIV for this, no
        // matter how many apps we have in the same page.
        try {
            RootPanel status = RootPanel.get("gwt-status");
            if (status.getWidgetCount() == 0) {
                status.add(new StatusPanel());
            }
            StdAsyncCallback
                    .setManager((StatusPanel) status.getWidget(0));
        } catch (Exception e) {
            Log.error("Status Panel problem: ");
        }

        // create the Local Server after enough of a delay so that we try
        // to appear as lightweight as possible.
        new Timer() {
            @Override
            public void run() {
                try {
                    doLocalServer();
                } catch (GearsException e) {
                    Log.warn("LocalServer exception: " + e);
                }
            }
        }.schedule(10000);

    }

    private void doLocalServer() throws GearsException {
        LocalServer localServer = new LocalServer();

        final ManagedResourceStore managedResourceStore = localServer
                .createManagedResourceStore("ToCollege.net");
        try {
            managedResourceStore.setManifestURL(Interactive
                    .getRelativeURL(MANIFEST_URL));
        } catch (Exception e) {
            // expected in hosted mode. catches JS exception from setting
            // 8080 when running on 8888
            throw new GearsException(e.getMessage());
        }

        new Timer() {
            public void run() {
                switch (managedResourceStore.getUpdateStatus()) {
                case ManagedResourceStore.UPDATE_OK:
                    Log.info("UPDATE_OK "
                            + managedResourceStore.getCurrentVersion());
                    cancel();
                    break;
                case ManagedResourceStore.UPDATE_CHECKING:
                    Log.debug("Checking "
                            + managedResourceStore.getCurrentVersion());
                    break;
                case ManagedResourceStore.UPDATE_DOWNLOADING:
                    Log.debug("Downloading "
                            + managedResourceStore.getCurrentVersion());
                    break;
                case ManagedResourceStore.UPDATE_FAILED:
                    Log.warn("Fail "
                            + managedResourceStore.getCurrentVersion());
                    Log.warn(managedResourceStore.getLastErrorMessage());
                    cancel();
                    break;
                }

            }
        }.scheduleRepeating(2000);
        managedResourceStore.checkForUpdate();
    }

    protected String getLoadID() {
        return getLoadID(pageID);
    }

    protected String getPreLoadID() {
        return getPreLoadID(pageID);
    }

    private static String getLoadID(int id) {
        return "gwt-slot-" + id;
    }

    protected String getParam(String string) {
        try {
            Dictionary dictionary = Dictionary.getDictionary("Vars");
            return dictionary.get(string + "_" + pageID);
        } catch (Exception e) {
            Log.info("Couldn't find param: " + string);
            return null;
        }

    }

    private static String getPreLoadID(int id) {
        return "gwt-loading-" + id;
    }

    protected void loadError(Exception e) {

        Log.error("e: " + e);

        e.printStackTrace();

        VerticalPanel panel = new VerticalPanel();

        panel.add(new Label("Error"));
        panel.add(new Label(e.getMessage()));

        RootPanel.get(getPreLoadID()).setVisible(false);
        RootPanel.get(getLoadID()).add(panel);
    }

    protected void show(Widget panel) {
        show(pageID, panel);
    }

    public static void show(int id, Widget panel) {
        RootPanel.get(getPreLoadID(id)).setVisible(false);
        RootPanel.get(getLoadID(id)).add(panel);
    }

    public LoginService getLoginService() {
        return loginService;
    }

    /**
     * get the Object that has been serialized under the JavaScript var
     * name "serialized"
     * 
     * @return
     */
    protected Object getBootstrapped() {
        return getBootstrapped("serialized");
    }

    /**
     * 
     * Remember, the RemoteServiceProxy that you use must have a method
     * that returns the type that you wish to serialize. Otherwise, the
     * deserializer will not be created.
     * 
     * Cast the service into a RemoteServiceProxy, grab the stream reader
     * and deserialize.
     * 
     * @param name
     * @return
     */
    private Object getBootstrapped(String name) {
        String serialized = getParam(name);
        if (serialized == null) {
            Log.warn("No param " + name);
            return null;
        }

        try {
            return deserialize(serialized);
        } catch (Exception e) {
            Log.error("Bootstrap " + name + " Problem ", e);
            return null;
        }
    }

    public Object deserialize(String serialized) {

        ClientSerializationStreamReader c;
        Log.debug("Try to deserialize: " + serialized);
        try {
            c = getBootstrapService().createStreamReader(serialized);

            Object o = c.readObject();
            return o;
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    // this doesn't work. serialization strings are different depending on
    // which way they go
    // School s = new School();
    // String ser = serialize(s);
    // School e = (School) deserialize(ser);
    //
    // public String serialize(Object o) {
    // try {
    // ClientSerializationStreamWriter w = getBootstrapService()
    // .createStreamWriter();
    // w.writeObject(o);
    // Log.debug("Serialized: " + o + "\nto\n"
    // + w.toString());
    // return w.toString();
    // } catch (SerializationException e) {
    // throw new RuntimeException(e);
    // }
    // }

    private RemoteServiceProxy getBootstrapService() {
        return (RemoteServiceProxy) getSchoolService();
    }

    /**
     * call initServices if your GWTApp would like the asynchronous
     * services to be setup
     */
    protected void initServices() {

        schoolService = (GWTSchoolServiceAsync) GWT
                .create(GWTSchoolService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) schoolService;

        String pre = Interactive.getRelativeURL("service/");

        endpoint.setServiceEntryPoint(pre + "schoolService");

        userService = (GWTUserServiceAsync) GWT
                .create(GWTUserService.class);
        ServiceDefTarget endpointUser = (ServiceDefTarget) userService;
        endpointUser.setServiceEntryPoint(pre + "userService");

        if (schoolService == null || userService == null) {
            Log.error("Service was null.");
        }

        serviceCache = new ServiceCache(this);
        loginService = new LoginService(serviceCache);

    }

    public ServiceCache getServiceCache() {
        return serviceCache;
    }

    public GWTSchoolServiceAsync getSchoolService() {
        return schoolService;
    }

    public GWTUserServiceAsync getUserService() {
        return userService;
    }
}
