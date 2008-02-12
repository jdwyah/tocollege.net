<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>Calculator</title>
</head>




<body>					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  


  	
  	<div id="side1">
  	 
				
	
	<@common.box "boxStyle", "calculator", "Calculator">
		
		<script language='javascript' src='<@gwt.gwtURL "com.apress.progwt.Interactive.nocache.js"/>'></script>

		<iframe id='__gwt_historyFrame' style='width:0;height:0;border:0'></iframe>
		<div id="slot1"></div>
		<div id="loading" class="loading"><p>Loading...</p></div>
		<div id="preload"></div>
	
		
	</@common.box>	
	
	
	</div><!--end browseWidgets-->  

	
    
</body>
</html>