package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.exception.SiteException;

public class SaveRatingCommand extends AbstractCommand implements
        Serializable {

    private long applicationID;
    private int selectedRating;
    private long ratingID;

    public SaveRatingCommand() {
    }

    public SaveRatingCommand(RatingType ratingType, int selectedRating,
            Application application) {
        super(ratingType, application);

        this.ratingID = ratingType.getId();
        this.selectedRating = selectedRating;
        this.applicationID = application.getId();

    }

    @Override
    public String toString() {
        return "SaveRatingCommand AppID " + applicationID
                + " RatingType " + ratingID + " Value " + selectedRating;
    }

    public boolean haveYouSecuredYourselfAndFilteredUserInput() {
        return true;
    }

    public void execute(CommandService commandService)
            throws SiteException {

        Application application = (Application) commandService.get(
                Application.class, getApplicationID());
        RatingType ratingType = (RatingType) commandService.get(
                RatingType.class, getRatingID());

        assertUserIsAuthenticated(application.getUser());

        System.out.println(toString());
        System.out.println("application " + application + " rating "
                + ratingType);
        if (application != null) {
            System.out
                    .println("app.ratings: " + application.getRatings());
        }

        application.getRatings().put(ratingType, getSelectedRating());

        commandService.save(application);

    }

    public long getApplicationID() {
        return applicationID;
    }

    public int getSelectedRating() {
        return selectedRating;
    }

    public long getRatingID() {
        return ratingID;
    }

}
