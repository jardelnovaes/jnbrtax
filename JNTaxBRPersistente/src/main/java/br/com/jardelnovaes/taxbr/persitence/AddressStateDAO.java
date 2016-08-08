package br.com.jardelnovaes.taxbr.persitence;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.AddressState;
import br.com.jardelnovaes.taxbr.models.AppUser;

public interface AddressStateDAO {
	List<AddressState> getAll();	
	
	AddressState getById(String id);
	
	void save(AddressState user) throws Exception;
	void delete(AddressState user) throws Exception;
	
	void beginTransaction();
	void commit();
	void setUseTransaction(boolean use);
	boolean isUseTransaction();
	
	PagedData getPagedData();
	void setPagedData(PagedData pagedData) ;
	
	void setCurrentAppUser(AppUser appUser);
	AppUser getCurrentAppUser();
}
