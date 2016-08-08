package br.com.jardelnovaes.taxbr.controllers;

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

import br.com.jardelnovaes.taxbr.controllers.utils.AppControllerUtils;
import br.com.jardelnovaes.taxbr.models.TransactionType;
import br.com.jardelnovaes.taxbr.services.GenericService;


@Controller
public class TransactionTypeController {	
	private static final String serviceBeanName = "transactionTypeService";
	private static final String baseFolder = "transactionTypes";
	private static final String deleteSuccessMsg = "Regra excluída com sucesso!";
	private static final String deleteErrorMsg = "Não foi possível excluir a regra!";
	private static final String saveSuccessMsg = "Regra Salva com sucesso!" ;
	private static final String saveErrorMsg = "Não foi possível salvar a regra!";
	private static final String locateErrorMsg = "Regra não localizada (ID: {})" ;
				
	private static final Logger logger = LoggerFactory.getLogger(TransactionTypeController.class);
	
	private GenericService<TransactionType> service;
	
	@Autowired(required=true)
    @Qualifier(value=serviceBeanName)	
    public void setService(GenericService<TransactionType> service){
		logger.info("Service bean was Injected => " + service.toString());
		
        this.service = service;
    }
	
	@RequestMapping(value={"/" + baseFolder}, method = RequestMethod.GET)
	public String index(Locale locale, Model model) throws Exception {
		service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
		model.addAttribute("items", service.getAll());
		
		return baseFolder + "/index";
	}
	
	@RequestMapping(value = "/" + baseFolder + "/create", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("item", service.getNew());
		
		return baseFolder + "/createOrEdit";
	}
	
	@RequestMapping(value = "/" + baseFolder + "/create", method = RequestMethod.POST)	
	public String add(TransactionType entity, BindingResult result, ModelMap model) {
		return actionPOSTByObject(entity, result, model, "create");	
	}
	
	@RequestMapping(value="/" + baseFolder + "/edit/{id}", method = RequestMethod.GET)
	public String edit(Model model,	        
	        @PathVariable(value = "id") long id) {	 
	    
		return actionGETById(model, "../edit", id);		
	}
		
	@RequestMapping(value = "/" + baseFolder + "/edit", method = RequestMethod.POST)	
	public String edit(TransactionType entity, BindingResult result, ModelMap model) {
		
		return actionPOSTByObject(entity, result, model, "edit");		
	}
	
	@RequestMapping(value="/" + baseFolder + "/delete/{id}", method = RequestMethod.GET)
	public String delete(Model model,	        
	        @PathVariable(value = "id") long id) {	    
		return actionGETById(model, "../delete", baseFolder + "/delete", id);
	}
		
	@RequestMapping(value = "/" + baseFolder + "/delete", method = RequestMethod.POST)	
	public String delete(TransactionType entity, BindingResult result, ModelMap model) {
		
		return actionPOSTByObject(entity, result, model, "delete", baseFolder + "/delete", 
					deleteSuccessMsg, deleteErrorMsg, true);				
	}
	
	private String actionGETById(Model model, String actionName, long id){
		return actionGETById(model, actionName, baseFolder + "/createOrEdit", id);
	}
	
	private String actionGETById(Model model, String actionName, String returnPage, long id){
		String errMsg;
		model.addAttribute("action", actionName);
		
		TransactionType entity = service.getById(id);
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
	
	private String actionPOSTByObject(TransactionType entity, BindingResult result, ModelMap model, String actionName) {
		return actionPOSTByObject(entity, result, model, actionName, 
					baseFolder + "/createOrEdit", saveSuccessMsg, saveErrorMsg, false);
	}
			
	private String actionPOSTByObject(TransactionType entity, BindingResult result, ModelMap model, 
			String actionName, String returnPage, String successMsg, String errorMsg, Boolean isDelete) {	
		try {
			model.addAttribute("action", actionName);
			service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
			if(isDelete)
				service.delete(entity);
			else
				service.save(entity);
			model.addAttribute("successMsg", successMsg);
		} catch (Exception e) {			
			model.addAttribute("errorMsg", errorMsg + "\nErro: "+e.getLocalizedMessage());
			logger.error((String) model.get("errorMsg"));
		}
		
		model.addAttribute("item", entity);
		return returnPage;		
	}
}
