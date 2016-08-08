<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="App Users" activeAction="AppUsers">
  <form:form method="POST" modelAttribute="user" action="${action}" cssClass="form-horizontal">  	  
	<div class="row">			
		<div class="form-group">
		  <label for="id" class="col-sm-2 control-label">Id</label>
		  <div class="col-sm-2">		    
		    <form:input type="text" path="id" id="id" disabled="true" cssClass="form-control" />
		    <form:input type="hidden" path="id" id="id" />
		  </div>
		</div>
		<div class="form-group">
		  <label for="name" class="col-sm-2 control-label">Nome</label>
		  <div class="col-sm-2">		    
		    <form:input type="text" path="name" id="name" cssClass="form-control" placeholder="Nome" />
		  </div>
		</div>
		<div class="form-group">
		  <label for="name" class="col-sm-2 control-label">E-mail</label>
		  <div class="col-sm-2">		    
		    <form:input type="text" path="email" id="email" cssClass="form-control" placeholder="e-mail" />
		  </div>
		</div>
		<div class="form-group">
		  <label for="name" class="col-sm-2 control-label">Senha</label>
		  <div class="col-sm-2">		    
		    <form:input type="password" path="password" id="password" cssClass="form-control" placeholder="Informe sua senha" />
		  </div>
		</div>
		<div class="form-group">
		  <label for="name" class="col-sm-2 control-label">Confirme a Senha</label>
		  <div class="col-sm-2">
		  	<form:input type="password" path="confirmPassword" id="confirmPassword" cssClass="form-control" placeholder="Confirme sua senha" />	    
		    
		  </div>
		</div>
		<div class="form-group">
		  <label for="name" class="col-sm-2 control-label">Ativo?</label>
		  <div class="col-sm-2">		    
		  	<form:checkbox path="active" id="active" cssClass="form-control" />		    
		  </div>
		</div>
		<div class="form-group">
		  <div class="col-sm-offset-2 col-sm-10">
		    <button type="submit" class="btn btn-success">Salvar</button>
		  </div>
		</div>		
	</div>  
	<tags:successMessage successMsg="${successMsg}" />
	<tags:errorMessage errorMsg="${errorMsg}" />  
  </form:form>
</tags:simplePage>