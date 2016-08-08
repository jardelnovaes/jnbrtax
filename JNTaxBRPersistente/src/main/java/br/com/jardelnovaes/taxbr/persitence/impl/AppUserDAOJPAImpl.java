package br.com.jardelnovaes.taxbr.persitence.impl;

import java.util.List;
import javax.persistence.TypedQuery;

//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.persitence.AbstractDAO;
import br.com.jardelnovaes.taxbr.persitence.AppUserDAO;
import br.com.jardelnovaes.taxbr.persitence.DAOCriteria;

@Repository
public class AppUserDAOJPAImpl extends AbstractDAO<AppUser, Integer> implements AppUserDAO  {
//extends AbstractByCompanyDAO<AppUser, Integer> implements AppUserDAO {
//org.springframework.data.repository.CrudRepository<T,ID extends Serializable>
	
	private static final Logger logger = LoggerFactory.getLogger(AppUserDAOJPAImpl.class);
	
	public List<AppUser> getAll() {
		super.createEntityCriteria();
    	super.getDaoCriterea().asc("name");
    	
        List<AppUser> users = super.getResults();
        logger.debug(this.getClass().getName() + "getAll() => " + String.valueOf(users.size()));
        
        return users;        
	}
	  
	
	public AppUser getById(int id) {
		return super.getByKey(id);
	}

	
	public void save(AppUser user) {
		try {			
			super.beginTransaction();			
			if(user.getId() <= 0 ){ // insert
				super.insert(user);
			}
			else{ //update
				super.update(user);
			}
			super.commit();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("Saved Id: {} - Name: {} - E-mail: {}", new Object[] {String.valueOf(user.getId()), user.getName(), user.getEmail()});
			
	}
	
	@Override
	public void delete(AppUser user) {
		try {
			super.beginTransaction();
			super.delete(user);
			super.commit();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AppUser getByName(String name) {		
		super.createEntityCriteria();
		getDaoCriterea().equal("email", name);		
		return getFirst();
	}


	public Company getCompanyByName(String name) {
		DAOCriteria<Company> daoCriteria = new DAOCriteria<Company>();    	
    	daoCriteria.setCriteria(getEntityManager().getCriteriaBuilder());
    	
    	daoCriteria.setCriteriaQuery(daoCriteria.getCriteria().createQuery(Company.class));        
    	daoCriteria.setRoot(daoCriteria.getCriteriaQuery().from(Company.class));       
    	daoCriteria.getCriteriaQuery().select(daoCriteria.getRoot()); 
        
    	daoCriteria.equal("name", name);
    	
    	TypedQuery<Company> qry = getEntityManager().createQuery(daoCriteria.getCriteriaQuery());
    	
    	List<Company> result = qry.getResultList();
    	
    	if((result != null) && (result.size() > 0))
    		return result.get(0);
    	else
    		return null;
	}
}
