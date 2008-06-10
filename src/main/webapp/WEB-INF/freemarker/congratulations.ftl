<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<html>
  <head>
    <title>Congratulations</title>
    <meta name="sm.showAccount" content="false">
  </head>

  <body>
  
	 <div class="middle-column-box-white">
        <div class="middle-column-box-title-green">Congratulations</div>


    
     <#if message?exists>
		 <div class="message">${message}</div>
	 </#if>


		Check out your page:
		<a href="<@spring.url "/secure/myList.html"/>">My List</a></strong>
			 

	<p>
 	    
	</div>

<script>
if(typeof(urchinTracker)!='function')document.write('<sc'+'ript src="'+
'http'+(document.location.protocol=='https:'?'s://ssl':'://www')+
'.google-analytics.com/urchin.js'+'"></sc'+'ript>')
</script>
<script>
_uacct = 'UA-1880676-4';
urchinTracker("/1102045388/goal");
</script>


  </body>
</html>
