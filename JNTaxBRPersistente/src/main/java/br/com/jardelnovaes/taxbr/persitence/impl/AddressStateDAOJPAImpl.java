package br.com.jardelnovaes.taxbr.persitence.impl;

import java.util.List;

//import javax.persistence.Cache;


import javax.persistence.Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.persitence.AbstractDAO;
import br.com.jardelnovaes.taxbr.persitence.AddressStateDAO;
import br.com.jardelnovaes.taxbr.models.AddressState;


public class AddressStateDAOJPAImpl 
		extends AbstractDAO<AddressState, String>
		implements AddressStateDAO {  
    
	private static final Logger logger = LoggerFactory.getLogger(AddressStateDAOJPAImpl.class);
    
	//Implementações da interface GenericDAO<T>
   	public List<AddressState> getAll() {
   		createEntityCriteria();
   		if((getPagedData() ==  null) || (getPagedData().getOrderPropertyName().isEmpty())){
	       	getDaoCriterea().orderBy(
	       		getDaoCriterea().getDescOrder("special"),
	       		getDaoCriterea().getAscOrder("name")
	       	); 
   		}
   		
   		//setUseCache(true);
        List<AddressState> items = getResults();
        logger.debug("getAll() => " + String.valueOf(items.size()));
           
        return items;        
   	}
   	
   	public AddressState getById(String id) {
   		return getByKey(id);
   	}
   	
   	public void save(AddressState entity) throws Exception {
   		if(isUseTransaction())
    		beginTransaction();
   		
   		Cache cache = getEntityManagerFactory().getCache();   		
   		logger.debug("In Cache " + entity.getId() + "? " + cache.contains(AddressState.class, entity.getId()));
   		if((entity.getId() == null) || (entity.getId().equals(""))){ // insert
   			insert(entity);
   		}
   		else{ //update
   			update(entity);
   		}
   		if(isUseTransaction())
   			commit();   		
   		
   		logger.debug("Saved Id: {} - Name: {} ", new Object[] {String.valueOf(entity.getId()), entity.getName()});		
   	}

	
}
