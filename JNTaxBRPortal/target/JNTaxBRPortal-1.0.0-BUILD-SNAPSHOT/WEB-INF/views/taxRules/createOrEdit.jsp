<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<c:url value="/resources/js" var="jsPath" />
<c:url value="/operations" var="operationsPath" />

<c:set var="cssFormInput" value="col-sm-2 col-md-2"/>
<c:set var="cssMsgPanels" value="col-md-4 col-md-offset-2 col-sm-1"/>
<tags:simplePage pageTitle="Tax Rules" activeAction="TaxRules">
  <form:form method="POST" modelAttribute="rule" action="${action}" cssClass="form-horizontal">
    
    <div id="tabs">
	    <ul class="nav nav-tabs">
		  <li role="presentation" class="active"><a id="tabHOverview"  href="#tabOverview" data-toggle="tab">Visão Geral</a></li>
		  <li role="presentation"><a id="tabHICMS" href="#tabICMS" data-toggle="tab">ICMS</a></li>
		  <li role="presentation"><a id="tabHPISCOFINS" href="#tabPISCOFINS" data-toggle="tab">PIS/COFINS</a></li>
		  <li role="presentation"><a id="tabHISS" href="#tabISS" data-toggle="tab">ISS</a></li>
		</ul> 	  

		<div class="row">
			<br />
			<div class="tab-content ">
				<div class="tab-pane fade in active" id="tabOverview">
					<div class="form-group">
					  <label for="id" class="<c:out value="${cssFormInput}" /> control-label">Id</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="text" path="id" id="id" disabled="true" cssClass="form-control" />
					    <form:input type="hidden" path="id" id="id" />
					  </div>
					<!-- </div>
					<div class="form-group">
					 -->
					  <label for="fromState.id" class="<c:out value="${cssFormInput}" /> control-label">UF Origem</label>
					  <div class="<c:out value="${cssFormInput}" />">
					  	<form:select path="fromState.id" items="${addressStateList}" itemValue="id" itemLabel="name" cssClass="form-control"/>		  			  	
					  </div>
					<!--  </div>	
					<div class="form-group">  -->
					  <label for="toState.id" class="<c:out value="${cssFormInput}" /> control-label">UF Destino</label>
					  <div class="<c:out value="${cssFormInput}" />">
					  	<form:select path="toState.id" items="${addressStateList}" itemValue="id" itemLabel="name" cssClass="form-control"/>		  			  	
					  </div>
					</div>
					
					<div class="form-group">
					  <label for="transactionType.id" class="<c:out value="${cssFormInput}" /> control-label">Tipo de Transação</label>
					  <div class="<c:out value="${cssFormInput}" />">
						<form:select path="transactionType.id" cssClass="form-control" id="transactionType_id">
							<form:option value="0" label="Todas"/>
			    			<form:options  items="${transactionTypeList}" itemValue="id" itemLabel="name"  />
						</form:select>
					  </div>
					<!-- </div>	
					<div class="form-group"> -->
					  <label for="operation.id" class="<c:out value="${cssFormInput}" /> control-label">Operação</label>
					  <div class="<c:out value="${cssFormInput}" />">
						<form:select path="operation.id" cssClass="form-control" id="operation_id">
							<form:option value="0" label="Todas"/>
			    			<form:options  items="${operationList}" itemValue="id" itemLabel="name"  />
						</form:select>
					  </div>
					<!-- </div>	
					<div class="form-group">  -->
					  <label for="personType.id" class="<c:out value="${cssFormInput}" /> control-label">Tipo de Pessoa</label>
					  <div class="<c:out value="${cssFormInput}" />">
						<form:select path="personType.id" cssClass="form-control">
							<form:option value="0" label="Todos"/>
			    			<form:options  items="${personTypeList}" itemValue="id" itemLabel="name"  />
						</form:select>
					  </div>
					</div>	
					
					<div class="form-group">
					  <label for="NCM" class="<c:out value="${cssFormInput}" /> control-label">NCM</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="text" path="NCM" id="NCM" cssClass="form-control" />		    
					  </div>
					<!-- </div>
					<div class="form-group">  -->
					  <label for="CEST" class="<c:out value="${cssFormInput}" /> control-label">CEST</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="text" path="CEST" id="CEST" cssClass="form-control" />		    
					  </div>
					<!--  </div>
					<div class="form-group">  -->
					  <label for="itemId" class="<c:out value="${cssFormInput}" /> control-label">Item</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="text" path="itemId" id="itemId" cssClass="form-control tabField" data-tabId="#tabHICMS"  />		    
					  </div>
					</div>
					<div class="form-group">
					  <label for="active" class="<c:out value="${cssFormInput}" /> control-label">Ativo?</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:checkbox path="active" id="active" cssClass="form-control tabField" data-tabId="#tabHISS"  />
					  </div>
					</div>
				</div>
				<div class="tab-pane fade" id="tabICMS">
					<div class="form-group">
					  <label for="taxRuleICMS.icmsCST" class="<c:out value="${cssFormInput}" /> control-label">CST</label>
					  <div class="<c:out value="${cssFormInput}" />">
					  	<form:select path="taxRuleICMS.icmsCST.CST" cssClass="form-control">
					  		<form:option value="" label="Escolha um CST"/>		    			
			    			<c:forEach var="cst" items="${icmsCSTList}">
						        <form:option value="${cst.CST}"><c:out value="${cst.CST}-${cst.description}"/></form:option>
						    </c:forEach>
			    		</form:select>
					  </div>
					  <label for="taxRuleICMS.taxPayerRate" class="<c:out value="${cssFormInput}" /> control-label">Alíquota Contribuinte</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="number" step="0.01" path="taxRuleICMS.taxPayerRate" id="taxRuleICMS.taxPayerRate" cssClass="form-control" />
					  </div>
					  <label for="taxRuleICMS.finalConsumerRate" class="<c:out value="${cssFormInput}" /> control-label">Alíq. Consumidor Final</label>
					  <div class="<c:out value="${cssFormInput}" />">
					    <form:input type="number" step="0.01" path="taxRuleICMS.finalConsumerRate" id="taxRuleICMS.finalConsumerRate" cssClass="form-control" />
					  </div>				  
					</div>
					
					<div class="form-group">
					  <label for="taxRuleICMS.reductionPercent" class="<c:out value="${cssFormInput}" /> control-label">% de Redução</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="number" step="0.01" path="taxRuleICMS.reductionPercent" id="taxRuleICMS.reductionPercent" cssClass="form-control" />
					  </div>				 
					  <label for="taxRuleICMS.legalNotes" class="<c:out value="${cssFormInput}" /> control-label">Textos Legais</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="text" path="taxRuleICMS.legalNotes" id="taxRuleICMS.legalNotes" cssClass="form-control" />
					  </div>
					 <label for="taxRuleICMS.active" class="<c:out value="${cssFormInput}" /> control-label">Ativo?</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:checkbox path="taxRuleICMS.active" id="taxRuleICMS.active" cssClass="form-control" />
					  </div>				  				  
					</div>
					  
					<div class="form-group">
					  <label for="taxRuleICMS.mvaRate" class="<c:out value="${cssFormInput}" /> control-label">MVA</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="number" step="0.01" path="taxRuleICMS.mvaRate" id="taxRuleICMS.mvaRate" cssClass="form-control" />				    
					  </div>				 
					  <label for="taxRuleICMS.stRate" class="<c:out value="${cssFormInput}" /> control-label">Alíquota de ST</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="number" step="0.01" path="taxRuleICMS.stRate" id="taxRuleICMS.stRate" cssClass="form-control" />
					  </div>
					  <label for="taxRuleICMS.stReductionPercent" class="<c:out value="${cssFormInput}" /> control-label">% de Redução ST</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="number" step="0.01" path="taxRuleICMS.stReductionPercent" id="taxRuleICMS.stReductionPercent" cssClass="form-control tabField" data-tabId="#tabHPISCOFINS" />
					  </div>				  				  
					</div>					
				</div>
				<div class="tab-pane fade" id="tabPISCOFINS">
					
					<div class="form-group">
					  <label for="taxRulePISCOFINS.pisCST" class="<c:out value="${cssFormInput}" /> control-label">CST PIS</label>
					  <div class="<c:out value="${cssFormInput}" />">
					  	<form:select path="taxRulePISCOFINS.pisCST.CST" cssClass="form-control">
					  		<form:option value="" label="Escolha um CST"/>		    			
			    			<c:forEach var="cst" items="${pisCofinsCSTList}">
						        <form:option value="${cst.CST}"><c:out value="${cst.CST}-${cst.description}"/></form:option>
						    </c:forEach>
			    		</form:select>	  			  	
					  </div>
					  <label for="taxRulePISCOFINS.pisRate" class="<c:out value="${cssFormInput}" /> control-label">Alíquota de PIS</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="number" step="0.01" path="taxRulePISCOFINS.pisRate" id="taxRulePISCOFINS.pisRate" cssClass="form-control" />
					  </div>
					</div>
					
					<div class="form-group">
					  <label for="taxRulePISCOFINS.cofinsCST" class="<c:out value="${cssFormInput}" /> control-label">CST COFINS</label>
					  <div class="<c:out value="${cssFormInput}" />">
					  	<form:select path="taxRulePISCOFINS.cofinsCST.CST" cssClass="form-control">
					  		<form:option value="" label="Escolha um CST"/>		    			
			    			<c:forEach var="cst" items="${pisCofinsCSTList}">
						        <form:option value="${cst.CST}"><c:out value="${cst.CST}-${cst.description}"/></form:option>
						    </c:forEach>
			    		</form:select>		  			  	
					  </div>				  
					  <label for="taxRulePISCOFINS.cofinsRate" class="<c:out value="${cssFormInput}" /> control-label">Alíquota de COFINS</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="number" step="0.01" path="taxRulePISCOFINS.cofinsRate" id="taxRulePISCOFINS.cofinsRate" cssClass="form-control" />
					  </div>
					</div>
					
					<div class="form-group">
					  <label for="taxRulePISCOFINS.legalNotes" class="<c:out value="${cssFormInput}" /> control-label">Textos Legais</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:input type="text" path="taxRulePISCOFINS.legalNotes" id="taxRulePISCOFINS.legalNotes" cssClass="form-control" />
					  </div>
					  <label for="taxRulePISCOFINS.active" class="<c:out value="${cssFormInput}" /> control-label">Ativo?</label>
					  <div class="<c:out value="${cssFormInput}" />">		    
					    <form:checkbox path="taxRulePISCOFINS.active" id="taxRulePISCOFINS.active" cssClass="form-control tabField" data-tabId="#tabHISS" />
					  </div>
					</div>
				</div>
				<div class="tab-pane fade" id="tabISS">
					<h3>ISS</h3>
				</div>
			</div>
		</div>
	</div>
	<tags:formNavigator backPath="taxRules" />	
	  
	<tags:successMessage successMsg="${successMsg}" />
	<tags:errorMessage errorMsg="${errorMsg}" />
  </form:form>
  <script type="text/javascript"> var operationsPath = "<c:out value="${operationsPath}" />"; </script>
  <script type="text/javascript" src="${jsPath}/app.taxRules.js"></script>
  
</tags:simplePage>