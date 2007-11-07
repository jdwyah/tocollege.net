<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title>${viewUser.nickname}</title>
</head>



<body>					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>${viewUser.nickname}</h1>
  	
  	<div id="side1">
  	
	<@common.box "boxStyle", "list", "The List">
	
	<table>
	<tr>
	<td>School</td>
	<#list viewUser.processTypes as processType>
	<td>
		${processType.name}				
	</td>
		</#list>
	</tr>
	


	
	<#list viewUser.schoolRankings as sap>
	<tr>
	<td>
		${sap.school.name}<br>
	</td>
		
		<#list sap.process?keys as processType>
		<td>		
		${sap.getTheProcess(processType).pctComplete}
		</td>		
		</#list>
	</tr>
	</#list>
	
	</table>
	
	</@common.box>	
	</div>  

	
  	<div id="side2">  		
			
	<@common.box "boxStyle", "userInfo", "About ${viewUser.nickname}">
				
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