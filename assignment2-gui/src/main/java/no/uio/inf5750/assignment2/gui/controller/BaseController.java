package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;
import org.apache.log4j.Logger;

import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.service.StudentSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BaseController {

	static Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	private StudentSystem studentSystem;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		model.addAttribute("message", "Leave a message using the form");

		populateModel(model);
		return "index";

	}

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init(ModelMap model) {

		if (studentSystem == null) {
			model.addAttribute("message",
					"Error filling in data. studentSystem == null");
		} else {
			int john = studentSystem.addStudent("John McClane");
			int jane = studentSystem.addStudent("Jane Fonda");
			int inf5750 = studentSystem.addCourse("INF5750",
					"Open Source Development");
			int inf5761 = studentSystem.addCourse("INF5761",
					"Health management information systems");


			studentSystem.addAttendantToCourse(inf5750, john);
			studentSystem.addAttendantToCourse(inf5750, jane);
			studentSystem.addAttendantToCourse(inf5761, john);
			studentSystem.addAttendantToCourse(inf5761, jane);
			
			studentSystem.setStudentLocation(jane, "59.9138688", "10.752245399999993");


			model.addAttribute("message", "Filled in data OK");
		}

		model.addAttribute("message", "Filling in data");

		populateModel(model);
		return "index";

	}

	/*All methods return string "student" which means, return back to page 'http://localhost:8080/student' */
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public String listStudents(ModelMap model) {
		populateModel(model);
		return "student";
	}

	/*http://localhost:8080/student/new?name='test'*/
	@RequestMapping(value = "/student/new", method = RequestMethod.POST)
	public String createStudent(ModelMap model,
			@RequestParam("name") String name) {

		studentSystem.addStudent(name);
		populateModel(model); //oppdaterer modellen
		return "student";
	}

	@RequestMapping(value = "/student/{studentId}/delete", method = RequestMethod.GET)
	public String deleteStudent(ModelMap model,
			@PathVariable("studentId") int studentId) {

		studentSystem.delStudent(studentId);
		populateModel(model);
		return "student";
	}
	
	@RequestMapping(value = "/student/edit", method = RequestMethod.GET)
	public String editStudent(ModelMap model,
			@RequestParam("name") String name, 
			@RequestParam("editedName") String editedName) {
		
		if(studentSystem.getStudentByName(name)!=null){
			int id = studentSystem.getStudentByName(name).getId();
			studentSystem.updateStudent(id, editedName);
		}
		populateModel(model);
		return "student";
	}
	
	@RequestMapping(value = "/student/{studentId}/enrollcourse", method = RequestMethod.POST)
	public String enrollCourse(ModelMap model,
			@PathVariable("studentId") int studentId,
			@RequestParam("courseid") int courseId) {
		
		logger.debug("Enrolling student " + studentId + " in course "+ courseId);
		
		studentSystem.addAttendantToCourse(courseId, studentId);
		populateModel(model);
		return "student";
	}

	@RequestMapping(value = "/student/{studentId}/unenrollcourse/{courseId}", method = RequestMethod.GET)
	public String unenrollCourse(ModelMap model,
			@PathVariable("studentId") int studentId,
			@PathVariable("courseId") int courseId) {

		logger.debug("Un-Enrolling student " + studentId + " in course "
				+ courseId);
		studentSystem.removeAttendantFromCourse(courseId, studentId);
		populateModel(model);
		return "student";
	}

	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public String listCourses(ModelMap model) {

		populateModel(model);
		return "course";
	}

	@RequestMapping(value = "/course/new", method = RequestMethod.POST)
	public String createCourse(ModelMap model,
			@RequestParam("name") String name,
			@RequestParam("coursecode") String courseCode) {

		studentSystem.addCourse(courseCode, name);
		populateModel(model);
		return "course";
	}

	@RequestMapping(value = "/course/{courseId}/delete", method = RequestMethod.GET)
	public String deleteCourse(ModelMap model,
			@PathVariable("courseId") int courseId) {

		studentSystem.delCourse(courseId);
		populateModel(model);
		return "course";
	}
	/*oppdaterer modellen og view*/
	private ModelMap populateModel(ModelMap model) {
		Collection<Student> students = studentSystem.getAllStudents();
		model.addAttribute("students", students);
		Collection<Course> courses = studentSystem.getAllCourses();
		model.addAttribute("courses", courses);
		return model;
	}

}
