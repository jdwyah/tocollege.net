package com.apress.progwt.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Based on code from fvisticot
 * http://code.google.com/p/gwt-google-apis/issues/detail?id=6
 * 
 * 
 */
public class GearsManifestFilter implements Filter {
    public static final String EXTENSIONS = "extensions";
    public static final String MANIFEST_VERSION = "manifestVersion";

    public enum Browser {
        OPERA("opera", "'opera'],"), SAFARI("safari", "'safari'],"), GECKO1_8(
                "gecko1_8", "'gecko1_8'],"), GECKO("gecko", "'gecko'],"), IE6(
                "ie6", "'ie6'],");

        private String name;
        private String pattern;
        private String cacheFileName;

        private Browser(String name, String pattern) {
            this.name = name;
            this.pattern = pattern;
        }

        public String getName() {
            return name;
        }

        public String getPattern() {
            return pattern;
        }

        public String getCacheFileName() {
            return cacheFileName;
        }

        public void createCacheFileName(StringBuffer noCacheStringBuffer) {

            String noCacheString = noCacheStringBuffer.toString();
            int beginIndex = noCacheString.indexOf(getPattern())
                    + getPattern().length() + 1;
            int endIndex = noCacheString.indexOf("'", beginIndex);
            String filename = noCacheString.substring(beginIndex,
                    endIndex);
            this.cacheFileName = filename;
        }

    }

    protected String _extensions;

    protected String _manifestVersion = "1.0";

    protected Map<Browser, String> _manifestMap = new HashMap<Browser, String>();

    public static class GearRegexpFilenameFilter implements
            FilenameFilter {
        private String _regexpFilter;
        private Pattern _regexpPattern;

        GearRegexpFilenameFilter(String regexpFilter) {
            _regexpFilter = regexpFilter;
            _regexpPattern = Pattern.compile(_regexpFilter);
        }

        public boolean accept(File dir, String name) {
            if ((name.indexOf(".cache.") == -1)
                    && (name.indexOf("-xs.nocache.") == -1)) {
                Matcher matcher = _regexpPattern.matcher(name);
                return matcher.find();
            } else
                return false;
        }
    }

    public static class GearResourcesExplorer {
        List<String> _result = new ArrayList<String>();

        public GearResourcesExplorer() {

        }

        public List<String> list(File directory, FilenameFilter filter,
                boolean recursive, boolean root) {
            if (recursive) {
                File[] dirs = directory.listFiles(new FileFilter() {

                    public boolean accept(File pathname) {
                        if (pathname.isDirectory())
                            return true;
                        return false;
                    }
                });
                for (int i = 0; i < dirs.length; i++)
                    list(dirs[i], filter, recursive, false);
            }

            String[] res = directory.list(filter);
            for (int i = 0; i < res.length; i++) {
                if (root) {
                    _result.add(res[i]);
                } else {
                    _result.add(directory.getName() + File.separator
                            + res[i]);
                }
            }
            return _result;
        }
    }

    public GearsManifestFilter() {
    }

