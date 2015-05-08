/*
 *************************************************************************
Copyright (c) 2011-2013:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy

See http://www.infn.it and and http://www.consorzio-cometa.it for details on
the copyright holders.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author <a href="mailto:giuseppe.larocca@ct.infn.it">Giuseppe La Rocca</a>
 ***************************************************************************
 */
package it.infn.ct.wrf;

// import liferay libraries
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

// import DataEngine libraries
import com.liferay.portal.util.PortalUtil;
import it.infn.ct.GridEngine.InformationSystem.BDII;
import it.infn.ct.GridEngine.Job.*;

// import generic Java libraries
import it.infn.ct.GridEngine.UsersTracking.UsersTrackingDBInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URI;

// import portlet libraries
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

// Importing Apache libraries
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Wrf extends GenericPortlet {

    private static Log log = LogFactory.getLog(Wrf.class);

    @Override
    protected void doEdit(RenderRequest request,
            RenderResponse response)
            throws PortletException, IOException
    {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");
        
        // Get the INFRASTRUCTURE from the portlet preferences for the DIT VO
        String dit_wrf_INFRASTRUCTURE = portletPreferences.getValue("dit_wrf_INFRASTRUCTURE", "N/A");
        // Get the login credential from the portlet preferences for the DIT Infrastrcture
        String dit_wrf_LOGIN = portletPreferences.getValue("dit_wrf_LOGIN", "N/A");
        // Get the passwrod credential from the portlet preferences for the DIT Infrastructure
        String dit_wrf_PASSWD = portletPreferences.getValue("dit_wrf_PASSWD", "N/A");
        // Get the cluster(s) hostname from the portlet preferences for the DIT Infrastructure
        String[] dit_wrf_WMS = portletPreferences.getValues("dit_wrf_WMS", new String[5]);
        // Get the ETOKENSERVER from the portlet preferences for the DIT Infrastructure
        String dit_wrf_ETOKENSERVER = portletPreferences.getValue("dit_wrf_ETOKENSERVER", "N/A");
        // Get the MYPROXYSERVER from the portlet preferences for the DIT Infrastructure
        String dit_wrf_MYPROXYSERVER = portletPreferences.getValue("dit_wrf_MYPROXYSERVER", "N/A");
        // Get the PORT from the portlet preferences for the DIT Infrastructure
        String dit_wrf_PORT = portletPreferences.getValue("dit_wrf_PORT", "N/A");
        // Get the ROBOTID from the portlet preferences for the DIT Infrastructure
        String dit_wrf_ROBOTID = portletPreferences.getValue("dit_wrf_ROBOTID", "N/A");
        // Get the ROLE from the portlet preferences for the DIT Infrastructure
        String dit_wrf_ROLE = portletPreferences.getValue("dit_wrf_ROLE", "N/A");
        // Get the RENEWAL from the portlet preferences for the DIT Infrastructure
        String dit_wrf_RENEWAL = portletPreferences.getValue("dit_wrf_RENEWAL", "checked");
        // Get the DISABLEVOMS from the portlet preferences for the DIT Infrastructure
        String dit_wrf_DISABLEVOMS = portletPreferences.getValue("dit_wrf_DISABLEVOMS", "unchecked");
        
        // Get the INFRASTRUCTURE from the portlet preferences for the GARUDA VO
        String garuda_wrf_INFRASTRUCTURE = portletPreferences.getValue("garuda_wrf_INFRASTRUCTURE", "N/A");
        // Get the VONAME from the portlet preferences for the GARUDA VO
        String garuda_wrf_VONAME = portletPreferences.getValue("garuda_wrf_VONAME", "N/A");
        // Get the TOPBDII from the portlet preferences for the GARUDA VO
        String garuda_wrf_TOPBDII = portletPreferences.getValue("garuda_wrf_TOPBDII", "N/A");
        // Get the WMS from the portlet preferences for the GARUDA VO
        String[] garuda_wrf_WMS = portletPreferences.getValues("garuda_wrf_WMS", new String[5]);
        // Get the ETOKENSERVER from the portlet preferences for the GARUDA VO
        String garuda_wrf_ETOKENSERVER = portletPreferences.getValue("garuda_wrf_ETOKENSERVER", "N/A");
        // Get the MYPROXYSERVER from the portlet preferences for the GARUDA VO
        String garuda_wrf_MYPROXYSERVER = portletPreferences.getValue("garuda_wrf_MYPROXYSERVER", "N/A");
        // Get the PORT from the portlet preferences for the GARUDA VO
        String garuda_wrf_PORT = portletPreferences.getValue("garuda_wrf_PORT", "N/A");
        // Get the ROBOTID from the portlet preferences for the GARUDA VO
        String garuda_wrf_ROBOTID = portletPreferences.getValue("garuda_wrf_ROBOTID", "N/A");
        // Get the ROLE from the portlet preferences for the GARUDA VO
        String garuda_wrf_ROLE = portletPreferences.getValue("garuda_wrf_ROLE", "N/A");
        // Get the RENEWAL from the portlet preferences for the GARUDA VO
        String garuda_wrf_RENEWAL = portletPreferences.getValue("garuda_wrf_RENEWAL", "checked");
        // Get the DISABLEVOMS from the portlet preferences for the GARUDA VO
        String garuda_wrf_DISABLEVOMS = portletPreferences.getValue("garuda_wrf_DISABLEVOMS", "unchecked");

        // Get the INFRASTRUCTURE from the portlet preferences for the CHAIN VO
        String chain_wrf_INFRASTRUCTURE = portletPreferences.getValue("chain_wrf_INFRASTRUCTURE", "N/A");
        // Get the VONAME from the portlet preferences for the CHAIN VO
        String chain_wrf_VONAME = portletPreferences.getValue("chain_wrf_VONAME", "N/A");
        // Get the TOPPBDII from the portlet preferences for the CHAIN VO
        String chain_wrf_TOPBDII = portletPreferences.getValue("chain_wrf_TOPBDII", "N/A");
        // Get the WMS from the portlet preferences for the CHAIN VO
        String[] chain_wrf_WMS = portletPreferences.getValues("chain_wrf_WMS", new String[10]);
        // Get the ETOKENSERVER from the portlet preferences for the CHAIN VO
        String chain_wrf_ETOKENSERVER = portletPreferences.getValue("chain_wrf_ETOKENSERVER", "N/A");
        // Get the MYPROXYSERVER from the portlet preferences for the CHAIN VO
        String chain_wrf_MYPROXYSERVER = portletPreferences.getValue("chain_wrf_MYPROXYSERVER", "N/A");
        // Get the PORT from the portlet preferences for the CHAIN VO
        String chain_wrf_PORT = portletPreferences.getValue("chain_wrf_PORT", "N/A");
        // Get the ROBOTID from the portlet preferences for the CHAIN VO
        String chain_wrf_ROBOTID = portletPreferences.getValue("chain_wrf_ROBOTID", "N/A");
        // Get the ROLE from the portlet preferences for the CHAIN VO
        String chain_wrf_ROLE = portletPreferences.getValue("chain_wrf_ROLE", "N/A");
        // Get the RENEWAL from the portlet preferences for the CHAIN VO
        String chain_wrf_RENEWAL = portletPreferences.getValue("chain_wrf_RENEWAL", "checked");
        // Get the DISABLEVOMS from the portlet preferences for the CHAIN VO
        String chain_wrf_DISABLEVOMS = portletPreferences.getValue("chain_wrf_DISABLEVOMS", "unchecked");
        
        // Get the INFRASTRUCTURE from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_INFRASTRUCTURE = portletPreferences.getValue("fedcloud_wrf_INFRASTRUCTURE", "N/A");
        // Get the VONAME from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_VONAME = portletPreferences.getValue("fedcloud_wrf_VONAME", "N/A");
        // Get the TOPPBDII from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_TOPBDII = portletPreferences.getValue("fedcloud_wrf_TOPBDII", "N/A");
        // Get the WMS from the portlet preferences for the FEDCLOUD VO
        String[] fedcloud_wrf_WMS = portletPreferences.getValues("fedcloud_wrf_WMS", new String[10]);
        // Get the ETOKENSERVER from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_ETOKENSERVER = portletPreferences.getValue("fedcloud_wrf_ETOKENSERVER", "N/A");
        // Get the MYPROXYSERVER from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_MYPROXYSERVER = portletPreferences.getValue("fedcloud_wrf_MYPROXYSERVER", "N/A");
        // Get the PORT from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_PORT = portletPreferences.getValue("fedcloud_wrf_PORT", "N/A");
        // Get the ROBOTID from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_ROBOTID = portletPreferences.getValue("fedcloud_wrf_ROBOTID", "N/A");
        // Get the ROLE from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_ROLE = portletPreferences.getValue("fedcloud_wrf_ROLE", "N/A");
        // Get the RENEWAL from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_RENEWAL = portletPreferences.getValue("fedcloud_wrf_RENEWAL", "checked");
        // Get the DISABLEVOMS from the portlet preferences for the FEDCLOUD VO
        String fedcloud_wrf_DISABLEVOMS = portletPreferences.getValue("fedcloud_wrf_DISABLEVOMS", "unchecked");

        // Get the INFRASTRUCTURE from the portlet preferences for the EUMED VO
        String eumed_wrf_INFRASTRUCTURE = portletPreferences.getValue("eumed_wrf_INFRASTRUCTURE", "N/A");
        // Get the VONAME from the portlet preferences for the EUMED VO
        String eumed_wrf_VONAME = portletPreferences.getValue("eumed_wrf_VONAME", "N/A");
        // Get the TOPPBDII from the portlet preferences for the EUMED VO
        String eumed_wrf_TOPBDII = portletPreferences.getValue("eumed_wrf_TOPBDII", "N/A");
        // Get the WMS from the portlet preferences for the EUMED VO
        String[] eumed_wrf_WMS = portletPreferences.getValues("eumed_wrf_WMS", new String[5]);
        // Get the ETOKENSERVER from the portlet preferences for the EUMED VO
        String eumed_wrf_ETOKENSERVER = portletPreferences.getValue("eumed_wrf_ETOKENSERVER", "N/A");
        // Get the MYPROXYSERVER from the portlet preferences for the EUMED VO
        String eumed_wrf_MYPROXYSERVER = portletPreferences.getValue("eumed_wrf_MYPROXYSERVER", "N/A");
        // Get the PORT from the portlet preferences for the EUMED VO
        String eumed_wrf_PORT = portletPreferences.getValue("eumed_wrf_PORT", "N/A");
        // Get the ROBOTID from the portlet preferences for the EUMED VO
        String eumed_wrf_ROBOTID = portletPreferences.getValue("eumed_wrf_ROBOTID", "N/A");
        // Get the ROLE from the portlet preferences for the EUMED VO
        String eumed_wrf_ROLE = portletPreferences.getValue("eumed_wrf_ROLE", "N/A");
        // Get the RENEWAL from the portlet preferences for the EUMED VO
        String eumed_wrf_RENEWAL = portletPreferences.getValue("eumed_wrf_RENEWAL", "checked");
        // Get the ISABLEVOMS from the portlet preferences for the EUMED VO
        String eumed_wrf_DISABLEVOMS = portletPreferences.getValue("eumed_wrf_DISABLEVOMS", "unchecked");

        // Get the INFRASTRUCTURE from the portlet preferences for the GISELA VO
        String gisela_wrf_INFRASTRUCTURE = portletPreferences.getValue("gisela_wrf_INFRASTRUCTURE", "N/A");
        // Get the VONAME from the portlet preferences for the GISELA VO
        String gisela_wrf_VONAME = portletPreferences.getValue("gisela_wrf_VONAME", "N/A");
        // Get the TOPPBDII from the portlet preferences for the GISELA VO
        String gisela_wrf_TOPBDII = portletPreferences.getValue("gisela_wrf_TOPBDII", "N/A");
        // Get the WMS from the portlet preferences for the GISELA VO
        String[] gisela_wrf_WMS = portletPreferences.getValues("gisela_wrf_WMS", new String[5]);
        // Get the ETOKENSERVER from the portlet preferences for the GISELA VO
        String gisela_wrf_ETOKENSERVER = portletPreferences.getValue("gisela_wrf_ETOKENSERVER", "N/A");
        // Get the MYPROXYSERVER from the portlet preferences for the GISELA VO
        String gisela_wrf_MYPROXYSERVER = portletPreferences.getValue("gisela_wrf_MYPROXYSERVER", "N/A");
        // Get the PORT from the portlet preferences for the GISELA VO
        String gisela_wrf_PORT = portletPreferences.getValue("gisela_wrf_PORT", "N/A");
        // Get the ROBOTID from the portlet preferences for the GISELA VO
        String gisela_wrf_ROBOTID = portletPreferences.getValue("gisela_wrf_ROBOTID", "N/A");
        // Get the ROLE from the portlet preferences for the GISELA VO
        String gisela_wrf_ROLE = portletPreferences.getValue("gisela_wrf_ROLE", "N/A");
        // Get the RENEWAL from the portlet preferences for the GISELA VO
        String gisela_wrf_RENEWAL = portletPreferences.getValue("gisela_wrf_RENEWAL", "checked");
        // Get the DISABLEVOMS from the portlet preferences for the GISELA VO
        String gisela_wrf_DISABLEVOMS = portletPreferences.getValue("gisela_wrf_DISABLEVOMS", "unchecked");
        
        // Get the INFRASTRUCTURE from the portlet preferences for the SAGRID VO
        String sagrid_wrf_INFRASTRUCTURE = portletPreferences.getValue("sagrid_wrf_INFRASTRUCTURE", "N/A");
        // Get the VONAME from the portlet preferences for the SAGRID VO
        String sagrid_wrf_VONAME = portletPreferences.getValue("sagrid_wrf_VONAME", "N/A");
        // Get the TOPPBDII from the portlet preferences for the SAGRID VO
        String sagrid_wrf_TOPBDII = portletPreferences.getValue("sagrid_wrf_TOPBDII", "N/A");
        // Get the WMS from the portlet preferences for the SAGRID VO
        String[] sagrid_wrf_WMS = portletPreferences.getValues("sagrid_wrf_WMS", new String[5]);
        // Get the ETOKENSERVER from the portlet preferences for the SAGRID VO
        String sagrid_wrf_ETOKENSERVER = portletPreferences.getValue("sagrid_wrf_ETOKENSERVER", "N/A");
        // Get the MYPROXYSERVER from the portlet preferences for the SAGRID VO
        String sagrid_wrf_MYPROXYSERVER = portletPreferences.getValue("sagrid_wrf_MYPROXYSERVER", "N/A");
        // Get the PORT from the portlet preferences for the SAGRID VO
        String sagrid_wrf_PORT = portletPreferences.getValue("sagrid_wrf_PORT", "N/A");
        // Get the ROBOTID from the portlet preferences for the SAGRID VO
        String sagrid_wrf_ROBOTID = portletPreferences.getValue("sagrid_wrf_ROBOTID", "N/A");
        // Get the ROLE from the portlet preferences for the SAGRID VO
        String sagrid_wrf_ROLE = portletPreferences.getValue("sagrid_wrf_ROLE", "N/A");
        // Get the RENEWAL from the portlet preferences for the SAGRID VO
        String sagrid_wrf_RENEWAL = portletPreferences.getValue("sagrid_wrf_RENEWAL", "checked");
        // Get the DISABLEVOMS from the portlet preferences for the SAGRID VO
        String sagrid_wrf_DISABLEVOMS = portletPreferences.getValue("sagrid_wrf_DISABLEVOMS", "unchecked");

        // Get the APPID from the portlet preferences
        String wrf_APPID = portletPreferences.getValue("wrf_APPID", "N/A");
        // Get the LOG LEVEL from the portlet preferences
        String wrf_LOGLEVEL = portletPreferences.getValue("wrf_LOGLEVEL", "INFO");
        // Get the OUTPUT from the portlet preferences
        String wrf_OUTPUT_PATH = portletPreferences.getValue("wrf_OUTPUT_PATH", "/tmp");
        // Get the SOFTWARE from the portlet preferences
        String wrf_SOFTWARE = portletPreferences.getValue("wrf_SOFTWARE", "N/A");
        // Get the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Get the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Get the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Get the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Get the SENDER MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        // Get the list of enabled Infrastructures
        String[] infras = portletPreferences.getValues("wrf_ENABLEINFRASTRUCTURE", new String[7]);

        // Set the default portlet preferences
        request.setAttribute("chain_wrf_INFRASTRUCTURE", chain_wrf_INFRASTRUCTURE.trim());
        request.setAttribute("chain_wrf_VONAME", chain_wrf_VONAME.trim());
        request.setAttribute("chain_wrf_TOPBDII", chain_wrf_TOPBDII.trim());
        request.setAttribute("chain_wrf_WMS", chain_wrf_WMS);
        request.setAttribute("chain_wrf_ETOKENSERVER", chain_wrf_ETOKENSERVER.trim());
        request.setAttribute("chain_wrf_MYPROXYSERVER", chain_wrf_MYPROXYSERVER.trim());
        request.setAttribute("chain_wrf_PORT", chain_wrf_PORT.trim());
        request.setAttribute("chain_wrf_ROBOTID", chain_wrf_ROBOTID.trim());
        request.setAttribute("chain_wrf_ROLE", chain_wrf_ROLE.trim());
        request.setAttribute("chain_wrf_RENEWAL", chain_wrf_RENEWAL);
        request.setAttribute("chain_wrf_DISABLEVOMS", chain_wrf_DISABLEVOMS);
        
        request.setAttribute("garuda_wrf_INFRASTRUCTURE", garuda_wrf_INFRASTRUCTURE.trim());
        request.setAttribute("garuda_wrf_VONAME", garuda_wrf_VONAME.trim());
        request.setAttribute("garuda_wrf_TOPBDII", garuda_wrf_TOPBDII.trim());
        request.setAttribute("garuda_wrf_WMS", garuda_wrf_WMS);
        request.setAttribute("garuda_wrf_ETOKENSERVER", garuda_wrf_ETOKENSERVER.trim());
        request.setAttribute("garuda_wrf_MYPROXYSERVER", garuda_wrf_MYPROXYSERVER.trim());
        request.setAttribute("garuda_wrf_PORT", garuda_wrf_PORT.trim());
        request.setAttribute("garuda_wrf_ROBOTID", garuda_wrf_ROBOTID.trim());
        request.setAttribute("garuda_wrf_ROLE", garuda_wrf_ROLE.trim());
        request.setAttribute("garuda_wrf_RENEWAL", garuda_wrf_RENEWAL);
        request.setAttribute("garuda_wrf_DISABLEVOMS", garuda_wrf_DISABLEVOMS);
        
        request.setAttribute("dit_wrf_INFRASTRUCTURE", dit_wrf_INFRASTRUCTURE.trim());
        request.setAttribute("dit_wrf_LOGIN", dit_wrf_LOGIN.trim());
        request.setAttribute("dit_wrf_PASSWD", dit_wrf_PASSWD.trim());
        request.setAttribute("dit_wrf_WMS", dit_wrf_WMS);
        request.setAttribute("dit_wrf_ETOKENSERVER", dit_wrf_ETOKENSERVER.trim());
        request.setAttribute("dit_wrf_MYPROXYSERVER", dit_wrf_MYPROXYSERVER.trim());
        request.setAttribute("dit_wrf_PORT", dit_wrf_PORT.trim());
        request.setAttribute("dit_wrf_ROBOTID", dit_wrf_ROBOTID.trim());
        request.setAttribute("dit_wrf_ROLE", dit_wrf_ROLE.trim());
        request.setAttribute("dit_wrf_RENEWAL", dit_wrf_RENEWAL);
        request.setAttribute("dit_wrf_DISABLEVOMS", dit_wrf_DISABLEVOMS);
        
        request.setAttribute("fedcloud_wrf_INFRASTRUCTURE", fedcloud_wrf_INFRASTRUCTURE.trim());
        request.setAttribute("fedcloud_wrf_VONAME", fedcloud_wrf_VONAME.trim());
        request.setAttribute("fedcloud_wrf_TOPBDII", fedcloud_wrf_TOPBDII.trim());
        request.setAttribute("fedcloud_wrf_WMS", fedcloud_wrf_WMS);
        request.setAttribute("fedcloud_wrf_ETOKENSERVER", fedcloud_wrf_ETOKENSERVER.trim());
        request.setAttribute("fedcloud_wrf_MYPROXYSERVER", fedcloud_wrf_MYPROXYSERVER.trim());
        request.setAttribute("fedcloud_wrf_PORT", fedcloud_wrf_PORT.trim());
        request.setAttribute("fedcloud_wrf_ROBOTID", fedcloud_wrf_ROBOTID.trim());
        request.setAttribute("fedcloud_wrf_ROLE", fedcloud_wrf_ROLE.trim());
        request.setAttribute("fedcloud_wrf_RENEWAL", fedcloud_wrf_RENEWAL);
        request.setAttribute("fedcloud_wrf_DISABLEVOMS", fedcloud_wrf_DISABLEVOMS);

        request.setAttribute("eumed_wrf_INFRASTRUCTURE", eumed_wrf_INFRASTRUCTURE.trim());
        request.setAttribute("eumed_wrf_VONAME", eumed_wrf_VONAME.trim());
        request.setAttribute("eumed_wrf_TOPBDII", eumed_wrf_TOPBDII.trim());
        request.setAttribute("eumed_wrf_WMS", eumed_wrf_WMS);
        request.setAttribute("eumed_wrf_ETOKENSERVER", eumed_wrf_ETOKENSERVER.trim());
        request.setAttribute("eumed_wrf_MYPROXYSERVER", eumed_wrf_MYPROXYSERVER.trim());
        request.setAttribute("eumed_wrf_PORT", eumed_wrf_PORT.trim());
        request.setAttribute("eumed_wrf_ROBOTID", eumed_wrf_ROBOTID.trim());
        request.setAttribute("eumed_wrf_ROLE", eumed_wrf_ROLE.trim());
        request.setAttribute("eumed_wrf_RENEWAL", eumed_wrf_RENEWAL);
        request.setAttribute("eumed_wrf_DISABLEVOMS", eumed_wrf_DISABLEVOMS);                

        request.setAttribute("gisela_wrf_INFRASTRUCTURE", gisela_wrf_INFRASTRUCTURE.trim());
        request.setAttribute("gisela_wrf_VONAME", gisela_wrf_VONAME.trim());
        request.setAttribute("gisela_wrf_TOPBDII", gisela_wrf_TOPBDII.trim());
        request.setAttribute("gisela_wrf_WMS", gisela_wrf_WMS);
        request.setAttribute("gisela_wrf_ETOKENSERVER", gisela_wrf_ETOKENSERVER.trim());
        request.setAttribute("gisela_wrf_MYPROXYSERVER", gisela_wrf_MYPROXYSERVER.trim());
        request.setAttribute("gisela_wrf_PORT", gisela_wrf_PORT.trim());
        request.setAttribute("gisela_wrf_ROBOTID", gisela_wrf_ROBOTID.trim());
        request.setAttribute("gisela_wrf_ROLE", gisela_wrf_ROLE.trim());
        request.setAttribute("gisela_wrf_RENEWAL", gisela_wrf_RENEWAL);
        request.setAttribute("gisela_wrf_DISABLEVOMS", gisela_wrf_DISABLEVOMS);
        
        request.setAttribute("sagrid_wrf_INFRASTRUCTURE", sagrid_wrf_INFRASTRUCTURE.trim());
        request.setAttribute("sagrid_wrf_VONAME", sagrid_wrf_VONAME.trim());
        request.setAttribute("sagrid_wrf_TOPBDII", sagrid_wrf_TOPBDII.trim());
        request.setAttribute("sagrid_wrf_WMS", sagrid_wrf_WMS);
        request.setAttribute("sagrid_wrf_ETOKENSERVER", sagrid_wrf_ETOKENSERVER.trim());
        request.setAttribute("sagrid_wrf_MYPROXYSERVER", sagrid_wrf_MYPROXYSERVER.trim());
        request.setAttribute("sagrid_wrf_PORT", sagrid_wrf_PORT.trim());
        request.setAttribute("sagrid_wrf_ROBOTID", sagrid_wrf_ROBOTID.trim());
        request.setAttribute("sagrid_wrf_ROLE", sagrid_wrf_ROLE.trim());
        request.setAttribute("sagrid_wrf_RENEWAL", sagrid_wrf_RENEWAL);
        request.setAttribute("sagrid_wrf_DISABLEVOMS", sagrid_wrf_DISABLEVOMS);

        request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);
        request.setAttribute("wrf_APPID", wrf_APPID.trim());
        request.setAttribute("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
        request.setAttribute("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
        request.setAttribute("wrf_SOFTWARE", wrf_SOFTWARE.trim());
        request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
        request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
        request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
        request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
        request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());        

        if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
        log.info("\nStarting the EDIT mode...with this settings"
        + "\ndit_wrf_INFRASTRUCTURE: " + dit_wrf_INFRASTRUCTURE
        + "\ndit_wrf_LOGIN: " + dit_wrf_LOGIN
        + "\ndit_wrf_PASSWD: " + dit_wrf_PASSWD                    
        + "\ndit_wrf_ETOKENSERVER: " + dit_wrf_ETOKENSERVER
        + "\ndit_wrf_MYPROXYSERVER: " + dit_wrf_MYPROXYSERVER
        + "\ndit_wrf_PORT: " + dit_wrf_PORT
        + "\ndit_wrf_ROBOTID: " + dit_wrf_ROBOTID
        + "\ndit_wrf_ROLE: " + dit_wrf_ROLE
        + "\ndit_wrf_RENEWAL: " + dit_wrf_RENEWAL
        + "\ndit_wrf_DISABLEVOMS: " + dit_wrf_DISABLEVOMS
                
        + "\n\ngaruda_wrf_INFRASTRUCTURE: " + garuda_wrf_INFRASTRUCTURE
        + "\ngaruda_wrf_VONAME: " + garuda_wrf_VONAME
        + "\ngaruda_wrf_TOPBDII: " + garuda_wrf_TOPBDII                    
        + "\ngaruda_wrf_ETOKENSERVER: " + garuda_wrf_ETOKENSERVER
        + "\ngaruda_wrf_MYPROXYSERVER: " + garuda_wrf_MYPROXYSERVER
        + "\ngaruda_wrf_PORT: " + garuda_wrf_PORT
        + "\ngaruda_wrf_ROBOTID: " + garuda_wrf_ROBOTID
        + "\ngaruda_wrf_ROLE: " + garuda_wrf_ROLE
        + "\ngaruda_wrf_RENEWAL: " + garuda_wrf_RENEWAL
        + "\ngaruda_wrf_DISABLEVOMS: " + garuda_wrf_DISABLEVOMS 
                
        + "\n\nchain_wrf_INFRASTRUCTURE: " + chain_wrf_INFRASTRUCTURE
        + "\nchain_wrf_VONAME: " + chain_wrf_VONAME
        + "\nchain_wrf_TOPBDII: " + chain_wrf_TOPBDII                    
        + "\nchain_wrf_ETOKENSERVER: " + chain_wrf_ETOKENSERVER
        + "\nchain_wrf_MYPROXYSERVER: " + chain_wrf_MYPROXYSERVER
        + "\nchain_wrf_PORT: " + chain_wrf_PORT
        + "\nchain_wrf_ROBOTID: " + chain_wrf_ROBOTID
        + "\nchain_wrf_ROLE: " + chain_wrf_ROLE
        + "\nchain_wrf_RENEWAL: " + chain_wrf_RENEWAL
        + "\nchain_wrf_DISABLEVOMS: " + chain_wrf_DISABLEVOMS
                
        + "\n\nfedcloud_wrf_INFRASTRUCTURE: " + fedcloud_wrf_INFRASTRUCTURE
        + "\nfedcloud_wrf_VONAME: " + fedcloud_wrf_VONAME
        + "\nfedcloud_wrf_TOPBDII: " + fedcloud_wrf_TOPBDII                    
        + "\nfedcloud_wrf_ETOKENSERVER: " + fedcloud_wrf_ETOKENSERVER
        + "\nfedcloud_wrf_MYPROXYSERVER: " + fedcloud_wrf_MYPROXYSERVER
        + "\nfedcloud_wrf_PORT: " + fedcloud_wrf_PORT
        + "\nfedcloud_wrf_ROBOTID: " + fedcloud_wrf_ROBOTID
        + "\nfedcloud_wrf_ROLE: " + fedcloud_wrf_ROLE
        + "\nfedcloud_wrf_RENEWAL: " + fedcloud_wrf_RENEWAL
        + "\nfedcloud_wrf_DISABLEVOMS: " + fedcloud_wrf_DISABLEVOMS

        + "\n\neumed_wrf_INFRASTRUCTURE: " + eumed_wrf_INFRASTRUCTURE
        + "\neumed_wrf_VONAME: " + eumed_wrf_VONAME
        + "\neumed_wrf_TOPBDII: " + eumed_wrf_TOPBDII                    
        + "\neumed_wrf_ETOKENSERVER: " + eumed_wrf_ETOKENSERVER
        + "\neumed_wrf_MYPROXYSERVER: " + eumed_wrf_MYPROXYSERVER
        + "\neumed_wrf_PORT: " + eumed_wrf_PORT
        + "\neumed_wrf_ROBOTID: " + eumed_wrf_ROBOTID
        + "\neumed_wrf_ROLE: " + eumed_wrf_ROLE
        + "\neumed_wrf_RENEWAL: " + eumed_wrf_RENEWAL
        + "\neumed_wrf_DISABLEVOMS: " + eumed_wrf_DISABLEVOMS

        + "\n\ngisela_wrf_INFRASTRUCTURE: " + gisela_wrf_INFRASTRUCTURE
        + "\ngisela_wrf_VONAME: " + gisela_wrf_VONAME
        + "\ngisela_wrf_TOPBDII: " + gisela_wrf_TOPBDII                   
        + "\ngisela_wrf_ETOKENSERVER: " + gisela_wrf_ETOKENSERVER
        + "\ngisela_wrf_MYPROXYSERVER: " + gisela_wrf_MYPROXYSERVER
        + "\ngisela_wrf_PORT: " + gisela_wrf_PORT
        + "\ngisela_wrf_ROBOTID: " + gisela_wrf_ROBOTID
        + "\ngisela_wrf_ROLE: " + gisela_wrf_ROLE
        + "\ngisela_wrf_RENEWAL: " + gisela_wrf_RENEWAL
        + "\ngisela_wrf_DISABLEVOMS: " + gisela_wrf_DISABLEVOMS
                
        + "\n\nsagrid_wrf_INFRASTRUCTURE: " + sagrid_wrf_INFRASTRUCTURE
        + "\nsagrid_wrf_VONAME: " + sagrid_wrf_VONAME
        + "\nsagrid_wrf_TOPBDII: " + sagrid_wrf_TOPBDII                   
        + "\nsagrid_wrf_ETOKENSERVER: " + sagrid_wrf_ETOKENSERVER
        + "\nsagrid_wrf_MYPROXYSERVER: " + sagrid_wrf_MYPROXYSERVER
        + "\nsagrid_wrf_PORT: " + sagrid_wrf_PORT
        + "\nsagrid_wrf_ROBOTID: " + sagrid_wrf_ROBOTID
        + "\nsagrid_wrf_ROLE: " + sagrid_wrf_ROLE
        + "\nsagrid_wrf_RENEWAL: " + sagrid_wrf_RENEWAL
        + "\nsagrid_wrf_DISABLEVOMS: " + sagrid_wrf_DISABLEVOMS
                 
        + "\nwrf_APPID: " + wrf_APPID
        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
        + "\nSMTP Server: " + SMTP_HOST
        + "\nSender: " + SENDER_MAIL);
        }

        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/edit.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        //super.doHelp(request, response);

        response.setContentType("text/html");

        log.info("\nStarting the HELP mode...");
        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/help.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");

        //java.util.Enumeration listPreferences = portletPreferences.getNames();
        PortletRequestDispatcher dispatcher = null;
        
        String dit_wrf_PASSWD = "";
        String dit_wrf_LOGIN = "";
        String garuda_wrf_TOPBDII = "";
        String garuda_wrf_VONAME = "";
        String chain_wrf_TOPBDII = "";
        String chain_wrf_VONAME = "";
        String fedcloud_wrf_TOPBDII = "";
        String fedcloud_wrf_VONAME = "";
        String eumed_wrf_TOPBDII = "";
        String eumed_wrf_VONAME = "";
        String gisela_wrf_TOPBDII = "";
        String gisela_wrf_VONAME = "";
        String sagrid_wrf_TOPBDII = "";
        String sagrid_wrf_VONAME = "";
        
        String dit_wrf_ENABLEINFRASTRUCTURE="";
        String garuda_wrf_ENABLEINFRASTRUCTURE="";
        String chain_wrf_ENABLEINFRASTRUCTURE="";
        String fedcloud_wrf_ENABLEINFRASTRUCTURE="";
        String eumed_wrf_ENABLEINFRASTRUCTURE="";
        String gisela_wrf_ENABLEINFRASTRUCTURE="";
        String sagrid_wrf_ENABLEINFRASTRUCTURE="";
        String[] infras = new String[7];
                
        String[] wrf_INFRASTRUCTURES = 
                portletPreferences.getValues("wrf_ENABLEINFRASTRUCTURE", new String[7]);
        
        for (int i=0; i<wrf_INFRASTRUCTURES.length; i++) {            
            if (wrf_INFRASTRUCTURES[i]!=null && wrf_INFRASTRUCTURES[i].equals("dit")) 
                { dit_wrf_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n DIT!"); }
            if (wrf_INFRASTRUCTURES[i]!=null && wrf_INFRASTRUCTURES[i].equals("garuda")) 
                { garuda_wrf_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GARUDA!"); }
            if (wrf_INFRASTRUCTURES[i]!=null && wrf_INFRASTRUCTURES[i].equals("chain")) 
                { chain_wrf_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n CHAIN!"); }
            if (wrf_INFRASTRUCTURES[i]!=null && wrf_INFRASTRUCTURES[i].equals("fedcloud")) 
                { fedcloud_wrf_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n FEDCLOUD!"); }
            if (wrf_INFRASTRUCTURES[i]!=null && wrf_INFRASTRUCTURES[i].equals("eumed")) 
                { eumed_wrf_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n EUMED!"); }
            if (wrf_INFRASTRUCTURES[i]!=null && wrf_INFRASTRUCTURES[i].equals("gisela")) 
                { gisela_wrf_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GISELA!"); }
            if (wrf_INFRASTRUCTURES[i]!=null && wrf_INFRASTRUCTURES[i].equals("sagrid")) 
                { sagrid_wrf_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n SAGRID!"); }
        }
                
        // Get the APPID from the portlet preferences
        String wrf_APPID = portletPreferences.getValue("wrf_APPID", "N/A");
        // Get the LOGLEVEL from the portlet preferences
        String wrf_LOGLEVEL = portletPreferences.getValue("wrf_LOGLEVEL", "INFO");
        // Get the  APPID from the portlet preferences
        String wrf_OUTPUT_PATH = portletPreferences.getValue("wrf_OUTPUT_PATH", "N/A");
        // Get the  SOFTWARE from the portlet preferences
        String wrf_SOFTWARE = portletPreferences.getValue("wrf_SOFTWARE", "N/A");
        // Get the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Get the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Get the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Get the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Get the SENDER_MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        
        if (dit_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[0]="dit";
            // Get the INFRASTRUCTURE from the portlet preferences for the DIT VO
            String dit_wrf_INFRASTRUCTURE = portletPreferences.getValue("dit_wrf_INFRASTRUCTURE", "N/A");
            // Get the VONAME from the portlet preferences for the DIT VO
            dit_wrf_LOGIN = portletPreferences.getValue("dit_wrf_LOGIN", "N/A");
            // Get the TOPPBDII from the portlet preferences for the DIT VO
            dit_wrf_PASSWD = portletPreferences.getValue("dit_wrf_PASSWD", "N/A");
            // Get the WMS from the portlet preferences for the DIT VO
            String[] dit_wrf_WMS = portletPreferences.getValues("dit_wrf_WMS", new String[5]);
            // Get the ETOKENSERVER from the portlet preferences for the DIT VO
            String dit_wrf_ETOKENSERVER = portletPreferences.getValue("dit_wrf_ETOKENSERVER", "N/A");
            // Get the MYPROXYSERVER from the portlet preferences for the DIT VO
            String dit_wrf_MYPROXYSERVER = portletPreferences.getValue("dit_wrf_MYPROXYSERVER", "N/A");
            // Get the PORT from the portlet preferences for the DIT VO
            String dit_wrf_PORT = portletPreferences.getValue("dit_wrf_PORT", "N/A");
            // Get the ROBOTID from the portlet preferences for the DIT VO
            String dit_wrf_ROBOTID = portletPreferences.getValue("chain_wrf_ROBOTID", "N/A");
            // Get the ROLE from the portlet preferences for the DIT VO
            String dit_wrf_ROLE = portletPreferences.getValue("dit_wrf_ROLE", "N/A");
            // Get the RENEWAL from the portlet preferences for the DIT VO
            String dit_wrf_RENEWAL = portletPreferences.getValue("dit_wrf_RENEWAL", "checked");
            // Get the DISABLEVOMS from the portlet preferences for the DIT VO
            String dit_wrf_DISABLEVOMS = portletPreferences.getValue("dit_wrf_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the DIT VO
            String dit_WMS = "";
            if (dit_wrf_ENABLEINFRASTRUCTURE.equals("checked")) {
                if (dit_wrf_WMS!=null) {
                    //log.info("length="+dit_wrf_WMS.length);
                    for (int i = 0; i < dit_wrf_WMS.length; i++)
                        if (!(dit_wrf_WMS[i].trim().equals("N/A")) ) 
                            dit_WMS += dit_wrf_WMS[i] + " ";                        
                } else { log.info("WMS not set for DIT!"); dit_wrf_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("dit_wrf_INFRASTRUCTURE", dit_wrf_INFRASTRUCTURE.trim());
            request.setAttribute("dit_wrf_LOGIN", dit_wrf_LOGIN.trim());
            request.setAttribute("dit_wrf_PASSWD", dit_wrf_PASSWD.trim());
            request.setAttribute("dit_wrf_WMS", dit_WMS);
            request.setAttribute("dit_wrf_ETOKENSERVER", dit_wrf_ETOKENSERVER.trim());
            request.setAttribute("dit_wrf_MYPROXYSERVER", dit_wrf_MYPROXYSERVER.trim());
            request.setAttribute("dit_wrf_PORT", dit_wrf_PORT.trim());
            request.setAttribute("dit_wrf_ROBOTID", dit_wrf_ROBOTID.trim());
            request.setAttribute("dit_wrf_ROLE", dit_wrf_ROLE.trim());
            request.setAttribute("dit_wrf_RENEWAL", dit_wrf_RENEWAL);
            request.setAttribute("dit_wrf_DISABLEVOMS", dit_wrf_DISABLEVOMS);
            
            //request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("wrf_APPID", wrf_APPID.trim());
            request.setAttribute("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
            request.setAttribute("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
            request.setAttribute("wrf_SOFTWARE", wrf_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (garuda_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[1]="garuda";
            // Get the INFRASTRUCTURE from the portlet preferences for the GARUDA VO
            String garuda_wrf_INFRASTRUCTURE = portletPreferences.getValue("garuda_wrf_INFRASTRUCTURE", "N/A");
            // Get the VONAME from the portlet preferences for the GARUDA VO
            garuda_wrf_VONAME = portletPreferences.getValue("garuda_wrf_VONAME", "N/A");
            // Get the TOPPBDII from the portlet preferences for the GARUDA VO
            garuda_wrf_TOPBDII = portletPreferences.getValue("garuda_wrf_TOPBDII", "N/A");
            // Get the WMS from the portlet preferences for the GARUDA VO
            String[] garuda_wrf_WMS = portletPreferences.getValues("garuda_wrf_WMS", new String[5]);
            // Get the ETOKENSERVER from the portlet preferences for the GARUDA VO
            String garuda_wrf_ETOKENSERVER = portletPreferences.getValue("garuda_wrf_ETOKENSERVER", "N/A");
            // Get the MYPROXYSERVER from the portlet preferences for the GARUDA VO
            String garuda_wrf_MYPROXYSERVER = portletPreferences.getValue("garuda_wrf_MYPROXYSERVER", "N/A");
            // Get the PORT from the portlet preferences for the GARUDA VO
            String garuda_wrf_PORT = portletPreferences.getValue("garuda_wrf_PORT", "N/A");
            // Get the ROBOTID from the portlet preferences for the GARUDA VO
            String garuda_wrf_ROBOTID = portletPreferences.getValue("garuda_wrf_ROBOTID", "N/A");
            // Get the ROLE from the portlet preferences for the GARUDA VO
            String garuda_wrf_ROLE = portletPreferences.getValue("garuda_wrf_ROLE", "N/A");
            // Get the RENEWAL from the portlet preferences for the GARUDA VO
            String garuda_wrf_RENEWAL = portletPreferences.getValue("garuda_wrf_RENEWAL", "checked");
            // Get the DISABLEVOMS from the portlet preferences for the GARUDA VO
            String garuda_wrf_DISABLEVOMS = portletPreferences.getValue("garuda_wrf_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GARUDA VO
            String garuda_WMS = "";
            if (garuda_wrf_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (garuda_wrf_WMS!=null) {
                    //log.info("length="+garuda_wrf_WMS.length);
                    for (int i = 0; i < garuda_wrf_WMS.length; i++)
                        if (!(garuda_wrf_WMS[i].trim().equals("N/A")) ) 
                            garuda_WMS += garuda_wrf_WMS[i] + " ";                        
                } else { log.info("WSGRAM not set for GARUDA!"); garuda_wrf_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("garuda_wrf_INFRASTRUCTURE", garuda_wrf_INFRASTRUCTURE.trim());
            request.setAttribute("garuda_wrf_VONAME", garuda_wrf_VONAME.trim());
            request.setAttribute("garuda_wrf_TOPBDII", garuda_wrf_TOPBDII.trim());
            request.setAttribute("garuda_wrf_WMS", garuda_WMS);
            request.setAttribute("garuda_wrf_ETOKENSERVER", garuda_wrf_ETOKENSERVER.trim());
            request.setAttribute("garuda_wrf_MYPROXYSERVER", garuda_wrf_MYPROXYSERVER.trim());
            request.setAttribute("garuda_wrf_PORT", garuda_wrf_PORT.trim());
            request.setAttribute("garuda_wrf_ROBOTID", garuda_wrf_ROBOTID.trim());
            request.setAttribute("garuda_wrf_ROLE", garuda_wrf_ROLE.trim());
            request.setAttribute("garuda_wrf_RENEWAL", garuda_wrf_RENEWAL);
            request.setAttribute("garuda_wrf_DISABLEVOMS", garuda_wrf_DISABLEVOMS);
            
            //request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("wrf_APPID", wrf_APPID.trim());
            request.setAttribute("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
            request.setAttribute("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
            request.setAttribute("wrf_SOFTWARE", wrf_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (chain_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[2]="chain";
            // Get the INFRASTRUCTURE from the portlet preferences for the CHAIN VO
            String chain_wrf_INFRASTRUCTURE = portletPreferences.getValue("chain_wrf_INFRASTRUCTURE", "N/A");
            // Get the VONAME from the portlet preferences for the CHAIN VO
            chain_wrf_VONAME = portletPreferences.getValue("chain_wrf_VONAME", "N/A");
            // Get the TOPPBDII from the portlet preferences for the CHAIN VO
            chain_wrf_TOPBDII = portletPreferences.getValue("chain_wrf_TOPBDII", "N/A");
            // Get the WMS from the portlet preferences for the CHAIN VO
            String[] chain_wrf_WMS = portletPreferences.getValues("chain_wrf_WMS", new String[5]);
            // Get the ETOKENSERVER from the portlet preferences for the CHAIN VO
            String chain_wrf_ETOKENSERVER = portletPreferences.getValue("chain_wrf_ETOKENSERVER", "N/A");
            // Get the MYPROXYSERVER from the portlet preferences for the CHAIN VO
            String chain_wrf_MYPROXYSERVER = portletPreferences.getValue("chain_wrf_MYPROXYSERVER", "N/A");
            // Get the PORT from the portlet preferences for the CHAIN VO
            String chain_wrf_PORT = portletPreferences.getValue("chain_wrf_PORT", "N/A");
            // Get the ROBOTID from the portlet preferences for the CHAIN VO
            String chain_wrf_ROBOTID = portletPreferences.getValue("chain_wrf_ROBOTID", "N/A");
            // Get the ROLE from the portlet preferences for the CHAIN VO
            String chain_wrf_ROLE = portletPreferences.getValue("chain_wrf_ROLE", "N/A");
            // Get the RENEWAL from the portlet preferences for the CHAIN VO
            String chain_wrf_RENEWAL = portletPreferences.getValue("chain_wrf_RENEWAL", "checked");
            // Get the DISABLEVOMS from the portlet preferences for the CHAIN VO
            String chain_wrf_DISABLEVOMS = portletPreferences.getValue("chain_wrf_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the CHAIN VO
            String chain_WMS = "";
            if (chain_wrf_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (chain_wrf_WMS!=null) {
                    //log.info("length="+chain_wrf_WMS.length);
                    for (int i = 0; i < chain_wrf_WMS.length; i++)
                        if (!(chain_wrf_WMS[i].trim().equals("N/A")) ) 
                            chain_WMS += chain_wrf_WMS[i] + " ";                        
                } else { log.info("WMS not set for CHAIN!"); chain_wrf_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("chain_wrf_INFRASTRUCTURE", chain_wrf_INFRASTRUCTURE.trim());
            request.setAttribute("chain_wrf_VONAME", chain_wrf_VONAME.trim());
            request.setAttribute("chain_wrf_TOPBDII", chain_wrf_TOPBDII.trim());
            request.setAttribute("chain_wrf_WMS", chain_WMS);
            request.setAttribute("chain_wrf_ETOKENSERVER", chain_wrf_ETOKENSERVER.trim());
            request.setAttribute("chain_wrf_MYPROXYSERVER", chain_wrf_MYPROXYSERVER.trim());
            request.setAttribute("chain_wrf_PORT", chain_wrf_PORT.trim());
            request.setAttribute("chain_wrf_ROBOTID", chain_wrf_ROBOTID.trim());
            request.setAttribute("chain_wrf_ROLE", chain_wrf_ROLE.trim());
            request.setAttribute("chain_wrf_RENEWAL", chain_wrf_RENEWAL);
            request.setAttribute("chain_wrf_DISABLEVOMS", chain_wrf_DISABLEVOMS);
            
            //request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("wrf_APPID", wrf_APPID.trim());
            request.setAttribute("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
            request.setAttribute("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
            request.setAttribute("wrf_SOFTWARE", wrf_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (fedcloud_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[3]="fedcloud";
            // Get the INFRASTRUCTURE from the portlet preferences for the FEDCLOUD VO
            String fedcloud_wrf_INFRASTRUCTURE = portletPreferences.getValue("fedcloud_wrf_INFRASTRUCTURE", "N/A");
            // Get the VONAME from the portlet preferences for the FEDCLOUD VO
            fedcloud_wrf_VONAME = portletPreferences.getValue("fedcloud_wrf_VONAME", "N/A");
            // Get the TOPPBDII from the portlet preferences for the FEDCLOUD VO
            fedcloud_wrf_TOPBDII = portletPreferences.getValue("fedcloud_wrf_TOPBDII", "N/A");
            // Get the WMS from the portlet preferences for the FEDCLOUD VO
            String[] fedcloud_wrf_WMS = portletPreferences.getValues("fedcloud_wrf_WMS", new String[10]);
            // Get the ETOKENSERVER from the portlet preferences for the FEDCLOUD VO
            String fedcloud_wrf_ETOKENSERVER = portletPreferences.getValue("fedcloud_wrf_ETOKENSERVER", "N/A");
            // Get the MYPROXYSERVER from the portlet preferences for the FEDCLOUD VO
            String fedcloud_wrf_MYPROXYSERVER = portletPreferences.getValue("fedcloud_wrf_MYPROXYSERVER", "N/A");
            // Get the PORT from the portlet preferences for the FEDCLOUD VO
            String fedcloud_wrf_PORT = portletPreferences.getValue("fedcloud_wrf_PORT", "N/A");
            // Get the ROBOTID from the portlet preferences for the FEDCLOUD VO
            String fedcloud_wrf_ROBOTID = portletPreferences.getValue("fedcloud_wrf_ROBOTID", "N/A");
            // Get the ROLE from the portlet preferences for the FEDCLOUD VO
            String fedcloud_wrf_ROLE = portletPreferences.getValue("fedcloud_wrf_ROLE", "N/A");
            // Get the RENEWAL from the portlet preferences for the FEDCLOUD VO
            String fedcloud_wrf_RENEWAL = portletPreferences.getValue("fedcloud_wrf_RENEWAL", "checked");
            // Get the DISABLEVOMS from the portlet preferences for the FEDCLOUD VO
            String fedcloud_wrf_DISABLEVOMS = portletPreferences.getValue("fedcloud_wrf_DISABLEVOMS", "unchecked");
                                    
            // Fetching all the WMS Endpoints for the FEDCLOUD VO
            String fedcloud_WMS = "";
            if (fedcloud_wrf_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (fedcloud_wrf_WMS!=null) {
                    //log.info("length="+fedcloud_wrf_WMS.length);
                    for (int i = 0; i < fedcloud_wrf_WMS.length; i++)
                        if (!(fedcloud_wrf_WMS[i].trim().equals("N/A")) ) 
                            fedcloud_WMS += fedcloud_wrf_WMS[i] + " ";                        
                } else { log.info("WMS not set for FEDCLOUD!"); fedcloud_wrf_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("fedcloud_wrf_INFRASTRUCTURE", fedcloud_wrf_INFRASTRUCTURE.trim());
            request.setAttribute("fedcloud_wrf_VONAME", fedcloud_wrf_VONAME.trim());
            request.setAttribute("fedcloud_wrf_TOPBDII", fedcloud_wrf_TOPBDII.trim());
            request.setAttribute("fedcloud_wrf_WMS", fedcloud_WMS);
            request.setAttribute("fedcloud_wrf_ETOKENSERVER", fedcloud_wrf_ETOKENSERVER.trim());
            request.setAttribute("fedcloud_wrf_MYPROXYSERVER", fedcloud_wrf_MYPROXYSERVER.trim());
            request.setAttribute("fedcloud_wrf_PORT", fedcloud_wrf_PORT.trim());
            request.setAttribute("fedcloud_wrf_ROBOTID", fedcloud_wrf_ROBOTID.trim());
            request.setAttribute("fedcloud_wrf_ROLE", fedcloud_wrf_ROLE.trim());
            request.setAttribute("fedcloud_wrf_RENEWAL", fedcloud_wrf_RENEWAL);
            request.setAttribute("fedcloud_wrf_DISABLEVOMS", fedcloud_wrf_DISABLEVOMS);

            //request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("wrf_APPID", wrf_APPID.trim());
            request.setAttribute("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
            request.setAttribute("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
            request.setAttribute("wrf_SOFTWARE", wrf_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (eumed_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[4]="eumed";
            // Get the INFRASTRUCTURE from the portlet preferences for the EUMED VO
            String eumed_wrf_INFRASTRUCTURE = portletPreferences.getValue("eumed_wrf_INFRASTRUCTURE", "N/A");
            // Get the VONAME from the portlet preferences for the EUMED VO
            eumed_wrf_VONAME = portletPreferences.getValue("eumed_wrf_VONAME", "N/A");
            // Get the TOPPBDII from the portlet preferences for the EUMED VO
            eumed_wrf_TOPBDII = portletPreferences.getValue("eumed_wrf_TOPBDII", "N/A");
            // Get the WMS from the portlet preferences for the EUMED VO
            String[] eumed_wrf_WMS = portletPreferences.getValues("eumed_wrf_WMS", new String[5]);
            // Get the ETOKENSERVER from the portlet preferences for the EUMED VO
            String eumed_wrf_ETOKENSERVER = portletPreferences.getValue("eumed_wrf_ETOKENSERVER", "N/A");
            // Get the MYPROXYSERVER from the portlet preferences for the EUMED VO
            String eumed_wrf_MYPROXYSERVER = portletPreferences.getValue("eumed_wrf_MYPROXYSERVER", "N/A");
            // Get the PORT from the portlet preferences for the EUMED VO
            String eumed_wrf_PORT = portletPreferences.getValue("eumed_wrf_PORT", "N/A");
            // Get the ROBOTID from the portlet preferences for the EUMED VO
            String eumed_wrf_ROBOTID = portletPreferences.getValue("eumed_wrf_ROBOTID", "N/A");
            // Get the ROLE from the portlet preferences for the EUMED VO
            String eumed_wrf_ROLE = portletPreferences.getValue("eumed_wrf_ROLE", "N/A");
            // Get the RENEWAL from the portlet preferences for the EUMED VO
            String eumed_wrf_RENEWAL = portletPreferences.getValue("eumed_wrf_RENEWAL", "checked");
            // Get the DISABLEVOMS from the portlet preferences for the EUMED VO
            String eumed_wrf_DISABLEVOMS = portletPreferences.getValue("eumed_wrf_DISABLEVOMS", "unchecked");
                                    
            // Fetching all the WMS Endpoints for the EUMED VO
            String eumed_WMS = "";
            if (eumed_wrf_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (eumed_wrf_WMS!=null) {
                    //log.info("length="+eumed_wrf_WMS.length);
                    for (int i = 0; i < eumed_wrf_WMS.length; i++)
                        if (!(eumed_wrf_WMS[i].trim().equals("N/A")) ) 
                            eumed_WMS += eumed_wrf_WMS[i] + " ";                        
                } else { log.info("WMS not set for EUMED!"); eumed_wrf_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("eumed_wrf_INFRASTRUCTURE", eumed_wrf_INFRASTRUCTURE.trim());
            request.setAttribute("eumed_wrf_VONAME", eumed_wrf_VONAME.trim());
            request.setAttribute("eumed_wrf_TOPBDII", eumed_wrf_TOPBDII.trim());
            request.setAttribute("eumed_wrf_WMS", eumed_WMS);
            request.setAttribute("eumed_wrf_ETOKENSERVER", eumed_wrf_ETOKENSERVER.trim());
            request.setAttribute("eumed_wrf_MYPROXYSERVER", eumed_wrf_MYPROXYSERVER.trim());
            request.setAttribute("eumed_wrf_PORT", eumed_wrf_PORT.trim());
            request.setAttribute("eumed_wrf_ROBOTID", eumed_wrf_ROBOTID.trim());
            request.setAttribute("eumed_wrf_ROLE", eumed_wrf_ROLE.trim());
            request.setAttribute("eumed_wrf_RENEWAL", eumed_wrf_RENEWAL);
            request.setAttribute("eumed_wrf_DISABLEVOMS", eumed_wrf_DISABLEVOMS);

            //request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("wrf_APPID", wrf_APPID.trim());
            request.setAttribute("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
            request.setAttribute("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
            request.setAttribute("wrf_SOFTWARE", wrf_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }

        if (gisela_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[5]="gisela";
            // Get the INFRASTRUCTURE from the portlet preferences for the GISELA VO
            String gisela_wrf_INFRASTRUCTURE = portletPreferences.getValue("gisela_wrf_INFRASTRUCTURE", "N/A");
            // Get the VONAME from the portlet preferences for the GISELA VO
            gisela_wrf_VONAME = portletPreferences.getValue("gisela_wrf_VONAME", "N/A");
            // Get the TOPPBDII from the portlet preferences for the GISELA VO
            gisela_wrf_TOPBDII = portletPreferences.getValue("gisela_wrf_TOPBDII", "N/A");
            // Get the WMS from the portlet preferences for the GISELA VO
            String[] gisela_wrf_WMS = portletPreferences.getValues("gisela_wrf_WMS", new String[5]);
            // Get the ETOKENSERVER from the portlet preferences for the GISELA VO
            String gisela_wrf_ETOKENSERVER = portletPreferences.getValue("gisela_wrf_ETOKENSERVER", "N/A");
            // Get the MYPROXYSERVER from the portlet preferences for the GISELA VO
            String gisela_wrf_MYPROXYSERVER = portletPreferences.getValue("gisela_wrf_MYPROXYSERVER", "N/A");
            // Get the PORT from the portlet preferences for the GISELA VO
            String gisela_wrf_PORT = portletPreferences.getValue("gisela_wrf_PORT", "N/A");
            // Get the ROBOTID from the portlet preferences for the GISELA VO
            String gisela_wrf_ROBOTID = portletPreferences.getValue("gisela_wrf_ROBOTID", "N/A");
            // Get the ROLE from the portlet preferences for the GISELA VO
            String gisela_wrf_ROLE = portletPreferences.getValue("gisela_wrf_ROLE", "N/A");
            // Get the RENEWAL from the portlet preferences for the GISELA VO
            String gisela_wrf_RENEWAL = portletPreferences.getValue("gisela_wrf_RENEWAL", "checked");
            // Get the DISABLEVOMS from the portlet preferences for the GISELA VO
            String gisela_wrf_DISABLEVOMS = portletPreferences.getValue("gisela_wrf_DISABLEVOMS", "unchecked");              
            
            // Fetching all the WMS Endpoints for the GISELA VO
            String gisela_WMS = "";
            if (gisela_wrf_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gisela_wrf_WMS!=null) {
                    //log.info("length="+gisela_wrf_WMS.length);
                    for (int i = 0; i < gisela_wrf_WMS.length; i++)
                        if (!(gisela_wrf_WMS[i].trim().equals("N/A")) ) 
                            gisela_WMS += gisela_wrf_WMS[i] + " ";                        
                } else { log.info("WMS not set for GISELA!"); gisela_wrf_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gisela_wrf_INFRASTRUCTURE", gisela_wrf_INFRASTRUCTURE.trim());
            request.setAttribute("gisela_wrf_VONAME", gisela_wrf_VONAME.trim());
            request.setAttribute("gisela_wrf_TOPBDII", gisela_wrf_TOPBDII.trim());
            request.setAttribute("gisela_wrf_WMS", gisela_WMS);
            request.setAttribute("gisela_wrf_ETOKENSERVER", gisela_wrf_ETOKENSERVER.trim());
            request.setAttribute("gisela_wrf_MYPROXYSERVER", gisela_wrf_MYPROXYSERVER.trim());
            request.setAttribute("gisela_wrf_PORT", gisela_wrf_PORT.trim());
            request.setAttribute("gisela_wrf_ROBOTID", gisela_wrf_ROBOTID.trim());
            request.setAttribute("gisela_wrf_ROLE", gisela_wrf_ROLE.trim());
            request.setAttribute("gisela_wrf_RENEWAL", gisela_wrf_RENEWAL);
            request.setAttribute("gisela_wrf_DISABLEVOMS", gisela_wrf_DISABLEVOMS);

            //request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("wrf_APPID", wrf_APPID.trim());
            request.setAttribute("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
            request.setAttribute("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
            request.setAttribute("wrf_SOFTWARE", wrf_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (sagrid_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[6]="sagrid";
            // Get the INFRASTRUCTURE from the portlet preferences for the SAGRID VO
            String sagrid_wrf_INFRASTRUCTURE = portletPreferences.getValue("sagrid_wrf_INFRASTRUCTURE", "N/A");
            // Get the VONAME from the portlet preferences for the SAGRID VO
            sagrid_wrf_VONAME = portletPreferences.getValue("sagrid_wrf_VONAME", "N/A");
            // Get the TOPPBDII from the portlet preferences for the SAGRID VO
            sagrid_wrf_TOPBDII = portletPreferences.getValue("sagrid_wrf_TOPBDII", "N/A");
            // Get the WMS from the portlet preferences for the SAGRID VO
            String[] sagrid_wrf_WMS = portletPreferences.getValues("sagrid_wrf_WMS", new String[5]);
            // Get the ETOKENSERVER from the portlet preferences for the SAGRID VO
            String sagrid_wrf_ETOKENSERVER = portletPreferences.getValue("sagrid_wrf_ETOKENSERVER", "N/A");
            // Get the MYPROXYSERVER from the portlet preferences for the SAGRID VO
            String sagrid_wrf_MYPROXYSERVER = portletPreferences.getValue("sagrid_wrf_MYPROXYSERVER", "N/A");
            // Get the PORT from the portlet preferences for the SAGRID VO
            String sagrid_wrf_PORT = portletPreferences.getValue("sagrid_wrf_PORT", "N/A");
            // Get the ROBOTID from the portlet preferences for the SAGRID VO
            String sagrid_wrf_ROBOTID = portletPreferences.getValue("sagrid_wrf_ROBOTID", "N/A");
            // Get the ROLE from the portlet preferences for the SAGRID VO
            String sagrid_wrf_ROLE = portletPreferences.getValue("sagrid_wrf_ROLE", "N/A");
            // Get the RENEWAL from the portlet preferences for the SAGRID VO
            String sagrid_wrf_RENEWAL = portletPreferences.getValue("sagrid_wrf_RENEWAL", "checked");
            // Get the DISABLEVOMS from the portlet preferences for the SAGRID VO
            String sagrid_wrf_DISABLEVOMS = portletPreferences.getValue("sagrid_wrf_DISABLEVOMS", "unchecked");              
            // Fetching all the WMS Endpoints for the SAGRID VO
            String sagrid_WMS = "";
            if (sagrid_wrf_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (sagrid_wrf_WMS!=null) {
                    //log.info("length="+sagrid_wrf_WMS.length);
                    for (int i = 0; i < sagrid_wrf_WMS.length; i++)
                        if (!(sagrid_wrf_WMS[i].trim().equals("N/A")) ) 
                            sagrid_WMS += sagrid_wrf_WMS[i] + " ";                        
                } else { log.info("WMS not set for SAGRID!"); sagrid_wrf_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("sagrid_wrf_INFRASTRUCTURE", sagrid_wrf_INFRASTRUCTURE.trim());
            request.setAttribute("sagrid_wrf_VONAME", sagrid_wrf_VONAME.trim());
            request.setAttribute("sagrid_wrf_TOPBDII", sagrid_wrf_TOPBDII.trim());
            request.setAttribute("sagrid_wrf_WMS", sagrid_WMS);
            request.setAttribute("sagrid_wrf_ETOKENSERVER", sagrid_wrf_ETOKENSERVER.trim());
            request.setAttribute("sagrid_wrf_MYPROXYSERVER", sagrid_wrf_MYPROXYSERVER.trim());
            request.setAttribute("sagrid_wrf_PORT", sagrid_wrf_PORT.trim());
            request.setAttribute("sagrid_wrf_ROBOTID", sagrid_wrf_ROBOTID.trim());
            request.setAttribute("sagrid_wrf_ROLE", sagrid_wrf_ROLE.trim());
            request.setAttribute("sagrid_wrf_RENEWAL", sagrid_wrf_RENEWAL);
            request.setAttribute("sagrid_wrf_DISABLEVOMS", sagrid_wrf_DISABLEVOMS);

            //request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("wrf_APPID", wrf_APPID.trim());
            request.setAttribute("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
            request.setAttribute("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
            request.setAttribute("wrf_SOFTWARE", wrf_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
         // Save in the preferences the list of supported infrastructures 
         request.setAttribute("wrf_ENABLEINFRASTRUCTURE", infras);        

         HashMap<String,Properties> GPS_table = new HashMap<String, Properties>();
         HashMap<String,Properties> GPS_queue = new HashMap<String, Properties>();

         // ********************************************************
         List<String> CEqueues_dit = null;                  
         List<String> CEqueues_garuda = null;
         List<String> CEqueues_chain = null;
         List<String> CEqueues_fedcloud = null;
         List<String> CEqueues_eumed = null;
         List<String> CEqueues_gisela = null;
         List<String> CEqueues_sagrid = null;
         
         List<String> CEs_list_dit = null;
         List<String> CEs_list_garuda = null;
         List<String> CEs_list_chain = null;
         List<String> CEs_list_fedcloud = null;
         List<String> CEs_list_eumed = null;
         List<String> CEs_list_gisela = null;
         List<String> CEs_list_sagrid = null;
         
         BDII bdii = null;
         
         if (dit_wrf_ENABLEINFRASTRUCTURE.equals("checked") && 
            (!dit_wrf_PASSWD.equals("N/A")) ) 
         {
           log.info("-----*FETCHING*THE*<DIT>*CLUSTER*RESOURCES*-----");
            CEs_list_dit = new ArrayList();
            CEs_list_dit.add("41.93.32.21");
                    
            CEqueues_dit = new ArrayList();
            CEqueues_dit.add("ssh://41.93.32.21");
         }
                
         //=========================================================
         // IMPORTANT: THIS FIX IS ONLY FOR INSTANCIATE THE 
         //            CHAIN INTEROPERABILITY DEMO                
         //=========================================================
         // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
         if (garuda_wrf_ENABLEINFRASTRUCTURE.equals("checked") && 
            (!garuda_wrf_TOPBDII.equals("N/A")) ) 
         {
            log.info("-----*FETCHING*THE*<GARUDA>*CLOUD*RESOURCES*-----");
            CEs_list_garuda = new ArrayList();
            CEs_list_garuda.add("xn03.ctsf.cdacb.in");
                    
            CEqueues_garuda = new ArrayList();
            CEqueues_garuda.add("wsgram://xn03.ctsf.cdacb.in:8443/GW");
         }
         // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
             
         if (chain_wrf_ENABLEINFRASTRUCTURE.equals("checked") && 
            (!chain_wrf_TOPBDII.equals("N/A")) ) 
         {
            log.info("-----*FETCHING*THE*<CHAIN>*CLOUD*RESOURCES*-----");
                    
            CEs_list_chain = new ArrayList();
            CEqueues_chain = new ArrayList();
                    
            // Get the CR from the portlet preferences for the CHAIN VO
            String[] chain_wrf_WMS = 
                portletPreferences.getValues("chain_wrf_WMS", 
                                              new String[5]);
                    
             if (chain_wrf_WMS!=null) 
             {
                for (int i = 0; i < chain_wrf_WMS.length; i++)
                    if (!(chain_wrf_WMS[i].trim().equals("N/A")) )
                    {
                        CEqueues_chain.add(chain_wrf_WMS[i].trim());
                              
                        // Removing "rocci://" prefix from string
                        chain_wrf_WMS[i] = 
                            chain_wrf_WMS[i].replace("rocci://", "");
                              
                        // Removing port from string
                        CEs_list_chain.add(
                            chain_wrf_WMS[i].substring(0,
                            chain_wrf_WMS[i].indexOf(":"))
                        );
                        
                        log.info("Cloud Resource = " + 
                                  chain_wrf_WMS[i].substring(0,
                                  chain_wrf_WMS[i].indexOf(":")));
                              
                    } else { log.info("Cloud Resource not set for the CHAIN VO!");
                             dit_wrf_ENABLEINFRASTRUCTURE="unchecked";
                    }                            
             }                    
         }
                
         if (fedcloud_wrf_ENABLEINFRASTRUCTURE.equals("checked") && 
            (!fedcloud_wrf_TOPBDII.equals("N/A")) ) 
         {
            log.info("-----*FETCHING*THE*<FEDCLOUD>*CLOUD*RESOURCES*-----");
                   
            CEs_list_fedcloud = new ArrayList();
            CEqueues_fedcloud = new ArrayList();
                    
            // Get the CR from the portlet preferences for the FEDCLOUD VO
            String[] fedcloud_wrf_WMS = 
                portletPreferences.getValues("fedcloud_wrf_WMS", 
                                              new String[10]);
            
            log.info("TOT Cloud Resource = " + fedcloud_wrf_WMS.length);
                    
            if (fedcloud_wrf_WMS!=null) 
            {
                for (int i = 0; i < fedcloud_wrf_WMS.length; i++)
                    if (!(fedcloud_wrf_WMS[i].trim().equals("N/A")) )
                    {
                        CEqueues_fedcloud.add(fedcloud_wrf_WMS[i].trim());
                                                                              
                        // Removing "rocci://" prefix from string
                        fedcloud_wrf_WMS[i] = 
                            fedcloud_wrf_WMS[i].replace("rocci://", "");
                              
                        // Removing port from string
                        CEs_list_fedcloud.add(
                            fedcloud_wrf_WMS[i].substring(0,
                            fedcloud_wrf_WMS[i].indexOf(":"))
                        );
                        
                        log.info("Cloud Resource = " + 
                                  fedcloud_wrf_WMS[i].substring(0,
                                  fedcloud_wrf_WMS[i].indexOf(":")));
                              
                    } else { log.info("Cloud Resource not set for the FEDCLOUD VO!");
                             fedcloud_wrf_ENABLEINFRASTRUCTURE="unchecked";
                    }                            
            }                    
         }
                
         if (eumed_wrf_ENABLEINFRASTRUCTURE.equals("checked") && 
            (!eumed_wrf_TOPBDII.equals("N/A")) ) 
         {
            try {
                log.info("-----*FETCHING*THE*<EUMED>*RESOURCES*-----");
                        
                CEs_list_eumed = new ArrayList();
                CEqueues_eumed = new ArrayList();
                                        
                bdii = new BDII(new URI(eumed_wrf_TOPBDII));
                CEs_list_eumed = 
                    getListofCEForSoftwareTag(eumed_wrf_VONAME,
                                              eumed_wrf_TOPBDII,
                                              wrf_SOFTWARE);
                        
                CEqueues_eumed = 
                    bdii.queryCEQueues(eumed_wrf_VONAME);
            } catch (NamingException ex) {
                Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
                
         if (gisela_wrf_ENABLEINFRASTRUCTURE.equals("checked") &&
            (!gisela_wrf_TOPBDII.equals("N/A")) ) 
         {
            log.info("-----*FETCHING*THE*<GISELA>*CLOUD*RESOURCES*-----");
                    
            CEs_list_gisela = new ArrayList();
            CEqueues_gisela = new ArrayList();
                    
            // Get the CR from the portlet preferences for the GISELA VO
            String[] gisela_wrf_WMS = 
                portletPreferences.getValues("gisela_wrf_WMS", 
                                             new String[5]);
                    
             if (gisela_wrf_WMS!=null) 
             {
                for (int i = 0; i < gisela_wrf_WMS.length; i++)
                    if (!(gisela_wrf_WMS[i].trim().equals("N/A")) )
                    {
                        CEqueues_gisela.add(gisela_wrf_WMS[i].trim());
                              
                        // Removing "rocci://" prefix from string
                        gisela_wrf_WMS[i] = 
                            gisela_wrf_WMS[i].replace("rocci://", "");
                              
                        // Removing port from string
                        CEs_list_gisela.add(
                            gisela_wrf_WMS[i].substring(0,
                            gisela_wrf_WMS[i].indexOf(":"))
                        );
                        
                        log.info("Cloud Resource = " + 
                                  gisela_wrf_WMS[i].substring(0,
                                  gisela_wrf_WMS[i].indexOf(":")));
                              
                    } else { log.info("Cloud Resource not set for the GISELA VO!");
                             fedcloud_wrf_ENABLEINFRASTRUCTURE="unchecked";
                    }                            
             }                    
         }
                
         if (sagrid_wrf_ENABLEINFRASTRUCTURE.equals("checked") &&
            (!sagrid_wrf_TOPBDII.equals("N/A")) ) 
         {
            log.info("-----*FETCHING*THE*<SAGRID>*CLOUD*RESOURCES*-----");
                    
            CEs_list_sagrid = new ArrayList();
            CEqueues_sagrid = new ArrayList();
                    
            // Get the CR from the portlet preferences for the SAGRID VO
            String[] sagrid_wrf_WMS = 
                portletPreferences.getValues("sagrid_wrf_WMS", 
                                              new String[5]);
                    
            if (sagrid_wrf_WMS!=null) 
            {
                for (int i = 0; i < sagrid_wrf_WMS.length; i++)
                    if (!(sagrid_wrf_WMS[i].trim().equals("N/A")) )
                    {
                        CEqueues_sagrid.add(sagrid_wrf_WMS[i].trim());
                              
                        // Removing "rocci://" prefix from string
                        sagrid_wrf_WMS[i] = 
                            sagrid_wrf_WMS[i].replace("rocci://", "");
                              
                        // Removing port from string
                        CEs_list_sagrid.add(
                            sagrid_wrf_WMS[i].substring(0,
                            sagrid_wrf_WMS[i].indexOf(":"))
                        );
                        
                        log.info("Cloud Resource = " + 
                                  sagrid_wrf_WMS[i].substring(0,
                                  sagrid_wrf_WMS[i].indexOf(":")));
                              
                    } else { log.info("Cloud Resource not set for the SAGRID VO!");
                             fedcloud_wrf_ENABLEINFRASTRUCTURE="unchecked";
                    }                            
            }                    
         }
                
         // Merging the list of CEs and queues
         List<String> CEs_list_TOT = new ArrayList<String>();
         
         if (dit_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_list_TOT.addAll(CEs_list_dit);
         if (chain_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_list_TOT.addAll(CEs_list_chain);
         // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
         if (garuda_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_list_TOT.addAll(CEs_list_garuda);
         // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
         if (fedcloud_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_list_TOT.addAll(CEs_list_fedcloud);
         if (eumed_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_list_TOT.addAll(CEs_list_eumed);
         if (gisela_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_list_TOT.addAll(CEs_list_gisela);
         if (sagrid_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_list_TOT.addAll(CEs_list_sagrid);
                                
         List<String> CEs_queue_TOT = new ArrayList<String>();
         
         if (dit_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_queue_TOT.addAll(CEqueues_dit);
         if (chain_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_queue_TOT.addAll(CEqueues_chain);
         if (fedcloud_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_queue_TOT.addAll(CEqueues_fedcloud);                
         if (eumed_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_queue_TOT.addAll(CEqueues_eumed);
         if (gisela_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_queue_TOT.addAll(CEqueues_gisela);
         if (sagrid_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
            CEs_queue_TOT.addAll(CEqueues_sagrid); 
                
         //=========================================================
         // IMPORTANT: INSTANCIATE THE UsersTrackingDBInterface
         //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
         //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
         //=========================================================
         /*UsersTrackingDBInterface DBInterface =
            new UsersTrackingDBInterface(
                TRACKING_DB_HOSTNAME.trim(),
                TRACKING_DB_USERNAME.trim(),
                TRACKING_DB_PASSWORD.trim());*/
                
         UsersTrackingDBInterface DBInterface =
            new UsersTrackingDBInterface();
                    
         if ( (CEs_list_TOT != null) && (!CEs_queue_TOT.isEmpty()) )
         {
            log.info("NOT EMPTY LIST!");
            // Fetching the list of CEs publushing the SW
            for (String CE:CEs_list_TOT) 
            {
                //log.info("Fetching the CE="+CE);
                Properties coordinates = new Properties();
                Properties queue = new Properties();

                float coords[] = DBInterface.getCECoordinate(CE);                        

                String GPS_LAT = Float.toString(coords[0]);
                String GPS_LNG = Float.toString(coords[1]);

                coordinates.setProperty("LAT", GPS_LAT);
                coordinates.setProperty("LNG", GPS_LNG);
                log.info("Fetching CE settings for [ " + CE + 
                         " ] Coordinates [ " + GPS_LAT + 
                         ", " + GPS_LNG + " ]");

                // Fetching the Queues
                for (String CEqueue:CEs_queue_TOT) {
                    if (CEqueue.contains(CE))
                        queue.setProperty("QUEUE", CEqueue);
                 }

                 // Saving the GPS location in a Java HashMap
                 GPS_table.put(CE, coordinates);

                 // Saving the queue in a Java HashMap
                 GPS_queue.put(CE, queue);                                  
            }
         } else log.info ("EMPTY LIST!");
             
         // Checking the HashMap
         Set set = GPS_table.entrySet();
         Iterator iter = set.iterator();
         while ( iter.hasNext() )
         {
            Map.Entry entry = (Map.Entry)iter.next();
            log.info(" - GPS location of the CE " +
                       entry.getKey() + " => " + entry.getValue());
         }                  

         // Checking the HashMap
         set = GPS_queue.entrySet();
         iter = set.iterator();
         while ( iter.hasNext() )
         {
            Map.Entry entry = (Map.Entry)iter.next();
            log.info(" - Queue " +
                       entry.getKey() + " => " + entry.getValue());
         }
         
         Gson gson = new GsonBuilder().create();
         request.setAttribute ("GPS_table", gson.toJson(GPS_table));
         request.setAttribute ("GPS_queue", gson.toJson(GPS_queue));

         // ********************************************************
         dispatcher = getPortletContext().getRequestDispatcher("/view.jsp");       
         dispatcher.include(request, response);         
    }

    // The init method will be called when installing for the first time the portlet
    // This is the right time to setup the default values into the preferences
    @Override
    public void init() throws PortletException {
        super.init();
    }

    @Override
    public void processAction(ActionRequest request,
                              ActionResponse response)
                throws PortletException, IOException {
        try {
            String action = "";

            // Get the action to be processed from the request
            action = request.getParameter("ActionEvent");

            // Determine the username and the email address
            ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);        
            User user = themeDisplay.getUser();
                    
            String username = user.getScreenName();
            String emailAddress = user.getDisplayEmailAddress();        
            
            Company company = PortalUtil.getCompany(request);
            String portal = company.getName();
            //String portalURL = themeDisplay.getPortalURL();

            PortletPreferences portletPreferences =
                    (PortletPreferences) request.getPreferences();                
            
            if (action.equals("CONFIG_WRF_PORTLET")) {
                log.info("\nPROCESS ACTION => " + action);
                
                // Get the APPID from the portlet request
                String wrf_APPID = request.getParameter("wrf_APPID");
                // Get the LOGLEVEL from the portlet request
                String wrf_LOGLEVEL = request.getParameter("wrf_LOGLEVEL");
                // Get the OUTPUT from the portlet request
                String wrf_OUTPUT_PATH = request.getParameter("wrf_OUTPUT_PATH");
                // Get the SOFTWARE from the portlet request
                String wrf_SOFTWARE = request.getParameter("wrf_SOFTWARE");
                // Get the TRACKING_DB_HOSTNAME from the portlet request
                String TRACKING_DB_HOSTNAME = request.getParameter("TRACKING_DB_HOSTNAME");
                // Get the TRACKING_DB_USERNAME from the portlet request
                String TRACKING_DB_USERNAME = request.getParameter("TRACKING_DB_USERNAME");
                // Get the TRACKING_DB_PASSWORD from the portlet request
                String TRACKING_DB_PASSWORD = request.getParameter("TRACKING_DB_PASSWORD");
                // Get the SMTP_HOST from the portlet request
                String SMTP_HOST = request.getParameter("SMTP_HOST");
                // Get the SENDER_MAIL from the portlet request
                String SENDER_MAIL = request.getParameter("SENDER_MAIL");
                String[] infras = new String[7];
                
                String dit_wrf_ENABLEINFRASTRUCTURE = "unchecked";
                String garuda_wrf_ENABLEINFRASTRUCTURE = "unchecked";
                String chain_wrf_ENABLEINFRASTRUCTURE = "unchecked";
                String fedcloud_wrf_ENABLEINFRASTRUCTURE = "unchecked";
                String eumed_wrf_ENABLEINFRASTRUCTURE = "unchecked";
                String gisela_wrf_ENABLEINFRASTRUCTURE = "unchecked";
                String sagrid_wrf_ENABLEINFRASTRUCTURE = "unchecked";
            
                String[] wrf_INFRASTRUCTURES = request.getParameterValues("wrf_ENABLEINFRASTRUCTURES");
            
                if (wrf_INFRASTRUCTURES != null) {
                    Arrays.sort(wrf_INFRASTRUCTURES);                    
                    dit_wrf_ENABLEINFRASTRUCTURE = 
                        Arrays.binarySearch(wrf_INFRASTRUCTURES, "dit") >= 0 ? "checked" : "unchecked";
                    garuda_wrf_ENABLEINFRASTRUCTURE = 
                        Arrays.binarySearch(wrf_INFRASTRUCTURES, "garuda") >= 0 ? "checked" : "unchecked";
                    chain_wrf_ENABLEINFRASTRUCTURE = 
                        Arrays.binarySearch(wrf_INFRASTRUCTURES, "chain") >= 0 ? "checked" : "unchecked";
                    fedcloud_wrf_ENABLEINFRASTRUCTURE = 
                        Arrays.binarySearch(wrf_INFRASTRUCTURES, "fedcloud") >= 0 ? "checked" : "unchecked";
                    eumed_wrf_ENABLEINFRASTRUCTURE = 
                        Arrays.binarySearch(wrf_INFRASTRUCTURES, "eumed") >= 0 ? "checked" : "unchecked";
                    gisela_wrf_ENABLEINFRASTRUCTURE = 
                        Arrays.binarySearch(wrf_INFRASTRUCTURES, "gisela") >= 0 ? "checked" : "unchecked";
                    sagrid_wrf_ENABLEINFRASTRUCTURE = 
                        Arrays.binarySearch(wrf_INFRASTRUCTURES, "sagrid") >= 0 ? "checked" : "unchecked";
                }
            
                if (dit_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
                {
                    infras[0]="dit";
                    // Get the  INFRASTRUCTURE from the portlet request for the DIT VO
                    String dit_wrf_INFRASTRUCTURE = request.getParameter("dit_wrf_INFRASTRUCTURE");
                    // Get the  VONAME from the portlet request for the DIT VO
                    String dit_wrf_LOGIN = request.getParameter("dit_wrf_LOGIN");
                    // Get the  TOPBDII from the portlet request for the DIT VO
                    String dit_wrf_PASSWD = request.getParameter("dit_wrf_PASSWD");
                    // Get the  WMS from the portlet request for the DIT VO
                    String[] dit_wrf_WMS = request.getParameterValues("dit_wrf_WMS");
                    // Get the  ETOKENSERVER from the portlet request for the DIT VO
                    String dit_wrf_ETOKENSERVER = request.getParameter("dit_wrf_ETOKENSERVER");
                    // Get the  MYPROXYSERVER from the portlet request for the DIT VO
                    String dit_wrf_MYPROXYSERVER = request.getParameter("dit_wrf_MYPROXYSERVER");
                    // Get the  PORT from the portlet request for the DIT VO
                    String dit_wrf_PORT = request.getParameter("dit_wrf_PORT");
                    // Get the  ROBOTID from the portlet request for the DIT VO
                    String dit_wrf_ROBOTID = request.getParameter("dit_wrf_ROBOTID");
                    // Get the  ROLE from the portlet request for the DIT VO
                    String dit_wrf_ROLE = request.getParameter("dit_wrf_ROLE");
                    // Get the  OPTIONS from the portlet request for the DIT VO
                    String[] dit_wrf_OPTIONS = request.getParameterValues("dit_wrf_OPTIONS");                

                    String dit_wrf_RENEWAL = "";
                    String dit_wrf_DISABLEVOMS = "";

                    if (dit_wrf_OPTIONS == null){
                        dit_wrf_RENEWAL = "checked";
                        dit_wrf_DISABLEVOMS = "unchecked";
                    } else {
                        Arrays.sort(dit_wrf_OPTIONS);
                        // Get the  RENEWAL from the portlet preferences for the DIT VO
                        dit_wrf_RENEWAL = Arrays.binarySearch(dit_wrf_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the  DISABLEVOMS from the portlet preferences for the DIT VO
                        dit_wrf_DISABLEVOMS = Arrays.binarySearch(dit_wrf_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                    }
                    
                    int nmax=0;                
                    for (int i = 0; i < dit_wrf_WMS.length; i++)
                        if ( dit_wrf_WMS[i]!=null && (!dit_wrf_WMS[i].trim().equals("N/A")) )                        
                            nmax++;
                    
                    log.info("\n\nLength="+nmax);
                    String[] dit_wrf_WMS_trimmed = new String[nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        dit_wrf_WMS_trimmed[i]=dit_wrf_WMS[i].trim();
                        log.info ("\n\nDIT [" + i + "] WMS=[" + dit_wrf_WMS_trimmed[i] + "]");
                    }
                    
                    // Set the portlet preferences
                    portletPreferences.setValue("dit_wrf_INFRASTRUCTURE", dit_wrf_INFRASTRUCTURE.trim());
                    portletPreferences.setValue("dit_wrf_LOGIN", dit_wrf_LOGIN.trim());
                    portletPreferences.setValue("dit_wrf_PASSWD", dit_wrf_PASSWD.trim());
                    portletPreferences.setValues("dit_wrf_WMS", dit_wrf_WMS_trimmed);
                    portletPreferences.setValue("dit_wrf_ETOKENSERVER", dit_wrf_ETOKENSERVER.trim());
                    portletPreferences.setValue("dit_wrf_MYPROXYSERVER", dit_wrf_MYPROXYSERVER.trim());
                    portletPreferences.setValue("dit_wrf_PORT", dit_wrf_PORT.trim());
                    portletPreferences.setValue("dit_wrf_ROBOTID", dit_wrf_ROBOTID.trim());
                    portletPreferences.setValue("dit_wrf_ROLE", dit_wrf_ROLE.trim());
                    portletPreferences.setValue("dit_wrf_RENEWAL", dit_wrf_RENEWAL);
                    portletPreferences.setValue("dit_wrf_DISABLEVOMS", dit_wrf_DISABLEVOMS);                
                    
                    portletPreferences.setValue("wrf_APPID", wrf_APPID.trim());
                    portletPreferences.setValue("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
                    portletPreferences.setValue("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
                    portletPreferences.setValue("wrf_SOFTWARE", wrf_SOFTWARE.trim());
                    portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                    portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                    portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n\nPROCESS ACTION => " + action
                        + "\n- Storing the  portlet preferences ..."
                        + "\ndit_wrf_INFRASTRUCTURE: " + dit_wrf_INFRASTRUCTURE
                        + "\ndit_wrf_LOGIN: " + dit_wrf_LOGIN
                        + "\ndit_wrf_PASSWD: " + dit_wrf_PASSWD                    
                        + "\ndit_wrf_ETOKENSERVER: " + dit_wrf_ETOKENSERVER
                        + "\ndit_wrf_MYPROXYSERVER: " + dit_wrf_MYPROXYSERVER
                        + "\ndit_wrf_PORT: " + dit_wrf_PORT
                        + "\ndit_wrf_ROBOTID: " + dit_wrf_ROBOTID
                        + "\ndit_wrf_ROLE: " + dit_wrf_ROLE
                        + "\ndit_wrf_RENEWAL: " + dit_wrf_RENEWAL
                        + "\ndit_wrf_DISABLEVOMS: " + dit_wrf_DISABLEVOMS
                            
                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + "dit"
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                }
                
                if (garuda_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
                {
                    infras[1]="garuda";
                    // Get the  INFRASTRUCTURE from the portlet request for the GARUDA VO
                    String garuda_wrf_INFRASTRUCTURE = request.getParameter("garuda_wrf_INFRASTRUCTURE");
                    // Get the  VONAME from the portlet request for the GARUDA VO
                    String garuda_wrf_VONAME = request.getParameter("garuda_wrf_VONAME");
                    // Get the  TOPBDII from the portlet request for the GARUDA VO
                    String garuda_wrf_TOPBDII = request.getParameter("garuda_wrf_TOPBDII");
                    // Get the  WMS from the portlet request for the GARUDA VO
                    String[] garuda_wrf_WMS = request.getParameterValues("garuda_wrf_WMS");
                    // Get the  ETOKENSERVER from the portlet request for the GARUDA VO
                    String garuda_wrf_ETOKENSERVER = request.getParameter("garuda_wrf_ETOKENSERVER");
                    // Get the  MYPROXYSERVER from the portlet request for the GARUDA VO
                    String garuda_wrf_MYPROXYSERVER = request.getParameter("garuda_wrf_MYPROXYSERVER");
                    // Get the  PORT from the portlet request for the GARUDA VO
                    String garuda_wrf_PORT = request.getParameter("garuda_wrf_PORT");
                    // Get the  ROBOTID from the portlet request for the GARUDA VO
                    String garuda_wrf_ROBOTID = request.getParameter("garuda_wrf_ROBOTID");
                    // Get the  ROLE from the portlet request for the GARUDA VO
                    String garuda_wrf_ROLE = request.getParameter("garuda_wrf_ROLE");
                    // Get the  OPTIONS from the portlet request for the GARUDA VO
                    String[] garuda_wrf_OPTIONS = request.getParameterValues("garuda_wrf_OPTIONS"); 

                    String garuda_wrf_RENEWAL = "";
                    String garuda_wrf_DISABLEVOMS = "";

                    if (garuda_wrf_OPTIONS == null){
                        garuda_wrf_RENEWAL = "checked";
                        garuda_wrf_DISABLEVOMS = "unchecked";
                    } else {
                        Arrays.sort(garuda_wrf_OPTIONS);
                        // Get the  RENEWAL from the portlet preferences for the GARUDA VO
                        garuda_wrf_RENEWAL = Arrays.binarySearch(garuda_wrf_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the  DISABLEVOMS from the portlet preferences for the GARUDA VO
                        garuda_wrf_DISABLEVOMS = Arrays.binarySearch(garuda_wrf_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                    }
                    
                    int nmax=0;                
                    for (int i = 0; i < garuda_wrf_WMS.length; i++)
                        if ( garuda_wrf_WMS[i]!=null && (!garuda_wrf_WMS[i].trim().equals("N/A")) )                        
                            nmax++;
                    
                    log.info("\n\nLength="+nmax);
                    String[] garuda_wrf_WMS_trimmed = new String[nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        garuda_wrf_WMS_trimmed[i]=garuda_wrf_WMS[i].trim();
                        log.info ("\n\nGARUDA [" + i + "] WSGRAM=[" + garuda_wrf_WMS_trimmed[i] + "]");
                    }
                    
                    // Set the portlet preferences
                    portletPreferences.setValue("garuda_wrf_INFRASTRUCTURE", garuda_wrf_INFRASTRUCTURE.trim());
                    portletPreferences.setValue("garuda_wrf_VONAME", garuda_wrf_VONAME.trim());
                    portletPreferences.setValue("garuda_wrf_TOPBDII", garuda_wrf_TOPBDII.trim());
                    portletPreferences.setValues("garuda_wrf_WMS", garuda_wrf_WMS_trimmed);
                    portletPreferences.setValue("garuda_wrf_ETOKENSERVER", garuda_wrf_ETOKENSERVER.trim());
                    portletPreferences.setValue("garuda_wrf_MYPROXYSERVER", garuda_wrf_MYPROXYSERVER.trim());
                    portletPreferences.setValue("garuda_wrf_PORT", garuda_wrf_PORT.trim());
                    portletPreferences.setValue("garuda_wrf_ROBOTID", garuda_wrf_ROBOTID.trim());
                    portletPreferences.setValue("garuda_wrf_ROLE", garuda_wrf_ROLE.trim());
                    portletPreferences.setValue("garuda_wrf_RENEWAL", garuda_wrf_RENEWAL);
                    portletPreferences.setValue("garuda_wrf_DISABLEVOMS", garuda_wrf_DISABLEVOMS);
                    
                    portletPreferences.setValue("wrf_APPID", wrf_APPID.trim());
                    portletPreferences.setValue("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
                    portletPreferences.setValue("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
                    portletPreferences.setValue("wrf_SOFTWARE", wrf_SOFTWARE.trim());
                    portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                    portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                    portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n\nPROCESS ACTION => " + action
                        + "\n- Storing the  portlet preferences ..."
                        + "\ngaruda_wrf_INFRASTRUCTURE: " + garuda_wrf_INFRASTRUCTURE
                        + "\ngaruda_wrf_VONAME: " + garuda_wrf_VONAME
                        + "\ngaruda_wrf_TOPBDII: " + garuda_wrf_TOPBDII                    
                        + "\ngaruda_wrf_ETOKENSERVER: " + garuda_wrf_ETOKENSERVER
                        + "\ngaruda_wrf_MYPROXYSERVER: " + garuda_wrf_MYPROXYSERVER
                        + "\ngaruda_wrf_PORT: " + garuda_wrf_PORT
                        + "\ngaruda_wrf_ROBOTID: " + garuda_wrf_ROBOTID
                        + "\ngaruda_wrf_ROLE: " + garuda_wrf_ROLE
                        + "\ngaruda_wrf_RENEWAL: " + garuda_wrf_RENEWAL
                        + "\ngaruda_wrf_DISABLEVOMS: " + garuda_wrf_DISABLEVOMS
                                
                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + "garuda"
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                }

                if (chain_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
                {
                    infras[2]="chain";
                    // Get the  INFRASTRUCTURE from the portlet request for the CHAIN VO
                    String chain_wrf_INFRASTRUCTURE = request.getParameter("chain_wrf_INFRASTRUCTURE");
                    // Get the  VONAME from the portlet request for the CHAIN VO
                    String chain_wrf_VONAME = request.getParameter("chain_wrf_VONAME");
                    // Get the  TOPBDII from the portlet request for the CHAIN VO
                    String chain_wrf_TOPBDII = request.getParameter("chain_wrf_TOPBDII");
                    // Get the  WMS from the portlet request for the CHAIN VO
                    String[] chain_wrf_WMS = request.getParameterValues("chain_wrf_WMS");
                    // Get the  ETOKENSERVER from the portlet request for the CHAIN VO
                    String chain_wrf_ETOKENSERVER = request.getParameter("chain_wrf_ETOKENSERVER");
                    // Get the  MYPROXYSERVER from the portlet request for the CHAIN VO
                    String chain_wrf_MYPROXYSERVER = request.getParameter("chain_wrf_MYPROXYSERVER");
                    // Get the  PORT from the portlet request for the CHAIN VO
                    String chain_wrf_PORT = request.getParameter("chain_wrf_PORT");
                    // Get the  ROBOTID from the portlet request for the CHAIN VO
                    String chain_wrf_ROBOTID = request.getParameter("chain_wrf_ROBOTID");
                    // Get the  ROLE from the portlet request for the CHAIN VO
                    String chain_wrf_ROLE = request.getParameter("chain_wrf_ROLE");
                    // Get the  OPTIONS from the portlet request for the CHAIN VO
                    String[] chain_wrf_OPTIONS = request.getParameterValues("chain_wrf_OPTIONS");                

                    String chain_wrf_RENEWAL = "";
                    String chain_wrf_DISABLEVOMS = "";

                    if (chain_wrf_OPTIONS == null){
                        chain_wrf_RENEWAL = "checked";
                        chain_wrf_DISABLEVOMS = "unchecked";
                    } else {
                        Arrays.sort(chain_wrf_OPTIONS);
                        // Get the  RENEWAL from the portlet preferences for the CHAIN VO
                        chain_wrf_RENEWAL = Arrays.binarySearch(chain_wrf_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the  DISABLEVOMS from the portlet preferences for the CHAIN VO
                        chain_wrf_DISABLEVOMS = Arrays.binarySearch(chain_wrf_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                    }
                    
                    int nmax=0;                
                    for (int i = 0; i < chain_wrf_WMS.length; i++)
                        if ( chain_wrf_WMS[i]!=null && (!chain_wrf_WMS[i].trim().equals("N/A")) )                        
                            nmax++;
                    
                    log.info("\n\nLength="+nmax);
                    String[] chain_wrf_WMS_trimmed = new String[nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        chain_wrf_WMS_trimmed[i]=chain_wrf_WMS[i].trim();
                        log.info ("\n\nCHAIN [" + i + "] WMS=[" + chain_wrf_WMS_trimmed[i] + "]");
                    }
                    
                    // Set the portlet preferences
                    portletPreferences.setValue("chain_wrf_INFRASTRUCTURE", chain_wrf_INFRASTRUCTURE.trim());
                    portletPreferences.setValue("chain_wrf_VONAME", chain_wrf_VONAME.trim());
                    portletPreferences.setValue("chain_wrf_TOPBDII", chain_wrf_TOPBDII.trim());
                    portletPreferences.setValues("chain_wrf_WMS", chain_wrf_WMS_trimmed);
                    portletPreferences.setValue("chain_wrf_ETOKENSERVER", chain_wrf_ETOKENSERVER.trim());
                    portletPreferences.setValue("chain_wrf_MYPROXYSERVER", chain_wrf_MYPROXYSERVER.trim());
                    portletPreferences.setValue("chain_wrf_PORT", chain_wrf_PORT.trim());
                    portletPreferences.setValue("chain_wrf_ROBOTID", chain_wrf_ROBOTID.trim());
                    portletPreferences.setValue("chain_wrf_ROLE", chain_wrf_ROLE.trim());
                    portletPreferences.setValue("chain_wrf_RENEWAL", chain_wrf_RENEWAL);
                    portletPreferences.setValue("chain_wrf_DISABLEVOMS", chain_wrf_DISABLEVOMS);                
                    
                    portletPreferences.setValue("wrf_APPID", wrf_APPID.trim());
                    portletPreferences.setValue("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
                    portletPreferences.setValue("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
                    portletPreferences.setValue("wrf_SOFTWARE", wrf_SOFTWARE.trim());
                    portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                    portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                    portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n\nPROCESS ACTION => " + action
                        + "\n- Storing the  portlet preferences ..."
                        + "\nchain_wrf_INFRASTRUCTURE: " + chain_wrf_INFRASTRUCTURE
                        + "\nchain_wrf_VONAME: " + chain_wrf_VONAME
                        + "\nchain_wrf_TOPBDII: " + chain_wrf_TOPBDII                    
                        + "\nchain_wrf_ETOKENSERVER: " + chain_wrf_ETOKENSERVER
                        + "\nchain_wrf_MYPROXYSERVER: " + chain_wrf_MYPROXYSERVER
                        + "\nchain_wrf_PORT: " + chain_wrf_PORT
                        + "\nchain_wrf_ROBOTID: " + chain_wrf_ROBOTID
                        + "\nchain_wrf_ROLE: " + chain_wrf_ROLE
                        + "\nchain_wrf_RENEWAL: " + chain_wrf_RENEWAL
                        + "\nchain_wrf_DISABLEVOMS: " + chain_wrf_DISABLEVOMS
                            
                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + "chain"
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                }
                
                if (fedcloud_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
                {
                    infras[3]="fedcloud";
                    // Get the  INFRASTRUCTURE from the portlet request for the FEDCLOUD VO
                    String fedcloud_wrf_INFRASTRUCTURE = request.getParameter("fedcloud_wrf_INFRASTRUCTURE");
                    // Get the  VONAME from the portlet request for the FEDCLOUD VO
                    String fedcloud_wrf_VONAME = request.getParameter("fedcloud_wrf_VONAME");
                    // Get the  TOPBDII from the portlet request for the FEDCLOUD VO
                    String fedcloud_wrf_TOPBDII = request.getParameter("fedcloud_wrf_TOPBDII");
                    // Get the  WMS from the portlet request for the FEDCLOUD VO
                    String[] fedcloud_wrf_WMS = request.getParameterValues("fedcloud_wrf_WMS");
                    // Get the  ETOKENSERVER from the portlet request for the FEDCLOUD VO
                    String fedcloud_wrf_ETOKENSERVER = request.getParameter("fedcloud_wrf_ETOKENSERVER");
                    // Get the  MYPROXYSERVER from the portlet request for the FEDCLOUD VO
                    String fedcloud_wrf_MYPROXYSERVER = request.getParameter("fedcloud_wrf_MYPROXYSERVER");
                    // Get the  PORT from the portlet request for the FEDCLOUD VO
                    String fedcloud_wrf_PORT = request.getParameter("fedcloud_wrf_PORT");
                    // Get the  ROBOTID from the portlet request for the FEDCLOUD VO
                    String fedcloud_wrf_ROBOTID = request.getParameter("fedcloud_wrf_ROBOTID");
                    // Get the  ROLE from the portlet request for the FEDCLOUD VO
                    String fedcloud_wrf_ROLE = request.getParameter("fedcloud_wrf_ROLE");
                    // Get the  OPTIONS from the portlet request for the FEDCLOUD VO
                    String[] fedcloud_wrf_OPTIONS = request.getParameterValues("fedcloud_wrf_OPTIONS");                

                    String fedcloud_wrf_RENEWAL = "";
                    String fedcloud_wrf_DISABLEVOMS = "";

                    if (fedcloud_wrf_OPTIONS == null){
                        fedcloud_wrf_RENEWAL = "checked";
                        fedcloud_wrf_DISABLEVOMS = "unchecked";
                    } else {
                        Arrays.sort(fedcloud_wrf_OPTIONS);
                        // Get the  RENEWAL from the portlet preferences for the FEDCLOUD VO
                        fedcloud_wrf_RENEWAL = Arrays.binarySearch(fedcloud_wrf_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the  DISABLEVOMS from the portlet preferences for the FEDCLOUD VO
                        fedcloud_wrf_DISABLEVOMS = Arrays.binarySearch(fedcloud_wrf_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                    }
                    
                    int nmax=0;                
                    for (int i = 0; i < fedcloud_wrf_WMS.length; i++)
                        if ( fedcloud_wrf_WMS[i]!=null && (!fedcloud_wrf_WMS[i].trim().equals("N/A")) )                        
                            nmax++;
                    
                    log.info("\n\nLength="+nmax);
                    String[] fedcloud_wrf_WMS_trimmed = new String[nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        fedcloud_wrf_WMS_trimmed[i]=fedcloud_wrf_WMS[i].trim();
                        log.info ("\n\nFEDCLOUD [" + i + "] Cloud Resource=[" + fedcloud_wrf_WMS_trimmed[i] + "]");
                    }
                    
                    // Set the portlet preferences
                    portletPreferences.setValue("fedcloud_wrf_INFRASTRUCTURE", fedcloud_wrf_INFRASTRUCTURE.trim());
                    portletPreferences.setValue("fedcloud_wrf_VONAME", fedcloud_wrf_VONAME.trim());
                    portletPreferences.setValue("fedcloud_wrf_TOPBDII", fedcloud_wrf_TOPBDII.trim());
                    portletPreferences.setValues("fedcloud_wrf_WMS", fedcloud_wrf_WMS_trimmed);
                    portletPreferences.setValue("fedcloud_wrf_ETOKENSERVER", fedcloud_wrf_ETOKENSERVER.trim());
                    portletPreferences.setValue("fedcloud_wrf_MYPROXYSERVER", fedcloud_wrf_MYPROXYSERVER.trim());
                    portletPreferences.setValue("fedcloud_wrf_PORT", fedcloud_wrf_PORT.trim());
                    portletPreferences.setValue("fedcloud_wrf_ROBOTID", fedcloud_wrf_ROBOTID.trim());
                    portletPreferences.setValue("fedcloud_wrf_ROLE", fedcloud_wrf_ROLE.trim());
                    portletPreferences.setValue("fedcloud_wrf_RENEWAL", fedcloud_wrf_RENEWAL);
                    portletPreferences.setValue("fedcloud_wrf_DISABLEVOMS", fedcloud_wrf_DISABLEVOMS);
                    
                    portletPreferences.setValue("wrf_APPID", wrf_APPID.trim());
                    portletPreferences.setValue("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
                    portletPreferences.setValue("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
                    portletPreferences.setValue("wrf_SOFTWARE", wrf_SOFTWARE.trim());
                    portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                    portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                    portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n\nPROCESS ACTION => " + action
                        + "\n- Storing the  portlet preferences ..."                    
                        + "\n\nfedcloud_wrf_INFRASTRUCTURE: " + fedcloud_wrf_INFRASTRUCTURE
                        + "\nfedcloud_wrf_VONAME: " + fedcloud_wrf_VONAME
                        + "\nfedcloud_wrf_TOPBDII: " + fedcloud_wrf_TOPBDII                    
                        + "\nfedcloud_wrf_ETOKENSERVER: " + fedcloud_wrf_ETOKENSERVER
                        + "\nfedcloud_wrf_MYPROXYSERVER: " + fedcloud_wrf_MYPROXYSERVER
                        + "\nfedcloud_wrf_PORT: " + fedcloud_wrf_PORT
                        + "\nfedcloud_wrf_ROBOTID: " + fedcloud_wrf_ROBOTID
                        + "\nfedcloud_wrf_ROLE: " + fedcloud_wrf_ROLE
                        + "\nfedcloud_wrf_RENEWAL: " + fedcloud_wrf_RENEWAL
                        + "\nfedcloud_wrf_DISABLEVOMS: " + fedcloud_wrf_DISABLEVOMS

                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + "fedcloud"
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                }

                if (eumed_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
                {
                    infras[4]="eumed";
                    // Get the  INFRASTRUCTURE from the portlet request for the EUMED VO
                    String eumed_wrf_INFRASTRUCTURE = request.getParameter("eumed_wrf_INFRASTRUCTURE");
                    // Get the  VONAME from the portlet request for the EUMED VO
                    String eumed_wrf_VONAME = request.getParameter("eumed_wrf_VONAME");
                    // Get the  TOPBDII from the portlet request for the EUMED VO
                    String eumed_wrf_TOPBDII = request.getParameter("eumed_wrf_TOPBDII");
                    // Get the  WMS from the portlet request for the EUMED VO
                    String[] eumed_wrf_WMS = request.getParameterValues("eumed_wrf_WMS");
                    // Get the  ETOKENSERVER from the portlet request for the EUMED VO
                    String eumed_wrf_ETOKENSERVER = request.getParameter("eumed_wrf_ETOKENSERVER");
                    // Get the  MYPROXYSERVER from the portlet request for the EUMED VO
                    String eumed_wrf_MYPROXYSERVER = request.getParameter("eumed_wrf_MYPROXYSERVER");
                    // Get the  PORT from the portlet request for the EUMED VO
                    String eumed_wrf_PORT = request.getParameter("eumed_wrf_PORT");
                    // Get the  ROBOTID from the portlet request for the EUMED VO
                    String eumed_wrf_ROBOTID = request.getParameter("eumed_wrf_ROBOTID");
                    // Get the  ROLE from the portlet request for the EUMED VO
                    String eumed_wrf_ROLE = request.getParameter("eumed_wrf_ROLE");
                    // Get the  OPTIONS from the portlet request for the EUMED VO
                    String[] eumed_wrf_OPTIONS = request.getParameterValues("eumed_wrf_OPTIONS");                

                    String eumed_wrf_RENEWAL = "";
                    String eumed_wrf_DISABLEVOMS = "";

                    if (eumed_wrf_OPTIONS == null){
                        eumed_wrf_RENEWAL = "checked";
                        eumed_wrf_DISABLEVOMS = "unchecked";
                    } else {
                        Arrays.sort(eumed_wrf_OPTIONS);
                        // Get the  RENEWAL from the portlet preferences for the EUMED VO
                        eumed_wrf_RENEWAL = Arrays.binarySearch(eumed_wrf_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the  DISABLEVOMS from the portlet preferences for the CHAIN VO
                        eumed_wrf_DISABLEVOMS = Arrays.binarySearch(eumed_wrf_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                    }
                    
                    int nmax=0;                
                    for (int i = 0; i < eumed_wrf_WMS.length; i++)
                        if ( eumed_wrf_WMS[i]!=null && (!eumed_wrf_WMS[i].trim().equals("N/A")) )                        
                            nmax++;
                    
                    log.info("\n\nLength="+nmax);
                    String[] eumed_wrf_WMS_trimmed = new String[nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        eumed_wrf_WMS_trimmed[i]=eumed_wrf_WMS[i].trim();
                        log.info ("\n\nEUMED [" + i + "] WMS=[" + eumed_wrf_WMS_trimmed[i] + "]");
                    }
                    
                    // Set the portlet preferences
                    portletPreferences.setValue("eumed_wrf_INFRASTRUCTURE", eumed_wrf_INFRASTRUCTURE.trim());
                    portletPreferences.setValue("eumed_wrf_VONAME", eumed_wrf_VONAME.trim());
                    portletPreferences.setValue("eumed_wrf_TOPBDII", eumed_wrf_TOPBDII.trim());
                    portletPreferences.setValues("eumed_wrf_WMS", eumed_wrf_WMS_trimmed);
                    portletPreferences.setValue("eumed_wrf_ETOKENSERVER", eumed_wrf_ETOKENSERVER.trim());
                    portletPreferences.setValue("eumed_wrf_MYPROXYSERVER", eumed_wrf_MYPROXYSERVER.trim());
                    portletPreferences.setValue("eumed_wrf_PORT", eumed_wrf_PORT.trim());
                    portletPreferences.setValue("eumed_wrf_ROBOTID", eumed_wrf_ROBOTID.trim());
                    portletPreferences.setValue("eumed_wrf_ROLE", eumed_wrf_ROLE.trim());
                    portletPreferences.setValue("eumed_wrf_RENEWAL", eumed_wrf_RENEWAL);
                    portletPreferences.setValue("eumed_wrf_DISABLEVOMS", eumed_wrf_DISABLEVOMS); 
                    
                    portletPreferences.setValue("wrf_APPID", wrf_APPID.trim());
                    portletPreferences.setValue("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
                    portletPreferences.setValue("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
                    portletPreferences.setValue("wrf_SOFTWARE", wrf_SOFTWARE.trim());
                    portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                    portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                    portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n\nPROCESS ACTION => " + action
                        + "\n- Storing the  portlet preferences ..."                    
                        + "\n\neumed_wrf_INFRASTRUCTURE: " + eumed_wrf_INFRASTRUCTURE
                        + "\neumed_wrf_VONAME: " + eumed_wrf_VONAME
                        + "\neumed_wrf_TOPBDII: " + eumed_wrf_TOPBDII                    
                        + "\neumed_wrf_ETOKENSERVER: " + eumed_wrf_ETOKENSERVER
                        + "\neumed_wrf_MYPROXYSERVER: " + eumed_wrf_MYPROXYSERVER
                        + "\neumed_wrf_PORT: " + eumed_wrf_PORT
                        + "\neumed_wrf_ROBOTID: " + eumed_wrf_ROBOTID
                        + "\neumed_wrf_ROLE: " + eumed_wrf_ROLE
                        + "\neumed_wrf_RENEWAL: " + eumed_wrf_RENEWAL
                        + "\neumed_wrf_DISABLEVOMS: " + eumed_wrf_DISABLEVOMS

                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + "eumed"
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                }

                if (gisela_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
                {
                    infras[5]="gisela";
                    // Get the  INFRASTRUCTURE from the portlet request for the GISELA VO
                    String gisela_wrf_INFRASTRUCTURE = request.getParameter("gisela_wrf_INFRASTRUCTURE");
                    // Get the  VONAME from the portlet request for the GISELA VO
                    String gisela_wrf_VONAME = request.getParameter("gisela_wrf_VONAME");
                    // Get the  TOPBDII from the portlet request for the GISELA VO
                    String gisela_wrf_TOPBDII = request.getParameter("gisela_wrf_TOPBDII");
                    // Get the  WMS from the portlet request for the GISELA VO
                    String[] gisela_wrf_WMS = request.getParameterValues("gisela_wrf_WMS");
                    // Get the  ETOKENSERVER from the portlet request for the GISELA VO
                    String gisela_wrf_ETOKENSERVER = request.getParameter("gisela_wrf_ETOKENSERVER");
                    // Get the  MYPROXYSERVER from the portlet request for the GISELA VO
                    String gisela_wrf_MYPROXYSERVER = request.getParameter("gisela_wrf_MYPROXYSERVER");
                    // Get the  PORT from the portlet request for the GISELA VO
                    String gisela_wrf_PORT = request.getParameter("gisela_wrf_PORT");
                    // Get the  ROBOTID from the portlet request for the GISELA VO
                    String gisela_wrf_ROBOTID = request.getParameter("gisela_wrf_ROBOTID");
                    // Get the  ROLE from the portlet request for the GISELA VO
                    String gisela_wrf_ROLE = request.getParameter("gisela_wrf_ROLE");
                    // Get the  OPTIONS from the portlet request for the GISELA VO
                    String[] gisela_wrf_OPTIONS = request.getParameterValues("gisela_wrf_OPTIONS");                

                    String gisela_wrf_RENEWAL = "";
                    String gisela_wrf_DISABLEVOMS = "";

                    if (gisela_wrf_OPTIONS == null){
                        gisela_wrf_RENEWAL = "checked";
                        gisela_wrf_DISABLEVOMS = "unchecked";
                    } else {
                        Arrays.sort(gisela_wrf_OPTIONS);
                        // Get the  RENEWAL from the portlet preferences for the GISELA VO
                        gisela_wrf_RENEWAL = Arrays.binarySearch(gisela_wrf_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the  DISABLEVOMS from the portlet preferences for the GISELA VO
                        gisela_wrf_DISABLEVOMS = Arrays.binarySearch(gisela_wrf_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                    }                       

                    int nmax=0;                
                    for (int i = 0; i < gisela_wrf_WMS.length; i++)
                        if ( gisela_wrf_WMS[i]!=null && (!gisela_wrf_WMS[i].trim().equals("N/A")) )                        
                            nmax++;
                    
                    log.info("\n\nLength="+nmax);
                    String[] gisela_wrf_WMS_trimmed = new String[nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        gisela_wrf_WMS_trimmed[i]=gisela_wrf_WMS[i].trim();
                        log.info ("\n\nGISELA [" + i + "] WMS=[" + gisela_wrf_WMS_trimmed[i] + "]");
                    }

                    // Set the portlet preferences
                    portletPreferences.setValue("gisela_wrf_INFRASTRUCTURE", gisela_wrf_INFRASTRUCTURE.trim());
                    portletPreferences.setValue("gisela_wrf_VONAME", gisela_wrf_VONAME.trim());
                    portletPreferences.setValue("gisela_wrf_TOPBDII", gisela_wrf_TOPBDII.trim());
                    portletPreferences.setValues("gisela_wrf_WMS", gisela_wrf_WMS_trimmed);
                    portletPreferences.setValue("gisela_wrf_ETOKENSERVER", gisela_wrf_ETOKENSERVER.trim());
                    portletPreferences.setValue("gisela_wrf_MYPROXYSERVER", gisela_wrf_MYPROXYSERVER.trim());
                    portletPreferences.setValue("gisela_wrf_PORT", gisela_wrf_PORT.trim());
                    portletPreferences.setValue("gisela_wrf_ROBOTID", gisela_wrf_ROBOTID.trim());
                    portletPreferences.setValue("gisela_wrf_ROLE", gisela_wrf_ROLE.trim());
                    portletPreferences.setValue("gisela_wrf_RENEWAL", gisela_wrf_RENEWAL);
                    portletPreferences.setValue("gisela_wrf_DISABLEVOMS", gisela_wrf_DISABLEVOMS);
                    
                    portletPreferences.setValue("wrf_APPID", wrf_APPID.trim());
                    portletPreferences.setValue("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
                    portletPreferences.setValue("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
                    portletPreferences.setValue("wrf_SOFTWARE", wrf_SOFTWARE.trim());
                    portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                    portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                    portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n\nPROCESS ACTION => " + action
                        + "\n- Storing the  portlet preferences ..."                    
                        + "\n\ngisela_wrf_INFRASTRUCTURE: " + gisela_wrf_INFRASTRUCTURE
                        + "\ngisela_wrf_VONAME: " + gisela_wrf_VONAME
                        + "\ngisela_wrf_TOPBDII: " + gisela_wrf_TOPBDII                    
                        + "\ngisela_wrf_ETOKENSERVER: " + gisela_wrf_ETOKENSERVER
                        + "\ngisela_wrf_MYPROXYSERVER: " + gisela_wrf_MYPROXYSERVER
                        + "\ngisela_wrf_PORT: " + gisela_wrf_PORT
                        + "\ngisela_wrf_ROBOTID: " + gisela_wrf_ROBOTID
                        + "\ngisela_wrf_ROLE: " + gisela_wrf_ROLE
                        + "\ngisela_wrf_RENEWAL: " + gisela_wrf_RENEWAL
                        + "\ngisela_wrf_DISABLEVOMS: " + gisela_wrf_DISABLEVOMS

                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + "gisela"
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                }
                
                if (sagrid_wrf_ENABLEINFRASTRUCTURE.equals("checked"))
                {
                    infras[6]="sagrid";
                    // Get the  INFRASTRUCTURE from the portlet request for the SAGRID VO
                    String sagrid_wrf_INFRASTRUCTURE = request.getParameter("sagrid_wrf_INFRASTRUCTURE");
                    // Get the  VONAME from the portlet request for the SAGRID VO
                    String sagrid_wrf_VONAME = request.getParameter("sagrid_wrf_VONAME");
                    // Get the  TOPBDII from the portlet request for the SAGRID VO
                    String sagrid_wrf_TOPBDII = request.getParameter("sagrid_wrf_TOPBDII");
                    // Get the  WMS from the portlet request for the SAGRID VO
                    String[] sagrid_wrf_WMS = request.getParameterValues("sagrid_wrf_WMS");
                    // Get the  ETOKENSERVER from the portlet request for the SAGRID VO
                    String sagrid_wrf_ETOKENSERVER = request.getParameter("sagrid_wrf_ETOKENSERVER");
                    // Get the  MYPROXYSERVER from the portlet request for the SAGRID VO
                    String sagrid_wrf_MYPROXYSERVER = request.getParameter("sagrid_wrf_MYPROXYSERVER");
                    // Get the  PORT from the portlet request for the SAGRID VO
                    String sagrid_wrf_PORT = request.getParameter("sagrid_wrf_PORT");
                    // Get the  ROBOTID from the portlet request for the SAGRID VO
                    String sagrid_wrf_ROBOTID = request.getParameter("sagrid_wrf_ROBOTID");
                    // Get the  ROLE from the portlet request for the SAGRID VO
                    String sagrid_wrf_ROLE = request.getParameter("sagrid_wrf_ROLE");
                    // Get the  OPTIONS from the portlet request for the SAGRID VO
                    String[] sagrid_wrf_OPTIONS = request.getParameterValues("sagrid_wrf_OPTIONS");                

                    String sagrid_wrf_RENEWAL = "";
                    String sagrid_wrf_DISABLEVOMS = "";

                    if (sagrid_wrf_OPTIONS == null){
                        sagrid_wrf_RENEWAL = "checked";
                        sagrid_wrf_DISABLEVOMS = "unchecked";
                    } else {
                        Arrays.sort(sagrid_wrf_OPTIONS);
                        // Get the  RENEWAL from the portlet preferences for the SAGRID VO
                        sagrid_wrf_RENEWAL = Arrays.binarySearch(sagrid_wrf_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the  DISABLEVOMS from the portlet preferences for the SAGRID VO
                        sagrid_wrf_DISABLEVOMS = Arrays.binarySearch(sagrid_wrf_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                    }                       

                    int nmax=0;                
                    for (int i = 0; i < sagrid_wrf_WMS.length; i++)
                        if ( sagrid_wrf_WMS[i]!=null && (!sagrid_wrf_WMS[i].trim().equals("N/A")) )                        
                            nmax++;
                    
                    log.info("\n\nLength="+nmax);
                    String[] sagrid_wrf_WMS_trimmed = new String[nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        sagrid_wrf_WMS_trimmed[i]=sagrid_wrf_WMS[i].trim();
                        log.info ("\n\nSAGRID [" + i + "] WMS=[" + sagrid_wrf_WMS_trimmed[i] + "]");
                    }

                    // Set the portlet preferences
                    portletPreferences.setValue("sagrid_wrf_INFRASTRUCTURE", sagrid_wrf_INFRASTRUCTURE.trim());
                    portletPreferences.setValue("sagrid_wrf_VONAME", sagrid_wrf_VONAME.trim());
                    portletPreferences.setValue("sagrid_wrf_TOPBDII", sagrid_wrf_TOPBDII.trim());
                    portletPreferences.setValues("sagrid_wrf_WMS", sagrid_wrf_WMS_trimmed);
                    portletPreferences.setValue("sagrid_wrf_ETOKENSERVER", sagrid_wrf_ETOKENSERVER.trim());
                    portletPreferences.setValue("sagrid_wrf_MYPROXYSERVER", sagrid_wrf_MYPROXYSERVER.trim());
                    portletPreferences.setValue("sagrid_wrf_PORT", sagrid_wrf_PORT.trim());
                    portletPreferences.setValue("sagrid_wrf_ROBOTID", sagrid_wrf_ROBOTID.trim());
                    portletPreferences.setValue("sagrid_wrf_ROLE", sagrid_wrf_ROLE.trim());
                    portletPreferences.setValue("sagrid_wrf_RENEWAL", sagrid_wrf_RENEWAL);
                    portletPreferences.setValue("sagrid_wrf_DISABLEVOMS", sagrid_wrf_DISABLEVOMS);
                    
                    portletPreferences.setValue("wrf_APPID", wrf_APPID.trim());
                    portletPreferences.setValue("wrf_LOGLEVEL", wrf_LOGLEVEL.trim());
                    portletPreferences.setValue("wrf_OUTPUT_PATH", wrf_OUTPUT_PATH.trim());
                    portletPreferences.setValue("wrf_SOFTWARE", wrf_SOFTWARE.trim());
                    portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                    portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                    portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                    portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n\nPROCESS ACTION => " + action
                        + "\n- Storing the  portlet preferences ..."                    
                        + "\n\nsagrid_wrf_INFRASTRUCTURE: " + sagrid_wrf_INFRASTRUCTURE
                        + "\nsagrid_wrf_VONAME: " + sagrid_wrf_VONAME
                        + "\nsagrid_wrf_TOPBDII: " + sagrid_wrf_TOPBDII                    
                        + "\nsagrid_wrf_ETOKENSERVER: " + sagrid_wrf_ETOKENSERVER
                        + "\nsagrid_wrf_MYPROXYSERVER: " + sagrid_wrf_MYPROXYSERVER
                        + "\nsagrid_wrf_PORT: " + sagrid_wrf_PORT
                        + "\nsagrid_wrf_ROBOTID: " + sagrid_wrf_ROBOTID
                        + "\nsagrid_wrf_ROLE: " + sagrid_wrf_ROLE
                        + "\nsagrid_wrf_RENEWAL: " + sagrid_wrf_RENEWAL
                        + "\nsagrid_wrf_DISABLEVOMS: " + sagrid_wrf_DISABLEVOMS

                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + "sagrid"
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                }
                
                for (int i=0; i<infras.length; i++)
                    if (infras[i]!=null) 
                        log.info("\n - Infrastructure Enabled = " + infras[i]);           
                
                portletPreferences.setValues("wrf_ENABLEINFRASTRUCTURE", infras);
                portletPreferences.setValue("dit_wrf_ENABLEINFRASTRUCTURE",infras[0]);
                portletPreferences.setValue("garuda_wrf_ENABLEINFRASTRUCTURE",infras[1]);
                portletPreferences.setValue("chain_wrf_ENABLEINFRASTRUCTURE",infras[2]);
                portletPreferences.setValue("fedcloud_wrf_ENABLEINFRASTRUCTURE",infras[3]);
                portletPreferences.setValue("eumed_wrf_ENABLEINFRASTRUCTURE",infras[4]);
                portletPreferences.setValue("gisela_wrf_ENABLEINFRASTRUCTURE",infras[5]);
                portletPreferences.setValue("sagrid_wrf_ENABLEINFRASTRUCTURE",infras[6]);

                portletPreferences.store();
                response.setPortletMode(PortletMode.VIEW);
            } // end PROCESS ACTION [ CONFIG_WRF_PORTLET ]
            

            if (action.equals("SUBMIT_WRF_PORTLET")) {
                log.info("\nPROCESS ACTION => " + action); 
                
                InfrastructureInfo infrastructures[] = new InfrastructureInfo[7];
                int NMAX=0;           
                String wmsList[] = new String [10];
                String _wmsListFedCloud[] = new String [10];                
                String fedcloud_wrf_ETOKENSERVER = "";
                String fedcloud_wrf_PORT = "";
                String fedcloud_wrf_ROBOTID = "";
                String fedcloud_wrf_VONAME = "";
                String fedcloud_wrf_ROLE = "";
                
                String _wmsListChainCloud[] = new String [10];
                String chain_wrf_ETOKENSERVER = "";
                String chain_wrf_PORT = "";
                String chain_wrf_ROBOTID = "";
                String chain_wrf_VONAME = "";
                String chain_wrf_ROLE = "";

                // Get the APPID from the portlet preferences
                String wrf_APPID = portletPreferences.getValue("wrf_APPID", "N/A");
                // Get the LOGLEVEL from the portlet preferences
                String wrf_LOGLEVEL = portletPreferences.getValue("wrf_LOGLEVEL", "INFO");
                // Get the APPID from the portlet preferences
                String wrf_OUTPUT_PATH = portletPreferences.getValue("wrf_OUTPUT_PATH", "/tmp");
                // Get the SOFTWARE from the portlet preferences
                String wrf_SOFTWARE = portletPreferences.getValue("wrf_SOFTWARE", "N/A");
                // Get the TRACKING_DB_HOSTNAME from the portlet request
                String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
                // Get the TRACKING_DB_USERNAME from the portlet request
                String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
                // Get the TRACKING_DB_PASSWORD from the portlet request
                String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD","N/A");
                // Get the SMTP_HOST from the portlet request
                String SMTP_HOST = portletPreferences.getValue("SMTP_HOST","N/A");
                // Get the SENDER_MAIL from the portlet request
                String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL","N/A");        
                
                String dit_wrf_ENABLEINFRASTRUCTURE =
                        portletPreferences.getValue("dit_wrf_ENABLEINFRASTRUCTURE","null");
                String garuda_wrf_ENABLEINFRASTRUCTURE =
                        portletPreferences.getValue("garuda_wrf_ENABLEINFRASTRUCTURE","null");
                String chain_wrf_ENABLEINFRASTRUCTURE =
                        portletPreferences.getValue("chain_wrf_ENABLEINFRASTRUCTURE","null");
                String fedcloud_wrf_ENABLEINFRASTRUCTURE =
                        portletPreferences.getValue("fedcloud_wrf_ENABLEINFRASTRUCTURE","null");
                String eumed_wrf_ENABLEINFRASTRUCTURE =
                        portletPreferences.getValue("eumed_wrf_ENABLEINFRASTRUCTURE","null");
                String gisela_wrf_ENABLEINFRASTRUCTURE =
                        portletPreferences.getValue("gisela_wrf_ENABLEINFRASTRUCTURE","null");
                String sagrid_wrf_ENABLEINFRASTRUCTURE =
                        portletPreferences.getValue("sagrid_wrf_ENABLEINFRASTRUCTURE","null");
            
                if (dit_wrf_ENABLEINFRASTRUCTURE != null &&
                    dit_wrf_ENABLEINFRASTRUCTURE.equals("dit"))
                {                
                    NMAX++;                
                    // Get the  VONAME from the portlet preferences for the DIT VO
                    String dit_wrf_INFRASTRUCTURE = portletPreferences.getValue("dit_wrf_INFRASTRUCTURE", "N/A");
                    // Get the  VONAME from the portlet preferences for the DIT VO
                    String dit_wrf_LOGIN = portletPreferences.getValue("dit_wrf_LOGIN", "N/A");
                    // Get the  TOPPBDII from the portlet preferences for the DIT VO
                    String dit_wrf_PASSWD = portletPreferences.getValue("dit_wrf_PASSWD", "N/A");
                    // Get the  WMS from the portlet preferences for the DIT VO                
                    String[] dit_wrf_WMS = portletPreferences.getValues("dit_wrf_WMS", new String[5]);
                    // Get the  ETOKENSERVER from the portlet preferences for the DIT VO
                    String dit_wrf_ETOKENSERVER = portletPreferences.getValue("dit_wrf_ETOKENSERVER", "N/A");
                    // Get the  MYPROXYSERVER from the portlet preferences for the DIT VO
                    String dit_wrf_MYPROXYSERVER = portletPreferences.getValue("dit_wrf_MYPROXYSERVER", "N/A");
                    // Get the  PORT from the portlet preferences for the DIT VO
                    String dit_wrf_PORT = portletPreferences.getValue("dit_wrf_PORT", "N/A");
                    // Get the  ROBOTID from the portlet preferences for the DIT VO
                    String dit_wrf_ROBOTID = portletPreferences.getValue("dit_wrf_ROBOTID", "N/A");
                    // Get the  ROLE from the portlet preferences for the DIT VO
                    String dit_wrf_ROLE = portletPreferences.getValue("dit_wrf_ROLE", "N/A");
                    // Get the  RENEWAL from the portlet preferences for the DIT VO
                    String dit_wrf_RENEWAL = portletPreferences.getValue("dit_wrf_RENEWAL", "checked");
                    // Get the  DISABLEVOMS from the portlet preferences for the DIT VO
                    String dit_wrf_DISABLEVOMS = portletPreferences.getValue("dit_wrf_DISABLEVOMS", "unchecked");
                    // Get the random CE for the Sonification portlet               
                    //RANDOM_CE = getRandomCE(dit_wrf_LOGIN, dit_wrf_PASSWD, wrf_SOFTWARE);
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n- Getting the  portlet preferences ..."
                        + "\ndit_wrf_INFRASTRUCTURE: " + dit_wrf_INFRASTRUCTURE
                        + "\ndit_wrf_LOGIN: " + dit_wrf_LOGIN
                        + "\ndit_wrf_PASSWD: " + dit_wrf_PASSWD                    
                        + "\ndit_wrf_ETOKENSERVER: " + dit_wrf_ETOKENSERVER
                        + "\ndit_wrf_MYPROXYSERVER: " + dit_wrf_MYPROXYSERVER
                        + "\ndit_wrf_PORT: " + dit_wrf_PORT
                        + "\ndit_wrf_ROBOTID: " + dit_wrf_ROBOTID
                        + "\ndit_wrf_ROLE: " + dit_wrf_ROLE
                        + "\ndit_wrf_RENEWAL: " + dit_wrf_RENEWAL
                        + "\ndit_wrf_DISABLEVOMS: " + dit_wrf_DISABLEVOMS
                     
                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + dit_wrf_ENABLEINFRASTRUCTURE
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                    
                    // Defining the WMS list for the "DIT" Infrastructure
                    int nmax=0;
                    for (int i = 0; i < dit_wrf_WMS.length; i++)
                        if ((dit_wrf_WMS[i]!=null) && (!dit_wrf_WMS[i].equals("N/A"))) nmax++;

                    String dit_wmsList[] = new String [nmax];                
                    for (int i = 0; i < nmax; i++)
                    {
                        if (dit_wrf_WMS[i]!=null) {
                        dit_wmsList[i]=dit_wrf_WMS[i].trim();
                        log.info ("\n\n[" + nmax
                                          + "] Submitting to DIT ["
                                          + i
                                          + "] using WMS=["
                                          + dit_wmsList[i]
                                          + "]");
                        }
                    }

                    infrastructures[0] = new InfrastructureInfo(
                        "SSH",
                        "ssh",
                        dit_wrf_LOGIN,
                        dit_wrf_PASSWD,
                        dit_wmsList);
                }
                
                if (garuda_wrf_ENABLEINFRASTRUCTURE != null &&
                    garuda_wrf_ENABLEINFRASTRUCTURE.equals("garuda"))
                {
                    NMAX++;                
                    // Get the  VONAME from the portlet preferences for the GARUDA VO
                    String garuda_wrf_INFRASTRUCTURE = portletPreferences.getValue("garuda_wrf_INFRASTRUCTURE", "N/A");
                    // Get the  VONAME from the portlet preferences for the GARUDA VO
                    String garuda_wrf_VONAME = portletPreferences.getValue("garuda_wrf_VONAME", "N/A");
                    // Get the  TOPPBDII from the portlet preferences for the GARUDA VO
                    String garuda_wrf_TOPBDII = portletPreferences.getValue("garuda_wrf_TOPBDII", "N/A");
                    // Get the  WMS from the portlet preferences for the GARUDA VO                
                    String[] garuda_wrf_WMS = portletPreferences.getValues("garuda_wrf_WMS", new String[5]);
                    // Get the  ETOKENSERVER from the portlet preferences for the GARUDA VO
                    String garuda_wrf_ETOKENSERVER = portletPreferences.getValue("garuda_wrf_ETOKENSERVER", "N/A");
                    // Get the  MYPROXYSERVER from the portlet preferences for the GARUDA VO
                    String garuda_wrf_MYPROXYSERVER = portletPreferences.getValue("garuda_wrf_MYPROXYSERVER", "N/A");
                    // Get the  PORT from the portlet preferences for the GARUDA VO
                    String garuda_wrf_PORT = portletPreferences.getValue("garuda_wrf_PORT", "N/A");
                    // Get the  ROBOTID from the portlet preferences for the GARUDA VO
                    String garuda_wrf_ROBOTID = portletPreferences.getValue("garuda_wrf_ROBOTID", "N/A");
                    // Get the  ROLE from the portlet preferences for the GARUDA VO
                    String garuda_wrf_ROLE = portletPreferences.getValue("garuda_wrf_ROLE", "N/A");
                    // Get the  RENEWAL from the portlet preferences for the GARUDA VO
                    String garuda_wrf_RENEWAL = portletPreferences.getValue("garuda_wrf_RENEWAL", "checked");
                    // Get the  DISABLEVOMS from the portlet preferences for the GARUDA VO
                    String garuda_wrf_DISABLEVOMS = portletPreferences.getValue("garuda_wrf_DISABLEVOMS", "unchecked"); 
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n- Getting the  portlet preferences ..."
                        + "\ngaruda_wrf_INFRASTRUCTURE: " + garuda_wrf_INFRASTRUCTURE
                        + "\ngaruda_wrf_VONAME: " + garuda_wrf_VONAME
                        + "\ngaruda_wrf_TOPBDII: " + garuda_wrf_TOPBDII                    
                        + "\ngaruda_wrf_ETOKENSERVER: " + garuda_wrf_ETOKENSERVER
                        + "\ngaruda_wrf_MYPROXYSERVER: " + garuda_wrf_MYPROXYSERVER
                        + "\ngaruda_wrf_PORT: " + garuda_wrf_PORT
                        + "\ngaruda_wrf_ROBOTID: " + garuda_wrf_ROBOTID
                        + "\ngaruda_wrf_ROLE: " + garuda_wrf_ROLE
                        + "\ngaruda_wrf_RENEWAL: " + garuda_wrf_RENEWAL
                        + "\ngaruda_wrf_DISABLEVOMS: " + garuda_wrf_DISABLEVOMS
                       
                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + garuda_wrf_ENABLEINFRASTRUCTURE
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                    
                    // Defining the WMS list for the "GARUDA" Infrastructure
                    int nmax=0;
                    for (int i = 0; i < garuda_wrf_WMS.length; i++)
                        if ((garuda_wrf_WMS[i]!=null) && 
                            (!garuda_wrf_WMS[i].equals("N/A"))) 
                            nmax++;

                    //String wmsList[] = new String [nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        if (garuda_wrf_WMS[i]!=null) {
                        wmsList[i]=garuda_wrf_WMS[i].trim();
                        log.info ("\n\n[" + nmax
                                          + "] Submitting to GARUDA ["
                                          + i
                                          + "] using WSGRAM=["
                                          + wmsList[i]
                                          + "]");
                        }
                    }

                    infrastructures[1] = new InfrastructureInfo(
                        "GARUDA",
                        "wsgram",
                        "",
                        wmsList,
                        garuda_wrf_ETOKENSERVER,
                        garuda_wrf_PORT,
                        garuda_wrf_ROBOTID,
                        garuda_wrf_VONAME,
                        garuda_wrf_ROLE);
                }
                
                if (chain_wrf_ENABLEINFRASTRUCTURE != null &&
                    chain_wrf_ENABLEINFRASTRUCTURE.equals("chain"))
                {
                    String OCCI_AUTH = "x509";
                    
                    // Possible RESOURCE values: 'os_tpl', 'resource_tpl', 'compute'
                    String OCCI_RESOURCE = "compute";
                    
                    // Possible ACTION values: 'list', 'describe', 'create' and 'delete'
                    String OCCI_ACTION = "create";
                    
                    NMAX++;                
                    // Get the  VONAME from the portlet preferences for the CHAIN VO
                    String chain_wrf_INFRASTRUCTURE = portletPreferences.getValue("chain_wrf_INFRASTRUCTURE", "N/A");
                    // Get the  VONAME from the portlet preferences for the CHAIN VO
                    chain_wrf_VONAME = portletPreferences.getValue("chain_wrf_VONAME", "N/A");
                    // Get the  TOPPBDII from the portlet preferences for the CHAIN VO
                    String chain_wrf_TOPBDII = portletPreferences.getValue("chain_wrf_TOPBDII", "N/A");
                    // Get the  WMS from the portlet preferences for the CHAIN VO                
                    String[] chain_wrf_WMS = portletPreferences.getValues("chain_wrf_WMS", new String[10]);
                    // Get the  ETOKENSERVER from the portlet preferences for the CHAIN VO
                    chain_wrf_ETOKENSERVER = portletPreferences.getValue("chain_wrf_ETOKENSERVER", "N/A");
                    // Get the  MYPROXYSERVER from the portlet preferences for the CHAIN VO
                    String chain_wrf_MYPROXYSERVER = portletPreferences.getValue("chain_wrf_MYPROXYSERVER", "N/A");
                    // Get the  PORT from the portlet preferences for the CHAIN VO
                    chain_wrf_PORT = portletPreferences.getValue("chain_wrf_PORT", "N/A");
                    // Get the  ROBOTID from the portlet preferences for the CHAIN VO
                    chain_wrf_ROBOTID = portletPreferences.getValue("chain_wrf_ROBOTID", "N/A");
                    // Get the  ROLE from the portlet preferences for the CHAIN VO
                    chain_wrf_ROLE = portletPreferences.getValue("chain_wrf_ROLE", "N/A");
                    // Get the  RENEWAL from the portlet preferences for the CHAIN VO
                    String chain_wrf_RENEWAL = portletPreferences.getValue("chain_wrf_RENEWAL", "checked");
                    // Get the  DISABLEVOMS from the portlet preferences for the CHAIN VO
                    String chain_wrf_DISABLEVOMS = portletPreferences.getValue("chain_wrf_DISABLEVOMS", "unchecked"); 
                    // Get the random CE for the Sonification portlet               
                    //RANDOM_CE = getRandomCE(chain_wrf_VONAME, chain_wrf_TOPBDII, wrf_SOFTWARE);
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n- Getting the  portlet preferences ..."
                        + "\nchain_wrf_INFRASTRUCTURE: " + chain_wrf_INFRASTRUCTURE
                        + "\nchain_wrf_VONAME: " + chain_wrf_VONAME
                        + "\nchain_wrf_TOPBDII: " + chain_wrf_TOPBDII                    
                        + "\nchain_wrf_ETOKENSERVER: " + chain_wrf_ETOKENSERVER
                        + "\nchain_wrf_MYPROXYSERVER: " + chain_wrf_MYPROXYSERVER
                        + "\nchain_wrf_PORT: " + chain_wrf_PORT
                        + "\nchain_wrf_ROBOTID: " + chain_wrf_ROBOTID
                        + "\nchain_wrf_ROLE: " + chain_wrf_ROLE
                        + "\nchain_wrf_RENEWAL: " + chain_wrf_RENEWAL
                        + "\nchain_wrf_DISABLEVOMS: " + chain_wrf_DISABLEVOMS
                       
                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + chain_wrf_ENABLEINFRASTRUCTURE
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                        
                        log.info("\n- Creating a proxy ...");
                        getRobotProxy(chain_wrf_ETOKENSERVER, 
                                      chain_wrf_PORT,
                                      chain_wrf_ROBOTID,
                                      chain_wrf_VONAME,
                                      chain_wrf_ROLE,
                                      chain_wrf_RENEWAL);
                    }
                    
                    // Defining the rOCCIResource list for the "CHAIN-REDS" Infrastructure                
                    int nmax=0;
                    for (int i = 0; i < chain_wrf_WMS.length; i++)
                        if ((chain_wrf_WMS[i]!=null) && 
                            (!chain_wrf_WMS[i].equals("N/A"))) 
                            nmax++;
                                                    
                    //String wmsList[] = new String [nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        if (chain_wrf_WMS[i]!=null) {
                            _wmsListChainCloud[i]=chain_wrf_WMS[i].trim()+                       
                                "?" +
                                "action=" + OCCI_ACTION +
                                "&resource=" + OCCI_RESOURCE +
                                "&attributes_title=OCCI_VM_TITLE" +
                                "&mixin_os_tpl=OCCI_OS" +
                                "&mixin_resource_tpl=OCCI_FLAVOUR" +
                                "&auth=" + OCCI_AUTH;                                
                        
                        log.info ("\n\n[" + nmax
                                          + "] CHAIN-REDS ["
                                          + i
                                          + "] rOCCIResourceID=["
                                          + _wmsListChainCloud[i]                                      
                                          + "]");
                        }
                    }

                    infrastructures[2] = new InfrastructureInfo(
                        "CHAIN-REDS",
                        "rocci",
                        "",
                        _wmsListChainCloud,
                        chain_wrf_ETOKENSERVER,
                        chain_wrf_PORT,
                        chain_wrf_ROBOTID,
                        chain_wrf_VONAME,
                        chain_wrf_ROLE,
                        true);                    
                }
                
                if (fedcloud_wrf_ENABLEINFRASTRUCTURE != null &&
                    fedcloud_wrf_ENABLEINFRASTRUCTURE.equals("fedcloud"))
                {
                    String OCCI_AUTH = "x509";
                    
                    // Possible RESOURCE values: 'os_tpl', 'resource_tpl', 'compute'
                    String OCCI_RESOURCE = "compute";
                    
                    //String OCCI_VM_TITLE = "MyROCCITest";
                    //String OCCI_FLAVOUR = "small";                
                    
                    // Possible ACTION values: 'list', 'describe', 'create' and 'delete'
                    String OCCI_ACTION = "create";
                    
                    NMAX++;                
                    // Get the  VONAME from the portlet preferences for the FEDCLOUD VO
                    String fedcloud_wrf_INFRASTRUCTURE = portletPreferences.getValue("fedcloud_wrf_INFRASTRUCTURE", "N/A");
                    // Get the  VONAME from the portlet preferences for the FEDCLOUD VO
                    fedcloud_wrf_VONAME = portletPreferences.getValue("fedcloud_wrf_VONAME", "N/A");
                    // Get the  TOPPBDII from the portlet preferences for the FEDCLOUD VO
                    String fedcloud_wrf_TOPBDII = portletPreferences.getValue("fedcloud_wrf_TOPBDII", "N/A");
                    // Get the  WMS from the portlet preferences for the FEDCLOUD VO
                    String[] fedcloud_wrf_WMS = portletPreferences.getValues("fedcloud_wrf_WMS", new String[10]);
                    // Get the  ETOKENSERVER from the portlet preferences for the FEDCLOUD VO
                    fedcloud_wrf_ETOKENSERVER = portletPreferences.getValue("fedcloud_wrf_ETOKENSERVER", "N/A");
                    // Get the  MYPROXYSERVER from the portlet preferences for the FEDCLOUD VO
                    String fedcloud_wrf_MYPROXYSERVER = portletPreferences.getValue("fedcloud_wrf_MYPROXYSERVER", "N/A");
                    // Get the  PORT from the portlet preferences for the FEDCLOUD VO
                    fedcloud_wrf_PORT = portletPreferences.getValue("fedcloud_wrf_PORT", "N/A");
                    // Get the  ROBOTID from the portlet preferences for the FEDCLOUD VO
                    fedcloud_wrf_ROBOTID = portletPreferences.getValue("fedcloud_wrf_ROBOTID", "N/A");
                    // Get the  ROLE from the portlet preferences for the FEDCLOUD VO
                    fedcloud_wrf_ROLE = portletPreferences.getValue("fedcloud_wrf_ROLE", "N/A");
                    // Get the  RENEWAL from the portlet preferences for the FEDCLOUD VO
                    String fedcloud_wrf_RENEWAL = portletPreferences.getValue("fedcloud_wrf_RENEWAL", "checked");
                    // Get the  DISABLEVOMS from the portlet preferences for the FEDCLOUD VO
                    String fedcloud_wrf_DISABLEVOMS = portletPreferences.getValue("fedcloud_wrf_DISABLEVOMS", "unchecked");
                    // Get the random CE for the Sonification portlet               
                    //RANDOM_CE = getRandomCE(fedcloud_wrf_VONAME, fedcloud_wrf_TOPBDII, wrf_SOFTWARE);
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n- Getting the  portlet preferences ..."
                        + "\n\nfedcloud_wrf_INFRASTRUCTURE: " + fedcloud_wrf_INFRASTRUCTURE
                        + "\nfedcloud_wrf_VONAME: " + fedcloud_wrf_VONAME
                        + "\nfedcloud_wrf_TOPBDII: " + fedcloud_wrf_TOPBDII                    
                        + "\nfedcloud_wrf_ETOKENSERVER: " + fedcloud_wrf_ETOKENSERVER
                        + "\nfedcloud_wrf_MYPROXYSERVER: " + fedcloud_wrf_MYPROXYSERVER
                        + "\nfedcloud_wrf_PORT: " + fedcloud_wrf_PORT
                        + "\nfedcloud_wrf_ROBOTID: " + fedcloud_wrf_ROBOTID
                        + "\nfedcloud_wrf_ROLE: " + fedcloud_wrf_ROLE
                        + "\nfedcloud_wrf_RENEWAL: " + fedcloud_wrf_RENEWAL
                        + "\nfedcloud_wrf_DISABLEVOMS: " + fedcloud_wrf_DISABLEVOMS

                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + fedcloud_wrf_ENABLEINFRASTRUCTURE
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                        
                        //log.info("\n- Creating a proxy ...");
                        getRobotProxy(fedcloud_wrf_ETOKENSERVER, 
                                      fedcloud_wrf_PORT,
                                      fedcloud_wrf_ROBOTID,
                                      fedcloud_wrf_VONAME,
                                      fedcloud_wrf_ROLE,
                                      fedcloud_wrf_RENEWAL);
                    }
                    
                    // Defining the rOCCIResource list for the "FEDCLOUD" Infrastructure                
                    int nmax=0;
                    for (int i = 0; i < fedcloud_wrf_WMS.length; i++)
                        if ((fedcloud_wrf_WMS[i]!=null) && 
                            (!fedcloud_wrf_WMS[i].equals("N/A"))) 
                            nmax++;
                                                    
                    //String wmsList[] = new String [nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        if (fedcloud_wrf_WMS[i]!=null) {
                            _wmsListFedCloud[i]=fedcloud_wrf_WMS[i].trim()+                       
                                "?" +
                                "action=" + OCCI_ACTION +
                                "&resource=" + OCCI_RESOURCE +                                
                                "&attributes_title=OCCI_VM_TITLE" +
                                "&mixin_os_tpl=OCCI_OS" +                                
                                "&mixin_resource_tpl=OCCI_FLAVOUR" +
                                "&auth=" + OCCI_AUTH;                                
                        
                        log.info ("\n\n[" + nmax
                                          + "] FEDCLOUD ["
                                          + i
                                          + "] rOCCIResourceID=["
                                          + _wmsListFedCloud[i]                                      
                                          + "]");
                        }
                    }

                    infrastructures[3] = 
                            new InfrastructureInfo(
                                "EGI-FEDCLOUD",
                                "rocci",
                                "",
                                _wmsListFedCloud,                            
                                fedcloud_wrf_ETOKENSERVER,
                                fedcloud_wrf_PORT,
                                fedcloud_wrf_ROBOTID,
                                fedcloud_wrf_VONAME,
                                fedcloud_wrf_ROLE,
                                true);
                }
                
                if (eumed_wrf_ENABLEINFRASTRUCTURE != null &&
                    eumed_wrf_ENABLEINFRASTRUCTURE.equals("eumed"))
                {
                    NMAX++;                
                    // Get the  VONAME from the portlet preferences for the EUMED VO
                    String eumed_wrf_INFRASTRUCTURE = portletPreferences.getValue("eumed_wrf_INFRASTRUCTURE", "N/A");
                    // Get the  VONAME from the portlet preferences for the EUMED VO
                    String eumed_wrf_VONAME = portletPreferences.getValue("eumed_wrf_VONAME", "N/A");
                    // Get the  TOPPBDII from the portlet preferences for the EUMED VO
                    String eumed_wrf_TOPBDII = portletPreferences.getValue("eumed_wrf_TOPBDII", "N/A");
                    // Get the  WMS from the portlet preferences for the EUMED VO
                    String[] eumed_wrf_WMS = portletPreferences.getValues("eumed_wrf_WMS", new String[5]);
                    // Get the  ETOKENSERVER from the portlet preferences for the EUMED VO
                    String eumed_wrf_ETOKENSERVER = portletPreferences.getValue("eumed_wrf_ETOKENSERVER", "N/A");
                    // Get the  MYPROXYSERVER from the portlet preferences for the EUMED VO
                    String eumed_wrf_MYPROXYSERVER = portletPreferences.getValue("eumed_wrf_MYPROXYSERVER", "N/A");
                    // Get the  PORT from the portlet preferences for the EUMED VO
                    String eumed_wrf_PORT = portletPreferences.getValue("eumed_wrf_PORT", "N/A");
                    // Get the  ROBOTID from the portlet preferences for the EUMED VO
                    String eumed_wrf_ROBOTID = portletPreferences.getValue("eumed_wrf_ROBOTID", "N/A");
                    // Get the  ROLE from the portlet preferences for the EUMED VO
                    String eumed_wrf_ROLE = portletPreferences.getValue("eumed_wrf_ROLE", "N/A");
                    // Get the  RENEWAL from the portlet preferences for the EUMED VO
                    String eumed_wrf_RENEWAL = portletPreferences.getValue("eumed_wrf_RENEWAL", "checked");
                    // Get the  DISABLEVOMS from the portlet preferences for the EUMED VO
                    String eumed_wrf_DISABLEVOMS = portletPreferences.getValue("eumed_wrf_DISABLEVOMS", "unchecked");
                    // Get the random CE for the Sonification portlet               
                    //RANDOM_CE = getRandomCE(eumed_wrf_VONAME, eumed_wrf_TOPBDII, wrf_SOFTWARE);
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n- Getting the  portlet preferences ..."
                        + "\n\neumed_wrf_INFRASTRUCTURE: " + eumed_wrf_INFRASTRUCTURE
                        + "\neumed_wrf_VONAME: " + eumed_wrf_VONAME
                        + "\neumed_wrf_TOPBDII: " + eumed_wrf_TOPBDII                    
                        + "\neumed_wrf_ETOKENSERVER: " + eumed_wrf_ETOKENSERVER
                        + "\neumed_wrf_MYPROXYSERVER: " + eumed_wrf_MYPROXYSERVER
                        + "\neumed_wrf_PORT: " + eumed_wrf_PORT
                        + "\neumed_wrf_ROBOTID: " + eumed_wrf_ROBOTID
                        + "\neumed_wrf_ROLE: " + eumed_wrf_ROLE
                        + "\neumed_wrf_RENEWAL: " + eumed_wrf_RENEWAL
                        + "\neumed_wrf_DISABLEVOMS: " + eumed_wrf_DISABLEVOMS

                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + eumed_wrf_ENABLEINFRASTRUCTURE
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                    
                    // Defining the WMS list for the "EUMED" Infrastructure
                    int nmax=0;
                    for (int i = 0; i < eumed_wrf_WMS.length; i++)
                        if ((eumed_wrf_WMS[i]!=null) && (!eumed_wrf_WMS[i].equals("N/A"))) nmax++;
                    
                    String eumed_wmsList[] = new String [nmax];
                    for (int i = 0; i < nmax; i++)
                    {
                        if (eumed_wrf_WMS[i]!=null) {
                        eumed_wmsList[i]=eumed_wrf_WMS[i].trim();
                        log.info ("\n\n[" + nmax
                                          + "] Submitting to EUMED ["
                                          + i
                                          + "] using WMS=["
                                          + eumed_wmsList[i]
                                          + "]");
                        }
                    }

                    infrastructures[4] = new InfrastructureInfo(
                        eumed_wrf_VONAME,
                        eumed_wrf_TOPBDII,
                        eumed_wmsList,
                        eumed_wrf_ETOKENSERVER,
                        eumed_wrf_PORT,
                        eumed_wrf_ROBOTID,
                        eumed_wrf_VONAME,
                        eumed_wrf_ROLE,
                        "VO-" + eumed_wrf_VONAME + "-" + wrf_SOFTWARE);
                }

                if (gisela_wrf_ENABLEINFRASTRUCTURE != null &&
                    gisela_wrf_ENABLEINFRASTRUCTURE.equals("gisela")) 
                {
                    NMAX++;                
                    // Get the  VONAME from the portlet preferences for the GISELA VO
                    String gisela_wrf_INFRASTRUCTURE = portletPreferences.getValue("gisela_wrf_INFRASTRUCTURE", "N/A");
                    // Get the  VONAME from the portlet preferences for the GISELA VO
                    String gisela_wrf_VONAME = portletPreferences.getValue("gisela_wrf_VONAME", "N/A");
                    // Get the  TOPPBDII from the portlet preferences for the GISELA VO
                    String gisela_wrf_TOPBDII = portletPreferences.getValue("gisela_wrf_TOPBDII", "N/A");
                    // Get the  WMS from the portlet preferences for the GISELA VO
                    String[] gisela_wrf_WMS = portletPreferences.getValues("gisela_wrf_WMS", new String[5]);
                    // Get the  ETOKENSERVER from the portlet preferences for the GISELA VO
                    String gisela_wrf_ETOKENSERVER = portletPreferences.getValue("gisela_wrf_ETOKENSERVER", "N/A");
                    // Get the  MYPROXYSERVER from the portlet preferences for the GISELA VO
                    String gisela_wrf_MYPROXYSERVER = portletPreferences.getValue("gisela_wrf_MYPROXYSERVER", "N/A");
                    // Get the  PORT from the portlet preferences for the GISELA VO
                    String gisela_wrf_PORT = portletPreferences.getValue("gisela_wrf_PORT", "N/A");
                    // Get the  ROBOTID from the portlet preferences for the GISELA VO
                    String gisela_wrf_ROBOTID = portletPreferences.getValue("gisela_wrf_ROBOTID", "N/A");
                    // Get the  ROLE from the portlet preferences for the GISELA VO
                    String gisela_wrf_ROLE = portletPreferences.getValue("gisela_wrf_ROLE", "N/A");
                    // Get the  RENEWAL from the portlet preferences for the GISELA VO
                    String gisela_wrf_RENEWAL = portletPreferences.getValue("gisela_wrf_RENEWAL", "checked");
                    // Get the  DISABLEVOMS from the portlet preferences for the GISELA VO
                    String gisela_wrf_DISABLEVOMS = portletPreferences.getValue("gisela_wrf_DISABLEVOMS", "unchecked");          
                    // Get the random CE for the Sonification portlet               
                    //RANDOM_CE = getRandomCE(gisela_wrf_VONAME, gisela_wrf_TOPBDII, wrf_SOFTWARE);
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n- Getting the  portlet preferences ..."
                        + "\n\ngisela_wrf_INFRASTRUCTURE: " + gisela_wrf_INFRASTRUCTURE
                        + "\ngisela_wrf_VONAME: " + gisela_wrf_VONAME
                        + "\ngisela_wrf_TOPBDII: " + gisela_wrf_TOPBDII                        
                        + "\ngisela_wrf_ETOKENSERVER: " + gisela_wrf_ETOKENSERVER
                        + "\ngisela_wrf_MYPROXYSERVER: " + gisela_wrf_MYPROXYSERVER
                        + "\ngisela_wrf_PORT: " + gisela_wrf_PORT
                        + "\ngisela_wrf_ROBOTID: " + gisela_wrf_ROBOTID
                        + "\ngisela_wrf_ROLE: " + gisela_wrf_ROLE
                        + "\ngisela_wrf_RENEWAL: " + gisela_wrf_RENEWAL
                        + "\ngisela_wrf_DISABLEVOMS: " + gisela_wrf_DISABLEVOMS

                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + gisela_wrf_ENABLEINFRASTRUCTURE
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);
                    }
                    
                    // Defining the WMS list for the "GISELA" Infrastructure
                    int nmax=0;
                    for (int i = 0; i < gisela_wrf_WMS.length; i++)
                        if ((gisela_wrf_WMS[i]!=null) && (!gisela_wrf_WMS[i].equals("N/A"))) nmax++;
                    
                    //String wmsList[] = new String [gisela_wrf_WMS.length];
                    for (int i = 0; i < gisela_wrf_WMS.length; i++)
                    {
                        if (gisela_wrf_WMS[i]!=null) {
                        wmsList[i]=gisela_wrf_WMS[i].trim();
                        log.info ("\n\nSubmitting for GISELA [" + i + "] using WMS=[" + wmsList[i] + "]");
                        }
                    }

                    infrastructures[5] = new InfrastructureInfo(
                        gisela_wrf_VONAME,
                        gisela_wrf_TOPBDII,
                        wmsList,
                        gisela_wrf_ETOKENSERVER,
                        gisela_wrf_PORT,
                        gisela_wrf_ROBOTID,
                        gisela_wrf_VONAME,
                        gisela_wrf_ROLE,
                        "VO-" + gisela_wrf_VONAME + "-" + wrf_SOFTWARE);
                }
                
                if (sagrid_wrf_ENABLEINFRASTRUCTURE != null &&
                    sagrid_wrf_ENABLEINFRASTRUCTURE.equals("sagrid")) 
                {
                    NMAX++;                
                    // Get the  VONAME from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_INFRASTRUCTURE = portletPreferences.getValue("sagrid_wrf_INFRASTRUCTURE", "N/A");
                    // Get the  VONAME from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_VONAME = portletPreferences.getValue("sagrid_wrf_VONAME", "N/A");
                    // Get the  TOPPBDII from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_TOPBDII = portletPreferences.getValue("sagrid_wrf_TOPBDII", "N/A");
                    // Get the  WMS from the portlet preferences for the SAGRID VO
                    String[] sagrid_wrf_WMS = portletPreferences.getValues("sagrid_wrf_WMS", new String[5]);
                    // Get the  ETOKENSERVER from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_ETOKENSERVER = portletPreferences.getValue("sagrid_wrf_ETOKENSERVER", "N/A");
                    // Get the  MYPROXYSERVER from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_MYPROXYSERVER = portletPreferences.getValue("sagrid_wrf_MYPROXYSERVER", "N/A");
                    // Get the  PORT from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_PORT = portletPreferences.getValue("sagrid_wrf_PORT", "N/A");
                    // Get the  ROBOTID from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_ROBOTID = portletPreferences.getValue("sagrid_wrf_ROBOTID", "N/A");
                    // Get the  ROLE from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_ROLE = portletPreferences.getValue("sagrid_wrf_ROLE", "N/A");
                    // Get the  RENEWAL from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_RENEWAL = portletPreferences.getValue("sagrid_wrf_RENEWAL", "checked");
                    // Get the  DISABLEVOMS from the portlet preferences for the SAGRID VO
                    String sagrid_wrf_DISABLEVOMS = portletPreferences.getValue("sagrid_wrf_DISABLEVOMS", "unchecked");          
                    // Get the random CE for the Sonification portlet               
                    //RANDOM_CE = getRandomCE(sagrid_wrf_VONAME, sagrid_wrf_TOPBDII, wrf_SOFTWARE);
                    
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE")) {
                        log.info("\n- Getting the  portlet preferences ..."
                        + "\n\nsagrid_wrf_INFRASTRUCTURE: " + sagrid_wrf_INFRASTRUCTURE
                        + "\nsagrid_wrf_VONAME: " + sagrid_wrf_VONAME
                        + "\nsagrid_wrf_TOPBDII: " + sagrid_wrf_TOPBDII                        
                        + "\nsagrid_wrf_ETOKENSERVER: " + sagrid_wrf_ETOKENSERVER
                        + "\nsagrid_wrf_MYPROXYSERVER: " + sagrid_wrf_MYPROXYSERVER
                        + "\nsagrid_wrf_PORT: " + sagrid_wrf_PORT
                        + "\nsagrid_wrf_ROBOTID: " + sagrid_wrf_ROBOTID
                        + "\nsagrid_wrf_ROLE: " + sagrid_wrf_ROLE
                        + "\nsagrid_wrf_RENEWAL: " + sagrid_wrf_RENEWAL
                        + "\nsagrid_wrf_DISABLEVOMS: " + sagrid_wrf_DISABLEVOMS

                        + "\n\nwrf_ENABLEINFRASTRUCTURE: " + sagrid_wrf_ENABLEINFRASTRUCTURE
                        + "\nwrf_APPID: " + wrf_APPID
                        + "\nwrf_LOGLEVEL: " + wrf_LOGLEVEL
                        + "\nwrf_OUTPUT_PATH: " + wrf_OUTPUT_PATH
                        + "\nwrf_SOFTWARE: " + wrf_SOFTWARE
                        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                        + "\nSMTP_HOST: " + SMTP_HOST
                        + "\nSENDER_MAIL: " + SENDER_MAIL);                                        
                    }
                    
                    // Defining the WMS list for the "SAGRID" Infrastructure
                    int nmax=0;
                    for (int i = 0; i < sagrid_wrf_WMS.length; i++)
                        if ((sagrid_wrf_WMS[i]!=null) && (!sagrid_wrf_WMS[i].equals("N/A"))) nmax++;
                    
                    for (int i = 0; i < sagrid_wrf_WMS.length; i++)
                    {
                        if (sagrid_wrf_WMS[i]!=null) {
                        wmsList[i]=sagrid_wrf_WMS[i].trim();
                        log.info ("\n\nSubmitting for SAGRID [" + i + "] using WMS=[" + wmsList[i] + "]");
                        }
                    }

                    infrastructures[6] = new InfrastructureInfo(
                        sagrid_wrf_VONAME,
                        sagrid_wrf_TOPBDII,
                        wmsList,
                        sagrid_wrf_ETOKENSERVER,
                        sagrid_wrf_PORT,
                        sagrid_wrf_ROBOTID,
                        sagrid_wrf_VONAME,
                        sagrid_wrf_ROLE,
                        "VO-" + sagrid_wrf_VONAME + "-" + wrf_SOFTWARE);
                }
                
                String[] _Parameters = new String [7];
                String[] _Settings = new String [4];

                // Upload the input settings for the application
                _Parameters = uploadSettings( request, response, username );
                _Settings[0] = _Parameters[6];
                _Settings[1] = username;                
                _Settings[2] = portal;
                _Settings[3] = emailAddress;

                log.info("\n- Input Parameters: ");
                //log.info("\n- ASCII or Text = " + _Parameters[0]);
                log.info("\n- VM Profile Type = " + _Parameters[1]);
                log.info("\n- VM Template = " + _Parameters[5]);
                log.info("\n- VM Name = " + _Parameters[6]);
                log.info("\n- Cloud Resource = " + _Parameters[2]);
                log.info("\n- Enable Notification = " + _Parameters[3]);
                log.info("\n- Description = " + _Parameters[4]);
                log.info("\n- Username = " + _Settings[1]);
                log.info("\n- E-mail = " + _Settings[2]);
                log.info("\n- Portal = " + _Settings[3]);
                
                // Preparing to submit applications in different infrastructures..
                //=============================================================
                // IMPORTANT: INSTANCIATE THE MultiInfrastructureJobSubmission
                //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
                //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
                //=============================================================
                /*MultiInfrastructureJobSubmission CloudMultiJobSubmission =
                new MultiInfrastructureJobSubmission(TRACKING_DB_HOSTNAME,
                                                     TRACKING_DB_USERNAME,
                                                     TRACKING_DB_PASSWORD);*/
                
                MultiInfrastructureJobSubmission CloudMultiJobSubmission =
                    new MultiInfrastructureJobSubmission();
                
                if (infrastructures[0]!=null) 
                 {
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE"))
                            log.info("\n- Adding the DIT Cluster Infrastructure.");
                    
                    CloudMultiJobSubmission.addInfrastructure(infrastructures[0]);
                 }
                
                if (infrastructures[2]!=null) 
                {
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE"))
                            log.info("\n- Adding the CHAIN-REDS Cloud Testbed.");
                    
                    for (int i = 0; i < _wmsListChainCloud.length; i++)
                    {
                        if ((_wmsListChainCloud[i]!=null) && (!_wmsListChainCloud[i].equals("N/A")))
                        {
                            // A.) WRFTYPE = [ wrf ]
                            if (_Parameters[1].equals("appwrf")) 
                            {
                                // === SETTINGS for the INFN-STACK CLOUD RESOURCE === //
                                if ((_wmsListChainCloud[i].indexOf("stack-server-01.ct.infn.it")) != -1)   
                                {
                                    log.info("\n- Adding some customizations for the INFN-STACK Cloud Provider.");
                                    _wmsListChainCloud[i] = (_wmsListChainCloud[i].trim()).replace("OCCI_FLAVOUR", 
                                    "m1-medium");
                                
                                    _wmsListChainCloud[i] = (_wmsListChainCloud[i].trim()).replace("OCCI_VM_TITLE", 
                                    _Parameters[6]);
                                
                                    _wmsListChainCloud[i] = (_wmsListChainCloud[i].trim()).replace("OCCI_OS", 
                                    "6ee0e31b-e066-4d39-86fd-059b1de8c52f");
                                }                            
                            
                                // === SETTINGS for the INFN-NEBULA CLOUD RESOURCE === //
                                if ((_wmsListChainCloud[i].indexOf("nebula-server-01.ct.infn.it")) != -1)   
                                {
                                    log.info("\n- Adding some customizations for the INFN-NEBULA Cloud Provider.");
                                    _wmsListChainCloud[i] = (_wmsListChainCloud[i].trim()).replace("OCCI_OS", 
                                        "uuid_appwrf_51"); 

                                    _wmsListChainCloud[i] = (_wmsListChainCloud[i].trim()).replace("OCCI_FLAVOUR", 
                                        _Parameters[5]);
                                
                                    _wmsListChainCloud[i] = (_wmsListChainCloud[i].trim()).replace("OCCI_VM_TITLE", 
                                        _Parameters[6]);
                                }
                            }
                        }
                     }
                        
                     // Defining the list of providers for the "CHAIN-REDS" Infrastructure
                     int nmax=0;
                     for (int i = 0; i < _wmsListChainCloud.length; i++)
                        if ((_wmsListChainCloud[i]!=null) && (!_wmsListChainCloud[i].equals("N/A"))) 
                                nmax++;
                    
                     String chaincloudwmsList[] = new String [nmax];
                     for (int i = 0; i < _wmsListChainCloud.length; i++)
                     {
                        if (_wmsListChainCloud[i]!=null) {
                            chaincloudwmsList[i]=_wmsListChainCloud[i].trim();
                            log.info ("\n\nCloud ResourceID = [" + chaincloudwmsList[i] + "]");
                        }
                     }
                                         
                     infrastructures[2] = 
                                new InfrastructureInfo(
                                "CHAIN-REDS",
                                "rocci",
                                "",                            
                                chaincloudwmsList,                                
                                chain_wrf_ETOKENSERVER,
                                chain_wrf_PORT,
                                chain_wrf_ROBOTID,
                                chain_wrf_VONAME,
                                chain_wrf_ROLE,
                                true);                                                

                     CloudMultiJobSubmission.addInfrastructure(infrastructures[2]);
                 }
                    
                 if (infrastructures[4]!=null) 
                 {
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE"))
                            log.info("\n- Adding the EUMED Grid Infrastructure.");
                    
                    CloudMultiJobSubmission.addInfrastructure(infrastructures[4]);
                 }
                    
                // A.) WRFTYPE = [ wrf ]
                if (_Parameters[1].equals("appwrf")) 
                {
                    if (wrf_LOGLEVEL.trim().equals("VERBOSE"))
                    log.info ("\n\nPreparing to launch an i686 VM with [ WRF-3.5 ]");
                
                    String CloudFilesPath = getPortletContext().getRealPath("/") 
                                            + "WEB-INF/config"; 
                
                    // Set the Output path for results            
                    CloudMultiJobSubmission.setOutputPath(wrf_OUTPUT_PATH);

                    // Set the StandardOutput for 
                    CloudMultiJobSubmission.setJobOutput("std.out");

                    // Set the StandardError for 
                    CloudMultiJobSubmission.setJobError("std.err");
                
                    // OutputSandbox (string with comma separated list of file names)                    
                    String CloudFiles="wrf_output.tar.gz, README.txt";                        

                    // Set the OutputSandbox files (string with comma separated list of file names)
                    CloudMultiJobSubmission.setOutputFiles(CloudFiles);
                
                    // Store the list of Argument(s) in a file
                    File WRF_Repository = new File ("/tmp");
                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String timeStamp = dateFormat.format(Calendar.getInstance().getTime());
            
                    String Arguments_File = WRF_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_patterns.txt";
            
                    storeFile (Arguments_File, _Settings);
                    
                    // Set the Executable                    
                    CloudMultiJobSubmission.setExecutable("/bin/bash");
                        
                    String Arguments = "wrf_demo_gridncloud.sh";
                    
                    // Set the list of Arguments
                    CloudMultiJobSubmission.setArguments(Arguments);
                    
                    String InputSandbox = 
                            CloudFilesPath + "/wrf_demo_gridncloud.sh" + "," 
                          + CloudFilesPath + "/all_wrf_inputs.tar.gz" + ","                                      
                          + Arguments_File;

                    // Set InputSandbox files (string with comma separated list of file names)
                    CloudMultiJobSubmission.setInputFiles(InputSandbox);
                }
               
                // Get the infra
                InfrastructureInfo infrastructure = 
                        CloudMultiJobSubmission.getInfrastructure();
                                
                if (infrastructure.getMiddleware().equals("glite")) {
                    log.info("\n- Selected Middleware = glite ");
                    // Set the queue if it's defined
                    // This option is not supported in multi-infrastructures mode
                    if (!_Parameters[2].isEmpty())
                        CloudMultiJobSubmission.setJobQueue(_Parameters[2]);
                }
                
                if (infrastructure.getMiddleware().equals("occi"))
                    log.info("\n- Selected Cloud Middleware = rocci ");
                
                if (infrastructure.getMiddleware().equals("ssh"))
                    log.info("\n- Selected Infrastructure = ssh ");
                
                InetAddress addr = InetAddress.getLocalHost();                
                
                try {
                    company = PortalUtil.getCompany(request);
                    String gateway = company.getName();
                    
                    // Send a notification email to the user if enabled.
                    if (_Parameters[3]!=null)
                        if ( (SMTP_HOST==null) || 
                             (SMTP_HOST.trim().equals("")) ||
                             (SMTP_HOST.trim().equals("N/A")) ||
                             (SENDER_MAIL==null) || 
                             (SENDER_MAIL.trim().equals("")) ||
                             (SENDER_MAIL.trim().equals("N/A"))
                           )
                        log.info ("\nThe Notification Service is not properly configured!!");
                        
                     else {
                            // Enabling Job's notification via email
                            CloudMultiJobSubmission.setUserEmail(emailAddress);
                            
                            sendHTMLEmail(username, 
                                          emailAddress, 
                                          SENDER_MAIL, 
                                          SMTP_HOST, 
                                          _Parameters[1],                                      
                                          gateway);
                    }
                    
                    // Submitting in progress ...
                    log.info("\n-  Job submittion in progress using JSAGA JobEngine");
                    CloudMultiJobSubmission.submitJobAsync(
                        infrastructure,
                        username,
                        addr.getHostAddress()+":8162",
                        Integer.valueOf(wrf_APPID),
                        _Parameters[4]);
                    
                } catch (PortalException ex) {
                    Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SystemException ex) {
                    Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
                }                        
            } // end PROCESS ACTION [ SUBMIT_WRF_PORTLET ]
        } catch (PortalException ex) {
            Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response)
                throws PortletException, IOException
    {
        //super.serveResource(request, response);

        PortletPreferences portletPreferences = (PortletPreferences) request.getPreferences();

        final String action = (String) request.getParameter("action");

        if (action.equals("get-ratings")) {
            //Get CE Ratings from the portlet preferences
            String wrf_CR = (String) request.getParameter("wrf_CR");

            String json = "{ \"avg\":\"" + 
        	          portletPreferences.getValue(wrf_CR+"_avg", "0.0") +
                    	  "\", \"cnt\":\"" + 
			  portletPreferences.getValue(wrf_CR+"_cnt", "0") + "\"}";

            response.setContentType("application/json");
            response.getPortletOutputStream().write( json.getBytes() );

        } else if (action.equals("set-ratings")) {

            String wrf_CR = (String) request.getParameter("wrf_CR");
            int vote = Integer.parseInt(request.getParameter("vote"));

             double avg = Double.parseDouble(portletPreferences.getValue(wrf_CR+"_avg", "0.0"));
             long cnt = Long.parseLong(portletPreferences.getValue(wrf_CR+"_cnt", "0"));

             portletPreferences.setValue(wrf_CR+"_avg", Double.toString(((avg*cnt)+vote) / (cnt +1)));
             portletPreferences.setValue(wrf_CR+"_cnt", Long.toString(cnt+1));

             portletPreferences.store();
        }
    }


    // Upload  input files
    public String[] uploadSettings(ActionRequest actionRequest,
                                        ActionResponse actionResponse, String username)
    {        
        String[] _Parameters = new String [7];
        boolean status;

        // Check that we have a file upload request
        boolean isMultipart = PortletFileUpload.isMultipartContent(actionRequest);

        if (isMultipart) {
            // Create a factory for disk-based file items.
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Set factory constrains
            File _Repository = new File ("/tmp");
            if (!_Repository.exists()) status = _Repository.mkdirs();
            factory.setRepository(_Repository);

            // Create a new file upload handler.
            PortletFileUpload upload = new PortletFileUpload(factory);

            try {
                    // Parse the request
                    List items = upload.parseRequest(actionRequest);

                    // Processing items
                    Iterator iter = items.iterator();

                    while (iter.hasNext())
                    {
                        FileItem item = (FileItem) iter.next();

                        String fieldName = item.getFieldName();
                        
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        String timeStamp = dateFormat.format(Calendar.getInstance().getTime());

                        // Processing a regular form field
                        if ( item.isFormField() )
                        {                                                        
                            if (fieldName.equals("wrf_textarea_OCTAVE")) 
                            {
                                _Parameters[0]=
                                        _Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        ".m";                                        
                            
                                // Store the textarea in a ASCII file
                                storeString(_Parameters[0], 
                                            item.getString());                               
                            }
                            
                            if (fieldName.equals("wrf_textarea_R")) 
                            {
                                _Parameters[0]=
                                        _Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        ".r";                                        
                            
                                // Store the textarea in a ASCII file
                                storeString(_Parameters[0], 
                                            item.getString());                               
                            }
                            
                            if (fieldName.equals("wrftype"))
                                _Parameters[1]=item.getString();
                                                        
                            if (fieldName.equals("wrf_CR"))
                                _Parameters[2]=item.getString();
                            
                            if (fieldName.equals("wrfvmtemplate"))
                                _Parameters[5]=item.getString();
                            
                            if (fieldName.equals("wrf_vmname"))
                                _Parameters[6]=item.getString();
                            
                        } else {
                            // Processing a file upload
                            if (fieldName.equals("wrf_file_OCTAVE") ||
                                fieldName.equals("wrf_file_R"))
                            {                                                               
                                log.info("\n- Uploading the following user's file: "
                                       + "\n[ " + item.getName() + " ]"
                                       + "\n[ " + item.getContentType() + " ]"
                                       + "\n[ " + item.getSize() + "KBytes ]"
                                       );                               

                                // Writing the file to disk
                                String uploadFile = 
                                        _Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_" + item.getName();

                                log.info("\n- Writing the user's file: [ "
                                        + uploadFile.toString()
                                        + " ] to disk");

                                item.write(new File(uploadFile)); 
                                
                                _Parameters[0]=uploadFile;                                
                            }
                        }
                        
                        if (fieldName.equals("EnableNotification"))
                                _Parameters[3]=item.getString(); 
                        
                        if (fieldName.equals("wrf_desc"))                                
                                if (item.getString().equals("Please, insert here a description for your run"))
                                    _Parameters[4]="Cloud Simulation Started";
                                else
                                    _Parameters[4]=item.getString();                                                
                        
                    } // end while
            } catch (FileUploadException ex) {
              Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
              Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return _Parameters;
    }
    
    // Retrieve a random Computing Element
    // matching the Software Tag for the  application    
    public String getRandomCE(String wrf_VONAME,
                              String wrf_TOPBDII,
                              String wrf_SOFTWARE)
                              throws PortletException, IOException
    {
        String randomCE = null;
        BDII bdii = null;    
        List<String> CEqueues = null;
                        
        log.info("\n- Querying the Information System [ " + 
                  wrf_TOPBDII + 
                  " ] and retrieving a random CE matching the SW tag [ VO-" + 
                  wrf_VONAME +
                  "-" +
                  wrf_SOFTWARE + " ]");  

       try {               

                bdii = new BDII( new URI(wrf_TOPBDII) );
                
                // Get the list of the available queues
                CEqueues = bdii.queryCEQueues(wrf_VONAME);
                
                // Get the list of the Computing Elements for the given SW tag
                randomCE = bdii.getRandomCEForSWTag("VO-" + 
                                              wrf_VONAME + 
                                              "-" +
                                              wrf_SOFTWARE);
                
                // Fetching the Queues
                for (String CEqueue:CEqueues) {
                    if (CEqueue.contains(randomCE))
                        randomCE=CEqueue;
                }

        } catch (URISyntaxException ex) {
                Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
        }                   

        return randomCE;
    }

    // Retrieve the list of Computing Elements
    // matching the Software Tag for the  application    
    public List<String> getListofCEForSoftwareTag(String wrf_VONAME,
                                                  String wrf_TOPBDII,
                                                  String wrf_SOFTWARE)
                                throws PortletException, IOException
    {
        List<String> CEs_list = null;
        BDII bdii = null;        
        
        log.info("\n- Querying the Information System [ " + 
                  wrf_TOPBDII + 
                  " ] and looking for CEs matching the SW tag [ VO-" + 
                  wrf_VONAME +
                  "-" +
                  wrf_SOFTWARE + " ]");  

       try {               
           
                bdii = new BDII( new URI(wrf_TOPBDII) );                
                
                if (!wrf_SOFTWARE.trim().isEmpty())                     
                    CEs_list = bdii.queryCEForSWTag("VO-" +
                                                    wrf_VONAME +
                                                    "-" +
                                                    wrf_SOFTWARE);                
                /*else
                    CEs_list = bdii.queryCEQueues(wrf_VONAME);*/
                
                // Fetching the CE hostnames
                for (String CE:CEs_list) 
                    log.info("\n- CE host found = " + CE);

        } catch (URISyntaxException ex) {
                Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
        }

        return CEs_list;
    }

    // Get the GPS location of the given grid resource
    public String[] getCECoordinate(RenderRequest request,
                                    String CE)
                                    throws PortletException, IOException
    {
        String[] GPS_locations = null;
        BDII bdii = null;

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        // Get the  TOPPBDII from the portlet preferences
        String chain_wrf_TOPBDII = 
                portletPreferences.getValue("chain_wrf_TOPBDII", "N/A");
        String fedcloud_wrf_TOPBDII = 
                portletPreferences.getValue("fedcloud_wrf_TOPBDII", "N/A");
        String eumed_wrf_TOPBDII = 
                portletPreferences.getValue("eumed_wrf_TOPBDII", "N/A");
        String gisela_wrf_TOPBDII = 
                portletPreferences.getValue("gisela_wrf_TOPBDII", "N/A");
        String sagrid_wrf_TOPBDII = 
                portletPreferences.getValue("sagrid_wrf_TOPBDII", "N/A");
        
        // Get the  ENABLEINFRASTRUCTURE from the portlet preferences
        String wrf_ENABLEINFRASTRUCTURE = 
                portletPreferences.getValue("wrf_ENABLEINFRASTRUCTURE", "N/A");

            try {
                if ( wrf_ENABLEINFRASTRUCTURE.equals("chain") )
                     bdii = new BDII( new URI(chain_wrf_TOPBDII) );
                
                if ( wrf_ENABLEINFRASTRUCTURE.equals("fedcloud") )
                     bdii = new BDII( new URI(fedcloud_wrf_TOPBDII) );                

                if ( wrf_ENABLEINFRASTRUCTURE.equals("eumed") )
                     bdii = new BDII( new URI(eumed_wrf_TOPBDII) );

                if ( wrf_ENABLEINFRASTRUCTURE.equals("gisela") )
                    bdii = new BDII( new URI(gisela_wrf_TOPBDII) );
                
                if ( wrf_ENABLEINFRASTRUCTURE.equals("sagrid") )
                    bdii = new BDII( new URI(sagrid_wrf_TOPBDII) );

                GPS_locations = bdii.queryCECoordinate("ldap://" + CE + ":2170");

            } catch (URISyntaxException ex) {
                Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Wrf.class.getName()).log(Level.SEVERE, null, ex);
            }

            return GPS_locations;
    }
    
    private void storeString (String fileName, String fileContent) 
                              throws IOException 
    { 
        log.info("\n- Writing textarea in a ASCII file [ " + fileName + " ]");        
        // Removing the Carriage Return (^M) from text
        String pattern = "[\r]";
        String stripped = fileContent.replaceAll(pattern, "");        
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));        
        writer.write(stripped);
        writer.write("\n");
        writer.close();
    }
    
    private void sendHTMLEmail (String USERNAME,
                                String TO, 
                                String FROM, 
                                String SMTP_HOST, 
                                String ApplicationAcronym,
                                String GATEWAY)
    {
                
        log.info("\n- Sending email notification to the user " + USERNAME + " [ " + TO + " ]");
        
        log.info("\n- SMTP Server = " + SMTP_HOST);
        log.info("\n- Sender = " + FROM);
        log.info("\n- Receiver = " + TO);
        log.info("\n- Application = " + ApplicationAcronym);
        log.info("\n- Gateway = " + GATEWAY);        
        
        // Assuming you are sending email from localhost
        String HOST = "localhost";
        
        // Get system properties
        Properties properties = System.getProperties();
        properties.setProperty(SMTP_HOST, HOST);
        
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        
        try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(FROM));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
         //message.addRecipient(Message.RecipientType.CC, new InternetAddress(FROM));

         // Set Subject: header field
         message.setSubject(" [liferay-sg-gateway] - [ " + GATEWAY + " ] ");

	 Date currentDate = new Date();
	 currentDate.setTime (currentDate.getTime());

         // Send the actual HTML message, as big as you like
         message.setContent(
	 "<br/><H4>" +         
	 "<img src=\"http://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc6/195775_220075701389624_155250493_n.jpg\" width=\"100\">Science Gateway Notification" +
	 "</H4><hr><br/>" +
         "<b>Description:</b> Notification for the application <b>[ " + ApplicationAcronym + " ]</b><br/><br/>" +         
         "<i>The application has been successfully submitted from the [ " + GATEWAY + " ]</i><br/><br/>" +
         "<b>TimeStamp:</b> " + currentDate + "<br/><br/>" +
	 "<b>Disclaimer:</b><br/>" +
	 "<i>This is an automatic message sent by the Science Gateway based on Liferay technology.<br/>" + 
	 "If you did not submit any jobs through the Science Gateway, please " +
         "<a href=\"mailto:" + FROM + "\">contact us</a></i>",         
	 "text/html");

         // Send message
         Transport.send(message);         
      } catch (MessagingException mex) { mex.printStackTrace(); }        
    }
    
    private Integer getUID()
    {
        Integer result = null;
        
        try {
            Process p = Runtime.getRuntime().exec("id -u");
            BufferedReader is = new BufferedReader(
                new InputStreamReader(p.getInputStream())
            );

            result = Integer.parseInt(is.readLine());
        }
        catch (java.io.IOException e) {
            log.error(e.getMessage());      
        }
        
        return result;
    }
    
    private void storeFile (String fileName, String[] fileContent) 
                              throws IOException 
    { 
        log.info("\n- Writing ASCII file [ " + fileName + " ]");
        
        BufferedWriter writer = 
                new BufferedWriter(new FileWriter(fileName));
                        
        writer.write("SIMTYPE=" + fileContent[0] +"\n");
        writer.write("USERNAME=" + fileContent[1] +"\n");
        writer.write("PORTAL=" + fileContent[2] +"\n");
        writer.write("EMAIL=" + fileContent[3] +"\n");
                            
        writer.close();                
    }

    private void getRobotProxy (String eTokenServer, 
                                String eTokenServerPort, 
                                String proxyId, 
                                String VO, 
                                String FQAN, 
                                String proxyRenewal) 
    {
        File proxyFile;
        Integer UID = getUID();

        proxyFile = new File("/tmp/x509up_u" + UID);
        
        String proxyContent="";
        
        try {
            
            URL proxyURL = 
                    new URL("http://" 
                    + eTokenServer
                    + ":"
                    + eTokenServerPort
                    + "/eTokenServer/eToken/" 
                    + proxyId 
                    + "?voms=" 
                    + VO 
                    + ":/" 
                    + VO 
                    + "&proxy-renewal=" 
                    + proxyRenewal
                    + "&disable-voms-proxy=false&rfc-proxy=true&cn-label=Empty");            
                    
            URLConnection proxyConnection = proxyURL.openConnection();
            proxyConnection.setDoInput(true);
            
            InputStream proxyStream = proxyConnection.getInputStream();
            BufferedReader input = new BufferedReader(new  InputStreamReader(proxyStream));
            
            String line = "";
            while ((line = input.readLine()) != null)                                 
                proxyContent += line+"\n";
                        
            FileUtils.writeStringToFile(proxyFile, proxyContent);
        } catch (Exception e) { e.printStackTrace(); }        
    }
}
