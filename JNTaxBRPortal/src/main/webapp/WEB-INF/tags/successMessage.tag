<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="successMsg" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	
	
	<c:if test="${not empty successMsg}">
	<div id="successMsgPanel" class="fade in messagePanel affix  col-md-4 col-md-offset-6 col-xs-8 col-xs-offset-3">
		<div class="inner-message <c:out value="${cssMsgPanels}" />">	
			<div class="panel panel-success">
		        <div class="panel-heading">
		        	<button type="button" class="close" data-target="#successMsgPanel" data-effect="fadeOut" data-dismiss="alert">&times;</button>
		            <h3 class="panel-title">Mensagem...</h3>
		        </div>
		        <div class="panel-body">${successMsg}</div>
		    </div>
	    </div>
    </div>
	</c:if>
	

