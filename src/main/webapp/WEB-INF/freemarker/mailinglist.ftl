<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title><@spring.message "mailinglist.title"/></title>
</head>

<body>
	
	
	 <div class="main">
        <h1><@spring.message "mailinglist.1"/></h1>
	
	
	 <#if message?exists>
		 <div class="message">${message}</div>
	 </#if>
	

		<form action="<@spring.url "/mailinglist.html"/>" method="POST">
			<fieldset>
				<legend><@spring.message "login.2.header"/></legend>				
								
					<@spring.message "login.2.addemail"/><@spring.formInput "command.email"/><@common.regError/>					
					<input value="<@spring.message "login.2.addemail"/>" type="submit">
			</fieldset>
		</form>			
	
		<p>
		<@spring.message "mailinglist.expl"/>
		<p>
		Have a secret key already? Then go <a href="<@spring.url "/signup.html"/>">Signup!</a>
	</div>
	
</body>
</html>