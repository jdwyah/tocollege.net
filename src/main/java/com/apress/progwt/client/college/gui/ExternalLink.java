package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.Interactive;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;

public class ExternalLink extends FocusWidget implements HasHTML,
        SourcesClickEvents, SourcesMouseEvents {

    private static native String urlEncode(String str)
    /*-{
        return escape( str );
    }-*/;

    private Element anchorElem;
    private ClickListenerCollection fClickListeners;
    private MouseListenerCollection fMouseListeners;

    private String target;

    /**
     * Creates an empty hyperlink.
     */
    public ExternalLink() {
        super(DOM.createDiv());
        DOM.appendChild(getElement(), anchorElem = DOM.createAnchor());
        sinkEvents(Event.ONCLICK);
        sinkEvents(Event.MOUSEEVENTS);

        setStyleName("H-External-Hyperlink");
    }

    public ExternalLink(String text, String url, boolean relative) {
        this();
        setText(text);

        if (relative) {
            setTarget(Interactive.getRelativeURL(urlEncode(url)));
        } else {
            setTarget(urlEncode(url));
        }
    }

    public void addClickListener(ClickListener listener) {
        if (fClickListeners == null)
            fClickListeners = new ClickListenerCollection();
        fClickListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener) {
        if (fMouseListeners == null)
            fMouseListeners = new MouseListenerCollection();
        fMouseListeners.add(listener);
    }

    public String getHTML() {
        return DOM.getInnerHTML(anchorElem);
    }

    /**
     * Gets the target referenced by this hyperlink.
     * 
     * @return the target history token
     * @see #setTargetHistoryToken
     */
    public String getTarget() {
        return target;
    }

    public String getText() {
        return DOM.getInnerText(anchorElem);
    }

    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONCLICK:
            if (fClickListeners != null)
                fClickListeners.fireClick(this);
            break;

        case Event.ONMOUSEDOWN:
        case Event.ONMOUSEUP:
        case Event.ONMOUSEMOVE:
        case Event.ONMOUSEOVER:
        case Event.ONMOUSEOUT:
            if (fMouseListeners != null)
                fMouseListeners.fireMouseEvent(this, event);
            break;
        }
    }

    public void removeClickListener(ClickListener listener) {
        if (fClickListeners != null)
            fClickListeners.remove(listener);
    }

    public void removeMouseListener(MouseListener listener) {
        if (fMouseListeners != null)
            fMouseListeners.remove(listener);
    }

    public void setHTML(String html) {
        DOM.setInnerHTML(anchorElem, html);
    }

    /**
     * Sets the history token referenced by this hyperlink. This is the
     * history token that will be passed to {@link History#newItem} when
     * this link is clicked.
     * 
     * @param target
     *            the new target history token
     */
    public void setTarget(String target) {
        this.target = target;
        DOM.setAttribute(anchorElem, "href", target);
    }

    public void setText(String text) {
        DOM.setInnerHTML(anchorElem, text);
    }

}
