package br.com.jardelnovaes.taxbr.controllers;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.jardelnovaes.taxbr.controllers.utils.AppControllerUtils;
import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Operation;
import br.com.jardelnovaes.taxbr.models.PersonType;
import br.com.jardelnovaes.taxbr.models.TaxRule;
import br.com.jardelnovaes.taxbr.models.TransactionType;
import br.com.jardelnovaes.taxbr.persitence.PagedData;
import br.com.jardelnovaes.taxbr.services.AddressStateService;
import br.com.jardelnovaes.taxbr.services.GenericService;
import br.com.jardelnovaes.taxbr.services.QueryTypeFindTaxRuleEnum;
import br.com.jardelnovaes.taxbr.services.TaxRuleService;


@Controller
public class TaxRuleController {
	private static final int RESULT_PAGE_SIZE = 10;
	
	private static final Logger logger = LoggerFactory.getLogger(TaxRuleController.class);
	
	//TODO Ver se passo (ou não) o uso desses serviços "agregados" para a AppControllerUtils
	private TaxRuleService service;
	private AddressStateService addressStateService;
	private GenericService<TransactionType> transactionTypeService;
	private GenericService<Operation> operationService;
	private GenericService<PersonType> personTypeService;
	
	@Autowired(required=true)
    @Qualifier(value="taxRuleService")	
    public void setService(TaxRuleService service){
		logger.info("Service bean was Injected => " + service.getClass().getName());
        this.service = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="addressStateService")	
    public void setAddressStateService(AddressStateService service){
		logger.info("AddressState Service bean was Injected => " + service.toString());		
        this.addressStateService = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="transactionTypeService")	
    public void setTransactionTypeService(GenericService<TransactionType> service){
		logger.info("Transaction Service bean was Injected => " + service.toString());		
        this.transactionTypeService = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="operationService")	
    public void setOperationService(GenericService<Operation> service){
		logger.info("Operation Service bean was Injected => " + service.toString());		
        this.operationService = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="personTypeService")	
    public void setPersonTypeService(GenericService<PersonType> service){
		logger.info("Person Service Service bean was Injected => " + service.toString());		
        this.personTypeService = service;
    }
	
	@RequestMapping(value={"/taxRules"}, method = RequestMethod.GET)
	public String index(Locale locale, ServletRequest request, Model model) throws Exception {
		
		int pageNum = ServletRequestUtils.getIntParameter(request, "pgNum", 0);
		String asc = ServletRequestUtils.getStringParameter(request, "asc", "");
		String desc = ServletRequestUtils.getStringParameter(request, "desc", "");
		String order = "desc";
		
		PagedData pg = new PagedData(pageNum, RESULT_PAGE_SIZE);
		if(!asc.isEmpty()){
			pg.setOrderPropertyName(asc);
			order = "desc";
		}
		else if(!desc.isEmpty()){
			pg.setDescending(true);
			pg.setOrderPropertyName(desc);
			order = "asc";
		}		
		
		service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		service.setPagedData(pg);
		
		model.addAttribute("pagedData", pg);
		model.addAttribute("orderDirection", order);
		model.addAttribute("rules", service.getAll());
		
		return "taxRules/index";
	}
	
	@RequestMapping(value = "/taxRules/create", method = RequestMethod.GET)
	public String add(Model model) throws Exception {
		
		addListObjectsInModel(model);
		model.addAttribute("action", "create");
		model.addAttribute("rule", service.getNew());
		
		return "taxRules/createOrEdit";
	}
	
	@RequestMapping(value = "/taxRules/create", method = RequestMethod.POST)	
	public String add(TaxRule entity, BindingResult result, Model model) throws Exception {
		return actionPOSTByObject(entity, result, model, "create");
		
		//@Valid User user, BindingResult result, Model model
	    //model.addAttribute("greeting", "Hello Spring MVC");		
		//AppUser usr = new AppUser();		
		//user.setName("Salvo");		
		//model.addAttribute("user", user);		
	    // return "redirect:/hello";	    
	}
	
	@RequestMapping(value="/taxRules/edit/{id}", method = RequestMethod.GET)
	public String edit(Model model,	        
	        @PathVariable(value = "id") long id) throws Exception {
		return actionGETById(model, "../edit", id);		
	}
		
	@RequestMapping(value = "/taxRules/edit", method = RequestMethod.POST)	
	public String edit(TaxRule entity, BindingResult result, Model model) throws Exception {
		
		return actionPOSTByObject(entity, result, model, "edit");		
	}
	
	@RequestMapping(value="/taxRules/delete/{id}", method = RequestMethod.GET)
	public String delete(Model model,	        
	        @PathVariable(value = "id") long id) throws Exception {	    
		return actionGETById(model, "../delete", "taxRules/delete", id);
	}
		
	@RequestMapping(value = "/taxRules/delete", method = RequestMethod.POST)	
	public String delete(TaxRule entity, BindingResult result, Model model) throws Exception {
		
		return actionPOSTByObject(entity, result, model, "delete", "taxRules/delete", 
				"Regra excluída com sucesso!", 
				"Não foi possível excluir a regra!", 
				true);				
	}
	
