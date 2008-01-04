<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>Calculator Example</title>
</head>




<body>					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  


  	
  	<div id="side1">
  	 
	
	<@common.box "boxStyle", "calculator", "Calculator">
	    <@gwt.widget "Calculator"/>      
        <@gwt.finalize/>    				
	</@common.box>	
	
	
	</div><!--end browseWidgets-->  

	
    
</body>
</html>