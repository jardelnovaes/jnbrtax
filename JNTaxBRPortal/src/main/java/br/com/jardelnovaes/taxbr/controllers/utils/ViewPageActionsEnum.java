package br.com.jardelnovaes.taxbr.controllers.utils;

public enum ViewPageActionsEnum {
	LIST("index"),
	CREATE("create"),
	EDIT("edit"),
	DELETE("delete");
	
	private String value;
	private ViewPageActionsEnum(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}	
}
