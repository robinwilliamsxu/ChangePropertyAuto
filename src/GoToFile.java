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
import GUI.PopMe;
import com.mentor.chs.api.IXAbstractPin;
import com.mentor.chs.api.IXCavity;
import com.mentor.chs.api.IXConnector;

import com.mentor.chs.api.IXDesign;
import com.mentor.chs.api.IXDevice;
import com.mentor.chs.api.IXNet;
import com.mentor.chs.api.IXObject;
import com.mentor.chs.api.IXValue;
import com.mentor.chs.api.IXWire;
import com.mentor.chs.plugin.IXApplicationContext;
import com.mentor.chs.plugin.IXAttributeSetter;
import com.mentor.chs.plugin.IXOutputWindow;
import com.mentor.chs.plugin.action.IXHarnessAction;
import com.mentor.chs.plugin.action.IXIntegratorAction;
import com.mentor.chs.plugin.action.IXLogicAction;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Desktop;

import java.io.File;

import java.net.URI;
import java.util.Iterator;

import java.util.Set;

import javax.swing.Icon;

public class GoToFile implements IXLogicAction, IXIntegratorAction, IXHarnessAction
    {
    IXApplicationContext cntx;
    public String   OldProperty = "";
    public String   NewProperty = "";
    public String   PropertyValue = "";
    public Desktop  desktop;
    public String  Object;
    

    // this is a nice way to throw any text to the output window
    public void p(String s) {
        cntx.getOutputWindow().println(s);
    }

    /*
     * Code executed by the action.
     */
    public boolean execute(IXApplicationContext context) {
        cntx = context;
        cntx.getOutputWindow().clear();
        
                // Open PopUp Dialog
        PopMe dialog = new PopMe(new javax.swing.JFrame(), true);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        OldProperty = dialog.retValue.toString();
        NewProperty= dialog.newValue.toString();
        Object = dialog.Object;
        IXOutputWindow outputWindow = cntx.getOutputWindow();
        outputWindow.println("Applied Object Type : "+Object);
        
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
                    PropertyValue = wire.getProperty(OldProperty);
                    if(wire.getProperty(OldProperty)!=null)
                        {
                            attributeSetter.removeProperty(OldProperty);
                            attributeSetter.addProperty(NewProperty, PropertyValue);
                            outputWindow.println(wire.getAttribute("Name")+":_"+"Property:"+OldProperty+": has been removed"+"_Property:"+NewProperty+": has been added");
                            state=true;
                        }   
                   
                 }
                 if(!state)
                    {
                    outputWindow.println("No Wire has the property:"+OldProperty);
                    }
            }
       else if(Object.toString().equals("Device"))
            {
             boolean state= false;
             Set<IXDevice> DeviceList = cntx.getCurrentDesign().getConnectivity().getDevices();
                     for (IXDevice DEV : DeviceList) 
                     {

                         // get a writer for the wire
                         final IXAttributeSetter attributeSetter = DEV.getAttributeSetter();

                         if (attributeSetter == null) 
                             {
                             p("ERROR: not in read/write mode. Can't change: " + DEV);
                             return false;
                             }
                         PropertyValue = DEV.getProperty(OldProperty);
                         if(DEV.getProperty(OldProperty)!=null)
                             {
                                 attributeSetter.removeProperty(OldProperty);
                                 attributeSetter.addProperty(NewProperty, PropertyValue);
                                 outputWindow.println(DEV.getAttribute("Name")+":_"+"Property:"+OldProperty+": has been removed"+"_Property:"+NewProperty+": has been added");
                                 state=true;
                             }
                      }
                     if(!state)
                    {
                        outputWindow.println("No Device has the property:"+OldProperty);
                    }
            }
       else if(Object.toString().equals("Connector"))
       {
            boolean state= false;
           Set<IXConnector> wireList = cntx.getCurrentDesign().getConnectivity().getConnectors();
                for (IXConnector wire : wireList) 
                {

                    // get a writer for the wire
                    final IXAttributeSetter attributeSetter = wire.getAttributeSetter();

                    if (attributeSetter == null) 
                        {
                        p("ERROR: not in read/write mode. Can't change: " + wire);
                        return false;
                        }
                    PropertyValue = wire.getProperty(OldProperty);
                    if(wire.getProperty(OldProperty)!=null)
                        {
                            attributeSetter.removeProperty(OldProperty);
                            attributeSetter.addProperty(NewProperty, PropertyValue);
                            outputWindow.println(wire.getAttribute("Name")+":_"+"Property:"+OldProperty+": has been removed"+"_Property:"+NewProperty+": has been added");
                             state=true;
                        }
                 }
                if(!state)
                    {
                    outputWindow.println("No Connector has the property:"+OldProperty);
                    }
       
       }
        else if(Object.toString().equals("Net")||Object.toString().startsWith("Net"))
       {
            boolean state= false;
           Set<IXNet> wireList = cntx.getCurrentDesign().getConnectivity().getNets();
                for (IXNet wire : wireList) 
                {

                    // get a writer for the wire
                    final IXAttributeSetter attributeSetter = wire.getAttributeSetter();

                    if (attributeSetter == null) 
                        {
                        p("ERROR: not in read/write mode. Can't change: " + wire);
                        return false;
                        }
                    PropertyValue = wire.getProperty(OldProperty);
                    if(wire.getProperty(OldProperty)!=null)
                        {
                            attributeSetter.removeProperty(OldProperty);
                            attributeSetter.addProperty(NewProperty, PropertyValue);
                            outputWindow.println(wire.getAttribute("Name")+":_"+"Property:"+OldProperty+": has been removed"+"_Property:"+NewProperty+": has been added");
                             state=true;
                        }
                 }
                if(!state)
                    {
                    outputWindow.println("No Net has the property:"+OldProperty);
                    }
       
       }
        else if(Object.toString().equals("ConnectorPin")||Object.toString().startsWith("ConnectorPin"))
       {
            boolean state= false;
           outputWindow.println("ss");
           Set<IXConnector> connList = cntx.getCurrentDesign().getConnectivity().getConnectors();
                for (IXConnector conn : connList) 
                {
                    Iterator i$;
                    for(i$ = conn.getPins().iterator(); i$.hasNext();)
                    {
                            IXAbstractPin pin = (IXAbstractPin) i$.next();
                            
                            IXCavity cav = (IXCavity)pin;
                            final IXAttributeSetter attributeSetter = cav.getAttributeSetter();

                            if (attributeSetter == null) 
                                {
                                   p("ERROR: not in read/write mode. Can't change: " + cav);
                                   return false;
                                }
                            PropertyValue = cav.getProperty(OldProperty);
                            if(cav.getProperty(OldProperty)!=null)
                             {
                                 attributeSetter.removeProperty(OldProperty);
                                 attributeSetter.addProperty(NewProperty, PropertyValue);
                                 outputWindow.println(cav.getAttribute("Name")+":_"+"Property:"+OldProperty+": has been removed"+"_Property:"+NewProperty+": has been added");
                                  state=true;
                             }  
                    }
                                       
                 }
                if(!state)
                    {
                    outputWindow.println("No Connector Pin has the property:"+OldProperty);
                    }
       
       }
       else if(Object.toString().equals("DevicePin"))
       {
            boolean state= false;
           Set<IXDevice> DevList = cntx.getCurrentDesign().getConnectivity().getDevices();
                for (IXDevice DEV : DevList) 
                {
                    Iterator i$;
                    for(i$ = DEV.getPins().iterator(); i$.hasNext();)
                    {
                            IXAbstractPin pin =  (IXAbstractPin) i$.next();
                            final IXAttributeSetter attributeSetter =pin.getAttributeSetter();

                            if (attributeSetter == null) 
                                {
                                   p("ERROR: not in read/write mode. Can't change: " + pin);
                                   return false;
                                }
                            PropertyValue = pin.getProperty(OldProperty);
                            if(pin.getProperty(OldProperty)!=null)
                             {
                                 attributeSetter.removeProperty(OldProperty);
                                 attributeSetter.addProperty(NewProperty, PropertyValue);
                                 outputWindow.println(pin.getAttribute("Name")+":_"+"Property:"+OldProperty+": has been removed"+"_Property:"+NewProperty+": has been added");
                                  state=true;
                             }  
                    }
                                       
                 }
                if(!state)
                    {
                    outputWindow.println("No Device Pin has the property:"+OldProperty);
                    }
       
       }
       else
       {
           outputWindow.println("Not support object type yet;Contact Mentor Graphics"+"\n"+"nothing changed");
       }
       return true;
    }

    /**
     * This action is available in both the context menu and the main menu.
     *
     * @return An array with both Content and Main menu triggers
     */
    public Trigger[] getTriggers() {
        return new Trigger[]{Trigger.ContextMenu, Trigger.MainMenu};
    }

    /**
     * No icon supplied.
     *
     * @return null
     */
    public Icon getSmallIcon() {
        return null;
    }

    /**
     * No mnemonic supplied.
     *
     * @return null
     */
    public Integer getMnemonicKey() {
        return null;
    }

    /**
     * @return The long description
     */
    public String getLongDescription() {
        return getDescription();
    }

    /**
     * This action's purpose is to change data.
     *
     * @return false
     */
    public boolean isReadOnly() {
        return false;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return "[ Mentor ] Change Property";
    }

    /**
     * @return The name
     */
    public String getName() {
        return "[ Mentor ] Change Property";
    }

    /**
     * @return The version string
     */
    public String getVersion() 
    {
        return "1.0";
    }

    /**
     * This action always says it is available, assuming that any object that
     * does not have a library part will be ignored.
     *
     * @param context The application context
     * @return true
     */
    public boolean isAvailable(IXApplicationContext context) {
        return true;
    }
    }
