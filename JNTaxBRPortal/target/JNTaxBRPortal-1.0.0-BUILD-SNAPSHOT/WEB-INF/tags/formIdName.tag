<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="action" required="true"%>
<%@ attribute name="backPath" required="true" type="java.lang.String" %>
<%@ attribute name="idName" required="false" type="java.lang.String"%>
<%@ attribute name="idIsEditable" required="false" type="java.lang.Boolean"%>
<%@ attribute name="isDelete" required="false" type="java.lang.Boolean"  %>
<%@ attribute name="submitName" required="false" type="java.lang.String" %>
<%@ attribute name="cssFormInput" required="false" type="java.lang.String" %>
<%@ attribute name="cssMsgPanels" required="false" type="java.lang.String" %>
<c:if test="${empty idName}">
  <c:set var="idName" value="Id" />
</c:if>
<c:if test="${empty cssFormInput}">
  <c:set var="cssFormInput" value="col-sm-2 col-md-3"/>
</c:if>
<c:if test="${empty cssMsgPanels}">
  <c:set var="cssMsgPanels" value="col-md-4 col-md-offset-1 col-sm-1"/>
</c:if>

<form:form method="POST" modelAttribute="item" action="${action}" cssClass="form-horizontal">  	  
	<div class="row">			
		<div class="form-group">
		  <label for="id" class="<c:out value="${cssFormInput}" /> control-label">${idName}</label>
		  <div class="<c:out value="${cssFormInput}" />">		  	
	  	<c:choose>
		    <c:when test="${idIsEditable}">
		        <form:input type="text" path="id" id="id" cssClass="form-control" />		    		
		    </c:when>    
		    <c:otherwise>
		        <form:input type="text" path="id" id="id" disabled="true" cssClass="form-control" />
	    		<form:input type="hidden" path="id" id="id" />
		    </c:otherwise>
		</c:choose>
		  </div>
		</div>	
		<div class="form-group">
		  <label for="name" class="<c:out value="${cssFormInput}" /> control-label">Nome</label>
		  <div class="<c:out value="${cssFormInput}" />">		    
		    <form:input type="text" path="name" id="name" cssClass="form-control" placeholder="Nome" />
		  </div>
		</div>		
		
		<jsp:doBody/>
		
		<tags:formNavigator backPath="${backPath}" submitName="${submitName}" isDelete="${isDelete}" />
	</div>
	<tags:successMessage successMsg="${successMsg}" />
	<tags:errorMessage errorMsg="${errorMsg}" />  
  </form:form>


