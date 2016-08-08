package br.com.jardelnovaes.taxbr.models;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Cacheable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_PISCOFINSCST")
public class PISCOFINSCST implements GenericCSTEntity{
	@Id
	@Column(name="CST", nullable = false, length=2)
	private String CST;
	@Column(name="Description", nullable = false, length=150)
	private String description;
	
	@Column(name="IsTaxed", nullable = false)
	private boolean taxed = true;
	
	
	public String getCST() {
		return CST;
	}
	public void setCST(String cST) {
		CST = cST;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isTaxed() {
		return taxed;
	}
	public void setTaxed(boolean taxed) {
		this.taxed = taxed;
	}
	
}
