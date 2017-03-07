package no.uio.inf5750.assignment2.dao.hibernate;
import java.util.ArrayList;
import java.util.Collection;

import no.uio.inf5750.assignment2.dao.*;
import no.uio.inf5750.assignment2.model.Course;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

//Provides basic operations on the database
@Transactional
public class HibernateCourseDAO implements CourseDAO{

    static Logger logger = Logger.getLogger(HibernateCourseDAO.class);
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional
    public void setSessionFactory( SessionFactory sessionFactory )
    {
        this.sessionFactory = sessionFactory;
    }
    
    @Transactional
	public int saveCourse(Course course) {
		    	
    	if(getCourseByCourseCode(course.getCourseCode() ) == null  ){
    		
    		int value = (Integer)this.sessionFactory.getCurrentSession().save( course );
    		
    		return value;
    	}
    	return 0;
	}
    
    @Transactional
	public Course getCourse(int id) {
		
		return (Course) sessionFactory.getCurrentSession().get( Course.class, id );
		
	}

	@SuppressWarnings("deprecation")
	@Transactional
	public Course getCourseByCourseCode(String courseCode) {
		
		return (Course) sessionFactory.getCurrentSession().createCriteria(Course.class).
				add(Restrictions.eq("courseCode",courseCode)).uniqueResult();
	}

	@SuppressWarnings("deprecation")
	@Transactional
	public Course getCourseByName(String name) {
		
		return (Course) sessionFactory.getCurrentSession().createCriteria(Course.class).
				add(Restrictions.eq("name",name)).uniqueResult();
	
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Transactional
	public Collection<Course> getAllCourses() {
		ArrayList<Course>courses=(ArrayList<Course>)sessionFactory.getCurrentSession().createCriteria(Course.class).list();
		return courses;
	}

	@Transactional
	public void delCourse(Course course) {
		Session session = sessionFactory.getCurrentSession();
		if(course!=null){
			session.delete(course);
		}
		
	}

}