<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>Users</title>
</head>



<body id="users">					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>Users</h1>
  	
  	<div id="main">
  	
  	<@common.box "boxStyle", "topUsers", "Top Users">
                
        <ol>
        <#list topUsers as user>
        <li><@common.userLink user/></li>
        </#list>
        </ol>       
        
    </@common.box>  
  	
	</div>  

	
  	<div id="sidebar">  		
		
  		
  	</div>
  	    
    
    
</body>
</html>