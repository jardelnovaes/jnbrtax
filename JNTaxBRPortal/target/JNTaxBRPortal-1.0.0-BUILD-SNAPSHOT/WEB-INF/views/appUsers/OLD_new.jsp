<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="App Users" activeAction="AppUsers">
  <form:form method="POST" modelAttribute="user" action="create" cssClass="form-horizontal">  	  
	<div class="row">			
		<div class="form-group">
		  <label for="id" class="col-sm-2 control-label">Id</label>
		  <div class="col-sm-2">		    
		    <form:input type="text" path="id" id="id" disabled="true" cssClass="form-control" />
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
		  <div class="col-sm-offset-2 col-sm-10">
		    <button type="submit" class="btn btn-success">Enviar</button>
		  </div>
		</div>
		<c:if test="${not empty successMsg}">	
		<div class="row">
			<div class="col-md-3 col-md-offset-1 col-sm-1">	
				<div class="panel panel-success">
			        <div class="panel-heading">
			            <h3 class="panel-title">Mensagem</h3>
			        </div>
			        <div class="panel-body">${successMsg}</div>
			    </div>
		    </div>
	    </div>
		</c:if>
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
  </form:form>
</tags:simplePage>