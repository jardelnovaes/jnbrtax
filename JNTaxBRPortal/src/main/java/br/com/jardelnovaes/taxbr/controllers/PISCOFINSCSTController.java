package br.com.jardelnovaes.taxbr.controllers;

import java.util.Locale;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jardelnovaes.taxbr.controllers.utils.AppControllerUtils;
import br.com.jardelnovaes.taxbr.controllers.utils.GenericViewsParameters;
import br.com.jardelnovaes.taxbr.controllers.utils.ViewPageActionsEnum;
import br.com.jardelnovaes.taxbr.models.PISCOFINSCST;
import br.com.jardelnovaes.taxbr.services.GenericCSTService;

//public class PISCOFINSCSTController
@Controller
public class PISCOFINSCSTController {
	private static final String serviceBeanName = "pisCofinsCSTService";
	private static final String baseURL = "cstPisCofins";
	private static final String baseFolder = "genericViews";
	private static final String deleteSuccessMsg = "CST de PIS/COFINS excluído com sucesso!";
	private static final String deleteErrorMsg = "Não foi possível excluir o CST!";
	private static final String saveSuccessMsg = "CST de PIS/COFINS salvo com sucesso!" ;
	private static final String saveErrorMsg = "Não foi possível salvar o CST!";
	private static final String locateErrorMsg = "CST não localizado (ID: {})" ;
	
		
	private static final Logger logger = LoggerFactory.getLogger(PISCOFINSCSTController.class);
	
	private GenericCSTService<PISCOFINSCST> service;
	
	
	@Autowired(required=true)
    @Qualifier(value=serviceBeanName)	
    public void setService(GenericCSTService<PISCOFINSCST> service){
		logger.info("Service bean was Injected => " + service.toString());
		
        this.service = service;
    }
	
	@RequestMapping(value={"/" + baseURL}, method = RequestMethod.GET)
	public String index(Locale locale, ServletRequest request, Model model) throws Exception {
		
		loadViewParameters(model, ViewPageActionsEnum.LIST, false);		
		model.addAttribute("items", service.getAll());
		
		return baseFolder + "/index";
	}
	
	@RequestMapping(value = "/" + baseURL + "/create", method = RequestMethod.GET)
	public String add(Model model) throws Exception {
		loadViewParameters(model, ViewPageActionsEnum.CREATE, true);
		model.addAttribute("item", service.getNew());
		
		return baseFolder + "/createEditOrDelete";
	}
	
	@RequestMapping(value = "/" + baseURL + "/create", method = RequestMethod.POST)	
	public String add(PISCOFINSCST entity, BindingResult result, Model model) {
		return actionPOSTByObject(entity, result, model, ViewPageActionsEnum.CREATE);	
	}
	
	@RequestMapping(value="/" + baseURL + "/edit/{id}", method = RequestMethod.GET)
	public String edit(Model model,	        
	        @PathVariable(value = "id") String id) throws Exception {	 
		//TODO Revisar ../
		//return actionGETById(model, "../edit", id);
		model.addAttribute("actionPrefix", "../");
		return actionGETById(model, ViewPageActionsEnum.EDIT, id);
	}
		
	@RequestMapping(value = "/" + baseURL + "/edit", method = RequestMethod.POST)	
	public String edit(PISCOFINSCST entity, BindingResult result, Model model) {		
		return actionPOSTByObject(entity, result, model, ViewPageActionsEnum.EDIT);		
	}
	
	@RequestMapping(value="/" + baseURL + "/delete/{id}", method = RequestMethod.GET)
	public String delete(Model model,	        
	        @PathVariable(value = "id") String id) throws Exception {	    
		//TODO Revisar ../
		//return actionGETById(model, "../delete", baseFolder + "/delete", id);
		model.addAttribute("actionPrefix", "../");
		
		//return actionGETById(model, ViewPageActionsEnum.DELETE, baseFolder + "/delete", id);
		return actionGETById(model, ViewPageActionsEnum.DELETE, baseFolder + "/createEditOrDelete", id);
	}
		
	@RequestMapping(value = "/" + baseURL + "/delete", method = RequestMethod.POST)	
	public String delete(PISCOFINSCST entity, BindingResult result, Model model) {
		
		return actionPOSTByObject(entity, result, model, ViewPageActionsEnum.DELETE , baseFolder + "/createEditOrDelete", //"/delete", 
					deleteSuccessMsg, deleteErrorMsg, true);				
	}
	
	private void loadViewParameters(Model model, ViewPageActionsEnum pageAction, boolean idIsEditable) throws Exception{
		GenericViewsParameters viewParams = new GenericViewsParameters(
				"Cadastro de CST de PIS/COFINS", "PISCOFINSCST", pageAction, baseURL, "CST"); 
		
		viewParams.setEntityClass(PISCOFINSCST.class);
		viewParams.setIdIsEditable(idIsEditable);
		if(pageAction == ViewPageActionsEnum.DELETE){
			viewParams.setIdIsEditable(false);
			viewParams.setDelete(true);
		}
		viewParams.inicialize();
				
		model.addAttribute("viewParams", viewParams);
	}
	
	private String actionGETById(Model model, ViewPageActionsEnum pageAction, String id) throws Exception{
		return actionGETById(model, pageAction, baseFolder + "/createEditOrDelete", id);
	}
	
	private String actionGETById(Model model, ViewPageActionsEnum pageAction, 
			String returnPage, String id) throws Exception{
		String errMsg;
		loadViewParameters(model, pageAction, false);
		
		PISCOFINSCST entity = service.getByCST(id);
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
	
	private String actionPOSTByObject(PISCOFINSCST entity, BindingResult result, Model model, ViewPageActionsEnum pageAction) {
		return actionPOSTByObject(entity, result, model, pageAction, 
					baseFolder + "/createEditOrDelete", saveSuccessMsg, saveErrorMsg, false);
	}
			
	private String actionPOSTByObject(PISCOFINSCST entity, BindingResult result, Model model, 
			ViewPageActionsEnum pageAction, String returnPage, String successMsg, String errorMsg, Boolean isDelete)  {	
		try {
			loadViewParameters(model, pageAction, false);
			
			//service.setCurrentAppUser((new AppUserController()).getCurrentUser());
			service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
			
			if(isDelete)
				service.delete(entity);
			else
				service.save(entity);
			model.addAttribute("successMsg", successMsg);
		} catch (Exception e) {
			model.addAttribute("errorMsg", errorMsg + "\nErro: "+e.getLocalizedMessage());
			logger.error((String) model.asMap().get("errorMsg"));
		}
		
		model.addAttribute("item", entity);
		return returnPage;		
	}
	
	
}

