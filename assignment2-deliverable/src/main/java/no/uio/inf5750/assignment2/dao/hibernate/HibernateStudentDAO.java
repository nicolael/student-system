package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;

import no.uio.inf5750.assignment2.dao.*;
//import no.uio.inf5750.assignment2.model.Course;
//import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import org.apache.log4j.Logger;
//import org.hibernate.Criteria;
//import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateStudentDAO implements StudentDAO {

    static Logger logger = Logger.getLogger(HibernateStudentDAO.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional    
    public void setSessionFactory( SessionFactory sessionFactory )
    {
        this.sessionFactory = sessionFactory;
    }
    @Transactional
	public int saveStudent(Student student) {
		
    	if(getStudentByName(student.getName())!=null) return 0;
		return (Integer) sessionFactory.getCurrentSession().save( student );
		
	}

    @Transactional
	public Student getStudent(int id) {
		return (Student) sessionFactory.getCurrentSession().get( Student.class, id );
	}

	@SuppressWarnings("deprecation")
	@Transactional
	public Student getStudentByName(String name) {
		
		return (Student) sessionFactory.getCurrentSession().createCriteria(Student.class).
				add(Restrictions.eq("name",name)).uniqueResult();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Transactional
	public Collection<Student> getAllStudents() {
		
		ArrayList<Student>students=(ArrayList<Student>)sessionFactory.getCurrentSession().createCriteria(Student.class).list();
		return students;
	}

	@Transactional
	public void delStudent(Student student) {
		Session session = sessionFactory.getCurrentSession();
		if(student!=null){
			session.delete(student);
		}
	}

}
