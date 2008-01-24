package com.apress.progwt.client.college.gui.ext;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusListenerCollection;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.FocusImpl;

/**
 * FocusPanel gives us no good way to see if the ctrl key was down, or do
 * double clicks.
 * 
 * 
 * Stores the ctrl key for before the event goes by and delays clickEvents
 * until we're sure it's not a double click before dispatching.
 * 
 * TODO click stuff can be replaced because 1.4RC2 should have
 * DOM.eventGetCurrentEvent()
 * 
 * @author Jeff Dwyer
 * 
 */
public class FocusPanelExt extends SimplePanel implements HasFocus,
        SourcesClickEvents, SourcesMouseEvents, SourcesMouseWheelEvents {

    private static final int DBL_CLICK_DELAY = 250;
    static final FocusImpl impl = FocusImpl.getFocusImplForPanel();
    private ClickListenerCollection clickListeners;

    private Timer delayedclickFirer;
    private DblClickListenerCollection doubleClickLisenerCollection;
    private FocusListenerCollection focusListeners;
    private KeyboardListenerCollection keyboardListeners;
    private boolean lastClickEventCtrl;
    private MouseListenerCollection mouseListeners;
    private MouseWheelListenerCollection mouseWheelListeners;
    private int lastClickClientX;
    private int lastClickClientY;

    public FocusPanelExt() {
        super(impl.createFocusable());
        sinkEvents(Event.FOCUSEVENTS | Event.KEYEVENTS | Event.ONCLICK
                | Event.MOUSEEVENTS | Event.ONMOUSEWHEEL
                | Event.ONDBLCLICK);

        delayedclickFirer = new Timer() {
            public void run() {
                // Log.debug("DELAYED CLICK FIRE");
                clickListeners.fireClick(FocusPanelExt.this);
            }
        };
    }

    public FocusPanelExt(Widget child) {
        this();
        setWidget(child);
    }

    public void addClickListener(ClickListener listener) {
        if (clickListeners == null) {
            clickListeners = new ClickListenerCollection();
        }
        clickListeners.add(listener);
    }

    public void addDblClickListener(DblClickListener listener) {
        if (doubleClickLisenerCollection == null) {
            doubleClickLisenerCollection = new DblClickListenerCollection();
        }
        doubleClickLisenerCollection.add(listener);
    }

    public void addFocusListener(FocusListener listener) {
        if (focusListeners == null) {
            focusListeners = new FocusListenerCollection();
        }
        focusListeners.add(listener);
    }

    public void addKeyboardListener(KeyboardListener listener) {
        if (keyboardListeners == null) {
            keyboardListeners = new KeyboardListenerCollection();
        }
        keyboardListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener) {
        if (mouseListeners == null) {
            mouseListeners = new MouseListenerCollection();
        }
        mouseListeners.add(listener);
    }

    public void addMouseWheelListener(MouseWheelListener listener) {
        if (mouseWheelListeners == null) {
            mouseWheelListeners = new MouseWheelListenerCollection();
        }
        mouseWheelListeners.add(listener);
    }

    public boolean getLastClickEventCtrl() {
        return lastClickEventCtrl;
    }

    public int getTabIndex() {
        return impl.getTabIndex(getElement());
    }

    public void onBrowserEvent(Event event) {

        switch (DOM.eventGetType(event)) {
        case Event.ONCLICK:
            lastClickEventCtrl = DOM.eventGetCtrlKey(event);
            lastClickClientX = DOM.eventGetClientX(event);
            lastClickClientY = DOM.eventGetClientY(event);
            if (clickListeners != null) {
                // Log.debug("DELAYED CLICK SCHEDULE");
                delayedclickFirer.schedule(DBL_CLICK_DELAY);
            }
            break;

        case Event.ONMOUSEDOWN:
        case Event.ONMOUSEUP:
        case Event.ONMOUSEMOVE:
        case Event.ONMOUSEOVER:
        case Event.ONMOUSEOUT:
            if (mouseListeners != null) {
                mouseListeners.fireMouseEvent(this, event);
            }
            break;
        case Event.ONDBLCLICK:
            if (doubleClickLisenerCollection != null) {
                delayedclickFirer.cancel();
                // Log.debug("DELAYED CLICK CANCEL ON DOUBLE");
                doubleClickLisenerCollection.fireDoubleClick(this);
            }
            break;
        case Event.ONMOUSEWHEEL:
            if (mouseWheelListeners != null) {
                mouseWheelListeners.fireMouseWheelEvent(this, event);
            }
            break;

        case Event.ONBLUR:
        case Event.ONFOCUS:
            if (focusListeners != null) {
                focusListeners.fireFocusEvent(this, event);
            }
            break;

        case Event.ONKEYDOWN:
        case Event.ONKEYUP:
        case Event.ONKEYPRESS:
            if (keyboardListeners != null) {
                keyboardListeners.fireKeyboardEvent(this, event);
            }
            break;
        }
        // lastClickEvent = null;
    }

    public int getLastClickClientX() {
        return lastClickClientX;
    }

    public int getLastClickClientY() {
        return lastClickClientY;
    }

    public void removeClickListener(ClickListener listener) {
        if (clickListeners != null) {
            clickListeners.remove(listener);
        }
    }

    public void removeFocusListener(FocusListener listener) {
        if (focusListeners != null) {
            focusListeners.remove(listener);
        }
    }

    public void removeKeyboardListener(KeyboardListener listener) {
        if (keyboardListeners != null) {
            keyboardListeners.remove(listener);
        }
    }

    public void removeMouseListener(MouseListener listener) {
        if (mouseListeners != null) {
            mouseListeners.remove(listener);
        }
    }

    public void removeMouseWheelListener(MouseWheelListener listener) {
        if (mouseWheelListeners != null) {
            mouseWheelListeners.remove(listener);
        }
    }

    public void setAccessKey(char key) {
        impl.setAccessKey(getElement(), key);
    }

    public void setFocus(boolean focused) {
        if (focused) {
            impl.focus(getElement());
        } else {
            impl.blur(getElement());
        }
    }

    public void setTabIndex(int index) {
        impl.setTabIndex(getElement(), index);
    }
}
