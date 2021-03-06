package br.com.jardelnovaes.taxbr.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_Operation")
public class Operation extends AbstractByCompanyEntity  implements GenericByCompanyEntity  {
	private static final String SEQ_NAME = "SEQ_PK_Operation";
	
	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, initialValue = 1, allocationSize=1)
	@Column(name="Id", nullable = false)
	private long id;
	
	@Column(name="Name", nullable = false, length=50)
	private String name;
	
	@Column(name="IsActive", nullable = false)
	private boolean active = true;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="TransactionTypeId", nullable = false) //, updatable = false, insertable = false)		
	private TransactionType transactionType;
	
	public Operation(){}
	public Operation(long id){
		setId(id);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public TransactionType getTransactionType() {		
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;		
		if(!transactionType.getOperations().contains(this))
			transactionType.addOperation(this);			
	}
}
