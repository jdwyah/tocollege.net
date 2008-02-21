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