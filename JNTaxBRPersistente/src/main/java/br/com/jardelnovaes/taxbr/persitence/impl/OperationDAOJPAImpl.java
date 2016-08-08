package br.com.jardelnovaes.taxbr.persitence.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.models.Operation;
import br.com.jardelnovaes.taxbr.persitence.OperationDAO;

public class OperationDAOJPAImpl extends GenericByCompanyDAOJPAImpl<Operation> implements OperationDAO{
    private static final Logger logger = LoggerFactory.getLogger(OperationDAOJPAImpl.class);
    
    public OperationDAOJPAImpl()
    {
    	super(logger);
    }

	public List<Operation> getByTransactionTypeId(long transTypeId) {
		createEntityCriteria();
	    getDaoCriterea().asc("name");
	    
	    getDaoCriterea().joinWithEqual(getDaoCriterea().join("transactionType"), "id", transTypeId);
	    
	    List<Operation> items = getResults();
	    logger.debug("getByTransactionTypeId(transTypeId:= " + String.valueOf(transTypeId) +  ") => " + String.valueOf(items.size()));
	           
	    return items;
	} 
    
    /*
    @Override
    public void delete(Operation entity){
    	//entity.getTransactionType().removeOperation(entity);
    	super.delete(entity);
    }
    */
}
