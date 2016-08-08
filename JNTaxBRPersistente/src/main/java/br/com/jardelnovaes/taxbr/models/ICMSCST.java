package br.com.jardelnovaes.taxbr.models;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Cacheable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_ICMSCST")
public class ICMSCST implements GenericCSTEntity{
	@Id
	@Column(name="CST", nullable = false, length=2)
	private String CST;
	@Column(name="Description", nullable = false, length=150)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ICMSTypeId", nullable = false)
	private ICMSType icmsType;
	
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
	
	public ICMSType getIcmsType() {
		return icmsType;
	}

	public void setIcmsType(ICMSType icmsType) {
		this.icmsType = icmsType;
	}

}
