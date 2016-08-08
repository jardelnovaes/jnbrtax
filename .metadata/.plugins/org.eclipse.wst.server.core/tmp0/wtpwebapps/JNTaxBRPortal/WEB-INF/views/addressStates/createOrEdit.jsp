<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<c:set var="isEditableControl" value="false" />
<c:if test="${action eq 'create'}">
	<c:set var="isEditableControl" value="true" />
</c:if>

<c:set var="cssFormInput" value="col-sm-2 col-md-3"/>
<tags:simplePage pageTitle="Address States" activeAction="AddressStates">  
	<tags:formIdName backPath="addressStates" action="${action}" idName="UF" idIsEditable="${isEditableControl}">		
		<div class="form-group">
		  <label for="special" class="<c:out value="${cssFormInput}" /> control-label">Especial?</label>
		  <div class="<c:out value="${cssFormInput}" />">		    
		    <form:checkbox path="special" id="special" cssClass="form-control" />
		  </div>
		</div>
	</tags:formIdName>		
</tags:simplePage>