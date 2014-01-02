/*
 * @(#)GoToFile.java   09/09/03
 *
 * Copyright (c) 2009 Mentor Graphics Corporation.  All Rights Reserved.
 * <p>
 * Recipients who obtain this code directly from Mentor Graphics use it solely
 * for internal purposes to serve as example Java or Java Script plugins.
 * This code may not be used in a commercial distribution. Recipients may
 * duplicate the code provided that all notices are fully reproduced with
 * and remain in the code. No part of this code may be modified, reproduced,
 * translated, used, distributed, disclosed or provided to third parties
 * without the prior written consent of Mentor Graphics, except as expressly
 * authorized above.
 * <p>
 * THE CODE IS MADE AVAILABLE "AS IS" WITHOUT WARRANTY OR SUPPORT OF ANY KIND.
 * MENTOR GRAPHICS OFFERS NO EXPRESS OR IMPLIED WARRANTIES AND SPECIFICALLY
 * DISCLAIMS ANY WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE,
 * OR WARRANTY OF NON-INFRINGEMENT. IN NO EVENT SHALL MENTOR GRAPHICS OR ITS
 * LICENSORS BE LIABLE FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING LOST PROFITS OR SAVINGS) WHETHER BASED ON CONTRACT, TORT
 * OR ANY OTHER LEGAL THEORY, EVEN IF MENTOR GRAPHICS OR ITS LICENSORS HAVE BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * <p>
 */



//~--- non-JDK imports --------------------------------------------------------

/**
 * CONTENTS: Open a file using the default handler from a property called FILE
 *
 * @author Mike Stamper
 * @date August 28, 2009
 */
import GUI.Find;
import GUI.WireAttributes;
import com.mentor.chs.api.IXWire;
import com.mentor.chs.plugin.IXApplicationContext;
import com.mentor.chs.plugin.IXAttributeSetter;
import com.mentor.chs.plugin.IXOutputWindow;
import com.mentor.chs.plugin.action.IXHarnessAction;
import com.mentor.chs.plugin.action.IXIntegratorAction;
import com.mentor.chs.plugin.action.IXLogicAction;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Desktop;


import java.util.Set;

import javax.swing.Icon;

public class Scope 
    {
    IXApplicationContext cntx;
    public double CSARange = 0;
    public String   MatValue = "";
    public double   CSAValue = 0;
    public Desktop  desktop;
    public String  Object;
    

    // this is a nice way to throw any text to the output window
    public void p(String s) {
        cntx.getOutputWindow().println(s);
    }

    /*
     * Code executed by the action.
     */
    public boolean execute(IXApplicationContext context) 
    {
        cntx = context;
        cntx.getOutputWindow().clear();
        
                // Open PopUp Dialog
        Find dialog = new  Find(new javax.swing.JFrame(), true);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
//        CSARange= dialog.CSARange;
//        MatValue= dialog.MatValue.toString();
//        Object = dialog.Object;
        IXOutputWindow outputWindow = cntx.getOutputWindow();
        outputWindow.println("Applied Object Type : "+Object);
//        
//        
//        Set<IXBuildList> buildlist= cntx.getCurrentProject().getBuildLists();
//        
       if(Object.toString().equals("Wire")||Object.toString().startsWith("Wire"))
            {
                boolean state= false;
                Set<IXWire> wireList = cntx.getCurrentDesign().getConnectivity().getWires();
                for (IXWire wire : wireList) 
                {
                     
                    // get a writer for the wire
                    final IXAttributeSetter attributeSetter = wire.getAttributeSetter();

                    if (attributeSetter == null) 
                        {
                        p("ERROR: not in read/write mode. Can't change: " + wire);
                        return false;
                        }
                     CSAValue =Double.parseDouble(wire.getAttribute("WireCSA"));
                     if(CSAValue == CSARange)
                        {
                        attributeSetter.addAttribute("WireMaterial", MatValue);
                        outputWindow.println(wire.getAttribute("Name")+":_"+"Material: has been change to"+ MatValue);
                        state =true;
                        }
                   
                 }
                 if(!state)
                    {
                    outputWindow.println("No Wire has been changed");
                    }
            }
        return true;
       }
    }
