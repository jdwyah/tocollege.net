<html>
<#import "/spring.ftl" as spring/>
<#import "common.ftl" as common/>
<#import "commonGWT.ftl" as gwt/>
<head>
  <title>About ToCollege.net</title>
</head>



<body id="about">					
        
 <#if message?exists>
	<div style="z-index: 99; position: absolute; left: 200px;">
	 <div class="message">${message}</div>
	</div>
 </#if>			  	 	  

  	<div id="main">
  	
    <h1>About ToCollege.net</h1>    
    <p>
    ToCollege.net gives you an easy way to feel like you're on top of the college application process. 
    <p>
    Throughout the application process, from initial research & school discovery, to prioritization & ranking, to creating the applications, to the final decision, ToCollege.net provides a way for you to keep track of where you are in the process and figure out what you need to focus on next. You can look to our school specific forums to see what other people are thinking about, who else is applying to schools and why. You can share your school rankings through Facebook and Google Gadgets to solicit feedback from your peers.

<p>
To contact us, email <a href="mailto:help@tocollege.net">help@tocollege.net</a>

<h1>The Product</h1>

<h3>MyRankings</h3>
<p>
A re-orderable list of your schools.
<p>
Stores your pros/cons and thoughts.
<p>
Stores 1-10 rankings for each school in categories such as english dept, food, social life, location, cost, or other user defined categories eg ('I like the coach')



<h3>MyDecision</h3>
<p>
Helps students decide which schools to apply to and after that helps them choose which school to go to.
<p>
Uses the category rankings from the MyRankings to run a weighted decision matrix to help clarify the applicants thinking. Students can interacticely play with the weightings that they place on the ranking categories and see which schools pop to the top of their list.



<h3>MyApplications</h3>
<p>
Monitors the process of your applications. 
<p>
Which schools still need recommendations sent? 
<p>
Which schools do I want to visit? 
<p>
How many essays are left?
<p>
MyApplications give you a timeline of coming deadlines and shows you what you've accomplished so far.



<h3>School Specific Pages & Forums</h3>
<p>
Forums are available on a school by school basis. The knowledge base contained within the school forums is a wonderful asset of school related information.


    
    
  
    
  	
	</div>  

	
  	<div id="sidebar">  		
  	    <@common.pngImage src="/img/profile.png" width="150" height="164"/>
  	    <br>
  	    Jeff Dwyer
  	      <h1>About The Book</h1>
    <p>
    Besides being a great place to keep track of you college application process, this website is the also the example site for
    the book <a href="http://www.amazon.com/Pro-Web-2-0-Application-Development/dp/1590599853">Pro Web 2.0 Application Development with GWT</a> by Jeff Dwyer.
    <p>
    ToCollege.net was conceived in September 2007 and went live in February 2008. It was created by one developer working about 50% (writing a 350 page book and paying the bills with consulting takes up a considerable amount of time!) so it represents about three months of development work with GWT, Spring & Hibernate.
  	<p>
  	The book was written because I was fed up with books that seemed like annotated version of javadocs. On the other end were the abstract architectural tracts which might have made for good reading, but they certainly didn’t give me a head start on the architecture. I wanted to see how these technologies worked when used for non-trivial applications. I wanted to see ugly problems and their solutions. What I wanted was a look at the source code of a modern web application.
  	<p>
  	So that's what I wrote and that's why the source code of this site is publicly available.
  	</div>
  	    
    
    
</body>
</html>