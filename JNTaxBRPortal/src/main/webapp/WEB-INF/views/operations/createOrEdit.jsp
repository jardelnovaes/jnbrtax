<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<c:set var="cssFormInput" value="col-sm-2 col-md-3"/>
<c:set var="cssMsgPanels" value="col-md-4 col-md-offset-2 col-sm-1"/>
<tags:simplePage pageTitle="Operation" activeAction="Operations">
	<tags:formIdName backPath="operations" action="${action}" cssFormInput="${cssFormInput}" cssMsgPanels="${cssMsgPanels}">
		<div class="form-group">
		  <label for="transactionType.id" class="<c:out value="${cssFormInput}" /> control-label">Tipo de Transa��o</label>
		  <div class="<c:out value="${cssFormInput}" />">
		  	<form:select path="transactionType.id" items="${transactionList}" itemValue="id" itemLabel="name" cssClass="form-control"/>		  			  	
		  </div>
		</div>	
		<div class="form-group">
		  <label for="active" class="<c:out value="${cssFormInput}" /> control-label">Ativo?</label>
		  <div class="<c:out value="${cssFormInput}" />">		    
		    <form:checkbox path="active" id="active" cssClass="form-control" />
		  </div>
		</div>
	</tags:formIdName>  		
</tags:simplePage>
