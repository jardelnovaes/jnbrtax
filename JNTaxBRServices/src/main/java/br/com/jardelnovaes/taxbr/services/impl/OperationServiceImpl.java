package br.com.jardelnovaes.taxbr.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.models.Operation;
import br.com.jardelnovaes.taxbr.persitence.GenericDAO;
import br.com.jardelnovaes.taxbr.persitence.OperationDAO;
import br.com.jardelnovaes.taxbr.services.OperationService;

public class OperationServiceImpl extends GenericServiceImpl<Operation> implements OperationService{
	private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	private OperationDAO dao;
	
	public void setDao(OperationDAO dao){
		logger.info("DAO Class bean was Injected: " + dao.getClass().getName());
	    this.dao = dao;
	    super.dao = (GenericDAO<Operation>) dao; //transform to GenericDAO to use in AbstractDAO
	    this.dao.setUseTransaction(true);
	}
		
	public List<Operation> getByTransactionTypeId(long transTypeId) {		
		return this.dao.getByTransactionTypeId(transTypeId) ;
	}
}
