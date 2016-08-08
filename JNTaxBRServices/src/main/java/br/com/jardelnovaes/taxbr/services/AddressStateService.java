package br.com.jardelnovaes.taxbr.services;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.AddressState;
import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.persitence.PagedData;

public interface AddressStateService {
	List<AddressState> getAll();
	AddressState getNew();
	AddressState getById(String id);
	
	void save(AddressState user) throws Exception;
	void delete(AddressState user) throws Exception;
	
	void setPagedData(PagedData pagedData);
	PagedData getPagedData();
	
	void setCurrentAppUser(AppUser appUser);
	AppUser getCurrentAppUser();
}
