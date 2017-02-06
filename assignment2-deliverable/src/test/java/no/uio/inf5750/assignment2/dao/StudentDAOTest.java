package no.uio.inf5750.assignment2.dao;
import no.uio.inf5750.assignment2.model.*;


import static org.junit.Assert.*;

import java.util.Collection;

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
public class StudentDAOTest {
		
	@Autowired
	StudentDAO studentDao;
	
	Student student;

	@Before
	public void init(){
		student = new Student("Nicolas Lopez");
	}
	
	@Test
	public void testSaveStudent(){
		int studId = studentDao.saveStudent(student);
		student = studentDao.getStudent(studId);
		assertEquals(studId,student.getId());
	}
	
	@Test
	public void testGetStudent(){
		int id = studentDao.saveStudent(student);
		student = studentDao.getStudent(id);
		assertNotNull(student);
		student = studentDao.getStudent(-1);
		assertNull(student);
	}
	@Test
	public void testGetStudentByName(){
		studentDao.saveStudent(student);
		String studName = student.getName();
		student = null;
		student = studentDao.getStudentByName(studName);
		assertNotNull(student);
		assertEquals(studName,student.getName());
		student = studentDao.getStudentByName("*#$");
		assertNull(student);
	}
	@Test
	public void testGetAllStudents(){
		studentDao.saveStudent(student);
		Collection<Student>students= studentDao.getAllStudents();
		assertNotNull(students);
		
	}
	@Test
	public void testDelStudent(){
		int id = studentDao.saveStudent(student);
		student = studentDao.getStudent(id);
		assertNotNull(student);
		studentDao.delStudent(student);
		student = studentDao.getStudent(id);
		assertNull(student);
		
	}
		
}
