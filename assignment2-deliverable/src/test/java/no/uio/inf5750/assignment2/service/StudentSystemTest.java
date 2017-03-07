package no.uio.inf5750.assignment2.service;
import no.uio.inf5750.assignment2.model.*;
import static org.junit.Assert.*;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@Transactional
public class StudentSystemTest {
	
	
	@Autowired
	StudentSystem studentSystem;

	Course course;
	Student student;
	
	@Before
	public void init(){
		
	
	}
	
	@Test
	public void testAddCourse(){
		System.out.println("\nTesting addCourse...");
		int id = studentSystem.addCourse("INF2220", "AlgDat");
		course = studentSystem.getCourse(id);
		assertNotNull(course);
		course = studentSystem.getCourse(-1);
		assertNull(course);
		System.out.println("Test performed successfully!");
	}
	
	@Test
	public void testUpdateCourse(){
		System.out.println("\nTesting updateCourse...");
		int courseId = studentSystem.addCourse("INF2220", "AlgDat");
		studentSystem.updateCourse(courseId, "INF2220", "Algoritmer og datastrukturer");
		Course course = studentSystem.getCourse(courseId);
		assertEquals("Algoritmer og datastrukturer",course.getName());
		System.out.println("Test performed successfully!");
	
	}
	
	@Test
	public void testGetCourse(){
		int courseId = studentSystem.addCourse("INF2220", "AlgDat");
		course = studentSystem.getCourse(courseId);
		assertNotNull(course);
		course = studentSystem.getCourse(-1);
		assertNull(course);
		
	}
	
	@Test
	public void testGetCourseByCourseCode(){
		studentSystem.addCourse("INF2220", "AlgDat");
		studentSystem.getAllCourses();
		course = studentSystem.getCourseByCourseCode("INF2220");
		assertNotNull(course);
		course = studentSystem.getCourseByCourseCode("INF2222");
		assertNull(course);
		
	}
	
	@Test
	public void testgetCourseByName(){
		studentSystem.addCourse("INF2220", "AlgDat");
		course = studentSystem.getCourseByName("AlgDat");
		assertNotNull(course);
		course = studentSystem.getCourseByName("Algoritmer og datastrukturer");
		assertNull(course);
	}
	
	@Test
	public void testGetAllCourses(){
	
		studentSystem.addCourse("INF2220", "AlgDat");
		studentSystem.addCourse("INF1010", "Object oriented programming");
		studentSystem.addCourse("INF1000", "Intro programming");
		int length = studentSystem.getAllCourses().size();
		assertEquals(3,length);
		
	}
	@Test
	public void testDelCourse(){
		int courseId = studentSystem.addCourse("INF2220", "AlgDat");
		course = studentSystem.getCourse(courseId);
		studentSystem.delCourse(courseId);
		course = studentSystem.getCourse(courseId);
		assertNull(course);
		
	}
	@Test
	public void testAddAttendantToCourse(){
		int courseId = studentSystem.addCourse("INF2220", "AlgDat");
		course = studentSystem.getCourse(courseId);
		//studentSystem.addStudent("Nicolas Lopez");
		int stud1_id = studentSystem.addStudent("Monica Hjertmoen");
		int stud2_id = studentSystem.addStudent("Nicolas Lopez");
		Student monica = studentSystem.getStudent(stud1_id);
		Student nicolas = studentSystem.getStudent(stud2_id);
		assertNotNull(monica);
		assertNotNull(nicolas);
		studentSystem.addAttendantToCourse(courseId, stud1_id);
		studentSystem.addAttendantToCourse(courseId, stud2_id);
		int length = course.getAttendants().size();
		assertEquals(2,length);
		assertTrue(studentSystem.getCourse(courseId).getAttendants().contains(nicolas));
	}
	@Test
	public void testRemoveAttendantFromCourse(){
		int courseId = studentSystem.addCourse("INF2220", "AlgDat");
		int stud = studentSystem.addStudent("Nicolas Lopez");
		Student nicolas = studentSystem.getStudent(stud);
		studentSystem.removeAttendantFromCourse(courseId, stud);
		assertFalse(studentSystem.getCourse(courseId).getAttendants().contains(nicolas));
	}
	@Test
	public void testAddStudent(){
		int studId = studentSystem.addStudent("Nicolas Lopez");
		Student nicolas = studentSystem.getStudent(studId);
		assertNotNull(nicolas);
	}
	@Test
	public void testUpdateStudent(){
		int studId = studentSystem.addStudent("Nicolas");
		studentSystem.updateStudent(studId, "Nicolas Lopez");
		Student nicolas = studentSystem.getStudent(studId);
		assertEquals("Nicolas Lopez",nicolas.getName());
		
	}
	@Test
	public void testGetStudent(){
		int id = studentSystem.addStudent("Nicolas Lopez");
		student = studentSystem.getStudent(id);
		assertNotNull(student);
		student = studentSystem.getStudent(-1);
		assertNull(student);
	}
	@Test
	public void testGetStudentByName(){
		studentSystem.addStudent("Nicolas Lopez");
		student = studentSystem.getStudentByName("Nicolas Lopez");
		assertNotNull(student);
		student = studentSystem.getStudentByName("Nikolas Lopez");
		assertNull(student);
	}
	@Test
	public void testgetAllStudents(){
		studentSystem.addStudent("Nicolas Lopez");
		studentSystem.addStudent("Monica Hjertmoen");
		studentSystem.addStudent("Harry Potter");
		int size = studentSystem.getAllStudents().size();
		assertEquals(3,size);
	}
	@Test
	public void testDelStudent(){
		int id = studentSystem.addStudent("Nicolas Lopez");
		student = studentSystem.getStudent(id);
		assertNotNull(student);
		studentSystem.delStudent(id);
		student = studentSystem.getStudent(id);
		assertNull(student);
	}
	@Test
	public void TestSetStudentLocation(){
		int id = studentSystem.addStudent("Nicolas Lopez");
		studentSystem.setStudentLocation(id, "59.9138688", "10.752245399999993");
		student = studentSystem.getStudent(id);
		assertEquals("59.9138688",student.getLatitude());
		assertEquals("10.752245399999993",student.getLongitude());
		
	}
	
	
}
