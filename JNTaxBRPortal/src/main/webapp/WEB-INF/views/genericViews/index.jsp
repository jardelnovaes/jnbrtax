<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<!--  < s p r ing:message code="display.ICMSType.hasReduction" />  --> 
<tags:simplePage pageTitle="${viewParams.tableListName}" activeAction="${viewParams.activeAction}">
	<c:url value="${viewParams.actUrlPath}" var="actUrl" />
	<br/>
	<div class="row">
		<div class="panel panel-default">
	  		<div class="panel-heading">
	  			<h3 class="panel-title pull-left">${viewParams.tableListName}</h3>
	  			<a href="${actUrl}/create" class="btn btn-primary pull-right">Criar Novo</a>
	  			<div class="clearfix"></div>
	  		</div>
	  		
			<table class="table">
			    <tr>
			    	<th></th>
			    	<c:forEach var="prop" items="${viewParams.genericViewFields}">
					<th>${prop.fieldDisplay}</th>									
					</c:forEach>    	
			    </tr>
			
			<c:forEach var="item" items="${items}">
				<tr>
					<td>
					  <a href="${actUrl}/edit/${item[viewParams.fieldIdName]}" class="btn btn-success btn-xs">
				     	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				     </a> 
				     <a href="${actUrl}/delete/${item[viewParams.fieldIdName]}" class="btn btn-danger btn-xs"  >
				     	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
				     </a>
					</td>
					<c:forEach var="prop" items="${viewParams.genericViewFields}">					
					<c:choose>
						<c:when test="${prop.fieldType eq 'boolean'}">						
							<td><input type="checkbox" <c:if test="${item[prop.fieldName]}">checked</c:if>  /></td>
						</c:when>
						<c:otherwise>
							<td>${item[prop.fieldName]}</td>
						</c:otherwise>
					</c:choose>
					</c:forEach>	
				</tr>
			</c:forEach>			
			</table>
		</div>		
		<c:if test="${not empty errorMsg}">	
		<div class="row">
			<div class="col-md-3 col-md-offset-1 col-sm-1">	
				<div class="panel panel-danger">
			        <div class="panel-heading">
			            <h3 class="panel-title">Mensagem</h3>
			        </div>
			        <div class="panel-body">${errorMsg}</div>
			    </div>
		    </div>
	    </div>
		</c:if>	
	</div>
</tags:simplePage>