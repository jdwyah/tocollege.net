package com.apress.progwt.client.college.gui.ext;

/*
 * Copyright 2006 Robert Hanson <iamroberthanson AT gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWordWrap;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Editable Label class, funcionality displays a Label UI Element until
 * clicked on, then if element is set to be editable (default) then an
 * editable area and Buttons are displayed instead.
 * 
 * If the Label is not set to be word wrapped (default) then the editable
 * area is a Text Box and clicking the OK button or hitting return key in
 * the TextBox will display the Label with the updated text.
 * 
 * If the Label is set to be word wrapped, using the setWordWrap(boolean)
 * method, then the editable area is a Text Area and clicking the OK
 * button will display the Label with the updated text.
 * 
 * In both cases, clicking Cancel button or hitting Escape key in the
 * TextBox/TextArea then the Label is displayed with original text.
 * 
 * @author Adam Tacy
 * @version 0.0.2
 * 
 * Changes since version 0.0.1 + made cancelLabelChange public [ref
 * request id: 1518134] + made originalText have default value of empty
 * string [to support ref request id: 1518134] *End*
 * 
 * NOTE! This is an extension of the EditableLabel that comes with
 * http://gwtwidgets.blogspot.com/ The only differences with respect to
 * the original is that ours has a "hover" listener & associated style
 * change and we're using the primary/dependent stylgins. ALSO CTOR will
 * change "" string to "Change Me" so that there's someplace to click
 * @author Jeff Dwyer
 * 
 */
