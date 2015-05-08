<%
/**************************************************************************
Copyright (c) 2011-2014:
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
****************************************************************************/
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>

<script type="text/javascript">
    $(document).ready(function() {
              
    $('.slideshow').cycle({
	fx: 'fade' // choose your transition type (fade, scrollUp, shuffle, etc)
    });
    
    // Roller
    $('#wrf_footer').rollchildren({
        delay_time         : 3000,
        loop               : true,
        pause_on_mouseover : true,
        roll_up_old_item   : true,
        speed              : 'slow'   
    });
    
    //var $tumblelog = $('#tumblelog');  
    $('#tumblelog').imagesLoaded( function() {
      $tumblelog.masonry({
        columnWidth: 440
      });
    });
});
</script>
                    
<br/>

<fieldset>
<legend>About the portlet</legend>

<section id="content">

<div id="tumblelog" class="clearfix">
    
  <div class="story col3">    
  <p style="text-align:justify; position: relative;">
  With this service it is possible to execute the Weather Research and Forecasting (WRF) application 
  on Virtual Machines (VMs) deployed on standard-based federated cloud or on the worker nodes
  of the Grid infrastructure.<br/><br/>
~ The present service is based on the following standards and software frameworks: <br/><br/>
  1.) <a href="http://occi-wg.org/">OCCI</a> interface which comprises a set of open community-led 
  specifications delivered through the Open Grid Forum. <br/><br/>
  OCCI is a Protocol and API for all kinds of Management tasks. OCCI was originally initiated 
  to create a remote management API for IaaS model based Services, allowing for the development 
  of interoperable tools for common tasks including deployment, autonomic scaling and monitoring. 
  It has since evolved into a flexible API with a strong focus on <b>integration, portability, 
  interoperability</b> and <b>innovation</b> while still offering a high degree of extensibility. 
  <br/><br/>
  The current release of the OCCI is suitable to serve many other models in addition to <b>IaaS</b>, 
  including <b>PaaS</b> and, in particular, <b>SaaS</b> as shown by the present service.
  <br/><br/>
  
  2.) <a href="http://grid.in2p3.fr/jsaga/">JSAGA</a> is a Java implementation of the Simple API 
  for Grid Applications (SAGA) specification made by the Open Grid Forum (<a href="http://www.ogf.org/">OGF</a>)
  <br/></br>
  
  3.) <a href="http://link.springer.com/article/10.1007%2Fs10723-012-9242-3#page-1">Catania Science Gateways Framework</a>
  <br/>    
  </p>  
  </div>

  <div class="story col3">            
    <table border="0">
    <tr>      
    <td width="100">
    <a href="http://www.wrf-model.org">
    <img width="130" src="<%=renderRequest.getContextPath()%>/images/wrf-model-logo.png"/>
    </a>
    </td>    
        
    <td> &nbsp;&nbsp; </td>
    <td>       
    <p align="justify">    
    The Weather Research and Forecasting (WRF) Model is a next-generation meso-scale numerical weather 
    prediction system designed to serve both atmospheric research and operational forecasting needs. <br/>
    It features two dynamical cores, a data assimilation system, and a software architecture allowing 
    for parallel computation and system extensibility. <br/>
    The model serves a wide range of meteorological applications across scales ranging from meters to thousands 
    of kilometers.    
    <p/>
    </td>
    </tr>
    
    <!--tr>
    <td width="100">
    <a href="http://www.r-project.org/">
    <img width="85" src="<%=renderRequest.getContextPath()%>/images/logo-R.png"/>
    </a>
    </td>    
        
    <td> &nbsp;&nbsp; </td>
    <td>
    <u>The GNU R-2.15.3 Environment</u><br/>
    <p align="justify">    
    R is a free software environment for statistical computing and graphics. 
    R provides a wide variety of statistical (linear and nonlinear modelling, 
    classical statistical tests, time-series analysis, classification, clustering,
    etc) and graphical techniques, and is highly extensible.<br/><br/><p/>
    <p/>
    </td>       
    </tr-->
    
    <!--tr>
    <td width="100">
    <a href="#">
    <img width="90" src="<%=renderRequest.getContextPath()%>/images/helloworld.png"/>
    </a>
    </td>    
        
    <td> &nbsp;&nbsp; </td>
    <td>
    <u>Sequential "Hello World!"</u><br/>
    <p align="justify">
    This application just outputs the name of the Virtual Machine where the job has been executed.
    <br/><br/><p/>
    </td>       
    </tr-->
    
    </table>    
  </div>                 
             
  <!--div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
      <h3>About Data Audification</h3>      
        <p style="text-align:justify;">
        Data audification is the representation of data by sound signals; it can 
        be considered as the acoustic counterpart of data graphic visualization, 
        a mathematical mapping of information from data sets to sounds. In the 
        past twenty years the word audification has acquired a new meaning in 
        the world of computer music, computer science, and auditory display 
        application development. Data audification is currently used in several 
        fields, for different purposes: science and engineering, education and
        training, in most of the cases to provide a quick and effective data 
        analysis and interpretation tool.<br/>        
        Although most data analysis techniques are exclusively visual in nature 
        (i.e. are based on the possibility of looking at graphical representations), 
        data presentation and exploration systems could benefit greatly from the 
        addition of sonification capabilities. [..] <br/>
        For further information about the sonification process, please download the <a href="http://bit.ly/uaDPAJ">White paper (PDF)</a>
        </p>
 </div-->
                          
  <!--div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
  <h2>MIDI Sonification of Mt. Etna Volcano Seismograms</h2>           
  <br/>
  <object height="81" width="100%" style="padding: 5px; border: 1px solid #ccc; background-color: #eee;">
  <param name="wmode" value="transparent">
  <param name="movie" value="http://player.soundcloud.com/player.swf?url=http%3A%2F%2Fapi.soundcloud.com%2Ftracks%2F29302665&amp;g=1&amp;show_comments=false&amp;auto_play=false&amp;color=000000">             
  </param>
  <embed height="81" src="http://player.soundcloud.com/player.swf?url=http%3A%2F%2Fapi.soundcloud.com%2Ftracks%2F29302665&amp;g=1&amp;show_comments=false&amp;auto_play=false&amp;color=000000" type="application/x-shockwave-flash" width="100%"> 
  </embed> 
  </object>                
  </div-->
    
  <!--div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
  <table border="0">
  <tr>
  <td-->              
    <!--iframe src="http://www.youtube.com/v/8HjRZ9JWnR0?version=3&feature=player_detailpage" 
            style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
            width="280" height="210" frameborder="0" 
            webkitAllowFullScreen mozallowfullscreen allowFullScreen>        
    </iframe-->            
    <!--a href="http://www.youtube.com/v/8HjRZ9JWnR0">
        <img align="center" width="280" height="210"
             src="<%= renderRequest.getContextPath()%>/images/sonification2_video.jpg" 
             border="0"/>
    </a>   
  </td>
  <td> &nbsp;&nbsp; </td>
  <td>
      <p style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; text-align:justify;">
          The Sonification application won the best poster and demo awards at the 
          <a href="http://cf2012.egi.eu/">EGI Community Forum, Munich, 2012</a>
          <br/><br/>The demo was about how we can create some music from text 
          (or tweets) using the <a href="http://www.egi.eu/">EGI Infrastructure</a>
      </p>
  </td>
  </tr>
  </table>
  </div-->
            
  <!--div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
  <h2>3D Image Rendering as Data Representation</h2>
  <table border="0">
  <tr>
    <td> 
    <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres1.png">
    <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres1.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
    </a>
    </td>
    <td>
    <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres5.png">
    <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres5.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
    </a>         
    </td>
    <td>
        <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres3.png">
        <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres3.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
        </a>
    </td>
    <td>
        <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres4.png">
        <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres4.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
        </a>
    </td>
  </tr>
  </table>
  </div-->

  <div class="story col3" 
       style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
      <h2><a href="mailto:info@riccardo.bruno@ct.infn.it">
      <img width="100" 
           src="<%= renderRequest.getContextPath()%>/images/contact6.jpg" 
           border="0" title="Get in touch with us"/></a>Contacts (in family name alphabetic order)</h2>
      <p style="text-align:justify;">Roberto BARBERA<i> &mdash; INFN Catania, Italy</i></p>
      <p style="text-align:justify;">Riccardo BRUNO<i> &mdash; INFN Catania, Italy</i></p>
      <p style="text-align:justify;">Giuseppe LA ROCCA<i> &mdash; INFN Catania, Italy</i></p>      
      <p style="text-align:justify;">Bjorn PEHRSON<i> &mdash; KTH, Stockholm, Sweden</i></p>
      <p style="text-align:justify;">Torleif MARKUSSEN LUNDE<i> &mdash; University of Bergen, Norway</i></p>
  </div>               
    
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
        <h2>Links</h2>
        <table border="0">                        
            <tr>                
            <td>
            <p align="justify">
            <a href="http://grid.in2p3.fr/jsaga/">
                <img align="center" width="200" 
                     src="<%= renderRequest.getContextPath()%>/images/logo-jsaga.png" 
                     border="0" 
                     title="JSAGA is a Java implementation of the Simple API for Grid Applications specification from the OGF" />
            </a>
            </p>
            </td>
                    
            <td>
            <p align="justify">
            <a href="http://occi-wg.org/">
                <img align="center" width="200"
                     src="<%= renderRequest.getContextPath()%>/images/OCCI-logo.png" 
                     border="0" title="Open Cloud Computing Interface (OCCI)" />
            </a>
            </p>            
            </td>
            
            <td>
            <p align="justify">
            <a href="http://www.catania-science-gateways.it">
                <img align="center" width="100"
                     src="<%= renderRequest.getContextPath()%>/images/CataniaScienceGateways.png" 
                     border="0" title="The Catania Science Gateways Framework" />
            </a>
            </p>
            </td>                        
            </tr>
            
            <tr>                
            <td>
            <p align="justify">
            <a href="http://www.infn.it/">
            <img align="center" width="130" heigth="140"
                 src="<%= renderRequest.getContextPath()%>/images/Infn_Logo.jpg" 
                 border="0" title="INFN - Istituto Nazionale di Fisica Nucleare" />
            </a>
            </p>
            </td>
            
            <td>
            <p align="justify">
            <a href="http://www.kth.se/">
            <img align="center" width="100" heigth="100"
                 src="<%= renderRequest.getContextPath()%>/images/logo-kth.png" 
                 border="0" title="KTH Royal Institute of Technology" />
            </a>
            </p>
            </td>
            
            <td>
                <p align="justify">
                <a href="http://www.uib.no/">
                <img align="center" width="120" 
                     src="<%= renderRequest.getContextPath()%>/images/uiblogo.svg" 
                 title="University of Bergen" />
                </a>   
                </p>
            </td>
            
            <!--td>
                <p align="justify">
                <a href="http://www.egi.eu/projects/egi-inspire/">
                <img align="center" width="100" 
                     src="<%= renderRequest.getContextPath()%>/images/EGI_Logo_RGB_315x250px.png" 
                 title="EGI - The European Grid Infrastructure" />
                </a>   
                </p>
            </td-->                        
            </tr>
        </table>   
  </div>
</div>
</section>
</fieldset>
           
<div id="wrf_footer" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    <div>WRF portlet ver. 1.0.3</div>
    <div>Italian National Institute of Nuclear Physics (INFN), Division of Catania, Italy</div>
    <div>Copyright © 2014. All rights reserved</div>    
    <div>This work has been partially supported by
    <a href="http://www.chain-project.eu/">
    <img width="45" 
         border="0"
         src="<%= renderRequest.getContextPath()%>/images/chain-logo-220x124.png" 
         title="The CHAIN-REDS EU FP7 Project"/>
    </a>
    </div>
</div>
