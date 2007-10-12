package com.apress.progwt.client.calculator;

/**
 * Represents an action that the calculator can perform.
 * 
 * Actions will be given the current and previous values as doubles and shoudl return a result to
 * write to the result textbox.
 * 
 * Override isEnder() to specify that you'd like
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

	public abstract double performAction(double previous, double current);

	/**
	 * Are we a result changer? or a 1/2 step, like +/-/
	 * 
	 * if true, we'll wait for the = sign
	 * 
	 * @return
	 */
	public boolean isMultiArg() {
		return true;
	}

	/**
	 * for enders, toggle whether to use our action (sqrt, 1/x, etc) or the previousAction (=)
	 * 
	 * @return
	 */
	public boolean useSelf() {
		return true;
	}


}
