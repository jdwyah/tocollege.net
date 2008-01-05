<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" xmlns:vml="urn:schemas-microsoft-com:vml">

<!---NOTE: this is the decorator spring.ftl--->
<#import "spring.ftl" as spring/>
<#import "../WEB-INF/freemarker/common.ftl" as common/>

<head>
  <!--default-->
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <meta name="description" content="Applying to college? Track your applications & connect with like minded individuals!" />
  <meta name="keywords" content="College,Application,Social,Web 2.0,GWT" />
  <meta name="author" content="Jeff Dwyer/" />
  
<link rel="icon" href="http://www.tocollege.net/favicon.ico" type="image/vnd.microsoft.icon" />
<link rel="shortcut icon" href="http://www.tocollege.net/favicon.ico" type="image/vnd.microsoft.icon" />

  <link rel="stylesheet" type="text/css" href="<@spring.url "/css/styles.css"/>"/>

  <title>${title}</title>
  ${head}
</head>

<body onload="${page.properties["body.onload"]?default("")}">

<div id="wrap">
    
	
            
	    <a href="<@spring.url "/site/index.html"/>">
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
    		  	 <@common.box "boxStyleSm", "loginBox", "Login">  	    			     			 
			 		<@common.loginForm/>
		 		 </@common.box>
		 	  </#if>
    		</div></a>
    		</#if>
    		
            <!--    		
    		<div id="header_logoTitle" >
    			<a href="<@spring.url "/site/index.html"/>"><@common.pngImage src="/img/Logo_388_126.png" width="388" height="126"/></a>
    		</div>-->
    			
    		
	    </div>
        
        <div  id="menu">
            <ul>
                <li id="current"><a href="<@spring.url "/site/index.html"/>">Home</a></li>
                <li><a href="<@spring.url "/site/schools.html"/>">Schools</a></li>
                <li><a href="<@spring.url "/site/users.html"/>">Users</a></li>
                <li><a href="<@spring.url "/site/forums.html"/>">Forums</a></li>
                <li><a href="<@spring.url "/site/book.html"/>">Book</a></li>
                <li><a href="<@spring.url "/site/about.html"/>">About</a></li>      
            </ul>
        </div>      

        <div id="content-wrap">     
             ${body}    	
    	</div><!--content-wrap-->
    	

	

    <div id="footer">
    	©2007 <a href="<@spring.url "/site/index.html"/>">Index</a> 
    	| <a href="<@spring.url "/site/contact.html"/>">Contact Us</a> 
    	| <a href="http://blogger.com/">Blog</a>
    	| <a href="<@spring.url "/site/acknowledgements.html"/>">Acknowledgements</a> 
    	<br>
	</div>
	
	
</div><!--wrapper-->

		
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-1880676-2";
urchinTracker();
</script>

</body>
</html>

