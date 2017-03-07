function getStudentData() {
	// The json variable should be fetched
	// from the server, not initiated with a static value as below. 
	// You must first download the student json data from the server
	// then call populateStudentTable(json);
	// and then populateStudentLocationForm(json);
	var json;
	$.getJSON("http://localhost:8080/api/student.json",function(data){
	populateStudentTable(data);
	populateStudentLocationForm(data,map);
	});
	
}

//Working on method that shows direction between 2 points on the map
function selectPoint(data) {

	//var json;
	//$.getJSON("http://localhost:8080/api/student.json",function(data){
	
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
			
	//});
}

function orig() 
{
	return $("#selectedStudent1").val();

}

function dest() 
{
	return $("#selectedStudent2").val();

}

function populateStudentTable(json) {

	// for each student make a row in the student location table
	// and show the name, all courses and location.
	// if there is no location print "No location" in the <td> instead
	// tip: see populateStudentLocationForm(json) og google how to insert html from js with jquery. 
	// Also search how to make rows and columns in a table with html
	// the table can you see in index.jsp with id="studentTable"
	$.each(json, function (i, item) {
		var tableString = '<tr>';
		tableString +='<td>'+item.name+'</td>';
		var data = item.courses;
		tableString +='<td>';
		for(var i in data){
		     var id = data[i].courseCode;
		     tableString += id+'   ';
		}
		tableString += '</td>';
		tableString += '<td>'+item.longitude+' , '+item.latitude+'</td>';
		tableString +='</tr>';
		$('#studentTable').append(tableString);
		if(item.latitude!="no location"&&item.longitude!="no location"){
			
			var myLatlng = new google.maps.LatLng(item.latitude, item.longitude);                        
			var marker = new google.maps.Marker({
			   position: myLatlng,
			   map: map,
			   title: item.name
			});
			
	        var geocoder = new google.maps.Geocoder;
	        var infowindow = new google.maps.InfoWindow;
	        /*Shows adress inside info-box over a merker*/  
			geocodeLatLng(geocoder, marker.map, infowindow, myLatlng, item.name)

		}
	    });
}

function populateStudentLocationForm(json) {
	var formString = '<td><select id="selectedStudent">';
	for (var s = 0; s < json.length; s++) {
		var student = json[s];
		student = explodeJSON(student);
		formString += '<option value="' + student.id + '">' + student.name
				+ '</option>';
	}
	formString += '</select></td>';
	$('#studentLocationTable').append(formString);
}

$('#locationbtn').on('click', function(e) {
	e.preventDefault();
	$('#directionTable').empty();
	$('#studentTable').empty();
	get_location();
	$('<tr><th>Student</th><th>Courses</th><th>Location</th></tr>').appendTo('#studentTable');
	
});


// This function gets called when you press the Set Location button
function get_location() {
	//if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(geoSuccess, geoError);		
	//}	
}
function geoSuccess(p) {
	  // let's show a map or do something interesting!
	 location_found(p);
	  
}

function geoError() {
	  alert("Could not find you! Try again");
}
/*Show infobox over marker */
function geocodeLatLng(geocoder, map, infowindow, myLatlng, studentName) {

    geocoder.geocode({'location': myLatlng}, function(results, status) {
      if (status === 'OK') {
        if (results[1]) {
          map.setZoom(11);
          var marker = new google.maps.Marker({
            position: myLatlng,
            map: map
          });
          infowindow.setContent(results[1].formatted_address + "<p>Student : <b>"+studentName+"</b></p>");
          infowindow.open(map, marker);
	      marker.addListener('click', function() {
	           infowindow.open(marker.map, marker);
	      });
        } else {
          window.alert('No results found');
        }
      } else {
        window.alert('Geocoder failed due to: ' + status);
      }
    });
     
 }

// Call this function when you've succesfully obtained the location. 
function location_found(p) {
	// Extract latitude and longitude and save on the server using an AJAX call.
	// When you've updated the location, call populateStudentTable(json); again
	// to put the new location next to the student on the page..
	var latitude = p.coords.latitude;
	var longitude = p.coords.longitude;

	alert("Found you at latitude " + latitude + ", longitude " + longitude);
	
	var selector = document.getElementById('selectedStudent');
	var student = selector[selector.selectedIndex].value;

	
	$.ajax({
		type : "GET",
		url : "/api/student/" + student + "/location?latitude=" + latitude + "&longitude=" + longitude,
		async : true,
		cache : false,
		success : function(data) {
			populateStudentTable(data);
			selectPoint(data); /*sends json with student data to our "selectors" */
		}
	});
	

}

var objectStorage = new Object();

function explodeJSON(object) {
	if (object instanceof Object == true) {
		objectStorage[object['@id']] = object;
		console.log('Object is object');
	} else {
		console.log('Object is not object');
		object = objectStorage[object];
		console.log(object);
	}
	return object;
}

var map;
/* This function loads the map way to slow,
 * I moved it to index.jsp and initialized with fixed position {lat: 59.9138688, lng: 10.752245399999993}
 * which makes google maps load faster when homepage(index.jsp) starts for the first time
 */
function initialize_map() {
       var mapOptions = {
               //center : {lat: 59.9138688, lng: 10.752245399999993},
    		   zoom : 10,
               mapTypeId : google.maps.MapTypeId.ROADMAP
             
       };
       map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
       // Try HTML5 geolocation
       if (navigator.geolocation) {
               navigator.geolocation.getCurrentPosition(function(position) {
                       var pos = new google.maps.LatLng(position.coords.latitude,
                                       position.coords.longitude);
                       map.setCenter(pos);
               }, function() {
            	   	   alert("no location");
                       //handleNoGeolocation(true);
               });
       } else {
               // Browser doesn't support Geolocation
               // Should really tell the user…
    	   alert("not supported");
       }
}
//my computer could not find my position
function handleNoGeolocation(errorFlag) {

	  var options = {
	    map: map,
	    position: new google.maps.LatLng(59.9138688, 10.752245399999993),
	    content: content,
	    pixelOffset: new google.maps.Size(0, -30)
	  };

	  var infowindow = new google.maps.InfoWindow(options);
	  map.setCenter(options.position);

	}