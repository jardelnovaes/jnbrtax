package br.com.jardelnovaes.taxbr.services;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.persitence.GenericCSTDAO;
import br.com.jardelnovaes.taxbr.persitence.PagedData;

public interface GenericCSTService<T> {
	List<T> getAll();
	T getNew();
	T getByCST(String id);
	
	void save(T user) throws Exception;
	void delete(T user) throws Exception;
	
	void setDao(GenericCSTDAO<T> dao);
	
	void setUseTransaction(boolean use) throws Exception;
	boolean isUseTransaction() throws Exception;
	
	void setPagedData(PagedData pagedData);
	PagedData getPagedData();
	
	void setCurrentAppUser(AppUser appUser);
	AppUser getCurrentAppUser();	
}
