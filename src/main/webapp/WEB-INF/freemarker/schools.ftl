<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>Schools</title>
</head>



<body id="schools">					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>Schools</h1>
  	
  	<div id="main">
  	
  	<@common.box "boxStyle", "topSchools", "Top Schools">
                
        <ol>
        <#list topSchools as school>
        <li><@common.schoolLink school/></li>
        </#list>
        </ol>       
        
    </@common.box>  
  	
	</div>  

	
  	<div id="sidebar">  		
		
  		
  	</div>
  	    
    
    
</body>
</html>