<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>Forums</title>
</head>



<body id="forums">					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>Forums</h1>
  	
  	<div id="main">
  	<#if uniqueForumID?exists>
        <#assign params = {"uniqueForumID":"${uniqueForumID}"}/>    
        <@gwt.widget "Forum", params/>           
        <@gwt.finalize/>
    <#else>
        <@gwt.widget widgetName="Forum" bootstrap=bootstrap extraParams={}/>        
        <#--><@common.showForumPosts bootstrap.postsList.posts/>-->
    </#if>
	</div>  

	
  	<div id="sidebar">  		
		
  		
  	</div>
  	    
    
    <@gwt.finalize/>
    
</body>
</html>