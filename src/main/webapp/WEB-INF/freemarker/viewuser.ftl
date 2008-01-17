<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>${viewUser.nickname}</title>
</head>



<body>					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>${viewUser.nickname}</h1>
  	
  	<div id="main">
  	
  	<@common.box "boxStyle", "opinions", "School Opinions">
  	 <table>
  	 <tr><th>School</th><th>Pros</th><th>Cons</th><th>Thoughts</th></tr>
  	 
  	 <#list viewUser.schoolRankings as sap>
  	  <tr>
  	     <td>${sap.school.name}</td>
  	     <td class="ProConPanel-Pro">
  	     <#list sap.pros as pro>
  	         ${pro}<p>
  	     </#list>
  	     </td>
  	     <td class="ProConPanel-Con">
  	     <#list sap.cons as con>
             ${con}<p>
         </#list>
  	     </td>
  	      <td>${sap.notes?default("")}</td>
  	    </tr>
  	 </#list>
  	 
  	 </table>
  	</@common.box>   
  	
  	
	<@common.box "boxStyle", "list", "The List">
	
	<table>
	<tr class="headerRow">
	<th>School</th>
	
	<#--Hold all params for our VerticalLabel widget-->
	<#assign allparams = {"total":"${viewUser.processTypes?size}"}/>
	
	<#list viewUser.processTypes as processType>
	   <#assign id="head_${processType_index}"/>
	   <th id="${id}">
	   
	   <#assign params = {"text${processType_index}":"${processType.name}",
                              "id${processType_index}":"${id}"}/>  
       <#--Use concatenation to work around lack of mutable hash-->                                
       <#assign allparams = allparams + params />               					
	</th>
		</#list>
	</tr>
	<@gwt.widget "VerticalLabel", allparams/>


	
	<#list viewUser.schoolRankings as sap>
	<tr>
	<td>
		${sap.school.name}<br>
	</td>
		
		<#list sap.process?keys as processType>
		<td>		 		
		${sap.getTheProcess(processType).pctComplete}
		</td>		
		</#list>
	</tr>
	</#list>
	
	</table>
	
	</@common.box>	
	
	
	<div id="bottom">       
         
    <@common.box "boxStyle", "forums", "My Wall">        
        <#assign params = {"uniqueForumID":"${viewUser.uniqueForumID}"}/>    
        <@gwt.widget "Forum", params/>        
    </@common.box>  
        
        
    </div>
	
	</div>  

	
  	<div id="sidebar">  		
			
	<@common.box "boxStyle", "userInfo", "About ${viewUser.nickname}">
				
		State: NH
		
	</@common.box>	
  		
  	</div>
  	
  
        
    <@gwt.finalize/>
</body>
</html>