package br.com.jardelnovaes.taxbr.persitence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.models.PISCOFINSCST;

public class PISCOFINSCSTDAOJPAImpl extends GenericCSTDAOJPAImpl<PISCOFINSCST>{
	private static final Logger logger = LoggerFactory.getLogger(PISCOFINSCSTDAOJPAImpl.class);
    
	public PISCOFINSCSTDAOJPAImpl(){
		super(logger);
	}
}
