package br.com.jardelnovaes.taxbr.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.transaction.annotation.Transactional;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.persitence.AppUserDAO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import br.com.jardelnovaes.taxbr.services.AppUserService;

public class AppUserServiceImpl implements AppUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);

	private AppUserDAO dao;
	    
	//@Autowired
	//@Qualifier(value="appUserDAOJPA")
	public void setDao(AppUserDAO dao){
		logger.info("appUserDAO bean was Injected: " + dao.getClass().getName());
	    this.dao = dao;
	}
	
	public List<AppUser> getAll() {
		return dao.getAll();
	}
	
	public AppUser getById(int id) {
		return dao.getById(id);
	}

	//@Transactional	
	public void save(AppUser user) throws Exception {
	
		if(user.getPassword().length() != 32){ //only if doen't have a MD5 content.
			java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
			md5.update(user.getPassword().getBytes(), 0, user.getPassword().length());
			user.setPassword(new java.math.BigInteger(1, md5.digest()).toString(16));
		}
		dao.save(user);
	}
	
	public AppUser getNew() {
		return new AppUser() ;
	}
	
	public void delete(AppUser user) throws Exception {
		dao.delete(user);		
	}
	
	public void setCurrentAppUser(AppUser appUser){
		dao.setCurrentAppUser(appUser);
	}
	
	public AppUser getCurrentAppUser(){
		return dao.getCurrentAppUser();
	}
	
	public AppUser getByName(String name){
		return dao.getByName(name);
	}
	
	public Company getCompanyByName(String name){
		return dao.getCompanyByName(name);
	}
}
