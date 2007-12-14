package com.apress.progwt.client.college.gui;

import com.google.gwt.user.client.ui.Widget;

public interface RemembersPosition {

	int getLeft();

	int getTop();

	Widget getWidget();

	void zoomToScale(double currentScale);

	void setTop(int top);

}
