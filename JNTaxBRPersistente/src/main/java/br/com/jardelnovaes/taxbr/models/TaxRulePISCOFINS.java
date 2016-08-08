package br.com.jardelnovaes.taxbr.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
//import javax.persistence.PrimaryKeyJoinColumn;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_TaxRulePISCOFINS")
public class TaxRulePISCOFINS {
	@Id
	@JsonIgnore
    //@Column(name="TaxRuleId")
    private long taxRuleId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PISCST", nullable = false) 	
	private PISCOFINSCST pisCST;
	
	@Column(name="PISRate", nullable = false, precision=5, scale=2)
	private BigDecimal pisRate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COFINSCST", nullable = false) 
	private PISCOFINSCST cofinsCST;
	
	@Column(name="COFINSRate", nullable = false, precision=5, scale=2)
	private BigDecimal cofinsRate;
	
	@Column(name="LegalNotes", nullable = true, length=200)
	private String legalNotes;
	
	@Column(name="IsActive", nullable = false)
	private boolean active = true;
	
    @OneToOne
    @MapsId
    @JoinColumn(name = "TaxRuleId")
    @JsonIgnore
    //@PrimaryKeyJoinColumn(name="TaxRuleId", referencedColumnName="Id") //It doesn't have a PK 'cause is part of the TaxRule Entity (an optional part)
	private TaxRule taxRule;
	
	public PISCOFINSCST getPisCST() {
		return pisCST;
	}

	public void setPisCST(PISCOFINSCST pisCST) {
		this.pisCST = pisCST;
	}

	public BigDecimal getPisRate() {
		return pisRate;
	}

	public void setPisRate(BigDecimal pisRate) {
		this.pisRate = pisRate;
	}

	public PISCOFINSCST getCofinsCST() {
		return cofinsCST;
	}

	public void setCofinsCST(PISCOFINSCST cofinsCST) {
		this.cofinsCST = cofinsCST;
	}

	public BigDecimal getCofinsRate() {
		return cofinsRate;
	}

	public void setCofinsRate(BigDecimal cofinsRate) {
		this.cofinsRate = cofinsRate;
	}

	public String getLegalNotes() {
		return legalNotes;
	}

	public void setLegalNotes(String legalNotes) {
		this.legalNotes = legalNotes;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public TaxRule getTaxRule() {
		return taxRule;
	}

	public void setTaxRule(TaxRule taxRule) {
		this.taxRule = taxRule;
		this.taxRuleId = taxRule.getId();
	}

	public long getTaxRuleId(){
		if((this.taxRule != null))
			this.taxRuleId = this.taxRule.getId();
		
		return this.taxRuleId;		
	}
	
	@Override
	public boolean equals(Object obj) {
		//Forçando o equals/hasCode por causa do uso do MapsId que está lançando um EntityExistsException 
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TaxRulePISCOFINS)) {
            return false;
        }
        
        TaxRulePISCOFINS other = (TaxRulePISCOFINS) obj;
        
        if((this.taxRule != null) && (other.taxRule != null)){
        	return  this.taxRule.getId() == other.taxRule.getId();	        	
        }
        return false;
    }
	
/*
	public long setTaxRuleId(long id){
		return this.taxRuleId = id;
	}
*/	
	/*
	 TaxRulePISCOFINS									
PISCST	PISRate	COFINSCST	COFINSRate	LegalNotes 
	 */
}
