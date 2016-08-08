<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="App Users" activeAction="AppUsers">
  <h3>Confirmação para exclusão de cadastro</h3>
  <form:form method="POST" modelAttribute="user" action="${action}" cssClass="form-horizontal">  	  
	 
  </form:form>
</tags:simplePage>