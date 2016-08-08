package br.com.jardelnovaes.taxbr.persitence;

import java.util.List;
import br.com.jardelnovaes.taxbr.models.AppUser;

public interface GenericCSTDAO<T> {
	List<T> getAll();	
	
	T getByCST(String id);
	
	void save(T entity) throws Exception;
	void delete(T entity) throws Exception;
	
	void beginTransaction();
	void commit();
	void setUseTransaction(boolean use);
	boolean isUseTransaction();
	
	PagedData getPagedData();
	void setPagedData(PagedData pagedData) ;
	
	void setCurrentAppUser(AppUser appUser);
	AppUser getCurrentAppUser();
}
