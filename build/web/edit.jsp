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
<%@page import="javax.portlet.*"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  //
  // WRF 1.0.3 portlet preferences for the GirdEngine interaction
  //
  // These parameters are:  
  // o *_wrf_INFRASTRUCTURE  - The Infrastructure Acronym to be enabled
  // o *_wrf_TOPBDII         - The TopBDII hostname for accessing the Infrastructure
  // o *_wrf_WMS             - The WMProxy hostname for accessing the Infrastructure
  // o *_wrf_VONAME          - The VO name
  // o *_wrf_ETOKENSERVER    - The eTokenServer hostname to be contacted for 
  //                                    requesting grid proxies
  // o *_wrf_MYPROXYSERVER   - The MyProxyServer hostname for requesting 
  //                                    long-term grid proxies
  // o *_wrf_PORT            - The port on which the eTokenServer is listening
  // o *_wrf_ROBOTID         - The robotID to generate the grid proxy
  // o *_wrf_ROLE            - The FQAN for the grid proxy (if any)
  // o *_wrf_REWAL           - Enable the creation of a long-term proxy to a 
  //                                    MyProxy Server (default 'yes')
  // o *_wrf_DISABLEVOMS     - Disable the creation of a VOMS proxy (default 'no')
  //
  // o wrf_APPID             - The ApplicationID
  // o wrf_LOGLEVEL          - The portlet log level (INFO, VERBOSE)
  // o wrf_OUTPUT_PATH       - The path where store results
  // o wrf_SOFTWARE          - The Application Software requested by the application
  // o TRACKING_DB_HOSTNAME        - The Tracking DB server hostname 
  // o TRACKING_DB_USERNAME        - The username credential for login
  // o TRACKING_DB_PASSWORD        - The password for login
%>

<jsp:useBean id="dit_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_PASSWD" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="dit_wrf_LOGIN" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="garuda_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="garuda_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="chain_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="chain_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="fedcloud_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="fedcloud_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="eumed_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gisela_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="gisela_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="sagrid_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="sagrid_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="wrf_ENABLEINFRASTRUCTURE" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="wrf_APPID" class="java.lang.String" scope="request"/>
<jsp:useBean id="wrf_LOGLEVEL" class="java.lang.String" scope="request"/>
<jsp:useBean id="wrf_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="wrf_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>

<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>

