package br.com.jardelnovaes.taxbr.persitence;

import java.io.Serializable;
 
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import javax.persistence.criteria.CriteriaQuery;

import br.com.jardelnovaes.taxbr.models.AppEntityLogTypeEnum;
import br.com.jardelnovaes.taxbr.models.AppUser;

public abstract class AbstractDAO<T, PK extends Serializable> {
    
	private final Class<T> persistentClass;
	
	private AppLogEntityUtils<T> appLogEntityUtils;
	private QueryHintUtils queryHintUtils;
	
	private PagedData pagedData;	
    protected DAOCriteria<T> daoCriteria;
	
	private boolean useTransaction;
	private boolean useCache;
	private boolean isGetFirst;
	
	private EntityManagerFactory factory;
    
    //@PersistenceContext
    private EntityManager entityManager;
    
	@SuppressWarnings("unchecked")
    public AbstractDAO(){
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
		if(persistentClass.getAnnotation(Cacheable.class) != null)
			setUseCache(true);	
		
		appLogEntityUtils = new AppLogEntityUtils<T>(persistentClass);	
		queryHintUtils = new QueryHintUtils();
    }
    	
    public AppUser getCurrentAppUser() {
		return appLogEntityUtils.getAppUser();
	}

	public void setCurrentAppUser(AppUser appUser) {
		appLogEntityUtils.setAppUser(appUser);
	}

	public PagedData getPagedData() {
		return pagedData;
	}

	public void setPagedData(PagedData pagedData) {
		this.pagedData = pagedData;
	}

	public DAOCriteria<T> getDaoCriterea() {
		return daoCriteria;
	}

	public void setDaoCriterea(DAOCriteria<T> daoCriterea) {
		this.daoCriteria = daoCriterea;
	}

	public boolean isUseTransaction() {
		return useTransaction;
	}

	public void setUseTransaction(boolean useTransaction) {
		this.useTransaction = useTransaction;
	}

	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	
    public EntityManagerFactory getNewFactory(){
    	return getNewFactory("JNBRTaxPersistente");
    }
    public EntityManagerFactory getNewFactory(String persistenceUnit){
    	return Persistence.createEntityManagerFactory(persistenceUnit);
    }
    
    public void createDefaultFactory(){
    	setEntityManagerFactory(getNewFactory());
    }
    
