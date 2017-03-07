<?xml version="1.0" encoding="UTF-8" ?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.png">
<title>Location</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">

<style>
html,body {
        height: 100%;
}

#Map-panel {
	position: relative;
	height: 90%;
	margin-left: 200px;
	margin-right: 200px;
	padding : 5px;
	margin-bottom : 20px;
}
</style>
</head>
<body>

	<!-- Static navbar -->
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/assignment2-gui/">Student system</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="student">Students</a></li>
					<li><a href="course">Courses</a></li>
					<li class="active"><a href="${pageContext.request.contextPath}/">Location</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="init">Init
							database</a></li>
				</ul>

			</div>
			<!--/.nav-collapse -->
		</div>
	</div>


	<div class="container">

		<h1>Students</h1>
		<p class="lead text-danger"></p>

		<table class="table" id="studentTable">
			<tr>
				<th>Student</th>
				<th>Courses</th>
				<th>Location</th>
			</tr>
		</table>

	</div>

	<div class="container">
		
		<form id="studentlocationform" name="studentlocationform">
			<table class="table" id="studentLocationTable">
 
			</table>
			 <td>
			<input id="locationbtn" class="btn btn-primary" type="button" value="Set location">
			</td>
			<br></br>
			<table class="table">

					<td> 
					
					<div id="directionTable">
			
					<td>
					<b>Travel mode : </b>
					<select id="mode">
					<option value="WALKING">WALKING</option>
					<option value="DRIVING">DRIVING</option>
					<option value="TRANSIT">TRANSIT</option>
					<option value="BICYCLING">BICYCLING</option>
					</select>
					</td>
					<td>
					<input id="direction" class="btn btn-primary" type="button" value="Get directions">
					</td>
					</div> 
					</td>
					<!--  
					<td><select id="selectedStud" name="students">></select></td>
					<td><select id="selectedStud" name="students">></select></td>
					-->
				
			</table>
		</form>
			
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"></script>
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/assignment.js"></script>

    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      /* Optional: Makes the sample page fill the window. */
      
      #map-canvas {
      	position: relative;
      	margin-top : 5px;
        height: 100%;
        width: 100%;
      }
      #right-panel {
      	overflow: auto;
      	visibility : hidden;
      	height : 625px;
      	background:WHITE;
      	opacity:0.85;
      	position: absolute;
      	float: right;
        font-family: 'Roboto','sans-serif';
        margin-right:10px;
        font-size: 16px;
        width : 25%;
       	top: 0;
  		right: 0;
		-webkit-transition: width 1s; /* For Safari 3.1 to 6.0 */
    	transition: width 1s; 
      }
      #right-panel:hover {
    	width: 500px;
		}
      #hide {
   		position:absolute;
   		width : 150px;
   		top:630px;
   		right:0px;
   		z-index:10;
   		display:none;
   		margin-right: 10px;
      }
      #show {
   		position:absolute;
   		width : 150px;
   		top:630px;
   		right:0px;
   		z-index:10;
   		display:none;
   		margin-right: 10px;
      }
	body{
	    scroll-behavior: smooth;
	}
    </style>
	
	<script>
    var map;
    var directionsService;
    var directionsDisplay;
    function initMap() {
        directionsService = new google.maps.DirectionsService;
        directionsDisplay = new google.maps.DirectionsRenderer;
      	
        map = new google.maps.Map(document.getElementById('map-canvas'), {
        center: {lat: 59.9138688, lng: 10.752245399999993},
        zoom: 11
      });
        directionsDisplay.setMap(map);
        directionsDisplay.setPanel(document.getElementById('right-panel'));               
    }
    
    function calculateAndDisplayRoute(directionsService, directionsDisplay) {
    	//alert("From : "+orig());
    	
    	directionsService.route({
          origin: String(orig()),
          destination: String(dest()),
          travelMode: String($("#mode").val()),
        }, function(response, status) {
          if (status === 'OK') {
            directionsDisplay.setDirections(response);
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      }
	
	$('#direction').on('click', function(e) {
		e.preventDefault();
		var div = document.getElementById('right-panel');
		div.style.visibility = 'visible';
		{document.getElementById("hide").style.display="block";}
		//document.getElementById("Map-panel").scrollIntoView({block: 'end', behaviour: 'smooth'})
		/*activates smooth scrolling when 'Get direction' button is clicked*/
	    $('html, body').animate({
	        scrollTop: $("#Map-panel").offset().top
	    }, 1000);		
		calculateAndDisplayRoute(directionsService, directionsDisplay)

	});

function hide(){
	//alert("pushed");
	var div = document.getElementById('right-panel');
	div.style.visibility = 'hidden';
	{document.getElementById("hide").style.display="none";}
	{document.getElementById("show").style.display="block";}
}

function show(){
	//alert("pushed");
	var div = document.getElementById('right-panel');
	div.style.visibility = 'visible';
	{document.getElementById("show").style.display="none";}
	{document.getElementById("hide").style.display="block";}
}
	
	$(document).ready(function() {
		getStudentData();
		$.getJSON("http://localhost:8080/api/student.json",function(data){
			var formString = '<b>From     :     </b><select id="selectedStudent1" onchange="orig()" >';
			for (var s = 0; s < data.length; s++) {
				var student = data[s];
				student = explodeJSON(student);
				
				if(student.latitude!="no location"&&student.longitude!="no location"){
				formString += '<option value="'+student.latitude+", "+student.longitude+'">' + student.name
						+ '</option>';
				}
			}
			formString += '</select>';
			var dest = '<b>     To     :     </b><select id="selectedStudent2" onchange="dest()" >';
			for (var s = 0; s < data.length; s++) {
				var student = data[s];
				student = explodeJSON(student);
				if(student.latitude!="no location"&&student.longitude!="no location"){
				dest += '<option value="'+student.latitude+", "+student.longitude+'">' + student.name
						+ '</option>';
				}
			}
			dest += '</select>';
			$('#directionTable').append(formString);
			$('#directionTable').append(dest);
		});
		//initialize_map();
	});
	
	</script>

<div id="Map-panel">
	
	<div id="map-canvas" tabindex='1'> </div>	
	<div id="right-panel"></div>
	<button onclick="hide()" id="hide" class="btn btn-primary">Hide instructions ></button>
	<button onclick="show()" id="show" class="btn btn-primary">Show instructions</button>
	<!--  <input id="hide" class="btn btn-primary" type="button" value="hide directions"> -->
	
</div>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCwjcFTbf_7jqQKwUMFIgNAnlAFSpy5Aio&callback=initMap"></script>
</body>
</html>
