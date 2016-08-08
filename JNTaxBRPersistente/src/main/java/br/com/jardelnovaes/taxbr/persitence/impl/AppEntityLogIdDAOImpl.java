package br.com.jardelnovaes.taxbr.persitence.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.models.AppLogEntityId;
import br.com.jardelnovaes.taxbr.persitence.AbstractDAO;
import br.com.jardelnovaes.taxbr.persitence.GenericDAO;

/*
 * 
 * @author Jardel Novaes 
*/
public class AppEntityLogIdDAOImpl 
	extends AbstractDAO<AppLogEntityId, Long> 
	implements GenericDAO<AppLogEntityId> 
{ 
	private Logger logger = LoggerFactory.getLogger(AppEntityLogIdDAOImpl.class);
    
	//Implementações da interface GenericDAO<T>
   	public List<AppLogEntityId> getAll() {
   		createEntityCriteria();
       	getDaoCriterea().asc("entityName");
       	
        List<AppLogEntityId> items = getResults();
        logger.debug("getAll() => " + String.valueOf(items.size()));
           
        return items;        
   	}
   	
   	public AppLogEntityId getById(long id) {
   		return getByKey(id);
   	}
   	
   	public void save(AppLogEntityId entity) throws Exception {
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
   	   		
   	   		logger.debug("Saved Id: {} - EntityName: {} ", new Object[] {String.valueOf(entity.getId()), entity.getEntityName()});	
		} catch (Exception e) {
			rollback();
			throw e;
		}
   				
   	}
   	//End::Implementações da interface GenericDAO<T>
}


