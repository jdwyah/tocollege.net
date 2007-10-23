package com.apress.progwt.client.calculator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

public class GUIEffects {

    private static GUIEffects singleInstance = new GUIEffects();

    /**
     * Utility to fade & remove a widget after a short time using 2 timers
     * 
     * @param w
     * @param i
     */
    public static void fadeAndRemove(final Widget w, int fadeInX,
            int removeInX) {

        fade(w, fadeInX);

        removeInXMilSecs(w, removeInX);
    }

    public static void fadeAndRemove(Widget w, int removeInX) {
        fade(w, 500);

        removeInXMilSecs(w, removeInX);
    }

    public static void fade(Widget w, int duration) {
        opacity(w, 1.0, 0.0, duration);
    }

    /**
     * Utility to remove a widget after a short time, for instance after
     * we Effect.fade()
     * 
     * @param w
     * @param i
     */
    public static void removeInXMilSecs(final Widget w, int i) {
        Timer t = new Timer() {
            public void run() {
                w.removeFromParent();
            }
        };
        t.schedule(i);
    }

    /**
     * options are not safe in Hosted GWT
     * 
     * @param toMove
     * @param i
     * @param x
     * @param cloud_move_sec
     */
    public static void move(Widget toMove, int x, int y, int duration) {

        int steps = duration / MoveTimer.FREQ;

        MoveTimer mover = singleInstance.new MoveTimer(toMove, x, y,
                steps);

        mover.schedule(100);

    }

    /**
     * 
     * @param toAppear
     * @param duration
     */
    public static void appear(Widget toAppear, int duration) {
        opacity(toAppear, .1, 1.0, duration);
    }

    public static void opacity(Widget widget, double from, double to,
            int duration) {

        int steps = duration / OpacityTimer.FREQ;

        OpacityTimer opacity = singleInstance.new OpacityTimer(widget,
                from, to, steps);

        opacity.schedule(100);

    }

    private class OpacityTimer extends Timer {
        public static final int FREQ = 100;

        private Element element;
        private double delta;

        private double cur;
        private double from;
        private double to;

        private int steps;

        private int curStep;

        public OpacityTimer(Widget widget, double from, double to,
                int steps) {
            this.element = widget.getElement();
            this.delta = (to - from) / steps;
            this.from = from;
            this.to = to;
            this.steps = steps;
            cur = from;
            curStep = 0;
        }

        public void run() {

            // TODO strBuff
            String ieStr = "alpha(opacity = " + (int) (cur * 100) + ")";
            // System.out.println("cur "+cur+" "+from+" "+to+" "+delta+"
            // "+ieStr);
            DOM.setStyleAttribute(element, "filter", ieStr);
            DOM.setStyleAttribute(element, "-moz-opacity", cur + "");
            DOM.setStyleAttribute(element, "opacity", cur + "");
            DOM.setStyleAttribute(element, "-khtml-opacity", cur + "");

            cur += delta;
            curStep++;
            if (curStep > steps) {
                cancel();
            } else {
                schedule(FREQ);
            }
        }
    }

    private class MoveTimer extends Timer {
        public static final int FREQ = 100;

        private Element element;

        private int curX;
        private int curY;
        private int curStep = 0;

        private int dx;
        private int dy;

        private int steps;

        public MoveTimer(Widget widget, int x, int y, int steps) {
            this.element = widget.getElement();
            this.dx = x / steps;
            this.dy = y / steps;
            this.steps = steps;
            this.curX = DOM.getIntStyleAttribute(element, "left");
            this.curY = DOM.getIntStyleAttribute(element, "top");
        }

        public void run() {

            DOM.setIntStyleAttribute(element, "left", curX);
            DOM.setIntStyleAttribute(element, "top", curY);

            curX += dx;
            curY += dy;
            curStep++;

            if (curStep > steps) {
                cancel();
            } else {
                schedule(FREQ);
            }

        }
    }

    public static native void close() /*-{
                            	$wnd.close();
                            }-*/;

}