    public void init(FilterConfig filterConfig) {
        _extensions = filterConfig.getInitParameter(EXTENSIONS);
        _manifestVersion = filterConfig
                .getInitParameter(MANIFEST_VERSION);
        System.out.println("Extensions: " + _extensions);
        String realPath = filterConfig.getServletContext().getRealPath(
                "/");
        System.out.println("Path:" + realPath);
        File dir = new File(realPath);

        List<String> resourceFilenames = new GearResourcesExplorer()
                .list(dir, new GearRegexpFilenameFilter(_extensions),
                        true, true);

        for (int i = 0; i < resourceFilenames.size(); i++) {
            System.out.println("File: " + resourceFilenames.get(i));
        }

        FilenameFilter noCacheFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".nocache.js")
                        && !name.endsWith("-xs.nocache.js"))
                    return true;
                return false;
            }
        };
        String[] noCacheFilenames = dir.list(noCacheFilter);
        if (noCacheFilenames.length != 1) {
            System.out.println("Error !!");
        } else {
            getResourcesAccordingUserAgent(realPath + noCacheFilenames[0]);
        }

        _manifestMap
                .put(Browser.SAFARI, createManifest(Browser.SAFARI
                        .getCacheFileName(), _manifestVersion,
                        resourceFilenames));
        _manifestMap
                .put(Browser.GECKO, createManifest(Browser.GECKO
                        .getCacheFileName(), _manifestVersion,
                        resourceFilenames));
        _manifestMap.put(Browser.GECKO1_8, createManifest(
                Browser.GECKO1_8.getCacheFileName(), _manifestVersion,
                resourceFilenames));
        _manifestMap
                .put(Browser.OPERA, createManifest(Browser.OPERA
                        .getCacheFileName(), _manifestVersion,
                        resourceFilenames));
        _manifestMap
                .put(Browser.IE6, createManifest(Browser.IE6
                        .getCacheFileName(), _manifestVersion,
                        resourceFilenames));
    }

    protected void getResourcesAccordingUserAgent(String noCacheFilename) {
        System.out.println(noCacheFilename);
        BufferedReader in = null;
        StringBuffer buffer = new StringBuffer();
        try {
            in = new BufferedReader(new FileReader(noCacheFilename));
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                buffer.append(currentLine);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Browser.OPERA.createCacheFileName(buffer);
        Browser.GECKO.createCacheFileName(buffer);
        Browser.GECKO1_8.createCacheFileName(buffer);
        Browser.SAFARI.createCacheFileName(buffer);
        Browser.IE6.createCacheFileName(buffer);

        // System.out.println("OperaFilename: " + _operaCacheFilename);
        // System.out.println("GeckoFilename: " + _geckoCacheFilename);
        // System.out.println("Gecko1_8Filename: " +
        // _gecko1_8CacheFilename);
        // System.out.println("SafariFilename: " + _safariCacheFilename);
        // System.out.println("IE6Filename: " + _ie6CacheFilename);

    }

    protected String createManifest(String userAgentHash, String version,
            List resourceFilenames) {
        StringBuffer manifestBuffer = new StringBuffer("{\n");
        manifestBuffer.append("\"betaManifestVersion\": 1,\n");
        manifestBuffer.append("\"version\": \"");
        manifestBuffer.append(version);
        manifestBuffer.append("\",\n");
        manifestBuffer.append("\"entries\": [\n");

        manifestBuffer.append("{ \"url\": \"");
        manifestBuffer.append(userAgentHash + ".cache.js\"");
        manifestBuffer.append("},\n");
        manifestBuffer.append("{ \"url\": \"");
        manifestBuffer.append(userAgentHash + ".cache.html\"");
        manifestBuffer.append("},\n");

        for (int i = 0; i < resourceFilenames.size() - 1; i++) {
            manifestBuffer.append("{ \"url\": \"");
            manifestBuffer.append(resourceFilenames.get(i));
            manifestBuffer.append("\"},\n");
        }
        manifestBuffer.append("{ \"url\": \"");
        manifestBuffer.append(resourceFilenames.get(resourceFilenames
                .size() - 1));
        manifestBuffer.append("\"}\n");
        manifestBuffer.append("]\n");
        manifestBuffer.append("}\n");
        System.out.println("Manifest: " + manifestBuffer.toString());
        return manifestBuffer.toString();
    }

    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        String userAgentString = request.getHeader("user-agent");
        System.out.println("UserAgent: " + userAgentString);
        String userAgent = getUserAgent(userAgentString);
        ServletOutputStream out = servletResponse.getOutputStream();
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/jsonrequest");
        out.println((String) _manifestMap.get(userAgent));
    }

    protected String getUserAgent(String userAgentString) {
        if (userAgentString.indexOf("IE6") != -1
                || userAgentString.indexOf("MSIE 7") != -1) {
            System.out.println("IE6 or more detected");
            return Browser.IE6.name;
        } else if (userAgentString.indexOf("MOZILLA") != -1) {
            System.out.println("MOZILLA detected");
            return Browser.GECKO.name;
        } else if (userAgentString.indexOf("MOZILLA") != -1) {
            System.out.println("MOZILLA detected");
            return Browser.IE6.name;
        }
        return Browser.SAFARI.name;
    }

    public void destroy() {
    }

    public void test() {
        File dir = new File("c:\\apache-ant-1.7.0");
        GearResourcesExplorer gearFilename = new GearResourcesExplorer();
        List res = gearFilename.list(dir, new GearRegexpFilenameFilter(
                ".xml|.xsl"), true, true);

        for (int i = 0; i < res.size(); i++)
            System.out.println("Selected file: " + res.get(i));
    }

    public static void main(String[] args) {
        GearsManifestFilter gearM = new GearsManifestFilter();
        gearM.test();
    }
}
