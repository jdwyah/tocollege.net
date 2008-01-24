<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>Schools</title>
</head>

<body id="schools">					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

<h1>Schools</h1>
  	
  	<div id="main">
  	
        <#assign letters = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]>        
        <ul class="letterSelector">
         <#list letters as letter>            
                <li <#if letter=startLetter>class="selected"</#if>>
                <a href="<@spring.url "/site/schools.html?startLetter=${letter}"/>">${letter}</a></li>
        </#list>
        </ul>        
        <p>
        <ol start=${start + 1}>
         <#list schools as school>
            <li><@common.schoolLink school/></li>
         </#list>
        </ol>

        <a href="<@spring.url "/site/schools.html?startLetter=${startLetter}&start=0"/>">First</a>
        <#if start gt 20>               
        <a href="<@spring.url "/site/schools.html?startLetter=${startLetter}&start=${start - 20}"/>">Prev</a>
        </#if>
        <a href="<@spring.url "/site/schools.html?startLetter=${startLetter}&start=${start + 20}"/>">Next</a>
    
	</div>  
    
    
    
</body>
</html>