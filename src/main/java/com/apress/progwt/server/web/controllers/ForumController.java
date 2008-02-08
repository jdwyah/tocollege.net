package com.apress.progwt.server.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.client.domain.GWTSerializer;
import com.apress.progwt.client.domain.dto.ForumBootstrap;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.domain.forum.RecentForumPostTopic;
import com.apress.progwt.client.exception.InfrastructureException;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

/**
 * Map to both /forums.html and /secure/forums.html that way when a not
 * logged in user wants to reply, we can simply forwrd them to the same
 * page but with in the /secure/ directory. That will make Acegi kick in
 * to gear to protect the page, and will make use of the fact that Acegi
 * remembers the originally requested page. Combine this with our GWT
 * history support and we should be all set.
 * 
 * @author Jeff Dwyer
 * 
 */
@Controller
@RequestMapping( { "/forums.html", "/secure/forums.html" })
public class ForumController {
    private static final Logger log = Logger
            .getLogger(ForumController.class);

    /**
     * specify qualifier because two beans implement this interface. this
     * acts as by-name injection. See:
     * http://springtips.blogspot.com/2007/11/spring-25-too-much-auto-wired.html
     */
    @Autowired
    @Qualifier(value = "GWTSchoolService")
    private GWTSerializer gwtSerializer;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView forumsHandler(HttpServletRequest req,
            @RequestParam(value = "uniqueForumID", required = false)
            String uniqueForumID) throws InfrastructureException {

        ModelMap rtn = ControllerUtil.getModelMap(req, userService);

        if (uniqueForumID != null) {

            rtn.addAttribute("uniqueForumID", uniqueForumID);

        } else {

            RecentForumPostTopic rfpt = new RecentForumPostTopic();
            PostsList postList = schoolService.getForum(rfpt, 0, 15);

            ForumBootstrap bootstrap = new ForumBootstrap(gwtSerializer,
                    postList, rfpt);

            rtn.addAttribute("bootstrap", bootstrap);

        }

        return new ModelAndView("forums", rtn);

    }

    public void setGwtSerializer(GWTSerializer gwtSerializer) {
        this.gwtSerializer = gwtSerializer;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }
}
