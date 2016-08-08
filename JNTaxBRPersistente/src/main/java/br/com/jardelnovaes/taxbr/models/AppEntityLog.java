package br.com.jardelnovaes.taxbr.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="APP_AppEntityLog")
public class AppEntityLog {
private static final String SEQ_NAME = "SEQ_PK_AppEntityLog";
	
	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=SEQ_NAME)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, initialValue = 1, allocationSize=1)
	@Column(name="Id", nullable = false)
	private long id;
	
	@Column(name="LogDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)	
	private Date date;
		
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="UserId", nullable = false) 	
	private AppUser user;
	
	@Column(name="LogType", nullable = false)
	private int logType; //1-Insert; 2-Update; 3-Delete;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="EntityId", nullable = false)
	private AppLogEntityId entity;
	
	@Column(name="EntityIdValue", nullable = true, length=100)
	private String entityIdValue;

	public AppEntityLog(){
		date = new Date();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {		
		return date;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public AppEntityLogTypeEnum getLogType() {
		return AppEntityLogTypeEnum.getValue(logType);
	}

	public void setLogType(AppEntityLogTypeEnum logType) {
		this.logType = logType.getValue();
	}

	public AppLogEntityId getEntity() {
		return entity;
	}

	public void setEntity(AppLogEntityId entity) {
		this.entity = entity;
	}
	
	public String getEntityIdValue() {
		return entityIdValue;
	}

	public void setEntityIdValue(String entityIdValue) {
		this.entityIdValue = entityIdValue;
	}

	//@Transient
	public void isAppLogValid() throws Exception{
		if ((getUser() == null) || (getUser().getId() <= 0))
    		throw new Exception("An invalid application user was found to log the information!");
    	
    	if ((getEntity() == null) || (getEntity().getId() <= 0))
    		throw new Exception("An invalid entity log indetify was found to log the information!");
    		
    }
	
}
