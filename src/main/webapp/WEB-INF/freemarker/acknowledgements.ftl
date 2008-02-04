<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title>Acknowledgements</title>
</head>

<body>					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  
  	
  	<div id="main">
  	 	
	<@common.box "boxStyle", "acknowledgements", "Acknowledgements">
	<p>ToCollege.net would like to acknowledge the developers of the following components for their contributions in making this site possible. 
	   </p>
	   <ul>	            
        <li><a href="http://code.google.com/webtoolkit/">Google Web Toolkit - GWT</a></li>      
        <li><a href="http://code.google.com/p/gwt-dnd/">GWT-DND Drag-and-Drop</a> from Fred Sauer</li>
        <li><a href="http://code.google.com/p/gwt-log/">GWT-Log</a> from Fred Sauer</li>
        <li><a href="http://code.google.com/p/gwt-google-apis/">GWT Google APIs</a></li>
        <li><a href="http://code.google.com/p/google-web-toolkit-incubator/wiki/StyleInjector">The GWT Incubator Style Injector</a></li>
        <li><a href="http://gears.google.com/">Google Gears</a></li>
        <li><a href="http://raykrueger.blogspot.com/">Spring Security OpenID</a> from Ray Krueger</li>
        <li><a href="http://www.opensymphony.com/compass/">Compass Java Search Engine</a></li>
        <li>Initial website template from <a href="http://www.dotemplate.com/">www.dotemplate.com/</a></li>
        <li><a href="http://www.famfamfam.com/lab/icons/silk/">FamFamFam Silk Icon Set</a></li>
       </ul>
       <pre>
       
       </pre>            			
	</@common.box>	
	
	
	</div>

	
    
</body>
</html>