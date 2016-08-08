package br.com.jardelnovaes.taxbr.models;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AbstractByCompanyEntity {

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="CompanyId", nullable = false)
	private Company company;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Transient
	protected String getDefaultValueNumericString(String prop, String defaultVal){
		if((prop == null) || (prop.isEmpty()) || (prop.equals("0"))){
			return defaultVal;
		}	
		return prop;
	}

}
