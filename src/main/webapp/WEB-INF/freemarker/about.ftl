<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>About ToCollege.net</title>
</head>



<body id="about">					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>About</h1>
  	
  	<div id="main">
  	
    About this site
  	
	</div>  

	
  	<div id="sidebar">  		
  	    <@common.pngImage src="/img/profile.png" width="150" height="164"/>
  	    Jeff Dwyer
  	</div>
  	    
    
    
</body>
</html>