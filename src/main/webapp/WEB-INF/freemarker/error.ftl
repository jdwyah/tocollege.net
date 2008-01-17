<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
	<head>
	<#--decorator won't be called on some errors-->
  	<link rel="stylesheet" type="text/css" href="/css/style.css"/>
	
	<title>ERROR</title>		
	</head>
	
	
<body>
	
	<div id="header_logo">	    	
	  	<a href="<@spring.url "/site/index.html"/>">Index
	  	</a>
    </div>
    		
	 <div class="middle-column-box-white">
        <div class="middle-column-box-title-green">Error Occurred</div>
	
	
	 <#if message?exists>
		 <div class="message">${message}</div>
	 </#if>

	We're sorry you received an error. Please do us a favor and let us know! Email <a href="mailto:help@myhippocampus.com">help@myhippocampus.com</a> with the error message and a description of what you were doing. Thanks!

	<#if exception?exists>

					${exception}
					<#list exception.stackTrace as st>
						${st}
					</#list>

	<#else>
			<#if javax?exists && javax.servlet?exists && javax.servlet.error?exists && javax.servlet.error.exception?exists>
				Servlet Exception:<p>				
					${javax.servlet.error.exception} <BR>
					${javax.servlet.error.exception.message?default("")} <BR>
					<#list javax.servlet.error.exception.stackTrace as st>
					${st}<BR>
					</#list>	

			<#else>
					No Error Message found !!!
			</#if>
					
	</#if>

	</div>
	
</body>
</html>