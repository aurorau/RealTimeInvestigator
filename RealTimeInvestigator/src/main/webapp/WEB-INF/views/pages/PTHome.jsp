<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<script src="static/script/jquery.min.js"></script>    
	<!-- JQ-Mobile JS -->
   <!--  <script src="static/script/jquery.mobile-1.4.5.js"></script> -->
	<script src="static/script/rtiNew.js"></script>
<!-- 	 <script src="static/script/jqMobile.js"></script> -->
	<script language="JavaScript" src="http://www.geoplugin.net/javascript.gp" type="text/javascript"></script>

<title>RTI</title>
<%-- <meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/> --%>
<meta name="description" content="website description" />
<meta name="keywords" content="website keywords, website keywords" />
<meta http-equiv="content-type" content="text/html; charset=windows-1252" />
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
</head>

<body id="page-top">
	<div id="main">
		<div id="header">
			<div id="logo">
				<div id="logo_text">
					<!-- class="logo_colour", allows you to change the colour of the text -->
					<h1>RTI</h1>
					<h2>Aurora</h2>
				</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<!-- put class="selected" in the li tag for the selected page - to highlight which page you're on -->
					<li class="selected"><a href="#">Home</a></li>
					<li><a href="gallary" id="gallaryLinkId">Gallery</a></li>
					<!--           <li><a href="page.html">A Page</a></li>
          <li><a href="another_page.html">Another Page</a></li>
          <li><a href="contact.html">Contact Us</a></li> -->
				</ul>
			</div>
		</div>
		<div id="content_header"></div>
		<div id="site_content">
			<div id="banner"></div>
			<div id="sidebar_container">
				<div class="sidebar">
					<div class="sidebar_top"></div>
					<div class="sidebar_item">
						<!-- insert your sidebar items here -->
						<h3>Latest News</h3>
						<h4>New Website Launched</h4>
						<h5>February 1st, 2014</h5>
						<p>
							2014 sees the redesign of our website. Take a look around and let
							us know what you think.<br />
							<a href="#">Read more</a>
						</p>
					</div>
					<div class="sidebar_base"></div>
				</div>
				<div class="sidebar">
					<div class="sidebar_top"></div>
					<div class="sidebar_item">
						<h3>Useful Links</h3>
						<ul>
							<li><a href="#">link 1</a></li>
							<li><a href="#">link 2</a></li>
							<li><a href="#">link 3</a></li>
							<li><a href="#">link 4</a></li>
						</ul>
					</div>
					<div class="sidebar_base"></div>
				</div>
				<div class="sidebar">
					<div class="sidebar_top"></div>
					<div class="sidebar_item">
						<h3>Search</h3>
						<!--             <table border="0">
            	<tr>
            		<td><input class="search" type="text" name="search_field" value="Enter keywords" id="searchKeyId"/></td>
            		<td><input name="search" type="image" src="images/search.png" alt="Search" title="Search" onclick="sendData()" /></td>
            	</tr>
            </table> -->
						<p>
							<input class="search" type="text" name="search_field"
								value="Enter keywords" id="searchKeyId" /> <input name="search"
								type="image" style="border: 0; margin: 0 0 -9px 5px;"
								src="static/images/search.png" alt="Search" title="Search"
								onclick="sendData()" />
						</p>
					</div>
					<div class="sidebar_base"></div>
				</div>
			</div>
			<input type="hidden" id="hiddenIpAddress" />
			<div id="content">
				<!-- insert the page content here -->
				<h1>Welcome to the RTI</h1>
				<input type="hidden" id="hiddenSessionId"/>
				<p class="test">
					This standards compliant, simple, fixed width website template is
					released as an 'open source' design (under a <a href="#">Creative
						Commons Attribution 3.0 Licence</a>), which means that you are free to
					download and use it for anything you want (including modifying and
					amending it). All I ask is that you leave the 'design from
					HTML5webtemplates.co.uk' link in the footer of the template, but
					other than that...
				</p>
				<p>
					This template is written entirely in <strong>HTML5</strong> and <strong>CSS</strong>,
					and can be validated using the links in the footer.
				</p>
				<p>
					You can view more free HTML5 web templates <a href="#">here</a>.
				</p>
				<p>
					This template is a fully functional 5 page website, with an <a
						href="#">examples</a> page that gives examples of all the styles
					available with this design.
				</p>
				<h2>Browser Compatibility</h2>
				<p>This template has been tested in the following browsers:</p>
				<ul>
					<li>Internet Explorer 9</li>
					<li>FireFox 25</li>
					<li>Google Chrome 31</li>
				</ul>
			</div>
		</div>
		<div id="content_footer"></div>
		<%-- <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /> --%>
		<div id="footer">
			<!-- <p><a href="index.html">Home</a> | <a href="examples.html">Examples</a> | <a href="page.html">A Page</a> | <a href="another_page.html">Another Page</a> | <a href="contact.html">Contact Us</a></p> -->
			<p>
				<a href="index.html">Home</a> | <a href="#">Examples</a> | <a
					href="#">A Page</a> | <a href="#">Another Page</a> | <a href="#">Contact
					Us</a>
			</p>
		</div>
	</div>
</body>
</html>
