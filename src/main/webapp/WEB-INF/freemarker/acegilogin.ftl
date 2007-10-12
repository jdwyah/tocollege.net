<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<html>
  <head>
    <title>Login</title>
    <meta name="sm.showAccount" content="false">
  </head>

  <body>
  
	 <div class="middle-column-box-white">
        <div class="middle-column-box-title-green">Login</div>


	<#if login_error?has_content>
      <div class="message">      
        Your login attempt was not successful, try again.<BR><BR>
        Reason: ${login_error}<br>
        Note: OpenID users must create a MyHippocampus account before they will be able to login. Use the signup link below.
      </div>
    </#if>
    
     <#if message?exists>
		 <div class="message">${message}</div>
	 </#if>


		<div id="loginBox">
		  <@common.loginForm/>
		</div>		 

	<p>
  		<#if !user?exists>
	      <@common.signupNow/>
	   </#if>
 	    
	</div>

  </body>
</html>
