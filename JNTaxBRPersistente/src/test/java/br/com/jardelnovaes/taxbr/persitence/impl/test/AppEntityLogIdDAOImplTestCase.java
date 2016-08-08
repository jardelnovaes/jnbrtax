/**
 * 
 */
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

import br.com.jardelnovaes.taxbr.models.AppLogEntityId;
import br.com.jardelnovaes.taxbr.persitence.impl.AppEntityLogIdDAOImpl;

/**
 * @author jardel.novaes
 *
 */
public class AppEntityLogIdDAOImplTestCase {
	//private static Logger logger = LoggerFactory.getLogger(AppEntityLogIdDAOImpl.class);
	
	private static final String PERSISTENCE_UNIT_NAME = "JNBRTaxPersistenteTest";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static AppEntityLogIdDAOImpl dao;
	
	private static void error(Exception e){		
		//for (StackTraceElement stack : e.getStackTrace()) {
			//logger.info(stack.toString());
			//System.out.println(stack.toString());
		//}
		fail("Error: " + e.getMessage());
	}
	/**
	 * @throws java.lang.Exception
	 */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			dao = new AppEntityLogIdDAOImpl();
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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link br.com.jardelnovaes.taxbr.persitence.impl.AppEntityLogIdDAOImpl#getAll()}.
	 */
	
	@Test
	public void testGetAll() {
		try {
			testSave("Teste1", false);
			testSave("Teste2", false);
			
			List<AppLogEntityId> items = dao.getAll();
			assertTrue(items.size() >= 2);
			
			testDelete();
			
		} catch (Exception e) {
			error(e);
		}
				
	}
	
	/**
	 * Test method for {@link br.com.jardelnovaes.taxbr.persitence.impl.AppEntityLogIdDAOImpl#getById(long)}.
	 */
	
	@Test
	public void testGetById() {
		
		try {
			testDelete();
			try {
				testSave(AppLogEntityId.class.getName(), false);
			} catch (Exception e) {}
			
			AppLogEntityId entity = null; 
			for(int i=1; i < 20; i++){
				entity = dao.getById(i);
				if(entity != null){
					assertTrue(entity.getEntityName().equals(entity.getClass().getName()));					
					return;
				}
			}
			
			fail("AppLogEntityId.EntityName='"+ AppLogEntityId.class.getName() +"' not found!");
			
			
		} catch (Exception e) {
			error(e);
		}
	}
	
	/**
	 * Test method for {@link br.com.jardelnovaes.taxbr.persitence.impl.AppEntityLogIdDAOImpl#save(br.com.jardelnovaes.taxbr.models.AppLogEntityId)}.
	 */
	
	@Test
	public void testSave() {
		testDelete();
		testSave(AppLogEntityId.class.getName(), true);
	}
	
	private void testSave(String entityName, boolean canDelete) {
		AppLogEntityId entity = new AppLogEntityId();
		entity.setId(-1);
		entity.setEntityName(entityName);
		
		//TODO testar duplicidade de EntityName
		
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
				System.out.println("Successful! Id: " + String.valueOf(entity.getId()) + " - Name: " + entity.getEntityName());
			} catch (Exception e2) {
				error(e2);
			}
		}
		
	}
	
	/**
	 * Test method for {@link br.com.jardelnovaes.taxbr.persitence.AbstractDAO#getDaoCriterea()}.
	 */
	
	@Test
	public void testGetDaoCriterea() {
		testDelete();
		assertNotNull(testGetDaoCriterea2());
		testDelete();
	}

	public AppLogEntityId testGetDaoCriterea2() {
		try {			
			testSave(AppLogEntityId.class.getName(), false);
			dao.getDaoCriterea().equal("entityName", AppLogEntityId.class.getName());
			return dao.getFirst();			
		} catch (Exception e) {
			error(e);
		}
		return null;
	}

	/**
	 * Test method for {@link br.com.jardelnovaes.taxbr.persitence.AbstractDAO#delete(java.lang.Object)}.
	 */
	
	@Test
	public void testDelete() {
		try {
			AppLogEntityId entity = new AppLogEntityId();			
			entity.setEntityName("Delete:: testDelete()");
			
			List<AppLogEntityId> items = dao.getAll();
			
			dao.beginTransaction();
			for (AppLogEntityId item : items) {
				dao.delete(item);				
			}
			dao.commit();
			assertTrue(true);
			
		} catch (Exception e) {
			error(e);
		}
	}
	
	/**
	 * Test method for {@link br.com.jardelnovaes.taxbr.persitence.AbstractDAO#update(java.lang.Object)}.
	 */
	
	@Test
	public void testUpdate() {
		try {			
			AppLogEntityId entity = testGetDaoCriterea2();
			if(entity != null){
				entity.setEntityName("This name was changed");
				dao.beginTransaction();
				dao.update(entity);
				dao.commit();
				assertEquals("This name was changed", entity.getEntityName());
			}
			
		} catch (Exception e) {
			error(e);
		}
		
	}
}
