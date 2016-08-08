package br.com.jardelnovaes.taxbr.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_TaxRule", uniqueConstraints=@UniqueConstraint(columnNames = {"CompanyId", "FromStateId", 
						   "ToStateId",  "TransactionTypeId", "OperationId", "NCM", "ExNCM", "CEST", "ItemId", 
						   "IsActive", "PersonTypeId"}))
public class TaxRule extends AbstractByCompanyEntity implements GenericByCompanySimpleEntity {
	private static final String SEQ_NAME = "SEQ_PK_TaxRule";
	
	public static final String DEFAULT_ZERO_NUMSTR = "";
	
	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, initialValue = 1, allocationSize=1)
	@Column(name="Id", nullable = false)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FromStateId", nullable = false) //, updatable = false, insertable = false)		
	private AddressState fromState;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ToStateId", nullable = false) //, updatable = false, insertable = false)	
	private AddressState toState;
	
	@Column(name="IsActive", nullable = false)
	private boolean active = true;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="TransactionTypeId", nullable = true) //, updatable = false, insertable = false)		
	private TransactionType transactionType;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="OperationId", nullable = true) //, updatable = false, insertable = false)		
	private Operation operation;
		
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="PersonTypeId", nullable = true) 
	private PersonType personType;
	
	@Column(name="NCM", nullable = true, length=8)
	private String NCM;
	
	@Column(name="ExNCM", nullable = true)
	private int ExNCM;
	
	@Column(name="CEST", nullable = true, length=7)
	private String CEST;
	
	@Column(name="ItemId", nullable = true)
	private int itemId;		
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy = "taxRule")
	private TaxRulePISCOFINS taxRulePISCOFINS;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy = "taxRule")
	private TaxRuleICMS taxRuleICMS;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		if(taxRulePISCOFINS != null)
			taxRulePISCOFINS.setTaxRule(this);
		
		if(taxRuleICMS != null)
			taxRuleICMS.setTaxRule(this);
	}

	public AddressState getFromState() {
		return fromState;
	}

	public void setFromState(AddressState fromState) {
		this.fromState = fromState;
	}

	public AddressState getToState() {
		return toState;
	}

	public void setToState(AddressState toState) {
		this.toState = toState;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}

	public TransactionType getTransactionType() {
		/*if((transactionType != null) && (transactionType.getId() == 0)){
			transactionType = null;
		}*/
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Operation getOperation() {
		/*if((operation != null) && (operation.getId() == 0)){
			operation = null;
		}*/
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public PersonType getPersonType() {
		//Para forçar o NULL dessa FK quando não for definido valor do Id.
		/*if((personType != null) && (personType.getId() == 0)){
			personType = null;
		}*/
		return personType;
	}

	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}

	public String getNCM() {		
		return super.getDefaultValueNumericString(NCM, DEFAULT_ZERO_NUMSTR);
	}

	public void setNCM(String nCM) {		
		NCM = super.getDefaultValueNumericString(nCM, DEFAULT_ZERO_NUMSTR);
	}

	public int getExNCM() {		
		return ExNCM;
	}

	public void setExNCM(int exNCM) {
		ExNCM = exNCM;
	}

	public String getCEST() {
		return super.getDefaultValueNumericString(CEST, DEFAULT_ZERO_NUMSTR);		
	}

	public void setCEST(String cEST) {
		CEST = super.getDefaultValueNumericString(cEST, DEFAULT_ZERO_NUMSTR);
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public TaxRulePISCOFINS getTaxRulePISCOFINS() {
		return taxRulePISCOFINS;		
	}

	public void setTaxRulePISCOFINS(TaxRulePISCOFINS taxRulePISCOFINS) {
		this.taxRulePISCOFINS = taxRulePISCOFINS;
		if(this.taxRulePISCOFINS != null){
			this.taxRulePISCOFINS.setTaxRule(this);			
		}
	}
	
	public TaxRuleICMS getTaxRuleICMS() {
		return taxRuleICMS;		
	}

	public void setTaxRuleICMS(TaxRuleICMS taxRuleICMS) {
		this.taxRuleICMS = taxRuleICMS;
		if(this.taxRuleICMS != null){
			this.taxRuleICMS.setTaxRule(this);			
		}
	}
	
}

/*
TaxRule									
id	FromState	ToState	TransactionType	Operation	PersonType	NCM	ExNCM	CEST	ItemId

									
TaxRuleICMS									
ICMSType	CST	TaxPayerRate	FinalConsumerRate	ReductionPercent	STRate	MVARate	STReductionPercent	LegalNotes	
									
									
TaxRulePISCOFINS									
PISCST	PISRate	COFINSCST	COFINSRate	LegalNotes	

TaxRuleISS
/*
 * @JoinColumn(name="COLUMN_NAME", referencedColumnName = "COLUMN_NAME_REF", nullable = false, updatable = false, insertable = false)
 */


