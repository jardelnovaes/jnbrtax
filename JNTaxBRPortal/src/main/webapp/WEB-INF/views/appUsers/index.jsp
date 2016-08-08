<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="App Users" activeAction="AppUsers">
	<c:url value="appUsers" var="actUrl" />
	<div class="row">
		<a href="${actUrl}/create" class="btn btn-primary">Criar Novo</a>
	</div>
	<br/>
	<div class="row">	
		<div class="panel panel-default">  
	  		<div class="panel-heading">Usuários da Aplicação</div>			
			<table class="table">
			    <tr>
			    	<th></th>
			    	<th>Nome</th>
			    	<th>E-mail</th>
			    	<th>Ativo?</th>			    	
			    </tr>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>
					  <a href="${actUrl}/edit/${user.id}" class="btn btn-success btn-xs">
				     	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				     </a> 
				      
				     <a href="${actUrl}/delete/${user.id}" class="btn btn-danger btn-xs"  >
				     	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
				     </a>
					</td>
					<td>${user.name}</td>
					<td>${user.email}</td>					
					<td>
						<input type="checkbox" <c:if test="${user.active}">checked</c:if>  />
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