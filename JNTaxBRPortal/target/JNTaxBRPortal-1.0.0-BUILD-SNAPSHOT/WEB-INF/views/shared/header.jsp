<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<header>	
	<c:url value="${pageContext.request.contextPath}" var="baseURL" />
	
	<c:set var="basicEntriesActive" value=""/>
	<c:set var="companyEntriesActive" value=""/>
		
	<c:set var="homeActive" value=""/>
	<c:set var="appUserActive" value=""/>
	<c:set var="taxRulesActive" value=""/>
	<c:set var="aboutActive" value=""/>
	<c:set var="contactActive" value=""/>	
	<c:set var="transactionTypesActive" value=""/>
	<c:set var="personTypesActive" value=""/>
	<c:set var="addressStatesActive" value=""/>
	<c:set var="operationsActive" value=""/>
	
	<c:set var="icmsTypeActive" value=""/>
	<c:set var="icmsCSTActive" value=""/>
	<c:set var="pisCOFINSCSTActive" value=""/>
			            
	<c:choose>
	    <c:when test="${activeAction.equals('AppUsers')}">
	    	<c:set var="appUserActive" value="active"/>	        
	    </c:when>
	    <c:when test="${activeAction.equals('About')}">
	        <c:set var="aboutActive" value="active"/>
	    </c:when>
	    <c:when test="${activeAction.equals('Contact')}">
	        <c:set var="contactActive" value="active"/>
	    </c:when>
	    <c:when test="${activeAction.equals('TaxRules')}">
	        <c:set var="taxRulesActive" value="active"/>
	    </c:when>
	    <c:when test="${activeAction.equals('TransactionTypes')}">
	        <c:set var="transactionTypesActive" value="active"/>
	        <c:set var="companyEntriesActive" value="active"/>
	    </c:when>	    
	    <c:when test="${activeAction.equals('PersonTypes')}">
	    	<c:set var="personTypesActive" value="active"/>	        
	        <c:set var="companyEntriesActive" value="active"/>
	    </c:when>
	    <c:when test="${activeAction.equals('AddressStates')}">
	    	<c:set var="addressStatesActive" value="active"/>	        
	        <c:set var="basicEntriesActive" value="active"/>
	    </c:when>
	    <c:when test="${activeAction.equals('Operations')}">
	    	<c:set var="operationsActive" value="active"/>	        
	        <c:set var="companyEntriesActive" value="active"/>
	    </c:when>	  
	    <c:when test="${activeAction.equals('ICMSType')}">
	    	<c:set var="icmsTypeActive" value="active"/>	        
	        <c:set var="basicEntriesActive" value="active"/>
	    </c:when>
	    <c:when test="${activeAction.equals('PISCOFINSCST')}">
	    	<c:set var="pisCOFINSCSTActive" value="active"/>	        
	        <c:set var="basicEntriesActive" value="active"/>
	    </c:when>
	    <c:when test="${activeAction.equals('ICMSCST')}">
	    	<c:set var="icmsCSTActive" value="active"/>	        
	        <c:set var="basicEntriesActive" value="active"/>
	    </c:when>	    
	    <c:otherwise>
	        <c:set var="homeActive" value="active"/>
	    </c:otherwise>
	</c:choose>

	<div class="navbar navbar-default navbar-static "> <!--  navbar-static | navbar-fixed-top -->
		<div class="container-fluid">
	        <div class="navbar-header" align="center">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#SiteNavBar">
				  <span class="icon-bar"></span>
				  <span class="icon-bar"></span>
				  <span class="icon-bar"></span>                        
				</button>
	        	<a class="navbar-brand" href="#" contenteditable="false">MVC App</a>
	        </div>
	        <div class="collapse navbar-collapse" id="SiteNavBar">
		        <ul class="nav navbar-nav">
		            <li class="${homeActive}"><a href="#" class="" contenteditable="false">Home</a></li>
		            
		            <li class="dropdown ${basicEntriesActive}">
			          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Basic Entries&nbsp;<span class="caret"></span></a>
			          <ul class="dropdown-menu">
			            <li class="${addressStatesActive}"><a href="${pageContext.request.contextPath}/addressStates" class="" contenteditable="false">Address States</a>
			            <li role="separator" class="divider"></li>			            
			            <li class="${icmsTypeActive}"><a href="${pageContext.request.contextPath}/icmsTypes" class="" contenteditable="false">ICMS Type</a></li>			            
			            <li class="${icmsCSTActive}"><a href="${pageContext.request.contextPath}/cstICMS" class="" contenteditable="false">ICMS CST</a></li>
			            <li role="separator" class="divider"></li>
			            <li class="${pisCOFINSCSTActive}"><a href="${pageContext.request.contextPath}/cstPisCofins" class="" contenteditable="false">PIS/COFINS CST</a></li>
			          </ul>
			        </li>
			        
			        <li class="dropdown ${companyEntriesActive}">
			          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Company Entries&nbsp;<span class="caret"></span></a>
			          <ul class="dropdown-menu">
			            <li class="${personTypesActive}"><a href="${pageContext.request.contextPath}/personTypes" class="" contenteditable="false">Person Types</a>
			            <li class="${operationsActive}"><a href="${pageContext.request.contextPath}/operations" class="" contenteditable="false">Operations</a>
			            <li class="${transactionTypesActive}"><a href="${pageContext.request.contextPath}/transactionTypes" class="" contenteditable="false">Transaction Types</a>			            
			            <li role="separator" class="divider"></li>
			            <li><a href="#">Another action</a></li>			            			            
			            <li role="separator" class="divider"></li>
			            <li><a href="#">Other</a></li>
			          </ul>
			        </li>
			        
		            <li class="divider-vertical"></li>
		            <li class="${appUserActive}"><a href="${pageContext.request.contextPath}/appUsers" class="" contenteditable="false">App Users</a>
		            
		            <li class="divider-vertical"></li>
		            <li class="${taxRulesActive}"><a href="${pageContext.request.contextPath}/taxRules" class="" contenteditable="false">Tax Rules</a>
		            
		            <li class="divider-vertical"></li>
		            <li class="${aboutActive}"><a href="${pageContext.request.contextPath}/about" class="" contenteditable="false">About</a>		
		            </li>
	
		            <li><a href="${pageContext.request.contextPath}/logout" class="">Logout</a></li>
		        </ul>
	        </div>
        </div>
    </div>     
    <div class="jumbotron">
         <h1 class="">JNTaxBR User Portal App</h1>
         <h5>${pageTitle}</h5>
         <span class="">Seja bem vindo, esta é uma aplicação de testes.</span>
    </div>
    <div class="row"></div>
</header>    