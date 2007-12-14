<#import "/spring.ftl" as spring/>
<#macro gwtURL str><@spring.url "/com.apress.progwt.Interactive/${str}"/></#macro>

<#macro css>
<link href="<@gwtURL "themes/alphacube.css"/>" rel="stylesheet" type="text/css"></link>
  		<link href="<@gwtURL "themes/alphacube-off.css"/>" rel="stylesheet" type="text/css"></link>
  		    	    			
		<#--<link href="<@gwtURL "themes/alphacube-blue.css"/>" rel="stylesheet" type="text/css"></link>
		<link href="<@gwtURL "themes/alphacube-blue-off.css"/>" rel="stylesheet" type="text/css"></link>   	
    	<link href="<@gwtURL "themes/overlay.css"/>" rel="stylesheet" type="text/css"></link>-->
</#macro>
<#macro mapScript>

<style type="text/css">
			v\:* {
			 behavior:url(#default#VML);
			}
		</style>
</#macro>


<#macro widget pageName, pageID=1, extraParams={}>
<script language="JavaScript">
            var Vars = {
                page: "${pageName}",
                pageIDNum: "1"<#list extraParams?keys as key>, 
                ${key}: "${extraParams[key]}" </#list>     
            };
        </script>
        <script language='javascript' src='<@gwt.gwtURL "com.apress.progwt.Interactive.nocache.js"/>'></script>

        <iframe id='__gwt_historyFrame' style='width:0;height:0;border:0'></iframe>
        <div id="gwt-slot-${pageID}"></div>
        <div id="gwt-loading-${pageID}" class="loading"><p>Loading...</p></div>
        <div id="preload"></div>
</#macro>