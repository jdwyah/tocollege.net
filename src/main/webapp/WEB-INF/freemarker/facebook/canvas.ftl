
<#import "/spring.ftl" as spring/>
<#import "../common.ftl" as common/>
<#import "../commonGWT.ftl" as gwt/>

 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<ul>
<li><a href="infinite.html">Infinite</a></li>
<li><a href="updateprofile.html">updateprofile</a></li>
</ul>
<p>

     <#list friends as friend>
      fr: ${friend}
     </#list>

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
  	
  	</div>
  	
  	<div id="sidebar">
  		
  		<h1>Top School</h1>
  		Dartmouth
  		Swarthmore
  		
  		<h1>Top Users</h1>
  		Bob
  		John
  		
  	</div>
  	