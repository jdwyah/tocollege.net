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

        AbstractImagePrototype A();

        AbstractImagePrototype B();

        AbstractImagePrototype C();

        AbstractImagePrototype D();

        AbstractImagePrototype E();

        AbstractImagePrototype F();

        AbstractImagePrototype G();

        AbstractImagePrototype H();

        AbstractImagePrototype I();

        AbstractImagePrototype J();

        AbstractImagePrototype K();

        AbstractImagePrototype L();

        AbstractImagePrototype M();

        AbstractImagePrototype N();

        AbstractImagePrototype O();

        AbstractImagePrototype P();

        AbstractImagePrototype Q();

        AbstractImagePrototype R();

        AbstractImagePrototype S();

        AbstractImagePrototype T();

        AbstractImagePrototype U();

        AbstractImagePrototype V();

        AbstractImagePrototype W();

        AbstractImagePrototype X();

        AbstractImagePrototype Y();

        AbstractImagePrototype Z();

        AbstractImagePrototype sA();

        AbstractImagePrototype sB();

        AbstractImagePrototype sC();

        AbstractImagePrototype sD();

        AbstractImagePrototype sE();

        AbstractImagePrototype sF();

        AbstractImagePrototype sG();

        AbstractImagePrototype sH();

        AbstractImagePrototype sI();

        AbstractImagePrototype sJ();

        AbstractImagePrototype sK();

        AbstractImagePrototype sL();

        AbstractImagePrototype sM();

        AbstractImagePrototype sN();

        AbstractImagePrototype sO();

        AbstractImagePrototype sP();

        AbstractImagePrototype sQ();

        AbstractImagePrototype sR();

        AbstractImagePrototype sS();

        AbstractImagePrototype sT();

        AbstractImagePrototype sU();

        AbstractImagePrototype sV();

        AbstractImagePrototype sW();

        AbstractImagePrototype sX();

        AbstractImagePrototype sY();

        AbstractImagePrototype sZ();

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
