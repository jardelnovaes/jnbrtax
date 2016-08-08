package br.com.jardelnovaes.taxbr.services;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.persitence.GenericDAO;
import br.com.jardelnovaes.taxbr.persitence.PagedData;


/*
 * Generic class for used in simples services, if necessary you can write another interface more complex
 * @author Jardel Novaes 
*/
public interface GenericService<T> {
	List<T> getAll();
	T getNew();
	T getById(long id);
	
	void save(T entity) throws Exception;	
	void delete(T entity) throws Exception;	
	void setDao(GenericDAO<T> dao);
	
	void setUseTransaction(boolean use) throws Exception;
	boolean isUseTransaction() throws Exception;
		
	void setPagedData(PagedData pagedData);
	PagedData getPagedData();
	
	void setCurrentAppUser(AppUser appUser);
	AppUser getCurrentAppUser();
}
