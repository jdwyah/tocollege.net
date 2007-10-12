<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title><@spring.message "site"/></title>
</head>




<body>					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  


  	
  	<div id="side1">
  	 
				
	
	<@common.box "boxStyle", "welcomeSection", "Welcome to the PRO GWT sample App">
		
		Welcome!
		
	</@common.box>	
	
	<@common.box "boxStyle", "app", "Calculator">  	 	  
		
		Go see   <a href="<@spring.url "/site/calculator.html"/>">Calculator</a> 
		
	</@common.box>	 	
	
	</div><!--end browseWidgets-->  

	
  	<div id="side2">  		
		<#assign title = springMacroRequestContext.getMessage("index.whatIs.header")>  	
		<@common.box "boxStyleSm", "whatIs", title>  			
			What is this site?
  		</@common.box>  
  		
  	</div>
    
</body>
</html>