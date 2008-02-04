<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" xmlns:vml="urn:schemas-microsoft-com:vml">

<#--NOTE: this is the webapp/decorators/spring.ftl-->
<#import "spring.ftl" as spring/>
<#import "../WEB-INF/freemarker/common.ftl" as common/>

<head>
  <!--default.ftl-->
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <meta name="description" content="Applying to college? Track your applications & connect with like minded individuals!" />
  <meta name="keywords" content="College,Application,Social,Web 2.0,GWT" />
  <meta name="author" content="Jeff Dwyer/" />  
  <meta name="verify-v1" content="F6Lj9etrCbKU6HeNZuhWB57qxSeqZOKCPHEMOOUdr6A=" >
  
<link rel="icon" href="http://www.tocollege.net/favicon.ico" type="image/vnd.microsoft.icon" />
<link rel="shortcut icon" href="http://www.tocollege.net/favicon.ico" type="image/vnd.microsoft.icon" />

  <link rel="stylesheet" type="text/css" href="<@spring.url "/css/styles.css"/>"/>

  <title>${title}</title>
  ${head}
</head>

<#--
Freemarker SiteMesh properties extraction from: 
http://jdwyah.blogspot.com/2006/04/freemarker-sitemesh-body-onload.html

See the very slick use of the ID property to highlight the menu tabs with css:
http://www.dehora.net/journal/2007/08/tab_switching_with_sitemesh.html
-->
<body onload="${page.properties["body.onload"]?default("")}"  id="${page.properties["body.id"]?default("")}">

<div id="wrapper">
    	  
	    <div id="header">	       		    
    		<#if "true" == page.properties["meta.sm.showAccount"]?default("true")>
    		<div id="header_account">
    		  <#if user?exists>
    		  	<@common.box "boxStyleSm", "userBox", "Welcome ${user.username}">  	
    		  	<ul>
    		  	  <li><strong>
    		  	  <a href="<@spring.url "/site/secure/myList.html"/>">My List</a></strong>
    		  	  </li>    		  	  
    		  	  |<li>
    		  	  <a href="<@spring.url "/site/secure/userPage.html"/>">Settings</a>
    		  	  </li>    		  	 
    		  	  |<li>
    		  	  <a href="<@spring.url "/site/j_acegi_logout"/>">Logout</a>
    		  	  </li>
    		  	 </@common.box>
    		  <#else>
    		  	 <@common.box "boxStyleSm", "loginBox", "">  	    			     			 
			 		<@common.loginForm/>
		 		 </@common.box>
		 	  </#if>
    		</div>
    		</#if>
    		
            <!--    		
    		<div id="header_logoTitle" >
    			<a href="<@spring.url "/site/index.html"/>"><@common.pngImage src="/img/Logo_388_126.png" width="388" height="126"/></a>
    		</div>-->
    			    		
	    </div>
	    
        
        <div  id="menu">
            <ul>
                <#--Styling Performed in CSS in conjunction with <body id>-->
                <li id="menu-index"><a href="<@spring.url "/site/index.html"/>">Home</a></li>
                <#if user?exists>
                    <li id="menu-mylist"><a href="<@spring.url "/site/secure/myList.html"/>">My List</a></li>
                </#if>
                <li id="menu-schools"><a href="<@spring.url "/site/schools.html"/>">Schools</a></li>
                <li id="menu-users"><a href="<@spring.url "/site/users.html"/>">Users</a></li>
                <li id="menu-forums"><a href="<@spring.url "/site/forums.html"/>">Forums</a></li>                
                <li id="menu-search"><a href="<@spring.url "/site/search.html"/>">Search</a></li>
                <li id="menu-about"><a href="<@spring.url "/site/about.html"/>">About</a></li> 
                <#if !user?exists>                
                    <li id="menu-signup"><a href="<@spring.url "/site/signupifpossible.html"/>">Signup</a></li>
                </#if>           
            </ul>
        </div>      

        <div id="content-wrap">     
             ${body}    	
    	</div><!--content-wrap-->
    	

	

    <div id="footer">
    	©2008 <a href="<@spring.url "/site/index.html"/>">Index</a> 
    	| <a href="<@spring.url "/site/contact.html"/>">Contact Us</a> 
    	| <a href="http://blogger.com/">Blog</a>
    	| <a href="<@spring.url "/site/acknowledgements.html"/>">Acknowledgements</a> 
    	<br>
	</div>
	
	
</div><!--wrapper-->

<#--Google Analytics-->		
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-1880676-2";
urchinTracker();
</script>

</body>
</html>

