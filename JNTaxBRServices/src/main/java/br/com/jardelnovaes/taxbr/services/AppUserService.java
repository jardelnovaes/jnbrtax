package br.com.jardelnovaes.taxbr.services;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;

public interface AppUserService {
	List<AppUser> getAll();
	AppUser getNew();
	AppUser getById(int id);
	AppUser getByName(String name);
	
	void save(AppUser user) throws Exception;
	void delete(AppUser user) throws Exception;
	
	void setCurrentAppUser(AppUser appUser);
	AppUser getCurrentAppUser();
	
	Company getCompanyByName(String name);
}