    //Utilizado pelo Spring para gerir o acesso aos dados.
  	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory factory) {
		this.factory = factory;
		queryHintUtils.setEntityManagerFactory(factory);
	}
	
	public EntityManagerFactory getEntityManagerFactory(){
		return this.factory;
	}
	
    protected  EntityManager getEntityManager() {
    	if(this.entityManager == null)
    		setEntityManager(this.factory.createEntityManager());
    	
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		appLogEntityUtils.setEntityManager(entityManager);
	}
	   
    public T getByKey(PK key) {    	
    	// retorna objeto pelo tipo da classe filho e pela PK dela
    	return getEntityManager().find(persistentClass, key);
        //return (T) getEntityManager().get(persistentClass, key);
    }
 
    public void insert(T entity) throws Exception { 
    	appLogEntityUtils.isAppLogValid(AppEntityLogTypeEnum.Insert, entity);//It needs to be first, 'cause when is an insert the entity has the appEntityLog without some informations.
        getEntityManager().persist(entity);        
        
    }
    
    public void update(T entity) throws Exception {   	
    	if(!getEntityManager().contains(entity))    		
    		getEntityManager().merge(entity);   
    	appLogEntityUtils.isAppLogValid(AppEntityLogTypeEnum.Update, entity);
    }
 
    public void delete(T entity) throws Exception {
    	if(!getEntityManager().contains(entity))
    		entity = getEntityManager().merge(entity);   
    	
    	if(isUseTransaction())
    		beginTransaction();
    	
    	getEntityManager().remove(entity);
    	appLogEntityUtils.isAppLogValid(AppEntityLogTypeEnum.Delete, entity);

    	if(isUseTransaction())
    		commit();
    }
    
    public void beginTransaction(){
    	if(!getEntityManager().getTransaction().isActive())
    		getEntityManager().getTransaction().begin();
    }
    
    public void commit(){
    	getEntityManager().flush();
    	if(getEntityManager().getTransaction().isActive())
    		getEntityManager().getTransaction().commit();
    }
    
    public void rollback(){    	
    	if(getEntityManager().getTransaction().isActive())
    		getEntityManager().getTransaction().rollback();
    }
    
    //pré configura um grupos de critérios
    public DAOCriteria<T> createEntityCriteria(){    	
    	daoCriteria = new DAOCriteria<T>();
    	
    	daoCriteria.setCriteria(getEntityManager().getCriteriaBuilder());
    	daoCriteria.setCriteriaQuery(daoCriteria.getCriteria().createQuery(persistentClass));        
    	daoCriteria.setRoot(daoCriteria.getCriteriaQuery().from(persistentClass));       
    	daoCriteria.getCriteriaQuery().select(daoCriteria.getRoot()); 
        
    	return daoCriteria;
        //return getSession().createCriteria(persistentClass);    	
    }

    @SuppressWarnings("unchecked")
	protected List<T> getResults(CriteriaQuery<T>  criteriaQuery){    	
    	Query query;
    	Query queryTotal; 
    	Long total = (long) 0 ;
    	
    	//getDaoCriterea().clearPredicates();

    	getDaoCriterea().applyPredicates();
    	if(pagedData != null){
    		if(!getPagedData().getOrderPropertyName().isEmpty()){
    			if(getPagedData().isDescending()){
    				criteriaQuery.orderBy(
        					getDaoCriterea().getDescOrder(getPagedData().getOrderPropertyName())
        		    );
    			}
    			else{
    				criteriaQuery.orderBy(        					
        		       		getDaoCriterea().getAscOrder(getPagedData().getOrderPropertyName())
        		    );	
    			}    			
    		}
    		
    		query = getEntityManager().createQuery(criteriaQuery);
    		    		
    		if (pagedData.getPageSize() > 0){
	    		query.setMaxResults(pagedData.getPageSize());
	    		query.setFirstResult(pagedData.getPageSize() * pagedData.getPageNumber());

	    		queryTotal = getEntityManager().createQuery(getDaoCriterea().getCountCriteria(persistentClass)); 
	    		if(isUseCache()){	    			
	    			queryTotal.setHint(queryHintUtils.getHintCache(), true);
	    			queryTotal.setHint(queryHintUtils.getHintCacheRegion(), persistentClass.getName());
	    		}
	    		
	    		total = (Long)queryTotal.getSingleResult();
	    		
	    		pagedData.setTotalRecords(total);
    		}
    	}
    	else{
    		query = getEntityManager().createQuery(criteriaQuery);
    		if(isGetFirst){
    			query.setMaxResults(1);	    		
	    		isGetFirst = false;
    		}
    	}
    	
    	if(isUseCache()){  
    		query.setHint(queryHintUtils.getHintCache(), true);
			query.setHint(queryHintUtils.getHintCacheRegion(), this.persistentClass.getName());
			
    		//query.setHint("javax.persistence.cache.storeMode",  CacheStoreMode.REFRESH);
    		//query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
    		//query.setHint("org.hibernate.cacheable", Boolean.TRUE);
            //query.setHint("org.hibernate.cacheMode", "NORMAL");
            //org.hibernate.Query hquery = query.unwrap(org.hibernate.Query.class);
            //<property name="hibernate.ejb.classcache.org.hibernate.ejb.test.Item" value="read-write"/>
    	}
    	List<T> result = query.getResultList();
    	getDaoCriterea().clearPredicates();
    	return result;
    }
    
    protected List<T> getResults(DAOCriteria<T> crit){
    	return getResults(crit.getCriteriaQuery());
    }
    
    protected List<T> getResults(){
    	return getResults(getDaoCriterea());
    }

    protected T getUniqueResult(CriteriaQuery<T>  criteriaQuery){
    	List<T> results = getResults(criteriaQuery);
    	 
        if (!results.isEmpty())
            return results.get(0);
        else
            return null;
    }
    
    
    protected T getUniqueResult(DAOCriteria<T> crit){
    	return getUniqueResult(crit.getCriteriaQuery());
    }
    
    protected T getUniqueResult(){
    	return getUniqueResult(getDaoCriterea()); 
    }
    
    public T getFirst(){
    	if(getDaoCriterea() == null)
    		createEntityCriteria();
    	isGetFirst = true;
    	return getUniqueResult();
    }	
}