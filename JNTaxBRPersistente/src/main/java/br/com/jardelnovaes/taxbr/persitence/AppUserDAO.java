package br.com.jardelnovaes.taxbr.persitence;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;

public interface AppUserDAO {

	List<AppUser> getAll();
	AppUser getById(int id);
	AppUser getByName(String name);
	void save(AppUser user) throws Exception;
	void delete(AppUser user) throws Exception;
	
	Company getCompanyByName(String name);
	//Company getCompany();
	//void setCompany(Company company);
	
	void setCurrentAppUser(AppUser appUser);
	AppUser getCurrentAppUser();
}
