package br.com.jardelnovaes.taxbr.services;

public enum QueryTypeFindTaxRuleEnum {
	AllTaxes(0),
	OnlyICMS(1),
	OnlyPISCOFINS(2);
	
	private int value;
	private QueryTypeFindTaxRuleEnum(int value){
		this.value = value;
	}
		
	public int getValue(){
		return this.value;
	}
	
	public static QueryTypeFindTaxRuleEnum getValue(int refValue){
		for( QueryTypeFindTaxRuleEnum i : QueryTypeFindTaxRuleEnum.values() ){
            if (i.getValue() == refValue)
               return i;
         }
		return null;
	}
}