<script type="text/javascript">
    
    //var EnabledInfrastructure = "<%= wrf_ENABLEINFRASTRUCTURE %>";    
    //console.log(EnabledInfrastructure);        
            
    $(document).ready(function() { 
                
        var dit_inputs = 1;        
        // ADDING a new WMS enpoint for the DIT infrastructure (MAX. 5)
        $('#adding_WMS_dit').click(function() {        
            ++dit_inputs;        
            if (dit_inputs>1 && dit_inputs<6) {
            var c = $('.cloned_dit_wrf_WMS:first').clone(true);            
            c.children(':text').attr('name','dit_wrf_WMS' );
            c.children(':text').attr('id','dit_wrf_WMS' );
            $('.cloned_dit_wrf_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the DIT infrastructure
        $('.btnDel_dit').click(function() {
        if (dit_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --dit_inputs;
            $(this).closest('.cloned_dit_wrf_WMS').remove();
            $('.btnDel_dit').attr('disabled',($('.cloned_dit_wrf_WMS').length < 2));
        }
        });
        
        $('.btnDel_dit2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --dit_inputs;
            $(this).closest('.cloned_cached_ditWMS').remove();
            $('.btnDel_dit2').attr('disabled',($('.cloned_cached_ditWMS').length < 2));
        }
        });
        
        var garuda_inputs = 1;        
        // ADDING a new WMS enpoint for the GARUDA infrastructure (MAX. 5)
        $('#adding_WMS_garuda').click(function() {        
            ++garuda_inputs;        
            if (garuda_inputs>1 && garuda_inputs<6) {
            var c = $('.cloned_garuda_wrf_WMS:first').clone(true);            
            c.children(':text').attr('name','garuda_wrf_WMS' );
            c.children(':text').attr('id','garuda_wrf_WMS' );
            $('.cloned_garuda_wrf_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the GARUDA infrastructure
        $('.btnDel_garuda').click(function() {
        if (garuda_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --garuda_inputs;
            $(this).closest('.cloned_garuda_wrf_WMS').remove();
            $('.btnDel_garuda').attr('disabled',($('.cloned_garuda_wrf_WMS').length < 2));
        }
        });
        
        $('.btnDel_garuda2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --garuda_inputs;
            $(this).closest('.cloned_cached_garudaWMS').remove();
            $('.btnDel_garuda2').attr('disabled',($('.cloned_cached_garudaWMS').length < 2));
        }
        });
                
        var chain_inputs = 1;        
        // ADDING a new WMS enpoint for the chain infrastructure (MAX. 10)
        $('#adding_WMS_chain').click(function() {        
            ++chain_inputs;        
            if (chain_inputs>1 && chain_inputs<11) {
            var c = $('.cloned_chain_wrf_WMS:first').clone(true);            
            c.children(':text').attr('name','chain_wrf_WMS' );
            c.children(':text').attr('id','chain_wrf_WMS' );
            $('.cloned_chain_wrf_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the chain infrastructure
        $('.btnDel_chain').click(function() {
        if (chain_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --chain_inputs;
            $(this).closest('.cloned_chain_wrf_WMS').remove();
            $('.btnDel_chain').attr('disabled',($('.cloned_chain_wrf_WMS').length < 2));
        }
        });
        
        $('.btnDel_chain2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --chain_inputs;
            $(this).closest('.cloned_cached_chainWMS').remove();
            $('.btnDel_chain2').attr('disabled',($('.cloned_cached_chainWMS').length < 2));
        }
        });
        
        var fedcloud_inputs = 1;        
        // ADDING a new WMS enpoint for the FEDCLOUD infrastructure (MAX. 10)
        $('#adding_WMS_fedcloud').click(function() {        
            ++fedcloud_inputs;        
            if (fedcloud_inputs>1 && fedcloud_inputs<11) {
            var c = $('.cloned_fedcloud_wrf_WMS:first').clone(true);            
            c.children(':text').attr('name','fedcloud_wrf_WMS' );
            c.children(':text').attr('id','fedcloud_wrf_WMS' );
            $('.cloned_fedcloud_wrf_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the FEDCLOUD infrastructure
        $('.btnDel_fedcloud').click(function() {
        if (fedcloud_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --fedcloud_inputs;
            $(this).closest('.cloned_fedcloud_wrf_WMS').remove();
            $('.btnDel_fedcloud').attr('disabled',($('.cloned_fedcloud_wrf_WMS').length < 2));
        }
        });
                
        $('.btnDel_fedcloud2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --fedcloud_inputs;
            $(this).closest('.cloned_cached_fedcloudWMS').remove();
            $('.btnDel_fedcloud2').attr('disabled',($('.cloned_cached_fedcloudWMS').length < 2));
        }
        });
                                
        var eumed_inputs = 1;        
        // ADDING a new WMS enpoint for the EUMED infrastructure (MAX. 5)
        $('#adding_WMS_eumed').click(function() {        
            ++eumed_inputs;        
            if (eumed_inputs>1 && eumed_inputs<6) {
            var c = $('.cloned_eumed_wrf_WMS:first').clone(true);            
            c.children(':text').attr('name','eumed_wrf_WMS' );
            c.children(':text').attr('id','eumed_wrf_WMS' );
            $('.cloned_eumed_wrf_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the EUMED infrastructure
        $('.btnDel_eumed').click(function() {
        if (eumed_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --eumed_inputs;
            $(this).closest('.cloned_eumed_wrf_WMS').remove();
            $('.btnDel_eumed').attr('disabled',($('.cloned_eumed_wrf_WMS').length < 2));
        }
        });
                
        $('.btnDel_eumed2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --eumed_inputs;
            $(this).closest('.cloned_cached_eumedWMS').remove();
            $('.btnDel_eumed2').attr('disabled',($('.cloned_cached_eumedWMS').length < 2));
        }
        });
        
        var gisela_inputs = 1;        
        // ADDING a new WMS enpoint for the GISELA infrastructure (MAX. 5)
        $('#adding_WMS_gisela').click(function() {        
            ++gisela_inputs;        
            if (gisela_inputs>1 && gisela_inputs<6) {
            var c = $('.cloned_gisela_wrf_WMS:first').clone(true);            
            c.children(':text').attr('name','gisela_wrf_WMS' );
            c.children(':text').attr('id','gisela_wrf_WMS' );
            $('.cloned_gisela_wrf_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the GISELA infrastructure
        $('.btnDel_gisela').click(function() {
        if (gisela_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --gisela_inputs;
            $(this).closest('.cloned_gisela_wrf_WMS').remove();
            $('.btnDel_gisela').attr('disabled',($('.cloned_gisela_wrf_WMS').length < 2));
        }
        });
        
        $('.btnDel_gisela2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --gisela_inputs;
            $(this).closest('.cloned_cached_giselaWMS').remove();
            $('.btnDel_gisela2').attr('disabled',($('.cloned_cached_giselaWMS').length < 2));
        }
        });
        
        var sagrid_inputs = 1;        
        // ADDING a new WMS enpoint for the SAGRID infrastructure (MAX. 5)
        $('#adding_WMS_sagrid').click(function() {        
            ++sagrid_inputs;        
            if (sagrid_inputs>1 && sagrid_inputs<6) {
            var c = $('.cloned_sagrid_wrf_WMS:first').clone(true);            
            c.children(':text').attr('name','sagrid_wrf_WMS' );
            c.children(':text').attr('id','sagrid_wrf_WMS' );
            $('.cloned_sagrid_wrf_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the SAGRID infrastructure
        $('.btnDel_sagrid').click(function() {
        if (sagrid_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --sagrid_inputs;
            $(this).closest('.cloned_sagrid_wrf_WMS').remove();
            $('.btnDel_sagrid').attr('disabled',($('.cloned_sagrid_wrf_WMS').length < 2));
        }
        });
        
        $('.btnDel_sagrid2').click(function() {
            if (confirm('Do you really want to delete the item ?')) {
            --sagrid_inputs;
            $(this).closest('.cloned_cached_sagridWMS').remove();
            $('.btnDel_sagrid2').attr('disabled',($('.cloned_cached_sagridWMS').length < 2));
        }
        });
        

        // Validate input form
        $('#WrfEditForm').validate({
            rules: {
                dit_wrf_INFRASTRUCTURE: {
                    required: true              
                },
                dit_wrf_LOGIN: {
                    required: true              
                },
                dit_wrf_PASSWD: {
                    required: true
                },
                dit_wrf_WMS: {
                    required: true
                },
                /*dit_wrf_MYPROXYSERVER: {
                    required: true
                },
                dit_wrf_ETOKENSERVER: {
                    required: true
                },                
                dit_wrf_PORT: {
                    required: true
                },
                dit_wrf_ROBOTID: {
                    required: true
                },*/                
                garuda_wrf_INFRASTRUCTURE: {
                    required: true              
                },
                garuda_wrf_VONAME: {
                    required: true              
                },
                garuda_wrf_WMS: {
                    required: true
                },
                garuda_wrf_ETOKENSERVER: {
                    required: true
                },                
                garuda_wrf_PORT: {
                    required: true
                },
                garuda_wrf_ROBOTID: {
                    required: true
                },
                
                
                chain_wrf_INFRASTRUCTURE: {
                    required: true              
                },
                chain_wrf_VONAME: {
                    required: true              
                },
                chain_wrf_WMS: {
                    required: true
                },
                chain_wrf_MYPROXYSERVER: {
                    required: true
                },
                chain_wrf_ETOKENSERVER: {
                    required: true
                },                
                chain_wrf_PORT: {
                    required: true
                },
                chain_wrf_ROBOTID: {
                    required: true
                },
                
                
                fedcloud_wrf_INFRASTRUCTURE: {
                    required: true              
                },
                fedcloud_wrf_VONAME: {
                    required: true              
                },
                fedcloud_wrf_WMS: {
                    required: true
                },
                fedcloud_wrf_MYPROXYSERVER: {
                    required: true
                },
                fedcloud_wrf_ETOKENSERVER: {
                    required: true
                },                
                fedcloud_wrf_PORT: {
                    required: true
                },
                fedcloud_wrf_ROBOTID: {
                    required: true
                },
                
                                
                eumed_wrf_INFRASTRUCTURE: {
                    required: true              
                },
                eumed_wrf_VONAME: {
                    required: true              
                },
                eumed_wrf_TOPBDII: {
                    required: true
                },
                eumed_wrf_WMS: {
                    required: true
                },
                eumed_wrf_MYPROXYSERVER: {
                    required: true
                },
                eumed_wrf_ETOKENSERVER: {
                    required: true
                },                
                eumed_wrf_PORT: {
                    required: true
                },
                eumed_wrf_ROBOTID: {
                    required: true
                },
                
                
                gisela_wrf_INFRASTRUCTURE: {
                    required: true              
                },
                gisela_wrf_VONAME: {
                    required: true              
                },
                gisela_wrf_TOPBDII: {
                    required: true
                },
                gisela_wrf_WMS: {
                    required: true
                },
                gisela_wrf_MYPROXYSERVER: {
                    required: true
                },
                gisela_wrf_ETOKENSERVER: {
                    required: true
                },                
                gisela_wrf_PORT: {
                    required: true
                },
                gisela_wrf_ROBOTID: {
                    required: true
                },
                
                
                sagrid_wrf_INFRASTRUCTURE: {
                    required: true              
                },
                sagrid_wrf_VONAME: {
                    required: true              
                },
                sagrid_wrf_TOPBDII: {
                    required: true
                },
                sagrid_wrf_WMS: {
                    required: true
                },
                sagrid_wrf_MYPROXYSERVER: {
                    required: true
                },
                sagrid_wrf_ETOKENSERVER: {
                    required: true
                },                
                sagrid_wrf_PORT: {
                    required: true
                },
                sagrid_wrf_ROBOTID: {
                    required: true
                },
                
                wrf_APPID: {
                    required: true              
                },
                wrf_LOGLEVEL: {
                    required: true              
                },
                wrf_OUTPUT_PATH: {
                    required: true              
                }
            },
            
            invalidHandler: function(form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors) {
                    $("#error_message").empty();
                    var message = errors == 1
                    ? ' You missed 1 field. It has been highlighted'
                    : ' You missed ' + errors + ' fields. They have been highlighted';                    
                    $('#error_message').append("<img width='30' src='<%=renderRequest.getContextPath()%>/images/Warning.png' border='0'>"+message);
                    $("#error_message").show();
                } else $("#error_message").hide();                
            },
            
            submitHandler: function(form) {
                   form.submit();
            }
        });
        
        $("#WrfEditForm").bind('submit', function () {            
            // Check if OPTIONS are NULL
            if ( !$('#dit_wrf_RENEWAL').is(':checked') && 
                 !$('#dit_wrf_DISABLEVOMS').is(':checked') 
             ) $('#dit_wrf_OPTIONS').val('NULL');
                 
            if ( !$('#garuda_wrf_RENEWAL').is(':checked') && 
                 !$('#garuda_wrf_DISABLEVOMS').is(':checked') 
             ) $('#garuda_wrf_OPTIONS').val('NULL');                 
                 
            if ( !$('#chain_wrf_RENEWAL').is(':checked') && 
                 !$('#chain_wrf_DISABLEVOMS').is(':checked') 
             ) $('#chain_wrf_OPTIONS').val('NULL');
                 
            if ( !$('#fedcloud_wrf_RENEWAL').is(':checked') && 
                  !$('#fedcloud_wrf_DISABLEVOMS').is(':checked') 
             ) $('#fedcloud_wrf_OPTIONS').val('NULL');
                 
            if ( !$('#eumed_wrf_RENEWAL').is(':checked') && 
                  !$('#eumed_wrf_DISABLEVOMS').is(':checked') 
             ) $('#eumed_wrf_OPTIONS').val('NULL');
                 
            if ( !$('#gisela_wrf_RENEWAL').is(':checked') && 
                  !$('#gisela_wrf_DISABLEVOMS').is(':checked') 
             ) $('#gisela_wrf_OPTIONS').val('NULL');                             
                 
            if ( !$('#sagrid_wrf_RENEWAL').is(':checked') && 
                  !$('#sagrid_wrf_DISABLEVOMS').is(':checked') 
             ) $('#sagrid_wrf_OPTIONS').val('NULL');
                 
             //("#wrf_ENABLEINFRASTRUCTURE").val(EnabledInfrastructure);                          
       });                
    });                
</script>

<br/>
<p style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    Please, select the Cloud Settings before to start</p>  

<!DOCTYPE html>
<form id="WrfEditForm"
      name="WrfEditForm"
      action="<portlet:actionURL><portlet:param name="ActionEvent" value="CONFIG_WRF_PORTLET"/></portlet:actionURL>" 
      method="POST">

<fieldset>
<legend>Portlet Settings</legend>
<div style="margin-left:15px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;" id="error_message"></div>
<br/>
<table id="results" border="0" width="620" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">

<!-- DIT -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="wrf_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= wrf_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='dit'}">
                <c:set var="results_dit" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_dit=='true'}">
            <input type="checkbox" 
                   id="dit_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="dit"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="dit_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="dit"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="dit_wrf_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="dit_wrf_INFRASTRUCTURE"
               name="dit_wrf_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="DIT" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The login credential to access the DIT Infrastructure" />
   
        <label for="dit_wrf_LOGIN">Login<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="dit_wrf_LOGIN"
               name="dit_wrf_LOGIN"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= dit_wrf_LOGIN %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The password credential to access the DIT Infrastructure" />
   
        <label for="dit_wrf_PASSWD">Password<em>*</em></label>
    </td>    
    <td>
        <input type="password" 
               id="dit_wrf_PASSWD"
               name="dit_wrf_PASSWD"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= dit_wrf_PASSWD %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The DIR cluster server hostname to access the Infrastructure" />
   
        <label for="dit_wrf_WMS">Cluster Server<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= dit_wrf_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_ditWMS">
            <input type="text"                
                   name="dit_wrf_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_dit2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a Cloud Resource Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_dit_wrf_WMS">
        <input type="text"                
               name="dit_wrf_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_dit" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new Cloud Resource Endopoint" />
        <img type="button" class="btnDel_dit" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a Cloud Resource Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="dit_wrf_MYPROXYSERVER">MyProxyServer</label>
    </td>
    <td>
        <input type="text" 
               id="dit_wrf_MYPROXYSERVER"
               name="dit_wrf_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= dit_wrf_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="dit_wrf_ETOKENSERVER">eTokenServer</label>
    </td>
    <td>
        <input type="text" 
               id="dit_wrf_ETOKENSERVER"
               name="dit_wrf_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= dit_wrf_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="dit_wrf_PORT">Port</label>
    </td>
    <td>
        <input type="text" 
               id="dit_wrf_PORT"
               name="dit_wrf_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="15px;" 
               value=" <%= dit_wrf_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="dit_wrf_ROBOTID">Serial Number</label>
    </td>
    <td>
        <input type="text" 
               id="dit_wrf_ROBOTID"
               name="dit_wrf_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= dit_wrf_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="dit_wrf_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="dit_wrf_ROLE"
               name="dit_wrf_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= dit_wrf_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="dit_wrf_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="dit_wrf_RENEWAL"
               name="dit_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= dit_wrf_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="dit_wrf_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="dit_wrf_DISABLEVOMS"
               name="dit_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= dit_wrf_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- GARUDA -->  
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="wrf_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= wrf_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='garuda'}">
                <c:set var="results_garuda" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_garuda=='true'}">
            <input type="checkbox" 
                   id="garuda_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="garuda"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="garuda_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="garuda"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="garuda_wrf_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="garuda_wrf_INFRASTRUCTURE"
               name="garuda_wrf_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="GARUDA" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="garuda_wrf_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="garuda_wrf_VONAME"
               name="garuda_wrf_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= garuda_wrf_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="garuda_wrf_TOPBDII">TopBDII</label>
    </td>    
    <td>
        <input type="text" 
               id="garuda_wrf_TOPBDII"
               name="garuda_wrf_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= garuda_wrf_TOPBDII %>" /> 
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="garuda_wrf_WMS">WSGRAM<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= garuda_wrf_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_garudaWMS">
            <input type="text"                
                   name="garuda_wrf_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_garuda2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WSGRAM Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_garuda_wrf_WMS">
        <input type="text"                
               name="garuda_wrf_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_garuda" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WSGRAM Endopoint" />
        <img type="button" class="btnDel_garuda" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WSGRAM Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="garuda_wrf_MYPROXYSERVER">MyProxyServer</label>
    </td>
    <td>
        <input type="text" 
               id="garuda_wrf_MYPROXYSERVER"
               name="garuda_wrf_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= garuda_wrf_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="garuda_wrf_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="garuda_wrf_ETOKENSERVER"
               name="garuda_wrf_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= garuda_wrf_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="garuda_wrf_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="garuda_wrf_PORT"
               name="garuda_wrf_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= garuda_wrf_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="garuda_wrf_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="garuda_wrf_ROBOTID"
               name="garuda_wrf_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= garuda_wrf_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="garuda_wrf_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="garuda_wrf_ROLE"
               name="garuda_wrf_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= garuda_wrf_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="garuda_wrf_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="garuda_wrf_RENEWAL"
               name="garuda_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= garuda_wrf_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="garuda_wrf_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="garuda_wrf_DISABLEVOMS"
               name="garuda_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= garuda_wrf_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>
    
<!-- CHAIN-REDS -->  
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="wrf_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= wrf_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='chain'}">
                <c:set var="results_chain" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_chain=='true'}">
            <input type="checkbox" 
                   id="chain_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="chain"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="chain_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="chain"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="chain_wrf_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="chain_wrf_INFRASTRUCTURE"
               name="chain_wrf_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="CHAIN-REDS" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="chain_wrf_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="chain_wrf_VONAME"
               name="chain_wrf_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= chain_wrf_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="chain_wrf_TOPBDII">TopBDII</label>
    </td>    
    <td>
        <input type="text" 
               id="chain_wrf_TOPBDII"
               name="chain_wrf_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= chain_wrf_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Cloud Resource hostname" />
   
        <label for="chain_wrf_WMS">Cloud Resource<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= chain_wrf_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_chainWMS">
            <input type="text"                
                   name="chain_wrf_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_chain2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a Cloud Resource Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_chain_wrf_WMS">
        <input type="text"                
               name="chain_wrf_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_chain" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new Cloud Resource Endopoint" />
        <img type="button" class="btnDel_chain" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a Cloud Resource Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="chain_wrf_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="chain_wrf_MYPROXYSERVER"
               name="chain_wrf_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= chain_wrf_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="chain_wrf_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="chain_wrf_ETOKENSERVER"
               name="chain_wrf_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= chain_wrf_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="chain_wrf_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="chain_wrf_PORT"
               name="chain_wrf_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= chain_wrf_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="chain_wrf_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="chain_wrf_ROBOTID"
               name="chain_wrf_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= chain_wrf_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="chain_wrf_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="chain_wrf_ROLE"
               name="chain_wrf_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= chain_wrf_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="chain_wrf_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="chain_wrf_RENEWAL"
               name="chain_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= chain_wrf_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="chain_wrf_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="chain_wrf_DISABLEVOMS"
               name="chain_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= chain_wrf_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- FEDCLOUD -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="wrf_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= wrf_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='fedcloud'}">
                <c:set var="results_fedcloud" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_fedcloud=='true'}">
            <input type="checkbox" 
                   id="fedcloud_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="fedcloud"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="fedcloud_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="fedcloud"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="FEDCLOUD_wrf_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="fedcloud_wrf_INFRASTRUCTURE"
               name="fedcloud_wrf_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="FEDCLOUD" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="fedcloud_wrf_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="fedcloud_wrf_VONAME"
               name="fedcloud_wrf_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= fedcloud_wrf_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="fedcloud_wrf_TOPBDII">TopBDII</label>
    </td>    
    <td>
        <input type="text" 
               id="fedcloud_wrf_TOPBDII"
               name="fedcloud_wrf_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= fedcloud_wrf_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Cloud Resource hostname" />
   
        <label for="fedcloud_wrf_WMS">Cloud Resource<em>*</em></label>
    </td>
    <td>
        <c:forEach var="wms" items="<%= fedcloud_wrf_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_fedcloudWMS">
            <input type="text"                
                   name="fedcloud_wrf_WMS"
                   id="fedcloud_wrf_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_fedcloud2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a Cloud Resource Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_fedcloud_wrf_WMS">
        <input type="text" 
               id="fedcloud_wrf_WMS"
               name="fedcloud_wrf_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_fedcloud" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new Cloud Resource Endopoint" />
        <img type="button" class="btnDel_fedcloud" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a Cloud Resource Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="fedcloud_wrf_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="fedcloud_wrf_MYPROXYSERVER"
               name="fedcloud_wrf_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= fedcloud_wrf_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="fedcloud_wrf_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="fedcloud_wrf_ETOKENSERVER"
               name="fedcloud_wrf_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= fedcloud_wrf_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="fedcloud_wrf_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="fedcloud_wrf_PORT"
               name="fedcloud_wrf_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= fedcloud_wrf_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="fedcloud_wrf_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="fedcloud_wrf_ROBOTID"
               name="fedcloud_wrf_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= fedcloud_wrf_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="fedcloud_wrf_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="fedcloud_wrf_ROLE"
               name="fedcloud_wrf_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= fedcloud_wrf_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="fedcloud_wrf_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="fedcloud_wrf_RENEWAL"
               name="fedcloud_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= fedcloud_wrf_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="fedcloud_wrf_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="fedcloud_wrf_DISABLEVOMS"
               name="fedcloud_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= fedcloud_wrf_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- EUMED -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="wrf_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= wrf_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='eumed'}">
                <c:set var="results_eumed" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_eumed=='true'}">
            <input type="checkbox" 
                   id="eumed_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="eumed"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="eumed_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="eumed"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="EUMED_wrf_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="eumed_wrf_INFRASTRUCTURE"
               name="eumed_wrf_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="EUMED" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="eumed_wrf_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="eumed_wrf_VONAME"
               name="eumed_wrf_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= eumed_wrf_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="eumed_wrf_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="eumed_wrf_TOPBDII"
               name="eumed_wrf_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_wrf_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="eumed_wrf_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        <c:forEach var="wms" items="<%= eumed_wrf_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_eumedWMS">
            <input type="text"                
                   name="eumed_wrf_WMS"
                   id="eumed_wrf_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_eumed2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a Cloud Resource Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_eumed_wrf_WMS">
        <input type="text" 
               id="eumed_wrf_WMS"
               name="eumed_wrf_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_eumed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new Cloud Resource Endopoint" />
        <img type="button" class="btnDel_eumed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a Cloud Resource Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="eumed_wrf_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_wrf_MYPROXYSERVER"
               name="eumed_wrf_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_wrf_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="eumed_wrf_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_wrf_ETOKENSERVER"
               name="eumed_wrf_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_wrf_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="eumed_wrf_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_wrf_PORT"
               name="eumed_wrf_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= eumed_wrf_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="eumed_wrf_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_wrf_ROBOTID"
               name="eumed_wrf_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_wrf_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="eumed_wrf_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="eumed_wrf_ROLE"
               name="eumed_wrf_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= eumed_wrf_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="eumed_wrf_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="eumed_wrf_RENEWAL"
               name="eumed_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= eumed_wrf_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="eumed_wrf_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="eumed_wrf_DISABLEVOMS"
               name="eumed_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= eumed_wrf_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- GISELA -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="wrf_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= wrf_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gisela'}">
                <c:set var="results_gisela" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gisela=='true'}">
            <input type="checkbox" 
                   id="gisela_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="gisela"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="gisela_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="gisela"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="GISELA_wrf_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gisela_wrf_INFRASTRUCTURE"
               name="gisela_wrf_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="GISELA" />
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="gisela_wrf_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="gisela_wrf_VONAME"
               name="gisela_wrf_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gisela_wrf_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="gisela_wrf_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gisela_wrf_TOPBDII"
               name="gisela_wrf_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gisela_wrf_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="gisela_wrf_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        
        <c:forEach var="wms" items="<%= gisela_wrf_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_giselaWMS">
            <input type="text"                
                   name="gisela_wrf_WMS"
                   id="gisela_wrf_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_gisela2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a Cloud Resource Endopoint" />
            </div>
            </c:if>
        </c:forEach>
        
        <div style="margin-bottom:4px;" class="cloned_gisela_wrf_WMS">
        <input type="text" 
               id="gisela_wrf_WMS"
               name="gisela_wrf_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_gisela" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new Cloud Resource Endopoint" />
        <img type="button" class="btnDel_gisela" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a Cloud Resource Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="gisela_wrf_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gisela_wrf_MYPROXYSERVER"
               name="gisela_wrf_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gisela_wrf_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="gisela_wrf_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gisela_wrf_ETOKENSERVER"
               name="gisela_wrf_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gisela_wrf_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="gisela_wrf_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gisela_wrf_PORT"
               name="gisela_wrf_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gisela_wrf_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="gisela_wrf_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gisela_wrf_ROBOTID"
               name="gisela_wrf_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gisela_wrf_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="gisela_wrf_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="gisela_wrf_ROLE"
               name="gisela_wrf_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= gisela_wrf_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="gisela_wrf_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gisela_wrf_RENEWAL"
               name="gisela_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= gisela_wrf_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="gisela_wrf_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gisela_wrf_DISABLEVOMS"
               name="gisela_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= gisela_wrf_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- SAGRID -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="wrf_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= wrf_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='sagrid'}">
                <c:set var="results_sagrid" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_sagrid=='true'}">
            <input type="checkbox" 
                   id="sagrid_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="sagrid"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="sagrid_wrf_ENABLEINFRASTRUCTURE"
                   name="wrf_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="sagrid"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="SAGRID_wrf_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="sagrid_wrf_INFRASTRUCTURE"
               name="sagrid_wrf_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="SAGRID" />
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="sagrid_wrf_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="sagrid_wrf_VONAME"
               name="sagrid_wrf_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= sagrid_wrf_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="sagrid_wrf_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="sagrid_wrf_TOPBDII"
               name="sagrid_wrf_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sagrid_wrf_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="sagrid_wrf_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        
        <c:forEach var="wms" items="<%= sagrid_wrf_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_sagridWMS">
            <input type="text"                
                   name="sagrid_wrf_WMS"
                   id="sagrid_wrf_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_sagrid2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a Cloud Resource Endopoint" />
            </div>
            </c:if>
        </c:forEach>
        
        <div style="margin-bottom:4px;" class="cloned_sagrid_wrf_WMS">
        <input type="text" 
               id="sagrid_wrf_WMS"
               name="sagrid_wrf_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_sagrid" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new Cloud Resource Endopoint" />
        <img type="button" class="btnDel_sagrid" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a Cloud Resource Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="sagrid_wrf_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_wrf_MYPROXYSERVER"
               name="sagrid_wrf_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sagrid_wrf_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="sagrid_wrf_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_wrf_ETOKENSERVER"
               name="sagrid_wrf_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sagrid_wrf_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="sagrid_wrf_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_wrf_PORT"
               name="sagrid_wrf_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= sagrid_wrf_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="sagrid_wrf_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_wrf_ROBOTID"
               name="sagrid_wrf_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sagrid_wrf_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="sagrid_wrf_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_wrf_ROLE"
               name="sagrid_wrf_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= sagrid_wrf_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="sagrid_wrf_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="sagrid_wrf_RENEWAL"
               name="sagrid_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= sagrid_wrf_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="sagrid_wrf_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="sagrid_wrf_DISABLEVOMS"
               name="sagrid_wrf_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= sagrid_wrf_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- LAST -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The ApplicationID" />
   
        <label for="wrf_APPID">AppID<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="wrf_APPID"
               name="wrf_APPID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= wrf_APPID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Log Level of the portlet (E.g.: VERBOSE, INFO)" />
   
        <label for="wrf_LOGLEVEL">Log Level<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="wrf_LOGLEVEL"
               name="wrf_LOGLEVEL"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= wrf_LOGLEVEL %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The output path of the server's file-system where download results" />
   
        <label for="wrf_OUTPUT_PATH">Output Path<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="wrf_OUTPUT_PATH"
               name="wrf_OUTPUT_PATH"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= wrf_OUTPUT_PATH %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Application Software TAG" />
   
        <label for="wrf_SOFTWARE">SoftwareTAG</label>
    </td>
    <td>
        <input type="text" 
               id="wrf_SOFTWARE"
               name="wrf_SOFTWARE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= wrf_SOFTWARE %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Tracking DB Server Hostname" />
   
        <label for="TRACKING_DB_HOSTNAME">HostName</label>
    </td>
    <td>
        <input type="text" 
               id="TRACKING_DB_HOSTNAME"
               name="TRACKING_DB_HOSTNAME"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_HOSTNAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The username credential for login the Tracking DB" />
   
        <label for="TRACKING_DB_USERNAME">UserName</label>
    </td>
    <td>
        <input type="text" 
               id="TRACKING_DB_USERNAME"
               name="TRACKING_DB_USERNAME"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_USERNAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The password credential for login  the Tracking DB" />
   
        <label for="TRACKING_DB_PASSWORD">Password</label>
    </td>
    <td>
        <input type="password" 
               id="TRACKING_DB_PASSWORD"
               name="TRACKING_DB_PASSWORD"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_PASSWORD %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The SMTP Server for sending notification" />
   
        <label for="SMTP_HOST">SMTP</label>
    </td>
    <td>
        <input type="text" 
               id="SMTP_HOST"
               name="SMTP_HOST"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= SMTP_HOST %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The email address for sending notification" />
   
        <label for="Sender">Sender</label>
    </td>
    <td>
        <input type="text" 
               id="SENDER_MAIL"
               name="SENDER_MAIL"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= SENDER_MAIL %>" />
    </td>    
</tr>

<!-- Buttons -->
<tr>            
    <tr><td>&nbsp;</td></tr>
    <td align="left">    
    <input type="image" src="<%= renderRequest.getContextPath()%>/images/save.png"
           width="50"
           name="Submit" title="Save the portlet settings" />        
    </td>
</tr>  

</table>
<br/>
<div id="pageNavPosition" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">   
</div>
</fieldset>
           
<script type="text/javascript">
    var pager = new Pager('results', 13); 
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
</script>
</form>
