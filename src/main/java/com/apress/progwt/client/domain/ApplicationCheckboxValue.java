package com.apress.progwt.client.domain;

/**
 * Not persisted to DB as an object. Wrap the int -> object conversion.
 * 
 * @author Jeff Dwyer
 * 
 */
public class ApplicationCheckboxValue {

    private int value;

    /**
     * avoid Integer == null conversion to int NPE
     * 
     * @param _value
     */
    public ApplicationCheckboxValue(Integer _value) {
        if (_value == null) {
            this.value = 0;
        } else {
            this.value = _value;
        }
    }

    public String getString() {
        switch (value) {
        case 0:
            return "N/A";
        case 1:
            return "No";
        case 2:
            return "Yes";
        case 3:
            return "TODO";
        default:
            throw new RuntimeException();
        }
    }

    public void increment() {
        value++;
        if (value == 4) {
            value = 0;
        }
    }

}
