package br.com.jardelnovaes.taxbr.persitence.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.models.GenericSimpleEntity;
import br.com.jardelnovaes.taxbr.persitence.AbstractDAO;
import br.com.jardelnovaes.taxbr.persitence.GenericDAO;

/*
 * Generic Data Access Object Class for used in simples entities, if necessary you can write another interface more complex.
 * That "simple entity" needs to implement the GenericSimpleEntity interface (minimal id and name fields)
 * @author Jardel Novaes 
*/
public class GenericSimpleDAOJPAImpl<T extends GenericSimpleEntity> 
	extends AbstractDAO<T, Long> 
	implements GenericDAO<T> 
{ 
	
    private Logger logger = LoggerFactory.getLogger(GenericSimpleDAOJPAImpl.class);
    
    //Guarda o  tipo do DAO (Classe DAO filha)
	private final Class<T> persistentClass;
	
    @SuppressWarnings("unchecked")
	public GenericSimpleDAOJPAImpl()
    {
    	this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
    }
    
    @SuppressWarnings("unchecked")
    public GenericSimpleDAOJPAImpl(Logger newLogger)
    {
    	logger = newLogger;
    	this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
		
    }
   	
   
    //Implementa��es da interface GenericDAO<T>
   	public List<T> getAll() {
   		createEntityCriteria();
       	getDaoCriterea().asc("name");
       	
        List<T> items = getResults();
        logger.debug("getAll() => " + String.valueOf(items.size()));
           
        return items;        
   	}
   	
   	public T getById(long id) {
   		return getByKey(id);
   	}
   	
   	public void save(T entity) throws Exception {
   		try {
   			if(isUseTransaction())
   	    		beginTransaction();
   	   		
   	   		if(entity.getId() <= 0 ){ // insert
   	   			insert(entity);
   	   		}
   	   		else{ //update
   	   			update(entity);
   	   		}
   	   		if(isUseTransaction())
   	   			commit();   		
   	   		
   	   		logger.debug("Saved Id: {} - Name: {} ", new Object[] {String.valueOf(entity.getId()), entity.getName()});	
		} catch (Exception e) {
			rollback();
			throw e;
		}
   				
   	}
   	//End::Implementa��es da interface GenericDAO<T>

}


