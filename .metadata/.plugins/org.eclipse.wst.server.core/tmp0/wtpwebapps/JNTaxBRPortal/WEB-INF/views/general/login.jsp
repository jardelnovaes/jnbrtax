<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<c:set var="cssFormInput" value="col-md-4 col-md-offset-1 col-lg-4 col-lg-offset-1"/>

<tags:simplePage pageTitle="App Users" activeAction="About">
	<br/>	
	<!-- form:form method="POST" modelAttribute="item" action="login" cssClass="form-horizontal" -->
	<c:if test="${not empty msg}">
		<div class="alert alert-default">${msg}</div>
	</c:if>
	<form name='loginForm' action="<c:url value='/login' />" method='POST'>		
	<div class="row">			
		<div class="panel panel-default">  
	  		<div class="panel-heading"><label>Login - Autenticação do Sistema</label></div>	  		
	  		<div class="row">	
	  			<br/>
	  			<div class="<c:out value="${cssFormInput}" />">	  			
		  			<div class="form-group">
					  <label for="username" class="control-label">Usuário</label>
					  <div class="">		    
					  	<input type="text" id="username" name="username" class="form-control" placeholder="Usuário" />
					  </div>
					</div>
		  		</div>
		  	</div>
		  	<div class="row">
		  		<div class="<c:out value="${cssFormInput}" />">
		  			<div class="form-group">
					  <label for="password" class="control-label">Senha</label>
					  <div class="">		    
					  	<input type="password" id="password" name="password" class="form-control" placeholder="Senha" />
					  </div>
					</div>
		  		</div>
	  		</div>
	  		<div class="row">	  			
	  			<div class="col-md-1 col-md-offset-1 col-lg-1 col-lg-offset-1"> 			
						    
				  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />					
					<input name="submit" type="submit" value="Login" class="btn btn-success"/>
									  
				</div>
	  		</div>
		</div>		
	</div>	
	</form>
</tags:simplePage>