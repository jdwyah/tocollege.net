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
package com.apress.progwt.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.compass.core.Compass;
import org.compass.core.CompassCallback;
import org.compass.core.CompassException;
import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassHitsOperations;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.gps.CompassGps;
import org.compass.gps.MirrorDataChangesGpsDevice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.dto.SearchResult;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.service.SearchService;
import com.apress.progwt.server.service.UserService;

@Transactional
public class SearchServiceImpl implements SearchService, InitializingBean {
    private static final Logger log = Logger
            .getLogger(SearchServiceImpl.class);

    private static final int DEFAULT_MAX_SEARCH_RESULTS = 10;

    private CompassTemplate compassTemplate;

    private Compass compass;

    private MirrorDataChangesGpsDevice mirrorGPS;
    private CompassGps compassGPS;

    private UserService userService;

    @Required
    public void setMirrorGPS(MirrorDataChangesGpsDevice mirrorGPS) {
        this.mirrorGPS = mirrorGPS;
    }

    @Required
    public void setCompass(Compass compass) {
        this.compass = compass;
    }

    @Required
    public void setCompassGPS(CompassGps compassGPS) {
        this.compassGPS = compassGPS;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void afterPropertiesSet() throws Exception {
        if (compass == null) {
            throw new IllegalArgumentException(
                    "Must set compass property");
        }
        this.compassTemplate = new CompassTemplate(compass);

        // ensure that our gps is going to mirror all data changes from
        // here on out.
        // this will give us real time searchability of saved objects
        mirrorGPS.setMirrorDataChanges(true);
    }

    public SearchResult search(final String searchString)
            throws SiteException {
        return search(searchString, userService.getCurrentUser(), 0,
                DEFAULT_MAX_SEARCH_RESULTS);
    }

    /**
     * run two searches. one on schools only, one on forum posts so that
     * we can be sure to get some of each.
     * 
     * TODO. find a way to return a better smattering of each without
     * running two queries.
     * 
     * @param searchString
     * @param user
     * @param start
     * @param max_num_hits
     * @return
     * @throws SiteException
     */
    private SearchResult search(final String searchString,
            final User user, final int start, final int max_num_hits)
            throws SiteException {
        try {
            log.debug("-----" + searchString + "--------" + user
                    + "-----");

            SearchResult res = new SearchResult();

            if (searchString == null || searchString.equals("")) {
                return res;
            }

            final String[] aliases = { "school" };
            CompassHitsOperations schUserHits = getHits(searchString,
                    start, max_num_hits, aliases);

            final String[] aliases2 = { "schoolforumpost",
                    "userforumpost" };
            CompassHitsOperations forumPostHits = getHits(searchString,
                    start, max_num_hits, aliases2);

            addHitsToRes(res, schUserHits);
            addHitsToRes(res, forumPostHits);

            return res;
        } catch (Exception e) {
            throw new SiteException(e);
        }
    }

    private CompassHitsOperations getHits(final String searchString,
            final int start, final int max_num_hits,
            final String[] aliases) {

        CompassHitsOperations hits = compassTemplate
                .executeFind(new CompassCallback() {
                    public Object doInCompass(CompassSession session)
                            throws CompassException {

                        CompassHits hits = session.queryBuilder()
                                .queryString(searchString).toQuery()
                                .setAliases(aliases).addSort(
                                        "searchOrder",
                                        SortPropertyType.INT,
                                        SortDirection.REVERSE).hits();

                        return hits.detach(start, max_num_hits);
                    }
                });
        log.debug("Search " + searchString + " alias "
                + ArrayUtils.toString(aliases) + " hits "
                + hits.getLength());
        return hits;
    }

    /**
     * classify the given hits into types and add them to the SearchResult
     * parameter
     * 
     * @param res
     * @param hits
     */
    private void addHitsToRes(SearchResult res, CompassHitsOperations hits) {
        //
        // Turn results into SearchResults
        //
        for (int i = 0; i < hits.length(); i++) {
            CompassHit defaultCompassHit = hits.hit(i);

            // log.debug(i + " score: " + defaultCompassHit.getScore());
            // log.debug("alias: " + defaultCompassHit.getAlias());

            Object obj = defaultCompassHit.getData();
            // log.debug("DATA: " + defaultCompassHit.getData());

            if (obj instanceof User) {
                User usr = (User) obj;
                res.getUsers().add(usr);
            }
            if (obj instanceof School) {
                School sch = (School) obj;
                res.getSchools().add(sch);
            }
            if (obj instanceof ForumPost) {
                ForumPost fp = (ForumPost) obj;
                res.getForumPosts().add(fp);
            }
            log.debug("Found: " + res);
        }
    }

    public List<String> searchForSchool(final String searchString)
            throws SiteException {

        return searchForSchool(searchString, 0,
                DEFAULT_MAX_SEARCH_RESULTS);

    }

    /**
     * parse search string into parts and search for '+string*' for each
     * part. eg "Cal Berk" -> "+Cal* +Berk*" which will both AND and
     * impose a startsWith restriction, which is good for autocomplete
     * 
     * @param searchStringP
     * @param start
     * @param max_num_hits
     * @return
     */
    public List<String> searchForSchool(final String searchStringP,
            final int start, final int max_num_hits) throws SiteException {
        try {
            final List<String> rtn = new ArrayList<String>();

            if (searchStringP == null || searchStringP.equals("")) {
                return rtn;
            }
            String[] split = searchStringP.split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                sb.append("+");
                sb.append(split[i]);
                sb.append("* ");
            }

            final String[] aliases = { "school" };
            final String searchString = sb.toString();

            compassTemplate.execute(new CompassCallback() {
                public Object doInCompass(CompassSession session)
                        throws CompassException {

                    CompassHits hits = session.queryBuilder()
                            .queryString(searchString).toQuery()
                            .setAliases(aliases).addSort(
                                    "popularityCounter",
                                    SortPropertyType.INT,
                                    SortDirection.REVERSE).hits();
                    log.debug("search string " + searchString + " hits "
                            + hits.length());
                    for (int i = start; i < hits.length()
                            && i < max_num_hits; i++) {
                        String name = hits.resource(i).get("name");
                        // log.debug("search string " + searchString
                        // + " hit: " + name + " "
                        // + hits.resource(i));
                        if (name != null) {
                            rtn.add(name);
                        }
                    }
                    return true;
                }
            });

            // log.debug("search string " + searchString + " hits "
            // + hits.length());
            // for (int i = 0; i < hits.length(); i++) {
            // CompassHit defaultCompassHit = hits.hit(i);
            // Object obj = defaultCompassHit.getData();
            // log.debug("DATA: " + defaultCompassHit.getData());
            //
            // if (obj instanceof School) {
            // School result = (School) obj;
            // rtn.add(result);
            // }
            // }

            return rtn;
        } catch (Exception e) {
            throw new SiteException(e);
        }
    }

    /**
     * re-index search. GPS will take care of most things, but if we edit
     * the DB directly compass won't hear about it and we'll need to
     * re-index.
     */
    public void reindex() {
        compassGPS.index();
    }
}
