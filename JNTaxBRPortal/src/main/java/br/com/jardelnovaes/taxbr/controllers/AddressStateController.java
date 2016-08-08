package br.com.jardelnovaes.taxbr.controllers;

import java.util.Locale;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jardelnovaes.taxbr.controllers.utils.AppControllerUtils;
import br.com.jardelnovaes.taxbr.models.AddressState;
import br.com.jardelnovaes.taxbr.persitence.PagedData;
import br.com.jardelnovaes.taxbr.services.AddressStateService;

@Controller
public class AddressStateController {
	private static final String serviceBeanName = "addressStateService";
	private static final String baseFolder = "addressStates";
	private static final String deleteSuccessMsg = "Estado exclu�da com sucesso!";
	private static final String deleteErrorMsg = "N�o foi poss�vel excluir o estado!";
	private static final String saveSuccessMsg = "Estado salvo com sucesso!" ;
	private static final String saveErrorMsg = "N�o foi poss�vel salvar o estado!";
	private static final String locateErrorMsg = "Estado n�o localizado (ID: {})" ;
	private static final int RESULT_PAGE_SIZE = 4;
		
	private static final Logger logger = LoggerFactory.getLogger(AddressStateController.class);
	
	private AddressStateService service;
	
	
	@Autowired(required=true)
    @Qualifier(value=serviceBeanName)	
    public void setService(AddressStateService service){
		logger.info("Service bean was Injected => " + service.toString());
		
        this.service = service;
    }
	
	@RequestMapping(value={"/" + baseFolder}, method = RequestMethod.GET)
	public String index(Locale locale, ServletRequest request, Model model) {
		
		int pageNum = ServletRequestUtils.getIntParameter(request, "pgNum", 0);
		String asc = ServletRequestUtils.getStringParameter(request, "asc", "");
		String desc = ServletRequestUtils.getStringParameter(request, "desc", "");
		String order = "asc";
		/*
		PagedListHolder pg = new PagedListHolder(service.getAll());
		
		pg.setPage(pageNum); 
		pg.setPageSize(4);
		
		//model.addAttribute("pagedListHolder", pg);
		//model.addAttribute("items", service.getAll());
		
		model.addAttribute("pagedItems", pg);
		*/
		
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
		service.setPagedData(pg);
		
		model.addAttribute("items", service.getAll());
		model.addAttribute("pagedData", pg);
		model.addAttribute("orderDirection", order);
		
		return baseFolder + "/index";
	}
	
	@RequestMapping(value = "/" + baseFolder + "/create", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("item", service.getNew());
		
		return baseFolder + "/createOrEdit";
	}
	
	@RequestMapping(value = "/" + baseFolder + "/create", method = RequestMethod.POST)	
	public String add(AddressState entity, BindingResult result, ModelMap model) {
		return actionPOSTByObject(entity, result, model, "create");	
	}
	
	@RequestMapping(value="/" + baseFolder + "/edit/{id}", method = RequestMethod.GET)
	public String edit(Model model,	        
	        @PathVariable(value = "id") String id) {	 
	    
		return actionGETById(model, "../edit", id);		
	}
		
	@RequestMapping(value = "/" + baseFolder + "/edit", method = RequestMethod.POST)	
	public String edit(AddressState entity, BindingResult result, ModelMap model) {
		
		return actionPOSTByObject(entity, result, model, "edit");		
	}
	
	@RequestMapping(value="/" + baseFolder + "/delete/{id}", method = RequestMethod.GET)
	public String delete(Model model,	        
	        @PathVariable(value = "id") String id) {	    
		return actionGETById(model, "../delete", baseFolder + "/delete", id);
	}
		
	@RequestMapping(value = "/" + baseFolder + "/delete", method = RequestMethod.POST)	
	public String delete(AddressState entity, BindingResult result, ModelMap model) {
		
		return actionPOSTByObject(entity, result, model, "delete", baseFolder + "/delete", 
					deleteSuccessMsg, deleteErrorMsg, true);				
	}
	
	private String actionGETById(Model model, String actionName, String id){
		return actionGETById(model, actionName, baseFolder + "/createOrEdit", id);
	}
	
	private String actionGETById(Model model, String actionName, String returnPage, String id){
		String errMsg;
		model.addAttribute("action", actionName);
		
		AddressState entity = service.getById(id);
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
	
	private String actionPOSTByObject(AddressState entity, BindingResult result, ModelMap model, String actionName) {
		return actionPOSTByObject(entity, result, model, actionName, 
					baseFolder + "/createOrEdit", saveSuccessMsg, saveErrorMsg, false);
	}
			
	private String actionPOSTByObject(AddressState entity, BindingResult result, ModelMap model, 
			String actionName, String returnPage, String successMsg, String errorMsg, Boolean isDelete) {	
		try {
			model.addAttribute("action", actionName);
			
			//service.setCurrentAppUser((new AppUserController()).getCurrentUser());
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
