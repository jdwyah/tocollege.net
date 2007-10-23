package com.apress.progwt.client.calculator;

/**
 * Represents an action that the calculator can perform.
 * 
 * Actions will be given the current and previous values as doubles and
 * should return a result to write to the result textbox.
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public abstract class ControlAction {

    private String displayString;
    private Calculator calculator;

    public ControlAction(Calculator calculator, String displayString) {
        this.displayString = displayString;
        this.calculator = calculator;
    }

    public String getDisplayString() {
        return displayString;
    }

    public abstract double performAction(ControlAction lastAction,
            double previous, double current);

    /**
     * Does this action change the result right away? or does it wait for
     * another argument and then an equals sign? eg (+/-/*)
     * 
     * if true, we'll wait for the = sign
     * 
     * @return
     */
    public boolean isMultiArg() {
        return true;
    }

}
