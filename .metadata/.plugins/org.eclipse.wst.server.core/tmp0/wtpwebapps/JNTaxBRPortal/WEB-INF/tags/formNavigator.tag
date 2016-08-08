<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="backPath" required="true" type="java.lang.String" %>
<%@ attribute name="submitName" required="false" type="java.lang.String" %>
<%@ attribute name="isDelete" required="false" type="java.lang.Boolean" %>

<c:choose>
	<c:when test="${empty submitName && !isDelete}">
	    <c:set var="submitName" value="Salvar" />		    		
	</c:when>    
	<c:when test="${empty submitName && isDelete}">
	    <c:set var="submitName" value="Excluir" />		    		
	</c:when>	
</c:choose>		
		<div class="form-group">
		  <div class="col-sm-offset-3 col-sm-10">
		    <button type="submit" class="btn btn-success">${submitName}</button>
		    &nbsp;
		    <c:if test="${!isDelete}">
		    <a href="${pageContext.request.contextPath}/${backPath}/create" class="btn btn-primary">Criar Novo</a>
		    &nbsp;
		    </c:if>
		    <a href="${pageContext.request.contextPath}/${backPath}" class="btn btn-default">Voltar</a>
		  </div>
		</div>

