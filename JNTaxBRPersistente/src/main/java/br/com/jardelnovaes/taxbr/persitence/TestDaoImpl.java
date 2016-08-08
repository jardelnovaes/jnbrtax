package br.com.jardelnovaes.taxbr.persitence;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.TestEntity;

/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
*/

//@Repository
public class TestDaoImpl extends AbstractDAO<TestEntity, Integer> implements TestDao{
	//private static final Logger logger = LoggerFactory.getLogger(AppUserDAOJPAImpl.class);
	
	public List<TestEntity> getAll(){
		super.createEntityCriteria();
    	super.getDaoCriterea().asc("textInfo");
    	
        List<TestEntity> items = super.getResults();
        //logger.debug(this.getClass().getName() + "getAll() => " + String.valueOf(users.size()));
        
        return items;        
	}
	  
	public TestEntity getById(int id) {
		return super.getByKey(id);
	}

	public void save(TestEntity entity) {
		
		
		try {
			super.beginTransaction();
			super.insert(entity);
			super.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//logger.debug("Saved Id: {} - Name: {}", String.valueOf(user.getId()), user.getName());
		
		/*
		 Session session = sessionFactory.openSession();
		 
		//session.persist(user);
		session.saveOrUpdate(user);
		session.flush();
		logger.debug("Saved Id: {} - Name: {}", String.valueOf(user.getId()), user.getName());
		session.close();
		*/
	}

}


