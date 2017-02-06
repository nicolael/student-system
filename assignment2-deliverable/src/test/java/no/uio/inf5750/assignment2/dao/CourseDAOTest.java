package no.uio.inf5750.assignment2.dao;
 
import no.uio.inf5750.assignment2.model.*;

 
import static org.junit.Assert.*;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@Transactional
public class CourseDAOTest {
	
	@Autowired 
	SessionFactory sessionFactory;
	@Autowired
	CourseDAO courseDao;
	
	Course course_test;
	 
	@Before
	public void init()
	{
		course_test = new Course("inf1300","intro to databases");
	}
	
	
    @Test
	public void testSaveCourse(){
		
    	
		int course_id = courseDao.saveCourse(course_test);
		Course courseTest = courseDao.getCourse(course_id);
		assertEquals(course_id,course_test.getId());
		assertEquals("intro to databases",courseTest.getName());
		assertEquals("inf1300",courseTest.getCourseCode());
		
		
	}
    
    @Test
	public void testGetCourse(){
    	
    	int course_id = courseDao.saveCourse(course_test);
    	course_test = courseDao.getCourse(course_id);
		assertNotNull(course_test);
		course_test = courseDao.getCourse(-1);
		assertNull(course_test);
	}
    @Test
	public void testGetCourseByCourseCode(){
    	
		courseDao.saveCourse(course_test);
		course_test = courseDao.getCourseByCourseCode("inf1300");
		assertNotNull(course_test);
		course_test = courseDao.getCourseByCourseCode("#%&");
		assertNull(course_test);
	}
    @Test
    public void testGetCourseByName(){
    	
    	courseDao.saveCourse(course_test);
    	String courseCode = course_test.getName();
    	course_test = courseDao.getCourseByName(courseCode);
    	assertNotNull(course_test);
    	course_test = courseDao.getCourseByName("#%&");
    	assertNull(course_test);
    	
    }
    @Test
    public void testGetAllCourses(){
    	courseDao.saveCourse(course_test);
    	Collection<Course>courses = courseDao.getAllCourses();
    	assertNotNull(courses);
    }
    @Test
    public void testDelCourse(){
    	int id = courseDao.saveCourse(course_test);
    	course_test = courseDao.getCourse(id);
    	assertNotNull(course_test);
    	courseDao.delCourse(course_test);
    	course_test = courseDao.getCourse(id); //should get null
    	assertNull(course_test);
    }
    
    

}

