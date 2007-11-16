package com.apress.progwt.client.college.gui;

import java.util.List;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProConDispPanel extends Composite implements ClickListener {

    private VerticalPanel mainPanel;
    private TextBox addTextBox;

    public ProConDispPanel(String string, List<String> list) {

        mainPanel = new VerticalPanel();
        mainPanel.add(new Label(string));
        for (String pro : list) {
            TextBox t = new TextBox();
            t.setText(pro);
            mainPanel.add(t);
        }

        mainPanel.addStyleName("ProConPanel");
        mainPanel.addStyleName("ProConPanel-" + string);

        HorizontalPanel addP = new HorizontalPanel();
        addTextBox = new TextBox();
        Button addB = new Button("Add");
        addB.addClickListener(this);
        addP.add(addTextBox);
        addP.add(addB);
        mainPanel.add(addP);

        initWidget(mainPanel);

    }

    public void onClick(Widget sender) {
        mainPanel.insert(new Label(addTextBox.getText()), 1);
        addTextBox.setText("");

    }
}
