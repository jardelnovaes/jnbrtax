<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="Tax Rules" activeAction="TaxRules">
	<c:url value="taxRules" var="actUrl" />
	
	<br/>
	<div class="row">	
		<div class="panel panel-default">			  
	  		<div class="panel-heading">
	  			<h3 class="panel-title pull-left">Regras de Tributação</h3>
	  			<a href="${actUrl}/create" class="btn btn-primary pull-right">Criar Novo</a>
	  			<div class="clearfix"></div>
	  		</div>  	
	  		
            	
			<table class="table">
			    <tr>
			    	<th></th>			    	
			    	<th><a href="?pgNum=${pagedData.pageNumber}&${orderDirection}=id">Id</a></th>
			    	<th><!-- <a href="?pgNum=${pagedData.pageNumber}&${orderDirection}=fromState.id">  -->UF Origem<!-- </a> --></th>
			    	<th><!--<a href="?pgNum=${pagedData.pageNumber}&${orderDirection}=toState.id">-->UF Destino<!-- </a> --></th>			    	
			    	<th>Tipo de Pessoa</th>
			    	<th>Tipo de Transação</th>
			    	<th>Operação</th>
			    	<th><a href="?pgNum=${pagedData.pageNumber}&${orderDirection}=NCM">NCM</a></th>
			    	<th><a href="?pgNum=${pagedData.pageNumber}&${orderDirection}=CEST">CEST</a></th>
			    	<th><a href="?pgNum=${pagedData.pageNumber}&${orderDirection}=itemId">Item</a></th>
			    	<th><a href="?pgNum=${pagedData.pageNumber}&${orderDirection}=active">Ativo?</a></th>			    		    	 
			    </tr>
			<c:forEach var="item" items="${rules}">
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
					<td>${item.fromState.id}</td>
					<td>${item.toState.id}</td>
					<td>${item.personType.name}</td>
					<td>${item.transactionType.name}</td>
					<td>${item.operation.name}</td>
					<td>${item.NCM}</td>
					<td>${item.CEST}</td>
					<td>${item.itemId}</td>
					<td>
						<input type="checkbox" <c:if test="${item.active}">checked</c:if>  />
					</td>
				</tr>
			</c:forEach>
			</table>
		</div>		
		<tags:pagination pagedData="${pagedData}" />
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