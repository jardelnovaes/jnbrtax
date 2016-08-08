package br.com.jardelnovaes.taxbr.persitence.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.models.AppEntityLog;
import br.com.jardelnovaes.taxbr.persitence.AbstractDAO;
import br.com.jardelnovaes.taxbr.persitence.GenericDAO;

/*
 * 
 * @author Jardel Novaes 
*/
public class AppEntityLogDAOImpl 
	extends AbstractDAO<AppEntityLog, Long> 
	implements GenericDAO<AppEntityLog> 
{ 
	private Logger logger = LoggerFactory.getLogger(AppEntityLogDAOImpl.class);
    
	//Implementações da interface GenericDAO<T>
   	public List<AppEntityLog> getAll() {
   		createEntityCriteria();
       	getDaoCriterea().desc("date");
       	
        List<AppEntityLog> items = getResults();
        logger.debug("getAll() => " + String.valueOf(items.size()));
           
        return items;        
   	}
   	
   	public AppEntityLog getById(long id) {
   		return getByKey(id);
   	}
   	
   	public void save(AppEntityLog entity) throws Exception {
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
   	   		
   	   		logger.debug("Saved Id: {} - EntityId: {} ", new Object[] {String.valueOf(entity.getId()), entity.getEntity().getId()});	
		} catch (Exception e) {
			rollback();
			throw e;
		}
   				
   	}
   	//End::Implementações da interface GenericDAO<T>
}


