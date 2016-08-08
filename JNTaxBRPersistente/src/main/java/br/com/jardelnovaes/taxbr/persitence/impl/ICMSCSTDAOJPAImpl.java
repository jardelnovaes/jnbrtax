package br.com.jardelnovaes.taxbr.persitence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.jardelnovaes.taxbr.models.ICMSCST;

public class ICMSCSTDAOJPAImpl extends GenericCSTDAOJPAImpl<ICMSCST>{
		
	private static final Logger logger = LoggerFactory.getLogger(ICMSCSTDAOJPAImpl.class);
    
	public ICMSCSTDAOJPAImpl(){
		super(logger);
	}
}
