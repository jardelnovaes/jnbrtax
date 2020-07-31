<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:simplePage pageTitle="App Users" activeAction="About">
	<br/>
	<div class="row">
		<div class="panel panel-default">  
	  		<div class="panel-heading"><label>Informations about install process</label></div>			
			<div class="msgPanelContent">				
				<p>${message}</p>
			</div>
		</div>
	</div>	
</tags:simplePage>