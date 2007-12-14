package com.apress.progwt.client.domain;

import java.io.Serializable;
import java.util.List;

import com.apress.progwt.client.domain.commands.Orderable;
import com.apress.progwt.client.domain.generated.AbstractApplication;

public class Application extends AbstractApplication implements
        Serializable, Loadable, Orderable {

    private transient ProcessType currentStatus;

    public Application() {
    }

    public Application(School school) {
        setSchool(school);

    }

    @Override
    public String toString() {
        return "ScAndApp School: " + getSchool() + " User: " + getUser();
    }

    /**
     * Used by FTL templates. See
     * http://fmpp.sourceforge.net/freemarker/app_faq.html#faq_question_12
     * 
     * @param type
     * @return
     */
    public ProcessValue getTheProcess(ProcessType type) {
        return getProcess().get(type);
    }

    /**
     * wrap possible null returns with default value.
     * 
     * @param ratingType
     * @return
     */
    public int getRating(RatingType ratingType) {
        Integer rating = getRatings().get(ratingType);
        if (rating == null) {
            return 5;
        } else {
            return rating;
        }
    }

    /**
     * Return the DecisionMatrix score for the given ratingTypes.
     * 
     * @param ratingTypes
     * @param user
     * @return
     */
    public ApplicationAndScore getAppAndScore(
            List<RatingType> ratingTypes, User user) {
        ApplicationAndScore rtn = new ApplicationAndScore(this);

        int myScore = 0;
        int totalScore = 0;

        for (RatingType ratingType : ratingTypes) {
            int score = getRating(ratingType);
            int priority = user.getPriority(ratingType);

            myScore += score * priority;
            totalScore += 10 * priority;
        }

        rtn.setScore(myScore);
        rtn.setTotal(totalScore);

        return rtn;

    }

    /**
     * return the highest valued, completed status process type.
     * 
     * @return
     */
    public ProcessType getCurrentStatus() {
        if (null == currentStatus) {
            for (ProcessType processType : getUser()
                    .getStatusProcessTypes()) {

                System.out.println("get status type " + processType);
                ProcessValue value = getProcess().get(processType);

                // TODO ditch .equals()
                if (processType.getName().equals("Considering")
                        || (value != null && value.getPctComplete() == 1.0)) {

                    System.out.println("==consider");
                    if (currentStatus == null
                            || currentStatus.getStatus_order() < processType
                                    .getStatus_order())
                        System.out.println("set rtn");
                    currentStatus = processType;
                }
            }
        }
        return currentStatus;
    }
}
