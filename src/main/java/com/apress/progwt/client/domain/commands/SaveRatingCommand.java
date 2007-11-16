package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.exception.SiteException;

public class SaveRatingCommand extends AbstractCommand implements
        Serializable {

    private transient Application application;
    private transient RatingType ratingType;
    private long applicationID;
    private int selectedRating;
    private long ratingID;

    public SaveRatingCommand() {
    }

    public SaveRatingCommand(RatingType ratingType, int selectedRating,
            Application application) {

        this.application = application;
        this.ratingType = ratingType;
        this.ratingID = ratingType.getId();
        this.selectedRating = selectedRating;
        this.applicationID = application.getId();

    }

    @Override
    public void executeCommandServer(CommandService commandService)
            throws SiteException {

        commandService.saveRatingCommand(this);

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

    @Override
    public void executeCommandClient() throws SiteException {
        application.getRatings().put(ratingType, getSelectedRating());
    }

}
