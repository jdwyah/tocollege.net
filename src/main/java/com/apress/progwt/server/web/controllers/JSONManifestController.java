package com.apress.progwt.server.web.controllers;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.support.ServletContextResource;

/**
 * doesn't work yet
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
@Controller
@RequestMapping("/manifest.json")
public class JSONManifestController {
    private static final Logger log = Logger
            .getLogger(JSONManifestController.class);

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
    public void forumsHandler(Writer output, WebRequest request,
            HttpServletRequest httpreq) throws IOException, JSONException {
        String context = httpreq.getRealPath(".");

        ServletContextResource file = new ServletContextResource(httpreq
                .getSession().getServletContext(), ".");

        log.info("file: " + file.getFile().getAbsolutePath());
        log.info("req: " + request);
        log.info("cp:" + request.getContextPath());
        log.info("cp2: " + "f");
        log.info("http: " + httpreq);
        log.info("cp:" + httpreq.getContextPath());
        log.info("cp2:" + httpreq.getRealPath("."));
        FileSystemResource u = new FileSystemResource(".");
        // FileSystemResource u = new FileSystemResource(".");
        log.info("u." + u.getFilename());
        log.info("u." + u.getFile());
        log.info("u." + u.getFile().getAbsolutePath());

        JSONObject json = new JSONObject();
        json.put("betaManifestVersion", 1);
        json.put("version", "0.0.1");
        json.put("entries", context);

        output.append(json.toString());

        //        
        // # These are the known files that are referenced by your GWT
        // app. Change as appropriate.
        // $manifest_files[] = 'index.html';
        // $manifest_files[] = 'index.html?locale=zh';
        // $manifest_files[] = 'index.html?locale=en';
        // $manifest_files[] = 'Sudoku.css';
        // $manifest_files[] = 'mygwt-all.css';
        // $manifest_files[] = 'history.html';
        // $manifest_files[] = 'gears_init.js';
        // $manifest_files[] = 'puzzles.txt';
        // $manifest_files[] = 'images/default/shared/large-loading.gif';
        // $manifest_files[] =
        // 'images/default/expandbar/expand-item-bg.gif';
        // $manifest_files[] = 'images/default/button/btn-sprite.gif';
        // $manifest_files[] = 'images/default/toolbar/split.gif';
        // $manifest_files[] = 'images/default/toolbar/bg.gif';
        // $manifest_files[] = 'images/lightbulb.png';
        // $manifest_files[] = 'images/wand.png';
        // $manifest_files[] = 'images/printer.png';
        // $manifest_files[] = 'images/clock.png';
        // $manifest_files[] = 'images/cursor.png';
        // $manifest_files[] = 'images/pencil.png';
        // $manifest_files[] = 'images/gsudokr2.jpg';
        //        
        // $handle = opendir(".");
        // while ($file = readdir($handle))
        // {
        // # Include all the GWT generated files
        // if (preg_match('/.cache/', $file) || preg_match('/.nocache/',
        // $file))
        // {
        // $manifest_files[] = $file;
        // }
        // }
        // closedir($handle);
        //        
        // $manifest_str = "{\n".
        // "\"betaManifestVersion\": 1,\n".
        // "\"version\": \"version 1.0\",\n".
        // "\"entries\": [\n";
        //            
        // # This is hardcoded for me.
        // $manifest_str = $manifest_str."{ \"url\": \".\", \"redirect\":
        // \"index.html\" },\n";
        //        
        // for ($i = 0; $i < sizeof($manifest_files)-1; $i++)
        // {
        // $manifest_str = $manifest_str."{ \"url\":
        // \"".$manifest_files[$i]."\"},\n";
        // }
        // $manifest_str = $manifest_str."{ \"url\":
        // \"".$manifest_files[sizeof($manifest_files)-1]."\"}\n";
        //        
        // $manifest_str = $manifest_str."]\n}";
        //        
        // $file = "manifest.json";
        // $handle = fopen($file, 'w');
        // fwrite($handle, $manifest_str);
        // fclose($handle);

        output.write("{foo}");

    }

    private JSONArray getEntries(String context) throws JSONException {

        JSONArray fileArray = new JSONArray();

        log.info("context: |" + context + "|");

        File dir = new File(context);
        for (String f : dir.list()) {
            JSONObject oo = new JSONObject();
            oo.put("url", f);
            fileArray.put(oo);
        }

        return fileArray;

    }
}
