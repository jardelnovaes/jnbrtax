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
@Table(name="APP_AddressState")
public class AddressState {
	@Id
	@Column(name="Id", nullable = false, length=2)
	private String id;
	
	@Column(name="Name", nullable = false, length=100)
	private String name;
	
	@Column(name="IsSpecial", nullable = false)
	private boolean special = false;
	
	public AddressState(){
		
	}
	
	public AddressState(String ufId){
		this.setId(ufId);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSpecial() {
		return special;
	}
	public void setSpecial(boolean isSpecial) {
		this.special = isSpecial;
	}
	
		
}
