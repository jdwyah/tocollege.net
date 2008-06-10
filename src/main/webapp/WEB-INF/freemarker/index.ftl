<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title><@spring.message "site"/></title>
</head>



<body id="index">					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  
  	
  	<div id="main">
  	  	
	<@common.box "boxStyle", "topSchools", "Top Schools">
				
		<ol>
		<#list frontPage.topSchools as school>
		<li><@common.schoolLink school/></li>
		</#list>
		</ol>		
		
	</@common.box>
		
	<@common.box "boxStyle", "forumBox", "Forums">
				
        <@common.showForumPosts frontPage.postList.posts/>				
		       		
	</@common.box>	
	
	<@common.box "boxStyle", "app", "Calculator">  	 	  
		
		Go see   <a href="<@spring.url "/calculator.html"/>">Calculator</a> 
		
	</@common.box>	 	
	
	</div><!--end browseWidgets-->  

	
  	<div id="sidebar">  					
			
	<#if !user?exists>
	<@common.box "boxStyle", "signup", "Need an account?">
	Signup and <a href="<@spring.url "/signupifpossible.html"/>">Pick a username here!</a>
  	</@common.box>	
  	</#if>  	
  	
	<@common.box "boxStyle", "popularSchools", "Who's Popular">
				
		<ol>		
		<#list frontPage.popularSchools as schoolAndRank>
		    
		    <#if schoolAndRank.rank gt 0>
		    <li><@common.schoolLink schoolAndRank.school/> :  <font color="green">+${(schoolAndRank.rank )?string(00)}</font></li>
		    <#else>
			<li><@common.schoolLink schoolAndRank.school/> :  <font color="red">${(schoolAndRank.rank )?string(00)}</font></li>
			</#if>
		</#list>
		</ol>		
		
	</@common.box>	
  		
	<@common.box "boxStyle", "topUsers", "Top Users">
				
		<ol>
		<#list frontPage.topUsers as user>
		<li><@common.userLink user/></li>
		</#list>
		</ol>		
		
	</@common.box>	
	
	
  		
  	</div>
    <script>
if(typeof(urchinTracker)!='function')document.write('<sc'+'ript src="'+
'http'+(document.location.protocol=='https:'?'s://ssl':'://www')+
'.google-analytics.com/urchin.js'+'"></sc'+'ript>')
</script>
<script>
_uacct = 'UA-1880676-4';
urchinTracker("/1102045388/goal");
</script>

    
</body>
</html>