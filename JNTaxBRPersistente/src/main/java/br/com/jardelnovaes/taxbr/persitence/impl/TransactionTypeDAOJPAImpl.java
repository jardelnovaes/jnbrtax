package br.com.jardelnovaes.taxbr.persitence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.jardelnovaes.taxbr.models.TransactionType;

/*
  Extende a GenericSimpleDAOJPA que faz todo o trabalho.
  Para ficar com o logger da classe, executa o construtor da classe super passando novo logger.
*/

@Repository
//public class TransactionTypeDAOJPAImpl extends GenericSimpleDAOJPA<TransactionType>{
public class TransactionTypeDAOJPAImpl extends GenericByCompanyDAOJPAImpl<TransactionType>{
    private static final Logger logger = LoggerFactory.getLogger(TransactionTypeDAOJPAImpl.class);
    
    public TransactionTypeDAOJPAImpl()
    {
    	super(logger);
    }
}


