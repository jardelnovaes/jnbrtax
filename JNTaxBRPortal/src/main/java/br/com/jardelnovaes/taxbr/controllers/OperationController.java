package br.com.jardelnovaes.taxbr.controllers;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.jardelnovaes.taxbr.controllers.utils.AppControllerUtils;
import br.com.jardelnovaes.taxbr.models.Operation;
import br.com.jardelnovaes.taxbr.models.TransactionType;
import br.com.jardelnovaes.taxbr.services.GenericService;
import br.com.jardelnovaes.taxbr.services.OperationService;


@Controller
public class OperationController {
	private static final String serviceBeanName = "operationService";
	private static final String baseFolder = "operations";
	private static final String deleteSuccessMsg = "Operação excluída com sucesso!";
	private static final String deleteErrorMsg = "Não foi possível excluir a operação!";
	private static final String saveSuccessMsg = "Operação salva com sucesso!" ;
	private static final String saveErrorMsg = "Não foi possível salvar a operação!";
	private static final String locateErrorMsg = "Operação não localizada (ID: {})" ;
		
	private static final Logger logger = LoggerFactory.getLogger(OperationController.class);
	
	private OperationService service;
	private GenericService<TransactionType> transactionTypeService;
	
	@Autowired(required=true)
    @Qualifier(value=serviceBeanName)	
    public void setService(OperationService service){
		logger.info("Service bean was Injected => " + service.toString());
		
        this.service = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="transactionTypeService")	
    public void setTransactionTypeService(GenericService<TransactionType> service){
		logger.info("Transaction Service bean was Injected => " + service.toString());		
        this.transactionTypeService = service;
    }
	
	@RequestMapping(value={"/" + baseFolder}, method = RequestMethod.GET)
	public String index(Locale locale, Model model)  throws Exception {
		service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		//transactionTypeService.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		model.addAttribute("items", service.getAll());
		
		return baseFolder + "/index";
	}
	
	@RequestMapping(value = "/" + baseFolder + "/create", method = RequestMethod.GET)
	public String add(Model model) throws Exception {
		model.addAttribute("action", "create");
		model.addAttribute("item", service.getNew());
		
		transactionTypeService.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		model.addAttribute("transactionList", transactionTypeService.getAll());
		
		return baseFolder + "/createOrEdit";
	}
	
	@RequestMapping(value = "/" + baseFolder + "/create", method = RequestMethod.POST)	
	public String add(Operation entity, BindingResult result, ModelMap model) throws Exception {		
		return actionPOSTByObject(entity, result, model, "create");	
	}
	
	@RequestMapping(value="/" + baseFolder + "/edit/{id}", method = RequestMethod.GET)
	public String edit(Model model,	        
	        @PathVariable(value = "id") long id) {	 
		model.addAttribute("transactionList", transactionTypeService.getAll());
		return actionGETById(model, "../edit", id);		
	}
		
	@RequestMapping(value = "/" + baseFolder + "/edit", method = RequestMethod.POST)	
	public String edit(Operation entity, BindingResult result, ModelMap model) throws Exception {
		
		return actionPOSTByObject(entity, result, model, "edit");		
	}
	
	@RequestMapping(value="/" + baseFolder + "/delete/{id}", method = RequestMethod.GET)
	public String delete(Model model,	        
	        @PathVariable(value = "id") long id) {	    
		return actionGETById(model, "../delete", baseFolder + "/delete", id);
	}
		
	@RequestMapping(value = "/" + baseFolder + "/delete", method = RequestMethod.POST)	
	public String delete(Operation entity, BindingResult result, ModelMap model) throws Exception {
		
		return actionPOSTByObject(entity, result, model, "delete", baseFolder + "/delete", 
					deleteSuccessMsg, deleteErrorMsg, true);				
	}
	
	@RequestMapping(value={"/" + baseFolder + "/getByTransType"}, method = RequestMethod.GET)
	@ResponseBody
	public List<Operation> getByTransactionType(@RequestParam long transTypeId) throws Exception {
		//http://localhost:9095/JNTaxBRPortal/operations/getByTransType?transTypeId=1
		service.setCurrentAppUser(AppControllerUtils.getCurrentUser());		
		return service.getByTransactionTypeId(transTypeId);
	}
	
	private void refreshObject(Operation entity){
		if((entity.getTransactionType() != null) && (entity.getTransactionType().getId() > 0)){
			entity.setTransactionType(transactionTypeService.getById(entity.getTransactionType().getId()));
		}
	}
	
	private String actionGETById(Model model, String actionName, long id){
		return actionGETById(model, actionName, baseFolder + "/createOrEdit", id);
	}
	
	private String actionGETById(Model model, String actionName, String returnPage, long id){
		String errMsg;
		model.addAttribute("action", actionName);
		
		Operation entity = service.getById(id);
		if (entity == null) {
			errMsg = String.format(locateErrorMsg, id);
			
			logger.info(errMsg);			
			model.addAttribute("errorMsg", errMsg);
			model.addAttribute("item", service.getNew());
		}
		else{
			model.addAttribute("item", entity);
		}
	    
	    return returnPage;
	}
	
	private String actionPOSTByObject(Operation entity, BindingResult result, ModelMap model, String actionName) throws Exception {
		return actionPOSTByObject(entity, result, model, actionName, 
					baseFolder + "/createOrEdit", saveSuccessMsg, saveErrorMsg, false);
	}
			
	private String actionPOSTByObject(Operation entity, BindingResult result, ModelMap model, 
			String actionName, String returnPage, String successMsg, String errorMsg, Boolean isDelete) throws Exception {	
		try {
			model.addAttribute("action", actionName);
			service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
			if(isDelete)
				service.delete(entity);
			else
				service.save(entity);
			refreshObject(entity);
			
			model.addAttribute("successMsg", successMsg);
		} catch (Exception e) {			
			model.addAttribute("errorMsg", errorMsg + "\nErro: "+e.getLocalizedMessage());
			logger.error((String) model.get("errorMsg"));
		}
		
		transactionTypeService.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		model.addAttribute("transactionList", transactionTypeService.getAll());
		model.addAttribute("item", entity);
		return returnPage;		
	}
	
	
}
