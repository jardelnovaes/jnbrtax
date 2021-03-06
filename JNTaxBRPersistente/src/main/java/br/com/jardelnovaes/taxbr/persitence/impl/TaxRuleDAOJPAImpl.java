package br.com.jardelnovaes.taxbr.persitence.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.jardelnovaes.taxbr.models.TaxRule;
import br.com.jardelnovaes.taxbr.persitence.AbstractByCompanyDAO;
import br.com.jardelnovaes.taxbr.persitence.GenericDAO;



@Repository
public class TaxRuleDAOJPAImpl extends AbstractByCompanyDAO<TaxRule, Long> implements GenericDAO<TaxRule> {
	
    private static final Logger logger = LoggerFactory.getLogger(TaxRuleDAOJPAImpl.class);
	
    /*
	@Override
	public void delete(TaxRule entity) {
		super.beginTran();
		super.delete(entity);
		super.commit();		
	}
	*/
    
	public List<TaxRule> getAll() {
		super.createEntityCriteria();
    	super.getDaoCriterea().asc("fromState", "toState");
    	
        List<TaxRule> entity = super.getResults();
        logger.debug(this.getClass().getName() + ".getAll() => " + String.valueOf(entity.size()));
        
        return entity;        
	}
	
	public TaxRule getById(long id) {
		return super.getByKey(id);
	}
	
	private void refreshNullableProperties(TaxRule entity){
		//Para for�ar o NULL dessa FK quando n�o for definido valor do Id.
		
		if((entity.getTransactionType() != null) && (entity.getTransactionType().getId() == 0)){
			entity.setTransactionType(null);
		}
		
		if((entity.getOperation() != null) && (entity.getOperation().getId() == 0)){
			entity.setOperation(null);
		}
		
		if((entity.getPersonType() != null) && (entity.getPersonType().getId() == 0)){
			entity.setPersonType(null);
		}
		
		if(entity.getTaxRulePISCOFINS() != null && 
			(entity.getTaxRulePISCOFINS().getPisCST() == null || 
				(entity.getTaxRulePISCOFINS().getPisCST() != null && 
				 entity.getTaxRulePISCOFINS().getPisCST().getCST().isEmpty()) ||
				(entity.getTaxRulePISCOFINS().getCofinsCST() != null && 
				 entity.getTaxRulePISCOFINS().getCofinsCST().getCST().isEmpty())
			)){
			entity.setTaxRulePISCOFINS(null);
		}
		
		if(entity.getTaxRuleICMS() != null && 
				(entity.getTaxRuleICMS().getIcmsCST() == null || 
					(entity.getTaxRuleICMS().getIcmsCST() != null && 
					 entity.getTaxRuleICMS().getIcmsCST().getCST().isEmpty())
				)){
				entity.setTaxRuleICMS(null);
			}
		
	}
	
	public void save(TaxRule entity) throws Exception {
   		try {
   			refreshNullableProperties(entity);
   			
   			if(isUseTransaction())
   	    		beginTransaction();
   	   		
   			if(entity.getId() <= 0 ){ // insert
   	   			insert(entity);
   	   		}
   	   		else{ //update
   	   			update(entity);
   	   		}
   	   		if(isUseTransaction())
   	   			commit();   		
   	   		
   	   		logger.debug("Saved Id: {} - From: {} - To: {}", new Object[] {String.valueOf(entity.getId()), entity.getFromState().getId(), entity.getToState().getId() });	
		} catch (Exception e) {
			rollback();
			throw e;
		}
   	}	
	
}


