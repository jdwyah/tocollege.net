<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<!---NOTE: this is the decorator spring.ftl--->
<#import "spring.ftl" as spring/>
<#import "../WEB-INF/freemarker/common.ftl" as common/>

<head>
  <!--default-->
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <meta name="description" content="Tame information anxiety. Connect your thoughts, Write your novel, Start your business, My Hippocampus" />
  <meta name="keywords" content="personal,knowledge,management,visualization,collection,GWT,information,anxiety,hippocampus,my" />
  <meta name="author" content="Jeff Dwyer/" />
  
<link rel="icon" href="http://www.myhippocampus.com/favicon.ico" type="image/vnd.microsoft.icon" />
<link rel="shortcut icon" href="http://www.myhippocampus.com/favicon.ico" type="image/vnd.microsoft.icon" />

  <link rel="stylesheet" type="text/css" href="<@spring.url "/css/style.css"/>"/>

  <title>${title}</title>
  ${head}
</head>

<body onload="${page.properties["body.onload"]?default("")}">

<div id="wrapper">

	<div id="main">
       
     
	    <div id="header">
	    
	    	<div id="header_logo">	    	
	    		<a href="<@spring.url "/site/index.html"/>"><@common.pngImage src="/img/sideSeaHorse120px.png" width="67" height="120"/>
	    		</a>
    		</div>
    		    	
    		    	
    		<#if "true" == page.properties["meta.sm.showAccount"]?default("true")>
    		<div id="header_account">
    		  <#if user?exists>
    		  	<@common.box "boxStyleSm", "userBox", "Welcome ${user.username}">  	
    		  	<ul>
    		  	  <li><strong>
    		  	  <a href="<@spring.url "/site/secure/mindscape.html"/>">My Mindscape</a></strong>
    		  	  </li>    		  	  
    		  	  |<li>
    		  	  <a href="<@spring.url "/site/secure/userPage.html"/>">Settings</a>
    		  	  </li>    		  	 
    		  	  |<li>
    		  	  <a href="<@spring.url "/site/j_acegi_logout"/>">Logout</a>
    		  	  </li>
    		  	 </@common.box>
    		  <#else>
    		  	 <@common.box "boxStyleSm", "loginBox", "Login">  	    			     			 
			 		<@common.loginForm/>
		 		 </@common.box>
		 	  </#if>
    		</div>
    		</#if>
    		
    		
    		<div id="header_logoTitle" >
    			<a href="<@spring.url "/site/index.html"/>"><@common.pngImage src="/img/myhippocampusLogo.png" width="552" height="82"/></a>
    			<p>Knowledge Reimagined<p>
    		</div>
    			
    		<div class="clearRdiv"/>
	    </div>


    	<div id="content">
             ${body}
    	</div><!--content-->

	</div><!--main-->

    <div id="footer">
    	©2007 <a href="<@spring.url "/site/index.html"/>">Index</a> 
    	| <a href="<@spring.url "/site/contact.html"/>">Contact Us</a> 
    	| <a href="http://blogger.com/">Blog</a>
    	| <a href="<@spring.url "/site/acknowledgments.html"/>">Acknowledgements</a> 
    	<br>
	</div>
	
	
</div><!--wrapper-->

		
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-1880676-1";
urchinTracker();
</script>


</body>
</html>

