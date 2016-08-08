package br.com.jardelnovaes.taxbr.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Cacheable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_TransactionType")
public class TransactionType  extends AbstractByCompanyEntity implements GenericByCompanyEntity {
	private static final String SEQ_NAME = "SEQ_PK_TransactionType";
	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, initialValue = 1, allocationSize=1)
	@Column(name="Id", nullable = false)
	private long id;
	
	@Column(name="Name", nullable = false, length=50)
	private String name;

	@Column(name="IsOut", nullable = false)
	private boolean out = true;
	

	@OneToMany(mappedBy="transactionType")	
	private List<Operation> operations;
	
	public TransactionType(){
		operations = new ArrayList<Operation>();
	}
	
	public TransactionType(long id){
		operations = new ArrayList<Operation>();
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

	public boolean isOut() {
		return out;
	}

	public void setOut(boolean isOut) {
		this.out = isOut;
	}

	public List<Operation> getOperations() {
	        List<Operation> listaSegura = Collections.unmodifiableList(this.operations);  
	        return listaSegura;
	}
	 
	public void addOperation(Operation operation){		
		if(this.operations.contains(operation))
			this.operations.remove(operation);
		
		this.operations.add(operation);
		if(operation.getTransactionType() != this)
			operation.setTransactionType(this);
	}
	
	public void removeOperation(Operation operation){
		if(this.operations.contains(operation))
			this.operations.remove(operation);
		
		if(operation.getTransactionType() == this)
			operation.setTransactionType(null);		
	}
}
