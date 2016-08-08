<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="Person Type" activeAction="PersonTypes">
  <h3>Confirmação para exclusão de cadastro</h3>
  <tags:formIdName backPath="personTypes" action="${action}" isDelete="true">
		<div class="form-group">
		  <label for="active" class="col-sm-2 control-label">Ativo?</label>
		  <div class="col-sm-2">		    
		    <form:checkbox path="active" id="active" cssClass="form-control" />
		  </div>
		</div>
	</tags:formIdName>
</tags:simplePage>