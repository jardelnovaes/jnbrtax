package br.com.jardelnovaes.taxbr.services;

import br.com.jardelnovaes.taxbr.models.TaxRule;

public interface TaxRuleService extends GenericService<TaxRule> {
	TaxRule findRule(String fromState, String toState, Long transactionType, Long operation,
					 Long personType, String ncm, Integer exNCM, String cest, 
					 Integer itemId, Long companyId) throws Exception ;
	
	TaxRule findRule(String fromState, String toState, Long transactionType, Long operation,
			 Long personType, String ncm, Integer exNCM, String cest, 
			 Integer itemId, Long companyId, boolean isShowInactives) throws Exception ;
	
	TaxRule findRule(String fromState, String toState, Long transactionType, Long operation,
			 Long personType, String ncm, Integer exNCM, String cest, 
			 Integer itemId, Long companyId, boolean isShowInactives, QueryTypeFindTaxRuleEnum queryType) throws Exception ;
	
	TaxRule findRule(TaxRule taxRuleFilterOptions) throws Exception ;
	TaxRule findRule(TaxRule taxRuleFilterOptions, boolean isShowInactives) throws Exception ;
	TaxRule findRule(TaxRule taxRuleFilterOptions, boolean isShowInactives, QueryTypeFindTaxRuleEnum queryType) throws Exception ;
	
}
