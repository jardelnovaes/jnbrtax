package br.com.jardelnovaes.taxbr.persitence;

import java.io.Serializable;

import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.models.GenericByCompanySimpleEntity;

public abstract class AbstractByCompanyDAO<T extends GenericByCompanySimpleEntity, PK extends Serializable>
		extends AbstractDAO<T, PK> {

	@Override
	public void insert(T entity) throws Exception {
		entity.setCompany(this.getCompany());
		super.insert(entity);
	}

	@Override
	public void update(T entity) throws Exception {
		entity.setCompany(this.getCompany());
		super.update(entity);
	}
	
	@Override
	public void delete(T entity) throws Exception {
		entity.setCompany(this.getCompany());
		super.delete(entity);
	}
	
	@Override
	 //pré configura um grupos de critérios
    public DAOCriteria<T> createEntityCriteria(){    	
		
    	super.daoCriteria = super.createEntityCriteria();    	
    	super.daoCriteria.fixedJoinEqual(super.daoCriteria.join("company"), "id", getCompany().getId());
    	return super.daoCriteria;
    	    	
    }
	
	public Company getCompany(){
		return super.getCurrentAppUser().getCompany();
	}
	
	public void setCompany(Company company){
		super.getCurrentAppUser().setCompany(company);
	}
}