<#import "/spring.ftl" as spring/>
<#macro gwtURL str><@spring.url "/com.apress.progwt.Interactive/std/${str}"/></#macro>

<#macro css>
<link href="<@gwtURL "themes/alphacube.css"/>" rel="stylesheet" type="text/css"></link>
  		<link href="<@gwtURL "themes/alphacube-off.css"/>" rel="stylesheet" type="text/css"></link>
  		    	    			
		<#--<link href="<@gwtURL "themes/alphacube-blue.css"/>" rel="stylesheet" type="text/css"></link>
		<link href="<@gwtURL "themes/alphacube-blue-off.css"/>" rel="stylesheet" type="text/css"></link>   	
    	<link href="<@gwtURL "themes/overlay.css"/>" rel="stylesheet" type="text/css"></link>-->
</#macro>
<#macro maps>
<script language='javascript' src="http://maps.google.com/maps?file=api&amp;v=2.x&amp;key=ABQIAAAA6k005Q8HxzHfW1quTBpGbBQiJfR8suas9TieyuC6pP83yB5P8BTmXjViCxeONSb9-B_BVwz4Kfc5SA"></script>
</#macro>
<#macro mapScript>

<style type="text/css">
			v\:* {
			 behavior:url(#default#VML);
			}
		</style>
</#macro>

<#macro image imageName>
<@widget "ImageBundle", {"name":"${imageName}"}/>
</#macro>

<#assign widgetID = 0>
<#macro widget widgetName, extraParams={}, bootstrap="">
    <#assign widgetID = widgetID + 1>    
    <#if widgetID == 1>
        <script language="JavaScript">
            var Vars = {}                          
        </script>
    </#if>
    <script language="JavaScript">
            Vars['widgetCount'] = "${widgetID}"                
            Vars['widget_${widgetID}'] = "${widgetName}"
            <#list extraParams?keys as key> 
                Vars['${key}_${widgetID}'] = "${extraParams[key]}"
            </#list>
            <#if bootstrap?has_content>
                <#--Replace \ with \\ and " with \"-->
                Vars['serialized_${widgetID}'] = "${bootstrap.serialized?default("")?replace("\\","\\\\")?replace("\"","\\\"")}"
            </#if>
    </script>     
    <#if bootstrap?has_content>
        <noscript>
        ${bootstrap.noscript}
        </noscript>
    </#if>           
        <div id="gwt-slot-${widgetID}"></div>
        <div id="gwt-loading-${widgetID}" class="loading"><p>Loading...</p></div>        
        <div id="preload"></div>    
</#macro>
<#macro finalize>
    <div id="gwt-status"></div>
    <script language='javascript' src='<@gwt.gwtURL "com.apress.progwt.Interactive.nocache.js"/>'></script>
    <iframe id='__gwt_historyFrame' style='width:0;height:0;border:0'></iframe>
</#macro>