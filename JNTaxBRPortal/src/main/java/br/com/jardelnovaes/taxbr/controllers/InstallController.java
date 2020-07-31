package br.com.jardelnovaes.taxbr.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jardelnovaes.taxbr.models.AddressState;
import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.models.Operation;
import br.com.jardelnovaes.taxbr.models.PISCOFINSCST;
import br.com.jardelnovaes.taxbr.models.PersonType;
import br.com.jardelnovaes.taxbr.models.TaxRule;
import br.com.jardelnovaes.taxbr.models.TransactionType;
import br.com.jardelnovaes.taxbr.services.AddressStateService;
import br.com.jardelnovaes.taxbr.services.AppUserService;
import br.com.jardelnovaes.taxbr.services.GenericCSTService;
import br.com.jardelnovaes.taxbr.services.GenericService;
import br.com.jardelnovaes.taxbr.services.OperationService;
import br.com.jardelnovaes.taxbr.services.TaxRuleService;

@Controller
public class InstallController {
	private static final Logger logger = LoggerFactory.getLogger(InstallController.class);
	
	private EntityManagerFactory factory;
    private EntityManager entityManager;
    
	private GenericService<Company> companySrv;
	private AppUserService userSrv;
	private GenericService<TransactionType> transTypeSrv;
	private AddressStateService addressSrv;
	private GenericCSTService<PISCOFINSCST> pisSrv;
	private GenericService<PersonType> personTypeSrv;
	private OperationService operationSrv;
	private TaxRuleService taxRuleSvr;
	
