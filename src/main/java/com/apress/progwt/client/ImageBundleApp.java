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
package com.apress.progwt.client;

import com.apress.progwt.client.consts.ConstHolder;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class ImageBundleApp extends GWTApp {

    public ImageBundleApp(int pageID) {
        super(pageID);

        initConstants();
        
        String name = getParam("name");

        if(name == null){
            throw new RuntimeException("Image '"+name+"' not mapped.");
        }
        
        Image image = null;


        if(name.equals("checked")){
            image = ConstHolder.images.checked().createImage();    
        }else if(name.equals("unchecked")){
            image = ConstHolder.images.unchecked().createImage();    
        }else if(name.equals("pctStatus100")){
            image = ConstHolder.images.pctStatus100().createImage();    
        }else if(name.equals("pctStatus75")){
            image = ConstHolder.images.pctStatus75().createImage();    
        }else if(name.equals("pctStatus50")){
            image = ConstHolder.images.pctStatus50().createImage();    
        }else if(name.equals("pctStatus25")){
            image = ConstHolder.images.pctStatus25().createImage();    
        }else if(name.equals("pctStatus0")){
            image = ConstHolder.images.pctStatus0().createImage();    
        }
        
        if(image == null){
            throw new RuntimeException("Image '"+name+"' not mapped.");
        }
        
        show(image);
        
        RootPanel.get(getPreLoadID()).setVisible(false);
    }

}
