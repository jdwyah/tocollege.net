package com.apress.progwt.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.compass.core.Compass;
import org.compass.core.CompassCallback;
import org.compass.core.CompassException;
import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassHitsOperations;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassQueryFilter;
import org.compass.core.CompassQueryFilterBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.core.CompassQueryBuilder.CompassBooleanQueryBuilder;
import org.compass.core.CompassQueryBuilder.CompassQueryStringBuilder;
import org.compass.core.engine.SearchEngineException;
import org.compass.gps.CompassGps;
import org.compass.gps.MirrorDataChangesGpsDevice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.dto.SearchResult;
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

        // TODO find another way to call this, since it's a bit
        // inefficient
        // to do it everytime tomcat restarts
        compassGPS.index();

        // ensure that our gps is going to mirror all data changes from
        // here on out.
        // this will give us real time searchability of saved objects
        mirrorGPS.setMirrorDataChanges(true);
    }

    public List<SearchResult> search(final String searchString) {
        return search(searchString, userService.getCurrentUser(), 0,
                DEFAULT_MAX_SEARCH_RESULTS);
    }

    private List<SearchResult> search(final String searchString,
            final User user, final int start, final int max_num_hits) {

        log.debug("-----" + searchString + "--------" + user + "-----");

        // search for "" -> bad things
        // org.compass.core.engine.SearchEngineQueryParseException: Failed
        // to parse query [];
        // nested exception is
        // org.apache.lucene.queryParser.ParseException: Encountered
        // "<EOF>" at
        // line 1, column 0.
        if (searchString == null || searchString.equals("")) {
            return new ArrayList<SearchResult>();
        }

        //
        // create search query
        // add filter by username
        // TODO how secure is this really? say somebodie's username is
        // 'j*' will they get to see
        // 'jdwyah' stuff? eek.
        // Maybe do a check before we return results?
        //
        // should we be parsing that searchString? forums said '???' as a
        // search string could make
        // things explode
        //
        CompassHitsOperations hits = compassTemplate
                .executeFind(new CompassCallback() {
                    public Object doInCompass(CompassSession session)
                            throws CompassException {

                        // CompassHits hits = queryBuilder.bool()
                        // .addShould(queryBuilder.queryString(searchString).toQuery())
                        // .addMust(queryBuilder.term("User.username",
                        // "test")).toQuery().hits();

                        // queryBuilder.bool().addMust(queryBuilder.equals("name",
                        // "jack")).addMust(queryBuilder.lt("birthdate",
                        // "19500101"))
                        // .toQuery().hits();

                        Map<String, Object> mustEqualMap = new HashMap<String, Object>();
                        if (null != user) {
                            log.debug("Do User Secured Search");
                            mustEqualMap.put("userID", user.getId());
                        } else {
                            log.debug("Do Public Search");
                            mustEqualMap.put("publicVisible", true);
                        }

                        // create compass query with free text query that
                        // the user
                        // typed in.
                        CompassQueryBuilder queryBuilder = session
                                .queryBuilder();
                        CompassQueryStringBuilder qStrBuilder = queryBuilder
                                .queryString(searchString);
                        CompassQuery compassQuery = qStrBuilder.toQuery();

                        // if mustEqualMap passed in with name-value
                        // pairs, loop
                        // through the
                        // map and create a query filter which is set with
                        // the
                        // free text query that
                        // was just created with the free text string.
                        if (mustEqualMap != null) {
                            CompassQueryFilterBuilder queryFilterBuilder = session
                                    .queryFilterBuilder();
                            CompassBooleanQueryBuilder booleanQueryBuilder = queryBuilder
                                    .bool();
                            Set<String> searchPropSet = mustEqualMap
                                    .keySet();
                            for (String searchProp : searchPropSet) {
                                Object value = mustEqualMap
                                        .get(searchProp);

                                // booleanQueryBuilder.addMust(queryBuilder.fuzzy(searchProp,value));

                                booleanQueryBuilder.addMust(queryBuilder
                                        .term(searchProp, value));
                            }
                            CompassQueryFilter queryFilter = queryFilterBuilder
                                    .query(booleanQueryBuilder.toQuery());

                            compassQuery.setFilter(queryFilter);
                        }

                        CompassHits hits = compassQuery.hits();

                        //
                        // need to do this before we unattach the hits
                        // http://www.opensymphony.com/compass/versions/1.1M2/html/core-workingwithobjects.html#CompassHighlighter
                        //
                        // TODO add in start / max_num_hits
                        for (int i = 0; i < hits.length(); i++) {
                            try {
                                log.debug("HIT "
                                        + i
                                        + " T:"
                                        + hits.highlighter(i).fragment(
                                                "text"));
                                // this will cache the highlighted
                                // fragment
                                hits.highlighter(i).fragment("text");
                            } catch (SearchEngineException see) {
                                log.warn("Search Engine Exception: "
                                        + see + " search term "
                                        + searchString + " username "
                                        + user);
                            }
                        }
                        return hits.detach(start, max_num_hits);
                    }
                });

        log.debug("Search: " + searchString + "Results:\t"
                + hits.getLength());

        List<SearchResult> returnList = new ArrayList<SearchResult>(hits
                .length());

        //
        // Turn results into SearchResults
        //
        for (int i = 0; i < hits.length(); i++) {
            CompassHit defaultCompassHit = hits.hit(i);

            log.debug(i + " score: " + defaultCompassHit.getScore());
            log.debug("alias: " + defaultCompassHit.getAlias());

            if (defaultCompassHit.getScore() < .05) {
                log.debug("skip <.05");// .041?
                continue;
            }

            SearchResult res = null;

            Object obj = defaultCompassHit.getData();
            log.debug("DATA: " + defaultCompassHit.getData());

            // if (obj instanceof Entry) {
            // Entry entry = (Entry) obj;
            //
            // log.debug("id: " + entry.getId());
            //
            // List<TopicIdentifier> topicIDList = selectDAO
            // .getTopicForOccurrence(entry.getId(), user);
            //
            // if (topicIDList.size() > 0) {
            // // TODO what if it has multiple refs?
            // TopicIdentifier topicID = topicIDList.get(0);
            //
            // CompassHighlightedText text = defaultCompassHit
            // .getHighlightedText();
            //
            // // PEND take this out == null only when we do the
            // // search for a term that is a
            // // username...
            // if (text == null) {
            //
            // res = new SearchResult(entry, topicIDList,
            // defaultCompassHit.getScore(), null);
            //
            // } else {
            // res = new SearchResult(entry, topicIDList,
            // defaultCompassHit.getScore(), text
            // .getHighlightedText("text"));
            //
            // }
            // } else {
            // log.warn("Occurrence w/o topic " + entry);
            // }
            // } else if (obj instanceof URI) {
            // URI uri = (URI) obj;
            // List<TopicIdentifier> topicIDList = selectDAO
            // .getTopicForOccurrence(uri.getId(), user);
            //
            // // PEND errored when we searched for a username... ie
            // // "test"
            // if (topicIDList.size() > 0) {
            // res = new SearchResult(uri, topicIDList,
            // defaultCompassHit.getScore(), uri.getData());
            // } else {
            // log.warn("Occurrence w/o topic " + uri);
            // }
            //
            // } else if (obj instanceof RealTopic) {
            // RealTopic top = (RealTopic) obj;
            //
            // res = new SearchResult(top, null, defaultCompassHit
            // .getScore(), null);
            //
            // // // TODO doesn't work!! need to exclude in cpm.
            // // Returning as a Topic.class
            // // // TODO messy. Maybe we need TopLevelTopic.class?
            // // if (!(top instanceof Association)) {
            // // System.out.println("found top: " + top);
            // // res = new SearchResult(top.getId(),
            // // defaultCompassHit.getScore(), top
            // // .getTitle(), null);
            // // } else {
            // // System.out.println("was assoc, skip " + top);
            // // }
            //
            // }
            // // I think root == false takes care of this..
            // else if (obj instanceof User) {
            // log.warn("Shouldn't Happen");
            // User userRes = (User) obj;
            // // TODO user.getID() will break this
            // // res = new
            // //
            // SearchResult(user.getId(),defaultCompassHit.getScore(),user.getUsername(),null);
            // } else {
            // log.warn("???" + obj);
            // }
            log.debug("Found: " + res);

            if (res != null) {
                returnList.add(res);
            }

        }

        return returnList;
    }

    public List<String> searchForSchool(final String searchString) {
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
            final int start, final int max_num_hits) {

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

        final String searchString = sb.toString();

        compassTemplate.execute(new CompassCallback() {
            public Object doInCompass(CompassSession session)
                    throws CompassException {

                CompassHits hits = session.queryBuilder().queryString(
                        searchString).toQuery().hits();
                log.debug("search string " + searchString + " hits "
                        + hits.length());
                for (int i = start; i < hits.length() && i < max_num_hits; i++) {
                    String name = hits.resource(i).get("name");
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
    }
}
