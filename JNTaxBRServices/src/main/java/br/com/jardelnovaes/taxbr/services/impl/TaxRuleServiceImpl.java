package br.com.jardelnovaes.taxbr.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;

import br.com.jardelnovaes.taxbr.models.AddressState;
import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.models.Operation;
import br.com.jardelnovaes.taxbr.models.PersonType;
import br.com.jardelnovaes.taxbr.models.TaxRule;
import br.com.jardelnovaes.taxbr.models.TransactionType;
import br.com.jardelnovaes.taxbr.services.QueryTypeFindTaxRuleEnum;
import br.com.jardelnovaes.taxbr.services.TaxRuleService;

public class TaxRuleServiceImpl extends GenericServiceImpl<TaxRule> implements TaxRuleService{

	public TaxRule findRule(String fromState, String toState, Long transactionType, Long operation,
			Long personType, String ncm, Integer exNCM, String cest, Integer itemId,
			Long companyId) throws Exception {
		
		return findRule(fromState, toState, transactionType, operation, personType, ncm, exNCM, cest, itemId, companyId, false);
	}
	
	public TaxRule findRule(String fromState, String toState, Long transactionType, Long operation,
			Long personType, String ncm, Integer exNCM, String cest, Integer itemId,
			Long companyId, boolean isShowInactives) throws Exception {
		return findRule(fromState, toState, transactionType, operation, personType, ncm, exNCM, cest, itemId, companyId, isShowInactives, QueryTypeFindTaxRuleEnum.AllTaxes);
	}
	
	public TaxRule findRule(String fromState, String toState, Long transactionType, Long operation,
							Long personType, String ncm, Integer exNCM, String cest, Integer itemId,
							Long companyId, boolean isShowInactives, QueryTypeFindTaxRuleEnum queryType) throws Exception {
		
		TaxRule taxRuleFilter = new TaxRule();
		
		taxRuleFilter.setFromState(new AddressState(fromState));
		taxRuleFilter.setToState(new AddressState(toState));
		
		if((transactionType != null) && (transactionType > 0))
			taxRuleFilter.setTransactionType(new TransactionType(transactionType));
		
		if((operation != null) && (operation > 0))
			taxRuleFilter.setOperation(new Operation(operation));
		
		if((personType != null) && (personType > 0))
			taxRuleFilter.setPersonType(new PersonType(personType));
		
		
		taxRuleFilter.setNCM(ncm);
		
		if((exNCM != null) && (exNCM > 0))
			taxRuleFilter.setExNCM(exNCM);
		
		taxRuleFilter.setCEST(cest);
		
		if((itemId != null) && (itemId > 0))
			taxRuleFilter.setItemId(itemId);
	
		if((companyId != null) && (companyId > 0))
			taxRuleFilter.setCompany(new Company(companyId));
		
		return findRule(taxRuleFilter, isShowInactives, queryType);
	}
	
	public TaxRule findRule(TaxRule taxRuleFilterOptions) throws Exception {
		return findRule(taxRuleFilterOptions, false);
	}
	
	public TaxRule findRule(TaxRule taxRuleFilterOptions, boolean isShowInactives) throws Exception {
		return findRule(taxRuleFilterOptions, isShowInactives, QueryTypeFindTaxRuleEnum.AllTaxes);
	}
	
	public TaxRule findRule(TaxRule taxRuleFilterOptions, boolean isShowInactives, QueryTypeFindTaxRuleEnum queryType) throws Exception {	
		
		Join<TaxRule, TransactionType> leftJoinTrans = null; 
		Join<TaxRule, TransactionType> leftJoinOper = null;
		Join<TaxRule, TransactionType> leftJoinPerson = null;
		
		this.validateFilters(taxRuleFilterOptions);

		//TODO Permitir N empresas aqui ou cada empresa tem que ter um usu�rio de integra��o? (e pega a Company do usu�rio?
		/*
		if((companyId != null) && (companyId > 0))
			taxRuleFilter.setCompany(new Company(companyId));
		*/
		
		if (queryType == null)
			queryType = QueryTypeFindTaxRuleEnum.AllTaxes;
		
		dao.createEntityCriteria();
		
		leftJoinTrans = dao.getDaoCriterea().leftJoin("transactionType");
		leftJoinOper = dao.getDaoCriterea().leftJoin("operation");
		leftJoinPerson = dao.getDaoCriterea().leftJoin("personType");
		
		dao.getDaoCriterea().joinWithEqual(
				dao.getDaoCriterea().join("fromState"), 
				"id", taxRuleFilterOptions.getFromState().getId());
		
		dao.getDaoCriterea().joinWithEqual(
				dao.getDaoCriterea().join("toState"), 
				"id", taxRuleFilterOptions.getToState().getId());
		
		
		if((taxRuleFilterOptions.getTransactionType() != null) && (taxRuleFilterOptions.getTransactionType().getId() != 0)){		
			dao.getDaoCriterea().joinWithOn(leftJoinTrans, "id", taxRuleFilterOptions.getTransactionType().getId());
		}
		else{			
			dao.getDaoCriterea().joinWithIsNull(leftJoinTrans, "id");
		}
		
		if((taxRuleFilterOptions.getOperation() != null) && (taxRuleFilterOptions.getOperation().getId() != 0)){
			dao.getDaoCriterea().joinWithOn(leftJoinOper, "id", taxRuleFilterOptions.getOperation().getId());
		}
		else{			
			dao.getDaoCriterea().joinWithIsNull(leftJoinOper, "id");
		}
		
		if((taxRuleFilterOptions.getPersonType() != null) && (taxRuleFilterOptions.getPersonType().getId() != 0)){			
			dao.getDaoCriterea().joinWithOn(leftJoinPerson, "id", taxRuleFilterOptions.getPersonType().getId());
		}
		else{
			dao.getDaoCriterea().joinWithIsNull(leftJoinPerson, "id");
		}
		
		this.proccessSimpleConditions(taxRuleFilterOptions);		
		
		if(!isShowInactives){
			dao.getDaoCriterea().equal("active", true);
		}
		
		this.proccessQueryType(queryType);	
		this.proccessOrder(new Join[] {leftJoinPerson, leftJoinOper, leftJoinTrans});
		
		TaxRule ret = dao.getFirst();
		if(ret == null)
			ret = new TaxRule();
		
		this.updateTaxesByQueryType(ret, queryType);
		
		destroyObjects(new Object[] {leftJoinTrans, leftJoinOper, leftJoinPerson, taxRuleFilterOptions});
				
		return ret;
	}
	
