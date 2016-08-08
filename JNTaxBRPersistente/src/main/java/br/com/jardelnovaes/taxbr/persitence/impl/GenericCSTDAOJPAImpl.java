package br.com.jardelnovaes.taxbr.persitence.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.persitence.AbstractDAO;
import br.com.jardelnovaes.taxbr.persitence.GenericCSTDAO;
import br.com.jardelnovaes.taxbr.models.GenericCSTEntity;

public class GenericCSTDAOJPAImpl<T extends GenericCSTEntity> 
		extends AbstractDAO<T, String>
		implements GenericCSTDAO<T> {  
    
	private Logger logger = LoggerFactory.getLogger(GenericCSTDAOJPAImpl.class);
    
    //Guarda o  tipo do DAO (Classe DAO filha)
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public GenericCSTDAOJPAImpl()
    {
    	this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
    }
    
    @SuppressWarnings("unchecked")
    public GenericCSTDAOJPAImpl(Logger newLogger)
    {
    	logger = newLogger;
    	this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
		
    }
    
	//Implementações da interface GenericDAO<T>
   	public List<T> getAll() {
   		createEntityCriteria();
   		if((getPagedData() ==  null) || (getPagedData().getOrderPropertyName().isEmpty())){
	       	getDaoCriterea().orderBy(
	       		getDaoCriterea().getAscOrder("CST")
	       	); 
   		}
        List<T> items = getResults();
        logger.debug("getAll() => " + String.valueOf(items.size()));
           
        return items;        
   	}
   	
   	public T getByCST(String cst) {
   		return getByKey(cst);
   	}
   	
   	public void save(T entity) throws Exception {
   		if(isUseTransaction())
    		beginTransaction();
   		
   		if((entity.getCST() == null) || (entity.getCST().equals(""))){ // insert
   			insert(entity);
   		}
   		else{ //update
   			update(entity);
   		}
   		if(isUseTransaction())
   			commit();   		
   		
   		logger.debug("Saved Id: {} - Name: {} ", new Object[] {String.valueOf(entity.getCST()), entity.getDescription()});		
   	}
}
