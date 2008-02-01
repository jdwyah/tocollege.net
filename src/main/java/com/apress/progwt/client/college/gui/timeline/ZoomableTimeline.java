package com.apress.progwt.client.college.gui.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.RemembersPosition;
import com.apress.progwt.client.college.gui.ViewPanel;
import com.apress.progwt.client.college.gui.ext.DblClickListener;
import com.apress.progwt.client.consts.ConstHolder;
import com.apress.progwt.client.ext.collections.GWTSortedMap;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * NOTE: make sure you understand the moveOccurred() and objectHasMoved()
 * callbacks
 * 
 * @author Jeff Dwyer
 * 
 */
public class ZoomableTimeline<T> extends ViewPanel implements
        ClickListener, DblClickListener {

    /**
     * just wrap the two backdrop click listeners together fopr simplicity
     * 
     * @author Jeff Dwyer
     * 
     */
    private class BackdropClickListener implements ClickListener,
            DblClickListener {

        public void onClick(Widget sender) {

            if (getFocusBackdrop().getLastClickEventCtrl()) {
                openContextMenu();
            } else {
                setSelected(null, false);
            }
        }

        public void onDblClick(Widget sender) {
            openContextMenu();
        }

        private void openContextMenu() {

            // int x = getFocusBackdrop().getLastClickClientX();
            // int y = getFocusBackdrop().getLastClickClientY();

            // ContextMenu p = new TimelineContextMenu(manager,
            // ZoomableTimeline.this, x);
            // p.show(x, y);

        }
    }

    static final String IMG_POSTFIX = "timeline/";

    private static final int NUM_LABELS = 5;
    private static final int WINDOW_GUTTER = 7;
    /**
     * This needs to correspond to the width of the background images.
     * It's basically an extra zoom
     */
    private static final int X_SPREAD = 600;

    private static final int Y_SPREAD = 17;

    private int height;
    private List<ProteanLabel> labelList = new ArrayList<ProteanLabel>();

    private Image magBig;
    private Image magSmall;

    private TimelineRemembersPosition selectedRP;
    private CheckBox showCreated;

    private GWTSortedMap<TimeLineObj<T>, Object> sorted = new GWTSortedMap<TimeLineObj<T>, Object>();

    private Label whenlabel;
    private int width;

    private int yEnd;
    private int[] ySlots;
    private boolean ySlotsDirty = false;
    private int ySpread;

    private int yStart;

    private ServiceCache serviceCache;

    private ZoomLevel currentZoom;

    private ZoomLevel oldZoom;

    public ZoomableTimeline(ServiceCache serviceCache, int width,
            int height) {
        super();
        this.serviceCache = serviceCache;
        this.height = height;
        this.width = width;
        init();

        setStylePrimaryName("ZoomableTL");

        setPixelSize(width, height);
        setDoYTranslate(false);

        setDoZoom(true);

        currentZoom = ZoomLevel.Year;
        currentScale = currentZoom.getScale();

        createDecorations();
        drawHUD();
        setBackground(currentScale);

        BackdropClickListener bdClickListener = new BackdropClickListener();
        getFocusBackdrop().addDblClickListener(bdClickListener);
        getFocusBackdrop().addClickListener(bdClickListener);

    }

    public void add(List<TimeLineObj<T>> timelines) {

        Log.debug("!!!!!Zoom add " + timelines.size() + " sorted size "
                + sorted.size());

        for (TimeLineObj<T> timeLineObj : timelines) {
            sorted.put(timeLineObj, null);
        }

        super.clear();
        initYSlots(false);

        Log.debug("addObj " + sorted.size());

        for (TimeLineObj<T> tlo : sorted.keySet()) {

            // int top = (int) (Math.random()*(double)height);

            TimelineRemembersPosition rp = TimeLineObjFactory.getWidget(
                    this, tlo);

            int slot = getBestSlotFor(rp);
            int top = yStart + (slot * ySpread);
            rp.setTop(top);

            if (slot < 0) {
                rp.getWidget().setVisible(false);
            }
            addObject(rp);

        }

        for (int i = -NUM_LABELS; i < NUM_LABELS; i++) {
            ProteanLabel ll = new ProteanLabel(i, yStart - 15);
            labelList.add(ll);
            addObject(ll);
        }

        if (!sorted.isEmpty()) {

            TimeLineObj<T> last = sorted.getKeyList().get(
                    sorted.size() - 1);
            Log.debug("last " + last);
            if (last != null) {
                // Log.debug("move to "+last.getLeft()+"
                // "+TimeLineObj.getDateForLeft(last.getLeft()));
                centerOn(last.getLeft(), 0);
            }
        }

        updateLabels();
        redraw();
    }

    // @Override
    public void clear() {
        super.clear();
        sorted.clear();

    }

    private void createDecorations() {
        whenlabel = new Label();

        magBig = ConstHolder.images.magnifyingBig().createImage();
        magBig.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                zoomIn();
            }
        });

        magSmall = ConstHolder.images.magnifyingSmall().createImage();
        magSmall.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                zoomOut();
            }
        });

        showCreated = new CheckBox("Topics");
        showCreated.setChecked(true);
        showCreated.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                showCreated(showCreated.isChecked());
            }
        });

        // editWidget = new TimelineEditBox(manager);
        //
        // add(editWidget);
        add(magSmall);
        add(whenlabel);
        add(magBig);
        // add(showCreated);
    }

    private void drawHUD() {
        int center = width / 2 - 50;
        center -= 50;// offset left
        int y = yEnd + 30;
        setWidgetPosition(magSmall, center - 40, y - 15);
        setWidgetPosition(whenlabel, center, y);
        setWidgetPosition(magBig, center + 70, y - 15);
        // setWidgetPosition(showCreated, center + 115, y);

        // setWidgetPosition(editWidget, center + 115, y - 20);
    }

    /**
     * Determine how far each element extends in the x-axis
     * 
     * return -1 if we can't fit
     * 
     * @param left
     * @param string
     * @return
     */
    private int getBestSlotFor(TimelineRemembersPosition rp) {
        int i = 0;

        int mywidth = (int) (rp.getWidth() / (double) getXSpread() / currentScale);

        for (; i < ySlots.length; i++) {
            int lastLeftForThisSlot = ySlots[i];

            // Log.debug("gb "+i+" "+lastLeftForThisSlot+"
            // "+tlo.getLeft()+" mywid
            // "+mywidth);
            if (lastLeftForThisSlot < rp.getLeft()) {

                ySlots[i] = (int) (rp.getLeft() + mywidth);
                // Log.debug("Ether.choose "+i);
                return i;
            }
        }
        // Log.debug("Ether.fail!!!!!!!!");
        return -1;
    }

    // @Override
    protected int getHeight() {
        return height;
    }

    public Widget getWidget() {
        return this;
    }

    // @Override
    protected int getWidth() {
        return width;
    }

    // @Override
    protected int getXSpread() {
        return X_SPREAD;
    }

    // private String getZoomStr(double scale) {
    // int index = zoomList.indexOf(new Double(scale));
    // return backGroundList.get(index);
    // }

    private void init() {
        yStart = 25;
        yEnd = height - 60;
        ySpread = Y_SPREAD;

        ySlots = new int[(yEnd - yStart) / ySpread];
        initYSlots();
    }

    /**
     * force a re-jiggering of the yslots on the next redraw()
     * 
     * @param dirty
     */
    private void initYSlots() {
        initYSlots(true);
    }

    private void initYSlots(boolean dirty) {

        Log.debug("ZoomableTimeline.initYSlots(" + dirty + ")");
        ySlotsDirty = dirty;

        for (int i = 0; i < ySlots.length; i++) {
            ySlots[i] = Integer.MIN_VALUE;
        }
    }

    // @Override
    protected void moveOccurredCallback() {

        // Log.debug("ZoomableTimeline.moveOccurredCallback
        // settingYSlots !dirty");

        // 600, otherwise 1 pixel per SCALE length

        updateLabels();

        ySlotsDirty = false;

        // setWidgetPosition(ll.getWidget(), ll.getLeft(), ll.getTop());
    }

    protected void objectHasMoved(RemembersPosition o, int halfWidth,
            int halfHeight, int centerX, int centerY) {

        // ProteanLabels come in here too
        if (o instanceof TimelineRemembersPosition) {

            // Log.debug("ZoomableTimelin.objHasMoved " +
            // ySlotsDirty + " " + o.getLeft()
            // + " " + o.getTop() + " " + o);

            // PEND MED necesary if they've been editting a range, but
            // besides that this is not
            // necessary
            o.zoomToScale(currentScale);

            TimelineRemembersPosition tlw = (TimelineRemembersPosition) o;

            if (ySlotsDirty) {

                int slot = getBestSlotFor(tlw);

                // Log.debug("best top "+slot+"
                // "+tlw.getTlo().getTopic().getTopicTitle()+"
                // "+(yStart + (slot * ySpread)));

                if (slot < 0) {
                    o.getWidget().setVisible(false);
                } else {
                    tlw.setTop(yStart + (slot * ySpread));

                    o.getWidget().setVisible(true);
                }

            }
        }

    }

    public void onClick(Widget sender) {
        TimelineRemembersPosition rp = (TimelineRemembersPosition) sender;

        setSelected(rp, true);

    }

    public void onDblClick(Widget sender) {

    }

    // @Override
    protected void postZoomCallback(double currentScale) {
        updateLabels();

    }

    public void resize(int newWidth, int newHeight) {

        width = newWidth;
        height = newHeight;

        init();

        setPixelSize(width, height);

        drawHUD();

        redraw();

    }

    protected void setBackground(ZoomLevel zoomLevel) {

        if (oldZoom != null) {
            removeStyleDependentName(oldZoom.getCssClass());
        }
        addStyleDependentName(currentZoom.getCssClass());

        Log.debug("SetBackground: " + currentZoom.getCssClass() + "::"
                + getStyleName());
    }

    @Override
    protected void setBackground(double scale) {

        ZoomLevel newZoom = ZoomLevel.getZoomForScale(scale);
        setBackground(newZoom);
        //
        // int index = zoomList.indexOf(new Double(scale));
        //
        // String img = backGroundList.get(index);
        //
        // Log.debug("setBack " + scale + " " + index + " " + img);
        //
        // addStyleDependentName(img);
        //
        // DOM.setStyleAttribute(getElement(), "backgroundImage", "url("
        // + ConstHolder.getImgLoc(IMG_POSTFIX) + img + ".png)");
    }

    public Date setDateFromDrag(TimeLineObj tlo,
            TimelineRemembersPosition rp, int clientX, boolean leftSide,
            boolean doSave) {

        // subtract the window left.
        // PEND low, this is missing the gutter ~10px
        clientX -= getAbsoluteLeft() + WINDOW_GUTTER;

        Date rtn = null;

        if (leftSide) {
            // Log.debug("LEFT left " + left + " size " +
            // sizeThisZoom);
            // left += dx;

            // Log.debug("ZoomableTimeline start " +
            // tlo.getHasDate().getStartDate());

            rtn = tlo.setStartDateToX(getPositionXFromGUIX(clientX));

            // Log.debug("ZoomableTimeline start " +
            // tlo.getHasDate().getStartDate());

        } else {
            // Log.debug("RIGHT left " + left + " size " +
            // sizeThisZoom);

            // Log.debug("ZoomableTimeline end " +
            // tlo.getHasDate().getEndDate());

            rtn = tlo.setEndDateToX(getPositionXFromGUIX(clientX));

            // Log.debug("ZoomableTimeline end " +
            // tlo.getHasDate().getEndDate());
        }

        if (doSave) {

        }

        redraw(rp);

        return rtn;
    }

    private void setSelected(TimelineRemembersPosition rp,
            boolean selected) {
        if (selected) {
            unselect();
            selectedRP = rp;
            // editWidget.setTopicAndVisible(selectedRP);
            selectedRP.addStyleName("Selected");
        } else {
            unselect();
        }
    }

    private void showCreated(boolean checked) {
        // TODO Auto-generated method stub

    }

    // @Override
    protected void unselect() {
        // editWidget.setVisible(false);
        if (selectedRP != null) {
            selectedRP.removeStyleName("Selected");
        }
        selectedRP = null;
    }

    private void updateLabels() {

        // int index = zoomList.indexOf(new Double(currentScale));

        int ii = getCenterX();
        Date d2 = TimeLineObj.getDateFromViewPanelX(ii);

        whenlabel.setText(ZoomLevel.Month.getDfFormat().format(d2));

        // Log.debug("ZoomableTimeline.updateLabels curback
        // "+-getCurbackX()+" "+" "+d2+"
        // ii "+ii+" "+backGroundList.get(index));

        DateTimeFormat format = currentZoom.getDfFormat();
        for (ProteanLabel label : labelList) {
            label.setCenter(d2, currentZoom);
        }
    }

    public void zoom(int upDown) {

        double oldScale = currentScale;
        oldZoom = currentZoom;

        // int index = zoomList.indexOf(new Double(oldScale));
        //
        // // Log.debug("ZoomableTL zoom: index " + index + " next "
        // // + (index + upDown));
        // index += upDown;
        //
        // index = index < 0 ? 0 : index;
        //
        // // TODO !!!!!!!
        // // NOTE the 2 this makes us unable to go up to Millenium, which
        // is
        // // only there to give us a higherScale
        // index = index >= zoomList.size() - 1 ? zoomList.size() - 2
        // : index;

        Log.debug("previous zoom: " + currentZoom + " css: "
                + currentZoom.getCssClass());

        ZoomLevel newZoom = null;
        if (upDown > 0) {
            newZoom = ZoomLevel.zoomOutOneFrom(currentZoom);
        } else {
            newZoom = ZoomLevel.zoomInOneFrom(currentZoom);
        }
        if (newZoom != null) {
            currentZoom = newZoom;
        }
        currentScale = currentZoom.getScale();

        Log.debug("new zoom: " + currentZoom + " css: "
                + currentZoom.getCssClass());

        initYSlots();

        finishZoom(oldScale);

    }

    // @Override
    public void zoomIn() {

        zoom(-1);
    }

    // @Override
    public void zoomOut() {
        zoom(1);
    }
}
