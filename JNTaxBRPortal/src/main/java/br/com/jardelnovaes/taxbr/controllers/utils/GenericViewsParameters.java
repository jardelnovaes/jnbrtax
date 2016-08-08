package br.com.jardelnovaes.taxbr.controllers.utils;

import java.util.ArrayList;

public class GenericViewsParameters {
	
	private String tableListName;
	private String activeAction;
	private ViewPageActionsEnum pageAction;
	private String actUrlPath;
	private String fieldIdName;	
	private String fieldIdDisplayName;
	private ArrayList<GenericViewField> genericViewFields; 
	
	//private ArrayList<String> fieldNames;
	//private ArrayList<String> fieldDisplayNames;
	
	private Class<?> entityClass;
	private boolean idIsEditable;
	private boolean delete;
	
	public GenericViewsParameters(){
		
	}
	
	public GenericViewsParameters(String tableListName, String activeAction, ViewPageActionsEnum pageAction,
								  String actUrlPath, String fieldIdName){
		this.tableListName = tableListName;
		this.activeAction = activeAction;
		this.pageAction = pageAction;
		this.actUrlPath = actUrlPath;
		this.fieldIdName = fieldIdName; 
	}
	
	public String getTableListName() {
		return tableListName;
	}
	public void setTableListName(String tableListName) {
		this.tableListName = tableListName;
	}
	public String getActiveAction() {
		return activeAction;
	}	
	public void setActiveAction(String activeAction) {
		this.activeAction = activeAction;
	}
	public String getPageAction() {
		return pageAction.getValue();
	}

	public void setPageAction(ViewPageActionsEnum pageAction) {
		this.pageAction = pageAction;
	}
	public String getActUrlPath() {
		return actUrlPath;
	}
	public void setActUrlPath(String actUrlPath) {
		this.actUrlPath = actUrlPath;
	}
	public String getFieldIdName() {
		return fieldIdName;
	}
	public void setFieldIdName(String fieldIdName) {
		this.fieldIdName = fieldIdName;
	}
	public String getFieldIdDisplayName() {
		return fieldIdDisplayName;
	}

	public ArrayList<GenericViewField> getGenericViewFields(){
		return this.genericViewFields;
	}
		
	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public boolean isIdIsEditable() {
		return idIsEditable;
	}

	public void setIdIsEditable(boolean idIsEditable) {
		this.idIsEditable = idIsEditable;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public void inicialize() throws Exception{
		genericViewFields = AppControllerUtils.getEntityFields(entityClass);
		
		for(GenericViewField field : genericViewFields){
			if(field.getFieldName().equals(fieldIdName)){
				fieldIdDisplayName = field.getFieldDisplay();
				field.setIdColumn(true);
				break;
			}
		}
	}
	
}
