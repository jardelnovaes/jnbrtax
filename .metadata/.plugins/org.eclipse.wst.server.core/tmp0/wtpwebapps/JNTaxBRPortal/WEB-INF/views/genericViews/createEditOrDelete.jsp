<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<c:set var="cssFormInput" value="col-sm-2 col-md-3"/>
<c:set var="cssMsgPanels" value="col-md-4 col-md-offset-2 col-sm-1"/>

<tags:simplePage pageTitle="${viewParams.tableListName}" activeAction="${viewParams.activeAction}">
	<c:if test="${viewParams.delete}">
	<h3>Confirmação para exclusão de cadastro</h3>  
	</c:if>
	<form:form method="POST" modelAttribute="item" action="${actionPrefix}${viewParams.pageAction}" cssClass="form-horizontal">  	  
	<div class="row">			
		<div class="form-group">
		  <label for="${viewParams.fieldIdName}" class="<c:out value="${cssFormInput}" /> control-label">${viewParams.fieldIdDisplayName}</label>
		  <div class="<c:out value="${cssFormInput}" />">		  	
	  		<c:choose>
		    <c:when test="${viewParams.idIsEditable}">
		        <form:input type="text" path="${viewParams.fieldIdName}" id="${viewParams.fieldIdName}" cssClass="form-control" />		    		
		    </c:when>    
		    <c:otherwise>
		        <form:input type="text" path="${viewParams.fieldIdName}" id="${viewParams.fieldIdName}" disabled="true" cssClass="form-control" />
	    		<form:input type="hidden" path="${viewParams.fieldIdName}" id="${viewParams.fieldIdName}" />
		    </c:otherwise>
			</c:choose>
		  </div>
		</div>	
		
		
		<c:forEach var="viewField" items="${viewParams.genericViewFields}">
			<div class="form-group">
			<c:if test="${!viewField.idColumn}">
			<label for="${viewField.fieldName}" class="<c:out value="${cssFormInput}" /> control-label">${viewField.fieldDisplay}</label>
		  	<div class="<c:out value="${cssFormInput}" />">
		  	<c:choose>
				<c:when test="${viewField.fieldType eq 'boolean'}">
					<form:checkbox path="${viewField.fieldName}" id="${viewField.fieldName}" cssClass="form-control" />
				</c:when>
				<c:otherwise>
					<form:input type="text" path="${viewField.fieldName}" id="${viewField.fieldName}" cssClass="form-control" placeholder="${viewField.fieldDisplay}" />
				</c:otherwise>
			</c:choose>		    	
		  	</div>								
		  	</c:if>
		  	</div>
		</c:forEach> 
		
		<tags:formNavigator backPath="${viewParams.actUrlPath}" isDelete="${viewParams.delete}" />
	</div>
	<tags:successMessage successMsg="${successMsg}" />
	<tags:errorMessage errorMsg="${errorMsg}" />  
  </form:form>	  		
</tags:simplePage>
