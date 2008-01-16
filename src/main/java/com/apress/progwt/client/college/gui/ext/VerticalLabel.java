package com.apress.progwt.client.college.gui.ext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.VerticalPanel;

public class VerticalLabel extends Composite {
    public interface LetterImages extends ImageBundle {

        /**
         * @gwt.resource A.png
         */
        AbstractImagePrototype A();

        /**
         * @gwt.resource B.png
         */
        AbstractImagePrototype B();

        /**
         * @gwt.resource C.png
         */
        AbstractImagePrototype C();

        /**
         * @gwt.resource D.png
         */
        AbstractImagePrototype D();

        /**
         * @gwt.resource E.png
         */
        AbstractImagePrototype E();

        /**
         * @gwt.resource F.png
         */
        AbstractImagePrototype F();

        /**
         * @gwt.resource G.png
         */
        AbstractImagePrototype G();

        /**
         * @gwt.resource H.png
         */
        AbstractImagePrototype H();

        /**
         * @gwt.resource I.png
         */
        AbstractImagePrototype I();

        /**
         * @gwt.resource J.png
         */
        AbstractImagePrototype J();

        /**
         * @gwt.resource K.png
         */
        AbstractImagePrototype K();

        /**
         * @gwt.resource L.png
         */
        AbstractImagePrototype L();

        /**
         * @gwt.resource M.png
         */
        AbstractImagePrototype M();

        /**
         * @gwt.resource N.png
         */
        AbstractImagePrototype N();

        /**
         * @gwt.resource O.png
         */
        AbstractImagePrototype O();

        /**
         * @gwt.resource P.png
         */
        AbstractImagePrototype P();

        /**
         * @gwt.resource Q.png
         */
        AbstractImagePrototype Q();

        /**
         * @gwt.resource R.png
         */
        AbstractImagePrototype R();

        /**
         * @gwt.resource S.png
         */
        AbstractImagePrototype S();

        /**
         * @gwt.resource T.png
         */
        AbstractImagePrototype T();

        /**
         * @gwt.resource U.png
         */
        AbstractImagePrototype U();

        /**
         * @gwt.resource V.png
         */
        AbstractImagePrototype V();

        /**
         * @gwt.resource W.png
         */
        AbstractImagePrototype W();

        /**
         * @gwt.resource X.png
         */
        AbstractImagePrototype X();

        /**
         * @gwt.resource Y.png
         */
        AbstractImagePrototype Y();

        /**
         * @gwt.resource Z.png
         */
        AbstractImagePrototype Z();

        /**
         * @gwt.resource sA.png
         */
        AbstractImagePrototype sA();

        /**
         * @gwt.resource sB.png
         */
        AbstractImagePrototype sB();

        /**
         * @gwt.resource sC.png
         */
        AbstractImagePrototype sC();

        /**
         * @gwt.resource sD.png
         */
        AbstractImagePrototype sD();

        /**
         * @gwt.resource sE.png
         */
        AbstractImagePrototype sE();

        /**
         * @gwt.resource sF.png
         */
        AbstractImagePrototype sF();

        /**
         * @gwt.resource sG.png
         */
        AbstractImagePrototype sG();

        /**
         * @gwt.resource sH.png
         */
        AbstractImagePrototype sH();

        /**
         * @gwt.resource sI.png
         */
        AbstractImagePrototype sI();

        /**
         * @gwt.resource sJ.png
         */
        AbstractImagePrototype sJ();

        /**
         * @gwt.resource sK.png
         */
        AbstractImagePrototype sK();

        /**
         * @gwt.resource sL.png
         */
        AbstractImagePrototype sL();

        /**
         * @gwt.resource sM.png
         */
        AbstractImagePrototype sM();

        /**
         * @gwt.resource sN.png
         */
        AbstractImagePrototype sN();

        /**
         * @gwt.resource sO.png
         */
        AbstractImagePrototype sO();

        /**
         * @gwt.resource sP.png
         */
        AbstractImagePrototype sP();

        /**
         * @gwt.resource sQ.png
         */
        AbstractImagePrototype sQ();

        /**
         * @gwt.resource sR.png
         */
        AbstractImagePrototype sR();

        /**
         * @gwt.resource sS.png
         */
        AbstractImagePrototype sS();

        /**
         * @gwt.resource sT.png
         */
        AbstractImagePrototype sT();

        /**
         * @gwt.resource sU.png
         */
        AbstractImagePrototype sU();

        /**
         * @gwt.resource sV.png
         */
        AbstractImagePrototype sV();

        /**
         * @gwt.resource sW.png
         */
        AbstractImagePrototype sW();

        /**
         * @gwt.resource sX.png
         */
        AbstractImagePrototype sX();

        /**
         * @gwt.resource sY.png
         */
        AbstractImagePrototype sY();

        /**
         * @gwt.resource Z.png
         */
        AbstractImagePrototype sZ();

        /**
         * @gwt.resource SPACE.png
         */
        AbstractImagePrototype SPACE();
    }

    private VerticalPanel mainPanel;
    private static final LetterImages images = (LetterImages) GWT
            .create(LetterImages.class);

    public VerticalLabel(String text) {

        mainPanel = new VerticalPanel();
        setText(text);

        initWidget(mainPanel);
    }

    private void setText(String text) {
        mainPanel.clear();

        for (int i = text.length() - 1; i >= 0; i--) {
            char c = text.charAt(i);
            mainPanel.add(getImage(Character.toUpperCase(c)));
        }
    }

    private Image getImage(char c) {
        switch (c) {
        case 'A':
            return images.sA().createImage();
        case 'B':
            return images.sB().createImage();
        case 'C':
            return images.sC().createImage();
        case 'D':
            return images.sD().createImage();
        case 'E':
            return images.sE().createImage();
        case 'F':
            return images.sF().createImage();
        case 'G':
            return images.sG().createImage();
        case 'H':
            return images.sH().createImage();
        case 'I':
            return images.sI().createImage();
        case 'J':
            return images.sJ().createImage();
        case 'K':
            return images.sK().createImage();
        case 'L':
            return images.sL().createImage();
        case 'M':
            return images.sM().createImage();
        case 'N':
            return images.sN().createImage();
        case 'O':
            return images.sO().createImage();
        case 'P':
            return images.sP().createImage();
        case 'Q':
            return images.sQ().createImage();
        case 'R':
            return images.sR().createImage();
        case 'S':
            return images.sS().createImage();
        case 'T':
            return images.sT().createImage();
        case 'U':
            return images.sU().createImage();
        case 'V':
            return images.sV().createImage();
        case 'W':
            return images.sW().createImage();
        case 'X':
            return images.sX().createImage();
        case 'Y':
            return images.sY().createImage();
        case 'Z':
            return images.sZ().createImage();
        case ' ':
            return images.SPACE().createImage();
        }
        throw new UnsupportedOperationException("Unmapped Character " + c);
    }
}
