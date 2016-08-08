package br.com.jardelnovaes.taxbr.models;
		
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.jardelnovaes.taxbr.annotations.DisplayName;

//import org.hibernate.annotations.Fetch;

@Entity
@Cacheable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_AppUsers")
public class AppUser extends AbstractByCompanyEntity {	
	/*
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 
	 @GeneratedValue(generator = "table", strategy=GenerationType.TABLE)
     @TableGenerator(name = "table", allocationSize = 10)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PK_PersonType")	
	//@SequenceGenerator(initialValue = 1, allocationSize=1, name = "SEQ_PK_PersonType", sequenceName = "SEQ_PK_PersonType")
	@GeneratedValue(generator = "table2", strategy=GenerationType.TABLE)
	@SequenceGenerator(initialValue = 1, increment_size = 1, allocationSize=1, prefer_sequence_per_entity = true)
	//prefer_sequence_per_entity = true
	//org.hibernate.id.enhanced.SequenceStyleGenerator
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PK_PersonType")
	//strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator"


		//@LazyToOne(value = LazyToOneOption.NO_PROXY)
		@Fetch(FetchMode.SELECT)
		@Fetch(FetchMode.JOIN)
		@OneToOne(fetch=FetchType.LAZY, optional=false)
		@JoinColumn(name="TransactionTypeId", nullable = false) //, updatable = false, insertable = false)		
		private TransactionType transactionType;
		
		problema do refresh bidirecional (Ex: @OneToOne)
			Não recomendado (Buscar novamente no banco):
				entityManager.persist(filho);
				entityManager.refresh(pai);
			Recomendado (Tratar no model):
				class Pai
				public void adicionarFilho(Filho p) {
			        this.filhos.add(p);
			        if (filho.getPai() != this) {
			            filho.setPai(this);
			        }
			    }
				class Filho 
				public void setPai(Pai t) {
			        this.pai = t;
			        if (!t.getFilhos().contains(this)) {
			            t.getFilhos().add(this);
			        }
			    }
	 */
	
	private static final String SEQ_NAME = "SEQ_PK_AppUsers";
	
	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, initialValue = 1, allocationSize=1)
	@Column(name="Id", nullable = false)
	private int id;
	
	@DisplayName("Nome")
	@Column(name="Name", nullable = false, length=255)	
	private String name;
	
	@DisplayName("e-mail")
	@Column(name="EMail", nullable = false, length=255, unique=true)	
	private String email;
	
	@DisplayName("Ativo")
	@Column(name="Active", nullable = false)	
	private boolean active;
	
	@DisplayName("Senha")
	@Column(name="Password", nullable = false, length=32)
	private String password;
	
	@Transient
	private String confirmPassword;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
