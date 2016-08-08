package br.com.jardelnovaes.taxbr.persitence.impl.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.jardelnovaes.taxbr.models.AppEntityLog;
import br.com.jardelnovaes.taxbr.models.AppEntityLogTypeEnum;
import br.com.jardelnovaes.taxbr.models.AppLogEntityId;
import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;
import br.com.jardelnovaes.taxbr.persitence.impl.AppEntityLogDAOImpl;
import br.com.jardelnovaes.taxbr.persitence.impl.AppEntityLogIdDAOImpl;
import br.com.jardelnovaes.taxbr.persitence.impl.AppUserDAOJPAImpl;
import br.com.jardelnovaes.taxbr.persitence.impl.CompanyDAOJPAImpl;

public class AppEntityLogDAOImplTestCase {
	private static final String PERSISTENCE_UNIT_NAME = "JNBRTaxPersistenteTest";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static AppEntityLogDAOImpl dao;
	private AppUser appUser = null;
	private AppLogEntityId entityId = null;
	
	private static void error(Exception e){		
		//for (StackTraceElement stack : e.getStackTrace()) {
			//logger.info(stack.toString());
			//System.out.println(stack.toString());
		//}
		fail("Error: " + e.getMessage());
	}
	
	private void criptPassword(AppUser user) throws Exception{
		if((user.getPassword() != null ) && (user.getPassword().length() != 32)){ //only if doen't have a MD5 content.
			java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
			md5.update(user.getPassword().getBytes(), 0, user.getPassword().length());
			user.setPassword(new java.math.BigInteger(1, md5.digest()).toString(16));
		}
	}
	private AppUser getUser(){
		try {
			if(appUser == null){
				AppUserDAOJPAImpl userDao = new AppUserDAOJPAImpl();
				userDao.setEntityManagerFactory(emf);
				userDao.setEntityManager(em);
				appUser = userDao.getByName("master@test.com");
				if(appUser == null){
					appUser =  new AppUser();
					appUser.setName("Master");
					appUser.setEmail("master@test.com");
					
					Company company = userDao.getCompanyByName("MASTER");
					
					if(company == null){
						CompanyDAOJPAImpl coDao = new CompanyDAOJPAImpl();
						coDao.setEntityManagerFactory(emf);
						coDao.setEntityManager(em);
						company = new Company();
						company.setName("MASTER");
						coDao.save(company);
					}
					appUser.setCompany(company);
					
					appUser.setPassword("master");
					criptPassword(appUser);
					
					userDao.save(appUser);
										
				}
			}
			
			return appUser;
		} catch (Exception e) {
			error(e);
		}
		return appUser;
	}
	
	private AppLogEntityId getEntity(String entityName){
		try {
			if((entityId == null) || (!entityId.getEntityName().equals(entityName))){				
				AppEntityLogIdDAOImpl eDao = new AppEntityLogIdDAOImpl();
				eDao.setEntityManagerFactory(emf);
				eDao.setEntityManager(em);				
				eDao.createEntityCriteria();
				eDao.getDaoCriterea().equal("entityName", entityName);
				entityId = eDao.getFirst();
				
				if(entityId == null){
					entityId = new AppLogEntityId();
					entityId.setEntityName(entityName);					
					eDao.beginTransaction();
					eDao.save(entityId);
					eDao.commit();					
				}
			}
			
			return entityId;
		} catch (Exception e) {
			error(e);
		}
		return entityId;
	}

	/**
	 * @throws java.lang.Exception
	 */

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			dao = new AppEntityLogDAOImpl();
			emf = dao.getNewFactory(PERSISTENCE_UNIT_NAME);
			em = emf.createEntityManager();
			
			dao.setEntityManagerFactory(emf);
			dao.setEntityManager(em);
			
		} catch (Exception e) {
			error(e);
		}
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		try {
			if (dao != null)
				dao = null;
			
			if(em != null){
				em.close();
				em = null;
			}
			
			if(emf != null){
				emf.close();
				emf = null;
			}
			
		} catch (Exception e) {
			error(e);
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetAll() {
		try {
			testSave("Teste1", false);
			testSave("Teste2", false);
			
			List<AppEntityLog> items = dao.getAll();
			assertTrue(items.size() >= 2);
			
			testDelete();
			
		} catch (Exception e) {
			error(e);
		}
				
	}

	@Test
	public void testGetById() {
		try {
			testDelete();
			try {
				testSave(AppEntityLog.class.getName(), false);
			} catch (Exception e) {}
			
			AppEntityLog entity = null; 
			for(int i=1; i < 20; i++){
				entity = dao.getById(i);
				if(entity != null){
					assertTrue(entity.getEntity().getEntityName().equals(entity.getClass().getName()));					
					return;
				}
			}
			
			fail("AppEntityLog.EntityName='"+ AppEntityLog.class.getName() +"' not found!");
			
			
		} catch (Exception e) {
			error(e);
		}
	}

	@Test
	public void testSave() {
		testDelete();
		testSave(AppLogEntityId.class.getName(), true);
	}

	private AppEntityLog testSave(String entityName, boolean canDelete) {
		AppEntityLog entity = new AppEntityLog();
		entity.setId(-1);
		entity.setLogType(AppEntityLogTypeEnum.Insert);
		entity.setUser(getUser());
		entity.setEntity(getEntity(entityName));
		try{
			
			dao.beginTransaction();
			dao.save(entity);
		}
		catch (Exception e) {
			//OK deveria dar erro mesmo, pq a PK é automatica.
			//dao.delete(entity);
			dao.rollback();
			
			try {
				dao.beginTransaction();
				entity.setId(0); // limpa o ID pq vai pegar na sequence.
				dao.save(entity);
				dao.commit();
				assertTrue(entity.getId()>0);
				//logger.info(String.format("Successful! Id: {} - Name {}", entity.getId(), entity.getEntityName()));
				
				if(canDelete){
					dao.beginTransaction();
					dao.delete(entity);
					dao.commit();
				}
				System.out.println("Successful! Id: " + String.valueOf(entity.getId()) + " - UserName: " + entity.getUser().getName());
				
				return entity;
			} catch (Exception e2) {
				error(e2);
			}
		}
		fail();
		return null;
		
	}
	
	@Test
	public void testGetDaoCriterea() {
		testDelete();
		assertNotNull(testGetDaoCriterea2());
		testDelete();
	}
	public AppEntityLog testGetDaoCriterea2() {
		try {			
			
			testSave(AppEntityLog.class.getName(), false);
			
			dao.createEntityCriteria();
			dao.getDaoCriterea().joinWithEqual(dao.getDaoCriterea().join("entity"), "entityName", AppEntityLog.class.getName());
			
			return dao.getFirst();			
		} catch (Exception e) {
			error(e);
		}
		return null;
	}

	@Test
	public void testUpdate() {
		try {			
			AppEntityLog entity = testGetDaoCriterea2();
			if(entity != null){
				entity.getEntity().setEntityName("This name was changed");
				dao.beginTransaction();
				dao.update(entity);
				dao.commit();
				assertEquals("This name was changed", entity.getEntity().getEntityName());
			}
			
		} catch (Exception e) {
			error(e);
		}
		
	}

	@Test
	public void testDelete() {
		try {
			testSave(AppLogEntityId.class.getName(), false);
			List<AppEntityLog> items = dao.getAll();
			
			dao.beginTransaction();
			for (AppEntityLog item : items) {
				dao.delete(item);				
			}
			dao.commit();
			assertTrue(true);
			
		} catch (Exception e) {
			error(e);
		}
	}
}
