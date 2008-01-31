package com.apress.progwt.server.web.controllers;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apress.progwt.server.util.HostPrecedingPropertyPlaceholderConfigurer;
import com.apress.progwt.server.util.RandomUtils;

/**
 * Displays the ENTIRE directory. Which isn't so good. Could filter on
 * user-agent...
 * 
 * @author Jeff Dwyer
 * 
 */
@Controller
@RequestMapping("/manifest.json")
public class GearsLocalServerManifestController {
    private static final Logger log = Logger
            .getLogger(GearsLocalServerManifestController.class);

    @Autowired
    @Qualifier(value = "propertyConfigurer2")
    private HostPrecedingPropertyPlaceholderConfigurer hostConfigurer;

    @Autowired
    private Properties properties;

    private static String manifest;

    /**
     * { "betaManifestVersion": 1, "version": "my_version_string",
     * "entries": [ { "url": "go_offline.html"}, { "url":
     * "go_offline.js"}, { "url": "gears_init.js"} ] }
     * 
     * @param output
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping(method = RequestMethod.GET)
    public void forumsHandler(Writer output) throws IOException,
            JSONException {

        if (manifest == null) {
            manifest = createManifest();
        }
        output.append(manifest);
    }

    private String createManifest() throws JSONException {

        String gwtROOT = hostConfigurer.resolvePlaceholder(
                "HOST.gears.localserver.dir", properties);
        String localServerURL = hostConfigurer.resolvePlaceholder(
                "HOST.gears.localserver.url", properties);

        File contextF = new File(gwtROOT);

        JSONObject json = new JSONObject();
        json.put("betaManifestVersion", Integer.parseInt(hostConfigurer
                .resolvePlaceholder("gears.betaManifestVersion",
                        properties)));
        json.put("version", "0.0.1." + RandomUtils.rand(0, 2048));
        json.put("entries", getEntries(contextF, localServerURL));

        return json.toString();
    }

    private JSONArray getEntries(File dir, String localServerURL)
            throws JSONException {
        JSONArray fileArray = new JSONArray();
        return getEntries(dir, localServerURL, "", fileArray);
    }

    private JSONArray getEntries(File dir, String localServerURL,
            String dirString, JSONArray fileArray) throws JSONException {

        log.info("context: |" + dir + "|" + dirString);

        for (File f : dir.listFiles()) {

            // skip what we can
            if (f.getName().endsWith(".xml")
                    || f.getName().endsWith(".rpc")) {
                continue;
            }
            // descend into directory
            if (f.isDirectory()) {
                log.info("found dir " + f);
                getEntries(f, localServerURL, f.getName() + "/",
                        fileArray);
                continue;
            }

            JSONObject oo = new JSONObject();
            oo.put("url", localServerURL + dirString + f.getName());
            fileArray.put(oo);
        }
        return fileArray;
    }

    public void setHostConfigurer(
            HostPrecedingPropertyPlaceholderConfigurer hostConfigurer) {
        this.hostConfigurer = hostConfigurer;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
