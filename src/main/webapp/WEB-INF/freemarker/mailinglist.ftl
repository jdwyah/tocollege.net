<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title><@spring.message "mailinglist.title"/></title>
</head>

<body>
	
	
	 <div class="middle-column-box-white">
        <div class="middle-column-box-title-green"><@spring.message "mailinglist.1"/></div>
	
	
	 <#if message?exists>
		 <div class="message">${message}</div>
	 </#if>
	

		<form action="<@spring.url "/site/interested.html"/>" method="POST">
			<fieldset>
				<legend><@spring.message "login.2.header"/></legend>				
								
					<@spring.message "login.2.addemail"/><@spring.formInput "command.email"/><@common.regError/>					
					<input value="<@spring.message "login.2.addemail"/>" type="submit">
			</fieldset>
		</form>			
	
		<p>
		<@spring.message "mailinglist.expl"/>
		<p>
		Have a secret key already? Then go <a href="<@spring.url "/site/signup.html"/>">Signup!</a>
	</div>
	
</body>
</html>