	private List<Company> companies;
	private List<TransactionType> transTypes;
	private AppUser user;
		
	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory factory) {
		this.factory = factory;		
	}
		
	@Autowired(required=true)
    @Qualifier(value="companyService")
    public void setCompanyService(GenericService<Company> service){
		logger.info("Company servive bean was Injected");
        this.companySrv = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="appUserService")
    public void setUserService(AppUserService service){
		logger.info("appUserService bean was Injected");
        this.userSrv = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="transactionTypeService")
    public void setTransactionTypeService(GenericService<TransactionType> service){
		logger.info("TransactionType bean was Injected");
        this.transTypeSrv = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="addressStateService")
    public void setAddressStateService(AddressStateService service){
		logger.info("Address State bean was Injected");
        this.addressSrv = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="pisCofinsCSTService")
    public void setPISCOFINSService(GenericCSTService<PISCOFINSCST> service){
		logger.info("PIS/COFINS CST bean was Injected");
        this.pisSrv = service;
    }
		
	@Autowired(required=true)
    @Qualifier(value="personTypeService")
    public void setPersonTypeService(GenericService<PersonType> service){
		logger.info("Person Type bean was Injected");
        this.personTypeSrv = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="operationService")
    public void setOperationService(OperationService service){
		logger.info("Operation bean was Injected");
        this.operationSrv = service;
    }
	
	@Autowired(required=true)
    @Qualifier(value="taxRuleService")
    public void setTaxRuleService(TaxRuleService service){
		logger.info("TaxRule bean was Injected");
        this.taxRuleSvr = service;
    }
	
	
	@RequestMapping(value = "/demo/resetPKs", method = RequestMethod.GET)
	public String demoResetPKs(Locale locale, Model model) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String msg = "This is the demo primary keys reset<br/><b><font color='RED'>Please, restart the server afeter it runs!</font></b><br/>";	
		msg += String.format("Start reset primary keys: %s", sdfDate.format(new Date()));
		
		try {
			getEntityManager().getTransaction().begin();
			msg = resetPK("hibernate_sequence", msg);
			msg = resetPK("seq_pk_appentitylog", msg);
			msg = resetPK("seq_pk_applogentityid", msg);
			msg = resetPK("seq_pk_appuserrole", msg);
			msg = resetPK("seq_pk_appusers", msg);
			msg = resetPK("seq_pk_company", msg);
			msg = resetPK("seq_pk_icmstype", msg);
			msg = resetPK("seq_pk_operation", msg);
			msg = resetPK("seq_pk_persontype", msg);
			msg = resetPK("seq_pk_taxrule", msg);
			msg = resetPK("seq_pk_transactiontype", msg);
			
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			msg = String.format("%s <br/> <font color='RED'><b>[ERROR] %s </b></font>", msg, e.getMessage());
			getEntityManager().getTransaction().rollback();
		}
		
		msg += String.format("<br/>Finish clear data: %s", sdfDate.format(new Date()));
		model.addAttribute("message", msg);
		logger.debug(String.format("InstallData sent this message to user: \n%s", msg.replace("<br/>", "\n")));
		
		return "general/installInfo";
	}
	
	
	@RequestMapping(value = "/demo/clearData/all", method = RequestMethod.GET)
	public String demoClearAllData(Locale locale, Model model) {
		return demoClearData(locale, model, true);
	}
	
	@RequestMapping(value = "/demo/clearData", method = RequestMethod.GET)
	public String demoClearData(Locale locale, Model model, boolean force) {		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String msg = "This is the demo data cleaner<i>  (use /all to force cleaning User and Company data)</i><br/>";	
		msg += String.format("Start clear data: %s", sdfDate.format(new Date()));
		
		try {			
			getEntityManager().getTransaction().begin();
			msg = clearEntity("TaxRule", msg);
			msg = clearEntity("Operation", msg);
			msg = clearEntity("PersonType", msg);
			msg = clearEntity("TransactionType", msg);
			msg = clearEntity("ICMSCST", msg);
			msg = clearEntity("ICMSType", msg);
			msg = clearEntity("PISCOFINSCST", msg);
			msg = clearEntity("AddressState", msg);
			msg = clearEntity("AppEntityLog", msg);	
						
			if(force){
				msg = clearEntity("AppUserRole", msg);
				msg = clearEntity("AppUser", msg);
				msg = clearEntity("Company", msg);
			}
			
			getEntityManager().getTransaction().commit();//change to commit;
		} catch (Exception e) {
			msg = String.format("%s <br/> <font color='RED'><b>[ERROR] %s </b></font>", msg, e.getMessage());
			getEntityManager().getTransaction().rollback();
		}
		
		msg += String.format("<br/>Finish clear data: %s", sdfDate.format(new Date()));
		model.addAttribute("message", msg);
		logger.debug(String.format("InstallData sent this message to user: \n%s", msg.replace("<br/>", "\n")));
		
		return "general/installInfo";
	}
	
	@RequestMapping(value = "/demo/installData", method = RequestMethod.GET)
	public String demoInstallData(Locale locale, Model model) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
	    String msg = "This is the demo data installation.<br/>";	    
	    
		msg += String.format("Start install: %s", sdfDate.format(new Date()));
		logger.debug("Welcome home! The client locale is {}.", locale);		
		logger.debug("Start installing demo data");
		
		try {
			
			Cache cache = this.factory.getCache();
			cache.evictAll();
			getEntityManager().clear();
						
			companies = null;
			Company coMaster = getOrCreateCompany("MASTER");	
			msg += "<br/>Companies installed";
			
			AppUser usr = userSrv.getByName("master@test.com");
			if(usr == null){
				usr = new AppUser();
				usr.setActive(true);
				usr.setCompany(coMaster);
				usr.setConfirmPassword("master");
				usr.setPassword("master");
				usr.setEmail("master@test.com");
				usr.setName("Master");
				
				userSrv.save(usr);
			
				//TODO Criar addROLE no User para adicionar as ROLES abaixo ?
				addRole(usr, "ROLE_ADMIN");
				addRole(usr, "ROLE_USER");
				addRole(usr, "ROLE_REST_USER");
				
			}
			this.user = usr;
			msg += "<br/>Users installed";
			Company co = getOrCreateCompany("EMP_LIXO");			
			addTransType(co, "TST:Vendas", true);
			addTransType(co, "TST:Compra", false);			
			msg += "<br/>Transaction type (DRAFT) installed";
			
			//co = getOrCreateCompany("MASTER");
			addTransType(coMaster, "Vendas", true);
			addTransType(coMaster, "Compra para Revenda", false);
			addTransType(coMaster, "Devolu��o de Venda", false);			
			msg += "<br/>Transaction type (MASTER) installed";
			
			
			addOrUpdateAddressState("BR", "Brasil", true);
			addOrUpdateAddressState("EX", "Exterior", true);			
			addOrUpdateAddressState("AC", "Acre", false);
			addOrUpdateAddressState("AL", "Alagoas", false);
			addOrUpdateAddressState("AP", "Amap�", false);
			addOrUpdateAddressState("AM", "Amazonas", false);			
			addOrUpdateAddressState("BA", "Bahia", false);
			addOrUpdateAddressState("CE", "Cear�", false);
			addOrUpdateAddressState("DF", "Distrito Federal", false);
			addOrUpdateAddressState("ES", "Esp�rito Santo", false);
			addOrUpdateAddressState("GO", "Goi�s", false);			
			addOrUpdateAddressState("MA", "Maranh�o", false);
			addOrUpdateAddressState("MT", "Mato Grosso", false);
			addOrUpdateAddressState("MS", "Mata Grosso do Sul", false);
			addOrUpdateAddressState("MG", "Goi�s", false);			
			addOrUpdateAddressState("PA", "Par�", false);
			addOrUpdateAddressState("PB", "Para�ba", false);
			addOrUpdateAddressState("PR", "Paran�", false);
			addOrUpdateAddressState("PE", "Pernambuco", false);
			addOrUpdateAddressState("PI", "Piau�", false);			
			addOrUpdateAddressState("RJ", "Rio de Janeiro", false);
			addOrUpdateAddressState("RN", "Rio Grande do Norte", false);
			addOrUpdateAddressState("RS", "Rio Grande do Sul", false);
			addOrUpdateAddressState("RO", "Rond�nia", false);
			addOrUpdateAddressState("RR", "Roraima", false);			
			addOrUpdateAddressState("SC", "Santa Catarina", false);
			addOrUpdateAddressState("SP", "S�o Paulo", false);
			addOrUpdateAddressState("SE", "Sergipe", false);
			addOrUpdateAddressState("TO", "Tocantins", false);
			msg += "<br/>Address State installed";
			
			addOrUpdateCSTPISCOFINS("01", "Opera��o Tribut�vel com Al�quota B�sica", true); 
			addOrUpdateCSTPISCOFINS("02", "Opera��o Tribut�vel com Al�quota Diferenciada", true);
			addOrUpdateCSTPISCOFINS("03", "Opera��o Tribut�vel com Al�quota por Unidade de Medida de Produto", true);  
			addOrUpdateCSTPISCOFINS("04", "Opera��o Tribut�vel Monof�sica - Revenda a Al�quota Zero", true); 
			addOrUpdateCSTPISCOFINS("05", "Opera��o Tribut�vel por Substitui��o Tribut�ria", true);  
			addOrUpdateCSTPISCOFINS("06", "Opera��o Tribut�vel a Al�quota Zero", true); 
			addOrUpdateCSTPISCOFINS("07", "Opera��o Isenta da Contribui��o", false);  
			addOrUpdateCSTPISCOFINS("08", "Opera��o sem Incid�ncia da Contribui��o", false);  
			addOrUpdateCSTPISCOFINS("09", "Opera��o com Suspens�o da Contribui��o", false);  
			addOrUpdateCSTPISCOFINS("49", "Outras Opera��es de Sa�da", false);  
			addOrUpdateCSTPISCOFINS("50", "Opera��o com Direito a Cr�dito - Vinculada Exclusivamente a Receita Tributada no Mercado Interno", true);  
			addOrUpdateCSTPISCOFINS("51", "Opera��o com Direito a Cr�dito � Vinculada Exclusivamente a Receita N�o Tributada no Mercado Interno", true);  
			addOrUpdateCSTPISCOFINS("52", "Opera��o com Direito a Cr�dito - Vinculada Exclusivamente a Receita de Exporta��o", true);  
			addOrUpdateCSTPISCOFINS("53", "Opera��o com Direito a Cr�dito - Vinculada a Receitas Tributadas e N�o-Tributadas no Mercado Interno", true);  
			addOrUpdateCSTPISCOFINS("54", "Opera��o com Direito a Cr�dito - Vinculada a Receitas Tributadas no Mercado Interno e de Exporta��o", true);  
			addOrUpdateCSTPISCOFINS("55", "Opera��o com Direito a Cr�dito - Vinculada a Receitas N�o-Tributadas no Mercado Interno e de Exporta��o", true);  
			addOrUpdateCSTPISCOFINS("56", "Opera��o com Direito a Cr�dito - Vinculada a Receitas Tributadas e N�o-Tributadas no Mercado Interno, e de Exporta��o", true);  
			addOrUpdateCSTPISCOFINS("60", "Cr�dito Presumido - Opera��o de Aquisi��o Vinculada Exclusivamente a Receita Tributada no Mercado Interno", true);  
			addOrUpdateCSTPISCOFINS("61", "Cr�dito Presumido - Opera��o de Aquisi��o Vinculada Exclusivamente a Receita N�o-Tributada no Mercado Interno", true);  
			addOrUpdateCSTPISCOFINS("62", "Cr�dito Presumido - Opera��o de Aquisi��o Vinculada Exclusivamente a Receita de Exporta��o", true);  
			addOrUpdateCSTPISCOFINS("63", "Cr�dito Presumido - Opera��o de Aquisi��o Vinculada a Receitas Tributadas e N�o-Tributadas no Mercado Interno", true);  
			addOrUpdateCSTPISCOFINS("64", "Cr�dito Presumido - Opera��o de Aquisi��o Vinculada a Receitas Tributadas no Mercado Interno e de Exporta��o", true);  
			addOrUpdateCSTPISCOFINS("65", "Cr�dito Presumido - Opera��o de Aquisi��o Vinculada a Receitas N�o-Tributadas no Mercado Interno e de Exporta��o", true);  
			addOrUpdateCSTPISCOFINS("66", "Cr�dito Presumido - Opera��o de Aquisi��o Vinculada a Receitas Tributadas e N�o-Tributadas no Mercado Interno, e de Exporta��o", true);  
			addOrUpdateCSTPISCOFINS("67", "Cr�dito Presumido - Outras Opera��es", false);  
			addOrUpdateCSTPISCOFINS("70", "Opera��o de Aquisi��o sem Direito a Cr�dito", false);  
			addOrUpdateCSTPISCOFINS("71", "Opera��o de Aquisi��o com Isen��o", false);  
			addOrUpdateCSTPISCOFINS("72", "Opera��o de Aquisi��o com Suspens�o", false);  
			addOrUpdateCSTPISCOFINS("73", "Opera��o de Aquisi��o a Al�quota Zero", true);  
			addOrUpdateCSTPISCOFINS("74", "Opera��o de Aquisi��o sem Incid�ncia da Contribui��o", false);  
			addOrUpdateCSTPISCOFINS("75", "Opera��o de Aquisi��o por Substitui��o Tribut�ria", false);  
			addOrUpdateCSTPISCOFINS("98", "Outras Opera��es de Entrada", false);  
			addOrUpdateCSTPISCOFINS("99", "Outras Opera��es", false);
			msg += "<br/>PIS/COFINS CST installed";
			
			addPersonType(co, "Distribuidor");
			addPersonType(co, "Optante pelo simples nacional");			 
			msg += "<br/>Person Type installed";
			
			transTypes = null;
			addOperation(co, "Venda � vista", "Vendas");
			addOperation(co, "Venda � prazo", "Vendas");
			addOperation(co, "Devolu��o de clientes", "Devolu��o de Venda");
			addOperation(co, "Entrada de fabricante", "Compra para Revenda");
			msg += "<br/>Operations installed";
			
			//TODO Resolver o BUG do Insert para poder rodar isso.
			//addTaxRules(co);					 
			//msg += "<br/>TaxRules installed";
			
		} catch (Exception e) {
			msg = String.format("%s <br/> [ERROR] %s", msg, e.getMessage());
		}
		
		
		
		msg += String.format("<br/>Finish install: %s", sdfDate.format(new Date()));
		model.addAttribute("message", msg);
		logger.debug(String.format("InstallData sent this message to user: \n%s", msg.replace("<br/>", "\n")));
		
		return "general/installInfo";
	}
			
	private  EntityManager getEntityManager() {
    	if(this.entityManager == null)
    		this.entityManager = this.factory.createEntityManager();
    	
		return this.entityManager;
	}
	
	private int execute(String query){
		//Query q = manager.createNativeQuery("BEGIN " + sqlScript + " END;");
		Query q = getEntityManager().createQuery(query);
		return q.executeUpdate();
	}
	
	private int executeNative(String query){
		return getEntityManager().createNativeQuery(query).executeUpdate();
	}
	
	private String resetPK(String name, String msg){
		executeNative(String.format("update %s set next_val = 1 ", name));
		return String.format("%s <br/>Reset PK: %s", msg, name);
	}
	
	private String clearEntity(String name, String msg){
		int rows = 0;
		rows = execute("DELETE FROM "+name);
		return String.format("%s <br/>Cleaning %s: %d rows", msg, name, rows);
	}
	
	private void addRole(AppUser user, String role) throws Exception{
		//TODO Change to jpql or object "syntax"
		long id = 0;
		try {
			getEntityManager().getTransaction().begin();
			String query = String.format("SELECT next_val FROM SEQ_PK_AppUserRole");		
			id = ((Number)getEntityManager().createNativeQuery(query).getSingleResult()).longValue();
			
			query = String.format("UPDATE SEQ_PK_AppUserRole SET next_val = %s ", id+1);		
			getEntityManager().createNativeQuery(query).executeUpdate();
			
			query = String.format(
						"INSERT INTO APP_AppUserRole (Id, name, UserId) VALUES (%d, '%s', %d)" , 
						id, role, user.getId()
					);		
			getEntityManager().createNativeQuery(query).executeUpdate();
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			getEntityManager().getTransaction().rollback();
			throw e;
		}
		
	}
	
	private Company getOrCreateCompany(String name) throws Exception {
		if((companies == null) || (companies.size()<=0)){
			companies = companySrv.getAll();
		}
		
		for (Company company : companies) {
			if(company.getName().equals(name))
				return company;
		}
		
		Company company = new Company();
		company.setName(name);
		
		companySrv.save(company);		
		return company;
		
	}
	
	private void addTransType(Company co, String name, Boolean isOut) throws Exception{
		TransactionType transType = new TransactionType();
		//transType.setCompany(co);
		transType.setName(name);
		transType.setOut(isOut);	
		transTypeSrv.setCurrentAppUser(this.user);
		transTypeSrv.save(transType);
	}
	
	private void addPersonType(Company co, String name) throws Exception{
		PersonType obj = new PersonType();
		//obj.setCompany(co);
		obj.setName(name);
		obj.setActive(true);	
		
		personTypeSrv.setCurrentAppUser(this.user);
		personTypeSrv.save(obj);
	}
	
	
	private void addOrUpdateAddressState(String id, String name, Boolean isSpecial) throws Exception{
		AddressState obj = addressSrv.getById(id);
		
		if(obj == null){
			obj = new AddressState();	
			obj.setId(id);
		}
		
		obj.setName(name);
		obj.setSpecial(isSpecial);
		
		addressSrv.setCurrentAppUser(this.user);
		addressSrv.save(obj);
	}
	
	private void addOrUpdateCSTPISCOFINS(String id, String name, Boolean isTaxed) throws Exception{
		PISCOFINSCST obj = pisSrv.getByCST(id);
		
		if(obj == null){
			obj = new PISCOFINSCST();
			obj.setCST(id);
		}
		
		obj.setDescription(name);
		obj.setTaxed(isTaxed);
		
		pisSrv.setCurrentAppUser(this.user);
		pisSrv.save(obj);
	}
	
	private void addOperation(Company co, String operationName, String transName) throws Exception{
		Operation oper = new Operation();
		
		if((transTypes == null) || (transTypes.size() <=0)){
			transTypes = transTypeSrv.getAll();
		}
		
		for (TransactionType trans : transTypes) {
			if(trans.getName().equals(transName)){
				//oper.setTransactionType(trans);
				oper.setTransactionType(new TransactionType());
				oper.getTransactionType().setId(trans.getId());
				break;
			}
		}
		
		if(oper.getTransactionType() != null){
			oper.setActive(true);
			//oper.setCompany(co);
			oper.setName(operationName);
			operationSrv.setCurrentAppUser(this.user);
			operationSrv.save(oper);
		}
	}
	
	private void addTaxRules(Company co) throws Exception{
		
		for (AddressState state : addressSrv.getAll()) {
			TaxRule rule = new TaxRule();
			
			rule.setFromState(state);
			rule.setToState(state);
			
			rule.setActive(true);
			rule.setCEST("");
			//rule.setCompany(co);
			rule.setExNCM(0);
			rule.setItemId(0);
			rule.setNCM("");
			taxRuleSvr.setCurrentAppUser(this.user);
			taxRuleSvr.save(rule);
			/*
			rule.setOperation(null);
			rule.setPersonType(null);
			rule.setTransactionType(null);
			*/
		}
	}
}
