<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="App Users" activeAction="About">
	<br/>
	<div class="row">
		<div class="panel panel-default">  
	  		<div class="panel-heading"><label>Sobre o MVC App</label></div>			
			<div class="msgPanelContent">
				<p>Este é um aplicativo de testes para avaliação do Spring MVC.</p>
				<p>Nele está sendo avaliado acesso aos dados por meio de JPA/Hibernate e Hibernate direto, além dos bancos de dados Oracle e SqlServer.</p>
			</div>
		</div>
	</div>
	<div class="row">	
		<div class="panel panel-default">  
	  		<div class="panel-heading"><label>Informações de localização</label></div>			
			<table class="table">
			    <tr>  	
			    	<th>Idioma</th>
			    	<th>País</th>
			    </tr>
				<tr>
					<td>${language}</td>
					<td>${country}</td>
				</tr>
			
			</table>
		</div>		
	</div>
</tags:simplePage>