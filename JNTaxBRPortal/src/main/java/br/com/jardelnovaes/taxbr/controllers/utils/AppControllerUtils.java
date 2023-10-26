package br.com.jardelnovaes.taxbr.controllers.utils;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.models.ICMSCST;
import br.com.jardelnovaes.taxbr.models.PISCOFINSCST;
import br.com.jardelnovaes.taxbr.services.AppUserService;
import br.com.jardelnovaes.taxbr.services.GenericCSTService;

@Component
public class AppControllerUtils {
	//private static final Logger logger = LoggerFactory.getLogger(AppControllerUtils.class);
	
	private static AppUserService appUserService;
	private static GenericCSTService<PISCOFINSCST> pisCofinsCSTService;
	private static GenericCSTService<ICMSCST> icmsCSTService;
	
	@Autowired(required=true)
    @Qualifier(value="appUserService")
    public void setAppUserService(AppUserService service){		
        appUserService = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="pisCofinsCSTService")
    public void setPisCofinsCSTService(GenericCSTService<PISCOFINSCST> service){		
		pisCofinsCSTService = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="icmsCSTService")
    public void setIcmsCSTService(GenericCSTService<ICMSCST> service){		
		icmsCSTService = service;
    }
	
	
	public static AppUser getCurrentUser() throws Exception{
		AppUser appUser = null;
		
		String name = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			name = ((User)principal).getUsername();
		} else {
			throw new AccessDeniedException("Access denied"); 
		}
	    //Master  
		appUser = appUserService.getByName(name);
		if(appUser == null){
			appUser =  new AppUser();
			appUser.setName("Master");
			appUser.setEmail("master@test.com");
			Company company = new Company();
			company.setId(1);
			appUser.setCompany(company);
			appUserService.save(appUser);							
		}
		return appUser;
	}
	
	public static List<PISCOFINSCST> getPisCofinsCSTList(){
		return pisCofinsCSTService.getAll();
	}
	
	public static List<ICMSCST> getIcmsCSTList(){
		return icmsCSTService.getAll();
	}
	
	public static String capitalizeFirstLetter(String val){
		return Character.toString(val.charAt(0)).toUpperCase()+val.substring(1);
	}
	
	public static ArrayList<GenericViewField> getEntityFields(Class<?> clazz) {
		
        if ((clazz == null)) 
            return null;

        ArrayList<GenericViewField>  ret = new ArrayList<GenericViewField>();
        
        /*
        Properties props = null;
        try {
        	Resource resource = new ClassPathResource("/app.models.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);	
		} catch (Exception e) {
			logger.warn("Resource not found! (app.models.properties)");
		}
		*/
        
        /*
        String entityName = clazz.getSimpleName();
        String sDispName = null;
        */
        
        for (Field f : clazz.getDeclaredFields()) {
            @SuppressWarnings("unused")
			Column col = null;
           
            //br.com.jardelnovaes.taxbr.annotations.DisplayName dispName = null;
            
            Annotation[] as = f.getAnnotations();
            for (Annotation a : as) {
                if (a.annotationType() == Column.class) {
                    col = (Column) a;  
                    /*
                    Annotation[] asD = f.getAnnotations();
                    for (Annotation aD : asD) {
                        if (aD.annotationType() == br.com.jardelnovaes.taxbr.annotations.DisplayName.class) {
                        	dispName = (br.com.jardelnovaes.taxbr.annotations.DisplayName) aD;
                        	
                        	sDispName = capitalizeFirstLetter(dispName.value());
                            continue;
                        }
                        
                        if(props != null){
	                        sDispName = props.getProperty(String.format("display.%s.%s", entityName, f.getName()));
	                        if(sDispName != null && !sDispName.isEmpty()){
	                        	sDispName = capitalizeFirstLetter(sDispName);
	                        	continue;
	                        }
                        }
                    }
                    if(sDispName == null || sDispName.isEmpty())
                    	sDispName = capitalizeFirstLetter(f.getName());
                    	
                    ret.add(new GenericViewField(f.getName(), sDispName, f.getType().getSimpleName().toLowerCase()));
                    */
                    ret.add(new GenericViewField(f.getName(), f.getType().getSimpleName().toLowerCase()));
                }              
            }           
        }       
        return ret;
    }
	
	
}
