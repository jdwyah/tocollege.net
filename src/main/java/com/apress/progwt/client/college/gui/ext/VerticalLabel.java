package com.apress.progwt.client.college.gui.ext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;

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

    private FlowPanel mainPanel;
    private static final LetterImages images = (LetterImages) GWT
            .create(LetterImages.class);

    public VerticalLabel(String text) {

        mainPanel = new FlowPanel();
        mainPanel.setStylePrimaryName("vertical-label");
        setText(text);

        initWidget(mainPanel);
    }

    private void setText(String text) {
        mainPanel.clear();

        for (int i = text.length() - 1; i >= 0; i--) {
            char c = text.charAt(i);
            mainPanel.add(getImage(c));
        }
    }

    private Image getImage(char c) {
        switch (c) {
        case 'A':
            return images.A().createImage();
        case 'B':
            return images.B().createImage();
        case 'C':
            return images.C().createImage();
        case 'D':
            return images.D().createImage();
        case 'E':
            return images.E().createImage();
        case 'F':
            return images.F().createImage();
        case 'G':
            return images.G().createImage();
        case 'H':
            return images.H().createImage();
        case 'I':
            return images.I().createImage();
        case 'J':
            return images.J().createImage();
        case 'K':
            return images.K().createImage();
        case 'L':
            return images.L().createImage();
        case 'M':
            return images.M().createImage();
        case 'N':
            return images.N().createImage();
        case 'O':
            return images.O().createImage();
        case 'P':
            return images.P().createImage();
        case 'Q':
            return images.Q().createImage();
        case 'R':
            return images.R().createImage();
        case 'S':
            return images.S().createImage();
        case 'T':
            return images.T().createImage();
        case 'U':
            return images.U().createImage();
        case 'V':
            return images.V().createImage();
        case 'W':
            return images.W().createImage();
        case 'X':
            return images.X().createImage();
        case 'Y':
            return images.Y().createImage();
        case 'Z':
            return images.Z().createImage();
        case 'a':
            return images.sA().createImage();
        case 'b':
            return images.sB().createImage();
        case 'c':
            return images.sC().createImage();
        case 'd':
            return images.sD().createImage();
        case 'e':
            return images.sE().createImage();
        case 'f':
            return images.sF().createImage();
        case 'g':
            return images.sG().createImage();
        case 'h':
            return images.sH().createImage();
        case 'i':
            return images.sI().createImage();
        case 'j':
            return images.sJ().createImage();
        case 'k':
            return images.sK().createImage();
        case 'l':
            return images.sL().createImage();
        case 'm':
            return images.sM().createImage();
        case 'n':
            return images.sN().createImage();
        case 'o':
            return images.sO().createImage();
        case 'p':
            return images.sP().createImage();
        case 'q':
            return images.sQ().createImage();
        case 'r':
            return images.sR().createImage();
        case 's':
            return images.sS().createImage();
        case 't':
            return images.sT().createImage();
        case 'u':
            return images.sU().createImage();
        case 'v':
            return images.sV().createImage();
        case 'w':
            return images.sW().createImage();
        case 'x':
            return images.sX().createImage();
        case 'y':
            return images.sY().createImage();
        case 'z':
            return images.sZ().createImage();
        case ' ':
            return images.SPACE().createImage();
        }
        throw new UnsupportedOperationException("Unmapped Character " + c);
    }
}
