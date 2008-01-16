package com.apress.progwt.client.college.gui;

import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.college.gui.ext.EditableLabelExtension;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProConDispPanel extends Composite implements ClickListener,
        ChangeListener {

    private VerticalPanel mainPanel;
    private TextBox addTextBox;
    private List<EditableLabelExtension> labels = new ArrayList<EditableLabelExtension>();
    private CollegeEntry collegeEntry;

    public ProConDispPanel(String string, List<String> list,
            CollegeEntry collegeEntry) {
        this.collegeEntry = collegeEntry;
        mainPanel = new VerticalPanel();
        mainPanel.add(new Label(string));
        for (String str : list) {
            addRow(str);
        }

        mainPanel.setStylePrimaryName("ProConPanel");
        mainPanel.addStyleDependentName(string);

        HorizontalPanel addP = new HorizontalPanel();
        addTextBox = new TextBox();
        Button addB = new Button("Add");
        addB.addClickListener(this);
        addP.add(addTextBox);
        addP.add(addB);
        mainPanel.add(addP);

        initWidget(mainPanel);

    }

    private void addRow(String str) {
        EditableLabelExtension ele = new EditableLabelExtension(str, this);
        labels.add(ele);
        mainPanel.insert(ele, 1);
    }

    public void onClick(Widget sender) {
        addRow(addTextBox.getText());
        addTextBox.setText("");
        onChange(sender);
    }

    public void bindFields(List<String> list) {
        list.clear();
        for (EditableLabelExtension l : labels) {
            list.add(l.getText());
        }
    }

    public void onChange(Widget sender) {
        collegeEntry.needsSave(true);
    }
}
