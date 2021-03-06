package br.com.jardelnovaes.taxbr.services.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.transaction.annotation.Transactional;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.GenericCSTEntity;
import br.com.jardelnovaes.taxbr.persitence.GenericCSTDAO;
import br.com.jardelnovaes.taxbr.persitence.PagedData;
import br.com.jardelnovaes.taxbr.services.GenericCSTService;

public class GenericCSTServiceImpl<T extends GenericCSTEntity>	
	implements GenericCSTService<T> { 
	
	private static Logger logger = LoggerFactory.getLogger(GenericCSTServiceImpl.class);
	private final Class<T> persistentClass;
	
	protected GenericCSTDAO<T> dao;	
	
	@SuppressWarnings("unchecked")
	public GenericCSTServiceImpl()
    {
    	this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
    }
    
    @SuppressWarnings("unchecked")
    public GenericCSTServiceImpl(Logger newLogger)
    {
    	logger = newLogger;
    	this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
    }
    
  //@Autowired
  	//@Qualifier(value="appUserDAOJPA")
  	public void setDao(GenericCSTDAO<T> dao){
  		logger.info("DAO Class bean was Injected: " + dao.getClass().getName());
  	    this.dao = dao;
  	    //deixando o controle de transa��o para o DAO. (setUseTransaction(true))
  	    this.dao.setUseTransaction(true);
  	}
  	
  	public void setUseTransaction(boolean use) throws Exception{
  		if(dao == null)
  			throw new Exception("Dao object was not defined. setUseTransaction() failed!");
  		
  		dao.setUseTransaction(use);
  	}
  	
  	public boolean isUseTransaction() throws Exception{
  		if(dao == null)
  			throw new Exception("Dao object was not defined. setUseTransaction() failed!");
  		
  		return dao.isUseTransaction();
  	}
  	
  	//@Override
  	public List<T> getAll() {
  		return dao.getAll();
  	}
  	
  	//@Override
  	public T getNew() {
  		try {
  			//T n = (T)(Class.forName(persistentClass.toString()).newInstance());
  			//T n = (T) persistentClass.newInstance();
  			return (T) persistentClass.newInstance();
  		} catch (Exception e) {
  			logger.warn("getNew() Cannot create instance of " + persistentClass.toString());
  			return null;
  		}
  	}

  	//@Override
  	public T getByCST(String cst) {
  		return dao.getByCST(cst);
  	}

  	//@Transactional
  	//@Override
  	public void save(T entity) throws Exception {	
  		//Pode controlar transa��o aqui ou deixar isso para o DAO. (setUseTransaction(true))
  		try {
  			dao.save(entity);
  		} catch (Exception e) {
  			throw e;
  		}
  		
  	}
  	
  	//@Override
  	public void delete(T entity) throws Exception {			
  		try {
  			dao.delete(entity);
  		} catch (Exception e) {
  			throw e;
  		}
  	}
  	
  	public void setPagedData(PagedData pagedData) {
  		dao.setPagedData(pagedData);
  	}
  	
  	public PagedData getPagedData(){
  		return dao.getPagedData();
  	}

  	public void setCurrentAppUser(AppUser appUser){
  		dao.setCurrentAppUser(appUser);
  	}
  	
  	public AppUser getCurrentAppUser(){
  		return dao.getCurrentAppUser();
  	}

}
