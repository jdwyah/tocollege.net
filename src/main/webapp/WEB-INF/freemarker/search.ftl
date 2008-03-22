<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
	<head>
	
	<title>Search</title>
			
	</head>

<#macro searchCategory name list type>
 <#if list?size gt 0>
    <div id="${name}"/>
        <h2>${name}</h2>
            <ul>
                <#list list as element>
                    <#if type = "school">
                    <li><@common.schoolLink element /></li>
                    <#elseif type = "forum">
                    <li><@common.forumLink element /></li>
                    <#elseif type = "user">
                    <li><@common.useLink element /></li>
                    </#if>                    
                </#list>             
            </ul>
     </div>
  </#if>
</#macro>	
	
<body id="search">   
	
	<div id="main">
	 <#if message?exists>
		 <div class="message">${message}</div>
	 </#if>

     <form action="<@spring.url "/search.html"/>" method="POST" id="searchForm">
      
     <fieldset id="search">
          <legend>Search ToCollege.net</legend>
          <label for="searchString"><@spring.formInput "command.searchString"/><@common.regError/>Search
          </label>
     <p>
     <input name="submit" type="submit" value="Search">
     </fieldset>
     
     </form>
     
     <p>
     <#if searchResult?exists>
     <div id="searchResults">       
        <@searchCategory "School", searchResult.schools, "school" />
        <@searchCategory "Users", searchResult.users, "user"/>
        <@searchCategory "Forum", searchResult.forumPosts, "forum"/>        
     </div>
     </#if>

	</div>
	
</body>
</html>