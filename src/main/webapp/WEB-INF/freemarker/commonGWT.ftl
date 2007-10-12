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