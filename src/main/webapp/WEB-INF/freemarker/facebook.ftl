<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>Facebook ${viewUser.nickname?html}</title>
</head>



<body>					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>${viewUser.nickname?html}</h1>
  	
  	<div id="main">
  	
  	<@common.box "boxStyle", "opinions", "School Opinions">
  	 <table>
  	 <tr><th>School</th><th>Pros</th><th>Cons</th><th>Thoughts</th></tr>
  	 
  	 <#list viewUser.schoolRankings as sap>
  	  <tr>
  	     <td><@common.schoolLink sap.school/></td>
  	     <td class="ProConPanel-Pro">
  	     <#list sap.pros as pro>
  	         ${pro?html}<p>
  	     </#list>
  	     </td>
  	     <td class="ProConPanel-Con">
  	     <#list sap.cons as con>
             ${con?html}<p>
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
		<@common.schoolLink sap.school/><br>
	</td>
		<#list viewUser.processTypes as processType>		
		<td>		 				
		<#assign processVal = sap.getTheProcess(processType)?default("")/>
		<#if processVal?has_content>
		  <#if processType.percentage>
		  ${processVal.pctComplete}
		  <#else>
		      <#if processVal.pctComplete == 1>		        
		       <@gwt.widget "ImageBundle", {"name":"checked"}/>
		      <#else>
		       <@gwt.widget "ImageBundle", {"name":"unchecked"}/>
		      </#if>		  	
		  </#if>
		</#if>
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
  		    <script type="text/javascript"><!--
google_ad_client = "pub-4227468677841471";
/* 160x600, created 2/20/08 */
google_ad_slot = "3131680677";
google_ad_width = 160;
google_ad_height = 600;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
  	</div>
  	
  
        
    <@gwt.finalize/>
</body>
</html>