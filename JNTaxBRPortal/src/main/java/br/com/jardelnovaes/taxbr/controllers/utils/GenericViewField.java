package br.com.jardelnovaes.taxbr.controllers.utils;

public class GenericViewField {
	private boolean idColumn;
	private String fieldName;
	private String fieldDisplay;
	private String fieldType;
	
	public GenericViewField(){}
	public GenericViewField(String fieldName){
		this.fieldName = fieldName;
	}
	public GenericViewField(String fieldName, String fieldType){
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}	
	public GenericViewField(String fieldName, String fieldDisplay, String fieldType){
		this.fieldName = fieldName;
		this.fieldDisplay = fieldDisplay;
		this.fieldType = fieldType;
	}
	
	public boolean isIdColumn() {
		return idColumn;
	}
	public void setIdColumn(boolean isIdColumn) {
		this.idColumn = isIdColumn;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldDisplay() {
		return fieldDisplay;
	}
	public void setFieldDisplay(String fieldDisplay) {
		this.fieldDisplay = fieldDisplay;
	}
	public Object getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	
}
