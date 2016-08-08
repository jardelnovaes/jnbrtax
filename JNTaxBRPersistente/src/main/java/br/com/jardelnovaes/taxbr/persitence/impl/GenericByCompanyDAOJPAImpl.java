package br.com.jardelnovaes.taxbr.persitence.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.jardelnovaes.taxbr.models.GenericByCompanyEntity;
import br.com.jardelnovaes.taxbr.persitence.AbstractByCompanyDAO;
import br.com.jardelnovaes.taxbr.persitence.GenericDAO;

/*
 * Generic Data Access Object Class for used in entities controlled by company, if necessary you can write another interface more complex.
 * That "by Company Entity" needs to implement the AbstractByCompanyEntity interface.
 * @author Jardel Novaes 
*/
public class GenericByCompanyDAOJPAImpl<T extends GenericByCompanyEntity> 
	extends AbstractByCompanyDAO<T, Long> 
	implements GenericDAO<T> 
{ 
	
    private Logger logger = LoggerFactory.getLogger(GenericByCompanyDAOJPAImpl.class);
    
    //Guarda o  tipo do DAO (Classe DAO filha)
	private final Class<T> persistentClass;
	
    @SuppressWarnings("unchecked")
	public GenericByCompanyDAOJPAImpl()
    {
    	this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
    }
    
    @SuppressWarnings("unchecked")
    public GenericByCompanyDAOJPAImpl(Logger newLogger)
    {
    	logger = newLogger;
    	this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("persistentClass => " +this.persistentClass.getName());
		
    }
   	
   
    //Implementações da interface GenericDAO<T>
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
   	//End::Implementações da interface GenericDAO<T>

}


