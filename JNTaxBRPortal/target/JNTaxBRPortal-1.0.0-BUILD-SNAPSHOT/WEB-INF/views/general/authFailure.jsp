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
				<p>Voc� est� informando dados incorretos de usu�rio ou senha!</p>
				<p>Voc� pode tentar novamente, ou caso n�o tenha certeza de sua senha pode alter�-la <a href="#esqueciMinhaSenha">aqui</a></p>
			</div>
		</div>	
	</div>
</tags:simplePage>