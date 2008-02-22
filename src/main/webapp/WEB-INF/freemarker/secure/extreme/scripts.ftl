
<#import "/spring.ftl" as spring/>

<html>
  <head>
    <title>Scripts</title>
  </head>

  <body>
  
  
    
        <#if message?exists>    
            <p class="message">${message}</p>
        </#if>                    
      <div class="main">
        <a href="<@spring.url "/site/secure/extreme/scripts_action_search_index.html"/>"/>Re-Index Search</a>
      </div>

  </body>
</html>
