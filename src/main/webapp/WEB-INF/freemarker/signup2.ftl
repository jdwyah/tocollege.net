<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<head>
  <title><@spring.message "signup.title"/></title>
</head>

<body id="signup">
   
      <div id="main">
      
        <h1>Sign-up now!</h1>
        
    <form action="<@spring.url "/createuser2.html"/>" method="POST" id="signupForm">
    
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
			
      	
		<#if hideSecretKey?exists>
			<@spring.formHiddenInput "command.randomkey" /><@common.regError/>			
		<#else>
			Special Key:</td><td><@spring.formInput "command.randomkey"/><@common.regError/>
		</#if>
     <p>
	Email:<@spring.formInput "command.email"/><@common.regError/>

	
	<h4>Terms Of Service</h4>
		
		   <textarea readonly rows="8" cols="30" name="tosbox">
			 <@common.terms/>
		   </textarea>
		 
		  <br>
		     <a href="<@spring.url "/terms.html"/>">Printable Version</a>
		  <p>
		    By clicking on '<@spring.message "signup.createAccount"/>' below you are agreeing to the Terms of Service above.
		
	<p>		
        <input name="submit" type="submit" value="<@spring.message "signup.createAccount"/>">
        </p>
    </form>
    
    <#if !hideSecretKey?exists>
	    <@spring.message "signup.message"/>
	    <p>
	    Add your name to the <a href="<@spring.url "/mailinglist.html"/>"> mailing list.</a>	   
	 </#if>
	 
	 

	</div>
		
<script>
if(typeof(urchinTracker)!='function')document.write('<sc'+'ript src="'+
'http'+(document.location.protocol=='https:'?'s://ssl':'://www')+
'.google-analytics.com/urchin.js'+'"></sc'+'ript>')
</script>
<script>
_uacct = 'UA-1880676-4';
urchinTracker("/1102045388/test");
</script>


</body>
</html>


