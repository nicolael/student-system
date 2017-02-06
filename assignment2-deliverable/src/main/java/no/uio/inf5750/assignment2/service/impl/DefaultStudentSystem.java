package no.uio.inf5750.assignment2.service.impl;
//import java.util.ArrayList;

import java.util.Collection;
import java.util.Set;

//import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.*;

@Component
public class DefaultStudentSystem implements StudentSystem{

	@Autowired
	private StudentDAO studentDao;
	@Autowired
	private CourseDAO courseDao;
	@Autowired
	private SessionFactory sessionFactory;
    
	@Autowired
	public void setSessionFactory( SessionFactory sessionFactory ){
        this.sessionFactory = sessionFactory;
    }
	@Override
	public int addCourse(String courseCode, String name) {
		Course course=new Course(courseCode,name);
		return courseDao.saveCourse(course);
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
	
		Course course = courseDao.getCourse(courseId);
		
		if(course!=null){
			course.setCourseCode(courseCode);
			course.setName(name);
			//System.out.println("\nUpdated to : "+course.getName()+" with courseCode : "+course.getCourseCode()+"\n");
			//courseDao.saveCourse(course); /*This lines updates the object in the current session*/
			return;
		}
		System.out.println("Course not updated!!");
	
	}

	@Override
	public Course getCourse(int courseId) {
		Course course = courseDao.getCourse(courseId);
		if(course!=null){
			return course;
		}
		return null;
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		Course course = courseDao.getCourseByCourseCode(courseCode);
		if(course!=null){
			return course;
		}
		return null;
	}

	@Override
	public Course getCourseByName(String name) {
		Course course = courseDao.getCourseByName(name);
		if(course!=null){
			return course;
		}
		return null;
	}

	@Override
	public Collection<Course> getAllCourses() {
		return courseDao.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		Course course = getCourse(courseId);
		if(course!=null){
			courseDao.delCourse(course);
			return;
		}
		System.out.println("Something went wrong when trying to delete the course");
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		Course course = getCourse(courseId);
		Student stud = getStudent(studentId);
		if(course!=null&&stud!=null){
			course.getAttendants().add(stud);
			//System.out.println("Student "+stud.getName()+" is now enrolled in Course : "+course.getCourseCode());
			return;
		}
		System.out.println("Something went wrong when trying to enroll the student");
	}

	
	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Student stud = getStudent(studentId);
		Course course = getCourse(courseId);
		if(course!=null&&stud!=null){
			course.getAttendants().remove(stud);
			//System.out.println("Student "+stud.getName()+" is now removed from Course : "+course.getCourseCode());
			return;
		}
		System.out.println("Something went wrong when trying to remove student from the course");
		
	}

	@Override
	public int addStudent(String name) {
		Student student = new Student(name);
		return studentDao.saveStudent(student);	
	}

	@Override
	public void updateStudent(int studentId, String name) {
		Student stud = getStudent(studentId);
		if(stud!=null){
			stud.setName(name);
			return;
		}
		
	}

	@Override
	public Student getStudent(int studentId) {
		return studentDao.getStudent(studentId);
	}

	@Override
	public Student getStudentByName(String name) {
		Student stud = studentDao.getStudentByName(name);
		return stud;
	}

	@Override
	public Collection<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {
		Student stud = getStudent(studentId);
		int studId= stud.getId();
		if(!stud.getCourses().isEmpty()){
			Set<Course> courses= stud.getCourses();
			for(Course c : courses){
				int courseCode = c.getId();
				removeAttendantFromCourse(courseCode,studId);
			}
		}
		if(stud!=null){
			studentDao.delStudent(stud);
			return;
			
		}
		System.out.println("Something went wrong when trying to delete a student");
		
	}
	
	@Override
	public void setStudentLocation(int studentId, String latitude, String longitude) {
		Student stud = getStudent(studentId);
		
		if(stud!=null){
			stud.setPos(longitude, latitude);
		}
		
	}
	
}