	private void addListObjectsInModel(Model model) throws Exception{
		addListObjectsInModel(model, null);
	}
	private void addListObjectsInModel(Model model, Operation operation) throws Exception{
		AppUser user = AppControllerUtils.getCurrentUser();
		
		transactionTypeService.setCurrentAppUser(user);
		addressStateService.setCurrentAppUser(user);
		personTypeService.setCurrentAppUser(user);		
		
		model.addAttribute("transactionTypeList", transactionTypeService.getAll());
		model.addAttribute("addressStateList", addressStateService.getAll());		
		model.addAttribute("personTypeList", personTypeService.getAll());
		
		model.addAttribute("pisCofinsCSTList", AppControllerUtils.getPisCofinsCSTList());
		
		model.addAttribute("icmsCSTList", AppControllerUtils.getIcmsCSTList());
		
		
		if (operation != null && operation.getId() > 0){
			ArrayList<Operation> opers = new ArrayList<Operation>();
			operationService.setCurrentAppUser(user);	
			opers.add(operationService.getById(operation.getId()));			
			model.addAttribute("operationList", opers);
		}
	}
	
	private void refreshObjects(TaxRule entity){
		
		//Não precisa atualizar esses objetos:
		/*
		if(entity.getTransactionType().getId() > 0){
			entity.setTransactionType(transactionTypeService.getById(entity.getTransactionType().getId()));
		}		
		if(!entity.getFromState().getId().isEmpty()){
			entity.setFromState(addressStateService.getById(entity.getFromState().getId()));
		}
		if(!entity.getToState().getId().isEmpty()){
			entity.setToState(addressStateService.getById(entity.getToState().getId()));
		}
		if(entity.getOperation().getId() > 0){
			entity.setOperation(operationService.getById(entity.getOperation().getId()));
		}
		if(entity.getPersonType().getId() > 0){
			entity.setPersonType(personTypeService.getById(entity.getPersonType().getId()));
		}
		else{
			entity.setPersonType(null); //tratamento incluso na classe Entity.
		}
		*/
	}
	
	private String actionGETById(Model model, String actionName, long id) throws Exception{
		return actionGETById(model, actionName, "taxRules/createOrEdit", id);
	}
	
	private String actionGETById(Model model, String actionName, String returnPage, long id) throws Exception{
		String errMsg;
		model.addAttribute("action", actionName);
		
		service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		TaxRule entity = service.getById(id);
		
		if (entity == null) {
			errMsg = "Regra não localizada (ID: " + String.valueOf(id) + ")" ;
			logger.info(errMsg);			
			model.addAttribute("errorMsg", errMsg);
			model.addAttribute("rule", service.getNew());
		}
		else{	
			addListObjectsInModel(model, entity.getOperation());
			
			model.addAttribute("rule", entity);
		}
	    
	    return returnPage;
	}
	
	private String actionPOSTByObject(TaxRule entity, BindingResult result, Model model, String actionName) throws Exception {
		return actionPOSTByObject(entity, result, model, actionName, 
				"taxRules/createOrEdit", "Regra Salva com sucesso!", 
				"Não foi possível salvar a regra!", 
				false);
	}
			
	private String actionPOSTByObject(TaxRule entity, BindingResult result, Model model, 
			String actionName, String returnPage, String successMsg, 
			String errorMsg, Boolean isDelete) throws Exception {	
		try {
			model.addAttribute("action", actionName);
			service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
			refreshObjects(entity);

			//TODO Teste (sem transaction)
			//service.setUseTransaction(false);
			
			if(isDelete)
				service.delete(entity);
			else
				service.save(entity);
		
			model.addAttribute("successMsg", successMsg);
			
			
		} catch (Exception e) {			
			model.addAttribute("errorMsg", errorMsg + "\nErro: "+e.getLocalizedMessage());
			logger.error((String) model.asMap().get("errorMsg"));
		}
		addListObjectsInModel(model);
		model.addAttribute("rule", entity);
		return returnPage;		
	}
	
	@RequestMapping(value={"/rest/taxRules/getTaxRule"}, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public TaxRule getTaxRule(
			@RequestParam(required = true) String fromState,
			@RequestParam(required = true) String toState,
			@RequestParam(required = false) Long transactionType,
			@RequestParam(required = false) Long operation,
			@RequestParam(required = false) Long personType,
			@RequestParam(required = false) String ncm,
			@RequestParam(required = false) Integer exNCM,
			@RequestParam(required = false) String cest,
			@RequestParam(required = false) Integer itemId,
			@RequestParam(required = false) QueryTypeFindTaxRuleEnum queryType
	) throws Exception {
		service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		
		return service.findRule(
				fromState, toState, transactionType, operation, personType, ncm, exNCM, 
				cest, itemId, AppControllerUtils.getCurrentUser().getCompany().getId(),
				false, queryType);
	}
	
	@RequestMapping(value={"/ws/taxRules/getTaxRule"}, method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public TaxRule getTaxRuleSOAP(
			@RequestParam(required = true) String fromState,
			@RequestParam(required = true) String toState,
			@RequestParam(required = false) Long transactionType,
			@RequestParam(required = false) Long operation,
			@RequestParam(required = false) Long personType,
			@RequestParam(required = false) String ncm,
			@RequestParam(required = false) Integer exNCM,
			@RequestParam(required = false) String cest,
			@RequestParam(required = false) Integer itemId,
			@RequestParam(required = false) QueryTypeFindTaxRuleEnum queryType
	) throws Exception {
		service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		
		return service.findRule(
				fromState, toState, transactionType, operation, personType, ncm, exNCM, 
				cest, itemId, AppControllerUtils.getCurrentUser().getCompany().getId(),
				false, queryType);
	}
}
