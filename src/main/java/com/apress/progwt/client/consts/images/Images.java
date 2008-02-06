package com.apress.progwt.client.consts.images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * 
 * Note the (at)gwt.resource comments aren't required if files are named
 * like the method name;
 * 
 * @author Jeff Dwyer
 * 
 */
public interface Images extends ImageBundle {

    AbstractImagePrototype magnifyingBig();

    AbstractImagePrototype magnifyingSmall();

    AbstractImagePrototype bullet_blue();

    AbstractImagePrototype applying();

    AbstractImagePrototype accepted();

    AbstractImagePrototype rejected();

    AbstractImagePrototype applied();

    AbstractImagePrototype considering();

    AbstractImagePrototype pctStatus0();

    AbstractImagePrototype pctStatus25();

    AbstractImagePrototype pctStatus50();

    AbstractImagePrototype pctStatus75();

    AbstractImagePrototype pctStatus100();

    AbstractImagePrototype checked();

    AbstractImagePrototype unchecked();

}
