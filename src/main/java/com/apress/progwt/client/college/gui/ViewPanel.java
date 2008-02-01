package com.apress.progwt.client.college.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.gui.ext.ClientMouseImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelVelocity;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
import com.google.gwt.user.client.ui.Widget;

public abstract class ViewPanel extends AbsolutePanel implements
        MouseListener, SourcesChangeEvents, MouseWheelListener {

    private class RedrawParams {
        public int centerX;
        public int centerY;
        public int halfHeight;
        public int halfWidth;
        public double yScale;
    }

    protected int backX = 0;
    protected int backY = 0;
    private ChangeListenerCollection changeCollection = new ChangeListenerCollection();

    private int curbackX = 0;

    private int curbackY = 0;

    protected double currentScale = 1;

    private boolean doYTranslate = true;

    private boolean doZoom;

    protected boolean dragEnabled = true;

    private boolean dragging;

    private int dragStartX;

    private int dragStartY;
    private EventBackdrop focusBackdrop;

    protected List objects = new ArrayList();

    private ClientMouseImpl clientMouseImpl;

    public ViewPanel() {
        super();

        clientMouseImpl = (ClientMouseImpl) GWT
                .create(ClientMouseImpl.class);

        focusBackdrop = new EventBackdrop();
        makeThisADragHandle(focusBackdrop);
        add(focusBackdrop, 0, 0);

    }

    public void addChangeListener(ChangeListener listener) {
        changeCollection.add(listener);
    }

    public void addObject(RemembersPosition rp) {
        add(rp.getWidget(), (int) rp.getLeft(), rp.getTop());
        objects.add(rp);
    }

    protected void centerOn(int x, int y) {

        // Log.debug("centering on "+x+" "+ y);

        int width = getWidth();
        int height = getHeight();

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        int left = (int) (x * currentScale * getXSpread()) - halfWidth;
        int top = (int) (y * currentScale) - halfHeight;

        // Log.debug("P.X "+P.X+" HW "+HALFWIDTH+" "+LEFT);
        // Log.debug("left "+left+" top "+top);

        // intuitively this is (left - curbackX) but things are reversed
        // int dx = left + curbackX;
        // int dy = top + curbackY;

        int dx = left + curbackX;
        int dy = top + curbackY;
        moveBy(dx, dy);

    }

    /**
     * 
     */
    protected void centerOnMouse() {

        int halfWidth = getWidth() / 2;
        int halfHeight = getHeight() / 2;

        // lastx, lasty are just a backup in case this doesn't work.
        // They're not a great backup,
        // because if child objects have obscured us we won't be getting
        // mouseMove events and we'll
        // be weird relative x & y's from senders anyway.
        // BUT it turns out that FF clientX & Y are rogered for
        // ScrollWheel events, -> replace-with
        // MozillaImpl
        Event curEvent = DOM.eventGetCurrentEvent();

        int lastx = clientMouseImpl.getClientX(curEvent);
        int lasty = clientMouseImpl.getClientY(curEvent);

        int dx = lastx - getAbsoluteLeft() - halfWidth;
        int dy = lasty - getAbsoluteTop() - halfHeight;

        Log.debug("ViewPanel.centerOnMouse last x " + lastx + " absLeft "
                + getAbsoluteLeft() + " curbackx " + curbackX + " dx "
                + dx);
        Log.debug("ViewPanel.centerOnMouse last y " + lasty + " absTop "
                + getAbsoluteTop() + " curbacky " + curbackY + " dy "
                + dy);

        moveBy(dx, dy);

    }

    /**
     * don't let a regular clear() happen or you'll lose the focusBackdrop
     */
    public void clear() {
        // Log.debug("calling our clear()");
        for (Iterator iter = objects.iterator(); iter.hasNext();) {
            Widget w = (Widget) iter.next();
            remove(w);
            iter.remove();
        }
    }

    private void endDrag() {
        if (dragging) {
            // Log.debug("(old)back x "+backX+" cur(new)
            // "+curbackX);
            // Log.debug("(old)back y "+backY+" cur(new)
            // "+curbackY);
            backX = curbackX;
            backY = curbackY;
        }
        dragging = false;
    }

    /**
     * Make sure that we're zoomed to 'scale' or higher
     * 
     * return the value that we settle on
     */
    public double ensureZoomOfAtLeast(double scale) {
        if (scale > currentScale) {
            zoomTo(scale);
        }
        return currentScale;
    }

    protected void finishZoom(double oldScale) {

        setBackground(currentScale);

        int width = getWidth();
        int height = getHeight();

        int centerX = getCenterX(oldScale, width);
        int centerY = getCenterY(oldScale, height);

        // moveTo(centerX, centerY);

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        reCenter(centerX, centerY, currentScale, halfWidth, halfHeight);

        redraw();

        postZoomCallback(currentScale);

        redraw();

    }

    public int getBackX() {
        return backX;
    }

    public int getBackY() {
        return backY;
    }

    protected int getCenterX() {
        return getCenterX(currentScale, getWidth());
    }

    protected int getCenterX(double scaleToUse, int width) {
        int halfWidth = width / 2;
        int centerX = (int) ((-curbackX + halfWidth) / (scaleToUse * getXSpread()));

        // Log.debug("get Center X "+scaleToUse+" "+(-curbackX +
        // halfWidth)+" "+centerX);
        return centerX;
    }

    protected int getCenterY() {
        return getCenterY(currentScale, getHeight());
    }

    protected int getCenterY(double scaleToUse, int height) {
        int halfHeight = height / 2;
        int centerY = (int) ((-curbackY + halfHeight) / scaleToUse);
        return centerY;
    }

    public int getCurbackX() {
        return curbackX;
    }

    public int getCurbackY() {
        return curbackY;
    }

    public EventBackdrop getFocusBackdrop() {
        return focusBackdrop;
    }

    protected abstract int getHeight();

    public int[] getLongLatForXY(int absLeft, int absTop) {

        int oceanLeft = getBackX();
        int oceanTop = getBackY();

        int newLeft = absLeft - oceanLeft;
        int newTop = absTop - oceanTop;

        int lng = (int) (newLeft / currentScale);
        int lat = (int) (newTop / currentScale);

        return new int[] { lng, lat };

    }

    private RedrawParams getParams(int dy) {
        RedrawParams rd = new RedrawParams();
        rd.yScale = 1;
        if (isDoYTranslate()) {
            curbackY = -dy + backY;
            rd.yScale = currentScale;
        }

        DOM.setStyleAttribute(getElement(), "backgroundPosition",
                curbackX + "px " + curbackY + "px");

        int width = getWidth();
        int height = getHeight();

        rd.halfWidth = width / 2;
        rd.halfHeight = height / 2;

        rd.centerX = getCenterX(currentScale, width);
        rd.centerY = getCenterY(rd.yScale, height);
        return rd;
    }

    public int getPositionX(int left) {

        // Log.debug("getPositionX " + left + " " + currentScale
        // + " " + getXSpread() + " "
        // + curbackX);
        return (int) (left * currentScale * getXSpread()) + curbackX;
    }

    public int getPositionXFromGUIX(int guix) {

        Log.debug("getPositionXFromGuiX " + guix + " " + currentScale
                + " " + getXSpread() + " " + curbackX);
        return (int) ((guix - curbackX) / currentScale / (double) getXSpread());
    }

    protected abstract int getWidth();

    /**
     * Basically an extra zoom factor. Spread to the size of the
     * background. Overridden to 600 for ZoomableTimeline
     * 
     * @return
     */
    protected int getXSpread() {
        return 1;
    }

    public boolean isDoYTranslate() {
        return doYTranslate;
    }

    public boolean isDoZoom() {
        return doZoom;
    }

    protected void makeThisADragHandle(Widget widget) {

        if (doZoom) {
            if (widget instanceof SourcesMouseWheelEvents) {
                SourcesMouseWheelEvents wheeler = (SourcesMouseWheelEvents) widget;
                wheeler.addMouseWheelListener(this);
            }
        }
        if (widget instanceof SourcesMouseEvents) {
            SourcesMouseEvents mouser = (SourcesMouseEvents) widget;
            mouser.addMouseListener(this);
        }
    }

    /**
     * This moves the background and then sets the back position. Call
     * this when you want a move.
     * 
     * @param dx
     * @param dy
     */
    public void moveBy(int dx, int dy) {
        moveByDelta(dx, dy);

        // this was normally set in finishDrag()
        backX = curbackX;
        backY = curbackY;
    }

    /**
     * Internal move method. Doesn't actually 'finish' the move. This
     * helps us make dragging smoother.
     * 
     * Use moveBy() unless you'll finish yourself.
     * 
     * Takes dx, dy as SouthEast (+,+) NW (-,-)
     * 
     * @param dx
     * @param dy
     */
    protected void moveByDelta(int dx, int dy) {
        curbackX = -dx + backX;

        RedrawParams rd = getParams(dy);

        // Log.debug("ViewPanel.moveByDelta dx " + dx + " dy " +
        // dy);

        for (Iterator iter = objects.iterator(); iter.hasNext();) {
            Object o = iter.next();
            RemembersPosition rp = (RemembersPosition) o;

            redrawObj(rp, rd);
        }

        moveOccurredCallback();
        // Log.debug("moved "+curbackX+" "+curbackY);

    }

    protected void moveOccurredCallback() {
    }

    /**
     * Do an absolute move
     * 
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        int dx = backX - x;
        int dy = backY - y;

        moveByDelta(dx, dy);

        // this was normally set in finishDrag()
        backX = curbackX;
        backY = curbackY;
    }

    /**
     * Override this if you want object move event processing
     * 
     * @param o
     * @param halfWidth
     * @param halfHeight
     * @param centerX
     * @param centerY
     */
    protected void objectHasMoved(RemembersPosition o, int halfWidth,
            int halfHeight, int centerX, int centerY) {
    }

    public void onMouseDown(Widget sender, int x, int y) {
        // Log.debug("down "+(sender instanceof Island) +" x
        // "+x+" y "+y +" "+dragging);

        // Log.debug("mouse downd " + GWT.getTypeName(sender) + "
        // x " + x + " y " + y + " "
        // + sender.getAbsoluteLeft() + " " + sender.getAbsoluteTop());

        // if (!dragEnabled || sender instanceof FocusPanel) {
        dragging = true;
        // }

        dragStartX = x + sender.getAbsoluteLeft();
        dragStartY = y + sender.getAbsoluteTop();

        // Log.debug("down " + GWT.getTypeName(sender) + "dsx " +
        // dragStartX + " dsy "
        // + dragStartY + " x " + x + " y " + y + " " +
        // sender.getAbsoluteLeft() + " "
        // + sender.getAbsoluteTop());

        unselect();
    }

    public void onMouseEnter(Widget sender) {
    }

    public void onMouseLeave(Widget sender) {
        endDrag();
    }

    public void onMouseMove(Widget sender, int x, int y) {
        int lastx = x + sender.getAbsoluteLeft();
        int lasty = y + sender.getAbsoluteTop();
        clientMouseImpl.setLastXY(lastx, lasty);

        int dx = dragStartX - x - sender.getAbsoluteLeft();
        int dy = dragStartY - y - sender.getAbsoluteTop();

        // Log.debug("move "+(sender instanceof Island) +" x
        // "+x+" y "+y +" "+dragging);
        if (dragging) {
            moveByDelta(dx, dy);
            changeCollection.fireChange(this);
        }
    }

    public void onMouseUp(Widget sender, int x, int y) {
        // Log.debug("up " + GWT.getTypeName(sender) + "dsx " +
        // dragStartX + " dsy "
        // + dragStartY + " x " + x + " y " + y + " " +
        // sender.getAbsoluteLeft() + " "
        // + sender.getAbsoluteTop());

        // Log.debug("up "+(sender instanceof Island) +" x "+x+"
        // y "+y +" "+dragging);
        endDrag();
    }

    public void onMouseWheel(Widget sender, MouseWheelVelocity velocity) {
        if (velocity.isSouth()) {
            zoomOut();
        } else {
            centerOnMouse();
            zoomIn();
        }
    }

    protected void postZoomCallback(double currentScale) {
    }

    private void reCenter(int centerX, int centerY, double scale,
            int halfWidth, int halfHeight) {

        // Log.debug("recenter\nback X "+backX+" backy "+backY);
        // Log.debug("center X "+centerX+" cy "+centerY+" scale
        // "+scale);

        // Log.debug("hw "+halfWidth+" hh "+halfHeight);
        // backX = halfWidth - halfWidth/currentScale;

        int newCenterX = (int) (centerX * scale * getXSpread());
        int newCenterY = (int) (centerY * scale);

        // Log.debug("new center X "+newCenterX+" "+newCenterY);

        backX = -(newCenterX - halfWidth);
        backY = -(newCenterY - halfHeight);

        // Log.debug("Newback X "+backX+" NEWbacky "+backY);

    }

    /**
     * redraw all
     */
    public void redraw() {
        // Log.debug("ViewPanel.redraw()");
        moveBy(0, 0);
    }

    /**
     * redraw a single object
     * 
     * @param rp
     */
    public void redraw(RemembersPosition rp) {
        Log.debug("ViewPanel.redraw(RP) ");
        RedrawParams rd = getParams(0);
        redrawObj(rp, rd);
        moveOccurredCallback();
    }

    private void redrawObj(RemembersPosition rp, RedrawParams params) {
        // Log.debug("found: "+GWT.getTypeName(rp));

        // Log.debug("Left "+isle.getLeft()+" Top
        // "+isle.getTop());
        // Log.debug("cur "+curbackX+" cury "+curbackY);

        // setWidgetPosition(rp.getWidget(),(int)((rp.getLeft()+curbackX)*currentScale),
        // (int)((rp.getTop()+curbackY)*currentScale));

        // Log.debug("move "+rp.getLeft()+"
        // "+(int)((rp.getLeft())*currentScale)+"
        // "+(int)((rp.getLeft())*currentScale*getXSpread())+" cs
        // "+currentScale);
        try {

            setWidgetPosition(rp.getWidget(), getPositionX(rp.getLeft()),
                    (int) ((rp.getTop()) * params.yScale) + curbackY);

        } catch (RuntimeException e) {

            Log.error("ERROR: ViewPanel. couldn't move: "
                    + rp.getWidget());

            throw e;
        }
        objectHasMoved(rp, params.halfWidth, params.halfHeight,
                params.centerX, params.centerY);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeCollection.remove(listener);
    }

    public boolean removeObj(Widget w) {
        Log.debug("ViewPanel.remove " + GWT.getTypeName(w));
        super.remove(w);
        return objects.remove(w);
    }

    protected abstract void setBackground(double scale);

    public void setDoYTranslate(boolean doYTranslate) {
        this.doYTranslate = doYTranslate;
    }

    public void setDoZoom(boolean doZoom) {
        this.doZoom = doZoom;
        makeThisADragHandle(focusBackdrop);
    }

    protected void unselect() {

    }

    public void zoomIn() {
        double oldScale = currentScale;

        currentScale *= 2;

        finishZoom(oldScale);
    }

    public void zoomOut() {
        double oldScale = currentScale;

        currentScale /= 2;

        finishZoom(oldScale);
    }

    public void zoomTo(double scale) {
        if (scale == currentScale) {
            return;
        }
        double oldScale = currentScale;

        currentScale = scale;

        finishZoom(oldScale);

    }
}
