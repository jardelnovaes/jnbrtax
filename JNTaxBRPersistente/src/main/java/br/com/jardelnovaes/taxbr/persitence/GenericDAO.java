package br.com.jardelnovaes.taxbr.persitence;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.AppUser;

/*
 * Generic class for used in simples entities, if necessary you can write another interface more complex
 * @author Jardel Novaes 
*/
public interface GenericDAO<T> {
	List<T> getAll();
	T getById(long id);
	void save(T entity) throws Exception;
	void delete(T entity) throws Exception;
	
	void beginTransaction();
	void commit();
	void rollback();
	void setUseTransaction(boolean use);
	boolean isUseTransaction();
	
	PagedData getPagedData();
	void setPagedData(PagedData pagedData);
	
	DAOCriteria<T> getDaoCriterea();
	DAOCriteria<T> createEntityCriteria();
	T getFirst();		
	
	void setCurrentAppUser(AppUser appUser);
	AppUser getCurrentAppUser();
	
}
