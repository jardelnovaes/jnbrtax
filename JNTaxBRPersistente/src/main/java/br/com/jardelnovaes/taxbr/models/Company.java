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

@Entity
@Cacheable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_Company")
public class Company implements GenericSimpleEntity{
	
		private static final String SEQ_NAME = "SEQ_PK_Company";
		
		@Id	
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=SEQ_NAME)
		@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, initialValue = 1, allocationSize=1)
		@Column(name="Id", nullable = false)
		private long id;
		
		@Column(name="Name", nullable = false, length=50)
		private String name;
		
		public Company(){}
		public Company(long id){
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
}
