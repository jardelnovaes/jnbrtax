package br.com.jardelnovaes.taxbr.persitence.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.persitence.AppUserDAO;

@Repository
public class AppUserDAOImpl implements AppUserDAO{

	private static final Logger logger = LoggerFactory.getLogger(AppUserDAOImpl.class);
	
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
	
	@SuppressWarnings("unchecked")
	public List<AppUser> getAll() {
		Session session = sessionFactory.openSession();
		List<AppUser> users = (List<AppUser>)session.createCriteria(AppUser.class).setCacheable(true).list();
		//List<AppUser> users = session.createCriteria(AppUser.class).setCacheable(true).setCacheMode(CacheMode.NORMAL).list();
		session.close();
		return users;
	}

	public AppUser getById(int id) {
		Session session = sessionFactory.openSession();
		
		AppUser u = (AppUser) session.get(AppUser.class, id);
		session.close();

		return u;		
	}
	
	

	
	public void save(AppUser user) {
		Session session = sessionFactory.openSession();
		//session.persist(user);
		session.saveOrUpdate(user);
		session.flush();
		logger.debug("Saved Id: {} - Name: {}", String.valueOf(user.getId()), user.getName());
		session.close();
	}

	
	public void delete(AppUser user) {
		Session session = sessionFactory.openSession();
		session.delete(user);
		session.flush();
		logger.debug("Deleted Id: {} - Name: {}", String.valueOf(user.getId()), user.getName());
		session.close();
	}


	public AppUser getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	public Company getCompany() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setCompany(Company company) {
		// TODO Auto-generated method stub
		
	}
	
	public Company getCompanyByName(String name) {
		
		return null;
	}
	
	public void setCurrentAppUser(AppUser appUser){
		
	}
	
	public AppUser getCurrentAppUser(){
		return null;
	}

}
