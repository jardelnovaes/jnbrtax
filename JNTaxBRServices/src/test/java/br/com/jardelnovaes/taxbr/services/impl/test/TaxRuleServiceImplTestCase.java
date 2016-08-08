package br.com.jardelnovaes.taxbr.services.impl.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.jardelnovaes.taxbr.models.AppLogEntityId;
import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.TaxRule;
import br.com.jardelnovaes.taxbr.persitence.impl.AppEntityLogDAOImpl;
import br.com.jardelnovaes.taxbr.persitence.impl.AppUserDAOJPAImpl;
import br.com.jardelnovaes.taxbr.persitence.impl.TaxRuleDAOJPAImpl;
import br.com.jardelnovaes.taxbr.services.impl.TaxRuleServiceImpl;

public class TaxRuleServiceImplTestCase {
	private static final String PERSISTENCE_UNIT_NAME = "JNBRTaxPersistenteTest";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static TaxRuleDAOJPAImpl dao;
	private static TaxRuleServiceImpl service;
	private AppUser appUser = null;
	
	private static void error(Exception e){		
		//for (StackTraceElement stack : e.getStackTrace()) {
			//logger.info(stack.toString());
			//System.out.println(stack.toString());
		//}
		fail("Error: " + e.getMessage());
	}
	
	private AppUser getUser(){
		try {
			if(appUser == null){
				AppUserDAOJPAImpl userDao = new AppUserDAOJPAImpl();
				userDao.setEntityManagerFactory(emf);
				userDao.setEntityManager(em);
				appUser = userDao.getByName("Master");
				if(appUser == null){
					appUser =  new AppUser();
					appUser.setName("Master");
					appUser.setEmail("master@test.com");
					
					userDao.save(appUser);
										
				}
			}
			
			return appUser;
		} catch (Exception e) {
			error(e);
		}
		return appUser;
	}
/*	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			dao = new TaxRuleDAOJPAImpl();
			emf = dao.getNewFactory(PERSISTENCE_UNIT_NAME);
			em = emf.createEntityManager();
			
			dao.setEntityManagerFactory(emf);
			dao.setEntityManager(em);
			
		} catch (Exception e) {
			error(e);
		}
	}

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
	public void testSetDao() {
		try {
			service.setDao(dao);
			assertNotNull(dao);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNew() {
		TaxRule newObj = new TaxRule();
		assertEquals(newObj, service.getNew());
	}

	@Test
	public void testGetById() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPagedData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPagedData() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCurrentAppUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentAppUser() {
		fail("Not yet implemented");
	}
*/
}
