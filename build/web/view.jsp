<%
/**************************************************************************
Copyright (c) 2011-2014:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy
    
See http://www.infn.it and and http://www.consorzio-cometa.it for details 
on the copyright holders.
    
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
**************************************************************************/
%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.model.Company" %>
<%@ page import="javax.portlet.*" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  Company company = PortalUtil.getCompany(request);
  String gateway = company.getName();
%>

<jsp:useBean id="GPS_table" class="java.lang.String" scope="request"/>
<jsp:useBean id="GPS_queue" class="java.lang.String" scope="request"/>

<jsp:useBean id="dit_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="dit_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="garuda_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="chain_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="chain_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="fedcloud_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="fedcloud_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_wrf_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gisela_wrf_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_wrf_WMS" class="java.lang.String" scope="request"/>
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
<jsp:useBean id="sagrid_wrf_WMS" class="java.lang.String" scope="request"/>
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
<jsp:useBean id="wrf_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="wrf_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>
<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>

<script type="text/javascript">
    
    var latlng2markers = [];
    var icons = [];
    
    icons["plus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/plus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["minus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/minus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["ce"] = new google.maps.MarkerImage(            
            '<%= renderRequest.getContextPath()%>/images/ce-run.png',
            new google.maps.Size(16,16),
            new google.maps.Point(0,0),
            new google.maps.Point(8,8));            
    
    function hideMarkers(markersMap,map) 
    {
            for( var k in markersMap) 
            {
                if (markersMap[k].markers.length >1) {
                    var n = markersMap[k].markers.length;
                    var centerMark = new google.maps.Marker({
                        title: "Coordinates:" + markersMap[k].markers[0].getPosition().toString(),
                        position: markersMap[k].markers[0].getPosition(),
                        icon: icons["plus"]
                    });
                    for ( var i=0 ; i<n ; i++ ) 
                        markersMap[k].markers[i].setVisible(false);
                    
                    centerMark.setMap(map);
                    google.maps.event.addListener(centerMark, 'click', function() {
                        var markersMap = latlng2markers;
                        var k = this.getPosition().toString();
                        var visibility = markersMap[k].markers[0].getVisible();
                        if (visibility == false ) {
                            splitMarkersOnCircle(markersMap[k].markers,
                            markersMap[k].connectors,
                            this.getPosition(),
                            map
                        );
                            this.setIcon(icons["minus"]);
                        }
                        else {
                            var n = markersMap[k].markers.length;
                            for ( var i=0 ; i<n ; i++ ) {
                                markersMap[k].markers[i].setVisible(false);
                                markersMap[k].connectors[i].setMap(null);
                            }
                            markersMap[k].connectors = [];
                            this.setIcon(icons["plus"]);
                        }
                    });
                }
            }
    }
    
    function splitMarkersOnCircle(markers, connectors, center, map) 
    {
            var z = map.getZoom();
            var r = 60.0 / (z*Math.exp(z/2));            
            var n = markers.length;
            var dtheta = 2.0 * Math.PI / n;            
            var theta = 0;
            
            for ( var i=0; i<n; i++ ) 
            {
                var X = center.lat() + r*Math.cos(theta);
                var Y = center.lng() + r*Math.sin(theta);
                //console.log(dtheta); console.log(X); console.log(Y);
                markers[i].setPosition(new google.maps.LatLng(X-0.5,Y));
                markers[i].setVisible(true);
                theta += dtheta;
                
                var line = new google.maps.Polyline({
                    path: [center,new google.maps.LatLng(X,Y)],
                    clickable: false,
                    strokeColor: "#0000ff",
                    strokeOpacity: 1,
                    strokeWeight: 2
                });
                
                line.setMap(map);
                connectors.push(line);
            }
    }
    
    function updateAverage(name) {
        
        $.getJSON('<portlet:resourceURL><portlet:param name="action" value="get-ratings"/></portlet:resourceURL>&wrf_CR='+name,
        function(data) {                                               
            console.log(data.avg);
            $("#fake-stars-on").width(Math.round(parseFloat(data.avg)*20)); // 20 = 100(px)/5(stars)
            $("#fake-stars-cap").text(new Number(data.avg).toFixed(2) + " (" + data.cnt + ")");
        });                
    }
    
    // Create the Google Map JavaScript APIs V3
    function initialize(lat, lng, zoom) {
        console.log(lat);
        console.log(lng);
        console.log(zoom);
        
        var myOptions = {
            zoom: zoom,
            center: new google.maps.LatLng(lat, lng),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        
        var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);  
        var image = new google.maps.MarkerImage('<%= renderRequest.getContextPath() %>/images/ce-run.png');
        
        var strVar="";
        strVar += "<table>";
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Vote the resource availability";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<tr><td>\&nbsp;\&nbsp;";
        strVar += "<\/td><\/tr>";
        
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Rating: <span id=\"stars-cap\"><\/span>";
        strVar += "<div id=\"stars-wrapper2\">";
        strVar += "<select name=\"selrate\">";
        strVar += "<option value=\"1\">Very poor<\/option>";
        strVar += "<option value=\"2\">Not that bad<\/option>";
        strVar += "<option value=\"3\" selected=\"selected\">Average<\/option>";
        strVar += "<option value=\"4\">Good<\/option>";
        strVar += "<option value=\"5\">Perfect<\/option>";
        strVar += "<\/select>";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";

        strVar += "<tr>";        
        strVar += "<td>";
        strVar += "Average: <span id=\"fake-stars-cap\"><\/span>";
        strVar += "";
        strVar += "<div id=\"fake-stars-off\" class=\"stars-off\" style=\"width:100px\">";
        strVar += "<div id=\"fake-stars-on\" class=\"stars-on\"><\/div>";
        strVar += "";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<\/table>";
    
        var data = <%= GPS_table %>;
        var queues = <%= GPS_queue %>;
        
        var infowindow = new google.maps.InfoWindow();
        google.maps.event.addListener(infowindow, 'closeclick', function() {
            this.setContent('');
        });
        
        var infowindowOpts = { 
            maxWidth: 200
        };
               
       infowindow.setOptions(infowindowOpts);       
       
       var markers = [];
       for (var key in data){
                        
            var LatLong = new google.maps.LatLng(parseFloat(data[key]["LAT"]), 
                                                 parseFloat(data[key]["LNG"]));                    
            
            // Identify locations on the map
            var marker = new google.maps.Marker ({
                animation: google.maps.Animation.DROP,
                position: LatLong,
                icon: image,
                title : key
            });    
  
            // Add the market to the map
            marker.setMap(map);
            
            var latlngKey=marker.position.toString();
            if ( latlng2markers[latlngKey] == null )
                latlng2markers[latlngKey] = {markers:[], connectors:[]};
            
            latlng2markers[latlngKey].markers.push(marker);
            markers.push(marker);
        
            google.maps.event.addListener(marker, 'click', function() {
             
            var cr_hostname = this.title;
            
            google.maps.event.addListenerOnce(infowindow, 'domready', function() {
                                        
                    $("#stars-wrapper2").stars({
                        cancelShow: false, 
                        oneVoteOnly: true,
                        inputType: "select",
                        callback: function(ui, type, value)
                        { 
                            $.getJSON('<portlet:resourceURL><portlet:param name="action" value="set-ratings"/></portlet:resourceURL>' +
                                '&wrf_CR=' + cr_hostname + 
                                '&vote=' + value);
                            
                            updateAverage(cr_hostname);                      
                        }
                    });
                    
                    updateAverage(cr_hostname);               
                });              
                                                
                infowindow.setContent('<h3>' + cr_hostname + '</h3><br/>' + strVar);
                infowindow.open(map, this);
                                           
                var CE_queue = (queues[cr_hostname]["QUEUE"]);
                $('#wrf_CR').val(CE_queue);
                
                marker.setMap(map);
            }); // function                             
        } // for
        
        hideMarkers(latlng2markers, map);
        var markerCluster = new MarkerClusterer(map, markers, {maxZoom: 3, gridSize: 20});
    }
    
</script>

<script type="text/javascript">  
    var EnabledInfrastructure = null;           
    var infrastructures = new Array();  
    var i = 0;    
    
    <c:forEach items="<%= wrf_ENABLEINFRASTRUCTURE %>" var="current">
    <c:choose>
    <c:when test="${current!=null}">
        infrastructures[i] = '<c:out value='${current}'/>';       
        i++;  
    </c:when>
    </c:choose>
    </c:forEach>
        
    for (var i = 0; i < infrastructures.length; i++) {
       console.log("Reading array = " + infrastructures[i]);
       if (infrastructures[i]=="dit") EnabledInfrastructure='dit';
       if (infrastructures[i]=="garuda") EnabledInfrastructure='garuda';
       if (infrastructures[i]=="chain") EnabledInfrastructure='chain';
       if (infrastructures[i]=="fedcloud") EnabledInfrastructure='fedcloud';
       if (infrastructures[i]=="eumed")  EnabledInfrastructure='eumed';
       if (infrastructures[i]=="gisela") EnabledInfrastructure='gisela';
       if (infrastructures[i]=="sagrid") EnabledInfrastructure='sagrid';
    }
    
    var NMAX = infrastructures.length;
    //console.log (NMAX);
    
    $(document).ready(function() 
    {           
        // Add the default message in the About the demo
        $('#wrf_textarea_WRF').html("Add here your macro file or use the default demo");        
        
        // Toggling the hidden div for the first time.        
        if ($('#divToToggle').css('display') == 'block')
            $('#divToToggle').toggle();
        
        var lat; var lng; var zoom;
        var found=0;
        
        if (parseInt(NMAX)>1) { 
            console.log ("More than one infrastructure have been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='70' src='<%= renderRequest.getContextPath()%>/images/world.png' border='0'> More than one infrastructure (grid/cloud) have been configured!");
            //lat=19;lng=14;zoom=3; 
            //lat=25;lng=20;zoom=3;
            lat=47;lng=12;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='dit') {
            console.log ("Start up: enabled the DIT Cluster Infrastructure!");
            $('#dit_wrf_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=34;lng=20;zoom=3;
            found=1;
        } else if (EnabledInfrastructure=='garuda') {
            console.log ("Start up: enabling garuda!");
            $('#garuda_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=29.15;lng=77.41;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='chain') {        
            console.log ("Start up: enabled the CHAIN-REDS Cloud testbed!");
            $('#chain_wrf_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=42;lng=12;zoom=5;
            found=1;            
        } else if (EnabledInfrastructure=='eumed') {       
            console.log ("Start up: enabled the Mediterranen Cloud Infrastructure!");
            $('#eumed_wrf_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=34;lng=20;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='fedcloud') {
            console.log ("Start up: enabled the Indian Cloud Infrastructure!");
            $('#fedcloud_wrf_ENABLEINFRASTRUCTURE').attr('checked','checked');            
            lat=47;lng=13;zoom=4;
            found=1;    
        } else if (EnabledInfrastructure=='gisela') {        
            console.log ("Start up: enabled the Latin America Cloud Infrastructure!");
            $('#gisela_wrf_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=2;lng=-36;zoom=2;
            found=1;            
        } else if (EnabledInfrastructure=='sagrid') {
            console.log ("Start up: enabled the SAgrid Cloud Infrastructure!");
            $('#sagrid_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=-16;lng=-24;zoom=2;
            found=1;
        }                 
                
        if (found==0) { 
            console.log ("None of the cloud infrastructures have been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> None of the available cloud infrastructures have been configured!");
        }                
        
        var accOpts = {
            change: function(e, ui) {                       
                $("<div style='width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;'>").addClass("notify ui-corner-all").text(ui.newHeader.find("a").text() +
                    " was activated... ").appendTo("#error_message").fadeOut(2500, function(){ $(this).remove(); });               
                // Get the active option                
                var active = $("#accordion").accordion("option", "active");
                if (active==1) initialize(lat, lng, zoom);
            },
            autoHeight: false
        };
        
        // Create the accordions
        //$("#accordion").accordion({ autoHeight: false });
        $("#accordion").accordion(accOpts);
                
        // Check file input size with jQuery (Max. 2.5MB)
        $('input[type=file][name=\'wrf_file_WRF\']').bind('change', function() {
            if (this.files[0].size/1000 > 25600) {     
                // Remove the img and text (if any)
                $("#error_message img:last-child").remove();
                $("#error_message").empty();
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> The user demo file must be less than 2.5MB");
                $("#error_message").css({"color":"red","font-size":"14px"});
                // Removing the input file
                $('input[type=\'file\'][name=\'wrf_file_WRF\']').val('');
                return false;
            }           
        });
        
        $("#commentForm").bind('submit', function() 
        {        
            var flag=true;         
            var extension=true;            
            
            // Remove the img and text error (if any)
            $("#error_message img:last-child").remove();
            $("#error_message").empty();
            
            // Validate input form
            if ($("#wrf_vmname").val() == "") flag=false; 
            
            if (flag) {
                $("#dialog-message").append("<p>Thanks for submitting a new request! <br/><br/>\n\
                Your request has been successfully submitted by the Science Gateway.\n\
                Have a look on MyJobs area to get more information about all your submitted jobs.</p>");
            
                $("#dialog-message").dialog({
                modal: true,
                title: "WRF Notification Message",
                height: 200,
                width: 350
                //buttons: { Ok: function() { $( this ).dialog("close"); } }
                });
            
                $("#error_message").css({"color":"red","font-size":"14px", "font-family": "Tahoma,Verdana,sans-serif,Arial"}); 
                $('#error_message').append("Submission in progress...")(30000, function(){ $(this).remove(); });
            } else return false;            
        });
                   
        // Roller
        $('#wrf_footer').rollchildren({
            delay_time         : 3000,
            loop               : true,
            pause_on_mouseover : true,
            roll_up_old_item   : true,
            speed              : 'slow'   
        });
        
        $("#stars-wrapper1").stars({
            cancelShow: false,
            captionEl: $("#stars-cap"),
            callback: function(ui, type, value)
            {
                $.getJSON("ratings.php", {rate: value}, function(json)
                {                                        
                    $("#fake-stars-on").width(Math.round( $("#fake-stars-off").width()/ui.options.items*parseFloat(json.avg) ));
                    $("#fake-stars-cap").text(json.avg + " (" + json.votes + ")");
                });
            }
         });                  
         
    });

    function enable_Demo_WRF(f) 
    {        
        var _Octave="";
        _Octave += "# [ Octave demo ]\n";
        _Octave += "# This is a macro demo that produces\n";
        _Octave += "# a simple bidimentional graph of the\n";
        _Octave += "# function: r=sqrt(x^2+y^2); sin(r)/r\n"; 
        _Octave += "# The output will be stored into an eps\n";
        _Octave += "# image file format.\n";
        _Octave += "#\n";
        _Octave += "# The image file will be available inside\n";
        _Octave += "# the compressedi job ouput file (tar.gz)\n"; 
        _Octave += "#\n"; 
        _Octave += "tx = ty = linspace (-8, 8, 41)';\n"; 
        _Octave += "[xx, yy] = meshgrid (tx, ty);\n"; 
        _Octave += "r = sqrt (xx .^ 2 + yy .^ 2) + eps;\n"; 
        _Octave += "tz = sin (r) ./ r;\n"; 
        _Octave += "mesh (tx, ty, tz);\n"; 
        _Octave += "print -deps demo_output.eps\n";
        
        if ($('input:checked[type=\'radio\'][name=\'wrf_demo_WRF\']',f).val() == "wrf_ASCII_WRF") {
            // Enabling the uploading of the user ASCII file
            $('input[type=\'file\'][name=\'wrf_file_WRF\']').removeAttr('disabled');
            // Disabling the specification of the wrf text via textarea
            $('#wrf_textarea_WRF').html("Add here your macro file or use the default demo");
            $('#wrf_textarea_WRF').attr('disabled','disabled');
        } else {
            // Disabling the uploading of the user file            
            $('input[type=\'file\'][name=\'wrf_file_WRF\']').attr('disabled','disabled');
            // Enabling the specification of the wrf text via textarea            
            $('#wrf_textarea_WRF').html(_Octave);
            $('#wrf_textarea_WRF').removeAttr('disabled');
                        
            $("#wrf_demo_R").removeAttr("checked");
            $('#wrf_textarea_R').html("Add here your macro file or use the default demo");
            $('#wrf_textarea_R').attr('disabled','disabled');
        }                
    }
        
    function DisableElement() {
        if ($('#divToToggle').css('display') == 'block')
            $('#divToToggle').toggle();
    }
    
    function toggleAndChangeText() {        
        
        if ($('#divToToggle').css('display') == 'none') {
            // Collapse the div
            $('#aTag').html('About the demo &#9658');
            if ($("select option:selected").val()=="wrf") {
                console.log("Showning additional demo info");
                $('#generic_WRF_Toggle').show();                
            }            
        } else 
            // Expand the div
            $('#aTag').html('About the demo &#9660');
        
        $('#divToToggle').toggle();
    }  
    
    function enableNotification()
    {
        if ($('#EnableNotification').attr('checked')) 
            $("#drope_mail").show()
        else $("#drope_mail").hide()        
    }
</script>

<br/>
<div id="dialog-message" title="Notification"></div>

<form enctype="multipart/form-data" 
      id="commentForm" 
      action="<portlet:actionURL><portlet:param name="ActionEvent" 
      value="SUBMIT_WRF_PORTLET"/></portlet:actionURL>"      
      method="POST">

<fieldset>
<legend>Input Form</legend>
<div style="margin-left:15px" id="error_message"></div>

<!-- Accordions -->
<div id="accordion" style="width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
<h3><a href="#">
    <img width="32" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/glass_numbers_1.png" />
    <b>Portlet Settings</b>
    <img width="45" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/info_image.png"/>
    </a>
</h3>
<div> <!-- Inizio primo accordion -->
<p>The current portlet has been configured to access </p>
<table id="results" border="0" width="600">
    
<!-- DIT -->
<tr></tr>
<tr>
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
                   name="dit_wrf_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The <a href="http://www.dit.ac.tz/">Dar es Salaam</a> Institute of Technology
            <img width="100" 
                 border="0"
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/DIT_LOGO.png" 
                 title="The Dar es Salaam Institute of Technology"/>    
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GARUDA -->
<tr></tr>
<tr>
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
                   name="garuda_wrf_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The GARUDA Infrastructure
            </c:when>
        </c:choose>       
    </td>
</tr>

<!-- CHAIN-REDS -->
<tr></tr>
<tr>
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
                   name="chain_wrf_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The <a href="http://www.chain-project.eu/">CHAIN-REDS</a> Cloud testbed
            <img width="130" 
                 border="0"
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/chain-reds.png" 
                 title="The CHAIN-REDS project"/>
            </c:when>
        </c:choose>       
    </td>
</tr>

<!-- FEDCLOUD -->
<tr></tr>
<tr>
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
                   name="fedcloud_wrf_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The EGI <a href="https://wiki.egi.eu/wiki/Fedcloud-tf:Testbed">Federated</a> Cloud
            <img width="90" 
                 border="0"
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/EGI_Logo_RGB_315x250px.png" 
                 title="The European Grid Infrastructure (EGI)"/>
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- EUMED -->
<tr></tr>
<tr>
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
                   name="eumed_wrf_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Mediterranean Grid e-Infrastructure
            <img width="110" 
                 border="0"
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/eumedgrid_logo.png" 
                 title="The EUMEDGRID Support"/>
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GISELA -->
<tr></tr>
<tr>
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
                   name="gisela_wrf_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Latin America Grid Infrastructure                    
            </c:when>
        </c:choose>                
    </td>
</tr>

<!-- SAGRID -->
<tr></tr>
<tr>
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
                   name="sagrid_wrf_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The South African Grid Infrastructure
            </c:when>
        </c:choose>                
    </td>
</tr>

</table>
<br/>
<div style="margin-left:15px" 
     id="error_infrastructure" 
     style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; display:none;">    
</div>
<br/>

<p align="center">
<img width="120" src="<%=renderRequest.getContextPath()%>/images/separatore.gif"/>
</p>

<!--p align="justify"-->
<u>Instructions for users:</u><br/>
~ With this service it is possible to execute the WRF application on Virtual Machines (VMs) 
  deployed on Clouds middleware based on rOCCI standard, Grid infrastructures or HPC clusters.<br/><br/>
~ The present service is based on the following standards and software frameworks: <br/><br/>
<p align="center">
<a href="http://grid.in2p3.fr/jsaga/">
<img width="200" src="<%=renderRequest.getContextPath()%>/images/logo-jsaga.png"/></a>

<a href="http://occi-wg.org/">
<img width="200" src="<%=renderRequest.getContextPath()%>/images/OCCI-logo.png"/></a>

<a href="http://www.catania-science-gateways.it">
<img width="100" src="<%=renderRequest.getContextPath()%>/images/CataniaScienceGateways.png"/></a>
</p>

~ The following applications are currently available for testing:
<br/><br/>

<table id="results" border="0">
<tr>
    <td>
    <u>WRF-3.5</u><br/>
    <p align="justify">
        The Weather Research and Forecasting <a href="http://www.wrf-model.org"> (WRF)</a> Model is a next-generation 
        meso-scale numerical weather prediction system designed to serve both atmospheric research and operational 
        forecasting needs. <br/>               
    </p>
    </td>
    
    <td>&nbsp;</td>
    
    <td width="100">
    <a href="http://www.wrf-model.org">
    <img width="110" src="<%=renderRequest.getContextPath()%>/images/wrf-model-logo.png"/>
    </a>
    </td>
</tr>

<tr>    
    <td colspan="3">
        <p align="justify">
        It features two dynamical cores, a data assimilation system, and a software architecture allowing 
        for parallel computation and system extensibility.
        The model serves a wide range of meteorological applications across scales ranging from meters to thousands 
        of kilometers.<br/><br/>
        The effort to develop WRF began in the latter part of the 1990's and was a collaborative partnership 
        principally among the National Center for Atmospheric Research <a href="http://ncar.ucar.edu/">(NCAR)</a>, 
        the National Oceanic and Atmospheric Administration (represented by the National Centers for Environmental 
        Prediction <a href="http://www.ncep.noaa.gov/">(NCEP)</a> and the (then) Forecast Systems Laboratory (FSL)), 
        the Air Force Weather Agency <a href="http://www.afweather.af.mil/">(AFWA)</a>, the 
        <a href="http://www.nrl.navy.mil/">Naval Research Laboratory</a>, the 
        <a href="https://www.ou.edu/">University of Oklahoma</a>, and the Federal Aviation Administration 
        <a href="http://www.faa.gov/">(FAA)</a>. 
        </p>
    </td>
</tr>

<tr>
<td colspan="3">
<img width="580" align="center" 
     src="<%=renderRequest.getContextPath()%>/images/meteo.png"/>
</td>
</tr>
</table>
       
<img width="20" src="<%=renderRequest.getContextPath()%>/images/help.png" title="Read the Help"/>
For further details, please click
<a href="<portlet:renderURL portletMode='HELP'><portlet:param name='action' value='./help.jsp' />
         </portlet:renderURL>" >here</a>
<br/><br/>

<p>If you need to change some preferences, please contact the
<a href="mailto:credentials-admin@ct.infn.it?Subject=Request for Technical Support [<%=gateway%> Science Gateway]&Body=Describe Your Problems&CC=sg-licence@ct.infn.it"> administrator</a>
</p>

<liferay-ui:ratings
    className="<%= it.infn.ct.wrf.Wrf.class.getName()%>"
    classPK="<%= request.getAttribute(WebKeys.RENDER_PORTLET).hashCode()%>" />
    
</div> <!-- Fine Primo Accordion -->

<h3><a href="#">
    <img width="32" align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/glass_numbers_2.png" />
    <b>The Testbed</b>
    <img width="40" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/clouds.png"/>
    </a>
</h3> 
    
<div> 
    <a href="https://developers.google.com/maps/documentation/javascript/tutorial?hl=it/">
    <img width="150" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/developers-logo.svg" 
             border="0" title="Google Maps JavaScript API v3"/>
    </a>
    
    <!-- Inizio Secondo accordion -->                
    <p align="justify">
    See with the Google Map APIs the Cloud/Grid/HPC resource(s) where it is possible to run the WRF application.
    Select the GPS location of the computing resource where you want run your application
    <u>OR, BETTER,</u> let the Science Gateway to choose the best one for you!<br/><br/>
    <img width="20" src="<%=renderRequest.getContextPath()%>/images/help.png"/>
    This option is <u>NOT SUPPORTED</u> if more than one computing infrastructure is enabled!
    </p>
    
    <table border="0">
        <tr>
            <td><legend>Legend</legend></td>
            <td>&nbsp;<img src="<%=renderRequest.getContextPath()%>/images/plus_new.png"/></td>
            <td>&nbsp;Split close sites&nbsp;</td>
        
            <td><img src="<%=renderRequest.getContextPath()%>/images/minus_new.png"/></td>
            <td>&nbsp;Unsplit close sites&nbsp;</td>
                        
            <td><img src="<%=renderRequest.getContextPath()%>/images/ce-run.png"/></td>
            <td>&nbsp;Computing resource&nbsp;</td>
        </tr>    
        <tr><td>&nbsp;</td></tr>
    </table>

    <legend>
        <div id="map_canvas" style="width:570px; height:600px;"></div>
    </legend>

    <input type="hidden" 
           name="wrf_CR" 
           id="wrf_CR"
           size="25px;" 
           class="textfield ui-widget ui-widget-content"
           value=""/>                  
</div> <!-- Fine Secondo Accordion -->        

<h3><a href="#">
    <img width="32" align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/glass_numbers_3.png"/>
    <b>VM Settings</b>
    <img width="40" align="absmiddle" 
         src="<%=renderRequest.getContextPath()%>/images/icon_small_settings.png"/>
    </a>
</h3>
    
<div> <!-- Inizio Terzo accordion -->
<p align="justify">
Please, use the drop-down list to choose the demo you 
want to run on the available infrastructures (Grid, Cloud or HPC clusters).</p>

<table border="0" width="590">
    
    <tr>
        <td width="180">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Please, select the generic VM to be launched"/>
        
        <label for="wrftype">Virtual Server </label><em>*</em>
        </td>
                
        <td width="260">
        <select name="wrftype" 
                style="height:30px; padding-left: 1px; border-style: solid; 
                       border-color: grey; border-width: 1px; padding-left: 1px;
                       width: 200px;"
                onChange="DisableElement();">                                
                            
        <option value="appwrf">WRF-3.5</option>
        </select>
        </td>                
    </tr>
    
    <tr>
        <td width="180">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Please, select the flavor"/>
        
        <label for="wrfvmtemplate">VM Template </label><em>*</em>
        </td>
            
        <td width="260">
        <select name="wrfvmtemplate" 
                style="height:30px; padding-left: 1px; border-style: solid; 
                       border-color: grey; border-width: 1px; padding-left: 1px;
                       width: 200px;">                                
                    
        <option value="small">small</option>
        <option value="medium">medium</option>
        <option value="large">large</option>        
        <option value="extra_large">extra_large</option>               
        </select>
        </td>                
    </tr>
    
    <tr>
        <td>&nbsp;</td>
    </tr>

    <tr>
        <td width="180">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Choose a label for your server "/>
        
        <label for="wrf_vmname">Simulation <em>*</em></label>
        </td>
        
        <td width="260">                      
        <input type="text"                
               id="wrf_vmname"
               name="wrf_vmname"
               style="padding-left: 1px; border-style: solid; 
                      border-color: grey; border-width: 1px; 
                      padding-left: 1px;"
               value="wrf_demo"
               class="required"
               size="40" />
        </td>           
    </tr>    
    
    <tr>
        <td width="180">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Choose a description for your run "/>
        
        <label for="wrf_desc">Description </label>
        </td>
             
        <td width="260">
        <input type="text"                
               id="wrf_desc"
               name="wrf_desc"
               style="padding-left: 1px; border-style: solid; border-color: grey; 
                      border-width: 1px; padding-left: 1px;"
               value="Please, insert here your description"
               size="40" />
        </td>           
    </tr>
    
    <tr><td><br/></td></tr>            
    
    <tr>
        <td width="180">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Enable email notification to the user"/>
        
        <c:set var="enabled_SMTP" value="<%= SMTP_HOST %>" />
        <c:set var="enabled_SENDER" value="<%= SENDER_MAIL %>" />
        <c:choose>
        <c:when test="${empty enabled_SMTP || empty enabled_SENDER}">
        <input type="checkbox" 
               name="EnableNotification"
               id="EnableNotification"
               disabled="disable"
               value="yes" 
               onchange="enableNotification();"/> Notification &nbsp;&nbsp;
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               name="EnableNotification"
               id="EnableNotification"
               value="yes" 
               onchange="enableNotification();"/> Notification &nbsp;&nbsp;&nbsp;
        </c:otherwise>
        </c:choose>
        </td>
        
        <!--td width="260">
        <img width="70"
             id="EnableNotificationid"             
             src="<%= renderRequest.getContextPath()%>/images/mailing2.png" 
             border="0"/>
        </td-->
        
        <td>                
        <div id="drope_mail" 
                 style="padding-left: 1px; border-style: hidden; border-color: grey; 
                        border-width: 1px; padding-left: 1px; display:none;">
        <p>
        <br/><img width="20" src="<%=renderRequest.getContextPath()%>/images/help.png"/>
        SMTP settings:<br/>
        [ <%= SMTP_HOST %>, 25 ]<br/>        
        </p>
        </div>
        </td>        
    </tr>
    
    <tr>
        <td colspan="2" width="150">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Customize some VM settings"/>
        
        <a id="aTag" href="javascript:toggleAndChangeText();">
        About the demo &#9660;
        </a>
        
        <div id="divToToggle" display="none">                            
        <p align="justify">
        ~ The current demonstrative WRF simulation refers to a region in Africa (Saudi Arabia, 
          LAT=31.96, LONG=37.98) in the period that comes from 16th. to 17th. of March 2003.
          <br/><br/>
        ~ The results of this demo will report only the raw data.
        </p>
        </div>
        </td>
    </tr>       

    <tr>                    
        <td align="left">
        <br/>
        <input type="image" 
               src="<%= renderRequest.getContextPath()%>/images/start-icon.png"
               width="60"                   
               name="submit"
               id ="submit" 
               title="Run your Simulation!" />                    
        </td>
     </tr>                                            
</table>    
</div>	<!-- Fine Terzo Accordion -->
</div> <!-- Fine Accordions -->
</fieldset>    
</form>                                                                         

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

