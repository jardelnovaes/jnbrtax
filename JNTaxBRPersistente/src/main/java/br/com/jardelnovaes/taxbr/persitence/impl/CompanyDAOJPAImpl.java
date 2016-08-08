package br.com.jardelnovaes.taxbr.persitence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.persitence.CompanyDAO;

public class CompanyDAOJPAImpl extends GenericSimpleDAOJPAImpl<Company>
		implements CompanyDAO {
	private static final Logger logger = LoggerFactory
			.getLogger(CompanyDAOJPAImpl.class);

	public CompanyDAOJPAImpl() {
		super(logger);
	}
}
