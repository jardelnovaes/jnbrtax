<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="pageTitle" required="true"%>
<%@ attribute name="bodyClass" required="false"%>
<%@ attribute name="activeAction" required="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<c:url value="/resources/bootstrap" var="bootstrap" />
	<!-- https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css -->
	<c:url value="/resources/css" var="cssPath" /> 
	<c:url value="/resources/js" var="jsPath" />
	
 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
		
	<!-- JQuery -->
	    <script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
	    <!-- script src="https://code.jquery.com/ui/1.10.4/jquery-ui.min.js"></script>  -->
	<!-- END:JQuery -->
	<!-- Bootstrap -->
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="${bootstrap}/css/bootstrap.min.css" />
	
		<!-- Optional theme -->
		<link rel="stylesheet" href="${bootstrap}/css/bootstrap-theme.min.css" />
	
		<!-- Latest compiled and minified JavaScript -->
		<script src="${bootstrap}/js/bootstrap.min.js"></script>
	<!-- END::Bootstrap -->

	<link href="${cssPath}/site.css" rel="stylesheet" type="text/css" />
	<link rel="icon" href="//cdn.shopify.com/s/files/1/0155/7645/t/177/assets/favicon.ico?11981592617154272979" type="image/ico" />
	
	<title>${pageTitle} - Spring MVC</title>
</head>
<body class="${bodyClass}">

<div class="container">
<%@ include file="/WEB-INF/views/shared/header.jsp" %>

<jsp:doBody/>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</div>
<div class="row"></div>
