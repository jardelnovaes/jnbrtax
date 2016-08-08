package br.com.jardelnovaes.taxbr.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.transaction.annotation.Transactional;

import br.com.jardelnovaes.taxbr.models.AddressState;
import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.persitence.AddressStateDAO;
import br.com.jardelnovaes.taxbr.persitence.PagedData;
import br.com.jardelnovaes.taxbr.services.AddressStateService;


public class AddressStateServiceImpl implements AddressStateService {
	
	private static final Logger logger = LoggerFactory.getLogger(AddressStateServiceImpl.class);
	
	private AddressStateDAO dao;	
	    
	//@Autowired
	//@Qualifier(value="appUserDAOJPA")
	public void setDao(AddressStateDAO dao){
		
		logger.info("AddressStateDAO bean was Injected: " + dao.getClass().getName());
	    this.dao = dao;
	    this.dao.setUseTransaction(true);
	}
	    
	//@Override
	public List<AddressState> getAll() {
		return dao.getAll();
	}

	//@Override
	public AddressState getById(String id) {
		return dao.getById(id);
	}

	//@Transactional
	//@Override
	public void save(AddressState entity) throws Exception {
		dao.save(entity);
	}

	//@Override
	public AddressState getNew() {
		return new AddressState() ;
	}

	//@Override
	public void delete(AddressState entity) throws Exception {
		dao.delete(entity);		
	}

	public void setPagedData(PagedData pagedData) {
		dao.setPagedData(pagedData);
	}
	
	public PagedData getPagedData(){
		return dao.getPagedData();
	}

	public void setCurrentAppUser(AppUser appUser){
		dao.setCurrentAppUser(appUser);
	}
	
	public AppUser getCurrentAppUser(){
		return dao.getCurrentAppUser();
	}
}
