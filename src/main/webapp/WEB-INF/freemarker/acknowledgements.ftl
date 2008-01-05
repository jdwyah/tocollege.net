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
	   Website template from <a href="http://www.dotemplate.com/">www.dotemplate.com/</a>    				
	</@common.box>	
	
	
	</div>

	
    
</body>
</html>