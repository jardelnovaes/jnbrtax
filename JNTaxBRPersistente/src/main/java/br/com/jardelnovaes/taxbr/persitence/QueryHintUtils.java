package br.com.jardelnovaes.taxbr.persitence;

import javax.persistence.EntityManagerFactory;

public class QueryHintUtils {
	private static final String PROP_CACHE_QUERYHINT = "br.com.jardelnovaes.taxbr.persitence.cache.queryHint";
	private static final String PROP_CACHE_QUERYREGION = "br.com.jardelnovaes.taxbr.persitence.cache.queryRegionHint";

	private String hintCache = null;
	private String hintCacheRegion = null;
	private EntityManagerFactory emf;

	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public void reset() {
		hintCache = null;
		hintCacheRegion = null;
	}

	public String getHintCache() {
		// br.com.jardelnovaes.taxbr.persitence.cache.queryHint
		if ((hintCache == null) || (hintCache.isEmpty())) {
			hintCache = (String) emf.getProperties().get(PROP_CACHE_QUERYHINT);
			if ((hintCache == null) || (hintCache.isEmpty())) {
				hintCache = "org.hibernate.cacheable";
			}
		}
		return hintCache;
	}
	
	public String getHintCacheRegion() {
		// br.com.jardelnovaes.taxbr.persitence.cache.queryRegionHint
		if ((hintCacheRegion == null) || (hintCacheRegion.isEmpty())) {
			hintCacheRegion = (String) emf.getProperties().get(PROP_CACHE_QUERYREGION);
			if ((hintCacheRegion == null) || (hintCacheRegion.isEmpty())) {
				hintCacheRegion = "org.hibernate.cacheRegion";
			}
		}
		return hintCacheRegion;
	}

}
