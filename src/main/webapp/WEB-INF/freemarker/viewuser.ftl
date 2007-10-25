<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title>${user.nickname}</title>
</head>



<body>					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>${user.nickname}</h1>
  	
  	<div id="side1">
  	
	<@common.box "boxStyle", "list", "The List">
	<#list user.schoolRankings as school>
		${school.name}<br>
	</#list>
	
	</@common.box>	
	</div>  

	
  	<div id="side2">  		
			
	<@common.box "boxStyle", "userInfo", "About ${user.nickname}">
				
		State: NH
		
	</@common.box>	
  		
  	</div>
  	
  	<div id="bottom">  		
			
		
	<@common.box "boxStyle", "userWall", "My Wall">
		
	No posts on this wall yet.
		
	</@common.box>	
  		
  	</div>
    
</body>
</html>