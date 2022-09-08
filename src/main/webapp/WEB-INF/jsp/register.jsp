<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
	<head>
		<title>Auto-Form-Submission</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/palette-responsive.css">
		<link rel="stylesheet" href="css/migration.css">
	</head>
	<body class="d-flex flex-column" style="font-family: SunLifeSansRegular">
		<div class="container main-content">
			<div class="row">
				<div class="col-12 yellow-stripe"></div>
			</div>
			<div class="row">
				<div class="col-12">
					<div class="img-container">
						<img src="images/sunlife-logo-web.png" />
					</div>
				</div>
				<div class="col-12">
					<ul class="tab-container list-inline">
						<li class="btn btn-default" id="statusTab"><a href="#">Registration Form</a></li>
					</ul>
				</div>
				<div class="col-12">
					<!-- Home Tab -->
				
	
					<!-- Status Tab -->
					<div id="statusTabContent">
						<h4>Following table contains pre-filled data</h4>
						<form action="register" method="post" name="emp">
						<table class="table table-bordered">
							<tbody id="configDataTable">
								<tr><td>First Name</td><td><input type="text" name="firstName" value="Harry"></td></tr>
								<tr><td>Last Name</td><td><input type="text" name="lastName" value="Potter"></td></tr>
								<tr><td>Designation</td><td><input type="text" name="designation" value="Magician"></td></tr>
								<tr><td>Year of Joining</td><td><input type="text" name="yearOfJoining" value="2005"></td></tr>
								<tr><td>Contact</td><td><input type="text" name="contact" value="+123456789"></td></tr>
								<tr><td>Address</td><td><textarea rows="3" cols="24" name="address">12 Picket Post Close in Bracknell, Berkshire</textarea></td></tr>
							</tbody>
						</table>
						<div style="text-align: center;">
							<input type="submit" class="btn btn-default migration" value="Click Me" />
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- Footer -->
		<footer class="footer mt-auto py-3">
			<!-- Light blue section for legal links -->
			<div class="container">
	
				<div class="row global-footer-row3 no-gutter">
					<div class="col-sm-12">
						<div class="row footer-sm content-area">
							<div class="col-sm-5">
								<div class="slf-blue h4" tabindex="0">Life's brighter under
									the sun</div>
							</div>
							<div class="col-sm-7">
								<ul class="list-inline">
									<li><a target="_blank" class="legal-text"
										href="https://dev-www.sunnet.sunlife.com/login.wca/English/legal.html">Legal</a>
									</li>
									<li>|</li>
									<li><a target="_blank" class="legal-text"
										href="https://dev-www.sunnet.sunlife.com/login.wca/English/privacy.html">Privacy</a>
									</li>
									<li>|</li>
									<li><a target="_blank" class="legal-text"
										href="https://dev-www.sunnet.sunlife.com/login.wca/English/secinfo.html">Security</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<!-- copyright section -->
				<div class="row copy-rights content-area">
					<div class="col-xs-12 col-sm-7 rights-reserved">© Sun Life
						Assurance Company of Canada. All rights reserved.</div>
					<div class="col-xs-12 col-sm-5  slf-footer">
						<span class="slf-footer-text">SLF</span>
					</div>
				</div>
			</div>
		</footer>
		<!-- Modal -->
		<div class="modal" tabindex="-1" id="myModal" role="dialog">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Error Message</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <p id="errorMsgId"></p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- Loader -->
		<div class="loader-container">
			Loading...
		</div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
		<script src="js/migration.js"></script>
	</body>
</html>