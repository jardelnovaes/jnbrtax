<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<c:set var="cssFormInput" value="col-sm-2 col-md-3"/>
<tags:simplePage pageTitle="Transaction Type" activeAction="TransactionTypes">
  <h3>Confirmação para exclusão de cadastro</h3>
  <tags:formIdName backPath="transactionTypes" action="${action}" isDelete="true">
		<div class="form-group">
		  <label for="out" class="<c:out value="${cssFormInput}" /> control-label">Operação de Saida?</label>
		  <div class="<c:out value="${cssFormInput}" />">		    
		    <form:checkbox path="out" id="out" cssClass="form-control" />
		  </div>
		</div>
  </tags:formIdName>
</tags:simplePage>

