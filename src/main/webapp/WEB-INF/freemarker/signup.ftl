<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title><@spring.message "signup.title"/></title>
</head>

<body id="signup">
   
      <div id="main">
      
        <h1>Sign-up now!</h1>
        
    <form action="<@spring.url "/site/createuser.html"/>" method="POST" id="signupForm">
      <table>
      
            
      		<tr>
      		<td colspan="2" valign="top">
      			<fieldset id="openIDSignup">
					<legend>Have an OpenID?</legend>
			 		<label for="openIDusername"><@spring.formInput "command.openIDusername", "class=\"openid-identifier\""/><@common.regError/>OpenID
			 		</label>
			 		<label for="openIDnickname"><@spring.formInput "command.openIDnickname"/><@common.regError/>Nickname
                    </label>
		 			<p>
                    If you have an existing OpenID you can use that to access ToCollege.net.
                    <div style="padding-bottom:10px;border-bottom:1px solid #ccc;"><small><a href="https://myvidoop.com/help/what-is-openid" target="_blank">What is OpenID?</a></small></div>
    Don't have OpenID?<br>
    <div style="padding:5px 5px 7px 5px;background-color:#f2f2f2;border-bottom:1px solid #ccc;">
        <div style="font-weight:bold;"><a href="https://myvidoop.com/register/affiliate/27">Sign up with Vidoop!</a></div>
        <div><img src="http://global.vidoop.com/images/myVidoopSiteBadge_82x17.png" width=80 height=15 valign=bottom/>           <small> More secure than passwords.</small>        </div>
    </div>    
    <div style="padding:5px 5px 7px 5px;background-color:#f2f2f2;border-bottom:1px solid #ccc;">
        <div style="font-weight:bold;"><a href="https://www.myopenid.com/affiliate_signup?affiliate_id=1463">Sign up with MyOpenID!</a></div>        
    </div>
				</fieldset>
			</td>
			<td>
				or
			</td>
			<td>
			<fieldset>
					<legend>Create a new username & password</legend>
			 		<label for="username"><@spring.formInput "command.username"/><@common.regError/>User
			 		</label>
		 		<p>
			 		<label for="password"><@spring.formPasswordInput "command.password"/><@common.regError/>Password
			 		</label>
		 		<p>
		 			<label for="password2"><@spring.formPasswordInput "command.password2"/><@common.regError/>Password Again
			 		</label>
		 		<p>		 		
				</fieldset>
				
			</td>
      		</tr>
      	
		<#if hideSecretKey?exists>
			<@spring.formHiddenInput "command.randomkey" /><@common.regError/>			
		<#else>
			<tr><td>Special Key:</td><td><@spring.formInput "command.randomkey"/><@common.regError/></td></tr>
		</#if>

	<tr><td colspan="4" align="left">Email:<@spring.formInput "command.email"/><@common.regError/></td></tr>

		
	<tr><td colspan="4" align="left">
		<table cellpadding="5" align="left" cellspacing="0" border="0">
			<th>Terms Of Service</th>
		 <tr valign="middle"> 
		  <td align="left">
		   <textarea readonly rows="8" cols="30" name="tosbox">
			 <@common.terms/>
		   </textarea>
		 
		  <br>
		     <a href="<@spring.url "/site/terms.html"/>">Printable Version</a>
		  <p>
		    By clicking on '<@spring.message "signup.createAccount"/>' below you are agreeing to the Terms of Service above.
		 </td> 
		</tr>
		</table>
    </td></tr>
		
		
        <tr><td colspan='4' align="left"><input name="submit" type="submit" value="<@spring.message "signup.createAccount"/>"></td></tr>
      </table>

    </form>
    
    <#if !hideSecretKey?exists>
	    <@spring.message "signup.message"/>
	    <p>
	    Add your name to the <a href="<@spring.url "/site/mailinglist.html"/>"> mailing list.</a>	   
	 </#if>
	 
	 

	</div>
		

</body>
</html>


