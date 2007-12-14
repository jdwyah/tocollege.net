package com.apress.progwt.client.consts;

import com.apress.progwt.client.Interactive;
import com.google.gwt.core.client.GWT;

public class ConstHolder {

    public static Images images;

    public static String getImgLoc(String img_postfix) {
        if (GWT.isScript()) {
            return Interactive.getRelativeURL("img/" + img_postfix);
        } else {
            return "file://C:/workspace/RealHippo/src/main/webapp/img/"
                    + img_postfix;
        }

    }

}
