<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="Access Denied" activeAction="AccessDenied">
	<br/>
	<div class="row">
		<div class="panel panel-danger">  
	  		<div class="panel-heading"><label>Desculpe!</label></div>			
			<div class="msgPanelContent">
				<p>Você não está autorizado a acessar esse conteúdo.</p>
				<p>Contate o administrador do sistema em sua empresa ou o nosso suporte.</p>
			</div>
		</div>	
	</div>
</tags:simplePage>