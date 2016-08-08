<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="Transaction Type" activeAction="TransactionTypes">
	<c:url value="transactionTypes" var="actUrl" />
	
	<div class="row">
		<a href="${actUrl}/create" class="btn btn-primary">Criar Novo</a>
	</div>
	<br/>
	<div class="row">	
		<div class="panel panel-default">  
	  		<div class="panel-heading">Tipos de Transação</div>			
			<table class="table">
			    <tr>
			    	<th></th>
			    	<th>Id</th>
			    	<th>Nome</th>
			    	<th>Operação de Saída?</th>
			    </tr>
			<c:forEach var="item" items="${items}">
				<tr>
					<td>
					  <a href="${actUrl}/edit/${item.id}" class="btn btn-success btn-xs">
				     	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				     </a> 
				      
				     <a href="${actUrl}/delete/${item.id}" class="btn btn-danger btn-xs"  >
				     	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
				     </a>
					</td>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>
						<input type="checkbox" <c:if test="${item.out}">checked</c:if>  />
					</td>
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