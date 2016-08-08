package br.com.jardelnovaes.taxbr.models;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import org.springframework.context.annotation.PropertySource;

//@PropertySource("classpath:app.models.properties")
@Entity
@Cacheable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_ICMSType")
public class ICMSType implements GenericSimpleEntity {
	
	private static final String SEQ_NAME = "SEQ_PK_ICMSType";
	
	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, initialValue = 1, allocationSize=1)
	@Column(name="Id", nullable = false)
	private long id;
	
	@Column(name="Name", nullable = false, length=50)
	private String name;
	
	@Column(name="IsTaxed", nullable = false)
	private boolean taxed = true;
	
	@Column(name="HasReduction", nullable = false)
	private boolean hasReduction = false;
	
	@Column(name="IsST", nullable = false)
	private boolean ST = false;
	
	@Column(name="IsSTTaxed", nullable = false)
	private boolean STTaxed = false;
	
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
	
	public boolean isTaxed() {
		return taxed;
	}
	
	public void setTaxed(boolean isTaxed) {
		this.taxed = isTaxed;
	}
	
	public boolean isHasReduction() {
		return hasReduction;
	}
	
	public void setHasReduction(boolean hasReduction) {
		this.hasReduction = hasReduction;
	}

	public boolean isST() {
		return ST;
	}

	public void setST(boolean isST) {
		this.ST = isST;
	}

	public boolean isSTTaxed() {
		return STTaxed;
	}

	public void setSTTaxed(boolean isSTTaxed) {
		STTaxed = isSTTaxed;
	}
	
}
