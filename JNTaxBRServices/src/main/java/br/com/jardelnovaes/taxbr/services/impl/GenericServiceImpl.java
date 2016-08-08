package br.com.jardelnovaes.taxbr.services.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.transaction.annotation.Transactional;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.persitence.GenericDAO;
import br.com.jardelnovaes.taxbr.persitence.PagedData;
import br.com.jardelnovaes.taxbr.services.GenericService;


public abstract class GenericServiceImpl<T> implements GenericService<T>{
	private static final Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);
	
	protected GenericDAO<T> dao;	
	
	protected final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericServiceImpl(){		
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
		
	}
	
	//@Autowired
	//@Qualifier(value="appUserDAOJPA")
	public void setDao(GenericDAO<T> dao){
		logger.info("DAO Class bean was Injected: " + dao.getClass().getName());
	    this.dao = dao;
	    //deixando o controle de transação para o DAO. (setUseTransaction(true))
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
	public T getById(long id) {
		return dao.getById(id);
	}

	//@Transactional
	//@Override
	public void save(T entity) throws Exception {	
		//Pode controlar transação aqui ou deixar isso para o DAO. (setUseTransaction(true))
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
	
	public static void destroyObjects(Object[] items){
		//TODO Tratar o tipo do objeto fazendo o fechamento de forma melhor (ex: .close())
		for (int i = 0; i < items.length; i++) {
			items[i] = null;
		}
		System.gc();
	}
}
