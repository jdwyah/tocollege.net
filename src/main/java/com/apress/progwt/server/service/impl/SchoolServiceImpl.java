package com.apress.progwt.server.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.AbstractCommand;
import com.apress.progwt.client.domain.commands.CommandService;
import com.apress.progwt.client.exception.AccessException;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.domain.SchoolPopularity;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

@Transactional
public class SchoolServiceImpl implements SchoolService, CommandService {

    private static final Logger log = Logger
            .getLogger(SchoolServiceImpl.class);

    private SchoolDAO schoolDAO;

    private UserService userService;

    public School getSchoolDetails(String schoolname) {

        return schoolDAO.getSchoolFromName(schoolname);
    }

    @Required
    public void setSchoolDAO(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<SchoolPopularity> getPopularSchools() {
        List<SchoolPopularity> ranked = new LinkedList<SchoolPopularity>();
        for (School school : getTopSchools()) {
            ranked.add(new SchoolPopularity(school,
                    Math.random() * 5 - 2.5));
        }
        return ranked;
    }

    public void executeAndSaveCommand(AbstractCommand command)
            throws SiteException {
        executeAndSaveCommand(command, true);
    }

    /**
     * Can turn off the userCache which avoid some problems with our
     * transactional testing.
     */
    public void executeAndSaveCommand(AbstractCommand command,
            boolean useUserCache) throws SiteException {

        User u = userService.getCurrentUser(useUserCache);
        if (u != null) {
            log.info(command
                    + " "
                    + userService.getCurrentUser(useUserCache)
                            .getUsername());
        } else {
            log.warn(command + " attempted by anonymous ");
        }

        log.info("Going to execute Command...");

        command.executeCommand(this);

        // schoolDAO.executeAndSaveCommand(u, command);

        log.info("Executed Command. Saving...");

        // hydrateCommand(command, useUserCache);
        // command.executeCommand();

        // saveCommand(command);
        //
        // log.info("Saved");
        //
        // deleteCommand(command);
    }

    private void save(Loadable loadable) {
        if (loadable instanceof User) {
            User user = (User) loadable;
            userService.save(user);
        } else {
            schoolDAO.save(loadable);
        }
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

    public List<School> getTopSchools() {
        return schoolDAO.getAllSchools().subList(0, 10);
    }

    public List<School> getSchoolsMatching(String match) {
        return schoolDAO.getSchoolsMatching(match);
    }

    public void setSchoolAtRank(School school, int rank) {
        schoolDAO.setSchoolAtRank(userService.getCurrentUser().getId(),
                school, rank);

    }

    public void removeSchool(School school) {
        schoolDAO.removeSchool(userService.getCurrentUser().getId(),
                school);
    }

    public List<ProcessType> matchProcessType(String queryString) {

        return schoolDAO.matchProcessType(queryString);
    }

    public void saveProcessValue(long schoolAppID, long processTypeID,
            ProcessValue value) throws UsernameNotFoundException,
            AccessException {

        SchoolAndAppProcess application = (SchoolAndAppProcess) schoolDAO
                .get(SchoolAndAppProcess.class, schoolAppID);

        if (!application.getUser().equals(userService.getCurrentUser())) {
            String msg = "Invalid User " + userService.getCurrentUser()
                    + " accessing " + application.getUser() + ".";
            log.warn(msg);

            throw new AccessException(msg);
        } else {
            ProcessType type = (ProcessType) schoolDAO.get(
                    ProcessType.class, processTypeID);
            application.getProcess().put(type, value);
            schoolDAO.save(application);
        }

    }
}
