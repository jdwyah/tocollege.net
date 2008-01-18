package com.apress.progwt.server.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.CommandService;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.client.exception.SiteSecurityException;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.domain.SchoolPopularity;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.SearchService;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.util.HTMLInputFilter;

@Transactional
public class SchoolServiceImpl implements SchoolService, CommandService {

    private static final Logger log = Logger
            .getLogger(SchoolServiceImpl.class);

    private SchoolDAO schoolDAO;

    private SearchService searchService;
    private UserService userService;
    private static final HTMLInputFilter htmlFilter = new HTMLInputFilter();

    public SiteCommand executeAndSaveCommand(SiteCommand command)
            throws SiteException {
        return executeAndSaveCommand(command, true);
    }

    /**
     * Can turn off the userCache which avoid some problems with our
     * transactional testing.
     */
    public SiteCommand executeAndSaveCommand(SiteCommand command,
            boolean useUserCache) throws SiteException {

        User loggedIn = userService.getCurrentUser(useUserCache);

        if (loggedIn == null) {
            log.warn(command + " attempted by anonymous ");
        }

        if (!command.haveYouSecuredYourselfAndFilteredUserInput()) {
            throw new BusinessException("Command " + command
                    + " hasn't secured.");
        }
        if (!userService.getToken(loggedIn).equals(command.getToken())) {
            log.warn("Possible XSRF: " + command.getToken());
            throw new SiteSecurityException("Invalid Session "
                    + command.getToken());
        } else {
            log.info("Tokens equal: " + userService.getToken(loggedIn));
        }
        if (loggedIn != null) {
            log.info(command + " " + loggedIn.getUsername());
        }

        log.info("Going to execute Command...");

        command.execute(this);

        // schoolDAO.executeAndSaveCommand(u, command);

        log.info("Executed Command. Saving...");

        // hydrateCommand(command, useUserCache);
        // command.executeCommand();

        // saveCommand(command);
        //
        // log.info("Saved");
        //
        // deleteCommand(command);
        return command;
    }

    public <T> T get(Class<T> clazz, long id) {
        return (T) schoolDAO.get((Class<? extends Loadable>) clazz, id);

    }

    public List<School> getAllSchools() {
        return schoolDAO.getAllSchools(0, 2500);
    }

    public List<SchoolPopularity> getPopularSchools() {
        List<SchoolPopularity> ranked = new LinkedList<SchoolPopularity>();
        for (School school : getTopSchools(0, 10)) {
            ranked.add(new SchoolPopularity(school,
                    Math.random() * 5 - 2.5));
        }
        return ranked;
    }

    public School getSchoolDetails(String schoolname) {

        return schoolDAO.getSchoolFromName(schoolname);
    }

    /**
     * Search for "match*" using searchService
     */
    public List<String> getSchoolsMatching(String match) {
        return searchService.searchForSchool(match);
        // return schoolDAO.getSchoolsMatching(match);
    }

    // private void hydrateCommand(AbstractCommand command,
    // boolean useUserCache) throws SiteException {
    //
    // log.debug("Hydrate: " + command);
    //
    // User current = userService.getCurrentUser(useUserCache);
    // command.setCurrentUser(current);
    //
    // List<Loadable> loadedObjs = new ArrayList<Loadable>(command
    // .getObjects().size());
    // for (Loadable loadable : command.getObjects()) {
    //
    // Loadable l = schoolDAO.get(loadable.getClass(), loadable
    // .getId());
    //
    // loadedObjs.add(l);
    // }
    // command.setObjects(loadedObjs);
    //
    // // int i = 0;
    // // for (Class<Loadable> loadable : command.getClasses()) {
    // //
    // // log.debug("loadable: " + loadable);
    // // log.debug("command.getLookups().get() "
    // // + command.getIds().get(i));
    // //
    // // Loadable l = schoolDAO.get(loadable, command.getIds().get(i));
    // //
    // // command.addObject(l);
    // // i++;
    // // }
    //
    // }

    public PostsList getSchoolThreads(long schoolID, int start, int max) {
        return schoolDAO.getSchoolThreads(schoolID, start, max);
    }

    public PostsList getPostsForThread(ForumPost post, int start, int max) {
        return schoolDAO.getPostsForThread(post, start, max);
    }

    public List<School> getTopSchools(int start, int max) {
        return schoolDAO.getAllSchools(start, max);
    }

    public List<ProcessType> matchProcessType(String queryString) {

        return schoolDAO.matchProcessType(queryString);
    }

    /**
     * TODO protect?
     */
    public void save(Loadable loadable) {
        if (loadable instanceof User) {
            User user = (User) loadable;
            userService.save(user);
        } else {
            schoolDAO.save(loadable);
        }
    }

    @Required
    public void setSchoolDAO(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    @Required
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<ForumPost> getRecentForumPosts(int start, int max) {
        return schoolDAO.getRecentForumPosts(start, max);
    }

    public void delete(Loadable loadable) {
        schoolDAO.delete(loadable);
    }

    public void assertUserIsAuthenticated(User toCheck)
            throws SecurityException {
        User loggedIn = userService.getCurrentUser();
        if (loggedIn == null || !loggedIn.equals(toCheck)) {
            throw new SecurityException("Logged in: " + loggedIn
                    + " Requested: " + toCheck);
        }
    }

    public String filterHTML(String input) {
        return htmlFilter.filter(input);
    }

}
