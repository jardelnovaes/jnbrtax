package br.com.jardelnovaes.taxbr.persitence;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.jardelnovaes.taxbr.models.AppEntityLog;
import br.com.jardelnovaes.taxbr.models.AppEntityLogTypeEnum;
import br.com.jardelnovaes.taxbr.models.AppLogEntityId;
import br.com.jardelnovaes.taxbr.models.AppUser;
import br.com.jardelnovaes.taxbr.models.Company;

public class AppLogEntityUtils<T> {

	private AppLogEntityId entityLogId;
	private AppEntityLog appEntityLog;
	private final Class<T> persistentClass;

	private EntityManager em;
	private AppUser appUser;

	public AppLogEntityUtils(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public EntityManager getEntityManager() {
		return em;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public AppLogEntityId getEntityLogIdByName(String entityName) {
		DAOCriteria<AppLogEntityId> criteria = new DAOCriteria<AppLogEntityId>();
		criteria.setCriteria(getEntityManager().getCriteriaBuilder());
		criteria.setCriteriaQuery(criteria.getCriteria().createQuery(
				AppLogEntityId.class));
		criteria.setRoot(criteria.getCriteriaQuery().from(AppLogEntityId.class));
		criteria.getCriteriaQuery().select(criteria.getRoot());

		criteria.equal("entityName", entityName);
		TypedQuery<AppLogEntityId> query = getEntityManager().createQuery(
				criteria.getCriteriaQuery());

		try {
			return query.getSingleResult();
		} catch (NoResultException notFoundEx) {
			return null;
		}
	}

	public AppLogEntityId getOrCreateEntityLogId() {
		String entityName = this.persistentClass.getName();

		if (entityLogId == null) {
			entityLogId = new AppLogEntityId();
		}

		if (entityLogId.getId() <= 0) {
			entityLogId = getEntityLogIdByName(entityName);
			if ((entityLogId == null) || (entityLogId.getId() == 0)) {
				entityLogId = new AppLogEntityId();
				entityLogId.setEntityName(entityName);
				getEntityManager().persist(entityLogId);
			}
		}
		return entityLogId;
	}

	public String getAppEntityIdValue(T entity) {
		return getAppEntityIdValue(persistentClass, entity);
	}

	public String getAppEntityIdValue(Class<?> clazz, T entity) {
		if ((clazz == null) || (entity == null))
			return null;

		for (Field f : clazz.getDeclaredFields()) {
			@SuppressWarnings("unused")
			Id id = null;
			// Column column = null;
			Annotation[] as = f.getAnnotations();
			for (Annotation a : as) {
				if (a.annotationType() == Id.class) {
					id = (Id) a;
					try {
						f.setAccessible(true);
						return String.valueOf(f.get(entity));
					} catch (Exception e) {
						return null;
					}
				}
				/*
				 * else if (a.annotationType() == Column.class) column =
				 * (Column) a;
				 */
			}
			/*
			 * if (id != null && column != null){ name = column.name(); break; }
			 */
		}

		// if it didn't find then it'll call this method using the superclass.
		if (clazz.getSuperclass() != Object.class)
			return getAppEntityIdValue(clazz.getSuperclass(), entity);

		return null;
	}

	public boolean createAppEntityLog(AppEntityLogTypeEnum logType, T entity) {
		entityLogId = getOrCreateEntityLogId();

		if (entityLogId.isLogActived()) {
			appEntityLog = new AppEntityLog();
			appEntityLog.setLogType(logType);
			appEntityLog.setUser(appUser);

			appEntityLog.setEntityIdValue(getAppEntityIdValue(entity));
			appEntityLog.setEntity(entityLogId);
			return true;
		}
		return false;
	}

	public void isAppLogValid(AppEntityLogTypeEnum logType, T entity)
			throws Exception {
		if (persistentClass == AppEntityLog.class
				|| persistentClass == AppLogEntityId.class
				|| (persistentClass == AppUser.class && logType == AppEntityLogTypeEnum.Insert)
				|| (persistentClass == Company.class && logType == AppEntityLogTypeEnum.Insert)) {
		} else {
			if (createAppEntityLog(logType, entity)) {			
				appEntityLog.isAppLogValid();

				//TODO Verificar porque trava aqui quando não tem entityLogId cadastrado. 
				// add log in EntityManager.
				getEntityManager().persist(appEntityLog);
			}
		}
	}

}