	private void validateFilters(TaxRule taxRuleFilterOptions) throws Exception {
		if ((taxRuleFilterOptions.getFromState() == null) || (taxRuleFilterOptions.getFromState().getId().isEmpty())){
			throw new Exception("From State Required Filter wasn't defined!");
		}
		
		if ((taxRuleFilterOptions.getToState() == null) || (taxRuleFilterOptions.getToState().getId().isEmpty())){
			throw new Exception("To State Required Filter wasn't defined!");
		}			
	}
	
	private void proccessQueryType(QueryTypeFindTaxRuleEnum queryType) {
		switch (queryType) {				
			case AllTaxes:
				// porque n�o Usar taxRuleICMS is not null???? Pq a query fica taxRule.id is not null o que daria problema, pois o taxRule.id pode n�o ser nulo e mesmo assim pode n�o haver uma TaxRuleICMS.
				//dao.getDaoCriterea().isNotNull("taxRuleICMS");
				dao.getDaoCriterea().join("taxRuleICMS");
				dao.getDaoCriterea().join("taxRulePISCOFINS");
				break;
			case OnlyICMS:
				dao.getDaoCriterea().join("taxRuleICMS");
				break;
			case OnlyPISCOFINS:				
				dao.getDaoCriterea().join("taxRulePISCOFINS");
				break;
			default:
				break;
		}
	}
	
	private void updateTaxesByQueryType(TaxRule taxRule, QueryTypeFindTaxRuleEnum queryType) {
	
		switch (queryType) {
			case OnlyICMS:
				taxRule.setTaxRulePISCOFINS(null);
				break;
			case OnlyPISCOFINS:
				taxRule.setTaxRuleICMS(null);
				break;	
			default:
				break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void proccessOrder(@SuppressWarnings("rawtypes") Join[] joins) {
		List<Order> order = new ArrayList<Order>();
		
		order.add(dao.getDaoCriterea().getDescOrder("itemId"));
		order.add(dao.getDaoCriterea().getDescOrder("CEST"));
		order.add(dao.getDaoCriterea().getDescOrder("ExNCM"));
		order.add(dao.getDaoCriterea().getDescOrder("NCM"));

		for (int i = 0; i < joins.length; i++) {
			order.add(dao.getDaoCriterea().getDescOrderFromJoin(joins[i], "id"));				
		}
		
		dao.getDaoCriterea().orderBy(order);		
	}
	
	private void proccessSimpleConditions(TaxRule taxRuleFilterOptions) {
		if(taxRuleFilterOptions.getNCM().equals(TaxRule.DEFAULT_ZERO_NUMSTR)){
			dao.getDaoCriterea().equal("NCM", TaxRule.DEFAULT_ZERO_NUMSTR);
		}
		else {
			dao.getDaoCriterea().in("NCM", new String[]{ TaxRule.DEFAULT_ZERO_NUMSTR, taxRuleFilterOptions.getNCM()});
		}
		
		if(taxRuleFilterOptions.getExNCM() == 0){
			dao.getDaoCriterea().equal("ExNCM", taxRuleFilterOptions.getExNCM());
		}
		else{
			dao.getDaoCriterea().in("ExNCM", new Integer[]{0, taxRuleFilterOptions.getExNCM()});
		}
		
		if(taxRuleFilterOptions.getCEST().equals(TaxRule.DEFAULT_ZERO_NUMSTR)){
			dao.getDaoCriterea().equal("CEST", TaxRule.DEFAULT_ZERO_NUMSTR);			
		}
		else {
			dao.getDaoCriterea().in("CEST", new String[]{ TaxRule.DEFAULT_ZERO_NUMSTR, taxRuleFilterOptions.getCEST()});
		}
			
		if(taxRuleFilterOptions.getItemId() == 0){
			dao.getDaoCriterea().equal("itemId", taxRuleFilterOptions.getItemId());			
		}
		else{
			dao.getDaoCriterea().in("itemId",new Integer[]{0, taxRuleFilterOptions.getItemId()});
		}
	}
}
