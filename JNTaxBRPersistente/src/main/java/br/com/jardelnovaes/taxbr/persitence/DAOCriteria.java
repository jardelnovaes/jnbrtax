package br.com.jardelnovaes.taxbr.persitence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

public class DAOCriteria <T>{
	private CriteriaBuilder criteria;
	private CriteriaQuery<T> criteriaQuery;        
	private Root<T> root;
	private List<Predicate> predicates;
	private List<Predicate> fixedPredicates;
	
	public DAOCriteria(){
		clearPredicates();
		fixedPredicates = new ArrayList<Predicate>();
	}
	
	public CriteriaBuilder getCriteria() {
		return criteria;
	}
	public void setCriteria(CriteriaBuilder criteria) {
		this.criteria = criteria;
	}
	public CriteriaQuery<T> getCriteriaQuery() {
		return criteriaQuery;
	}
	
	public void setCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
		this.criteriaQuery = criteriaQuery;
	}
	
	public Root<T> getRoot() {
		return root;
	}
	public void setRoot(Root<T> root) {
		this.root = root;
	}
    
	public CriteriaQuery<Long> getCountCriteria(Class<T> clazz){
		CriteriaQuery<Long> countQuery = getCriteria().createQuery(Long.class);
		countQuery.select(getCriteria().count(countQuery.from(clazz)));
		
		return countQuery;
	}
	

	public void clearPredicates(){
		if(predicates != null)
			predicates.clear();
		
		predicates = new ArrayList<Predicate>();
	}
	
	/*
	 * Apply the WHERE/AND/ETC clauses with propName = value. (Like as CountryName = 'Brazil').
	 */
	public void applyPredicates(){
		//query.where(andPredicates.toArray(new Predicate[andPredicates.size()]));
		List<Predicate> preds = new ArrayList<Predicate>();
		
		if((fixedPredicates != null) && (fixedPredicates.size() > 0)){
			preds.addAll(0, fixedPredicates);
		}
		
		if((predicates != null) && (predicates.size() > 0)){
			preds.addAll(preds.size(), predicates);			
		}
		
		if((preds.size() > 0) && (criteriaQuery != null)){
			getCriteriaQuery().where(preds.toArray(new Predicate[preds.size()]));
		}
	}
	
	/*
	 * Create the WHERE clause with propName = value. (Like as CountryName = 'Brazil').
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param propName Name of entity's property to use in the left side of equal.
	 * @param value Value to use in the right side of equal, comparing with propName. 
	 */
	public void equal(String propName, Object value){
		//getCriteriaQuery().where(getCriteria().equal(getRoot().get(propName), value));
		predicates.add(getCriteria().equal(getRoot().get(propName), value));	
	}
	
	/*
	 * Create the IN clause with propName IN (values). (Like as CountryName IN ('Brazil', 'England').
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param propName Name of entity's property to use in the left side of equal.
	 * @param values List of values to use in the "IN", comparing their with propName. 
	 */
	public void in(String propName, Object[] values){
		predicates.add(getRoot().get(propName).in(values));	
	}
	
	/*
	 * Create the WHERE clause with propName IS NOT NULL. (Like as CountryName IS NOT NULL).
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param propName Name of entity's property.
	 */
	public void isNotNull(String propName){
		predicates.add(getCriteria().isNotNull(getRoot().get(propName)));	
	}
	
	/*
	 * Create the JOIN clause with TJoin entity. 
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param joinEntityName Name of entity's property that represents the other entity of join (Ex: person.getHomeAddress() +> "homeAddrees")
	 * @return Returns a Join<T, TJoin> object where T is the main entity and TJoin is the entity of joinEntityName.  
	 */
	@SuppressWarnings("unchecked")
	public <TJoin> Join<T, TJoin> join(String joinEntityName){				
		 return (Join<T, TJoin>) getRoot().join(joinEntityName);
	}
	
	/*
	 * Create the LEFT JOIN clause with TJoin entity. 
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param joinEntityName Name of entity's property that represents the other entity of join (Ex: person.getHomeAddress() +> "homeAddrees")
	 * @return Returns a Join<T, TJoin> object where T is the main entity and TJoin is the entity of joinEntityName.  
	 */
	@SuppressWarnings("unchecked")
	public <TJoin> Join<T, TJoin> leftJoin(String joinEntityName){				
		 return (Join<T, TJoin>) getRoot().join(joinEntityName, JoinType.LEFT);
	}
	
	/*
	 * Create an additional EQUAL clause in the WHERE clause using a JOINED entity (Ex: WHERE P1.Value > 100 AND P2.XType=3).
	 * Use this method to add the "AND P2.XType=3" inside the WHERE clause. 
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param joinEntityName Name of entity's property that represents the other entity of join (Ex: person.getHomeAddress() +> "homeAddrees")
	 * @param value Value to use in the clause (ex: 3)
	 * @return Returns a Join<T, TJoin> object where T is the main entity and TJoin is the entity of joinEntityName.  
	 */
	public  <TJoin> void joinWithEqual(Join<T, TJoin> join, String joinEntityProp, Object value){
		//Create the WHERE EQUAL clause for the joined entity.
		//getCriteriaQuery().where(
		//			getCriteria().equal(join.get(joinEntityProp), value)
		//		);		
		predicates.add(getCriteria().equal(join.get(joinEntityProp), value));
	}
	
	/*
	 * Create an additional >= clause in the WHERE clause using a JOINED entity (Ex: WHERE P1.Value = 100 AND P2.XType >= 3).
	 * Use this method to add the "AND P2.XType >= 3" inside the WHERE clause.
	 * For LONG properties. 
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param joinEntityName Name of entity's property that represents the other entity of join (Ex: person.getHomeAddress() +> "homeAddrees")
	 * @param value Value to use in the clause (ex: 3)
	 * @return Returns a Join<T, TJoin> object where T is the main entity and TJoin is the entity of joinEntityName.  
	 */
	public  <TJoin> void joinWithGE(Join<T, TJoin> join, String joinEntityProp, long value){
		predicates.add(getCriteria().ge(join.<Long>get(joinEntityProp), value));
	}
	
	/*
	 * Create an additional IS NOT NULL clause in the WHERE clause using a JOINED entity (Ex: WHERE P1.Value = 100 AND P2.XType IS NOT NULL).
	 * Use this method to add the "AND P2.XType IS NOT NULL" inside the WHERE clause. 
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param joinEntityName Name of entity's property that represents the other entity of join (Ex: person.getHomeAddress() +> "homeAddrees")	 
	 * @return Returns a Join<T, TJoin> object where T is the main entity and TJoin is the entity of joinEntityName.  
	 */
	public  <TJoin> void joinWithIsNotNull(Join<T, TJoin> join, String joinEntityProp){		
		predicates.add(getCriteria().isNotNull(join.<Long>get(joinEntityProp)));
	}
	
	/*
	 * Create an additional IS NULL clause in the WHERE clause using a JOINED entity (Ex: WHERE P1.Value = 100 AND P2.XType IS NULL).
	 * Use this method to add the "AND P2.XType IS NOT NULL" inside the WHERE clause. 
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param joinEntityName Name of entity's property that represents the other entity of join (Ex: person.getHomeAddress() +> "homeAddrees")	 
	 * @return Returns a Join<T, TJoin> object where T is the main entity and TJoin is the entity of joinEntityName.  
	 */
	public  <TJoin> void joinWithIsNull(Join<T, TJoin> join, String joinEntityProp){		
		predicates.add(getCriteria().isNull(join.<Long>get(joinEntityProp)));
	}
	
	/*
	 * Create an additional ON clause in the JOIN clause, specially from LEFT JOINs (Ex: ON P1.Id = P2.P1_Id AND P2.XType=3).
	 * Use this method to add the "AND P2.XType=3" inside the ON clause. 
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param joinEntityName Name of entity's property that represents the other entity of join (Ex: person.getHomeAddress() +> "homeAddrees")
	 * @param value Value to use in the clause (ex: 3)
	 * @return Returns a Join<T, TJoin> object where T is the main entity and TJoin is the entity of joinEntityName.  
	 */
	public  <TJoin> void joinWithOn(Join<T, TJoin> join, String joinEntityProp, Object value){
		join.on(getCriteria().equal(join.get(joinEntityProp), value));
	}
	
	
	public <TJoin> void fixedJoinEqual(Join<T, TJoin> join, String joinEntityProp, Object value){
		fixedPredicates.add(getCriteria().equal(join.get(joinEntityProp), value));
	}
	
	/*
	 * Create the ORDER BY.
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param order Order object with the sorting rules.
	 */
	public void orderBy(Order order){
		getCriteriaQuery().orderBy(order);
	}
	
	/*
	 * Create the ORDER BY.
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param order1 First order object with the sorting rules.
	 * @param order2 Second order object with the sorting rules.
	 */
	public void orderBy(Order order1, Order order2){
		getCriteriaQuery().orderBy(order1, order2);		
	}
	
	/*
	 * Create the ORDER BY.
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param orderList List with the orders objects with the sorting rules.
	 */
	public void orderBy(List<Order> orderList){
		getCriteriaQuery().orderBy(orderList);		
	}
	
	/*
	 * Create the ascending ORDER BY clause using propName.
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param propName Name of entity's property to use in the sorting.
	 */
	public void asc(String propName){
		orderBy(getCriteria().asc(getRoot().get(propName)));
	}
	
	public Order getAscOrder(String propName){
		return  getCriteria().asc(getRoot().get(propName));
	}
	
	public Order getDescOrder(String propName){
		return  getCriteria().desc(getRoot().get(propName));
	}
	
	public <TJoin> Order getDescOrderFromJoin(Join<T, TJoin> join, String propName){
		return  getCriteria().desc(join.get(propName));
	}
	
	public <TJoin> Order getAscOrderFromJoin(Join<T, TJoin> join, String propName){
		return  getCriteria().asc(join.get(propName));
	}
	
	public void asc(String propName1, String propName2){
		orderBy(
			getAscOrder(propName1),
			getAscOrder(propName2)
		);
	}
	
	/*
	 * Create the descending ORDER BY clause using propName.
	 * When used the getResult of AbstractDAO will apply this clause in the query.
	 * @param propName Name of entity's property to use in the sorting.
	 */
	public void desc(String propName){
		orderBy(getCriteria().desc(getRoot().get(propName)));
	}
	
	public void desc(String propName1, String propName2){
		orderBy(
			getDescOrder(propName1),
			getDescOrder(propName2)
		);
	}
}