public class EditableLabelExtension extends Composite implements
        HasWordWrap, HasText {

    protected static final String HOVER_STYLE = "hover";

    /**
     * TextBox element to enable text to be changed if Label is not word
     * wrapped
     */
    private TextBox changeText;

    /**
     * TextArea element to enable text to be changed if Label is
     * wordwrapped
     */
    private TextArea changeTextArea;

    /**
     * Label element, which is initially is diplayed.
     */
    private Label text;

    /**
     * String element that contains the original text of a Label prior to
     * it being edited.
     */
    private String originalText;

    /**
     * Simple button to confirm changes
     */
    private Widget confirmChange;

    /**
     * Simple button to cancel changes
     */
    private Widget cancelChange;

    /**
     * Flag to indicate that Label is in editing mode.
     */
    private boolean isEditing = false;

    /**
     * Flag to indicate that label can be edited.
     */
    private boolean isEditable = true;

    /**
     * Local copy of the update class passed in to the constructor.
     */
    private ChangeListener updater = null;

    /**
     * Default String value for OK button
     */
    private String defaultOkButtonText = "OK";

    /**
     * Default String value for Cancel button
     */
    private String defaultCancelButtonText = "Cancel";

    /**
     * Allows the setting of the isEditable flag, marking the label as
     * editable or not.
     * 
     * @param flag
     *            True or False value depending if the Label is to be
     *            editable or not
     */
    public void setEditable(boolean flag) {
        isEditable = flag;
    }

    /**
     * Returns the value of the isEditable flag.
     * 
     * @return
     */
    public boolean isFieldEditable() {
        return isEditable;
    }

    /**
     * Returns the value of the isEditing flag, allowing outside users to
     * see if the Label is being edited or not.
     * 
     * @return
     */
    public boolean isInEditingMode() {
        return isEditing;
    }

    /**
     * Change the displayed label to be a TextBox and copy label text into
     * the TextBox.
     * 
     */
    private void changeTextLabel() {
        if (isEditable) {
            // Set up the TextBox
            originalText = text.getText();

            // Change the view from Label to TextBox and Buttons
            text.setVisible(false);
            confirmChange.setVisible(true);
            cancelChange.setVisible(true);

            if (text.getWordWrap()) {
                // If Label word wrapped use the TextArea to edit
                changeTextArea.setText(originalText);
                changeTextArea.setVisible(true);
                changeTextArea.setFocus(true);
            } else {
                // Otherwise use the TextBox to edit.
                changeText.setText(originalText);
                changeText.setVisible(true);
                changeText.setFocus(true);
            }

            // Set instance as being in editing mode.
            isEditing = true;
        }
    }

    /**
     * Restores visibility of Label and hides the TextBox and Buttons
     * 
     */
    private void restoreVisibility() {
        // Change appropriate visibilities
        text.setVisible(true);
        confirmChange.setVisible(false);
        cancelChange.setVisible(false);
        if (text.getWordWrap()) {
            // If Label is word wrapped hide the TextArea
            changeTextArea.setVisible(false);
        } else {
            // Otherwise hide the TextBox
            changeText.setVisible(false);
        }
        // Set isEditing flag to false as we are no longer editing
        isEditing = false;
    }

    /**
     * Sets the Label text to the new value, restores the display and
     * calls the update method.
     * 
     */
    private void setTextLabel() {
        if (text.getWordWrap()) {
            // Set the Label to be the text in the Text Box
            text.setText(changeTextArea.getText());
        } else {
            // Set the Label to be the text in the Text Box
            text.setText(changeText.getText());
        }
        // Set the object back to display label rather than TextBox and
        // Buttons
        restoreVisibility();

        // Call the update method provided in the Constructor
        // (this could be anything from alerting the user through to
        // Making an AJAX call to store the data.
        updater.onChange(this);
    }

    /**
     * Sets the Label text to the original value, restores the display.
     * 
     */
    public void cancelLabelChange() {
        // Set the Label text back to what it was originally
        text.setText(originalText);
        // Set the object back to display Label rather than TextBox and
        // Buttons
        restoreVisibility();
    }

    /**
     * Creates the Label, the TextBox and Buttons. Also associates the
     * update method provided in the constructor with this instance.
     * 
     * @param labelText
     *            The value of the initial Label.
     * @param onUpdate
     *            The class that provides the update method called when
     *            the Label has been updated.
     * @param visibleLength
     *            The visible length (width) of the TextBox/TextArea.
     * @param maxLength
     *            The maximum length of text in the TextBox.
     * @param maxHeight
     *            The maximum number of visible lines of the TextArea
     * @param okButtonText
     *            The text diplayed in the OK button.
     * @param cancelButtonText
     *            The text displayed in the Cancel button.
     */
    private void createEditableLabel(String labelText,
            ChangeListener onUpdate, String okButtonText,
            String cancelButtonText) {
        // Put everything in a VerticalPanel
        FlowPanel instance = new FlowPanel();

        if (labelText == null || labelText.length() < 1) {
            labelText = "Click to edit me";
        }

        // Create the Label element and add a ClickListener to call out
        // Change method when clicked
        text = new Label(labelText);
        text.setStylePrimaryName("editableLabel-label");

        text.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                changeTextLabel();
            }
        });
        text.addMouseListener(new MouseListenerAdapter() {
            public void onMouseEnter(Widget sender) {
                text.addStyleDependentName(HOVER_STYLE);
            }

            public void onMouseLeave(Widget sender) {
                text.removeStyleDependentName(HOVER_STYLE);
            }
        });

        // Create the TextBox element used for non word wrapped Labels
        // and add a KeyboardListener for Return and Esc key presses
        changeText = new TextBox();
        changeText.setStyleName("editableLabel-textBox");

        changeText.addKeyboardListener(new KeyboardListenerAdapter() {
            public void onKeyPress(Widget sender, char keyCode,
                    int modifiers) {
                // If return then save, if Esc cancel the change,
                // otherwise do nothing
                switch (keyCode) {
                case 13:
                    setTextLabel();
                    break;
                case 27:
                    cancelLabelChange();
                    break;
                }
            }
        });

        // Create the TextAre element used for word-wrapped Labels
        // and add a KeyboardListener for Esc key presses (not return in
        // this case)

        changeTextArea = new TextArea();
        changeTextArea.setStyleName("editableLabel-textArea");

        changeTextArea.addKeyboardListener(new KeyboardListenerAdapter() {
            public void onKeyPress(Widget sender, char keyCode,
                    int modifiers) {
                // If Esc then cancel the change, otherwise do nothing
                switch (keyCode) {
                case 27:
                    cancelLabelChange();
                    break;
                }
            }
        });

        // Set up Confirmation Button
        confirmChange = createConfirmButton(okButtonText);

        if (!(confirmChange instanceof SourcesClickEvents)) {
            throw new RuntimeException(
                    "Confirm change button must allow for click events");
        }

        ((SourcesClickEvents) confirmChange)
                .addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                        setTextLabel();
                    }
                });

        // Set up Cancel Button
        cancelChange = createCancelButton(cancelButtonText);
        if (!(cancelChange instanceof SourcesClickEvents)) {
            throw new RuntimeException(
                    "Cancel change button must allow for click events");
        }

        ((SourcesClickEvents) cancelChange)
                .addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                        cancelLabelChange();
                    }
                });

        // Put the buttons in a panel
        FlowPanel buttonPanel = new FlowPanel();
        buttonPanel.setStyleName("editableLabel-buttonPanel");
        buttonPanel.add(confirmChange);
        buttonPanel.add(cancelChange);

        // Add panels/widgets to the widget panel
        instance.add(text);
        instance.add(changeText);
        instance.add(changeTextArea);
        instance.add(buttonPanel);

        // Set initial visibilities. This needs to be after
        // adding the widgets to the panel because the FlowPanel
        // will mess them up when added.
        text.setVisible(true);
        changeText.setVisible(false);
        changeTextArea.setVisible(false);
        confirmChange.setVisible(false);
        cancelChange.setVisible(false);

        // Set the updater method.
        updater = onUpdate;

        // Assume that this is a non word wrapped Label unless explicitly
        // set otherwise
        text.setWordWrap(false);

        // Set the widget that this Composite represents
        initWidget(instance);
    }

    /**
     * @param cancelButtonText
     */
    protected Widget createCancelButton(String cancelButtonText) {
        Button result = new Button();
        result.setStyleName("editableLabel-buttons");
        result.addStyleName("editableLabel-cancel");
        result.setText(cancelButtonText);
        return result;
    }

    /**
     * @param okButtonText
     */
    protected Widget createConfirmButton(String okButtonText) {
        Button result = new Button();
        result.setStyleName("editableLabel-buttons");
        result.addStyleName("editableLabel-confirm");
        result.setText(okButtonText);
        return result;
    }

    /**
     * Set the word wrapping on the label (if word wrapped then the
     * editable field becomes a TextArea, if not then the editable field
     * is a TextBox.
     * 
     * @param b
     *            Boolean value, true means Label is word wrapped, false
     *            means it is not.
     */
    public void setWordWrap(boolean b) {
        text.setWordWrap(b);
    }

    /**
     * Return whether the Label is word wrapped or not.
     */
    public boolean getWordWrap() {
        return text.getWordWrap();
    }

    /**
     * Return the text value of the Label
     */
    public String getText() {
        return text.getText();
    }

    /**
     * Set the text value of the Label
     */
    public void setText(String newText) {
        text.setText(newText);
    }

    /**
     * Sets the number of visible lines for a word-wrapped editable label.
     * 
     * @param number
     *            Number of visible lines.
     * @throws RuntimeException
     *             if the editable label is not word-wrapped.
     */
    public void setVisibleLines(int number) {
        if (text.getWordWrap()) {
            changeTextArea.setVisibleLines(number);
        } else {
            throw new RuntimeException(
                    "Cannnot set number of visible lines for a non word-wrapped Editable Label");
        }
    }

    /**
     * Get the number of Visible Lines of editable area of a word-wrapped
     * editable Label.
     * 
     * @return Number of Visible Lines.
     * @throws RuntimeException
     *             If the Label is not word-wrapped.
     */
    public int getVisibleLines() {
        if (text.getWordWrap()) {
            return changeTextArea.getVisibleLines();
        } else {
            throw new RuntimeException(
                    "Editable Label that is not word-wrapped has no number of Visible Lines");
        }
    }

    /**
     * Set maximum length of editable area.
     * 
     * @param length
     *            Length of editable area.
     */
    public void setMaxLength(int length) {
        if (text.getWordWrap()) {
            changeTextArea.setCharacterWidth(length);
        } else {
            changeText.setMaxLength(length);
        }
    }

    /**
     * Get maximum length of editable area.
     * 
     * @return maximum length of editable area.
     */
    public int getMaxLength() {
        if (text.getWordWrap()) {
            return changeTextArea.getCharacterWidth();
        } else {
            return changeText.getMaxLength();
        }
    }

    /**
     * Set the visible length of the editable area.
     * 
     * @throws RuntimeExcpetion
     *             If editable label is word wrapped.
     */
    public void setVisibleLength(int length) {
        if (text.getWordWrap()) {
            throw new RuntimeException(
                    "Cannnot set visible length for a word-wrapped Editable Label");
        } else {
            changeText.setVisibleLength(length);
        }
    }

    /**
     * Get the visible length of the editable area.
     * 
     * @return Visible length of editable area if not a word wrapped
     *         label.
     * @throws RuntimeExcpetion
     *             If editable label is word wrapped.
     */
    public int getVisibleLength() {
        if (text.getWordWrap()) {
            throw new RuntimeException(
                    "Cannnot get visible length for a word-wrapped Editable Label");
        } else {
            return changeText.getVisibleLength();
        }
    }

    /**
     * Constructor that changes default text for buttons and allows the
     * setting of the wordwrap property directly.
     * 
     * @param labelText
     *            The initial text of the label.
     * @param onUpdate
     *            Handler object for performing actions once label is
     *            updated.
     * @param okText
     *            Text for use in overiding the default OK button text.
     * @param cancelText
     *            Text for use in overiding the default CANCEL button
     *            text.
     * @param wordWrap
     *            Boolean representing if the label should be word wrapped
     *            or not
     */
    public EditableLabelExtension(String labelText,
            ChangeListener onUpdate, String okText, String cancelText,
            boolean wordWrap) {
        createEditableLabel(labelText, onUpdate, okText, cancelText);
        text.setWordWrap(wordWrap);
    }

    /**
     * Constructor that uses default text values for buttons and sets the
     * word wrap property.
     * 
     * @param labelText
     *            The initial text of the label.
     * @param onUpdate
     *            Handler object for performing actions once label is
     *            updated.
     * @param wordWrap
     *            Boolean representing if the label should be word wrapped
     *            or not
     */
    public EditableLabelExtension(String labelText,
            ChangeListener onUpdate, boolean wordWrap) {
        createEditableLabel(labelText, onUpdate, defaultOkButtonText,
                defaultCancelButtonText);
        text.setWordWrap(wordWrap);
    }

    /**
     * Constructor that changes default button text.
     * 
     * @param labelText
     *            The initial text of the label.
     * @param onUpdate
     *            Handler object for performing actions once label is
     *            updated.
     * @param okText
     *            Text for use in overiding the default OK button text.
     * @param cancelText
     *            Text for use in overiding the default CANCEL button
     *            text.
     */
    public EditableLabelExtension(String labelText,
            ChangeListener onUpdate, String okText, String cancelText) {
        createEditableLabel(labelText, onUpdate, okText, cancelText);
    }

    /**
     * Constructor that uses default text values for buttons.
     * 
     * @param labelText
     *            The initial text of the label.
     * @param onUpdate
     *            Handler object for performing actions once label is
     *            updated.
     */
    public EditableLabelExtension(String labelText,
            ChangeListener onUpdate) {
        createEditableLabel(labelText, onUpdate, defaultOkButtonText,
                defaultCancelButtonText);
    }

    /**
     * Contrucotr with default values and no handler
     * 
     * @param str
     */
    public EditableLabelExtension(String str) {
        this(str, new ChangeListener() {
            public void onChange(Widget sender) {
            }
        });
    }
}
