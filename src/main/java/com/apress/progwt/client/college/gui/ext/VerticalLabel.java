/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.client.college.gui.ext;

import java.util.HashMap;

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
         * @gwt.resource sZ.png
         */
        AbstractImagePrototype sZ();

        /**
         * @gwt.resource SPACE.png
         */
        AbstractImagePrototype SPACE();
    }

    private FlowPanel mainPanel;
    private static HashMap<Character, AbstractImagePrototype> allImages;
    private static final LetterImages images = (LetterImages) GWT
            .create(LetterImages.class);

    public VerticalLabel(String text) {

        if (allImages == null) {
            createMap();
        }
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
        try {
            return allImages.get(new Character(c)).createImage();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unmapped Character "
                    + c);
        }
    }

    private void createMap() {

        allImages = new HashMap<Character, AbstractImagePrototype>();

        allImages.put('A', images.A());
        allImages.put('B', images.B());
        allImages.put('C', images.C());
        allImages.put('D', images.D());
        allImages.put('E', images.E());
        allImages.put('F', images.F());
        allImages.put('G', images.G());
        allImages.put('H', images.H());
        allImages.put('I', images.I());
        allImages.put('J', images.J());
        allImages.put('K', images.K());
        allImages.put('L', images.L());
        allImages.put('M', images.M());
        allImages.put('N', images.N());
        allImages.put('O', images.O());
        allImages.put('P', images.P());
        allImages.put('Q', images.Q());
        allImages.put('R', images.R());
        allImages.put('S', images.S());
        allImages.put('T', images.T());
        allImages.put('U', images.U());
        allImages.put('V', images.V());
        allImages.put('W', images.W());
        allImages.put('X', images.X());
        allImages.put('Y', images.Y());
        allImages.put('Z', images.Z());
        allImages.put('a', images.sA());
        allImages.put('b', images.sB());
        allImages.put('c', images.sC());
        allImages.put('d', images.sD());
        allImages.put('e', images.sE());
        allImages.put('f', images.sF());
        allImages.put('g', images.sG());
        allImages.put('h', images.sH());
        allImages.put('i', images.sI());
        allImages.put('j', images.sJ());
        allImages.put('k', images.sK());
        allImages.put('l', images.sL());
        allImages.put('m', images.sM());
        allImages.put('n', images.sN());
        allImages.put('o', images.sO());
        allImages.put('p', images.sP());
        allImages.put('q', images.sQ());
        allImages.put('r', images.sR());
        allImages.put('s', images.sS());
        allImages.put('t', images.sT());
        allImages.put('u', images.sU());
        allImages.put('v', images.sV());
        allImages.put('w', images.sW());
        allImages.put('x', images.sX());
        allImages.put('y', images.sY());
        allImages.put('z', images.sZ());
        allImages.put(' ', images.SPACE());

    }
}
