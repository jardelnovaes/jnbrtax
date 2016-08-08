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
		//TODO Fazer multi-idioma ou trazer de um dicion�rio.
		String ret = null;
		switch (this.value) {
			case 1:
				ret = "Inclus�o";
				break;
			case 2:
				ret = "Atualiza��o";
				break;
			case 3:			
				ret = "Exclus�o";
				break;		
		}
		return ret;
	}	
}
