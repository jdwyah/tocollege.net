<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>${school.name}</title>
  <@gwt.maps/>
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
        <@gwt.widget widgetName="Forum" bootstrap=forumBootstrap />         
    </@common.box>  
	
    
   
	</div>  

	
  	<div id="sidebar">  		
				
	  <#if interestedIn?size gt 0 >	    
		<h4>Interested Users</h4>	
		<ul>	
		<#list interestedIn as user>
			<li><@common.userLink user/></li>
		</#list>
		</ul>
      </#if>			
		
		<script type="text/javascript"><!--
google_ad_client = "pub-4227468677841471";
/* 160x600, created 2/20/08 */
google_ad_slot = "3131680677";
google_ad_width = 160;
google_ad_height = 600;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
  		
  	</div>
  	
    
    <@gwt.finalize/>
    
</body>
</html>