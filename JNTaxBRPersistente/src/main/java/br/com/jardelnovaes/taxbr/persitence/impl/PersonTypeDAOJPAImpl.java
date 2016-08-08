package br.com.jardelnovaes.taxbr.persitence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.models.PersonType;

public class PersonTypeDAOJPAImpl extends GenericByCompanyDAOJPAImpl<PersonType>{
    private static final Logger logger = LoggerFactory.getLogger(PersonTypeDAOJPAImpl.class);
    
    public PersonTypeDAOJPAImpl()
    {
    	super(logger);
    } 
}
