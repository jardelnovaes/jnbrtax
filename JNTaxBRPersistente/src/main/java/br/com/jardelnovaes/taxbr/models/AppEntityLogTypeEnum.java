package br.com.jardelnovaes.taxbr.models;

public enum AppEntityLogTypeEnum {
	Insert(1),
	Update(2),
	Delete(3);
	
	private int value;
	private AppEntityLogTypeEnum(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public static AppEntityLogTypeEnum getValue(int refValue){
		for( AppEntityLogTypeEnum i : AppEntityLogTypeEnum.values() ){
            if (i.getValue() == refValue)
               return i;
         }
		return null;
	}
	
	public String getDisplayName(){
		//TODO Fazer multi-idioma ou trazer de um dicionário.
		String ret = null;
		switch (this.value) {
			case 1:
				ret = "Inclusão";
				break;
			case 2:
				ret = "Atualização";
				break;
			case 3:			
				ret = "Exclusão";
				break;		
		}
		return ret;
	}	
}
