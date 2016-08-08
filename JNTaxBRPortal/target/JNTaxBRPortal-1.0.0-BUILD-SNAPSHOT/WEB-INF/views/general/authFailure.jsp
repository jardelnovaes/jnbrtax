<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="Auth Failure" activeAction="AuthFailure">
	<br/>
	<div class="row">
		<div class="panel panel-danger">  
	  		<div class="panel-heading"><label>Ops!</label></div>			
			<div class="msgPanelContent">
				<p>Você está informando dados incorretos de usuário ou senha!</p>
				<p>Você pode tentar novamente, ou caso não tenha certeza de sua senha pode alterá-la <a href="#esqueciMinhaSenha">aqui</a></p>
			</div>
		</div>	
	</div>
</tags:simplePage>