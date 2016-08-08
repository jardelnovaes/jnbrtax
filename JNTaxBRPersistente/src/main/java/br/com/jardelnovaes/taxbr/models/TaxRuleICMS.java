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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "APP_TaxRuleICMS")
public class TaxRuleICMS {
	@Id	
	@JsonIgnore
	private long taxRuleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ICMSCSTId", nullable = false)
	private ICMSCST icmsCST;

	@Column(name = "TaxPayerRate", nullable = false, precision = 5, scale = 2)
	private BigDecimal taxPayerRate;

	@Column(name = "FinalConsumerRate", nullable = false, precision = 5, scale = 2)
	private BigDecimal finalConsumerRate;

	@Column(name = "ReductionPercent", nullable = false, precision = 5, scale = 2)
	private BigDecimal reductionPercent = new BigDecimal(0);

	@Column(name = "STRate", nullable = false, precision = 5, scale = 2)
	private BigDecimal stRate = new BigDecimal(0);;

	@Column(name = "MVARate", nullable = false, precision = 5, scale = 2)
	private BigDecimal mvaRate = new BigDecimal(0);;

	@Column(name = "STReductionPercent", nullable = false, precision = 5, scale = 2)
	private BigDecimal stReductionPercent = new BigDecimal(0);;

	/*
	 * TaxRuleICMS ICMSType CST TaxPayerRate FinalConsumerRate ReductionPercent
	 * STRate MVARate STReductionPercent LegalNotes
	 */

	@Column(name = "LegalNotes", nullable = true, length = 200)
	private String legalNotes;

	@Column(name = "IsActive", nullable = false)
	private boolean active = true;

	@OneToOne
	@MapsId
    @JoinColumn(name = "TaxRuleId")
	@JsonIgnore
	private TaxRule taxRule;

	public TaxRule getTaxRule() {
		return taxRule;
	}

	public void setTaxRule(TaxRule taxRule) {
		this.taxRule = taxRule;
		this.taxRuleId = taxRule.getId();
	}

	public long getTaxRuleId() {
		if((this.taxRule != null))
			this.taxRuleId = this.taxRule.getId();
		
		return this.taxRuleId;
	}

	public ICMSCST getIcmsCST() {
		return icmsCST;
	}

	public void setIcmsCST(ICMSCST icmsCST) {
		this.icmsCST = icmsCST;
	}

	public BigDecimal getTaxPayerRate() {
		return taxPayerRate;
	}

	public void setTaxPayerRate(BigDecimal taxPayerRate) {
		this.taxPayerRate = taxPayerRate;
	}

	public BigDecimal getFinalConsumerRate() {
		return finalConsumerRate;
	}

	public void setFinalConsumerRate(BigDecimal finalConsumerRate) {
		this.finalConsumerRate = finalConsumerRate;
	}

	public BigDecimal getReductionPercent() {
		return reductionPercent;
	}

	public void setReductionPercent(BigDecimal reductionPercent) {
		this.reductionPercent = reductionPercent;
	}

	public BigDecimal getStRate() {
		return stRate;
	}

	public void setStRate(BigDecimal stRate) {
		this.stRate = stRate;
	}

	public BigDecimal getMvaRate() {
		return mvaRate;
	}

	public void setMvaRate(BigDecimal mvaRate) {
		this.mvaRate = mvaRate;
	}

	public BigDecimal getStReductionPercent() {
		return stReductionPercent;
	}

	public void setStReductionPercent(BigDecimal stReductionPercent) {
		this.stReductionPercent = stReductionPercent;
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

	public void setActive(boolean isActive) {
		this.active = isActive;
	}
}
