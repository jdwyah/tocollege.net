<html>
<#if inviter?exists>
Congratulations, ${inviter.username} has sent you an invitation to ToCollege.net. 
ToCollege.net is a website that let's you keep track of your College application process.
<#else>
Congratulations, your number has been called and you can now sign up to use ToCollege.net. 
ToCollege.net is a website that let's you keep track of your College application process.
</#if>
<p>
Your secret key is ${randomkey}	
<p>
You can use this key to signup for an account. Go to http://www.ToCollege.net/site/signup.html?secretkey=${randomkey} to signup or just
<a href="http://www.ToCollege.net.com/site/signup.html?secretkey=${randomkey}&email=${email?url}">click here.</a>
<p>
If you want to learn more about the site, just go to <a href="http://www.ToCollege.net/">ToCollege.net</a> and take a look at the screencasts.
<p>
Thanks for your time,
<p>
-Jeff Dwyer
Founder, ToCollege.net
http://www.ToCollege.net
</html>