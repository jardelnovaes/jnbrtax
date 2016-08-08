package br.com.jardelnovaes.taxbr.controllers;

import java.util.ArrayList;
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

import br.com.jardelnovaes.taxbr.controllers.utils.AppControllerUtils;
import br.com.jardelnovaes.taxbr.controllers.utils.GenericViewsParameters;
import br.com.jardelnovaes.taxbr.controllers.utils.ViewPageActionsEnum;
import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.services.AppUserService;

@Controller
public class AppUserController {
	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
	
	private AppUserService service;
	
	@Autowired(required=true)
    @Qualifier(value="appUserService")
    public void setService(AppUserService service){
		logger.info("appUserService bean was Injected");
        this.service = service;
    }
	
	@RequestMapping(value={"/", "/appUsers"}, method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		logger.debug("Welcome home! The client locale is {}.", locale);		
		logger.debug("AppUser test object was created");
		
		model.addAttribute("users", service.getAll());
		
		return "appUsers/index";
	}
			
	@RequestMapping(value = "/appUsers/create", method = RequestMethod.GET)
	public String add(Model model) {
		//logger.debug("AppUser test object will be create");
		
		service.setCurrentAppUser(service.getByName("Master"));
		
		model.addAttribute("action", "create");
		model.addAttribute("user", service.getNew());
		
		return "appUsers/createOrEdit";
		// return "redirect:/hello";
	}
	
	@RequestMapping(value = "/appUsers/create", method = RequestMethod.POST)	
	public String add(AppUser user, BindingResult result, ModelMap model) {
		return actionPOSTByObject(user, result, model, "create");
		
		//@Valid User user, BindingResult result, ModelMap model
	    //model.addAttribute("greeting", "Hello Spring MVC");		
		//AppUser usr = new AppUser();		
		//user.setName("Salvo");		
		//model.addAttribute("user", user);		
	    // return "redirect:/hello";	    
	}
	
	@RequestMapping(value="/appUsers/edit/{id}", method = RequestMethod.GET)
	public String edit(Model model,	        
	        @PathVariable(value = "id") int id) {	 
	    
		return actionGETById(model, "../edit", id);		
	}
		
	@RequestMapping(value = "/appUsers/edit", method = RequestMethod.POST)	
	public String edit(AppUser user, BindingResult result, ModelMap model) {
		
		return actionPOSTByObject(user, result, model, "edit");		
	}
	
	@RequestMapping(value="/appUsers/delete/{id}", method = RequestMethod.GET)
	public String delete(Model model,	        
	        @PathVariable(value = "id") int id) {	    
		return actionGETById(model, "../delete", "appUsers/delete", id);
	}
		
	@RequestMapping(value = "/appUsers/delete", method = RequestMethod.POST)	
	public String delete(AppUser user, BindingResult result, ModelMap model) {
		
		return actionPOSTByObject(user, result, model, "delete", "appUsers/delete", 
				"Usuário excluído com sucesso!", 
				"Não foi possível excluir o usuário", 
				true);				
	}
	
	@RequestMapping(value = "/appUsers/test", method = RequestMethod.GET) 
	public String appTest(Model model) { 
		//AppUserService service = new AppUserServiceImpl();
		List<AppUser> users = service.getAll();
		
		if(users == null)
			users = new ArrayList<AppUser>();
		if(users.size()==0)
			users.add(new AppUser());
		
		 model.addAttribute("user", users.get(0));
		 return "appUsers/new";       
		    
		//return new ModelAndView("new", "command", users.get(0)); 
	}
	
	private String actionGETById(Model model, String actionName, int id){
		return actionGETById(model, actionName, "appUsers/createOrEdit", id);
	}
	
	private String actionGETById(Model model, String actionName, String returnPage, int id){
		String errMsg;
		model.addAttribute("action", actionName);
		
		AppUser user = service.getById(id);
		if (user == null) {
			errMsg = "Usuário não localizado (ID: " + String.valueOf(id) + ")" ;
			logger.info(errMsg);			
			model.addAttribute("errorMsg", errMsg);
			model.addAttribute("user", service.getNew());
		}
		else{
			model.addAttribute("user", user);
		}
	    
	    return returnPage;
	}
	
	private String actionPOSTByObject(AppUser user, BindingResult result, ModelMap model, String actionName) {
		return actionPOSTByObject(user, result, model, actionName, 
				"appUsers/createOrEdit", "Usuário Salvo com sucesso!", 
				"Não foi possível salvar o usuário", 
				false);
	}
			
	private String actionPOSTByObject(AppUser user, BindingResult result, ModelMap model, 
			String actionName, String returnPage, String successMsg, String errorMsg, Boolean isDelete) {	
		try {
			model.addAttribute("action", actionName);
			
			if((user.getConfirmPassword().length() > 0) && (!user.getPassword().equals(user.getConfirmPassword()))){
				throw new Exception("The password and it confirmation aren't equals, please retype it again!");
			}
			//
//			service.save(user);
			
			service.setCurrentAppUser(AppControllerUtils.getCurrentUser());
			
			//TODO Revisar como será a regra par definir qual é a empresa.
			user.setCompany(service.getCompanyByName("MASTER"));		
			
			if(isDelete)
				service.delete(user);
			else
				service.save(user);
			
			user.setConfirmPassword("");
			model.addAttribute("successMsg", successMsg);
		} catch (Exception e) {
			model.addAttribute("errorMsg", errorMsg + "\nErro: "+e.getLocalizedMessage());
			logger.error((String) model.get("errorMsg"));
		}
		
		model.addAttribute("user", user);
		return returnPage;		
	}
	

	@RequestMapping(value="/test", method = RequestMethod.GET)
	public String test(Model model) throws Exception{
		
		GenericViewsParameters viewParams = new GenericViewsParameters("Usuários", "AppUser", ViewPageActionsEnum.CREATE, "appUsers", "id"); 
		
		viewParams.setEntityClass(AppUser.class);
		viewParams.inicialize();
				
		model.addAttribute("viewParams", viewParams);				
		//model.addAttribute("items", service.getAll());
		model.addAttribute("item", service.getNew());
		
		return "genericViews/createOrEdit";
	}
	
}
