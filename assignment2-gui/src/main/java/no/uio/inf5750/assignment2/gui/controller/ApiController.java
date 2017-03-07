package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.log4j.Logger;

import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

@Controller
@RequestMapping("/")
public class ApiController {
	
	static Logger logger = Logger.getLogger(ApiController.class);
 
	@Autowired
	StudentSystem studentSystem;
	
	
	@RequestMapping(value = "/api/student", method = RequestMethod.GET)
	//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
	@ResponseBody
	public Collection getAllStudents() {
	 
		return studentSystem.getAllStudents(); //Return a collection of all students, supporting JSON format
	 
	}
	 
	@RequestMapping(value = "/api/course", method = RequestMethod.GET)
	@ResponseBody
	public Collection getAllCourses() { 
	 
		return studentSystem.getAllCourses(); //return a collection of all courses, supporting JSON format.
	                                          //@JsonIgnore  (declared in Course.java to stop endless JSON-file)
											 // private Set<Student> attendants = new HashSet<Student>();
	}										//  check out http://localhost:8080/api/student.json to verify JSON	
		
	@RequestMapping(value = "/api/student/{student}/location", method = RequestMethod.GET)
	@ResponseBody
	public Collection setLocation(@PathVariable("student") int student,
			@RequestParam(value="longitude") String longitude, @RequestParam(value="latitude") String latitude) {

		studentSystem.setStudentLocation(student, latitude, longitude);
		return studentSystem.getAllStudents();
	}
}
