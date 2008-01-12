<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>${school.name}</title>
</head>



<body id="schools">					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>${school.name}</h1>
  	
  	<div id="main">
  	
  	<div>
	<@common.box "boxStyle", "topSchools", "Details">
	${school.address}<br>
	${school.city}, ${school.state} ${school.zip}<br>	
	<a href="http://${school.website}">http://${school.website}</a><br>		
	Undergrads: ${school.undergrads}<br>
	Graduate Students: ${school.students - school.undergrads}<br>
	Athletics: ${school.varsity?default("None")}
	</@common.box>	
	
    <@common.box "boxStyle", "collegeMap", "Map">  
        <#assign params = {"latitude":"${school.latitude}", "longitude":"${school.longitude}"}/>    
        <@gwt.widget "CollegeMap", params/>   
    </@common.box>
    </div>  
	
	     
    <@common.box "boxStyle", "forums", "Forums">        
        <#assign params = {"uniqueForumID":"${school.uniqueForumID}"}/>    
        <@gwt.widget "SchoolForum", params/>        
    </@common.box>  
	
    
   
	</div>  

	
  	<div id="sidebar">  		
				
		<h4>Interested Users</h4>
		<#list interestedIn as user>
			<@common.userLink user/>
		</#list>			
  		
  	</div>
  	
    
    <@gwt.finalize/>
    
</body>
</html>