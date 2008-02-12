package com.apress.progwt.server.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.AbstractCommand;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.domain.SchoolAndRank;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

public class SchoolServiceImpl implements SchoolService {

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

    public List<SchoolAndRank> getPopularSchools() {
        List<SchoolAndRank> ranked = new LinkedList<SchoolAndRank>();
        for (School school : getTopSchools()) {
            ranked
                    .add(new SchoolAndRank(school,
                            Math.random() * 5 - 2.5));
        }
        return ranked;
    }

    public void executeAndSaveCommand(AbstractCommand command)
            throws SiteException {

        User u = userService.getCurrentUser();
        if (u != null) {
            log.info(command + " "
                    + userService.getCurrentUser().getUsername());
        } else {
            log.warn(command + " attempted by anonymous ");
        }

        hydrateCommand(command);
        command.executeCommand();
        saveCommand(command);
        deleteCommand(command);
    }

    private void deleteCommand(AbstractCommand command)
            throws BusinessException {
        Set<Loadable> topics = command.getDeleteSet();
        for (Loadable loadable : topics) {
            delete(loadable);
        }
    }

    private void delete(Loadable loadable) {
        // TODO Auto-generated method stub

    }

    private void saveCommand(AbstractCommand command)
            throws BusinessException {

        List<Loadable> topics = command.getObjects();
        for (Loadable loadable : topics) {
            save(loadable);
        }
    }

    private void save(Loadable loadable) {
        schoolDAO.save(loadable);
    }

    private void hydrateCommand(AbstractCommand command)
            throws SiteException {

        log.debug("Hydrate: " + command);

        int i = 0;
        for (Class<Loadable> loadable : command.getClasses()) {

            log.debug("loadable: " + loadable);
            log.debug("command.getLookups().get() "
                    + command.getIds().get(i));

            Loadable l = schoolDAO.get(loadable, command.getIds().get(i));

            command.addObject(l);
            i++;
        }

    }

    public List<School> getTopSchools() {
        return schoolDAO.getAllSchools().subList(0, 10);
    }

    public List<School> getSchoolsMatching(String match) {
        return schoolDAO.getSchoolsMatching(match);
    }
}
